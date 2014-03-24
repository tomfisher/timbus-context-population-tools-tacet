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

package squirrel.model.video;

/**
 * The Class SyncPoint.
 */
public class SyncPoint {
    private long[] syncTimeStamp;

    /**
     * Instantiates a new sync point.
     *
     * @param syncTimeStamp time values of the tracks
     */
    public SyncPoint(long[] syncTimeStamp) {
        this.syncTimeStamp = syncTimeStamp;
    }

    /**
     * Gets the time values of all tracks.
     *
     * @return the time values
     */
    public long[] getSyncTimeStamps() {
        return syncTimeStamp;
    }

    /**
     * Gets the time value of the track.
     *
     * @param track the track
     * @return the value
     */
    public long getValue(int track) {
        return syncTimeStamp[track];
    }
}
