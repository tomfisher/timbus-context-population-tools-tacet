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

package squirrel.view.video;

import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import squirrel.controller.video.RemoteController;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * The Class Update.
 *
 * Updates the view (MediaPlayerControls) based on the progress of the mediaplayer.
 */
public final class UpdateView implements Runnable {

    private final MediaPlayer mp;
    private final RemoteController mpc;
    private boolean kill = false;

    private boolean maxTimeSet = false;

    /**
     * Instantiates a new update.
     *
     * @param mediaPlayer the media player
     * @param mpc the RemoteController wich controlls the mediaplayer
     */
    public UpdateView(MediaPlayer mediaPlayer, RemoteController mpc) {
        this.mp = mediaPlayer;
        this.mpc = mpc;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (!kill) {
            final int position = (int) (mp.getPosition() * 1000.0f);
            final long time = mp.getTime();
            final long length = mp.getLength();
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (mp.isPlaying()) {
                        try {
                            updateTime(time);
                            updatePosition(position);
                            if (!maxTimeSet) {
                                updateMaxTime(length);
                                maxTimeSet = true;
                            }
                        } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mp.release();
            System.exit(0);
        }
    }

    private void updateTime(long time) throws RemoteException {
        mpc.setTime(time);
    }

    private void updateMaxTime(long time) throws RemoteException {
        mpc.setMaxTime(time);
    }

    private void updatePosition(int value) throws RemoteException {
        mpc.setPositionValue(value);
    }

    /**
     * Sets the kill flag, which ensures the termination of the JVM.
     *
     * @param kill the new kill
     */
    public void setKill(boolean kill) {
        this.kill = kill;
    }
}
