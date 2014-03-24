/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
 */

package squirrel.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import squirrel.model.io.CSVDataSource;
import squirrel.model.io.CommaSeparatedValuesWriter;
import squirrel.model.io.DataSink;

import squirrel.model.io.Writer;
import squirrel.util.Range;

public class ExportModel {

    ModelFacade mf;
    DataSink ds;
    Writer writer;
    Worker swingworker;

    public ExportModel(ModelFacade mf, DataSink ds) {
        this.mf = mf;
        this.ds = ds;
        swingworker = new Worker();
    }

    public void export() {
        try {
            swingworker.doInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Worker extends SwingWorker<Boolean, Void> {

        @Override
        protected Boolean doInBackground() throws Exception {
            setProgress(0);
            createWriter();

            long offset =
                    (long) ((((mf.getEndTimeStamp() - mf.getStartTimeStamp()) / mf
                            .getAverageDistance()) / 100) * mf.getAverageDistance());

            long start = mf.getStartTimeStamp()-1;
            long end = start + offset;
            writer.initialiseWriter();
            for (int i = 0; i < 100; i++) {
                Range rangeToWrite = new Range(start, end);

                // Creates the tracks
                ArrayList<Annotation[]> tracks = new ArrayList<>();
                ArrayList<SensorDatum> sdA = new ArrayList<>();
                synchronized (this) {
                    for (int j = 0; j < ds.getDataSource().getNumberOfAnnotations(); j++) {

                        Iterable<Annotation> annotations = mf.getAnnotations(rangeToWrite, j);

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

                    Iterable<SensorDatum> sd =
                            mf.getSensorDataInRange(rangeToWrite);

                    for (SensorDatum s : sd) {
                        sdA.add(s);
                    }
                }
                SensorDatum[] temp = (SensorDatum[]) sdA.toArray(new SensorDatum[sdA.size()]);
                if (sdA != null) {
                    writer.writeExport(tracks, temp);
                }

                // for progressbar
                setProgress(i);
                // For the next iteration
                start = end;
                if (i == 98) {
                    end = mf.getEndTimeStamp()+1;
                } else {
                    end += offset;
                }
            }
            writer.finalizeWriter();
            return true;
        }
    }

    private void createWriter() {
        switch (ds.getType()) {
        case CSV:
            writer =
                    new CommaSeparatedValuesWriter(ds.getLocation(),
                            (CSVDataSource) ds.getDataSource());
            break;
        default:
            break;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        swingworker.addPropertyChangeListener(pcl);
    }
}
