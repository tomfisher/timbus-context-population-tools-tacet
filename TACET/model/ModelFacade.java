/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package squirrel.model;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import squirrel.controller.ClassificatorController;
import squirrel.model.io.CSVDataSource;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSink;
import squirrel.model.io.DataSource;
import squirrel.model.io.IOFactory;
import squirrel.model.io.Reader;
import squirrel.util.Range;
import squirrel.view.ProgressView;

public class ModelFacade implements StatisticModel {
    private AnnotationModel annotationModel;
    private Reader reader;
    private SensorDataFilterManager sensorDataFilterManager = null;
    private SensorDataModel sensorDataModel = null;
    private DataSource<? extends DataColumn> dataSource;
    private Range startAndEndTime;
    private TimeCoordinator timeCoordinator;
    private Double[] sensorAvgList;
    private ClassificatorController classificatorController;

    public ModelFacade(DataSource<? extends DataColumn> currentDataSource) {
        this(null, null, null, null, currentDataSource, null, null);
    }

    public ModelFacade(AnnotationModel annotationModel, Reader reader,
        SensorDataFilterManager sensorDataFilterManager, SensorDataModel sensorDataModel,
        DataSource<? extends DataColumn> currentDataSource, Range startAndEndTime,
        TimeCoordinator timeCoordinator) {
        super();
        this.annotationModel = annotationModel;
        this.reader = reader;
        this.sensorDataFilterManager = sensorDataFilterManager;
        this.sensorDataModel = sensorDataModel;
        this.dataSource = currentDataSource;
        this.startAndEndTime =
            startAndEndTime == null ? new Range(getReader().startTimeStamp(), getReader()
                .endTimeStamp()) : startAndEndTime;
        this.timeCoordinator = timeCoordinator;
    }

    public List<Annotation[]> getAllAnnoations() {
        ArrayList<Annotation[]> tracks = new ArrayList<>();
        for (int j = 0; j < dataSource.getNumberOfAnnotations(); j++) {
            Iterable<Annotation> annotations =
                getAnnotations(new Range(getStartTimeStamp(), getEndTimeStamp()), j);
            int count = 0;
            for (Annotation annotation : annotations) {
                count++;
            }

            Annotation[] at = new Annotation[count];
            count = 0;
            for (Annotation annotation : annotations) {
                at[count] = annotation;
                count++;
            }
            tracks.add(at);
        }
        return tracks;
    }

    public void insertAnnoationModel(AnnotationModelImpl annotationModel) {
        this.annotationModel = annotationModel;
        annotationModel.setReader(this.reader);
    }

    public AnnotationModel getAnnotationModel() {
        return getAnnotationModelIntern();
    }

    public DataSource<? extends DataColumn> getCurrentDataSource() {
        return dataSource;
    }

    public SensorDataFilter getFilteredSensorData(Range range, int rate) {
        return getSensorDataFilterManager().getFilteredSensorData(range, rate);
    }

    public SensorDatum getSensorDatum(long timestamp) {
        return getSensorDataModel().getSensorDatum(timestamp);
    }

    public double getSensorValue(long timestamp, int track) {
        return getSensorDataModel().getSensorValue(timestamp, track);
    }

    public Iterable<Annotation> getAnnotations(Range range, int track) {
        return getAnnotationModelIntern().getAnnotations(range, track);
    }

    public Annotation getAnnotation(long timestamp, int track) {
        return getAnnotationModelIntern().getAnnotation(timestamp, track);
    }

    public boolean canInsertAt(Range range, int track) {
        return getAnnotationModelIntern().canInsertAt(range, track);
    }

    public void deleteAnnotations(Range range, int track) {
        getAnnotationModelIntern().deleteAnnotations(range, track);
    }

    public void insertAnnotation(Annotation annot, int track) {
        getAnnotationModelIntern().insertAnnotation(annot, track);
    }

    public void replaceAnnotation(Annotation annotation, int track) {
        getAnnotationModelIntern().replace(annotation, track);
    }

    public double getAverageDistance() {
        return reader == null ? getReader().averageDistance() : reader.averageDistance();
    }

    private AnnotationModel getAnnotationModelIntern() {
        if (this.annotationModel == null)
            /*
             * this.annotationModel = new
             * AnnotationModelImpl(getReader().readAnnotations( new
             * Range(getStartTimeStamp(), getEndTimeStamp())),
             * dataSource.getNumberOfOldAnnotations() +
             * dataSource.getNumberOfNewAnnotations());
             */
            this.annotationModel =
                new AnnotationModelImpl(getReader(),
                    new Range(getStartTimeStamp(), getStartTimeStamp() + 8196),
                    dataSource.getNumberOfOldAnnotations()
                        + dataSource.getNumberOfNewAnnotations());
        return this.annotationModel;
    }

