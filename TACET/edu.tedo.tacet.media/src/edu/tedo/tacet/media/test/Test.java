package edu.tedo.tacet.media.test;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.linux.LinuxVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.mac.MacVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.windows.WindowsVideoSurfaceAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;

public class Test {
	// Create a media player factory

    // Create a new media player instance for the run-time platform
    private EmbeddedMediaPlayer mediaPlayer;

    private JPanel panel;
    private Canvas canvas;
    private JFrame frame;

    //Constructor
    public Test(String url){

        //Creating a panel that while contains the canvas
        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        

        MediaPlayerFactory mpf = new MediaPlayerFactory();
        mediaPlayer = mpf.newEmbeddedMediaPlayer();
		canvas = new Canvas();
		canvas.setBackground(java.awt.Color.black);
		//CanvasVideoSurface videoSurface = mpf.newVideoSurface(canvas);
		//mediaPlayer.setVideoSurface(videoSurface);
		
        //Creating the canvas and adding it to the panel :

        panel.add(canvas, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();

        //Creation a media player :

        //Construction of the jframe :
        frame = new JFrame("Demo with Canvas AWT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.setSize(700, 500);

        //Adding the panel to the 
        frame.add(panel);
        frame.setVisible(true);
        

		configureMP(Native.getComponentID(canvas));

        //Playing the video
        mediaPlayer.prepareMedia(url);
        mediaPlayer.play();


    }
    
    private void configureMP(long canvasId) {
        VideoSurfaceAdapter videoSurfaceAdapter = null;

        if (RuntimeUtil.isNix()) {
            videoSurfaceAdapter = new LinuxVideoSurfaceAdapter();
        } else if (RuntimeUtil.isWindows()) {
            videoSurfaceAdapter = new WindowsVideoSurfaceAdapter();
        } else if (RuntimeUtil.isMac()) {
            videoSurfaceAdapter = new MacVideoSurfaceAdapter();
        }

        videoSurfaceAdapter.attach(LibVlc.INSTANCE, mediaPlayer, canvasId);
    }
    //Main function :
    public static void main(String[] args) {
    	new NativeDiscovery().discover();
    	
        final String url = "C:\\b.avi";

        new Test(url);

    }
}
