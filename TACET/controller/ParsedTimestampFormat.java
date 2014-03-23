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




import java.text.DateFormat;

import squirrel.model.io.DataColumn.Unit;

public class ParsedTimestampFormat {

    final String customSeparator;
    final Unit[] customUnits;
    final DateFormat date;
    final boolean isCustomDate;
    final String regexMatch, regexReplace;

    /**
     * Constructor for standard date
     * @param date
     */
    public ParsedTimestampFormat(DateFormat date) {
        this("", null, date, false, "", "");
    }

    /**
     * Constructor for custom time format
     * @param customSeparator - seperator in csv fiel
     * @param customUnits - Unit (e.g. minutes, seconds...)
     */
    public ParsedTimestampFormat(String customSeparator, Unit[] customUnits) {
        this(customSeparator, customUnits, null, true, "", "");
    }

    private ParsedTimestampFormat(String customSeparator, Unit[] customUnits, DateFormat date,
            boolean isCustomDate, String regexMatch, String regexReplace) {
        super();
        this.customSeparator = customSeparator;
        this.customUnits = customUnits;
        this.date = date;
        this.isCustomDate = isCustomDate;
        this.regexMatch = regexMatch;
        this.regexReplace = regexReplace;
    }
}
