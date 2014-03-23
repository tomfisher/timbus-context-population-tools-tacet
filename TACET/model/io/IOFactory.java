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
