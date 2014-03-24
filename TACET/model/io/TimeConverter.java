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
