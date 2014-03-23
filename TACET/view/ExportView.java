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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import net.miginfocom.swing.MigLayout;
import squirrel.controller.ExportController;
import squirrel.controller.TimestampFormat;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSink;
import squirrel.model.io.DataSource;
import squirrel.model.io.DataColumn.Type;

/**
 * Export UI
 */
public class ExportView extends WarningView {

    /**
     * Controller of UI
     */
    private ExportController controller;
    private JDialog dialog;

    /**
     * Tabs to switch between different file types
     */
    private JTabbedPane tabbedPane;

    /**
     * UI to export CSV file
     */
    private CSVPanel csvPnlMain;

    /**
     * UI Elements
     */
    private JButton btnCancel, btnImport;
    private JFileChooser fchBrowse;
    private JLabel info;

    /**
     * Constructor of Export View
     * @param controller - that handels user input
     */
    public ExportView(ExportController controller) {
        this.controller = controller;
        controller.setValidationController(DataSource.Type.CSV);
        initGUI();
        controller.setExportView(this);

    }

    /**
     * Initializes the UI
     */
    private void initGUI() {
        // Set up the tabbed pane and main panels and buttons
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        btnCancel = new JButton("Cancel");
        btnCancel.setMnemonic('C');
        btnImport = new JButton("Export");
        btnImport.setMnemonic('E');
        csvPnlMain = new CSVPanel();
        csvPnlMain.initGUI();
        info = new JLabel("You can only change to Ignore and add Timestamps");
        tabbedPane.addTab("CSV", csvPnlMain);

        ImportActionListener csvAl = new ImportActionListener();
        btnImport.addActionListener(csvAl);
        btnCancel.addActionListener(csvAl);

        this.setLayout(new MigLayout(""));
        this.add(info, "wrap");
        this.add(tabbedPane, "grow, push, span, wrap");
        this.add(btnCancel, "tag cancel");
        this.add(btnImport, "tag apply");

        fchBrowse = new JFileChooser(System.getProperty("user.home"));
        fchBrowse.setFileSelectionMode(JFileChooser.APPROVE_OPTION);

    }

    /**
     * Return the selected source type to export
     * @return
     */
    public DataSource.Type getSelectedSourceType() {
        switch (tabbedPane.getSelectedIndex()) {
        case 0:
            return DataSource.Type.CSV;

        default:
            throw new IllegalStateException("The selected tab/source is not supported.");
        }
    }

    public void showAsDialog(Frame owner) {
        controller.defaultValues();
        dialog = new JDialog(owner, "Export Dialog", true);
        dialog.add(this, BorderLayout.CENTER);
        dialog.pack();
        dialog.setVisible(true);

    }

    public void disposeDialog() {
        if (this.dialog != null)
            dialog.dispose();
    }

    /**
     * For testing...
     */
    public void setCSVValues(String lineSeparator, String elemSeparator,
        String location,
        DefaultTableModel fieldsTblModel, TableCellEditor typeEditor, String date,
        String units, String unitsSeparator, String from, String to) {

        csvPnlMain.txfLineSeparator.setText(lineSeparator);
        csvPnlMain.txfElemSeparator.setText(elemSeparator);
        csvPnlMain.txfExportLocation.setText(location);
        csvPnlMain.columnDesciptionPanel.tblModel = fieldsTblModel;
        csvPnlMain.columnDesciptionPanel.table.setModel(fieldsTblModel);
        csvPnlMain.columnDesciptionPanel.table.getColumnModel().getColumn(2)
            .setCellEditor(typeEditor);
    }

    private boolean nonEmpty(String s) {
        return s != null && s.length() > 0;
    }

