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

package squirrel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeCoordinator {
    private long actualTime = 0;
    private long maxTime;
    private boolean isPlaying = false;
    private final Timer executorService = new Timer();
    private long minimalDeltaTime;
    private double playbackSpeed = 1.0;
    private Scheduler scheduler;

    private List<TimeListener> listeners = new ArrayList<>();

    public TimeCoordinator() {
        this(200, Long.MAX_VALUE);
    }

    public TimeCoordinator(long minimalDeltaTime, long maxTime) {
        this.minimalDeltaTime = minimalDeltaTime;
        this.maxTime = maxTime;
        this.scheduler = new Scheduler();
        executorService.scheduleAtFixedRate(scheduler, 0L, 40);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public synchronized void play() {
        isPlaying = true;
        setViewsPause();
    }

    public synchronized void pause() {
        isPlaying = false;
        setViewsPlay();
    }

    public synchronized void stop() {
        isPlaying = false;
        actualTime = 0;
        setViewsPlay();
        fireTimeChanged();
    }

    public void addListener(TimeListener tl) {
        listeners.add(tl);
    }

    public long getMinimalDeltaTime() {
        return minimalDeltaTime;
    }

    public synchronized void setTime(long timeStamp) {
        if (timeStamp < 0) {
            actualTime = 0;
        } else if (timeStamp > maxTime) {
            actualTime = maxTime;
            pause();
        } else {
            actualTime = timeStamp;
        }
        fireTimeChanged();
    }

    public long getTime() {
        return actualTime;
    }

    public double getPlaybackSpeed() {
        return playbackSpeed;
    }

    public void setPlaybackSpeed(double playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    public synchronized void changeTime(long delta) {
        this.setTime(actualTime += delta);
    }

    public void nextStep() {
        changeTime(minimalDeltaTime);
    }

    public void prevStep() {
        changeTime(-minimalDeltaTime);
    }

    private void setViewsPlay() {
        for (TimeListener tl : listeners) {
            tl.setPlay();
        }
    }

    private void setViewsPause() {
        for (TimeListener tl : listeners) {
            tl.setPause();
        }
    }

    private void fireTimeChanged() {
        for (TimeListener tl : listeners) {
            tl.timeChanged(actualTime);
        }
    }

    private final class Scheduler extends TimerTask {
        @Override
        public void run() {
            if (isPlaying) {
                setTime((long) (actualTime + 40 * playbackSpeed));
            }
        }
    }
}
