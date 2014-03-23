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

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.MouseInputListener;

import squirrel.model.Annotation;
import squirrel.model.ModelFacade;
import squirrel.model.TimeCoordinator;
import squirrel.model.TimeListener;
import squirrel.util.Range;
import squirrel.view.AnnotationTrackView;
import squirrel.view.CreateAnnotationView;
import squirrel.view.SensorDataTrackView;
import squirrel.view.TrackView;

/**
 * This class manages the input events that occur on {@link SensorDataTrackView}
 * and {@link AnnotationTrackView} instances. It also publishes said input
 * events and subscribes to a {@link TrackEventBus} to keep in sync with other
 * tracks.
 */
public abstract class TrackController implements MouseInputListener, MouseWheelListener,
        ComponentListener, KeyListener, TimeListener {
    private static double inverseGoldenRatio = 1 - (2 / (double) (1 + Math.sqrt(5)));

    private int dragDirection = -1;

    private int lastDragX;
    protected TrackEventBus synchroniser;
    protected TimeCoordinator timeCoordinator;
    protected ModelFacade modelFacade;
    protected int trackIndex;
    protected CreateAnnotationView createAnnotationView;
    protected CreateAnnotationView createEditAnnotationView;
    protected TrackView view;
    protected double pixelTimeRatio = 1.0d;
    private long minVisibleExcerpt = 10L;

    private long selectionStartTs;
    private boolean selectionInProgress = false;

    /**
     * Creates a new instance of this class.
     *
     * @param modelFacade the model to query for certain time info.
     * @param synchroniser the eventbus that is used to keep in sync with other
     *            {@link TrackController}.
     */
    public TrackController(TrackView view, ModelFacade modelFacade, TrackEventBus synchroniser) {
        super();
        this.view = view;
        this.modelFacade = modelFacade;
        this.trackIndex = view.getIndex();
        this.synchroniser = synchroniser;
        this.synchroniser.subscribe(this, 0);
        this.timeCoordinator = modelFacade.getTimeCoordinator();
    }

    /**
     * Sets the eventbus that is used to keep in sync with other
     * {@link TrackController} instances.
     *
     * @param eventBus
     */
    public void setEventBus(TrackEventBus eventBus) {
        this.synchroniser = eventBus;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.lastDragX = e.getX(); // start a potential drag
        view.cancelSelection();
        if (e.isShiftDown()) {
            this.selectionStartTs = view.translatePixelToTimestamp(lastDragX);
            this.selectionInProgress = true;
            excerptSelected(Range.point(selectionStartTs));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // drag is in "motion"
        int dragDeltaX = (e.getX() - lastDragX) * dragDirection;
        if (e.isShiftDown()) { // we are selecting an excerpt
            Range selection = Range.fromUnsorted(
                    this.selectionStartTs, view.translatePixelToTimestamp(e.getX()));
            excerptSelected(selection);
            synchroniser.publishExcerptSelected(this, 0, selection);
        } else { // we are changing the current time
            if (dragDeltaX != 0)
                timeCoordinator.changeTime(dragDirection *
                        (view.translatePixelToTimestamp(e.getX())
                        - view.translatePixelToTimestamp(lastDragX)));
        }
        lastDragX = e.getX();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selectionInProgress = false;
        if (e.isShiftDown()) {
            final boolean wasPlaying = timeCoordinator.isPlaying();
            if (wasPlaying)
                timeCoordinator.pause();
            createAnnotationRequest();
            createAnnotationView.setPropertyChangedListener(new PropertyChangeListener() {
                // This method is called, when a new Annotation has been
                // created.
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    modelFacade.insertAnnotation((Annotation) evt.getNewValue(),
                            createAnnotationView.getSelectedTrack());
                    excerptSelectionCanceled();
                    // this is only executed, when the Annotate button was
                    // pressed. So we will not resume Playback on Cancel.
                    if (wasPlaying)
                        timeCoordinator.play();
                }
            });
            createAnnotationView.presentAsDialog(null);
        } else {
            lastDragX = -1; // cancel a potential drag
            synchroniser.publishSelectionCanceled(this, 0);
            excerptSelectionCanceled();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int numberOfTicks = e.getWheelRotation();
        double ptr = view.getPixelTimeRatio();
        if (numberOfTicks > 0) {
            zoomStepChanged(ptr * 1.2 * numberOfTicks);
        } else if (numberOfTicks < 0) {
            zoomStepChanged(ptr / (1.2 * Math.abs(numberOfTicks)));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        double newPTR = Double.NaN;
        switch (key) {
        case '-':
            newPTR = view.getPixelTimeRatio() / 1.2;
            break;
        case '+':
        case '=':
            newPTR = view.getPixelTimeRatio() * 1.2;
            break;
        case '0':
            newPTR = 1.0;
            break;
        default:
            return;
        }
        zoomStepChanged(newPTR);
        synchroniser.publishZoomStepChanged(this, 0, newPTR);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Component comp = e.getComponent();
        int width = 0;
        if (comp != null && (width = comp.getWidth()) > 0) {
            long newVEdist = view.translatePixelToTimestampDistance(width);
            visibleExcerptChangedWidth(newVEdist);
        }
    }

    /**
     * This method is a hook. It is called when the pixelTimeRatio changed.
     *
     * @param newPixelTimeRatio the new pixelTimeRatio.
     * @see squirrel.view.TrackView#setPixelTimeRatio(double)
     */
    protected void zoomStepChanged(double newPixelTimeRatio) {
        double diffPTR = newPixelTimeRatio / view.getPixelTimeRatio();
        long currTime = timeCoordinator.getTime() + modelFacade.getStartTimeStamp();
        Range visibleExcerpt = view.getVisibleExcerpt();

        long newStart = currTime - (long) ((currTime - visibleExcerpt.getStart()) / diffPTR);

        long newEnd = currTime + (long) ((visibleExcerpt.getEnd() - currTime) / diffPTR);

        if (newStart < modelFacade.getStartTimeStamp()) {
            newEnd += modelFacade.getStartTimeStamp() - newStart;
            newStart = modelFacade.getStartTimeStamp();
        }
        if (newEnd > modelFacade.getEndTimeStamp())
            newEnd = modelFacade.getEndTimeStamp();

        Range newVE = new Range(newStart, newEnd);
        view.setVisibleExcerpt( // Do not make vE smaller than a certain
                                // threshold
        (newVE.getDistance() >= this.minVisibleExcerpt) ?
                newVE : newVE.changeDistance(this.minVisibleExcerpt));
        view.setPixelTimeRatio(newPixelTimeRatio);
    }

    /**
     * This method is a hook. It is called when the user has selected a range
     * and wants to create a new annotation over said range.
     */
    protected void createAnnotationRequest() {
        if (this.createAnnotationView == null) {
            this.createAnnotationView = new CreateAnnotationView(this.modelFacade);
        }
        this.createAnnotationView.setSelectedRange(view.getSelection());
    }

    protected void createEditAnnotationRequest(Annotation annotation) {
        if (this.createEditAnnotationView == null) {
            this.createEditAnnotationView = new CreateAnnotationView(this.modelFacade, annotation);
        } else {
            createEditAnnotationView.setAnnotation(annotation);
        }
        this.createEditAnnotationView.setSelectedTrack(trackIndex);
    }

    /**
     * This method is a hook. It is called when the track changed it's width and
     * the visibleExcerpt has to be changed accordingly.
     *
     * @param newWidth track's the new width in milliseconds.
     */
    protected void visibleExcerptChangedWidth(long newWidth) {
        view.setVisibleExcerpt(view.getVisibleExcerpt().changeDistance(newWidth));
    }

    /**
     * This method is a hook. It is called when the user changed the visible
     * portion of the track by {@code delta} milliseconds.
     *
     * @param delta the amount of milliseconds the excerpt was shifted. This can
     *            be negative, indicating shifting to the left.
     */
    protected void visibleExcerptMoved(long delta) {
        updateSelection(delta);
        view.setVisibleExcerpt(view.getVisibleExcerpt().move(delta));
    }

    /**
     * This method is a hook. It is called when the user selected a certain part
     * of this track.
     *
     * @param selectedExcerpt the selected excerpt in milliseconds.
     */
    protected void excerptSelected(Range selectedExcerpt) {
        view.setSelection(selectedExcerpt);
    }

    /**
     * This method is a hook. It is called when the current selection has been
     * canceled.
     */
    protected void excerptSelectionCanceled() {
        view.cancelSelection();
    }

    @Override
    public void timeChanged(long timeStamp) {
        Range currentRange = view.getVisibleExcerpt();
        long newStartTime = timeStamp - (long) (currentRange.getDistance() * inverseGoldenRatio);
        if (newStartTime < modelFacade.getStartTimeStamp())
            newStartTime = modelFacade.getStartTimeStamp();
        timeChanged(newStartTime, timeStamp);
    }

    /**
     * This method is a hook. It is called when the time has changed.
     *
     * @param firstTimestampToDisplay the first visible timestamp.
     * @param currentTime the current time.
     */
    protected void timeChanged(long firstTimestampToDisplay, long currentTime) {
        Range oldVE = view.getVisibleExcerpt();
        Range newVE = new Range(firstTimestampToDisplay, view.getVisibleExcerpt()
                .getDistance() + firstTimestampToDisplay);
        view.setVisibleExcerpt(newVE);
        updateSelection(newVE.getEnd() - oldVE.getEnd());
        view.setCurrentTime(currentTime);
    }

    /**
     * Updates the selection when a selection is currently in progress by
     * expanding or shrinking the current selection depending on {@code delta}.
     * This method is intended to be used when the views' {@code visibleExcerpt}
     * changed. If there no selection is in progress, this method does nothing.
     *
     * @param delta the amount by which the {@code visibleExcerpt} has been
     *            shifted. May be negative to indicate a shift to the left.
     */
    private void updateSelection(long delta) {
        if (this.selectionInProgress) {
            Range currSel = view.getSelection();
            if (currSel.getStart() == selectionStartTs) {
                view.setSelection(Range.fromUnsorted(
                        selectionStartTs, view.getSelection().getEnd() + delta));
            } else if (currSel.getEnd() == selectionStartTs) {
                view.setSelection(Range.fromUnsorted(
                        selectionStartTs, view.getSelection().getStart() + delta));
            }
        }
    }

    private Range translateRangeToTimestamp(Range range) {
        return new Range(view.translatePixelToTimestamp((int) range.getStart()),
                view.translatePixelToTimestamp((int) range.getEnd()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
