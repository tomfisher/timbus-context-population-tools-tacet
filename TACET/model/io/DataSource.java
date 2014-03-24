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

package squirrel.model.io;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import squirrel.model.AnnotationFactory;
import weka.core.Attribute;

public class DataSource<T extends DataColumn> implements Iterable<T>, Serializable, Cloneable {

    private static final long serialVersionUID = -7901961351301035378L;

    /**
     * enum of all data types (binary, csv, database)
     */
    public enum Type {
        CSV, BINARY, DATABASE;
    }

    /**
     * Location of file on disc
     */
    private URL location;

    /**
     * List of data sets
     */
    private List<T> dataColumns = new ArrayList<>();

    /**
     * List of Paths to the media files on disk
     */
    private List<String> mediaPaths = new ArrayList<>();

    /**
     * type of file on disk
     */
    private Type type;

    /**
     * AnnotationFactory to create Annotations
     */
    private AnnotationFactory annotationFactory;

    /**
     * Constructor of data source
     *
     * @param location - location of file on disk
     * @param dataColumns - Iterable of data columns
     * @param mediaPaths - Paths to media files on disk
     * @param annotationFactory - AnnotationFactory for g
     */
    public DataSource(URL location, Iterable<T> dataColumns, List<String> mediaPaths,
            AnnotationFactory annotationFactory) {
        super();
        this.location = location;
        for (T t : dataColumns)
            this.dataColumns.add(t);
        updateIndices();
        this.mediaPaths = mediaPaths;
        this.annotationFactory = annotationFactory;
    }

    /**
     * Updates the indices of sensor, annotations and timestamp
     * @SuppressWarnings("incomplete-switch")
     */
    private void updateIndices() {
        int cntTs = 0;
        int cntAnnot = 0;
        int cntSens = 0;
        for (DataColumn dc : this) {
            switch (dc.getType()) {
            case SENSOR:
                dc.setSensorIdx(cntSens++);
                break;
            case FLOATING_POINT_ANNOTATION:
            case INTEGER_ANNOTATION:
            case LABEL_ANNOTATION:
            case NEW_FLOATING_POINT_ANNOTATION:
            case NEW_INTEGER_ANNOTATION:
            case NEW_LABEL_ANNOTATION:
            case NEW_TRAIN_ANNOTATION:
                dc.setAnnotationIdx(cntAnnot++);
                break;
            case TIMESTAMP:
                dc.setTimestampIdx(cntTs++);
                break;
            }
        }
    }

    /**
     * Constructor
     * @param location
     * @param mediaPaths
     * @param annotationFactory
     */
    public DataSource(URL location, List<String> mediaPaths, AnnotationFactory annotationFactory) {
        this(location, Collections.<T> emptyList(), mediaPaths, annotationFactory);
    }

    /**
     * Default constructor
     */
    public DataSource() {}

    /**
     * Getter of Location
     * @return URL-Location of file on disk
     */
    public URL getLocation() {
        return location;
    }

    /**
     * Setter of URL-Location
     * @param location - of file on disk
     */
    public void setLocation(URL location) {
        this.location = location;
    }

    /**
     * Adds a data column to the data source
     *
     * @param i - Index of data column
     * @param dataColumn - you want to add
     */
    public void addDataColumn(int i, T dataColumn) {
        dataColumns.add(i, dataColumn);
        updateIndices();
    }

    /**
     * Adds a data column to the end of the data source
     * @param dataColumn - new
     */
    public void addDataColumn(T dataColumn) {
        dataColumns.add(dataColumn);
        updateIndices();
    }

    /**
     * Getter of data column No @code i
     * @param i - index of datacolumn
     * @return data column of index i
     */
    public T getDataColumn(int i) {
        return dataColumns.get(i);
    }

    /**
     * Removes data column of index @code i
     * @param i - index of data column
     */
    public void removeDataColumn(int i) {
        dataColumns.remove(i);
        updateIndices();
    }

    /**
     * Calculates size of data set
     * @return Size of data set
     */
    public int getNumberOfColumns() {
        return dataColumns.size();
    }

    @Override
    public Iterator<T> iterator() {
        return dataColumns.iterator();
    }

    /**
     * Set a new dataset
     * @param dataColumn - new set of data
     */
    public void setIteratorDataColumn(List<T> dataColumn) {
        this.dataColumns = dataColumn;
        updateIndices();
    }

    /**
     * Searches @dc at data set and returns its index
     * @param dc - searched datacolumn
     * @return
     */
    public int indexOf(DataColumn dc) {
        return dataColumns.indexOf(dc);
    }

    /**
     * Getter of source type
     * @return type of file source
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets type of file source
     * @param type - of file source
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Gets the annotation factory
     * @return annotationfactory
     */
    public AnnotationFactory getAnnotationFactory() {
        return annotationFactory;
    }

    /**
     * Sets a new annotation factory
     * @param annotationFactory - new
     */
    public void setAnnotationFactory(AnnotationFactory annotationFactory) {
        this.annotationFactory = annotationFactory;
    }

    /**
     * Sets new media paths
     * @param mediaPaths - location of media files on disk
     */
    public void setMediaPaths(List<String> mediaPaths) {
        this.mediaPaths = mediaPaths;
    }

    /**
     * Getter mediapaths
     * @return mediaPaths - location of media files on disk
     */
    public List<String> getMediaPath() {
        return mediaPaths;
    }

