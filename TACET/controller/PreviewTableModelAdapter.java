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
