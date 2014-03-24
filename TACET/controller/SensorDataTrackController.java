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

import squirrel.model.ModelFacade;
import squirrel.view.SensorDataTrackView;

/**
 * Controlls input on SensorDataTrackView
 */
public class SensorDataTrackController extends TrackController {
    public SensorDataTrackController(SensorDataTrackView view, ModelFacade modelFacade,
            TrackEventBus synchroniser) {
        super(view, modelFacade, synchroniser);
        view.chartPanel.addMouseListener(this);
        view.chartPanel.addMouseMotionListener(this);
        view.chartPanel.addKeyListener(this);
        view.chartPanel.addComponentListener(this);
    }

    @Override
    public void visibleExcerptChangedWidth(long newWidth) {
        super.visibleExcerptChangedWidth(newWidth);
        modelFacade.setFilterSize(3 * (int) newWidth);
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