    public Reader getReader() {
        if (reader == null) {
            if (dataSource.getType() == DataSource.Type.CSV) {
                return IOFactory.createCSVReader((CSVDataSource) dataSource);
            }
        } else {
            return reader;
        }

        throw new UnsupportedOperationException("Can't creat a reader should never occur");
    }

    private SensorDataFilterManager getSensorDataFilterManager() {
        return sensorDataFilterManager == null ? (sensorDataFilterManager =
            new SensorDataFilterManager(8, 8, 0.72, 1000, getSensorDataModel(), startAndEndTime))
            : sensorDataFilterManager;
    }

    private SensorDataModel getSensorDataModel() {
        if (sensorDataModel == null) {
            // If average distance is > 5 minutes, assume the complete data set
            // to fit in memory
            if (getReader().averageDistance() > 300000.0) {
                this.sensorDataModel = new SparseDataModel(getReader());
                System.out.println("INFO: Using Sparse Data Model.");
            } else {
                this.sensorDataModel =
                    new HashMapModel(256, 16384, (int) getReader().averageDistance(), getReader());
            }
        }
        return sensorDataModel;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void export(DataSink dataSink, Dialog view) {
        ProgressView progressView = new ProgressView("Export data ...", "This may take a while.");
        ExportModel em = new ExportModel(this, dataSink);
        em.addPropertyChangeListener(progressView);
        progressView.presentAsDialog(view);
        em.export();
    }

    public void setFilterSize(int newSize) {
        getSensorDataFilterManager().setFilterSize(newSize);
    }

    public int getFilterSize() {
        return getSensorDataFilterManager().getFilterSize();
    }

    /**
     * Returns the total number of annotations.
     * @return number of annotations
     */
    @Override
    public int countAnnotations() {
        int res = 0;
        for (int i = 0; i < dataSource.getNumberOfAnnotations(); i++) {
            res += countAnnotations(i);
        }
        return res;
    }

    /**
     * Returns the number of annotations for a given track.
     * @param trackNo track number
     * @param number of annotations for {@code trackNo}
     */
    @Override
    public int countAnnotations(int trackNo) {
        int res = 0;
        Range range = new Range(getStartTimeStamp(), getEndTimeStamp());
        for (Annotation anno : getAnnotationModelIntern().getAnnotations(range, trackNo)) {
            if (anno != null) {
                res++;
            }
        }
        return res;
    }

    /**
     * Returns how many percent of the sensordata is annotated.
     * @return percentage of annotations for sensordata
     */
    @Override
    public float getPercentAnnotation(int trackNo) {
        float res;
        float timeCount = (getEndTimeStamp() - getStartTimeStamp());
        int annoRange = 0;
        for (Annotation anno : getAnnotations(startAndEndTime, trackNo)) {
            if (anno != null) {
                annoRange += anno.getEnd() - anno.getStart();
            }
        }
        res = (annoRange / timeCount);
        return res * 100;
    }

    /**
     * Returns the median of sensordata.
     * @return median of sensordata
     */
    @Override
    public double getMedianSensordata(int trackNo) {
        double out = Double.NaN;
        long ts = getStartTimeStamp() + (long) ((getEndTimeStamp() - getStartTimeStamp()) / 2);
        int delta = 0;
        while (Double.isNaN(out)) {
            out = this.getSensorDataModel().getSensorValue(ts + delta, trackNo);
            if (Double.isNaN(out)) {
                out = this.getSensorDataModel().getSensorValue(ts - delta, trackNo);
            }
            delta++;
        }
        return out;
    }

    public Iterable<SensorDatum> getSensorDataInRange(Range r) {
        return sensorDataModel.getSensorDataRange(r, 1);
    }

    /**
     * Returns the average sensordata.
     * @return average sensordata
     */
    @Override
    public double getAvgSensordata(int trackNo) {
        if (sensorAvgList == null) {
            int size = getNumberOfSensorTracks();
            sensorAvgList = new Double[size];
        }
        if (sensorAvgList[trackNo] == null) {
            long mid = (getEndTimeStamp() - getStartTimeStamp()) / 2 + getStartTimeStamp();
            Range range1 = new Range(getStartTimeStamp(), mid);
            Range range2 = new Range(mid + 1, getEndTimeStamp());
            Iterator<SensorDatum> it1 =
                getSensorDataModel().getSensorDataRange(range1, 1).iterator();
            Iterator<SensorDatum> it2 =
                getSensorDataModel().getSensorDataRange(range2, 1).iterator();
            double avg = 0;
            int i = 1;
            while (it1.hasNext() || it2.hasNext()) {
                if (it1.hasNext()) {
                    avg = avg * (i - 1) / i + it1.next().getValues()[trackNo] / i;
                    i++;
                }
                if (it2.hasNext()) {
                    avg = avg * (i - 1) / i + it2.next().getValues()[trackNo] / i;
                    i++;
                }
            }
            sensorAvgList[trackNo] = avg;
        }
        return sensorAvgList[trackNo];
    }

    /**
     * Returns the standard deviation of sensordata.
     * @return standard deviation of sensordata
     */
    @Override
    public double getStandardDeviationSensordata(int trackNo) {
        double avg = getAvgSensordata(trackNo);
        long mid = (getEndTimeStamp() - getStartTimeStamp()) / 2 + getStartTimeStamp();
        Range range1 = new Range(getStartTimeStamp(), mid);
        Range range2 = new Range(mid + 1, getEndTimeStamp());
        Iterator<SensorDatum> it1 = getSensorDataModel().getSensorDataRange(range1, 1).iterator();
        Iterator<SensorDatum> it2 = getSensorDataModel().getSensorDataRange(range2, 1).iterator();
        double dev = 0;
        double tmp = 0;
        int i = 1;
        for (i = 1; i < 3; i++) {
            if (it1.hasNext())
                tmp += Math.pow(it1.next().getValues()[trackNo] - avg, 2);
            if (it2.hasNext()) {
                tmp += Math.pow(it2.next().getValues()[trackNo] - avg, 2);
                i++;
            }
        }
        while (it1.hasNext() || it2.hasNext()) {
            if (it1.hasNext()) {
                tmp =
                    tmp * (i - 2) / (i - 1)
                        + Math.pow(it1.next().getValues()[trackNo] - avg, 2)
                        / (i - 1);
                i++;
            }
            if (it2.hasNext()) {
                tmp =
                    tmp * (i - 2) / (i - 1)
                        + Math.pow(it2.next().getValues()[trackNo] - avg, 2)
                        / (i - 1);
                i++;
            }
        }
        dev = Math.sqrt(tmp);
        return dev;
    }

    @Override
    public long getStartTimeStamp() {
        return this.startAndEndTime.getStart();
    }

    @Override
    public long getEndTimeStamp() {
        return this.startAndEndTime.getEnd();
    }

    public TimeCoordinator getTimeCoordinator() {
        return (this.timeCoordinator == null) ?
            this.timeCoordinator =
                new TimeCoordinator(1, getEndTimeStamp() - getStartTimeStamp())
            : this.timeCoordinator;
    }

    @Override
    public int getNumberOfSensorTracks() {
        return this.getCurrentDataSource().getNumberOfSensorColumns();
    }

    @Override
    public int getNumberOfAnnotationTracks() {
        return this.getCurrentDataSource().getNumberOfAnnotations();
    }

    @Override
    public double getAvgTimeDistance() {
        return this.getReader().averageDistance();
    }

    public void setClassificatorController(ClassificatorController classificatorController) {
        this.classificatorController = classificatorController;
    }

    // public void classify(Range range) {
    // classificatorController.classify(range);
    // }

    public void mergeAnnotations(int track, Range range) {
        Annotation curr;
        Annotation prev = null;
        for (Annotation a : getAnnotations(range, track)) {
            if (prev == null) {
                prev = a;
            } else {
                curr = a;
                if (curr.getStart() >= prev.getEnd()) {
                    if (curr.toValue().equals(prev.toValue())) {
                        Range between = new Range(prev.getEnd(), curr.getStart());
                        Iterator<SensorDatum> iter = getSensorDataInRange(between).iterator();
                        if (iter.hasNext())
                            iter.next();
                        if (!iter.hasNext() || prev.getEnd() ==
                            curr.getStart()) {
                            prev.setNewRange(new Range(prev.getStart(), curr.getEnd()));
                            deleteAnnotations(curr.getRange(), track);
                            deleteAnnotations(prev.getRange(), track);
                            insertAnnotation(prev, track);
                            curr = prev;
                        }
                    }
                }
                prev = curr;
            }
        }
        timeCoordinator.setTime(timeCoordinator.getTime());
    }
}
