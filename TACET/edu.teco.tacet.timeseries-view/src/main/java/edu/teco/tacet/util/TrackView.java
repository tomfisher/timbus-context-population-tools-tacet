package edu.teco.tacet.util;

import org.eclipse.ui.part.ViewPart;

import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.TrackManager;

public abstract class TrackView {

	private int timeLinePos = 0;
	
	private double pixelTimeRatio = 1.0d;

	private boolean isSelectionActive;
    private Range selection = new Range(0, 1);
	private int selectedTrack;
	
    private Range visibleExcerpt;
    
    private boolean isEditingActive;
	private Range editingExcerpt;
	
	

	/**
     * Given a pixel {@code x} compute corresponding timestamp.
     *
     * @param x an x-value contained in this component.
     * @return return the corresponding timestamp.
     * @see #translateTimestampToPixel(int)
     */
    public abstract long translatePixelToTimestamp(int x);

    /**
     * Given a {@code timestamp} compute the corresponding pixel in this
     * component.
     *
     * @param timestamp that is to be translated
     * @return return the corresponding x value in pixel
     * @see #translatePixelToTimestamp(int)
     */
    public abstract int translateTimestampToPixel(long timestamp);

    /**
     * Given a {@code distance} in pixel and the current {@code pixelTimeRatio},
     * compute the distance in timeunits.
     * @param distance a distance given in pixel
     * @return the distance in timeunits
     * @see #getPixelTimeRatio()
     */
    public long translatePixelToTimestampDistance(int distance) {
        return (long) (distance / pixelTimeRatio);
    }

    /**
     * Given a {@code distance} in timeunits and the current {@code
     * pixelTimeRatio}, compute the distance in pixels.
     * @param distance a distance given in timeunits
     * @return the distance in pixel
     * @see #getPixelTimeRatio()
     */
    public int translateTimestampToPixelDistance(long distance) {
        return (int) (distance * pixelTimeRatio);
    }

    /**
     * Set this components visibleExcerpt to {@code range}.
     * <p>
     * The {@code visibleExcerpt} of this instance defines which excerpt of the
     * timeline is currently displayed by this instance.  {@code
     * range.getStart()} and {@code range.getEnd()} determine the first and the
     * last timestamp that is currently displayed.
     *
     * @param range the range to become the new {@code  visibleExcerpt}.
     */
    public void setVisibleExcerpt(Range range){
    	this.visibleExcerpt = range;
    }

    /**
     * Returns this components {@code visibleExcerpt}.
     *
     * @return this components {@code visibleExcerpt}.
     * @see #setVisibleExcerpt(Range)
     */
    public Range getVisibleExcerpt(){
    	return this.visibleExcerpt;
    }

    public void setCurrentTime(long time) {
		this.timeLinePos = translateTimestampToPixel(time);
	}

	public int getTimeLinePos(){
		return timeLinePos;
	}

	public abstract void redraw();

    /**
	 * Returns the current pixelTimeRatio.
	 * @return the current pixelTimeRatio.
	 * @see #setPixelTimeRatio(double)
	 */
	public double getPixelTimeRatio() {
	    return pixelTimeRatio;
	}

	/**
	 * This ratio controls how many pixels are painted for every time unit. If
	 * it's value is > 1, then more than one pixel is used to represent a single
	 * time unit. A value < has the exact opposite effect, more than one units
	 * of time are represented by one pixel. The last case is a value equal to
	 * one which leads to one time unit being represented by exactly one pixel.
	 */
	public void setPixelTimeRatio(double pixelTimeRatio) {
	
		this.pixelTimeRatio = pixelTimeRatio;
	}

	public void setResolution(long resolution) {}

	public boolean isSelectionActive() {
	    return this.isSelectionActive;
	}

	public void cancelSelection() {
    	this.isSelectionActive = false;
    	redraw();
    }

    public void setSelection(Range selectedExcerpt) {
    	this.isSelectionActive = true;
        this.selection = selectedExcerpt;
        redraw();
    }

    public Range getSelection() {
    	return this.selection;
    }

    public void setSelectedTrack(int selectedTrack) {
		this.selectedTrack = selectedTrack;		
	}

	public int getSelectedTrack() {
		return this.selectedTrack;
	}

	public boolean isEditingActive() {
		return isEditingActive;
	}

	public void cancelEditing() {
		this.isEditingActive = false;
		redraw();
	}

	public void setEditingExcerpt(Range editingExcerpt) {
		this.isEditingActive = true;
		this.editingExcerpt = editingExcerpt;
		redraw();
	}
	
	public Range getEditingExcerpt() {
		return this.editingExcerpt;
	}
	
	protected Range calculateStartRange() {
	    Range globalRange = TrackManager.getInstance().getGlobalRange();
	    if (globalRange == null)
	        return null;
        return new Range(globalRange.getStart(), globalRange.getStart() + globalRange.getDistance() / 2L);
	}

}
