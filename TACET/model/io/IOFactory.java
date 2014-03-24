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

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import squirrel.util.Range;

public class IOFactory {

    /**
     * Return a csvReader
     * @param location the location of the csvfile
     * @param valueSeparator the separator between the values
     * @param lineSeparator the symbol for the end of the line
     * @param descriptions the descriptions of the columns
     * @param timeFormatter the format of the date
     * @param replace the sperator between timeformatter and timestamp
     * @param isDatum if it can be parsed with simpledateformat
     * @return csvReader
     */
    public static Reader createCSVReader(CSVDataSource dataSource) {
        return new CommaSeparatedValuesReader(dataSource);
    }



    /**
     * Return a csvWriter
     * @param exportPath where to export the csvfile
     * @param location the location of the csvfile
     * @param valueSeparator the separator between the values
     * @param lineSeparator the symbol for the end of the line
     * @param descriptions the descriptions of the columns
     * @param timeFormatter the format of the date
     * @param replace the sperator between timeformatter and timestamp
     * @param isDatum if it can be parsed with simpledateformat
     * @return csvWriter
     */
    public static Writer<CSVDataColumn> createCSVWriter(URL exportPath, CSVDataSource dataSource) {
        return new CommaSeparatedValuesWriter(exportPath, dataSource);
    }

}
