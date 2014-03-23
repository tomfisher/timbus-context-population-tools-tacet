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

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import squirrel.model.Annotation;
import squirrel.model.ModelFacade;
import squirrel.util.Range;
import squirrel.view.AnnotationTrackView;

public class AnnotationTrackController extends TrackController {

    /**
     * Constructor of AnnotationTrackController
     * @param view - its UI
     * @param model - reference to model
     * @param synchroniser
     */
    public AnnotationTrackController(AnnotationTrackView view, ModelFacade model,
            TrackEventBus synchroniser) {
        super(view, model, synchroniser);
        view.addMouseListener(this);
        view.addMouseMotionListener(this);
        view.addKeyListener(this);
        view.addComponentListener(this);
    }

    @Override
    protected void createAnnotationRequest() {
        super.createAnnotationRequest();
        super.createAnnotationView.setSelectedTrack(trackIndex);
    }

    @Override
    public void setPlay() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            final boolean wasPlaying = timeCoordinator.isPlaying();
            if (wasPlaying)
                timeCoordinator.pause();
            createEditAnnotationRequest(modelFacade.getAnnotation(
                    view.translatePixelToTimestamp(e.getX()), trackIndex));
            createEditAnnotationView.setPropertyChangedListener(new PropertyChangeListener() {
                // This method is called, when a new Annotation has been
                // created.
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals("deleteAnnotation")) {
                        modelFacade.deleteAnnotations((Range) evt.getNewValue(),
                                createEditAnnotationView.getSelectedTrack());
                    } else if (evt.getPropertyName().equals("editAnnotation")) {
                        modelFacade.deleteAnnotations(((Annotation) evt.getNewValue()).getRange(),
                                createEditAnnotationView.getSelectedTrack());
                        modelFacade.insertAnnotation((Annotation) evt.getNewValue(),
                                createEditAnnotationView.getSelectedTrack());
                    }

                    // this is only executed, when the Annotate button was
                    // pressed. So we will not resume Playback on Cancel.
                    view.validate();
                    if (wasPlaying)
                        timeCoordinator.play();
                }
            });
            createEditAnnotationView.presentAsDialog(null);
        }
    }
}
