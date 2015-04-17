package edu.teco.tacet.test;


import java.util.Observable;
import java.util.Observer;

import edu.teco.tacet.observer.IViewComponent;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.State;

public class ViewComponentImpl implements IViewComponent{
	State state;
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof State){
			state = (State) arg;
			System.out.println(state.getTime());			
		}		
	}
	@Override
	public State getState() {
		return state;
	}
	@Override
	public void setState(State state) {
		this.state = state;
	}
	@Override
	public long translatePixelToTimestamp(int x) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int translateTimestampToPixel(long timestamp) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getTimeLinePos() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void redraw() {
		// TODO Auto-generated method stub
		
	}
	
	

	
	

	
		
}
