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
