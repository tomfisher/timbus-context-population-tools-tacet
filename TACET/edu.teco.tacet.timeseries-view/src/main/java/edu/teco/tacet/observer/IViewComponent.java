package edu.teco.tacet.observer;

import java.util.Observer;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.State;
import edu.teco.tacet.track.TrackManager;

public interface IViewComponent extends Observer{
	public State getState(); 

	public void setState(State state);
	
	/**
     * Given a pixel {@code x} compute corresponding timestamp.
     *
     * @param x an x-value contained in this component.
     * @return return the corresponding timestamp.
     * @see #translateTimestampToPixel(int)
     */
    public long translatePixelToTimestamp(int x);

    /**
     * Given a {@code timestamp} compute the corresponding pixel in this
     * component.
     *
     * @param timestamp that is to be translated
     * @return return the corresponding x value in pixel
     * @see #translatePixelToTimestamp(int)
     */
    public int translateTimestampToPixel(long timestamp);


	public int getTimeLinePos();	

	public abstract void redraw();
	
}
