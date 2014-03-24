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
