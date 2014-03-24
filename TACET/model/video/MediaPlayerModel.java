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

package squirrel.model.video;

import java.io.IOException;

/**
 * The Interface MediaPlayerModel.
 *
 */
public interface MediaPlayerModel {

    /**
     * Pause media.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void pauseMedia() throws IOException;

    /**
     * Stop media.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void stopMedia() throws IOException;

    /**
     * Play media.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void playMedia() throws IOException;

    /**
     * Sets the current time to timeStamp.
     *
     * @param timeStamp the new current time
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void setTime(long timeStamp) throws IOException;

    /**
     * Gets the current time.
     *
     * @return the current time
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public long getTime() throws IOException;

    /**
     * Sets the volume.
     *
     * @param volume the new volume
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void setVolume(int volume) throws IOException;

    /**
     * Mute.
     *
     * @param isMute the is mute
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void mute(boolean isMute) throws IOException;

	/**
	 * Loads the mediafile on the path.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadVid(String path) throws IOException;

	/**
	 * Gets the duration of the mediafile.
	 *
	 * @return the length
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public long getLength() throws IOException;
}
