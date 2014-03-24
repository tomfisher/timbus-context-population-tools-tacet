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

package squirrel.controller;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.security.CodeSource;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import squirrel.controller.video.MainController;
import squirrel.controller.video.SynchronisationController;
import squirrel.model.AnnotationModelImpl;
import squirrel.model.ModelFacade;
import squirrel.model.TimeCoordinator;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.model.video.SyncData;
import squirrel.view.AboutView;
import squirrel.view.Gui;
import squirrel.view.ImportWizard.ImportWizard;
import squirrel.view.video.SynchronisationView;


public class MasterController {
    /**
     * Main View Component
     */
    private Gui gui;
    private ImportController importController;
    private TimeCoordinator timeCoordinator;
    private SyncData syncData;
    private TimeCoordinatorController timeCoordinatorController;
    private MainController mainController;
    private boolean mediaToPlay = false;
    private ProjectController projectController;
      private ExportController exportController;
    private ClassificatorController classificatorController;

    /**
     * Actions of Menu
     */
    private Action importAction;
    private Action synchronizeAction;
    private Action classifyAction;
    private Action stopClassificationAction;
    private Action startClassificationAction;
    private Action exitAction;
    private Action saveAction;
    private Action loadAction;
    private Action exportAction;
    private Action statisticsAction;
    private Action aboutAction;
    private boolean imported = false;

    private ModelFacade model;

    /**
     * Constructor - inits the whole programm
     */
    public MasterController(ImportController importController) {
        if (importController == null)
            this.importController = new ImportController(this);
        else
            this.importController = importController;
        timeCoordinator = new TimeCoordinator();
        timeCoordinatorController = new TimeCoordinatorController(timeCoordinator);
        projectController = new ProjectController(this);
        exportController = new ExportController(this);
    }

    /**
     * Default constructor
     */
    public MasterController() {
        this(null);
    }

    /**
     * Returns an instance of ModelFacade representing model of mvc
     * @return
     */
    public ModelFacade getModel() {
        return model;
    }

    /**
     * Inits the JMenuBar of the Main Window and adds its functionality
     */
    private void initJMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        JMenu classifyMenu = new JMenu("Classify");
        menuBar.add(classifyMenu);

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        aboutAction = new AbstractAction("About") {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                AboutView av = new AboutView();
                av.showAsDialog(gui);

            }
        };
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(aboutAction);
        helpMenu.add(about);


        importAction = new AbstractAction("Import") {
            @Override
            public void actionPerformed(ActionEvent e) {
                importController.presentDialog(gui);
            }
        };
        JMenuItem importProject = new JMenuItem(importAction);
        importProject.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_I,
                java.awt.Event.CTRL_MASK));
        fileMenu.add(importProject);

        exportAction = new AbstractAction("Export") {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                exportController.presentDialog(gui);
            }
        };

        JMenuItem exportProject = new JMenuItem(exportAction);
        exportProject.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_E,
                java.awt.Event.CTRL_MASK));
        fileMenu.add(exportProject);
        exportAction.setEnabled(false);

        saveAction = new AbstractAction("Save Project") {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                projectController.presentDialogSave(gui);
            }
        };

        JMenuItem saveProject = new JMenuItem(saveAction);
        saveProject.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_S,
                java.awt.Event.CTRL_MASK));
        fileMenu.add(saveProject);
        saveAction.setEnabled(false);

        loadAction = new AbstractAction("Load Project") {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                projectController.presentDialogLoad(gui);
            }
        };

        JMenuItem loadProject = new JMenuItem(loadAction);
        loadProject.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_L,
                java.awt.Event.CTRL_MASK));
        fileMenu.add(loadProject);

        synchronizeAction = new AbstractAction("Synchronize") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] mediaPaths = model.getCurrentDataSource().getMediaPath().toArray(
                        new String[model.getCurrentDataSource().getMediaPath()
                                .size()]);
                SynchronisationController synchronisationController =
                        new SynchronisationController(syncData, mediaPaths,
                                timeCoordinator.getMaxTime(), timeCoordinator.getMinimalDeltaTime());
                synchronisationController.addSensorTracks(model);
                synchronisationController.presentDialog(gui);
                mainController.setStartTimes(syncData.getStartTimes());
                mainController.setVideoSpeed(syncData.getVideoSpeed());
            }
        };
        //fileMenu.add(synchronizeAction);
        synchronizeAction.setEnabled(false);

