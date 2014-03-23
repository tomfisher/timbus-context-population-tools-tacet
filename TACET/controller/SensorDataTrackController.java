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
