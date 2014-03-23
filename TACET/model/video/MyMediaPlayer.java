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
import java.rmi.RemoteException;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * The Class MyMediaPlayer.
 */
public class MyMediaPlayer implements MediaPlayerModel {
    protected EmbeddedMediaPlayer mediaPlayer;
    protected String mediaPath;
    protected MediaPlayerFactory mpf;

    /**
     * Instantiates a new my media player.
     */
    public MyMediaPlayer() {
        mpf = new MediaPlayerFactory();
        mediaPlayer = mpf.newEmbeddedMediaPlayer();
    }

    @Override
    public void pauseMedia() throws IOException {
        mediaPlayer.pause();
    }

    @Override
    public void stopMedia() throws IOException {
        mediaPlayer.stop();
    }

    @Override
    public void playMedia() throws IOException {
        mediaPlayer.play();
    }

    @Override
    public void setTime(long timeStamp) throws IOException {
        if (timeStamp < 0 || timeStamp > getLength()) {
            mediaPlayer.setTime(0);
        } else {
            mediaPlayer.setTime(timeStamp);
        }
    }

    @Override
    public long getTime() throws IOException {
        if (mediaPlayer.getTime() < 0) {
            return 0;
        } else {
            return mediaPlayer.getTime();
        }
    }

    @Override
    public void setVolume(int volume) throws IOException {
        mediaPlayer.setVolume(volume);
    }

    @Override
    public void loadVid(String path) throws RemoteException {
        mediaPath = path;
        mediaPlayer.prepareMedia(path);
    }

    @Override
    public long getLength() throws IOException {
        return mediaPlayer.getLength();
    }

    @Override
    public void mute(boolean isMute) throws IOException {
        mediaPlayer.mute(isMute);
    }
}
