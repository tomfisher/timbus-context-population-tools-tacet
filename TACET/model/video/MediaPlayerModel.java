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
