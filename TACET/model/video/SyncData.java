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

import java.util.LinkedList;

/**
 * The Class SyncData. Calculates the linear regression of each video track to
 * the sensor data track
 */
public class SyncData {
    private LinkedList<SyncPoint> syncPoints;
    private int numberOfTracks;
    private int numberOfPoints;
    private float[] videoSpeed;
    private long[] startTime;
    private boolean setSynchronisation = false;

    /**
     * Instantiates a new syncData.
     *
     * @param nOT the number of tracks
     */
    public SyncData(int nOT) {
        this.startTime = new long[nOT - 1];
        this.videoSpeed = new float[nOT - 1];
        for (int i = 0; i < nOT - 1; i++) {
            this.startTime[i] = 0;
            this.videoSpeed[i] = 1;
        }
        this.syncPoints = new LinkedList<SyncPoint>();
        this.numberOfTracks = nOT;
        this.numberOfPoints = 0;
    }

    /**
     * Gets the number of SyncPoints added to SyncData.
     *
     * @return the number of SyncPoints
     */
    public int geNumberOfPonts() {
        return numberOfPoints;
    }

    /**
     * Gets the start times of the synchronized video tracks.
     *
     * @return the start times
     */
    public long[] getStartTimes() {
        return startTime;
    }

    /**
     * Gets the video speed of the synchronized video tracks.
     *
     * @return the video speed
     */
    public float[] getVideoSpeed() {
        return videoSpeed;
    }

    /**
     * Sets the indicator of ending the synchronization true.
     */
    public void setSynchronisation() {
        this.setSynchronisation = true;
    }

    /**
     * Sync is set.
     *
     * @return true, if the synchronisation has ended
     */
    public boolean syncIsSet() {
        return setSynchronisation;
    }

    /**
     * Adds the sync point.
     *
     * @param sp the syncPoint
     */
    public void addSyncPoint(SyncPoint sp) {
        if (sp.getSyncTimeStamps().length == numberOfTracks) {
            syncPoints.add(sp);
            numberOfPoints++;
        }
    }

    /**
     * Removes the sync points with the selected indexes.
     *
     * @param selIdxs the selected indexes
     */
    public void removeSynPoints(int[] selIdxs) {
        for (int i = selIdxs.length - 1; i >= 0; i--) {
            syncPoints.remove(selIdxs[i]);
            numberOfPoints--;
        }
    }

    /**
     * Synchronizes the tracks.
     */
    public void synchronize() {
        if (numberOfPoints == 0) {
            startTime = new long[numberOfTracks - 1];
            videoSpeed = new float[numberOfTracks - 1];
            for (int i = 0; i < numberOfTracks - 1; i++) {
                startTime[i] = 0;
                videoSpeed[i] = 1;
            }
        } else if (numberOfPoints == 1) {
            startTime = new long[numberOfTracks - 1];
            videoSpeed = new float[numberOfTracks - 1];
            SyncPoint sp = syncPoints.getFirst();
            for (int i = 0; i < numberOfTracks - 1; i++) {
                startTime[i] = sp.getValue(0) - sp.getValue(i + 1);
                videoSpeed[i] = 1;
            }
        } else {
            double[] avg = calculateAvg();
            double[] deviation = calculateDeviation(avg);
            double[] correlation = calculateCorrelation(avg, deviation);

            calculateVideoSpeed(correlation, deviation);
            calculateStartTime(avg);
        }
    }

    private double[] calculateAvg() {
        double[] avg = new double[numberOfTracks];
        double sum = 0;
        for (int i = 0; i < numberOfTracks; i++) {
            int counter = 0;
            for (SyncPoint sp : syncPoints) {
                if (sp.getValue(i) >= 0) {
                    sum = sum + sp.getValue(i);
                    counter++;
                }
            }
            if (counter > 0) {
                avg[i] = sum / counter;
            } else {
                avg[i] = 1;
            }
            sum = 0;
        }
        return avg;
    }

    private double[] calculateDeviation(double[] avg) {
        double[] deviation = new double[numberOfTracks];
        double sum = 0;
        for (int i = 0; i < numberOfTracks; i++) {
            int counter = numberOfPoints;
            for (SyncPoint sp : syncPoints) {
                if (sp.getValue(i) >= 0) {
                    sum = sum + ((sp.getValue(i) - avg[i]) * (sp.getValue(i) - avg[i]));
                } else {
                    counter--;
                }
            }
            if (counter > 1) {
                deviation[i] = Math.sqrt(sum / (counter - 1));
            } else {
                deviation[i] = 1;
            }
            sum = 0;
        }
        return deviation;
    }

    private double[] calculateCorrelation(double[] avg, double[] deviation) {
        double[] correlation = new double[numberOfTracks - 1];
        double sum = 0;
        for (int i = 1; i < numberOfTracks; i++) {
            int counter = numberOfPoints;
            for (SyncPoint sp : syncPoints) {
                if (sp.getValue(i) >= 0) {
                    sum = sum + ((sp.getValue(i) - avg[i]) * (sp.getValue(0) - avg[0]));
                } else {
                    counter--;
                }
            }
            if (counter > 1) {
                correlation[i - 1] = (sum / (counter - 1)) / (deviation[i] * deviation[0]);
            } else {
                correlation[i - 1] = 1;
            }
            sum = 0;
        }
        return correlation;
    }

    private void calculateVideoSpeed(double[] correlation, double[] deviation) {
        videoSpeed = new float[numberOfTracks - 1];
        for (int i = 0; i < numberOfTracks - 1; i++) {
            videoSpeed[i] = (float) (correlation[i] * deviation[i + 1] / deviation[0]);
        }
    }

    private void calculateStartTime(double[] avg) {
        startTime = new long[numberOfTracks - 1];
        for (int i = 0; i < numberOfTracks - 1; i++) {
            startTime[i] = Math.round(avg[0] - (avg[i + 1] / videoSpeed[i]));
        }
    }
}
