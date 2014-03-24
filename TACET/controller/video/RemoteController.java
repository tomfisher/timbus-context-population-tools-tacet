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
