package edu.teco.tacet.track;


import java.util.TimerTask;


public abstract class Scheduler extends TimerTask {
	protected boolean isPlaying;
	protected long actualTime;
	protected long startTime;
	protected long endTime;
	private static double playbackSpeed = 1.0;
	private static long minimalDeltaTime = 200;

	public Scheduler(long actualTime, long endTime) {
		super();
		this.actualTime = actualTime;
		this.startTime = actualTime;
		this.endTime = endTime;
	}

	public static double getPlaybackSpeed() {
		return playbackSpeed;
	}

	public static void setPlaybackSpeed(double playbackSpeed) {
		Scheduler.playbackSpeed = playbackSpeed;
	}

	@Override
	public void run() {
		if (isPlaying) {
			if (actualTime < endTime) {
				StateObservable.getInstance().publishTime((long) (actualTime + 40 * playbackSpeed));
			} else {
			    StateObservable.getInstance().publishTime(endTime);
				isPlaying(false);
			}
		}
	}


	public void isPlaying(boolean isPlaying){
		this.isPlaying = isPlaying;
	}

	public void publishTime(long time){
		actualTime = time;
	}

	public synchronized void changeTime(long delta) {
		actualTime += delta;
		publishTime(actualTime);
	}

	public void nextStep() {
		changeTime(minimalDeltaTime);
	}

	public void prevStep() {
		changeTime(-minimalDeltaTime);
	}

	public void changeEndTime(long l) {
		this.endTime = l;
	}

	public void changeStartTime(long l) {
        this.startTime = l;
    }

}
