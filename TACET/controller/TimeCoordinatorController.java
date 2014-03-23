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

package squirrel.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import squirrel.controller.video.MainController;
import squirrel.model.TimeCoordinator;
import squirrel.view.TimeCoordinatorView;

public class TimeCoordinatorController implements ActionListener, MouseListener {
    private List<TimeCoordinatorView> controls;
    private TimeCoordinator timeCoordinator;
    private MainController mainController;
    private boolean mainControllerSet = false;
    private boolean mousePressedPlaying = false;

    public TimeCoordinatorController(TimeCoordinator timeCoordinator) {
        this.timeCoordinator = timeCoordinator;
        controls = new ArrayList<TimeCoordinatorView>();
    }

    public void addControls(TimeCoordinatorView tcv) {
        this.controls.add(tcv);
        timeCoordinator.addListener(tcv);
    }

    public void addMainController(MainController mainController) {
        this.mainController = mainController;
        this.mainControllerSet = true;
        timeCoordinator.addListener(mainController);
    }

    public long getMaxTime() {
        return timeCoordinator.getMaxTime();
    }

    public long getTime() {
        return timeCoordinator.getTime();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        for (TimeCoordinatorView tcv : controls) {
            if (arg0.getSource() == tcv.positionSlider) {
                if (timeCoordinator.isPlaying()) {
                    mousePressedPlaying = true;
                    timeCoordinator.pause();
                    if (mainControllerSet) {
                        try {
                            mainController.pauseAll();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    mousePressedPlaying = false;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        for (TimeCoordinatorView tcv : controls) {
            if (arg0.getSource() == tcv.positionSlider) {
                setSliderBasedPosition(tcv);
            }
        }
    }

    private void setSliderBasedPosition(TimeCoordinatorView tcv) {
        timeCoordinator.setTime(tcv.getPositionValue());
        if (mainControllerSet) {
            try {
                mainController.setTimeAll(timeCoordinator.getTime());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        updatePosition();
        if (mousePressedPlaying) {
            timeCoordinator.play();
            if (mainControllerSet) {
                try {
                    mainController.playAll();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private void updatePosition() {
        for (TimeCoordinatorView tcv : controls) {
            tcv.setPositionValue(timeCoordinator.getTime());
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        for (TimeCoordinatorView tcv : controls) {
            try {
                if (arg0.getSource() == tcv.playButton) {
                    if (timeCoordinator.isPlaying()) {
                        timeCoordinator.pause();
                        if (mainControllerSet) {
                            mainController.pauseAll();
                        }
                    } else {
                        timeCoordinator.play();
                        if (mainControllerSet) {
                            mainController.playAll();
                        }
                    }
                } else if (arg0.getSource() == tcv.stopButton) {
                    timeCoordinator.stop();
                    if (mainControllerSet) {
                        mainController.stopAll();
                    }
                } else if (arg0.getSource() == tcv.next) {
                    timeCoordinator.pause();
                    timeCoordinator.nextStep();
                    if (mainControllerSet) {
                        mainController.nextAll();
                    }
                } else if (arg0.getSource() == tcv.prev) {
                    timeCoordinator.pause();
                    timeCoordinator.prevStep();
                    if (mainControllerSet) {
                        mainController.prevAll();
                    }
                } else if (arg0.getSource() == tcv.toggleMuteButton) {
                    if (mainControllerSet) {
                        if (mainController.isMute()) {
                            mainController.unMuteAll();
                        } else {
                            mainController.muteAll();
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