    /**
     * Returns a iterable of all sensor columns
     * @return ret - iterable of all sensor columns
     */
    public Iterable<DataColumn> getSensorColumns() {
        List<DataColumn> ret = new ArrayList<>();
        for (DataColumn dc : this) {
            if (dc.getType().isSensor())
                ret.add(dc);
        }
        return ret;
    }

    /**
     * Returns a iterable of all annotation columns
     * @return ret - iterable of all annotation columns
     */
    public Iterable<DataColumn> getAnnotationColumns() {
        List<DataColumn> ret = new ArrayList<>();
        for (DataColumn dc : this) {
            if (dc.getType().isAnnotation())
                ret.add(dc);
        }
        return ret;
    }

    /**
     * Return the column selected as the training column.
     *
     * @return training column or {@code null} if there is none.
     */
    public DataColumn getTrainColumn() {
        for (DataColumn dc : this) {
            if (dc.getType().isTrainAnnotation()) return dc;
        }
        return null;
    }

    /**
     * Calculates the count of annotations
     * @return count of annotations
     */
    public int getNumberOfAnnotations() {
        int cnt = 0;
        for (DataColumn dc : this)
            if (dc.getType().isAnnotation())
                cnt++;
        return cnt;
    }

    /**
     * Calculates the count of old annotations
     * @return count of old annotations
     */
    public int getNumberOfOldAnnotations() {
        int cnt = 0;
        for (DataColumn dc : this)
            if (dc.getType().isOldAnnotation())
                cnt++;
        return cnt;
    }

    /**
     * Calculates the count of new annotations
     * @return count of new annotations
     */
    public int getNumberOfNewAnnotations() {
        int cnt = 0;
        for (DataColumn dc : this)
            if (dc.getType().isNewAnnotation())
                cnt++;
        return cnt;
    }

    /**
     * Counts all timestamps
     * @return count of timestamps
     */
    public int getNumberOfTimestamps() {
        int cnt = 0;
        for (DataColumn dc : this)
            if (dc.getType().isTimeStamp())
                cnt++;
        return cnt;
    }

    /**
     * Calculates the count of sensors
     * @return count of annotations
     */
    public int getNumberOfSensorColumns() {
        int cnt = 0;
        for (DataColumn dc : this)
            if (dc.getType().isSensor())
                cnt++;
        return cnt;
    }

    /**
     * Return the zero based idx of the last column to contain timestamps.
     *
     * @return idx of the last timestamp or a negative value if there are no
     *         timestamps.
     */
    public int idxOfLastTimestamp() {
        for (int i = dataColumns.size() - 1; i >= 0; i--) {
            if (dataColumns.get(i).getTimestampIdx() >= 0)
                return i;
        }
        return -1;
    }

    public int toSensorIndex(int column) {
        if (dataColumns.get(column).getType().isSensor()) {
            int cnt = 0;
            for (int i = 0; i < column; i++)
                if (dataColumns.get(i).getType().isSensor())
                    cnt++;
            return cnt;
        }
        return -1;
    }

    public int toAnnotationIndex(int column) {
        if (dataColumns.get(column).getType().isAnnotation()) {
            int cnt = 0;
            for (int i = 0; i < column; i++)
                if (dataColumns.get(i).getType().isAnnotation())
                    cnt++;
            return cnt;
        }
        return -1;
    }

    public int sensorIndexToColumn(int sensorIndex) {
        for (DataColumn dc : dataColumns) {
            if (dc.getSensorIdx() == sensorIndex) {
                return dataColumns.indexOf(dc);
            }
        }
        return -1;
    }

    public int annotationIndexToColumn(int annotationIndex) {
        for (DataColumn dc : dataColumns) {
            if (dc.getAnnotationIdx() == annotationIndex) {
                return dataColumns.indexOf(dc);
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder cols = new StringBuilder(1000);
        for (T dc : dataColumns) {
            cols.append(dc.toString() + ",\n");
        }
        cols.delete(cols.length() - 2, cols.length());
        return String.format(
                "[DS] Type: %s\n     URL: %s\n     Tracks:\n%s\n     Media: %s\n     AnnFac: %s",
                type, location, cols, mediaPaths, annotationFactory);
    }

    @Override
    public DataSource clone() {
        DataSource<DataColumn> theClone = null;
        try {

            theClone = (DataSource) super.clone();
            List<DataColumn> cloneData = new ArrayList<>();

            for (DataColumn dc : this) {
                cloneData.add(dc.clone());
            }
            theClone.setIteratorDataColumn(cloneData);

        } catch (CloneNotSupportedException e) {}
        return theClone;
    }

    public ArrayList<Attribute> getAttributes() {
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
        for (DataColumn dc : this) {
            if (dc.isSelected()) {
                Attribute attribute = convertTypeToAttribute(dc);
                attributeList.add(attribute);
            }
        }
        return attributeList;
    }

    private Attribute convertTypeToAttribute(DataColumn dc) {
        String name = dc.getName();
        if (dc.getType().isLabelAnnotation()) {
            return new Attribute(name, annotationFactory.getAllowedValues(dc.getAnnotationIdx()));
        } else if (dc.getType().isTimeStamp()){
            return new Attribute("timeStamp");
        } else {
            return new Attribute(name);
        }
    }
}
