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

package squirrel.controller.video;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import squirrel.model.TimeCoordinator;
import squirrel.model.TimeListener;
import squirrel.view.video.MediaPlayerControls;

/**
 * The Class MainController. Controls every RemoteControlledMediaPlayer in
 * external JVMs.
 */
public class MainController implements RemoteController, ActionListener,
        MouseListener, ChangeListener, TimeListener {

    private static long epsilon = 500;
    private String[] mediaPaths;
    private RemoteControlled[] mediaPlayers;
    private long startTimes[];
    private float videoSpeed[];
    private List<MediaPlayerControls> controls;
    private boolean mousePressedPlaying = false;
    private int numberOfPlayers;
    private boolean isPlaying = false;
    private boolean isMute = false;
    private long maxTime;
    private long minDeltaTime;
    private long actualTime = 0;
    private int noOfConMp = 0;
    private TimeCoordinator timeCoordinator;

    /**
     * Instantiates a new main controller.
     *
     * @param mpc the GUI for mediaplayer conrols
     * @param numberOfPlayers the number of mediaplayers
     * @param maxTime the maximal timestamp of sensordata
     * @param minDeltaTime the minimal time to skip
     */
    public MainController(String[] mediaPaths, TimeCoordinator tc, long[] startTimes,
            float[] videoSpeed) {
        this.numberOfPlayers = mediaPaths.length;
        this.mediaPlayers = new RemoteControlled[numberOfPlayers];
        this.minDeltaTime = tc.getMinimalDeltaTime();
        this.maxTime = tc.getMaxTime();
        this.mediaPaths = mediaPaths;
        this.controls = new ArrayList<MediaPlayerControls>();
        this.timeCoordinator = tc;
        this.startTimes = startTimes;
        this.videoSpeed = videoSpeed;
        try {
            RemoteController stub = (RemoteController) UnicastRemoteObject
                    .exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind("Controller", stub);
            System.out.println("MainController Server ready");
        } catch (Exception ex) {
            System.err
                    .println("Controller Server exception: " + ex.toString());
            ex.printStackTrace();
        }
    }

    /**
     * Gets the number of media players.
     *
     * @return the no of players
     */
    public int getNoOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Starts the connection between the mediaplayers and the MainController.
     */
    @Override
    public void startConnection(int i) {
        String name = "MediaPlayer0" + Integer.toString(i);
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            mediaPlayers[i] = (RemoteControlled) registry.lookup(name);
            mediaPlayers[i].loadVid(mediaPaths[i]);
            mediaPlayers[i].setMinDeltaTime(minDeltaTime);
            mediaPlayers[i].setDefaultStartTime(startTimes[i]);
            mediaPlayers[i].setDefaultSpeed(videoSpeed[i]);
            mediaPlayers[i].setMaxTime(maxTime);
            System.out
                    .println("MainController connected to MediaPlayer" + Integer.toString(i));
            noOfConMp++;
        } catch (Exception ex) {
            System.err
                    .println("MediaPlayer Client exception: " + ex.toString());
            ex.printStackTrace();
        }
    }

    /**
     * Checks if is mute.
     *
     * @return true, if is mute
     */
    public boolean isMute() {
        return isMute;
    }

    /**
     * Terminates all external JVMs of the mediaplayers.
     *
     * @throws RemoteException the remote exception
     */
    @Override
    public void killMp() throws RemoteException {
        for (int i = 0; i < numberOfPlayers; i++) {
            mediaPlayers[i].killProcess();
        }
    }

    /**
     * Play all.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void playAll() throws IOException {
        isPlaying = true;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (startTimes[i] <= actualTime)
                mediaPlayers[i].playMedia();
        }
    }

    /**
     * Pause all.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void pauseAll() throws IOException {
        isPlaying = false;
        for (int i = 0; i < numberOfPlayers; i++) {
            mediaPlayers[i].pauseMedia();
        }
    }

    /**
     * Sets the time of all mediaPlayers to timeStamp.
     *
     * @param timeStamp the new time to set to
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void setTimeAll(long timeStamp) throws IOException {
        for (int i = 0; i < numberOfPlayers; i++) {
            mediaPlayers[i].setTime(timeStamp);
        }
    }

    /**
     * Mute all.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void muteAll() throws IOException {
        isMute = false;
        for (int i = 0; i < numberOfPlayers; i++) {
            mediaPlayers[i].mute(true);
        }
    }

    /**
     * Unmute all.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void unMuteAll() throws IOException {
        isMute = true;
        for (int i = 0; i < numberOfPlayers; i++) {
            mediaPlayers[i].mute(false);
        }
    }

    /**
     * Every mediaplayer jumps minDeltaTmie forward.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void nextAll() throws IOException {
        pauseAll();
        mousePressedPlaying = false;
        for (int i = 0; i < numberOfPlayers; i++) {
            mediaPlayers[i].nextTS();
        }
        updateUIState();
    }

    /**
     * Every mediaplayer jumps minDeltaTmie backward.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void prevAll() throws IOException {
        pauseAll();
        mousePressedPlaying = false;
        for (int i = 0; i < numberOfPlayers; i++) {
            mediaPlayers[i].prevTS();
        }
        updateUIState();
    }

    /**
     * Stop all.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void stopAll() throws IOException {
        isPlaying = false;
        for (int i = 0; i < numberOfPlayers; i++) {
            mediaPlayers[i].stopMedia();
        }
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        for (MediaPlayerControls mpc : controls) {
            if (arg0.getSource() == mpc.volumeSlider) {
                if (isMute) {
                    isMute = false;
                    try {
                        mediaPlayers[mpc.getMpNumber()].mute(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    mediaPlayers[mpc.getMpNumber()].setVolume(mpc.getVolumeValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {}

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent arg0) {
        for (MediaPlayerControls mpc : controls) {
            if (arg0.getSource() == mpc.positionSlider) {
                if (isPlaying) {
                    mousePressedPlaying = true;
                    timeCoordinator.pause();
                    try {
                        pauseAll();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mousePressedPlaying = false;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        for (MediaPlayerControls mpc : controls) {
            if (arg0.getSource() == mpc.positionSlider) {
                setSliderBasedPosition(mpc);
                updateUIState();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        for (MediaPlayerControls mpc : controls) {
            try {
                if (arg0.getSource() == mpc.playButton) {
                    if (!isPlaying) {
                        playAll();
                        timeCoordinator.play();
                    } else {
                        pauseAll();
                        timeCoordinator.pause();
                    }
                } else if (arg0.getSource() == mpc.toggleMuteButton) {
                    if (!isMute) {
                        muteAll();
                    } else {
                        unMuteAll();
                    }
                } else if (arg0.getSource() == mpc.next) {
                    nextAll();
                    timeCoordinator.pause();
                    timeCoordinator.nextStep();
                } else if (arg0.getSource() == mpc.prev) {
                    prevAll();
                    timeCoordinator.pause();
                    timeCoordinator.prevStep();
                } else if (arg0.getSource() == mpc.stopButton) {
                    stopAll();
                    timeCoordinator.stop();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setSliderBasedPosition(MediaPlayerControls mpc) {
        float positionValue = mpc.positionSlider.getValue() / 1000.0f;
        long newActualTime = (long) (positionValue * maxTime);
        try {
            setPlayerPosition(newActualTime);
            timeCoordinator.setTime(newActualTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPlayerPosition(long timeStamp) throws IOException {
        try {
            setTimeAll(timeStamp);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        updateUIState();
    }

    private void updateUIState() {
        if (mousePressedPlaying) {
            timeCoordinator.play();
            try {
                playAll();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            try {
                playAll();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {}
            try {
                pauseAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isPlaying = false;
        }
    }

    @Override
    public void setTime(long timeStamp) throws RemoteException {
        for (MediaPlayerControls mpc : controls) {
            mpc.setTime(convertTime(timeStamp));
        }
    }

    @Override
    public void setMaxTime(long timeStamp) throws RemoteException {}

    @Override
    public void setPositionValue(int position) throws RemoteException {
        for (MediaPlayerControls mpc : controls) {
            mpc.setPositionValue((int) (((float) actualTime / maxTime) * 1000));
        }
    }

    @Override
    public void printMsg(String s) throws RemoteException {
        System.err.println(s);

    }

    @Override
    public void startConnection() throws RemoteException {

    }

    /**
     * Adds the controls to be controlled by the MainController.
     *
     * @param controls the controls
     */
    public void addControls(MediaPlayerControls controls) {
        this.controls.add(controls);
        controls.setMaxTime(convertTime(maxTime));
    }

    /**
     * Maintains the synchronization of the Media Players and the
     * TimeCoordinator
     */
    @Override
    public void timeChanged(long timeStamp) {
        actualTime = timeStamp;
        if (actualTime >= maxTime) {
            try {
                pauseAll();
                isPlaying = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            setTime(timeStamp);
            setPositionValue(0);
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (actualTime % (epsilon * 2) == 0) {
            if (noOfConMp == mediaPlayers.length) {
                for (int i = 0; i < mediaPlayers.length; i++) {
                    try {
                        if (mediaPlayers[i].isPlaying()) {
                            long assumedTime;
                            assumedTime =
                                    (long) ((float) mediaPlayers[i].getTime() / videoSpeed[i])
                                            + startTimes[i];
                            if (Math.abs(actualTime - assumedTime) > epsilon) {
                                mediaPlayers[i].setTime(timeCoordinator.getTime());
                                if (!isPlaying) {
                                    mediaPlayers[i].playMedia();
                                    Thread.sleep(5);
                                    mediaPlayers[i].pauseMedia();
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (isPlaying) {
                for (int i = 0; i < mediaPlayers.length; i++) {
                    try {
                        if (!mediaPlayers[i].isPlaying()) {
                            if (actualTime >= startTimes[i]) {
                                mediaPlayers[i].playMedia();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (int i = 0; i < mediaPlayers.length; i++) {
                    try {
                        if (mediaPlayers[i].isPlaying()) {
                            mediaPlayers[i].pauseMedia();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String convertTime(long time) {
        String s = String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                                .toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                .toMinutes(time)));
        return s;
    }

    /**
     * Restores media player.
     *
     * @param mediaPlayerID the media player id
     * @param canvasID the canvas id
     */
    public void restoreMediaPlayer(long[] canvasID) {
        for (int i = 0; i < numberOfPlayers; i++) {
            try {
                mediaPlayers[i].restoreMediaPlayer(canvasID[i], isPlaying, actualTime);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            setTimeAll(actualTime);
            if (isPlaying) {
                playAll();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setStartTimes(long[] startTimes) {
        this.startTimes = startTimes;
        for (int i = 0; i < numberOfPlayers; i++) {
            try {
                System.out.println(Long.toString(startTimes[i]));
                mediaPlayers[i].setDefaultStartTime(startTimes[i]);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void setVideoSpeed(float[] videoSpeed) {
        this.videoSpeed = videoSpeed;
        for (int i = 0; i < numberOfPlayers; i++) {
            try {
                mediaPlayers[i].setDefaultSpeed(videoSpeed[i]);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public long getCanvasID() throws RemoteException {
        // TODO Auto-generated method stub
        return 0;
    }

    public boolean isConnected() {
        return numberOfPlayers == noOfConMp;
    }

    @Override
    public void setPlay() {
        for (MediaPlayerControls mpc : controls) {
            mpc.setPlay();
        }
    }

    @Override
    public void setPause() {
        for (MediaPlayerControls mpc : controls) {
            mpc.setPause();
        }
    }
}
