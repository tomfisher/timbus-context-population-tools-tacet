/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
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
