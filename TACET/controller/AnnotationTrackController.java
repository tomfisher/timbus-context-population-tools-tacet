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
