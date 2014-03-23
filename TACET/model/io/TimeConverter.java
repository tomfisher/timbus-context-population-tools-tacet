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

import squirrel.model.io.DataColumn.Unit;

/**
 * TimeConverter to convert the timestamp to milliseconds
 * @author marc
 *
 */
public class TimeConverter {

    private Unit[] timeUnit;

    /**
     * Constructs the TimeConverter
     * @param timeUnit the Units of the Time
     */
    public TimeConverter(Unit[] timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * Return the milliseconds from the time array
     * @param time timestamp split in its units
     * @return the milliseconds of the timestamp
     */
    public long getMilliseconds(Long[] time) {

        long milliseconds = 0;
        if (time.length > timeUnit.length) {
            throw new IllegalArgumentException(
                    "TimeUnits not the same amount of units as timestamp");
        }

        for (int i = 0; i < time.length; i++) {

            if (timeUnit[i] != null) {
                milliseconds += timeUnit[i].toMillis(time[i]);
            }

        }

        return milliseconds;
    }

    /**
     * Return the milliseconds from the time array
     * @param timeS timestamp split in its units
     * @return the milliseconds of the timestamp
     */
    public long getMilliseconds(String[] timeS, int lastLength) {

        Long[] timeL = new Long[timeS.length];
        for (int i = 0; i < timeS.length; i++) {

            if(i == timeS.length-1 && i!=0){
                if(timeUnit[timeUnit.length-1]==Unit.MILLISECONDS){
                    timeS[timeS.length-1] = convertToRightLength(timeS[timeS.length-1], 3);
                } else if(timeUnit[timeUnit.length-1]==Unit.HUNDRETH_SECONDS){
                    timeS[timeS.length-1] = convertToRightLength(timeS[timeS.length-1], 2);
                }
            }


            timeL[i] = Long.parseLong(timeS[i]);
        }
        return getMilliseconds(timeL);
    }

    private String convertToRightLength(String time, int zeros){
        if(zeros == 2){
            if(time.length()==1){
                time += "0";
            }
        } else if(zeros == 3){
            if(time.length()==1){
                time += "00";
            } else if(time.length()==2) {
                time += "0";
            }
        }
        return time;
    }

}
