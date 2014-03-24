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

import java.util.ArrayList;
import java.util.List;

import squirrel.model.TimeListener;
import squirrel.util.Range;

public class TrackSynchroniser implements TrackEventBus, TimeListener {

    private List<Entry> trackControllers = new ArrayList<>();
    private long startTime;

    /**
     * A little helper class, that associates a track with a group.
     */
    private class Entry {
        TrackController trackController;
        int group;

        Entry(int group, TrackController trackController) {
            this.trackController = trackController;
            this.group = group;
        }
    }

    /**
     * Constructs a new instance of this class using {@code startTime} to
     * convert the current time to a time that is applicable to the
     * {@code TrackController}s.
     *
     * @param startTime the time used to convert between current and track time
     */
    public TrackSynchroniser(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void subscribe(TrackController instance, int group) {
        trackControllers.add(new Entry(group, instance));
    }

    @Override
    public void publishVisibleExcerptMoved(TrackController publisher, int group, int delta) {
        for (Entry e : trackControllers) {
            if (e.group == group && publisher != e.trackController) {
                e.trackController.visibleExcerptMoved(delta);
            }
        }
    }

    @Override
    public void publishExcerptSelected(TrackController publisher, int group, Range selection) {
        for (Entry e : trackControllers) {
            if (e.group == group && publisher != e.trackController) {
                e.trackController.excerptSelected(selection);
            }
        }

    }

    @Override
    public void publishSelectionCanceled(TrackController publisher, int group) {
        for (Entry e : trackControllers) {
            if (e.group == group && publisher != e.trackController) {
                e.trackController.excerptSelectionCanceled();
            }
        }

    }

    @Override
    public void publishZoomStepChanged(TrackController publisher, int group,
            double newPixelTimeRatio) {
        for (Entry e : trackControllers) {
            if (e.group == group && publisher != e.trackController) {
                e.trackController.zoomStepChanged(newPixelTimeRatio);
            }
        }
    }

    @Override
    public void timeChanged(long timeStamp) {
        for (Entry e : trackControllers) {
            e.trackController.timeChanged(timeStamp + startTime);
        }
    }

    @Override
    public void setPlay() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPause() {
        // TODO Auto-generated method stub

    }
}
