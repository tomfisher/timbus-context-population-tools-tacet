package edu.teco.tacet.timeview;

import java.util.Observable;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.part.ViewPart;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.osgi.service.prefs.Preferences;

import edu.teco.tacet.observer.IViewComponent;
import edu.teco.tacet.track.State;
import edu.teco.tacet.track.StateObservable;
import edu.teco.tacet.track.TrackManager;

public class TimeView extends ViewPart implements IViewComponent {
	public TimeView() {
	}
	/** The play button. */
	private Button playButton;
	/** The stop button. */
	private Button stopButton;
	/** The next frame button. */
	private Button next;
	/** The previous frame button. */
	private Button prev;
	/** The position slider. */
	private Scale positionSlider;
	/** The time label. */
	private Label timeLabel;
	private Label slash;
	/** The max time. */
	private Label maxTime;
	private boolean init = false;
	private TimeViewMouseListener tMListener;
	private long maximumTime;
	private long minimumTime;
	private Composite parent = null;
	private long actualTime;
	private Display display;
	private boolean disposed = false;

	private StateObservable stObservable;
	
	/**Volume slider*/
	private Scale volumeSlider;	
	/**Speed slider*/
	private Scale speedSlider;
	
	private Button minusButton;
	private Button plusButton;
	Label speedLabel;
		
	

	/** Sets up GUI elements*/
	@Override
	public void createPartControl(Composite parent) {
		disposed = false;
		display = Display.getCurrent();
		this.parent = parent;
		parent.setLayout(new FormLayout());
		
		// Buttons
		playButton = new Button(parent, SWT.NONE);
		FormData fd_playButton = new FormData();
		fd_playButton.top = new FormAttachment(0, 13);
		fd_playButton.left = new FormAttachment(0, 5);
		playButton.setLayoutData(fd_playButton);
		stopButton = new Button(parent, SWT.NONE);
		FormData fd_stopButton = new FormData();
		fd_stopButton.top = new FormAttachment(playButton, 0, SWT.TOP);
		fd_stopButton.left = new FormAttachment(playButton, 6);
		stopButton.setLayoutData(fd_stopButton);
		prev = new Button(parent, SWT.NONE);
		FormData fd_prev = new FormData();
		fd_prev.top = new FormAttachment(playButton, 0, SWT.TOP);
		fd_prev.left = new FormAttachment(stopButton, 6);
		prev.setLayoutData(fd_prev);
		next = new Button(parent, SWT.NONE);
		FormData fd_next = new FormData();
		fd_next.top = new FormAttachment(playButton, 0, SWT.TOP);
		fd_next.left = new FormAttachment(prev, 6);
		next.setLayoutData(fd_next);
		playButton.setText("Start");
		stopButton.setText("Stop");
		next.setText("Next");
		prev.setText("Prev");
		
		// Time Labels in reverse order (layout dependency)
		maxTime = new Label(parent, SWT.NONE);
		maxTime.setText("[max time]");
		FormData fd_maxTime = new FormData();
		fd_maxTime.top = new FormAttachment(0, 18);
		fd_maxTime.right = new FormAttachment(100, -6);
		maxTime.setLayoutData(fd_maxTime);
		slash = new Label(parent, SWT.NONE);

		slash.setText("/");
		FormData fd_slash = new FormData();
		fd_slash.top = new FormAttachment(0, 18);
		fd_slash.right = new FormAttachment(maxTime, -6);
		slash.setLayoutData(fd_slash);

		timeLabel = new Label(parent, SWT.NONE);
		timeLabel.setText("[time]");
		FormData fd_timeLabel = new FormData();
		fd_timeLabel.top = new FormAttachment(0, 18);
		fd_timeLabel.right = new FormAttachment(slash, -6);
		timeLabel.setLayoutData(fd_timeLabel);
	
		// Position Slider
		positionSlider = new Scale(parent, SWT.NONE);
		FormData fd_positionSlider = new FormData();
		fd_positionSlider.top = new FormAttachment(0, 4);
		fd_positionSlider.left = new FormAttachment(next, 6);
		fd_positionSlider.right = new FormAttachment(timeLabel, -6);
		positionSlider.setLayoutData(fd_positionSlider);
		positionSlider.setMaximum(1000);
		positionSlider.setMinimum(0);


		// Set up *volume slider* and label
		Label lblVolume = new Label(parent, SWT.NONE);
		FormData fd_lblVolume = new FormData();
		fd_lblVolume.top = new FormAttachment(playButton, 18);
		fd_lblVolume.left = new FormAttachment(playButton, 0, SWT.LEFT);
		lblVolume.setLayoutData(fd_lblVolume);
		lblVolume.setText("Volume");
		
		volumeSlider = new Scale(parent, SWT.NONE);
		volumeSlider.setMaximum(200);
		volumeSlider.setSelection(100);
		FormData fd_volumeSlider = new FormData();
		fd_volumeSlider.top = new FormAttachment(positionSlider);
		fd_volumeSlider.left = new FormAttachment(lblVolume, 0, SWT.RIGHT);
		volumeSlider.setLayoutData(fd_volumeSlider);
		

		// Set up *speed slider* and label
		Label lblPlaybackSpeed = new Label(parent, SWT.NONE);
		FormData fd_lblPlaybackSpeed = new FormData();
		fd_lblPlaybackSpeed.top = new FormAttachment(playButton, 18);
		fd_lblPlaybackSpeed.left = new FormAttachment(volumeSlider, 12);
		lblPlaybackSpeed.setLayoutData(fd_lblPlaybackSpeed);
		lblPlaybackSpeed.setText("Playback speed");
		
		speedSlider = new Scale(parent, SWT.NONE);
		speedSlider.setMaximum(50);
		speedSlider.setSelection(25);
		FormData fd_scale = new FormData();
		fd_scale.top = new FormAttachment(positionSlider);
		fd_scale.left = new FormAttachment(lblPlaybackSpeed, 6);
		speedSlider.setLayoutData(fd_scale);
		
		// Speed buttons and label
		minusButton = new Button(parent, SWT.NONE);
		FormData fd_minusButton = new FormData();
		fd_minusButton.top = new FormAttachment(positionSlider, 6);
		fd_minusButton.left = new FormAttachment(speedSlider, 6);
		minusButton.setLayoutData(fd_minusButton);
		minusButton.setText("-");
		
		plusButton = new Button(parent, SWT.NONE);
		FormData fd_plusButton = new FormData();
		fd_plusButton.top = new FormAttachment(positionSlider, 6);
		fd_plusButton.left = new FormAttachment(minusButton, 6);
		plusButton.setLayoutData(fd_plusButton);
		plusButton.setText("+");
		
		speedLabel = new Label(parent, SWT.NONE);
		FormData fd_lblxspeed = new FormData();
		fd_lblxspeed.top = new FormAttachment(playButton, 18);
		fd_lblxspeed.left = new FormAttachment(plusButton, 6);
		speedLabel.setLayoutData(fd_lblxspeed);
		speedLabel.setText("x2^0        ");
		
		

	}

