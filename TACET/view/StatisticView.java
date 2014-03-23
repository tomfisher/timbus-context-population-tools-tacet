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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import squirrel.model.ModelFacade;

@SuppressWarnings("serial")
public class StatisticView extends JPanel {

    private ModelFacade mf;

    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JLabel avgDistLabel;
    private JLabel annoNumbLabel;

    private DefaultTableModel annoTableModel;
    private DefaultTableModel sensorTableModel;

    private Vector annoNames;
    private Vector annoData;

    int numberOfTracks;

    private JButton refreshBt;

    public StatisticView(ModelFacade mf) {
        this.mf = mf;
        initGui();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void initGui() {
        this.setLayout(new MigLayout());

        // set SensorData statistics
        this.add(new JLabel("SensorData"), "wrap");

        Vector sensorNames = new Vector();
        sensorNames.add("Track");
        sensorNames.add("Average");
        sensorNames.add("Median");
        sensorNames.add("Deviation");

        Vector sensorData = new Vector();

        numberOfTracks = mf.getNumberOfSensorTracks();

        for (int i = 0; i < numberOfTracks; i++) {
            Vector track = new Vector();

            track.add(i);
            track.add(String.format("%.2f", mf.getAvgSensordata(i)));
            track.add(String.format("%.2f", mf.getMedianSensordata(i)));
            track.add(String.format("%.2f", mf.getStandardDeviationSensordata(i)));

            sensorData.add(track);
        }

        sensorTableModel = new DefaultTableModel(sensorData, sensorNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };

        JTable sensorTable = new JTable(sensorTableModel);
        JScrollPane sensorPane = new JScrollPane(sensorTable);
        this.add(sensorPane, "span 2, push, grow, wrap");

        // set Annotation statistics
        this.add(new JLabel("Annotations"), "wrap");

        numberOfTracks = mf.getNumberOfAnnotationTracks();

        annoNames = new Vector();
        annoNames.add("Track");
        annoNames.add("# of annotations");
        annoNames.add("Percentage");

        annoData = getAnnoData();

        annoTableModel = new DefaultTableModel(annoData, annoNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };
        JTable annoTable = new JTable(annoTableModel);
        JScrollPane annoPane = new JScrollPane(annoTable);
        this.add(annoPane, "span 2, push, grow, wrap");

        // set general statistics

        annoNumbLabel = new JLabel(Integer.toString(mf.countAnnotations()));
        startTimeLabel = new JLabel(Long.toString(mf.getStartTimeStamp()));
        endTimeLabel = new JLabel(Long.toString(mf.getEndTimeStamp()));
        avgDistLabel = new JLabel(String.format("%.2f", mf.getAvgTimeDistance()));

        this.add(new JLabel("Total number of annotations: "), "align left");
        this.add(annoNumbLabel, "align right, wrap");
        this.add(new JLabel("Start timestamp: "), "align left");
        this.add(startTimeLabel, "align right, wrap");
        this.add(new JLabel("End timestamp: "), "align left");
        this.add(endTimeLabel, "align right, wrap");
        this.add(new JLabel("Average time distance: "), "align left");
        this.add(avgDistLabel, "align right, wrap");

        // set refresh button
        refreshBt = new JButton("Refresh");
        this.add(refreshBt, "skip 1, align right, wrap");
        refreshBt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }

        });
    }

    public void refresh() {
        this.annoNumbLabel.setText(Integer.toString(mf.countAnnotations()));
        annoData = getAnnoData();
        annoTableModel.setDataVector(annoData, annoNames);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Vector getAnnoData() {
        Vector out = new Vector();
        for (int i = 0; i < numberOfTracks; i++) {
            Vector track = new Vector();

            track.add(i);
            track.add(mf.countAnnotations(i));
            track.add(String.format("%.2f", mf.getPercentAnnotation(i)) + "%");

            out.add(track);
        }
        return out;
    }

}
