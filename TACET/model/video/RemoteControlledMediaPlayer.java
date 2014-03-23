/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package squirrel.model.video;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import squirrel.controller.video.RemoteControlled;
import squirrel.controller.video.RemoteController;
import squirrel.view.video.UpdateView;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.linux.LinuxVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.mac.MacVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.windows.WindowsVideoSurfaceAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.version.LibVlcVersion;

/**
 * The Class RemoteControlledMediaPlayer.
 */
public class RemoteControlledMediaPlayer extends MyMediaPlayer implements
        RemoteControlled {

    private float defaultSpeed = 1;
    private long maxTime;
    private long defaultStartTime = 0;
    private long minDeltaTime = 200;
    private final ScheduledExecutorService executorService = Executors
            .newSingleThreadScheduledExecutor();
    private static RemoteController controller;
    private UpdateView update;

    /**
     * Instantiates a new remote controlled media player which plays the media
     * on a canvas placed in a different JVM.
     *
     * @param canvasId the id of the canvas in the other JVM
     */
    public RemoteControlledMediaPlayer(long canvasId) {
        super();
        configureMP(canvasId);
        update = new UpdateView(mediaPlayer, controller);
        executorService.scheduleAtFixedRate(update, 0L, 1L, TimeUnit.SECONDS);
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

    /**
     * The main method. Creates the connection between the JVMs, and creates the
     * mediaplayer. canvasID is the ID of the canvas to be painted to,
     * mediaPalyerID is the unique identifier of the mediaplayer, solo shows
     * whether the mediaplayer is controlled by a MediaPlayerControlsController
     * (1) or a MainController(0).
     *
     * @param args [int mediaplayerID, boolean solo, long canvasID, ]
     */
    public static void main(String args[]) {
        String controllerName;
        String name;
        new NativeDiscovery().discover();

        try {
            LibVlcVersion.getVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (args[1].equals("1")) {
            controllerName = "Controller" + args[0];
            name = "MediaPlayer" + args[0];
        } else {
            controllerName = "Controller";
            name = "MediaPlayer0" + args[0];
        }

        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            controller = (RemoteController) registry.lookup(controllerName);
            controller.printMsg(name + " Client to " + controllerName + " Server connected");
        } catch (Exception ex) {
            System.err
                    .println("MediaPlayer Client exception: " + ex.toString());
            ex.printStackTrace();
        }

        RemoteControlledMediaPlayer mp = null;

        if(args.length == 3) {
            mp = new RemoteControlledMediaPlayer(Long.parseLong(args[2]));
            try {
                controller.printMsg(args[2]);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {

                mp = new RemoteControlledMediaPlayer(controller.getCanvasID());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        try {
            RemoteControlled stub = (RemoteControlled) UnicastRemoteObject
                    .exportObject(mp, 0);
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind(name, stub);
            controller.printMsg(name + " Server Ready");
        } catch (Exception ex) {
            System.err
                    .println("MediaPlayer Server exception: " + ex.toString());
            ex.printStackTrace();
        }

        try {
            if (args[1].equals("1")) {
                controller.startConnection();
            } else {
                int playerNum = Integer.parseInt(args[0]);
                controller.startConnection(playerNum);
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void setDefaultSpeed(float rate) throws RemoteException {
        this.defaultSpeed = rate;
        mediaPlayer.setRate(defaultSpeed);
    }

    @Override
    public void setDefaultStartTime(long timeStamp) throws RemoteException {
        this.defaultStartTime = timeStamp;
        controller.printMsg(Long.toString(timeStamp));
    }

    @Override
    public void setTime(long timeStamp) throws IOException {
        long calculatedTime = Math.round((timeStamp - defaultStartTime) * defaultSpeed);
        if (calculatedTime > getLength() || timeStamp < 0 || timeStamp > maxTime || calculatedTime < 0) {
            mediaPlayer.stop();
        } else {
            mediaPlayer.setTime(calculatedTime);
        }
    }

    @Override
    public void setPosition(float position) throws RemoteException {
        mediaPlayer.setPosition(position);
    }

    @Override
    public float getPosition() throws RemoteException {
        return mediaPlayer.getPosition();
    }

    @Override
    public void killProcess() throws RemoteException {
        update.setKill(true);
    }

    @Override
    public void nextTS() throws IOException {
        long canlculatedTime =Math.round(mediaPlayer.getTime()/defaultSpeed) + defaultStartTime;
        setTime(canlculatedTime + minDeltaTime - 15);
        mediaPlayer.pause();
    }

    @Override
    public void prevTS() throws IOException {
        long canlculatedTime =Math.round(mediaPlayer.getTime()/defaultSpeed) + defaultStartTime;
        setTime(canlculatedTime - this.minDeltaTime - 15);
        mediaPlayer.pause();
    }

    @Override
    public void setMinDeltaTime(long deltaTime) throws RemoteException {
        this.minDeltaTime = deltaTime;
        controller.printMsg(mediaPath);
    }

    @Override
    public void nextFrame() throws RemoteException {
        float fps = mediaPlayer.getFps();
        long newTime = Math.round(mediaPlayer.getTime() + (1000 / fps) - 15);
        mediaPlayer.setTime(newTime);
        mediaPlayer.pause();
    }

    @Override
    public void previousFrame() throws RemoteException {
        float fps = mediaPlayer.getFps();
        long newTime = Math.round(mediaPlayer.getTime() - (1000 / fps) - 15);
        mediaPlayer.setTime(newTime);
        mediaPlayer.pause();
    }

    @Override
    public boolean isPlaying() throws RemoteException {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void setMaxTime(long maxTime) throws RemoteException {
        this.maxTime = maxTime;
    }

    @Override
    public void restoreMediaPlayer(long canvasID, boolean isPlaying, long timeStamp) throws IOException {
        mediaPlayer.stop();
        mediaPlayer = mpf.newEmbeddedMediaPlayer();
        configureMP(canvasID);
        update = new UpdateView(mediaPlayer, controller);
        executorService.scheduleAtFixedRate(update, 0L, 1L, TimeUnit.SECONDS);
        mediaPlayer.prepareMedia(mediaPath);
        if(isPlaying) {
            mediaPlayer.play();
            setTime(timeStamp);
//        } else {
//            mediaPlayer.play();
//            setTime(timeStamp);
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            mediaPlayer.pause();
        }
    }
}
