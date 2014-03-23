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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.regex.Matcher;



public class CSVDataColumn extends DataColumn {

    public enum DataType {
        SHORT, INTEGER, LONG, FLOAT, DOUBLE, STRING, NONE;
    }

    public enum Encoding {
        UTF8, ASCII;
    }

    private DataType dataType;
    private DateFormat dateFormat;
    private Unit[] customDateUnits;
    private String customDateSeparator;
    private Matcher timeRegex;
    private String timeReplace;
    private boolean isDate;

    public CSVDataColumn(String name, Type type, Unit unit, DataType dataType,
        DateFormat dateFormat, Unit[] customDateUnits, String customDateSeparator,
            Matcher timeRegex, String timeReplace, boolean isDate) {
        super(name, type, unit);
        this.dataType = dataType;
        this.dateFormat = dateFormat;
        this.customDateUnits = customDateUnits;
        this.customDateSeparator = customDateSeparator;
        this.timeRegex = timeRegex;
        this.timeReplace = timeReplace;
        this.isDate = isDate;

    }

    public CSVDataColumn(String name, Type type, DataType dataType) {
        this(name, type, Unit.NONE, dataType, null, null, null, null, null, false);
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Matcher getTimeRegex() {
        return timeRegex;
    }

    public void setTimeRegex(Matcher timeRegex) {
        this.timeRegex = timeRegex;
    }

    public String getTimeReplace() {
        return timeReplace;
    }

    public void setTimeReplace(String timeReplace) {
        this.timeReplace = timeReplace;
    }

    public Unit[] getCustomDateUnits() {
        return customDateUnits;
    }

    public void setCustomDateUnits(Unit[] customDateUnits) {
        this.customDateUnits = customDateUnits;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getCustomDateSeparator() {
        return customDateSeparator;
    }

    public void setCustomDateSeparator(String customDateSeparator) {
        this.customDateSeparator = customDateSeparator;
    }

    public boolean isDate() {
        return isDate;
    }

    public void setDate(boolean isDate) {
        this.isDate = isDate;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
            " || DataType: %s | DateFormat: %s | cuUnits: %s | custSep: %s | isCust: %s]",
            dataType, dateFormat, Arrays.toString(customDateUnits), customDateSeparator, isDate);
    }
}
