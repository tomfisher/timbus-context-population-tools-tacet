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
