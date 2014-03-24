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
