package edu.tedo.tacet.media.importmanager;

import java.awt.Canvas;
import java.awt.Frame;
import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

public class MediaImportPage extends WizardPage {

	private Text tPath;
	private Text tName;

	private Composite container;
	private Label lSelect;
	private Label lName;
	private Button bBrowser;
	private boolean fileSelected = false;
	private boolean nameSet = false;
	private EmbeddedMediaPlayer mediaPlayer;
	private MediaPlayerFactory mpf;
	private Composite videoComposite;
	private long length = 0;

	protected MediaImportPage() {
		super("Media Import");
		setTitle("Media file selection");
		setDescription("Please select the Media file to be imported.");
		new NativeDiscovery().discover();
        mpf = new MediaPlayerFactory();
        mediaPlayer = mpf.newEmbeddedMediaPlayer();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		lSelect = new Label(container, SWT.NONE | SWT.SINGLE);
		lSelect.setText("Select File");
		initPathView();
		lName = new Label(container, SWT.NONE | SWT.SINGLE);
		lName.setText("Name");
		tName = new Text(container, SWT.BORDER | SWT.SINGLE);
		tName.setText("");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		tName.setLayoutData(gridData);
		tName.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tName.getText().length() > 0) {
					nameSet = true;
					getWizard().getContainer().updateButtons();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		tPath.setLayoutData(gd);
		
		videoComposite = new Composite(parent, SWT.EMBEDDED);
		Frame videoFrame = SWT_AWT.new_Frame(videoComposite);
		Canvas canvas = new Canvas();
		canvas.setBackground(java.awt.Color.black);
		CanvasVideoSurface videoSurface = mpf.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);
		
		mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventListener() {

			@Override
			public void backward(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void buffering(MediaPlayer arg0, float arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void elementaryStreamAdded(MediaPlayer arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void elementaryStreamDeleted(MediaPlayer arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void elementaryStreamSelected(MediaPlayer arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void endOfSubItems(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void error(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void finished(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void forward(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void lengthChanged(MediaPlayer arg0, long arg1) {
				length = mediaPlayer.getLength();
			}

			@Override
			public void mediaChanged(MediaPlayer arg0, libvlc_media_t arg1,
					String arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaDurationChanged(MediaPlayer arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaFreed(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaMetaChanged(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaParsedChanged(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaStateChanged(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mediaSubItemAdded(MediaPlayer arg0, libvlc_media_t arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void newMedia(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void opening(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void pausableChanged(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void paused(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void playing(MediaPlayer arg0) {
				arg0.pause();
			}

			@Override
			public void positionChanged(MediaPlayer arg0, float arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void scrambledChanged(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void seekableChanged(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void snapshotTaken(MediaPlayer arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void stopped(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void subItemFinished(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void subItemPlayed(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void timeChanged(MediaPlayer arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void titleChanged(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void videoOutput(MediaPlayer arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		videoFrame.add(canvas);
		videoComposite.setVisible(true);
		
		setControl(container);
		setPageComplete(false);
	}

	private void initPathView() {
		tPath = new Text(container, SWT.BORDER | SWT.SINGLE);
		tPath.setText("");
		tPath.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (tPath.getText().length() > 0) {
					fileSelected = true;
					getWizard().getContainer().updateButtons();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		
		bBrowser = new Button(container, SWT.NONE);
		bBrowser.setText("Browse");
		bBrowser.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(container.getShell(), SWT.OPEN);
				dlg.setText("Open");
				String path = dlg.open();
				if (path != null) {
					fileSelected = true;
					tPath.setText(path);
					File f = new File(path);
					String[] name = f.getName().split("\\.");
					tName.setText(name[0]);
					nameSet = true;
					getWizard().getContainer().updateButtons();
				}
			}
		});
	}

	@Override
	public boolean isPageComplete() {
		if (fileSelected == true) {
			File f = new File(tPath.getText());
			if (f.exists() && !f.isDirectory()) {
				if(nameSet) {
					if(length == 0) {
						mediaPlayer.prepareMedia(tPath.getText());
						mediaPlayer.play();
						mediaPlayer.mute(true);
					}
					return true;
				} else {
					setErrorMessage("Please name the selected Media");
				}
			} else {
				setErrorMessage("The file selected does not exist");
			}
		}
		return false;
	}

	public String gettPath() {
		return tPath.getText();
	}
	
	public String getName() {
		return tName.getText();
	}
	
	public long getLength() {
		if(length != 0) {
			return length;
		} else {
			return 1;
		}
	}

}
