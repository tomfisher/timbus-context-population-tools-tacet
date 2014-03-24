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
