package edu.teco.tacet.timeseriesview;

public interface ITrackDisplayer {
	
	/**
	 * All corresponding Tracks to the id will be shown
	 * @param id a Iterable of the id corresponding to a track
	 */
	public void showTracks(Iterable<Long> ids);	
	
	public Iterable<Long> getVisibleTracks();
	
	public void setOwnID(String id);

	public String getOwnID();
	
	public void setDisplayerName(String name);
}
