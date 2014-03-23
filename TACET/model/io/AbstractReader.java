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

package squirrel.model.io;

import java.util.ArrayList;
import java.util.List;

import squirrel.model.Annotation;
import squirrel.model.AnnotationFactory;
import squirrel.model.SensorDatum;
import squirrel.util.Range;

/**
 * The abstract class Reader for Reading the different files
 * (CSV/Binary/DataBase)
 * @author marc
 *
 */
public abstract class AbstractReader<T extends DataColumn> implements Reader {

    protected AnnotationFactory aF;
    protected Range bufferRange;
    protected List<SensorAnnotationTuple> bufferTuple;
    protected DataSource<T> dataSource;

    /**
     * Constructs a new AbstractReader with the given {@code dataSource}.
     * @param dataSource contains descriptions for each column and the path of
     *            the source file.
     */
    public AbstractReader(DataSource<T> dataSource) {
        aF = dataSource.getAnnotationFactory();
        this.dataSource = dataSource;
    }

    /*
     * (non-Javadoc)
     *
     * @see squirrel.model.io.IReader#readSensorData(squirrel.util.Range)
     */
    @Override
    public List<SensorDatum> readSensorData(Range range) {
        if (!range.equals(bufferRange)) {
            readData(range);
        }
        assert bufferTuple != null;
        List<SensorDatum> listDatum = new ArrayList<>();
        if(bufferTuple==null){
            return listDatum;
        }
        for (SensorAnnotationTuple b : bufferTuple) {
            listDatum.add(b.getSensorDatum());
        }
        return listDatum;
    }

    /*
     * (non-Javadoc)
     *
     * @see squirrel.model.io.IReader#readAnnotations(squirrel.util.Range)
     */
    @Override
    public List<Annotation[]> readAnnotations(Range range) {
        if (!range.equals(bufferRange)) {
            readData(range);
        }
        assert bufferTuple != null;
        List<Annotation[]> listAnno = new ArrayList<>();
        if(bufferTuple==null){
            return listAnno;
        }
        for (SensorAnnotationTuple b : bufferTuple) {
            listAnno.add(b.getAnnotation());
        }
        return listAnno;
    }

    /*
     * (non-Javadoc)
     *
     * @see squirrel.model.io.IReader#setaF(squirrel.model.AnnotationFactory)
     */
    @Override
    public void setaF(AnnotationFactory aF) {
        this.aF = aF;
    }

    /**
     * Returns the position of the timestamp in the description
     * @return the position of the timestamp in the description
     */
    protected int posOfTimestamp() {
        for (DataColumn dc : dataSource) {
            if (dc.getType().isTimeStamp())
                return dataSource.indexOf(dc);
        }
        throw new IllegalArgumentException("Description needs a Timestamp");
    }

    /**
     * Return the Annotation
     * @param range range containing start and end of the Annotation
     * @param value the value for the Annotation
     * @param dataColumn description of the column
     * @param trackNumber the number of the track for the annotation
     * @return an Annotation for the value
     */
    protected Annotation getAnnotation(Range range, String value, DataColumn dataColumn,
            int trackNumber) {
        if (value.equals("")) {
            //why equals and then null
            //return null;
        }
        switch (dataColumn.getType()) {
        case FLOATING_POINT_ANNOTATION:
            return aF.createFloatingpointAnnotation(trackNumber, range, Float.parseFloat(value));
        case INTEGER_ANNOTATION:
            return aF.createIntegerAnnotation(trackNumber, range, Integer.parseInt(value));
        case LABEL_ANNOTATION:
            return aF.createLabelAnnotation(trackNumber, range, value);
        default:
            throw new UnknownError("Wrong use of getAnnotation");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see squirrel.model.io.IReader#readData(squirrel.util.Range)
     */
    @Override
    public abstract Iterable<SensorAnnotationTuple> readData(Range range);

    /*
     * (non-Javadoc)
     *
     * @see squirrel.model.io.IReader#averageDistance()
     */
    @Override
    public abstract double averageDistance();

    /*
     * (non-Javadoc)
     *
     * @see squirrel.model.io.IReader#startTimeStamp()
     */
    @Override
    public abstract long startTimeStamp();

    /*
     * (non-Javadoc)
     *
     * @see squirrel.model.io.IReader#endTimeStamp()
     */
    @Override
    public abstract long endTimeStamp();

}
