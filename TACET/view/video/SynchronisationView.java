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
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.security.CodeSource;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import squirrel.controller.SensorDataTrackController;
import squirrel.controller.TimeCoordinatorController;
import squirrel.controller.TrackSynchroniser;
import squirrel.controller.video.SynchronisationController;
import squirrel.model.ModelFacade;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.model.video.SyncData;
import squirrel.model.video.SyncPoint;
import squirrel.util.Range;
import squirrel.view.GroupView;
import squirrel.view.SensorDataTrackView;
import squirrel.view.TimeCoordinatorView;

@SuppressWarnings("serial")
public class SynchronisationView extends JPanel {

    private JDialog dialog;
    private MediaPlayerView[] mediaPlayers;
    private JTabbedPane tabbedPane;
    private GroupView groupView;
    private TimeCoordinatorView controls;
    private JButton setSyncPoint;
    private JButton synchronize;
    private TimeCoordinatorController timeCoordinatorController;
    private DefaultTableModel syncPointsTableModel;
    private JTable syncPointsTable;
    private JScrollPane syncPointsScp;
    private JButton remove;
    private JPanel preSync;
    private JScrollPane preSyncScp;
    private JLabel[] vidNames;
    private JLabel[] startTimes;
    private JLabel[] videoSpeed;
    private JPanel preSyncAndRemoveBtn;
    private JButton cancel;

