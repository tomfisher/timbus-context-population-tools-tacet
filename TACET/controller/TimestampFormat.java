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
