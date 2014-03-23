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

package squirrel.view.ImportWizard;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import jwf.WizardPanel;
import net.miginfocom.swing.MigLayout;
import squirrel.controller.ImportController;
import squirrel.controller.TimestampFormat;
import squirrel.controller.ValidationResult;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.model.io.DataColumn.Type;

public class SpecPanel extends WizardPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public ImportController controller;
    public JPanel gui;
    public JFrame container;
    public SpecPanel(ImportController controller, JFrame container) {

        this.controller = controller;
        this.container = container;
        this.initGui();
    }

    private void initGui() {

        this.container.setSize(new Dimension(650, 700));
        this.setLayout(new MigLayout());
        JLabel lablogo = new JLabel();
        lablogo.setIcon(new javax.swing.ImageIcon(FileFormatPanel.class.getClassLoader()
            .getResource("squirrel/resources/teco.png")));
        this.add(lablogo, "wrap 50, w 390!, h 100!");

        switch (controller.getSelectedSourceType()) {

        case CSV:
            this.gui = new CSVPanel();
            break;

        }

        this.add(gui);
    }

    private class CSVPanel extends JPanel {

        /**
         * UI components
         */
        JTextField txfLineSeparator, txfElemSeparator;
        JLabel lblLineSeparator, lblElemSeparator;
        ColumnDescriptionPanel columnDescriptionPanel;

        public CSVPanel() {

            this.initGUI();

        }

        /**
         * Inits UI
         */
        void initGUI() {
            lblLineSeparator = new JLabel("Line Separator:");
            lblElemSeparator = new JLabel("Element Separator:");
            txfLineSeparator = new JTextField(3);
            txfLineSeparator.setText("\\n");
            txfLineSeparator.setVisible(false);
            lblLineSeparator.setVisible(false);
            txfElemSeparator = new JTextField(3);
            txfElemSeparator.setText("");

            this.setLayout(new MigLayout("", "", "[nogrid][nogrid][]"));

            // These following components share one cell and constitute a row
            this.add(lblElemSeparator, "align label");
            this.add(txfElemSeparator, "gapright unrelated");
            this.add(lblLineSeparator, "align label");
            this.add(txfLineSeparator, "wrap, gapbottom unrel");

            columnDescriptionPanel = new ColumnDescriptionPanel(
                new String[] { "#", "Name", "Type", "Allowed Values" }, 2);
            columnDescriptionPanel.initGUI();
            columnDescriptionPanel.table
                .getColumnModel()
                .getColumn(2)
                .setCellEditor(
                    new DefaultCellEditor(new JComboBox<DataColumn.Type>(DataColumn.Type
                        .values())));
            this.add(columnDescriptionPanel, "pushx, growx, wrap");

        }

        /**
         * Validates user imput
         * @param controller - import controller
         */
        void validateAll(ImportController controller) {

            controller.clearErrors();

            controller.validateCSVElemSep(txfElemSeparator.getText());
            controller.validateCSVLineSep(txfLineSeparator.getText());
            controller.validateCSVColumnDescriptions(columnDescriptionPanel.tblModel);
            Map<Integer, TimestampFormat> formats = new HashMap<>();
            for (Integer key : columnDescriptionPanel.timePanels.keySet()) {
                TimespecPanel panel = columnDescriptionPanel.timePanels.get(key);
                if (panel.rdbCustDate.isSelected()) {
                    formats.put(key, new TimestampFormat(panel.txfCustUnits.getText(),
                        panel.txfCustSeparator.getText()));
                } else {
                    formats.put(key, new TimestampFormat(panel.txfDate.getText()));
                }
            }
            controller.validateCSVTimestampFormats(formats);

        }

    }

    /*
     * private DBDataSource dataSource; private TimeConverter timeConverter;
     * private PreparedStatement statement = null; private ResultSet resultSet =
     * null; private float average = -1; private long startTimestamp = -1;
     * private long endTimestamp = -1; private int amountForAverage; private
     * Range statmentRange; private String pattern;
     */
    // DBDataSource dataSource, int amountForAverage, Range
    // statmentRange,String
    // pattern

    private class ColumnDescriptionPanel extends JPanel {
        JTable table;
        JScrollPane scrollPane;
        JButton btnAdd, btnRemove, btnMoveUp, btnMoveDown;
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
            btnMoveUp = new JButton("Move Up");
            btnMoveDown = new JButton("Move Down");
            tblModel.setValueAt(0, 0, 0);
            tblModel.setValueAt(1, 1, 0);
            table = new JTable(tblModel);
            table.getColumnModel().getColumn(0).setMaxWidth(20);
            table.setPreferredScrollableViewportSize(
                new Dimension((int) table.getPreferredSize().getHeight(),
                    table.getRowHeight() * 6));
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getSelectionModel().addListSelectionListener(new TableSelectionListener());
            scrollPane = new JScrollPane(table);

            lblNoTimestamp = new JLabel("No timestamp column is selected.");
            lblNoTimestamp
                .setFont(new Font(lblNoTimestamp.getFont().getName(),
                    lblNoTimestamp.getFont().getStyle(), lblNoTimestamp.getFont()
                        .getSize() * 2));
            currentTimePanel = lblNoTimestamp;

            // layout
            this.setLayout(new MigLayout());
            this.add(scrollPane, "push, grow");
            this.add(btnAdd, "growx, flowy, aligny top, split 4");
            this.add(btnRemove, "growx, gapbottom unrelated");
            this.add(btnMoveUp, "growx");
            this.add(btnMoveDown, "growx, wrap, gapbottom unrel");
            this.add(lblNoTimestamp, "pushx, growx, wrap");

            ActionListener al = new ActionListenerImpl();
            btnAdd.addActionListener(al);
            btnRemove.addActionListener(al);
            btnMoveUp.addActionListener(al);
            btnMoveDown.addActionListener(al);
        }

        private void showTimePanel(int index) {
            this.remove(currentTimePanel);
            if (index < 0) {
                this.add(lblNoTimestamp, "pushx, growx, wrap");
                currentTimePanel = lblNoTimestamp;
            } else {
                currentTimePanel = timePanels.get(index);
                this.add(currentTimePanel, "pushx, pushy, growy, growx, wrap");
            }
            revalidate();
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

                    try {
                        Type colType =
                            (Type) tblModel.getValueAt(table.getSelectedRow(), typeIndex);

                        switch (controller.getSelectedSourceType()) {

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
                    } catch (Exception e1) {

                    }

                }
            }
        }

        private class ActionListenerImpl implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnAdd) {
                    Integer nextNum = tblModel.getRowCount();
                    tblModel.addRow(new Object[] { String.valueOf(nextNum), "", Type.IGNORE, "" });
                } else if (e.getSource() == btnRemove) {
                    CellEditor editor = table.getCellEditor();
                    if (editor != null)
                        editor.stopCellEditing();

                    int selRowIdx = table.getSelectedRow();
                    if (selRowIdx > -1) {
                        maybeRemoveTimePanel(selRowIdx);
                        tblModel.removeRow(selRowIdx);
                    }
                } else if (e.getSource() == btnMoveUp) {
                    // Only one of multiple selected rows is moved up
                    int selRow = table.getSelectedRow();
                    if (selRow > 0)
                        tblMoveRowAndSelection(selRow, selRow - 1);
                    else
                        // if this is the first row, move it to the bottom
                        tblMoveRowAndSelection(selRow, tblModel.getRowCount() - 1);
                } else if (e.getSource() == btnMoveDown) {
                    // Only one of multiple selected rows is moved up
                    int selRow = table.getSelectedRow();
                    // if this is the last row, move it to the top
                    if (selRow == tblModel.getRowCount() - 1)
                        tblMoveRowAndSelection(selRow, 0);
                    else
                        tblMoveRowAndSelection(selRow, selRow + 1);
                }
            }

            private void tblMoveRowAndSelection(int rowIdx, int to) {
                tblModel.moveRow(rowIdx, rowIdx, to);
                table.getSelectionModel().setSelectionInterval(to, to);
            }
        }
    }

    /**
     * Panel to specify Timestamp sepcs of csv
     */
    class TimespecPanel extends JPanel {
        JCheckBox chbApplyRegex;
        JRadioButton rdbDate, rdbCustDate;
        ButtonGroup btnGroup;
        JTextField txfRegexMatch, txfRegexReplace, txfDate, txfCustUnits,
            txfCustSeparator;

        /**
         * Constructor
         */
        public TimespecPanel() {
            initGUI();
        }

        /**
         * Inits UI
         */
        void initGUI() {
            this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Timestamp specifications"));
            chbApplyRegex = new JCheckBox("Apply Regex");
            JLabel lblRegexMatch = new JLabel("Match:");
            txfRegexMatch = new JTextField(20);
            JLabel lblRegexReplace = new JLabel("Replace:");
            txfRegexReplace = new JTextField(20);
            rdbDate = new JRadioButton("Date");
            rdbDate.setSelected(true);
            // controller.setSelectedDate(false);
            JLabel lblDate = new JLabel("Format:");
            txfDate = new JTextField(10);
            rdbCustDate = new JRadioButton("Custom");
            JLabel lblCustUnits = new JLabel("Unit:");
            txfCustUnits = new JTextField(10);
            JLabel lblCustSeparator = new JLabel("Separator:");
            txfCustSeparator = new JTextField(3);
            btnGroup = new ButtonGroup();
            btnGroup.add(rdbDate);
            btnGroup.add(rdbCustDate);
            // layout
            this.setLayout(new MigLayout("", "[][][][][][]"));
            //this.add(chbApplyRegex, "span 2");
            this.add(rdbDate, "span 2");
            this.add(rdbCustDate, "span 2, wrap");
            //this.add(lblRegexMatch);
            //this.add(txfRegexMatch);
            this.add(lblDate);
            this.add(txfDate);
            this.add(lblCustUnits);
            this.add(txfCustUnits, "wrap");
            //this.add(lblRegexReplace);
            //this.add(txfRegexReplace);
            this.add(lblCustSeparator, "skip 2");
            this.add(txfCustSeparator, "wrap");

        }
    }

    @Override
    public boolean canFinish() {

        return true;
    }

    @Override
    public void display() {

    }

    @Override
    public void finish() {

        controller.doImport();
        this.setVisible(false);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public WizardPanel next() {

        return null;
    }

    @Override
    public boolean validateFinish(List errors) {
        boolean validation = true;

        switch (controller.getSelectedSourceType()) {

        case CSV:
            ((CSVPanel) gui).validateAll(controller);
            break;
        }

        List<ValidationResult> errorResults = controller.getErrorValidations();
        if (!errorResults.isEmpty()) {

            validation = false;

            for (int i = 0; i < errorResults.size(); i++) {

                errors.add(errorResults.get(i).toString());
            }
        }
        return validation;
    }

    @Override
    public boolean validateNext(List errors) {
        // Never refered
        boolean validation = true;

        switch (controller.getSelectedSourceType()) {

        case CSV:
            ((CSVPanel) gui).validateAll(controller);
            break;
        }

        List<ValidationResult> errorResults = controller.getErrorValidations();
        if (!errorResults.isEmpty()) {

            validation = false;

            for (int i = 0; i < errorResults.size(); i++) {

                errors.add(errorResults.get(i).toString());
            }
        }
        return validation;
    }

    public void setCSVValues(String lineSep, String valueSep, DefaultTableModel csvModel,
        TableCellEditor typeEditor, TimestampFormat[] tsfs, String previewStart,
        String previewEnd) {

        ((CSVPanel) this.gui).txfElemSeparator.setText(valueSep);
        ((CSVPanel) this.gui).txfLineSeparator.setText(lineSep);
        ((CSVPanel) this.gui).columnDescriptionPanel.tblModel = csvModel;
        ((CSVPanel) this.gui).columnDescriptionPanel.table.setModel(csvModel);
        ((CSVPanel) this.gui).columnDescriptionPanel.table.getColumnModel().getColumn(2)
            .setCellEditor(typeEditor);

        for (int i = 0; i < csvModel.getRowCount(); i++) {
            ((CSVPanel) this.gui).columnDescriptionPanel.table.getSelectionModel()
                .setSelectionInterval(i, i);
        }
        for (int i = 0; i < tsfs.length; i++) {
            TimespecPanel tsp = ((CSVPanel) this.gui).columnDescriptionPanel.timePanels.get(i);
            tsp.txfDate
                .setText(
                tsfs[i].date);
            tsp.txfCustSeparator.setText(tsfs[i].customSeparator);
            tsp.txfCustUnits.setText(tsfs[i].customUnits);
            if (tsfs[i].isCustomDate)
                tsp.rdbCustDate.setSelected(true);
            else
                tsp.rdbDate.setSelected(true);
        }
    }

    public void showWarningDialog(List<ValidationResult> results) {
        // Never Refer
        final JDialog dialog = new JDialog((Frame) null, "Warning", true);
        dialog.setLayout(new MigLayout("flowy"));
        JLabel label0 = new JLabel("Refresh is not possible.");
        JLabel label1 = new JLabel("The following requirements have not been met:");
        JList<ValidationResult> list =
            new JList<ValidationResult>(results.toArray(new ValidationResult[] {}));
        JScrollPane scrollPane = new JScrollPane(list);
        JButton buttonClose = new JButton("Close");
        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(label0, "gap -5");
        dialog.add(label1, "gapbottom unrelated");
        dialog.add(scrollPane);
        dialog.add(buttonClose, "tag cancel");
        dialog.pack();
        dialog.setVisible(true);
    }

}
