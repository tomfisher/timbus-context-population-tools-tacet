package edu.teco.tacet.track;


public class State {
    private long time;
	private Range visibleRange;
	private Range selectedRange;
	private long resolution;
	private double pixelTimeRatio;
	private boolean playing;
	private double playbackSpeed = 1.0;
	private boolean isRangeSelected = false;
	private boolean annotationDialogOpen = false;
	private int volume = 100;

    private static final int MAX_VOLUME = 200;

	public State(long time, Range visibleRange, Range selectedRange,
			long resolution, double pixelTimeRatio, boolean playing) {
		super();
		this.time = time;
		this.visibleRange = visibleRange;
		this.selectedRange = selectedRange;
		this.resolution = resolution;
		this.pixelTimeRatio = pixelTimeRatio;
		this.playing = playing;
	}

	public boolean isPlaying() {
		return playing;
	}

	public boolean getAnnotationDialogOpen() {
		return annotationDialogOpen;
	}

	public void setAnnotationDialogOpen(boolean dialogOpen) {
		this.annotationDialogOpen = dialogOpen;
	}

	public double getPlaybackSpeed() {
		return playbackSpeed;
	}

	public void setPlaybackSpeed(double playbackSpeed) {
		this.playbackSpeed = playbackSpeed;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isRangeSelected() {
		return isRangeSelected;
	}

	public void setRangeSelected(boolean isRangeSelected) {
		this.isRangeSelected = isRangeSelected;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		if(TrackManager.getInstance().getGlobalRange().getStart()>time){
			this.time = TrackManager.getInstance().getGlobalRange().getStart();
		} else {
			this.time = time;
		}
	}

	public Range getVisibleRange() {
		return visibleRange;
	}

	public void setVisibleRange(Range visibleRange) {
		this.visibleRange = visibleRange;
	}

	public Range getSelectedRange() {
		return selectedRange;
	}

	public void setSelectedRange(Range selectedRange) {
		this.setRangeSelected(selectedRange != null);
		this.selectedRange = selectedRange;
	}

	public long getResolution() {
		return resolution;
	}

	public void setResolution(long resolution) {
		this.resolution = resolution;
	}

	public double getPixelTimeRatio() {
		return pixelTimeRatio;
	}

	public void setPixelTimeRatio(double pixelTimeRatio) {
		this.pixelTimeRatio = pixelTimeRatio;
	}

	public int getVolume() {
	    return volume;
	}

	public void setVolume(int value) {
	    if (value < 0) {
	        volume = 0;
	    } else if (value > MAX_VOLUME) {
	        volume = MAX_VOLUME;
	    } else {
	        volume = value;
	    }
	}



	@Override
	public String toString() {
		String output = "The Actual State schould be: \n" + "Time: "
				+ time + "\n" + "visibleRange " + visibleRange + "\n"
				+ "selectedRange " + selectedRange + "\n" + "resolution "
				+ resolution + "\n" + "pixelTimeRatio " + pixelTimeRatio + "\n"
				+ "playing " + playing + "\n" + "playbackSpeed "
				+ playbackSpeed + "\n" + "isRangeSelected " + isRangeSelected
				+ "\n" + "openCreatDialog " + annotationDialogOpen;
		return output;

	}
}
