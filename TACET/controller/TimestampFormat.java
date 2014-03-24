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

public class TimestampFormat {
    public final String customUnits, customSeparator, date;
    public final boolean isCustomDate;
    public final String regexMatch, regexReplace;

    public TimestampFormat(String customUnits, String customSeparator) {
        this(customUnits, customSeparator, "", true, "", "");
    }

    public TimestampFormat(String date) {
        this("", "", date, false, "", "");
    }

    private TimestampFormat(String customUnits, String customSeparator, String date,
            boolean isCustomDate, String regexMatch, String regexReplace) {
        super();
        this.customUnits = customUnits;
        this.customSeparator = customSeparator;
        this.date = date;
        this.isCustomDate = isCustomDate;
        this.regexMatch = regexMatch;
        this.regexReplace = regexReplace;
    }
}
