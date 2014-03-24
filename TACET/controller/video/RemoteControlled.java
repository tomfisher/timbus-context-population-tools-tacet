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

package squirrel.controller.video;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import squirrel.model.video.MediaPlayerModel;

/**
 * The Interface RemoteControlled.
 *
 * Defines the methods to be available in a remotely controlled mediaplayer.
 */
public interface RemoteControlled extends Remote, MediaPlayerModel {

    /**
     * Sets the default speed ratio for the mediaplayer to play the media.
     *
     * @param rate the default speed ratio
     * @throws RemoteException the remote exception
     */
    public void setDefaultSpeed(float rate) throws RemoteException;

    /**
     * Sets the default start time.
     *
     * @param TimeStamp the default start time
     * @throws RemoteException the remote exception
     */
    public void setDefaultStartTime(long TimeStamp) throws RemoteException;

    /**
     * Sets the position (time) of the mediaplayer regarding the duration of the media.
     *
     * @param position the new position
     * @throws RemoteException the remote exception
     */
    public void setPosition(float position) throws RemoteException;

    /**
     * Gets the position (time) of the mediaplayer regarding the duration of the media.
     *
     * @return the position
     * @throws RemoteException the remote exception
     */
    public float getPosition() throws RemoteException;

    /**
     * Terminates the process.
     *
     * @throws RemoteException the remote exception
     */
    public void killProcess() throws RemoteException;

    /**
     * Sets the minimal time to skip.
     *
     * @param deltaTime the new minimal delta time
     * @throws RemoteException the remote exception
     */
    public void setMinDeltaTime(long deltaTime) throws RemoteException;

    /**
     * Jumps the minimal jumpable time forward.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void nextTS() throws IOException;

    /**
     * Jumps the minimal jumpable time backward.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void prevTS() throws IOException;

    /**
     * Next frame.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void nextFrame() throws IOException;

    /**
     * Previous frame.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void previousFrame() throws IOException;

    /**
     * Checks if is playing.
     *
     * @return true, if is playing
     * @throws RemoteException the remote exception
     */
    public boolean isPlaying() throws RemoteException;

    /**
     * Sets the max time.
     *
     * @param maxTime the new max time
     * @throws RemoteException the remote exception
     */
    public void setMaxTime(long maxTime) throws RemoteException;

    /**
     * Restore media player to the given properties.
     *
     * @param canvasID the canvas id
     * @param isPlaying the is playing
     * @param timeStamp the time stamp
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void restoreMediaPlayer(long canvasID, boolean isPlaying, long timeStamp) throws IOException;
}
