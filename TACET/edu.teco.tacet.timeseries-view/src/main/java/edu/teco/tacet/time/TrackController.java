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

package edu.teco.tacet.time;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Widget;

import edu.teco.tacet.jfreechart.SensorDataTrackView;
import edu.teco.tacet.observer.IViewComponent;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.State;
import edu.teco.tacet.track.StateObservable;
import edu.teco.tacet.track.TrackManager;

/**
 * This class manages the input events that occur on {@link SensorDataTrackView}
 * and {@link AnnotationTrackView} instances. It also publishes said input
 * events and subscribes to a {@link TrackSynchroniser} to keep in sync with
 * other tracks.
 */
public class TrackController implements MouseListener, KeyListener, MouseMoveListener {
	public static double inverseGoldenRatio = 1 - (2 / (double) (1 + Math
			.sqrt(5)));

	private boolean mouseDown = false;
	private boolean shiftActive = false;
	private int xStart;
	
	private long lastMouseMove = 0;
	private long lastMouseDown = 0;

	private long minVisibleExcerpt = 10L;
	private int selectionStart = -1;
	protected IViewComponent view;
	private long resolution = 1;
	private double speed = 1.0;
	protected StateObservable sObservable;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param modelFacade
	 *            the model to query for certain time info.
	 * @param synchroniser
	 *            the eventbus that is used to keep in sync with other
	 *            {@link TrackController}.
	 */
	public TrackController(IViewComponent view) {
		super();
		this.view = view;
		sObservable = StateObservable.getInstance();
	}
	
	public void setState(State state){
		sObservable.setState(state);
	}
	
	public void addObserver(IViewComponent component){
		sObservable.addObserver(component);
	}
	

	/**
	 * This method is a hook. It is called when the pixelTimeRatio changed.
	 * 
	 * @param newPixelTimeRatio
	 *            the new pixelTimeRatio.
	 * @see squirrel.view.TrackView#setPixelTimeRatio(double)
	 */
	public void zoomStepChanged(double newPixelTimeRatio, long resolution) {
		this.resolution = resolution;
		double diffPTR = newPixelTimeRatio / sObservable.getState().getPixelTimeRatio();
		long currTime = sObservable.getState().getTime();
		Range visibleExcerpt = sObservable.getState().getVisibleRange();

		long newStart = currTime
				- (long) ((currTime - visibleExcerpt.getStart()) / diffPTR);

		long newEnd = currTime
				+ (long) ((visibleExcerpt.getEnd() - currTime) / diffPTR);
		Range newVE = new Range(newStart, newEnd);
		if(newVE.contains(TrackManager.getInstance().getGlobalRange())){
			if(newVE.equals(TrackManager.getInstance().getGlobalRange())){
				return;
			}
			newVE = TrackManager.getInstance().getGlobalRange();
		} else if(TrackManager.getInstance().getGlobalRange().getStart()<newStart){
			newVE = newVE.setStart(TrackManager.getInstance().getGlobalRange().getStart());
		} else if(TrackManager.getInstance().getGlobalRange().getEnd()>newEnd){
			newVE = newVE.setEnd(TrackManager.getInstance().getGlobalRange().getEnd());
		}
		// Do not make visible excerpt smaller than a certain threshold
		sObservable.getState().setVisibleRange( 
		(newVE.getDistance() >= this.minVisibleExcerpt) ? newVE : newVE
				.changeDistance(this.minVisibleExcerpt));
		if(speed != (double) sObservable.getState().getVisibleRange().getDistance() / 4000) {
    		speed = (double) sObservable.getState().getVisibleRange().getDistance() / 4000;
    		sObservable.setPlaybackSpeed(speed);
    		System.out.println(speed);
    	};
    	sObservable.getState().setPixelTimeRatio(newPixelTimeRatio);
    	sObservable.getState().setResolution(resolution);
    	sObservable.updateState();
	}

	/**
	 * This method is a hook. It is called when the user selected a certain part
	 * of this track.
	 * 
	 * @param selectedExcerpt
	 *            the selected excerpt in milliseconds.
	 */
	public void setSelectionRange(Range selectedExcerpt) {
		sObservable.getState().setSelectedRange(selectedExcerpt);
		sObservable.updateState();
	}