    private class ImportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnImport) {
                switch (getSelectedSourceType()) {
                case CSV:
                    controller.setValidationController(DataSource.Type.CSV);
                    csvPnlMain.validateAll(controller);

                    controller.doExport(DataSink.Type.CSV);
                    break;
                }
            } else if (e.getSource() == btnCancel) {
                dialog.dispose();
            }
        }
    }

    private class CSVPanel extends JPanel {
        JTextField txfLineSeparator, txfElemSeparator, txfExportLocation;
        JLabel lblLineSeparator, lblElemSeparator, lblExportLocation;
        JButton btnBrowse;
        ColumnDescriptionPanel columnDesciptionPanel;

        void initGUI() {
            lblLineSeparator = new JLabel("Line Separator:");
            lblElemSeparator = new JLabel("Element Separator:");
            lblExportLocation = new JLabel("Location:");
            txfLineSeparator = new JTextField(3);
            txfLineSeparator.setText("\n");
            txfElemSeparator = new JTextField(3);
            txfElemSeparator.setText(",");
            txfExportLocation = new JTextField(20);
            btnBrowse = new JButton("Browse");

            this.setLayout(new MigLayout("", "", "[nogrid][nogrid][]"));
            // These following components share one cell and constitute a row
            this.add(lblExportLocation, "align label");
            this.add(txfExportLocation, "pushx, growx");
            this.add(btnBrowse, "gapleft unrel, align right, wrap");

            // These following components share one cell and constitute a row
            this.add(lblElemSeparator, "align label");
            this.add(txfElemSeparator, "gapright unrelated");
            this.add(lblLineSeparator, "align label");
            this.add(txfLineSeparator, "wrap, gapbottom unrel");

            columnDesciptionPanel = new ColumnDescriptionPanel(
                new String[] { "#", "Name", "Type" }, 2);
            columnDesciptionPanel.initGUI();

            JPanel splitPane = columnDesciptionPanel;
            this.add(splitPane, "pushx, growx, wrap");

            ActionListener al = new ActionListenerImpl();
            btnBrowse.addActionListener(al);
        }

        void validateAll(ExportController controller) {
            controller.clearValidations();
            controller.validateLocation(txfExportLocation.getText());
            controller.validateCSVElemSep(txfElemSeparator.getText());
            controller.validateCSVLineSep(txfLineSeparator.getText());
            controller.validateCSVColumnDescriptions(columnDesciptionPanel.tblModel, true);

            Map<Integer, TimestampFormat> formats = new HashMap<>();
            for (Integer key : columnDesciptionPanel.timePanels.keySet()) {
                TimespecPanel panel = columnDesciptionPanel.timePanels.get(key);
                if (panel.rdbCustDate.isSelected()) {
                    formats.put(key, new TimestampFormat(panel.txfCustUnits.getText(),
                        panel.txfCustSeparator.getText()));

                } else {
                    formats.put(key, new TimestampFormat(panel.txfDate.getText()));
                }
            }
            controller.validateCSVTimestampFormats(formats);
        }

        private class ActionListenerImpl implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnBrowse) {
                    int result = fchBrowse.showSaveDialog(ExportView.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File fileToOpen = fchBrowse.getSelectedFile();
                        txfExportLocation.setText("file://" + fileToOpen.getPath() + ".csv");
                    }
                }
            }
        }

    }

    private class ColumnDescriptionPanel extends JPanel {
        JTable table;
        JScrollPane scrollPane;
        JButton btnAdd, btnRemove;
        DefaultTableModel tblModel;

        Map<Integer, TimespecPanel> timePanels = new HashMap<>();
        Component currentTimePanel;
        JLabel lblNoTimestamp;

        private int typeIndex;

        public ColumnDescriptionPanel(String[] columnNames, int typeIndex) {
            this.tblModel = new DefaultTableModel(columnNames, 2);
            this.typeIndex = typeIndex;
        }

        void initGUI() {
            btnAdd = new JButton("Add");
            btnRemove = new JButton("Remove");
            tblModel.setValueAt(0, 0, 0);
            tblModel.setValueAt(1, 1, 0);
            table = new JTable(tblModel);
            table.getColumnModel()
                .getColumn(2)
                .setCellEditor(
                    new DefaultCellEditor(new JComboBox<DataColumn.Type>(DataColumn.Type
                        .values())));
            table.getColumnModel().getColumn(0).setMaxWidth(20);
            table.setPreferredScrollableViewportSize(
                new Dimension((int) table.getPreferredSize().getHeight(),
                    table.getRowHeight() * 6));
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
            scrollPane = new JScrollPane(table);

            lblNoTimestamp = new JLabel("No timestamp column is selected.");
            lblNoTimestamp.setFont(new Font(lblNoTimestamp.getFont().getName(),
                lblNoTimestamp.getFont().getStyle(), lblNoTimestamp.getFont().getSize() * 2));
            currentTimePanel = lblNoTimestamp;

            // layout
            this.setLayout(new MigLayout());
            this.add(scrollPane, "push, grow");
            this.add(btnAdd, "growx, flowy, aligny top, split 4");
            this.add(btnRemove, "growx, wrap, gapbottom unrelated");
            // this.add(btnMoveUp, "growx");
            // this.add(btnMoveDown, "growx, wrap, gapbottom unrel");
            this.add(lblNoTimestamp, "pushx, growx, wrap");

            ActionListener al = new ActionListenerImpl();
            btnAdd.addActionListener(al);
            btnRemove.addActionListener(al);

        }

        private void showTimePanel(int index) {
            this.remove(currentTimePanel);
            if (index < 0) {
                this.add(lblNoTimestamp, "pushx, growx, wrap");
                currentTimePanel = lblNoTimestamp;
            } else {
                currentTimePanel = timePanels.get(index);
                this.add(currentTimePanel, "pushx, growx, wrap");
            }
            validate();
            super.repaint();
        }

        private void maybeRemoveTimePanel(int index) {
            Type colType = (Type) tblModel.getValueAt(table.getSelectedRow(), typeIndex);
            if (colType != null && colType == Type.TIMESTAMP) {
                timePanels.remove(index);
            }
        }

        private class TableSelectionListener implements ListSelectionListener {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                int min = lsm.getMinSelectionIndex();
                int max = lsm.getMaxSelectionIndex();
                if (min > -1 && max > -1) {
                    Type colType =
                        (Type) tblModel.getValueAt(table.getSelectedRow(), typeIndex);

                    switch (getSelectedSourceType()) {

                    case CSV:
                        if (colType != null && colType.isTimeStamp()) {
                            if (!timePanels.containsKey(min)) {
                                timePanels.put(min, new TimespecPanel());
                            }
                            showTimePanel(min);
                        } else {
                            showTimePanel(-1);
                        }

                        break;

                    }
                }
            }
        }

        private class ActionListenerImpl implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnAdd) {
                    Integer nextNum = tblModel.getRowCount();
                    tblModel.addRow(new Object[] { String.valueOf(nextNum), "",
                        DataColumn.Type.IGNORE, "" });
                } else if (e.getSource() == btnRemove) {
                    CellEditor editor = table.getCellEditor();
                    if (editor != null)
                        editor.stopCellEditing();

                    maybeRemoveTimePanel(table.getSelectedRow());
                    if (controller.validateDeleteColumn(table.getSelectedRow())) {
                        tblModel.removeRow(table.getSelectedRow());
                    }
                }
            }
        }
    }

    class TimespecPanel extends JPanel {
        JCheckBox chbApplyRegex;
        JRadioButton rdbDate, rdbCustDate;
        ButtonGroup btnGroup;
        JTextField txfDate, txfCustUnits,
            txfCustSeparator;

        public TimespecPanel() {
            initGUI();
        }

        void initGUI() {
            this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Timestamp specifications"));
            chbApplyRegex = new JCheckBox("Apply Regex");
            rdbDate = new JRadioButton("Date");
            rdbDate.setSelected(true);
            controller.setSelectedDate(false);
            JLabel lblDate = new JLabel("Format:");
            txfDate = new JTextField(10);
            rdbCustDate = new JRadioButton("Custom");
            JLabel lblCustUnits = new JLabel("Units:");
            txfCustUnits = new JTextField(10);
            JLabel lblCustSeparator = new JLabel("Separator:");
            txfCustSeparator = new JTextField(3);
            btnGroup = new ButtonGroup();
            btnGroup.add(rdbDate);
            btnGroup.add(rdbCustDate);
            // layout
            this.setLayout(new MigLayout("", "[][][][][][]"));
            this.add(rdbDate, "span 2");
            this.add(rdbCustDate, "span 2, wrap");
            this.add(lblDate);
            this.add(txfDate);
            this.add(lblCustUnits);
            this.add(txfCustUnits);
            this.add(lblCustSeparator);
            this.add(txfCustSeparator, "wrap");
        }
    }

    public Dialog getDialog() {
        return this.dialog;
    }
}
