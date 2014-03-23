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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeUnit;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import squirrel.view.video.MediaPlayerControls;
import squirrel.view.video.MediaPlayerView;

/**
 * The Class MediaPlayerControlsController. Controls one
 * RemoteControlledMediaPlayer in a JVMs.
 */
public class MediaPlayerControlsController implements ActionListener,
        MouseListener, ChangeListener, RemoteController {

    private RemoteControlled mediaPlayer;
    private MediaPlayerControls mpc;
    private boolean mousePressedPlaying = false;
    private String mediaPath;
    private int mpNumber;
    private MediaPlayerView mpView;
    private boolean isPlaying = false;
    private boolean isMute = false;

    /**
     * Instantiates a new media player controls controller.
     *
     * @param mpc the GUI for mediaplayer conrols
     * @param mediaPath the path of the mediafile to play
     * @param mpNumber the id of the mediaPlayer
     */
    public MediaPlayerControlsController(MediaPlayerControls mpc, String mediaPath, int mpNumber,
            MediaPlayerView mpView) {
        super();
        this.mpc = mpc;
        this.mediaPath = mediaPath;
        this.mpNumber = mpNumber;
        this.mpView = mpView;
    }

    @Override
    public void startConnection() throws RemoteException {
        String name = "MediaPlayer" + Integer.toString(mpNumber);
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            mediaPlayer = (RemoteControlled) registry.lookup(name);
            loadVid();
            System.out
                    .println("Controller" + Integer.toString(mpNumber) + " Client to " + name
                            + " Server connected");
        } catch (Exception ex) {
            System.err
                    .println("MediaPlayer Client exception: " + ex.toString());
            ex.printStackTrace();
        }
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        if (arg0.getSource() == mpc.volumeSlider) {
            if (isMute) {
                isMute = false;
                try {
                    mediaPlayer.mute(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                mediaPlayer.setVolume(mpc.getVolumeValue());
            } catch (IOException e) {
                e.printStackTrace();
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
        if (isPlaying) {
            mousePressedPlaying = true;
            isPlaying = false;
            try {
                mediaPlayer.pauseMedia();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mousePressedPlaying = false;
        }
        setSliderBasedPosition();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        setSliderBasedPosition();
        updateUIState();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == mpc.playButton) {
            try {
                if (!isPlaying) {
                    isPlaying = true;
                    mediaPlayer.playMedia();
                    mpc.setPause();
                } else {
                    isPlaying = false;
                    mediaPlayer.pauseMedia();
                    mpc.setPlay();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg0.getSource() == mpc.stopButton) {
            try {
                isPlaying = false;
                mediaPlayer.stopMedia();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg0.getSource() == mpc.toggleMuteButton) {
            try {
                mediaPlayer.mute(!isMute);
                isMute = !isMute;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg0.getSource() == mpc.next) {
            try {
                isPlaying = false;
                mediaPlayer.pauseMedia();
                mediaPlayer.nextFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg0.getSource() == mpc.prev) {
            try {
                isPlaying = false;
                mediaPlayer.pauseMedia();
                mediaPlayer.previousFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setSliderBasedPosition() {
        float positionValue = (float) mpc.getPositionValue() / 1000.0f;
        try {
            setPlayerPosition(positionValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPlayerPosition(float pos) throws IOException {
        if (pos > 0.99f) {
            pos = 0.99f;
        }
        try {
            mediaPlayer.setPosition(pos);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        updateUIState();
    }

    private void updateUIState() {
        if (!isPlaying) {
            try {
                isPlaying = true;
                mediaPlayer.playMedia();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (!mousePressedPlaying) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                try {
                    isPlaying = false;
                    mediaPlayer.pauseMedia();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        int position = 0;
        try {
            position = (int) (mediaPlayer.getPosition() * 1000.0f);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        updatePosition(position);
    }

    private void updatePosition(int value) {
        mpc.setPositionValue(value);
    }

    private void loadVid() throws IOException {
        mediaPlayer.loadVid(mediaPath);
    }

    /**
     * Terminates the external JVM of the mediaplayer.
     *
     * @throws RemoteException the remote exception
     */
    @Override
    public void killMp() throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(1099);
        try {
            registry.unbind("Controller" + Integer.toString(mpNumber));
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mediaPlayer.killProcess();
        try {
            registry.unbind("MediaPlayer" + Integer.toString(mpNumber));
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void setTime(long timeStamp) throws RemoteException {
        mpc.setTime(convertTime(timeStamp));
    }

    @Override
    public void setMaxTime(long timeStamp) throws RemoteException {
        mpc.setMaxTime(convertTime(timeStamp));
    }

    @Override
    public void setPositionValue(int position) throws RemoteException {
        mpc.setPositionValue(position);
    }

    @Override
    public void printMsg(String s) throws RemoteException {
        System.err.println(s);
    }

    @Override
    public void startConnection(int i) throws RemoteException {}

    /**
     * Gets the current time of the meida player.
     *
     * @return the mp time
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public long getMpTime() throws IOException {
        return mediaPlayer.getTime();
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

    @Override
    public long getCanvasID() {
        return mpView.getCanvasId();
    }
}
