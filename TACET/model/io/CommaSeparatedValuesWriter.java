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
