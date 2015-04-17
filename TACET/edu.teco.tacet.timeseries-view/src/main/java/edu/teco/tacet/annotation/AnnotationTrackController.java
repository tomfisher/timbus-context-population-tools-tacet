package edu.teco.tacet.annotation;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Widget;

import edu.teco.tacet.observer.IAnnotationViewComponent;
import edu.teco.tacet.time.TrackController;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Range;

public class AnnotationTrackController extends TrackController {
	
	private AnnotationModel annoModel;
	private IAnnotationViewComponent annoView; 
	
	public AnnotationTrackController(IAnnotationViewComponent annoView, AnnotationModel annoModel, AnnotationPart composite) {
		super(annoView);
		this.annoModel = annoModel;
		this.annoView = annoView;
	}
	
	private void openAnnotationDialog() {
		sObservable.getState().setAnnotationDialogOpen(true);
		sObservable.updateState();
	}
	
	// Open by shift-click
	@Override
	protected void openAnnotationDialog(Widget sender) {
		TrackView tv = (TrackView) sender;
		annoView.getAnnotationDialog().setOldTrackNumber(tv.trackNumber);
		openAnnotationDialog();
//		sObservable.updateState();
	}

	// TODO make sure overlap is avoided
	public boolean insertAnnotation(Annotation anno, int track) {
		System.out.println(anno + " " + track);
		
		if(rangeIsOccupied(anno.getRange(), track)) {
			return false;
		}
		
		boolean added = annoModel.insertAnnotation(anno, track);
		if (added) {
			sObservable.getState().setRangeSelected(false); // evtl mit synchroniser
			sObservable.getState().setAnnotationDialogOpen(false);
			sObservable.updateState();
		}
		return added;
	}

	public void deleteAnnotations(Range range, int trackNo) {
		annoModel.deleteAnnotations(range, trackNo);
	}

	// TODO: check for collision on target track
	public boolean editAnnotation(Annotation newAnno, Annotation oldAnnotation, int newTrackNo, int oldTrackNo) {
		if (!hasEditOverlap(newAnno, oldAnnotation, newTrackNo)) {
			annoModel.deleteAnnotations(oldAnnotation.getRange(), oldTrackNo);
			annoModel.insertAnnotation(newAnno, newTrackNo);
			sObservable.getState().setRangeSelected(false);
//			sObservable.updateState();
			return true;
		}
		return false;
	}
	
	private boolean hasEditOverlap(Annotation newAnno, Annotation oldAnno, int newTrackNo) {
		for (Annotation a : annoModel.getAnnotations(newAnno.getRange(), newTrackNo)) {
			if (!a.equals(oldAnno) && newAnno.getRange().overlapsWith(a.getRange())) {
				System.out.println("collision: "+newAnno.getRange()+" with "+a.getRange());
				return true;
			}
		}
		return false;
	}
	
	private boolean rangeIsOccupied(Range range, int trackNumber) {
		for (Annotation a : annoModel.getAnnotations(range, trackNumber)) {
			if (range.overlapsWith(a.getRange())) {
				return true;
			}
		}
		return false;
	}

	public String[] getTrackNames() {
		String[] result = new String[annoModel.getNoTracks()];
		for (int i = 0; i < annoModel.getNoTracks(); i++) {
			result[i] = annoModel.getTrackName(i);
		}
		return result;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
	}

	public String[] getNameSuggestions(int trackNo) {
		return annoModel.getNameSuggestions(trackNo);
	}

	// MARK auto-fitting
	
	// TODO check for subrange
	public Range autoFitAnnotation(Range range, int trackNumber) {
		Range newRange = range;
		// selected start overlaps
		newRange = newRange.setStart(autoFitStart(newRange, trackNumber));
		// selected end overlaps
		newRange = newRange.setEnd(autoFitEnd(newRange, trackNumber));
		return newRange;
	}

	private long autoFitStart(Range range, int trackNumber) {
		long result = range.getStart();
		Annotation a = annoModel.getAnnotation(range.getStart(), trackNumber);
		System.out.println(range + " has conflict with " + a);
		if (a != null && a.getEnd() < range.getEnd())
			result = a.getEnd();
		return result;
	}

	private long autoFitEnd(Range range, int trackNumber) {
		long result = range.getEnd();
		Annotation a = annoModel.getAnnotation(range.getEnd(), trackNumber);
		System.out.println(range + " has conflict with " + a);
		if (a != null && range.getStart() < a.getStart())
			result = a.getStart();
		return result;
	}

	// TODO check for overlap
	public Range autoFillLeft(Range range, int trackNo) {
		Range newRange = new Range(sObservable.getState().getVisibleRange().getStart(), range.getEnd());
		Range checkRange = sObservable.getState().getVisibleRange();
		Iterable<? extends Annotation> annos = annoModel.getAnnotations(checkRange, trackNo);
		Annotation anno = null;
		for (Annotation a : annos)
			if (a.getEnd() < newRange.getEnd())
				anno = a; // get last element where new start is < oldEnd
		if (anno != null)
			newRange = newRange.setStart(anno.getEnd());
		return newRange;
	}

	public Range autoFillRight(Range range, int trackNo) {
		Range newRange = new Range(range.getStart(), sObservable.getState().getVisibleRange().getEnd());
		Range checkRange = sObservable.getState().getVisibleRange();
		Iterable<? extends Annotation> annos = annoModel.getAnnotations(checkRange, trackNo);
		Annotation anno = null;
		for (Annotation a : annos) {
			if (anno == null && a.getStart() > range.getEnd())
				anno = a;
		}
		if (anno != null)
			newRange = newRange.setEnd(anno.getStart());
		return newRange;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		if (e.keyCode == SWT.BS || e.keyCode == SWT.DEL) {
			HashSet<AnnotationView> unneededAnnotations = annoView.getSelectedAnnotations();
			for (AnnotationView av : unneededAnnotations) {
				deleteAnnotations(av.annotation.getRange(), av.getTrackNumber());
				av.redraw();
			}
		}
	}
}
