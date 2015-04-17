package edu.teco.tacet.annotation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Range;

public class AnnotationView extends Composite {

	private AnnotationCompositePaintListener paintListener;
	private boolean isSelected = false;
	private TrackView trackView;
	private AnnotationPart annotationPart;
	public Annotation annotation;
	
	public AnnotationView(TrackView parent, int style, AnnotationPart annotationView, Annotation annotation) {
		super(parent, style);
		this.annotationPart = annotationView;
		this.annotation = annotation;
		setTrack(parent);
			
		// setToolTipText(annotation.getLabel());
		
		// add listeners
		paintListener = new AnnotationCompositePaintListener();
		paintListener.setTitle(annotation.getLabel()); // set title
		this.addPaintListener(paintListener);
		
		// position annotation
		int x = trackView.translateTimestampToPixel(annotation.getStart());
		int width = trackView.translateTimestampToPixel(annotation.getEnd()) - x;
		setBounds(x, 5, width, 30);
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				AnnotationView av = (AnnotationView) e.widget;
				av.annotationPart.openAnnotationDialog(av.trackView.trackNumber, av.annotation);
			}
			@Override
			public void mouseDown(MouseEvent e) {	
			}
			@Override
			public void mouseUp(MouseEvent e) {
				if ((e.stateMask & SWT.CTRL) != 0) {
					AnnotationView.this.setMultiSelected(true); // TODO make deselectable
				} else {
					AnnotationView.this.setSelected(true);
				}
			}
		});
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		if (paintListener != null) {
			this.paintListener.setDimensions(width, height);
		}
	}
	
	public boolean getSelected() {
		return this.isSelected;
	}
	
	public void setSelected(boolean isSelected) {
		if(isSelected) {
			// remember which annotation is currently selected
			annotationPart.setSelectedAnnotation(this);
			paintListener.setBorderColor(paintListener.BORDER_COLOR_HIGHLIGHT);
			paintListener.setBorderWidth(2);
		}
		else {
			paintListener.setBorderColor(paintListener.BORDER_COLOR_DEFAULT);
			paintListener.setBorderWidth(1);
		}
		this.isSelected = isSelected;
		redraw();
	}
	
	protected void setMultiSelected(boolean isSelected) {
		annotationPart.addSelectedAnnotation(this);
		paintListener.setBorderColor(paintListener.BORDER_COLOR_HIGHLIGHT);
		paintListener.setBorderWidth(2);
		this.isSelected = isSelected;
		redraw();
	}
	
	public void setTrack(TrackView trackView) {
		this.trackView = trackView;
	}
	
	public void updatePosition(Range newRange) {
		Rectangle rect = getBounds();
    	rect.x = trackView.translateTimestampToPixel(annotation.getStart()); 
    	if (newRange.overlapsWith(annotation.getRange())) {
    		setBounds(rect);
    	} else {
    		dispose(); // remove because offscreen
    	}
	}

	// could be observed or always requested on redraw
	public void updateTitle() {
		paintListener.setTitle(annotation.getLabel());
	}
	
	public int getTrackNumber() {
		return trackView.trackNumber;
	}
	
}
