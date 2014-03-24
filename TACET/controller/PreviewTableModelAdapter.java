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

package squirrel.controller;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import squirrel.model.Annotation;
import squirrel.model.FloatingpointAnnotation;
import squirrel.model.IntegerAnnotation;
import squirrel.model.LabelAnnotation;
import squirrel.model.SensorDatum;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.model.io.Reader;
import squirrel.util.Range;

public class PreviewTableModelAdapter extends AbstractTableModel implements TableModel {
    private Reader adapter;

    private List<SensorDatum> sensorBuffer = Collections.emptyList();
    private List<Annotation[]> annotationBuffer = Collections.emptyList();
    private DataSource<? extends DataColumn> dataSource;

    private Range previewRange;

    public PreviewTableModelAdapter(Range previewRange, Reader adapter,
            DataSource<? extends DataColumn> dataSource) {
        super();
        this.previewRange = previewRange;
        this.adapter = adapter;
        this.dataSource = dataSource;
    }

    @Override
    public int getRowCount() {
        return sensorBuffer.size();
    }

    @Override
    public int getColumnCount() {
        return dataSource.getNumberOfColumns() - dataSource.getNumberOfNewAnnotations();
    }

    @Override
    public String getColumnName(int column) {
        return dataSource.getDataColumn(column).getName();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            return sensorBuffer.get(rowIndex).getTimestamp();
        int sensorIdx = dataSource.toSensorIndex(columnIndex);
        if (sensorIdx >= 0)
            return sensorBuffer.get(rowIndex).getValues()[sensorIdx];
        if (dataSource.getDataColumn(columnIndex).getType().isAnnotation())
            return annotationToString(
                annotationBuffer.get(rowIndex)[dataSource.toAnnotationIndex(columnIndex)]);

        return "No Data";
    }

    private String annotationToString(Annotation annotation) {
        if (annotation instanceof IntegerAnnotation) {
            return String.valueOf(((IntegerAnnotation) annotation).getValue());
        } else if (annotation instanceof FloatingpointAnnotation) {
            return String.valueOf(((FloatingpointAnnotation) annotation).getValue());
        } else if (annotation instanceof LabelAnnotation) {
            return ((LabelAnnotation) annotation).getLabel();
        }
        return "-";
    }

    public void readData() {
        sensorBuffer = adapter.readSensorData(previewRange);
        annotationBuffer = adapter.readAnnotations(previewRange);
    }

    public Range getPreviewRange() {
        return previewRange;
    }

    public void setPreviewRange(Range previewRange) {
        this.previewRange = previewRange;
        readData();
    }

    public Reader getadapter() {
        return adapter;
    }

    public void setadapter(Reader adapter) {
        this.adapter = adapter;
        readData();
    }
}
