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

package squirrel.view.video;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JPanel;

import squirrel.controller.video.MainController;
import squirrel.controller.video.MediaPlayerControlsController;
import squirrel.controller.video.RemoteController;

import com.sun.jna.Native;

/**
 * The Class MediaPlayerView.
 */
@SuppressWarnings("serial")
public class MediaPlayerView extends JPanel {

    private Canvas canvas;
    private MediaPlayerControls controls;
    private MediaPlayerControlsController controller;
    private int mediaPlayerID;

    /**
     * Instantiates a new media player view for solo media player.
     *
     * @param mediaPath the path of the media to play
     * @param mpNumber the id of the media player
     */
    public MediaPlayerView(String mediaPath, int mpNumber) {
        super();
        this.mediaPlayerID = mpNumber;
        setLayout(new BorderLayout());
        canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        canvas.setMinimumSize(new Dimension(600, 250));
        this.add(canvas, BorderLayout.CENTER);
        controls = new MediaPlayerControls();
        controller = new MediaPlayerControlsController(controls, mediaPath, mpNumber, this);
        controls.addListeners(controller);
        this.add(controls, BorderLayout.PAGE_END);

        String name = "Controller" + Integer.toString(mpNumber);

        try {
            RemoteController stub = (RemoteController) UnicastRemoteObject
                    .exportObject(controller, 0);
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind(name, stub);
            System.out.println(name + " Server ready");
        } catch (Exception ex) {
            System.err
                    .println("Controller Server exception: " + ex.toString());
            ex.printStackTrace();
        }
    }

    /**
     * Instantiates a new media player view for synchronized media players.
     *
     * @param controller the MainController
     * @param mpNumber the ID of the media player
     */
    public MediaPlayerView(MainController controller, int mpNumber) {
        super();
        this.mediaPlayerID = mpNumber;
        setLayout(new BorderLayout());
        canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        //canvas.setMinimumSize(new Dimension(600, 250));
        this.add(canvas, BorderLayout.CENTER);
        controls = new MediaPlayerControls(mpNumber);
        controller.addControls(controls);
        controls.addListeners(controller);
        this.add(controls, BorderLayout.PAGE_END);
    }

    /**
     * Gets the canvas id.
     *
     * @return the canvas id
     */
    public long getCanvasId() {
        return Native.getComponentID(canvas);
    }

    /**
     * Terminates the JVM of the mediaplayer.
     *
     * @throws RemoteException the remote exception
     */
    public void killMP() throws RemoteException {
        controller.killMp();
    }

    /**
     * Gets the current time of the media player.
     *
     * @return the current time of the media player
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public long getMpTime() throws IOException {
        return controller.getMpTime();
    }

    /**
     * Gets the media player id.
     *
     * @return the media player id
     */
    public int getMediaPlayerID() {
        return mediaPlayerID;
    }
}
