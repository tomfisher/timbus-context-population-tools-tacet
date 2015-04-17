package edu.teco.tacet.track;
import java.util.Observable;
import java.util.Timer;


public class StateObservable extends Observable{
    private State defaultState;
	private Scheduler scheduler;
	private final Timer executorService = new Timer();
	private static StateObservable instance;
	public static double inverseGoldenRatio = 1 - (2 / (double) (1 + Math
			.sqrt(5)));

	private StateObservable() {
	    defaultState = new State(TrackManager.getInstance().getGlobalRange().getStart(), calculateStartRange(), new Range(0, 0), 1L, 1, false);
		final long globalRangeStart = TrackManager.getInstance().getGlobalRange().getStart();
		final long globalRangeEnd = TrackManager.getInstance().getGlobalRange().getEnd();
		scheduler = new Scheduler(globalRangeStart, globalRangeEnd) {
			@Override
			public void publishTime(long time) {

				Range currentRange = defaultState.getVisibleRange();
				long goldenTimestamp = currentRange.getStart()
						+ (long) (currentRange.getDistance() * inverseGoldenRatio);
				Range newRange = currentRange.move(time - goldenTimestamp);
				if (newRange.getStart() < globalRangeStart) {
					newRange = newRange.move(globalRangeStart
							- newRange.getStart());
				} else if (newRange.getEnd() > globalRangeEnd) {
					newRange = newRange.move(globalRangeEnd - newRange.getEnd());
				}
//				if(speed != (double) newRange.getDistance() / 4000) {
//		    		speed = (double) newRange.getDistance() / 4000;
//		    		setPlaybackSpeed(speed);
//		    		System.out.println(speed);
//		    	}

				defaultState.setVisibleRange(newRange);
				super.publishTime(time);
				getState().setTime(time);
				updateState();
			}
		};
		executorService.scheduleAtFixedRate(scheduler, 0L, 40);
	}

	public static StateObservable getInstance() {
		if (StateObservable.instance == null) {
			StateObservable.instance = new StateObservable();
		}
		return StateObservable.instance;
	}

	public void updateState(){
		setChanged();
		notifyObservers(defaultState);
	}

	public State getState() {
		return defaultState;
	}

	public void setPlaying(boolean isPlaying){
		getState().setPlaying(isPlaying);
		scheduler.isPlaying(isPlaying);
	}

	public void setState(State state) {
	    defaultState = state;
		scheduler.publishTime(defaultState.getTime());
	}

	public void setPlaybackSpeed(double speed) {
		Scheduler.setPlaybackSpeed(speed);
		getState().setPlaybackSpeed(speed);
	}

	protected Range calculateStartRange() {
		Range globalRange = TrackManager.getInstance().getGlobalRange();
		if (globalRange == null)
			return null;
		return new Range(globalRange.getStart(), globalRange.getStart()
				+ globalRange.getDistance() / 32L);
	}

	public void updateSchedulerRange(long start, long end) {
	    scheduler.changeEndTime(end);
	    scheduler.changeStartTime(start);
	}

	public void publishTime(long time) {
	    defaultState.setTime(time);
	    scheduler.publishTime(time);
	    updateState();
	}
}
