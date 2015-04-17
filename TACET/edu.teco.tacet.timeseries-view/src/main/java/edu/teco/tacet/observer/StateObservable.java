package edu.teco.tacet.observer;

import java.util.Observable;
import java.util.Timer;

import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.Scheduler;
import edu.teco.tacet.track.State;
import edu.teco.tacet.track.TrackManager;

public class StateObservable extends Observable{
	private State state;
	private Scheduler scheduler;
	private final Timer executorService = new Timer();
	private static StateObservable instance;
	public static double inverseGoldenRatio = 1 - (2 / (double) (1 + Math
			.sqrt(5)));

	private double speed = 1.0;
	private StateObservable() {		
		final long globalRangeStart = TrackManager.getInstance().getGlobalRange().getStart();
		final long globalRangeEnd = TrackManager.getInstance().getGlobalRange().getEnd();
		this.state = new State(globalRangeStart, calculateStartRange(), new Range(0, 0), 1L, 1, false);
		scheduler = new Scheduler(globalRangeStart, globalRangeEnd) {
			@Override
			public void publishTime(long time) {
				
				Range currentRange = state.getVisibleRange();
				long goldenTimestamp = currentRange.getStart()
						+ (long) (currentRange.getDistance() * inverseGoldenRatio);
				Range newRange = currentRange.move(time - goldenTimestamp);
				if (newRange.getStart() < globalRangeStart) {
					newRange = newRange.move(globalRangeStart
							- newRange.getStart());
				} else if (newRange.getEnd() > globalRangeEnd) {
					newRange = newRange.move(globalRangeEnd - newRange.getEnd());
				}
				if(speed != (double) newRange.getDistance() / 4000) {
		    		speed = (double) newRange.getDistance() / 4000;
		    		setPlaybackSpeed(speed);
		    		System.out.println(speed);
		    	}
				
				state.setVisibleRange(newRange);
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
		notifyObservers(state);	
	}

	public State getState() {
		return state;
	}
	
	public void setPlaying(boolean isPlaying){
		getState().setPlaying(isPlaying);
		scheduler.isPlaying(isPlaying);
	}

	public void setState(State state) {
		this.state = state;
		scheduler.publishTime(state.getTime());		
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

	public void refreshState() {
		scheduler.publishTime(state.getTime());		
	}
	
}
