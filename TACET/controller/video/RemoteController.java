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

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface RemoteController.
 *
 * Defines the methods to be available for a controller, which controls a remote controllable mediaplayer.
 */
public interface RemoteController extends Remote {

	/**
	 * Start connection with the external JVM of the mediaplayer.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void startConnection() throws RemoteException;

	/**
	 * Updates the current time of the MediaPlayerControls (view) to the current time of the mediaplayer.
	 *
	 * @param s the new time
	 * @throws RemoteException the remote exception
	 */
	public void setTime(long timeStamp) throws RemoteException;

	/**
	 * Updates the current time of the MediaPlayerControls (view) to the max time of the mediaplayer.
	 *
	 * @param s the new max time
	 * @throws RemoteException the remote exception
	 */
	public void setMaxTime(long timeStamp) throws RemoteException;

	/**
	 * Sets the position value based on the duration of the mediafile.
	 *
	 * @param position the new position value
	 * @throws RemoteException the remote exception
	 */
	public void setPositionValue(int position) throws RemoteException;

	/**
	 * Prints the message of the media player.
	 *
	 * @param s the s
	 * @throws RemoteException the remote exception
	 */
	public void printMsg(String s) throws RemoteException;

	/**
	 * Kills the connected external JVMs.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void killMp() throws RemoteException;

    /**
     * Starts the connection with the mediaplayer with the given ID.
     *
     * @param i the i
     * @throws RemoteException the remote exception
     */
    public void startConnection(int i) throws RemoteException;

    public long getCanvasID() throws RemoteException;
}