	public double getSliderPosition() {
		// double missingDifference = 10;
		return (double) positionSlider.getSelection()
				/ (double) positionSlider.getMaximum();
	}
	
	public long getSliderTime() {
		
		return minimumTime + (long)(getSliderPosition()*(maximumTime - minimumTime));
	}

	public void addListener() {
		
		positionSlider.addMouseListener(tMListener);
		positionSlider.addMouseMoveListener(tMListener);
		
		// TODO higher level listeners would be more appropriate
		playButton.addMouseListener(tMListener);
		stopButton.addMouseListener(tMListener);
		next.addMouseListener(tMListener);
		prev.addMouseListener(tMListener);
					
		// Use higher level listeners instead
		volumeSlider.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  stObservable.getState().setVolume(volumeSlider.getSelection());
		    	  //stObservable.updateState();
		        }
		      });

		speedSlider.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  int exponent = speedSlider.getSelection() - 25;
		    	  double playbackSpeed = Math.pow(2, exponent);
		    	  speedLabel.setText("x2^" + exponent);
		    	  stObservable.setPlaybackSpeed(playbackSpeed);
		    	  //stObservable.updateState();
		        }
		      });
		
		minusButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				speedSlider.setSelection(speedSlider.getSelection() - 1);
				final int exponent = speedSlider.getSelection() - 25;
				double playbackSpeed = Math.pow(2, exponent);
				display.asyncExec(new Runnable() {
					public void run() {
						if (!disposed) {
							speedLabel.setText("x2^" + exponent);
						}
					}
				});
				stObservable.setPlaybackSpeed(playbackSpeed);
				// stObservable.updateState();
			}
		});
		
		plusButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				speedSlider.setSelection(speedSlider.getSelection() + 1);
				final int exponent = speedSlider.getSelection() - 25;
				double playbackSpeed = Math.pow(2, exponent);
				display.asyncExec(new Runnable() {
					public void run() {
						if (!disposed) {
							speedLabel.setText("x2^" + exponent);
						}
					}
				});
				stObservable.setPlaybackSpeed(playbackSpeed);
				System.out.println("Rate: " + playbackSpeed);
				// stObservable.updateState();
			}
		});
		
	}

	@Override
	public void setFocus() {
		if (!TrackManager.isInitialised() || init) {
			return;
		}
		init = true;
		timeLabel.setText(convertTime(getMinTime()));
		slash.setText("/");
		maxTime.setText(convertTime(getMaxTime()));
		maximumTime = getMaxTime();
		minimumTime = getMinTime();
		stObservable = StateObservable.getInstance();
		stObservable.addObserver(this);
		tMListener = new TimeViewMouseListener();
		addListener();
		parent.redraw();
	}

	public void refreshMaxTimeLabel() {
		maxTime.setText(convertTime(maximumTime));
	}

	public void setPositionValue(long timeStamp) {
		int position = Math
				.round((((float) (timeStamp - minimumTime) / (float) (maximumTime - minimumTime)) * 1000));
		positionSlider.setSelection(position);

	}

	private String convertTime(long time) {
		Preferences preferences = ConfigurationScope.INSTANCE
				.getNode("edu.teco.tacet.preferences");
		Preferences sub1 = preferences.node("ui1");
		if (sub1.getBoolean("numerical", false)) {
			return String.valueOf(time);
		} else {
			DateTimeFormatter fmt = DateTimeFormat.forPattern(sub1.get("timestampformat", "yyyy-MM-dd HH:mm:ss"));
			DateTime datetime = new DateTime(time);
			String s = datetime.toString(fmt);
			return s;
		}
	}

	public void timeChanged(long timeStamp) {
		actualTime = timeStamp;
		// setPositionValue(actualTime);
		display.asyncExec(new Runnable() {
			public void run() {
				if (!disposed) {
					timeLabel.setText(convertTime(actualTime));
					setPositionValue(actualTime);

					// Only needed if global range is changed, need better way?
					maximumTime = getMaxTime();
					minimumTime = getMinTime();
					maxTime.setText(convertTime(getMaxTime()));
				}
			}
		});
	}

	public void setPlay(boolean isPlaying) {
		if (!isPlaying) {
			display.asyncExec(new Runnable() {
				public void run() {
					if (!disposed) {
						playButton.setText("Play");
					}
				}
			});
		} else {
			display.asyncExec(new Runnable() {
				public void run() {
					if (!disposed) {
						playButton.setText("Pause");
					}
				}
			});
		}
	}

	@Override
	public void dispose() {
		disposed = true;
		super.dispose();
	}

	@Override
	public State getState() {
		return StateObservable.getInstance().getState();
	}
	


	@Override
	public void setState(State state) {
		if (state.isPlaying()) {
			setPlay(true);
		} else {
			setPlay(false);
		}
		timeChanged(state.getTime());
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof State) {
			setState((State) arg1);
		}

	}

	@Override
	public long translatePixelToTimestamp(int x) {
		return 0;
	}

	@Override
	public int translateTimestampToPixel(long timestamp) {
		return 0;
	}

	@Override
	public int getTimeLinePos() {
		return 0;
	}

	@Override
	public void redraw() {

	}
	
	public long getMaxTime() {
		return TrackManager.getInstance().getGlobalRange().getEnd();
	}

	public long getMinTime() {
		return TrackManager.getInstance().getGlobalRange().getStart();
	}
	
	private class TimeViewMouseListener implements MouseListener, MouseMoveListener {
		
		private boolean mouseDown = false;
		private boolean wasPlaying = false;
		private long minimalDeltaTime = 200;
		
		@Override
		public void mouseMove(MouseEvent e) {
			if (e.getSource() == positionSlider) {
				if (mouseDown) {
					float position = (float)(getSliderPosition());
					float length = maximumTime-minimumTime;
					double offset = minimumTime;
					long time = (long)(position*length+offset);
					stObservable.publishTime(time);
					stObservable.updateState();
				}
			} 
		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {
		}

		@Override
		public void mouseDown(MouseEvent e) {
			if (e.getSource() == playButton) {
				StateObservable.getInstance().setPlaying(!StateObservable.getInstance().getState().isPlaying());
			} else if (e.getSource() == stopButton) {
				stObservable.publishTime(getMinTime());
				stObservable.setPlaying(false);
			} else if (e.getSource() == positionSlider) {
				mouseDown = true;
				if (stObservable.getState().isPlaying()) {
					stObservable.setPlaying(false);
					wasPlaying = true;
				}	
			} else if (e.getSource() == next) {
				stObservable.setPlaying(false);
				stObservable.publishTime(stObservable.getState().getTime()+minimalDeltaTime);		
			} else if (e.getSource() == prev) {
				stObservable.setPlaying(false);
				stObservable.publishTime(stObservable.getState().getTime()-minimalDeltaTime);
			}
			stObservable.updateState();
		}

		@Override
		public void mouseUp(MouseEvent e) {
			if (e.getSource() == positionSlider) {
				mouseDown = false;
				stObservable.publishTime(getSliderTime());
				if(wasPlaying) {
					stObservable.setPlaying(true);
					wasPlaying = false;
				}
				stObservable.updateState();
			}
		
		}
	}
}
