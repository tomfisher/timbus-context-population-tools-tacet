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

package squirrel.controller.video;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;

import squirrel.controller.TimeCoordinatorController;
import squirrel.controller.TrackSynchroniser;
import squirrel.model.ModelFacade;
import squirrel.model.TimeCoordinator;
import squirrel.model.video.SyncData;
import squirrel.model.video.SyncPoint;
import squirrel.view.video.SynchronisationView;

/**
 * The Class SynchronisationController.
 */
public class SynchronisationController implements ActionListener {

    private SyncData syncData;
    private SynchronisationView syncView;
    private TimeCoordinator tc;

    /**
     * Instantiates a new synchronization controller.
     *
     * @param syncView the sync view
     * @param syncData the sync data
     */
    public SynchronisationController(SyncData syncData,
            String[] mediaPaths, long maxTime, long minimalDeltaTime) {
        this.tc = new TimeCoordinator(minimalDeltaTime, maxTime);
        TimeCoordinatorController tcc = new TimeCoordinatorController(tc);

        this.syncData = syncData;
        this.syncView = new SynchronisationView(mediaPaths, tcc, syncData);
        this.syncView.addListeners(this);
    }

    public void addSensorTracks(ModelFacade model) {
        TrackSynchroniser synchroniser = new TrackSynchroniser(model.getStartTimeStamp());
        tc.addListener(synchroniser);
        syncView.addSensorTrack(model, synchroniser);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == syncView.getSyncPointButton()) {
            try {
                SyncPoint sp = new SyncPoint(syncView.getTimes());
                syncData.addSyncPoint(sp);
                syncView.addSyncPoint(sp);
                syncData.synchronize();
                syncView.setNewVidConfig(syncData.getStartTimes(), syncData.getVideoSpeed());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (arg0.getSource() == syncView.getSynchronizeButton()) {
            syncData.synchronize();
            syncData.setSynchronisation();
            try {
                syncView.killMPs();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            syncView.disposeDialog();
        } else if (arg0.getSource() == syncView.getCancelButton()) {
            try {
                syncView.killMPs();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            syncView.disposeDialog();
        } else if (arg0.getSource() == syncView.getRemoveButton()) {
            if (syncData.geNumberOfPonts() > 0) {
                int[] selIdxs = syncView.removeSelected();
                syncData.removeSynPoints(selIdxs);
            }
            if (syncData.geNumberOfPonts() > 1) {
                syncData.synchronize();
                syncView.setNewVidConfig(syncData.getStartTimes(), syncData.getVideoSpeed());
            }
        }
    }

    public void presentDialog(Frame owner) {
        syncView.showAsDialog(owner);
    }

}
