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
