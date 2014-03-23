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

package squirrel.view.video;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import net.miginfocom.swing.MigLayout;
import squirrel.controller.video.MainController;
import squirrel.controller.video.MediaPlayerControlsController;

/**
 * The Class MediaPlayerControls. GUI for mediaplayer conrols
 */
@SuppressWarnings("serial")
public class MediaPlayerControls extends JPanel {

    private int mpNumber;

    /** The play button. */
    public JButton playButton;

    /** The stop button. */
    public JButton stopButton;

    /** The next frame button. */
    public JButton next;

    /** The previous frame button. */
    public JButton prev;

    /** The position slider. */
    public JSlider positionSlider;

    /** The time label. */
    public JLabel timeLabel;

    /** The max time. */
    public JLabel maxTime;

    /** The toggle mute button. */
    public JButton toggleMuteButton;

    /** The volume slider. */
    public JSlider volumeSlider;

    private MediaPlayerView mpView;

    /**
     * Instantiates a new media player controls.
     */
    public MediaPlayerControls() {
        createUI();
        this.mpView = mpView;
    }


    /**
     * Instantiates a new media player controls.
     *
     * @param mpNumber the ID of the media player
     */
    public MediaPlayerControls(int mpNumber) {
        this.mpNumber = mpNumber;
        createUI();
        this.mpView = mpView;
    }

    /**
     * Gets the ID of the meida player.
     *
     * @return the ID of the media player
     */
    public int getMpNumber() {
        return mpNumber;
    }

    /**
     * Gets the value of the volumeSlider.
     *
     * @return the volume value
     */
    public int getVolumeValue() {
        return volumeSlider.getValue();
    }

    /**
     * Gets the value of the positionSlider.
     *
     * @return the position value
     */
    public int getPositionValue() {
        return positionSlider.getValue();
    }

    /**
     * Sets the value of the positionSlider.
     *
     * @param position the new position value
     */
    public void setPositionValue(int position) {
        positionSlider.setValue(position);
    }

    /**
     * Sets the value of the volumeSlider.
     *
     * @param position the new position value
     */
    public void setVolumeValue(int volume) {
        volumeSlider.setValue(volume);
    }

    /**
     * Sets the time.
     *
     * @param s the new time
     */
    public void setTime(String s) {
        timeLabel.setText(s);
    }

    /**
     * Sets the max time.
     *
     * @param s the new max time
     */
    public void setMaxTime(String s) {
        maxTime.setText("/ " + s);
    }

    private void createUI() {

        this.setLayout(new MigLayout());

        prev = new JButton("Prev");
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        next = new JButton("Next");

        positionSlider = new JSlider();
        positionSlider.setMaximum(1000);
        positionSlider.setValue(0);
        timeLabel = new JLabel("00:00:00");
        maxTime = new JLabel("/ 00:00:00");
        toggleMuteButton = new JButton("Mute");
        volumeSlider = new JSlider();
        volumeSlider.setMaximum(200);

        this.add(prev);
        this.add(playButton);
        this.add(stopButton);
        this.add(next);
        this.add(positionSlider, "growx, pushx");
        this.add(timeLabel);
        this.add(maxTime);
        this.add(toggleMuteButton);
        this.add(volumeSlider);
    }

    /**
     * Adds listeners to the buttons and sliders.
     *
     * @param mpcc
     */
    public void addListeners(MainController mpcc) {
        playButton.addActionListener(mpcc);
        stopButton.addActionListener(mpcc);
        toggleMuteButton.addActionListener(mpcc);
        positionSlider.addMouseListener(mpcc);
        volumeSlider.addChangeListener(mpcc);
        next.addActionListener(mpcc);
        prev.addActionListener(mpcc);
    }

    /**
     * Adds listeners to the buttons and sliders.
     *
     * @param mpcc
     */
    public void addListeners(MediaPlayerControlsController mpcc) {
        playButton.addActionListener(mpcc);
        stopButton.addActionListener(mpcc);
        toggleMuteButton.addActionListener(mpcc);
        positionSlider.addMouseListener(mpcc);
        volumeSlider.addChangeListener(mpcc);
        next.addActionListener(mpcc);
        prev.addActionListener(mpcc);
    }


    public void setPause() {
        playButton.setLabel("Pause");
    }

    public void setPlay() {
        playButton.setLabel("Play");
    }
}
