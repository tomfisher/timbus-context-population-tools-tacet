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
