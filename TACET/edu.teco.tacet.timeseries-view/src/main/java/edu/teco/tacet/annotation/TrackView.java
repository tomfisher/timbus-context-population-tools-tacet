package edu.teco.tacet.annotation;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Range;

public class TrackView extends Composite implements PaintListener {

	public int trackNumber;
	public Range visibleRange;
	public AnnotationModel annotationModel;
	private AnnotationView selectedAnnotation;
	private AnnotationPart annotationPart;
	
	public TrackView(Composite parent, int style, AnnotationModel annotationModel, AnnotationPart annotationPart) {
		super(parent, style);
		this.annotationModel = annotationModel;
		this.annotationPart = annotationPart;
		this.addPaintListener(this);
		
		this.addMouseListener(annotationPart.annoController);
		this.addMouseMoveListener(annotationPart.annoController);
//		this.addKeyListener(annotationPart.annoController);
		
		Color lightGrey = new Color(Display.getCurrent(), 200, 200, 200);
		setBackground(lightGrey);
		// make subviews have 'transparent' background
		setBackgroundMode(SWT.INHERIT_FORCE);
				
		this.redraw();
		this.update();
	}
	
	public int translateTimestampToPixel(long timestamp) {
		double ratio = (double) getBounds().width / (double) visibleRange.getDistance();
		long offset = timestamp - visibleRange.getStart();
		return (int) (ratio * offset);
	}
	
	public long translatePixelToTimestamp(int x) {
		double ratio = (double) visibleRange.getDistance() / (double) getBounds().width;
		long offset = visibleRange.getStart();
		return (long) (ratio * x + offset);
	}

	@Override
	public void paintControl(PaintEvent e) {
		visibleRange = annotationPart.getState().getVisibleRange();
		
		HashSet<Annotation> annotationsInModel = new HashSet<Annotation>();
		for (Annotation annotation : annotationModel.getAnnotations(visibleRange, trackNumber)) {
			annotationsInModel.add(annotation);
		}
		
		// reposition existing annotations
		HashSet<Annotation> movedAnnotations = moveAnnotations(annotationsInModel); 
		
		// add missing annotations
		insertMissingAnnotations(annotationsInModel, movedAnnotations);
		
		// paint Preview
		Range previewRange = annotationPart.getState().getSelectedRange();
		if (annotationPart.getState().isRangeSelected() && previewRange != null) {
			int x = translateTimestampToPixel(previewRange.getStart());
			int width = translateTimestampToPixel(previewRange.getEnd()) - x;
			e.gc.setAlpha(100);
			e.gc.setBackground(new Color(Display.getCurrent(), 0, 0, 200));
			e.gc.fillRectangle(translateTimestampToPixel(previewRange.getStart()), 0, width, 40);
		}
	}
	
	/**
	 * @param validAnnotations
	 * @return Set of moved Annotations
	 */
	private HashSet<Annotation> moveAnnotations(HashSet<Annotation> validAnnotations) {
		HashSet<Annotation> result = new HashSet<Annotation>();
		for (Control control : this.getChildren()) {
	        if (control.getClass() == AnnotationView.class) {
	        	AnnotationView annotationView = (AnnotationView) control;
	        	if (validAnnotations.contains(annotationView.annotation)) {
	        		annotationView.updatePosition(visibleRange);
	        		annotationView.updateTitle();
	        		result.add(annotationView.annotation);
	        	} else {
	        		annotationView.dispose(); // annotation not in model anymore
	        	}
	        }
	    }
		return result;
	}
	
	private void insertMissingAnnotations(HashSet<Annotation> validAnnotations, HashSet<Annotation> exceptedAnnotations) {
		validAnnotations.removeAll(exceptedAnnotations);
		for (Annotation annotation : validAnnotations) {
			new AnnotationView(this, SWT.PUSH, annotationPart, annotation);
		}
	}
	
	public void setSelectedAnnotation(AnnotationView annotationView) {
		if(selectedAnnotation != null && !selectedAnnotation.isDisposed()) {
			selectedAnnotation.setSelected(false);
		}
		selectedAnnotation = annotationView;
	}

	public void setPreviewName(String label) {
		// TODO Auto-generated method stub
		
	}

}
