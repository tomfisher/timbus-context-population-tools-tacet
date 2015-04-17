package edu.teco.tacet.annotation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

import edu.teco.tacet.observer.IAnnotationViewComponent;
import edu.teco.tacet.test.MockAnnotationModel;
import edu.teco.tacet.timeseriesview.ITrackDisplayer;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.State;
import edu.teco.tacet.track.StateObservable;
import edu.teco.tacet.track.TrackManager;

public class AnnotationPart extends ViewPart implements ITrackDisplayer, IAnnotationViewComponent {

	private Display display;
	private Composite parent = null;
	private boolean disposed = false;
	
	private State state;
	private StateObservable stateObservable;
	public AnnotationTrackController annoController;
	private AnnotationModel annoModel;
	private boolean viewsInitializing = false;
	private boolean setupsComplete = false;
	private int selectedtrack = 0;
	private String id;
	public ArrayList<TrackView> tracks = new ArrayList<TrackView>();
	private ArrayList<Label> labels = new ArrayList<Label>();
	public AnnotationView selectedAnnotation;
	public HashSet<AnnotationView> selectedAnnotations = new HashSet<AnnotationView>();
	
	public Composite container;
	public Composite timeSlider;
	
	private AnnotationDialog annotationDialog;
	private boolean annotationDialogOpen = false;
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		disposed = false;
	}
	
	@Override
	public void setFocus() {
		if (!TrackManager.isInitialised()) {
			return;
		}

		if (!viewsInitializing) {
			viewsInitializing = true;
			setupViews();
			setupsComplete = true;
		} 
		if (setupsComplete) {
			updateBounds();
			container.forceFocus();
		}

		this.redraw();
	}
	
	private void setupViews() {
		if (this.annoController == null) {
			stateObservable = StateObservable.getInstance();
			stateObservable.addObserver(this);
			state = stateObservable.getState();
			this.annoController = new AnnotationTrackController(this, annoModel, this);
		}
		
		// Custom TrackComposite
		container = new Composite(parent, SWT.FILL);
		container.setBounds(0, 0, parent.getBounds().width, parent.getBounds().height);
		
		container.addKeyListener(annoController);
		
		display = container.getDisplay();
		
		container.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				AnnotationPart.this.updateBounds();
			}
		});
		
		// time slider
		timeSlider = new Composite(container, SWT.PUSH);
		Color red = new Color(Display.getCurrent(), 211, 0, 0);
		timeSlider.setBackground(red);
		timeSlider.setBounds(this.getTimeLinePos(), 0, 3, container.getBounds().height);
		red.dispose();
		
		// add all tracks
		for (int i = 0; i < annoModel.getNoTracks(); i++) {
			// add label
			Label nameLabel = new Label(container, 0);
			nameLabel.setBounds(0, i*65+5,container.getBounds().width,20);
			nameLabel.setText(annoModel.getTrackName(i));
			labels.add(nameLabel);
			
			// add track
			TrackView trackView = new TrackView(container, SWT.PUSH, annoModel, this);
			trackView.trackNumber = i;
			trackView.annotationModel = annoModel;
			trackView.setBounds(0, i*65+20, container.getBounds().width, 40);
			tracks.add(i, trackView);
			//timeSlider.moveAbove(trackView); // put time slider in foreground
		}
		
	}
	
	private void updateBounds() {
		container.setBounds(0, 0, parent.getBounds().width, parent.getBounds().height);
		int i = 0;
		for (Label label : labels) {
			label.setBounds(0, i*65+5, container.getBounds().width, 20);
			i++;
		}
		i=0;
		for (TrackView trackView : tracks) {
			trackView.setBounds(0, i*65+20, container.getBounds().width, 40);
			i++;
		}
		
		timeSlider.setBounds(this.getTimeLinePos(), 0, 3, container.getBounds().height);
	}
	
	// MARK AnnotationDialog
	
	public AnnotationDialog getAnnotationDialog() {
		if (annotationDialog == null) {
			annotationDialog = new AnnotationDialog(new Shell(), this, annoController);
		}
		return annotationDialog;
	}

	/** 
	 * default openAnnotationDialog method 
	 */
	private void openAnnotationDialog() {
		getAnnotationDialog().setSelectedRange(state.getSelectedRange());
		getAnnotationDialog().setSelectedTrack(getSelectedTrack());
		getAnnotationDialog().setBlockOnOpen(false);
		if (getAnnotationDialog().open() == AnnotationDialog.OK) {
			annotationDialogOpen = true;
		}
	}
	
	// Open by double-click
	public void openAnnotationDialog(int trackNumber, Annotation annotation) {
		getAnnotationDialog().setAnnotation(annotation);
		openAnnotationDialog();
	}

	public void closeAnnotationDialog() {
		annotationDialog = null;
		annotationDialogOpen = false;
		stateObservable.getState().setAnnotationDialogOpen(false);
		stateObservable.updateState();
	}
	
	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State state) {
		this.state = state; // TODO state is already set by reference
		if (state.getAnnotationDialogOpen() && !annotationDialogOpen) {
			openAnnotationDialog();
		}
	}
	
	public void redraw() {
		if(container != null && display != null) {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					if (!disposed)
						container.redraw();
				}
			});
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof State) {
			setState((State) arg);
		}
		redraw();
	}

	@Override
	public long translatePixelToTimestamp(int x) {
		double ratio = (double) state.getVisibleRange().getDistance() / (double) parent.getSize().x;
		long addTs = state.getVisibleRange().getStart();
		return (long) (ratio * x) + addTs;
	}

	private int width;
	@Override
	public int translateTimestampToPixel(long timestamp) {
		
		// TODO fix timestamp bug here
		
//		disp.asyncExec(new Runnable() {
//			public void run() {
//				if (!disposed) {
					width = container.getSize().x;
//				}	
//			}
//		});
		double ratio = (double) width / (double) state.getVisibleRange().getDistance();
		long subTs = timestamp - state.getVisibleRange().getStart();
		return (int) (ratio * subTs);
	}

	@Override
	public void showTracks(Iterable<Long> ids) {
		if(annoModel == null) {
			this.annoModel = new MockAnnotationModel();
		}
		annoModel.showTracks(ids);
		setFocus();
		redraw();
	}

	@Override
	public Iterable<Long> getVisibleTracks() {
		return annoModel.getVisibleTracks();
	}

	@Override
	public void dispose() {
		stateObservable.deleteObserver(this);
		disposed = true;
		super.dispose();
	}

	// MARK Accessors
	
	@Override
	public int getTimeLinePos() {
		return translateTimestampToPixel(state.getTime());
	}

	@Override
	public int getSelectedTrack() {
		return selectedtrack;
	}

	@Override
	public void setOwnID(String id) {
		this.id = id;
	}

	@Override
	public String getOwnID() {
		return id;
	}

	@Override
	public void setDisplayerName(String name) {
		this.setPartName(name);		
	}
	
	/**
	 * @return the selectedAnnotation
	 */
	public HashSet<AnnotationView> getSelectedAnnotations() {
		return selectedAnnotations;
	}

	/**
	 * @param selectedAnnotation the selectedAnnotation to set
	 */
	public void addSelectedAnnotation(AnnotationView selectedAnnotation) {
		selectedAnnotations.add(selectedAnnotation);
	}
	
	public void setSelectedAnnotation(AnnotationView selectedAnnotation) {
		for (AnnotationView av : selectedAnnotations) {
			if (av != null && !av.isDisposed()) {
				av.setSelected(false);
			}
		}
		selectedAnnotations = new HashSet<AnnotationView>();
		addSelectedAnnotation(selectedAnnotation);
	}
}
