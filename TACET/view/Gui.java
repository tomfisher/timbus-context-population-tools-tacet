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

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import squirrel.controller.AnnotationTrackController;
import squirrel.controller.ClassifierScheduler;
import squirrel.controller.SensorDataTrackController;
import squirrel.controller.TimeCoordinatorController;
import squirrel.controller.TrackEventBus;
import squirrel.controller.video.MainController;
import squirrel.model.ModelFacade;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.util.Range;
import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.extension.gui.dock.theme.FlatTheme;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CLocation;
import bibliothek.gui.dock.common.DefaultSingleCDockable;
import bibliothek.gui.dock.common.SingleCDockable;
import bibliothek.gui.dock.common.event.CDockableLocationEvent;
import bibliothek.gui.dock.common.event.CDockableLocationListener;
import bibliothek.gui.dock.common.intern.CDockController;

public class Gui extends JFrame {

    /**
     * Video section of the main window
     */
    private SingleCDockable media;

    private TimeCoordinatorView controlsView;

    /**
     * References by index to every track view.
     */
    private Map<Integer, TrackView> trackViews = new HashMap<>();

    /**
     * Annotation section of the main window
     */
    private GroupView<AnnotationTrackView> annotationView;

    /**
     * Sensor section of the main window
     */
    private GroupView<SensorDataTrackView> sensorView;

    /**
     * Statistic section of the main window.
     */
    private StatisticView statisticView;

    /**
     * Controller of the docking framework
     */
    private CControl control;

    private ModelFacade model;
    private TrackEventBus trackSynchroniser;

    /**
     * Constructor of the main window
     */
    public Gui() {
        super("Tacet");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        control = new CControl(this);
        control.setTheme(new FlatTheme());
        this.add(control.getContentArea());

        this.setBounds(20, 20, 1000, 600);
    }


    public void setSensorViewVisible() {
        this.sensorView = getSensorView(trackSynchroniser);
        DefaultSingleCDockable sensorDockable =
                new DefaultSingleCDockable(Integer.toString(sensorView.hashCode()),
                        "Sensor Data", sensorView);
        control.addDockable(sensorDockable);
        sensorDockable.setVisible(true);
    }

    public void setAnnotationViewVisible() {
        this.annotationView = getAnnotationView(trackSynchroniser);
        DefaultSingleCDockable annotationDockable =
                new DefaultSingleCDockable(Integer.toString(annotationView.hashCode()),
                        "Annotations", annotationView);
        control.addDockable(annotationDockable);
        annotationDockable.setVisible(true);
    }

    private GroupView<SensorDataTrackView> getSensorView(TrackEventBus synchroniser) {
        DataSource<? extends DataColumn> dataSource = model.getCurrentDataSource();
        GroupView<SensorDataTrackView> sensorGroups = new GroupView<>();
        sensorGroups.setElementHeight(100);
        Range rangeToDisplay =
            new Range(model.getStartTimeStamp(), model.getStartTimeStamp() + 700);
        for (DataColumn dc : dataSource.getSensorColumns()) {
            int index = dataSource.indexOf(dc);
            SensorDataTrackView sensorTrackView =
                new SensorDataTrackView(model, index, rangeToDisplay);
            trackViews.put(index, sensorTrackView);
            SensorDataTrackController sensorController =
                new SensorDataTrackController(sensorTrackView, model, synchroniser);
            sensorGroups.addElement(sensorTrackView);
        }
        return sensorGroups;
    }


    private GroupView<AnnotationTrackView> getAnnotationView(TrackEventBus synchroniser) {
        DataSource<? extends DataColumn> dataSource = model.getCurrentDataSource();
        GroupView<AnnotationTrackView> annotationGroups = new GroupView<>();
        Range rangeToDisplay =
            new Range(model.getStartTimeStamp(), model.getStartTimeStamp() + 600);
        for (DataColumn dc : dataSource.getAnnotationColumns()) {
            int index = dataSource.indexOf(dc);
            AnnotationTrackView annotationTrackView =
                new AnnotationTrackView(model, index, rangeToDisplay);
            trackViews.put(index, annotationTrackView);
            AnnotationTrackController annotationController =
                new AnnotationTrackController(annotationTrackView, model, synchroniser);
            annotationGroups.addElement(annotationTrackView);
        }
        return annotationGroups;
    }

    public void initializeClassifierScheduler() {
        DataColumn dc = model.getCurrentDataSource().getTrainColumn();
        if (dc != null) {
            int idx = model.getCurrentDataSource().indexOf(dc);
            new ClassifierScheduler(trackViews.get(idx), model, trackSynchroniser);
        }
    }

    /**
     * Saves reference to model of mvc
     * @param model
     */
    public void setModel(ModelFacade model) {
        this.model = model;
    }

    public void setTrackSynchroniser(TrackEventBus trackSynchroniser) {
        this.trackSynchroniser = trackSynchroniser;
    }

    /**
     * Get CanvasID of MediaPlayer
     * @return Cavnas ID of Media Player
     */
    public long[] getCanvasId() {
        return ((MediaView) media).getCanvasIDs();
    }

    /**
     * Adds media view to gui
     * @param mainController - instance of MainController
     */
    public void addMediaView(MainController mainController) {
        this.media = new MediaView(this, mainController);
        media.addCDockableLocationListener(new DockableListener());
        control.addDockable(media);
        media.setVisible(true);
    }

    /**
     * Sets controll buttons
     * @param tcc - instance of time coordinator
     */
    public void setControls(TimeCoordinatorController tcc) {
        this.controlsView = new TimeCoordinatorView(tcc);
        DefaultSingleCDockable controlsDockable =
                new DefaultSingleCDockable(Integer.toString(controlsView.hashCode()),
                        "Controls", controlsView);
        control.addDockable(controlsDockable);
        controlsDockable.setVisible(true);
    }

    /**
     * Listener for docking event
     */
    private class DockableListener implements CDockableLocationListener {

        @Override
        public void changed(CDockableLocationEvent arg0) {
            if (media.isVisible() && ((MediaView) media).isConnected() && ((MediaView) media).isEnalrged()) {
                ((MediaView) media).reloadMediaPlayers();
            }
        }

    }

    /**
     * Adds the statistic view to the gui
     */
    public void addStatistics() {
        statisticView = new StatisticView(model);
        DefaultSingleCDockable statisticsDockable =
                   new DefaultSingleCDockable(Integer.toString(statisticView.hashCode()), "Statistics", statisticView);
        control.addDockable(statisticsDockable);
        statisticsDockable.setLocation(CLocation.base().normalEast(0.2));
        statisticsDockable.setVisible(true);
    }
}
