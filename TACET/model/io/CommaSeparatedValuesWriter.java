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

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;

import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import squirrel.model.Annotation;

import squirrel.model.SensorDatum;
import squirrel.model.io.DataColumn.Unit;

/**
 * Writer for csvfile
 * @author marc
 *
 */
public class CommaSeparatedValuesWriter extends Writer<CSVDataColumn> {

    private CSVDataSource dataSource;
    private BufferedWriter bw = null;
    /**
     * Constructs
     * @param exportPath the path where the export file should be saved
     * @param dataSource contains descriptions for each column and the path of
     *            the source file
     */
    public CommaSeparatedValuesWriter(URL exportPath, CSVDataSource dataSource) {
        super(exportPath, dataSource);
        this.dataSource = dataSource;

        int counterUnits = 0;
        int posUnit = 0;
    }



    @Override
    public void writeExport(List<Annotation[]> data, SensorDatum[] sd) {


        // Annotation[][] grid = new Annotation[data.size()][];
        // for (int i = 0; i < grid.length; i++) {
        // grid[i] = data.get(i);
        // }

        String line = "";
        int countSensor = 0;
        int countAnno = 0;
        int[] counterPos = new int[data.size()];
        for (int i = 0; i < counterPos.length; i++) {
            counterPos[i] = 0;
        }

        String separator = dataSource.getElemSeparator();
        for (int i = 0; i < sd.length; i++) {

            for (int j = 0; j < dataSource.getNumberOfColumns(); j++) {
                // For the end of the line
                if (j == dataSource.getNumberOfColumns() - 1) {
                    separator = "";
                }
                switch (dataSource.getDataColumn(j).getType()) {
                case SENSOR:
                    line += sd[i].getValues()[countSensor] + separator;
                    countSensor++;
                    break;
                case TIMESTAMP:
                    line += getTimestamp(j, sd[i].getTimestamp()) + separator;
                    break;
                case IGNORE:
                    break;
                default:
                    // For Annotation
                    if (counterPos[countAnno] >= data.get(countAnno).length) {
                        break;
                    }
                    Annotation anno = data.get(countAnno)[counterPos[countAnno]];
                    if (anno.isInRange(sd[i].getTimestamp())) {
                        line += anno.toValue();
                    } else {
                        // Looks if the next Annotation is in Range
                        if (counterPos[countAnno] + 1 < data.get(countAnno).length) {
                            anno = data.get(countAnno)[counterPos[countAnno] + 1];
                            if (anno.isInRange(sd[i].getTimestamp())) {
                                line += anno.toValue();
                                counterPos[countAnno]++;
                            }
                        }
                    }
                    line += separator;
                    countAnno++;
                    break;
                }

            }
            try {

                bw.append(line);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            line = "";
            countAnno = 0;
            countSensor = 0;
            separator = dataSource.getElemSeparator();
        }



    }

    /**
     * Returns the milliseconds of the value
     * @param value a timestamp
     * @param separator between the timeunits
     * @return milliseconds of the timestamp
     */
    protected String getTimestamp(int pos, long timestamp) {
        if (dataSource.getDataColumn(pos).isDate()) {
            Date d = new Date(timestamp);
            return dataSource.getDataColumn(pos).getDateFormat().format(d);
        } else {
            return convert(dataSource.getDataColumn(pos).getCustomDateUnits(), timestamp,
                    dataSource.getDataColumn(pos).getCustomDateSeparator());
        }
    }

    private String convert(Unit[] units, long timestamp, String separator) {
        if(units.length>1){
            Arrays.sort(units);
        }

        String result = "";
        if (units.length == 1) {
            result += units[0].toOldTimeMax(timestamp);
        }
        for (int i = 0; i < units.length-1 ; i++) {
            if (i == 0) {
                result += units[i].toOldTimeMax(timestamp) + separator;
            } else {
                result += units[i].toOldTime(timestamp) + separator;
            }
        }
        if(units.length!=1){
            result += units[units.length - 1].toOldTime(timestamp);
        }

        return result;
    }

    @Override
    public void initialiseWriter() {
        try {
            bw = new BufferedWriter(new FileWriter(exportPath.getPath()));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    @Override
    public void finalizeWriter() {
        try {
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
