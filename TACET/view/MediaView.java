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

package squirrel.view;

import java.net.URISyntaxException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import squirrel.controller.video.MainController;
import squirrel.view.video.MediaPlayerView;
import bibliothek.gui.dock.common.DefaultSingleCDockable;

public class MediaView extends DefaultSingleCDockable{

    private JTabbedPane tabbedPane;
    private MediaPlayerView[] mediaPlayers;
    private MainController mainController;

    /**
     * Constructor of MediaView
     * @param masterGui - link to main window
     * @throws URISyntaxException
     */
    public MediaView(Gui masterGui, MainController mainController){
        super("MediaView", "MediaView", new JPanel());
        this.mainController = mainController;

        mediaPlayers = new MediaPlayerView[mainController.getNoOfPlayers()];

        tabbedPane = new JTabbedPane();
        for (int i = 0; i < mainController.getNoOfPlayers(); i++) {
            mediaPlayers[i] = new MediaPlayerView(mainController, i);
            tabbedPane.addTab("Video " + Integer.toString(i + 1), mediaPlayers[i]);
        }
        this.add(tabbedPane);
    }

    /**
     *
     * @return array of CanvasIDs  of media player
     */
    public long[] getCanvasIDs() {
        long[] canvasIDs = new long[mediaPlayers.length];
        for (int i = 0; i < mediaPlayers.length; i++) {
            canvasIDs[i] = mediaPlayers[i].getCanvasId();
        }
        return canvasIDs;
    }

    /**
     * Restarts Mediaplayer
     *
     * e.g. When Mediaplayer is docked on other position
     */
    public void reloadMediaPlayers() {
        mainController.restoreMediaPlayer(getCanvasIDs());
    }

    public boolean isConnected() {
        return mainController.isConnected();
    }

    public boolean isEnalrged() {
        return tabbedPane.isDisplayable();
    }

}
