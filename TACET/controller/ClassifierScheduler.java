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

import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import squirrel.model.ModelFacade;
import squirrel.util.Range;
import squirrel.view.TrackView;


/**
 * This class is not a TrackController as intended, but is rather
 * silly regarding controlling a {@link TrackView} instance.  It does
 * not alter it's refereced view instance in any direct matter, as
 * reacting to user input.<p>
 *
 * It rather only classifies the data in the currently visible excerpt
 * of its view instance every {@code getSchedulingInteval()} milliseconds.
 */
public class ClassifierScheduler extends TrackController {
    private long schedulingInteval = 500;

    private Range lastClassifiedRange = null;
    private long lastClassification = -1;

    public ClassifierScheduler(TrackView view, ModelFacade modelFacade,
            TrackEventBus synchroniser) {
        super(view, modelFacade, synchroniser);
    }

    public long getSchedulingInteval() {
        return schedulingInteval;
    }

    public void setSchedulingInteval(long schedulingInteval) {
        this.schedulingInteval = schedulingInteval;
    }

    @Override
    public void timeChanged(long timeStamp) {
//        if (lastClassification < 0 || timeStamp - lastClassification >= schedulingInteval) {
//            Range range = view.getVisibleExcerpt();
//            if (!range.equals(lastClassifiedRange)) {
//                modelFacade.classify(range);
//                lastClassification = timeStamp;
//                lastClassifiedRange = range;
//            }
//        }
    }

    @Override public void setPlay() {}
    @Override public void setPause() {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void componentResized(ComponentEvent e) {}
    @Override public void componentMoved(ComponentEvent e) {}
    @Override public void componentShown(ComponentEvent e) {}
    @Override public void componentHidden(ComponentEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseWheelMoved(MouseWheelEvent e) {}
}