//        startClassificationAction = new AbstractAction("Start Classification") {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                classificatorController.setClassify(true);
//                startClassificationAction.setEnabled(false);
//                stopClassificationAction.setEnabled(true);
//            }
//        };
//        classifyMenu.add(startClassificationAction);
//        startClassificationAction.setEnabled(false);
//
//        stopClassificationAction = new AbstractAction("Stop Classification") {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                classificatorController.setClassify(false);
//                stopClassificationAction.setEnabled(false);
//                startClassificationAction.setEnabled(true);
//            }
//        };
//        classifyMenu.add(stopClassificationAction);
//        stopClassificationAction.setEnabled(false);

        classifyAction = new AbstractAction("Classify") {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                classificatorController.presentDialog(gui);
//                if (classificatorController.isConfigured()) {
//                    startClassificationAction.setEnabled(true);
//                }
                gui.initializeClassifierScheduler();
            }
        };
        classifyMenu.add(classifyAction);
        classifyAction.setEnabled(false);

        statisticsAction = new AbstractAction("Statistics") {

            @Override
            public void actionPerformed(ActionEvent e) {
                gui.addStatistics();
            }
        };
        viewMenu.add(statisticsAction);
        statisticsAction.setEnabled(false);

        exitAction = new AbstractAction("Quit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resp;
                if (imported) {
                    Object[] options = { "Save",
                            "Don't save",
                            "Cancel" };
                    resp = JOptionPane.showOptionDialog(null,
                            "Save project before quitting?",
                            "Quit",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[2]);
                    if (resp == JOptionPane.YES_OPTION) {
                        projectController.presentDialogSave(gui);
                    }
                } else {
                    resp = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                }
                if (resp != JOptionPane.CANCEL_OPTION) {
                    if (mediaToPlay) {
                        try {
                            mainController.killMp();
                        } catch (RemoteException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    System.exit(0);
                }

            }
        };
        JMenuItem exit = new JMenuItem(exitAction);
        exit.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_Q,
                java.awt.Event.CTRL_MASK));
        fileMenu.add(exit);

        this.gui.setJMenuBar(menuBar);

        AbstractAction shortcutAction = new AbstractAction("Shortcuts") {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                JOptionPane pane = new JOptionPane();
                pane.setMessage("Shortcuts for the Menu are CTRL + First letter of the MenuEntry \n Shortcuts for Buttons are ALT + First Letter of Button Titel");
                JDialog d = pane.createDialog(null, "Shortcuts");
                d.setVisible(true);



            }
        };

        JMenuItem shortcuts = new JMenuItem(shortcutAction);
        helpMenu.add(shortcuts);

    }

    /**
     * Inits Mastercontroller and starts the whole programm
     * @param args
     */
    public static void main(String args[]) {
        MasterController mcontroller = new MasterController();
        mcontroller.onStart();
    }

    /**
     * Init and show the main window.
     */
    public void onStart() {
        this.gui = new Gui();
        initJMenuBar();
        SwingUtilities.invokeLater(new Runnable() {
                @Override public void run() {
                    gui.setVisible(true);
                }
            });
        //startRegistry();
        gui.addWindowListener(new WindowListener() {

            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (mediaToPlay) {
                    try {
                        mainController.killMp();
                    } catch (RemoteException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                System.exit(0);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (mediaToPlay) {
                    try {
                        mainController.killMp();
                    } catch (RemoteException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                System.exit(0);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

        });
        gui.setVisible(true);
    }

    /**
     * This method is called, when the import-Dialog has finished.
     * @param dataSource The data source that describes the imported file.
     */
    public void onImportFinish(DataSource<? extends DataColumn> dataSource) {
        model = new ModelFacade(null, null, null, null, dataSource, null, timeCoordinator);
        timeCoordinator.setMaxTime(model.getEndTimeStamp() - model.getStartTimeStamp());
        gui.setModel(model);
        TrackSynchroniser synchroniser = new TrackSynchroniser(model.getStartTimeStamp());
        timeCoordinator.addListener(synchroniser);
        gui.setTrackSynchroniser(synchroniser);
        gui.setControls(timeCoordinatorController);
        gui.setSensorViewVisible();
        gui.setAnnotationViewVisible();
        exportAction.setEnabled(true);
        saveAction.setEnabled(true);
        loadAction.setEnabled(false);
        importAction.setEnabled(false);
        statisticsAction.setEnabled(true);
        classificatorController = new ClassificatorController(model);
        classifyAction.setEnabled(true);
        imported = true;
        if (dataSource.getMediaPath().size() > 0) {
            mediaToPlay = true;
            syncData = new SyncData(dataSource.getMediaPath().size() + 1);
            synchronizeAction.setEnabled(true);
            String[] mediaPaths = dataSource.getMediaPath().toArray(
                    new String[model.getCurrentDataSource().getMediaPath()
                            .size()]);
            mainController =
                    new MainController(mediaPaths,
                            timeCoordinator,
                            syncData.getStartTimes(), syncData.getVideoSpeed());
            timeCoordinatorController.addMainController(mainController);

            gui.addMediaView(mainController);
            try {
                startSynchronizedMediaPlayers();
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void onLoadFinish(DataSource<? extends DataColumn> dataSource,
            AnnotationModelImpl model) {
        onImportFinish(dataSource);
        this.model.insertAnnoationModel(model);
    }

    private void startRegistry() {
        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e1) {
            JOptionPane.showMessageDialog(gui,
                    "An other instance of the program already running.",
                    "Squirrel is already running",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Starts the media players
     * @throws URISyntaxException
     */
    private void startSynchronizedMediaPlayers() throws URISyntaxException {
        if (gui.isVisible()) {
            long[] canvasIDs = gui.getCanvasId();

            CodeSource codeSource = SynchronisationView.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

            for (int i = 0; i < canvasIDs.length; i++) {
                ProcessBuilder pb =
                        new ProcessBuilder("java", "-cp", jarDir
                                + "/squirrel-1.0-SNAPSHOT-jar-with-dependencies.jar",
                                "squirrel.model.video.RemoteControlledMediaPlayer",
                                Integer.toString(i), "0", Long.toString(canvasIDs[i]));

                try {
                    pb.start();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
