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
