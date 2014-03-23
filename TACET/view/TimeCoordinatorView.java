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

import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import net.miginfocom.swing.MigLayout;
import squirrel.controller.TimeCoordinatorController;
import squirrel.model.TimeListener;

@SuppressWarnings("serial")
public class TimeCoordinatorView extends JPanel implements TimeListener {
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

    long maximumTime;

    public TimeCoordinatorView(TimeCoordinatorController tcc) {
        this.maximumTime = tcc.getMaxTime();
        tcc.addControls(this);
        createUI();
        addListeners(tcc);
    }

    private void addListeners(TimeCoordinatorController tcc) {
        playButton.addActionListener(tcc);
        playButton.setMnemonic(' ');
        stopButton.addActionListener(tcc);
        stopButton.setMnemonic('S');
        toggleMuteButton.addActionListener(tcc);
        toggleMuteButton.setMnemonic('T');
        positionSlider.addMouseListener(tcc);
        next.addActionListener(tcc);
        next.setMnemonic('N');
        prev.addActionListener(tcc);
        prev.setMnemonic('P');
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
        maxTime = new JLabel();
        setMaxTime();
        toggleMuteButton = new JButton("Mute");

        this.add(prev);
        this.add(playButton);
        this.add(stopButton);
        this.add(next);
        this.add(positionSlider, "growx, pushx");
        this.add(timeLabel);
        this.add(maxTime);
        this.add(toggleMuteButton);
    }

    public void setTime(long time) {
        timeLabel.setText(convertTime(time));
    }

    public void setMaxTime() {
        maxTime.setText("/ " + convertTime(maximumTime));
    }

    public long getPositionValue() {
        int position = positionSlider.getValue();
        long timeStamp = Math.round(((float)position / 1000.0) * maximumTime);
        return timeStamp;
    }

    public void setPositionValue(long timeStamp) {
        int position = Math.round((((float) timeStamp / (float) maximumTime) * 1000));
        positionSlider.setValue(position);
    }

    private String convertTime(long time) {
        String s = String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                                .toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                .toMinutes(time)));
        return s;
    }

    @Override
    public void timeChanged(long timeStamp) {
        setPositionValue(timeStamp);
        setTime(timeStamp);
    }

    @Override
    public void setPause() {
        this.playButton.setLabel("Pause");
    }

    @Override
    public void setPlay() {
        this.playButton.setLabel("Play");
    }

}