	/**
	 * This method is a hook. It is called when the current selection has been
	 * canceled.
	 */
	public void cancelSelection() {
		sObservable.getState().setRangeSelected(false);
		sObservable.getState().setAnnotationDialogOpen(false);
		sObservable.updateState();
	}

	private void checkAndChangeTime(long delta) {
		if(delta==0){
			return;
		}
		long currentTimestamp = sObservable.getState().getTime();
		long newTimestamp = currentTimestamp + delta;
		if (getGlobalRange().contains(newTimestamp)){
			sObservable.getState().setTime(newTimestamp);					
		} else if(getGlobalRange().getStart()>newTimestamp){
			sObservable.getState().setTime(getGlobalRange().getStart());
		} else if(getGlobalRange().getEnd()<newTimestamp){
			sObservable.getState().setTime(getGlobalRange().getEnd());
		}
		sObservable.updateState();
	}

	private void checkAndPublishSelection(int xEnd) {
		Range selectionRange = Range.fromUnsorted(
				view.translatePixelToTimestamp(selectionStart),
				view.translatePixelToTimestamp(xEnd));
		if (selectionRange.getStart() < getGlobalRange().getStart()) {
			selectionRange = selectionRange.setStart(getGlobalRange().getStart());
		} else if (selectionRange.getEnd() > getGlobalRange().getEnd()) {
			selectionRange = selectionRange.setEnd(getGlobalRange().getEnd());
		}
		sObservable.getState().setSelectedRange(selectionRange);
		sObservable.updateState();
	}
	
	@Override
	public void mouseMove(MouseEvent e) {
		lastMouseMove = e.time;
	    
		if (mouseDown && shiftActive) {
			// select range
			checkAndPublishSelection(e.x);
		} else if (mouseDown) {
			// drag
			long translatedDelta = translatePixelToTimestamp(xStart)
					- translatePixelToTimestamp(e.x);
			this.checkAndChangeTime(translatedDelta);
			xStart = e.x;
		}
	}
	
	public long translatePixelToTimestamp(int x) {
		return view.translatePixelToTimestamp(x);
	}

	@Override
	public void mouseDown(MouseEvent e) {		
		lastMouseDown = e.time;
	    
		if (e.count == 1) {
			xStart = e.x;
			mouseDown = true;
			if (shiftActive) {
				selectionStart = e.x;
			}
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
	    // if we did not move the mouse since the last move down event
	    if (mouseDown && lastMouseMove < lastMouseDown) {
	        // we set the current time to the time at point
	        long start = view.translatePixelToTimestamp(xStart);
            checkAndChangeTime(start-sObservable.getState().getTime());
	    }
		mouseDown = false;
		if (selectionStart >= 0 && shiftActive) {
			selectionStart = -1;
			openAnnotationDialog(e.widget);
			shiftActive = false;
//			sObservable.getState().setRangeSelected(false);
			if (sObservable.getState().isPlaying()){
				sObservable.setPlaying(false);
			}
//			sObservable.updateState();							
		}
	}
	
	protected void openAnnotationDialog(Widget sender) {
		// TODO: determine default track
		sObservable.getState().setAnnotationDialogOpen(true);
		sObservable.updateState();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.keyCode == SWT.SHIFT) {
			shiftActive = true;
		}

		char key = e.character;
		double newPTR = Double.NaN;
		switch (key) {
		case '-':
			newPTR = sObservable.getState().getPixelTimeRatio() / 2;
			if (newPTR > 1) {
				resolution++;
			}
			break;
		case '+':
		case '=':
			newPTR = sObservable.getState().getPixelTimeRatio() * 2;
			if (resolution > 1) {
				resolution--;
			}
			break;
		case '0':
		case '1':
			newPTR = 1.0;
			resolution = 1;
			break;
		default:
			return;
		}
		zoomStepChanged(newPTR, resolution);
		sObservable.getState().setPixelTimeRatio(newPTR);
		sObservable.getState().setResolution(resolution);
		sObservable.updateState();		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.keyCode == SWT.SHIFT) {
			shiftActive = false;
		}
	}

	public Range getGlobalRange() {
		return TrackManager.getInstance().getGlobalRange();
	}

	public int calcSelectedTrack(int coord) {
		return 0;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {}


	public void openAnnotationDialog(int trackNumber, Annotation annotation) {}

}