    public SynchronisationView(String[] mediaPaths, TimeCoordinatorController tcc, SyncData syncData) {
        this.timeCoordinatorController = tcc;
        this.setLayout(new MigLayout("", "", "[][pref][]"));
        this.mediaPlayers = new MediaPlayerView[mediaPaths.length];
        setSyncPoint = new JButton("Set Sync. Point");
        synchronize = new JButton("Synchronize");
        cancel = new JButton("Cancel");
        remove = new JButton("Remove");

        preSync = new JPanel();
        preSyncScp = new JScrollPane(preSync);

        vidNames = new JLabel[mediaPaths.length + 1];
        startTimes = new JLabel[mediaPaths.length + 1];
        videoSpeed = new JLabel[mediaPaths.length + 1];

        preSync.setLayout(new MigLayout("wrap 3"));
        vidNames[0] = new JLabel();
        startTimes[0] = new JLabel("Start time (ms)");
        videoSpeed[0] = new JLabel("Speed");
        preSync.add(vidNames[0]);
        preSync.add(startTimes[0], "pushx");
        preSync.add(videoSpeed[0], "pushx");
        for (int i = 1; i <= mediaPaths.length; i++) {
            vidNames[i] = new JLabel("Video " + Integer.toString(i));
            startTimes[i] = new JLabel(Integer.toString(0), JLabel.RIGHT);
            videoSpeed[i] = new JLabel(Integer.toString(1), JLabel.RIGHT);
            preSync.add(vidNames[i]);
            preSync.add(startTimes[i], "align right, pushx");
            preSync.add(videoSpeed[i], "align right, pushx");
        }

        preSyncAndRemoveBtn = new JPanel();
        preSyncAndRemoveBtn.setLayout(new BorderLayout(0, 5));
        preSyncAndRemoveBtn.add(remove, BorderLayout.NORTH);
        preSyncAndRemoveBtn.add(preSyncScp, BorderLayout.CENTER);

        String[] columnNames = new String[mediaPaths.length + 1];
        columnNames[0] = "SensorData";
        for (int i = 1; i <= mediaPaths.length; i++) {
            columnNames[i] = "Video " + Integer.toString(i);
        }

        syncPointsTableModel =
                new DefaultTableModel(columnNames, 0);
        syncPointsTable = new JTable(syncPointsTableModel) {
            @Override
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        syncPointsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        syncPointsScp = new JScrollPane(syncPointsTable);

        JPanel buttons = new JPanel();
        buttons.add(setSyncPoint);
        buttons.add(synchronize);
        buttons.add(cancel);

        tabbedPane = new JTabbedPane();
        for (int i = 0; i < mediaPaths.length; i++) {
            mediaPlayers[i] = new MediaPlayerView(mediaPaths[i], i);
            tabbedPane.addTab("Video " + Integer.toString(i + 1), mediaPlayers[i]);
        }

        groupView = new GroupView<>();
        groupView.setElementHeight(100);
        groupView.setGroupSize(3);
        // groupView.setMinimumSize(new Dimension(600, 250));

        controls = new TimeCoordinatorView(tcc);

        JPanel dataView = new JPanel();
        dataView.setLayout(new BorderLayout());

        dataView.add(controls, BorderLayout.PAGE_START);
        dataView.add(groupView, BorderLayout.CENTER);

        this.add(tabbedPane, "pushx, grow");
        this.add(syncPointsScp, "push, grow, spany 2, wrap");
        this.add(buttons, "align center, wrap");
        this.add(dataView, "pushx, grow");
        this.add(preSyncAndRemoveBtn, "align center, pushx, grow");
    }

    public void addSensorTrack(int groupNo, JComponent element) {
        groupView.addElement(groupNo, element);
    }

    public void addSensorTrack(ModelFacade model, TrackSynchroniser synchroniser) {
        DataSource<? extends DataColumn> dataSource = model.getCurrentDataSource();
        Range rangeToDisplay =
                new Range(model.getStartTimeStamp(), model.getStartTimeStamp() + 700);
        for (DataColumn dc : dataSource.getSensorColumns()) {
            int index = dataSource.indexOf(dc);
            SensorDataTrackView sensorTrackView =
                    new SensorDataTrackView(model, index, rangeToDisplay);
            SensorDataTrackController sensorController =
                    new SensorDataTrackController(sensorTrackView, model,
                            synchroniser);
            groupView.addElement(sensorTrackView);
        }
    }

    public void addListeners(SynchronisationController sc) {
        setSyncPoint.addActionListener(sc);
        synchronize.addActionListener(sc);
        cancel.addActionListener(sc);
        remove.addActionListener(sc);
    }

    public long[] getCanvasIds() {
        long[] canvasIDs = new long[mediaPlayers.length];
        for (int i = 0; i < mediaPlayers.length; i++) {
            canvasIDs[i] = mediaPlayers[i].getCanvasId();
        }
        return canvasIDs;
    }

    public void killMPs() throws RemoteException {
        for (int i = 0; i < mediaPlayers.length; i++) {
            mediaPlayers[i].killMP();
        }
    }

    public int getNoOfMp() {
        return mediaPlayers.length;
    }

    public long[] getTimes() throws IOException {
        long[] times = new long[mediaPlayers.length + 1];
        times[0] = timeCoordinatorController.getTime();
        for (int i = 0; i < mediaPlayers.length; i++) {
            times[i + 1] = mediaPlayers[i].getMpTime();
        }
        return times;
    }

    public void addSyncPoint(SyncPoint sp) {
        Object[] timeStamps = new Object[mediaPlayers.length + 1];
        for (int i = 0; i <= mediaPlayers.length; i++) {
            timeStamps[i] = (Object) (sp.getSyncTimeStamps()[i] / 1000.0);
        }
        syncPointsTableModel.addRow(timeStamps);
    }

    public int[] removeSelected() {
        int[] selRowIdxs = syncPointsTable.getSelectedRows();
        for (int i = selRowIdxs.length - 1; i >= 0; i--) {
            syncPointsTableModel.removeRow(selRowIdxs[i]);
        }
        return selRowIdxs;
    }

    public void setNewVidConfig(long[] startTime, float[] speed) {
        for (int i = 1; i <= startTime.length; i++) {
            startTimes[i].setText(Long.toString(startTime[i - 1]));
            videoSpeed[i].setText(
                    String.format("%.2f", speed[i - 1]));
        }
    }

    public JButton getSyncPointButton() {
        return setSyncPoint;
    }

    public JButton getSynchronizeButton() {
        return synchronize;
    }

    public JButton getCancelButton() {
        return cancel;
    }

    public JButton getRemoveButton() {
        return remove;
    }

    public void disposeDialog() {
        if (this.dialog != null)
            dialog.dispose();
    }

    public void showAsDialog(Frame owner) {
        try {
            startSoloMediaPlayers();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dialog = new JDialog(owner, "Synchronisation Dialog", true);
        dialog.add(this, BorderLayout.CENTER);
        dialog.setSize(new Dimension(900, 720));
        dialog.setVisible(true);
    }

    private void startSoloMediaPlayers() throws URISyntaxException {
        CodeSource codeSource = SynchronisationView.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();

        for (int i = 0; i < mediaPlayers.length; i++) {
            ProcessBuilder pb =
                    new ProcessBuilder("java", "-cp", jarDir
                            + "/squirrel-1.0-SNAPSHOT-jar-with-dependencies.jar",
                            "squirrel.model.video.RemoteControlledMediaPlayer",
                            Integer.toString(i), "1");

            try {
                pb.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
