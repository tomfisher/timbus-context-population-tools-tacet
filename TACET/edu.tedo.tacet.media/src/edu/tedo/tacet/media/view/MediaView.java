package edu.tedo.tacet.media.view;

import java.awt.Canvas;
import java.awt.Frame;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MediaDatasource;
import edu.teco.tacet.meta.MediaTimeseries;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.observer.IViewComponent;
import edu.teco.tacet.timeseriesview.ITrackDisplayer;
import edu.teco.tacet.track.State;
import edu.teco.tacet.track.StateObservable;
import edu.teco.tacet.track.TrackManager;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class MediaView extends ViewPart implements ITrackDisplayer,
		IViewComponent {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "edu.tedo.tacet.media.views.MediaView";

	private Canvas canvas;
	protected EmbeddedMediaPlayer mediaPlayer;
	protected MediaPlayerFactory mpf;
	private String id;
	private Iterable<Long> ids;
	private String mediaPath;
	private boolean isPlaying = false;

	/**
	 * The constructor.
	 */
	public MediaView() {
		new NativeDiscovery().discover();
		mpf = new MediaPlayerFactory();
		mediaPlayer = mpf.newEmbeddedMediaPlayer();
		StateObservable.getInstance().addObserver(this);
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		Composite videoComposite = new Composite(parent, SWT.EMBEDDED
				| SWT.NO_BACKGROUND);
		Frame videoFrame = SWT_AWT.new_Frame(videoComposite);
		canvas = new Canvas();
		canvas.setBackground(java.awt.Color.black);
		CanvasVideoSurface videoSurface = mpf.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);

		videoFrame.add(canvas);
		videoComposite.setBounds(100, 100, 450, 200);
		videoComposite.setVisible(true);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		if (!TrackManager.isInitialised()) {
			return;
		}
	}

	public void pauseMedia() {
		mediaPlayer.pause();
		isPlaying = false;
	}

	public void stopMedia() {
		mediaPlayer.stop();
		isPlaying = false;
	}

	public void playMedia() {
		isPlaying = true;
		mediaPlayer.play();
		mediaPlayer.parseMedia();
	}

	public void setTime(long timeStamp) {
		if (timeStamp < 0 || timeStamp > getLength()) {
			mediaPlayer.setTime(0);
		} else {
			mediaPlayer.setTime(timeStamp);
		}
	}

	public long getTime() {
		if (mediaPlayer.getTime() < 0) {
			return 0;
		} else {
			return mediaPlayer.getTime();
		}
	}

	public void setVolume(int volume) {
		mediaPlayer.setVolume(volume);
	}

	public void setPlaybackSpeed(double value) {
		mediaPlayer.setRate((float) value);
	}

	public long getLength() {
		return mediaPlayer.getLength();
	}

	public void mute(boolean isMute) {
		mediaPlayer.mute(isMute);
	}

	@Override
	public void showTracks(Iterable<Long> ids) {
		this.ids = ids;
		long mediaTimeseriesId = ids.iterator().next();
		MediaDatasource datasource = null;
		for (Datasource d : TrackManager.getInstance().getCurrentProject()
				.getDatasources()) {
			if (d instanceof MediaDatasource) {
				datasource = (MediaDatasource) d;
				break;
			}
		}
		MediaTimeseries mediaTimeseries = null;
		for (Timeseries t : datasource.getTimeseries()) {
			if (t.getId() == mediaTimeseriesId) {
				mediaTimeseries = (MediaTimeseries) t;
				mediaPath = mediaTimeseries.getFilepath();
			}
		}
		mediaPlayer.prepareMedia(mediaPath);
	}

	@Override
	public Iterable<Long> getVisibleTracks() {
		return ids;
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

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof State) {
			setState((State) arg1);
		} else {
			System.out.println("Whats the Problem?");
		}
	}

	@Override
	public State getState() {
		return StateObservable.getInstance().getState();
	}

	@Override
	public void setState(State state) {
		if (state.isPlaying()) {
			if (!isPlaying)
				playMedia();
		} else {
			if (isPlaying)
				pauseMedia();
		}
		if (state.getTime() == TrackManager.getInstance().getGlobalRange()
				.getStart()
				&& !state.isPlaying()) {
			stopMedia();
		}
		if (Math.abs(Math.abs(state.getTime()
				- TrackManager.getInstance().getGlobalRange().getStart())
				- getTime()) > 300) {
			// System.out.println("Global: " + state.getTime());
			// System.out.println("Vid: " + getTime());
			// System.out.println("=====================================================");
			setTime(state.getTime()
					- TrackManager.getInstance().getGlobalRange().getStart());
		}

		setVolume(state.getVolume());
		setPlaybackSpeed(state.getPlaybackSpeed());

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