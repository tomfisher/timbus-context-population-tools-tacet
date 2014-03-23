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

import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import squirrel.controller.ImportController;
import squirrel.controller.TimestampFormat;
import squirrel.controller.ValidationResult;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import net.miginfocom.swing.MigLayout;
import jwf.WizardPanel;

class FileFormatPanel extends WizardPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private TextField txfLocation;
    private JButton cmdBrowse;
    private ButtonGroup fileFormat = new ButtonGroup();
    private JRadioButton csvButton = new JRadioButton("CSV File");
    private ImportController controller;
    private JFrame superFrame;
    private SpecPanel csvNext;

    private JPanel gui = new JPanel();
    private JTextField txfElemSeparator;
    private JLabel lblElemSeparator, lblHeader;
    private JCheckBox cHeader;

    public FileFormatPanel(ImportController controller, JFrame container) {
        this.controller = controller;
        this.superFrame = container;
        this.setLayout(new MigLayout());
        this.initGui();
        add(gui);

        csvNext = new SpecPanel(controller, superFrame);

    }

    private void initGui() {

        JLabel lablogo = new JLabel();
        lablogo.setIcon(new javax.swing.ImageIcon(FileFormatPanel.class.getClassLoader()
            .getResource("squirrel/resources/teco.png")));

        lblElemSeparator = new JLabel("Element Separator:");
        lblHeader = new JLabel("Header");

        txfElemSeparator = new JTextField(3);
        txfElemSeparator.setText(",");
        cHeader = new JCheckBox();
        cHeader.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (cHeader.isSelected()) {

                    lblElemSeparator.setVisible(true);

                    txfElemSeparator.setVisible(true);
                    txfElemSeparator.setText("");
                } else {

                    lblElemSeparator.setVisible(false);

                    txfElemSeparator.setVisible(false);
                }
            }
        });

        gui.setLayout(new MigLayout());
        gui.add(lablogo, "wrap 50, w 390!, h 100!, span 5");
        gui.add(new Label("Choose your file formate:"), "wrap, span 2");

        this.fileFormat.add(csvButton);

        gui.add(csvButton, "wrap");

        this.txfLocation = new TextField();
        this.cmdBrowse = new JButton("Browse");
        this.cmdBrowse.addActionListener(new ActionListenerImpl());

        gui.add(new Label("Please specify location of file:"), "wrap");
        gui.add(txfLocation, "pushx, growx");
        gui.add(cmdBrowse, "wrap");

        if (controller.getDebug()) {
            this.setDefaultValues();
        }

        gui.add(lblHeader, "wrap");
        gui.add(cHeader, "wrap");

        gui.add(lblElemSeparator, "wrap");

        gui.add(txfElemSeparator);

        lblElemSeparator.setVisible(false);

        txfElemSeparator.setVisible(false);

    }

    private void setDefaultValues() {

        this.csvButton.setSelected(true);

    }

    private class ActionListenerImpl implements ActionListener {
        JFileChooser fchBrowse = new JFileChooser((new File("")).getAbsolutePath());

        @Override
        public void actionPerformed(ActionEvent e) {

            int result = fchBrowse.showOpenDialog(FileFormatPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {

                File fileToOpen = fchBrowse.getSelectedFile();
                txfLocation.setText("file://" + fileToOpen.getPath());
            }

        }
    }

    /** Called when the panel is set. */
    @Override
    public void display() {
        this.setPreferredSize(new Dimension(1000, 1000));
    }

    /**
     * Is there be a next panel?
     * @return true if there is a panel to move to next
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Called to validate the panel before moving to next panel.
     * @param list a List of error messages to be displayed.
     * @return true if the panel is valid,
     */
    @Override
    public boolean validateNext(List list) {

       this.validateAll();

        List<ValidationResult> errorResults = controller.getErrorValidations();

        if (!errorResults.isEmpty()) {

            for (int i = 0; i < errorResults.size(); i++) {

                list.add(errorResults.get(i).reason);
            }
            // eelist = errorResults;//this.showWarningDialog(errorResults);
            return false;
        }
        if (this.csvButton.isSelected() ) {
            DefaultTableModel dft = controller.analyseLine(cHeader.isSelected());
            TableCellEditor typeEditor =
                new DefaultCellEditor(new JComboBox<>(DataColumn.Type.values()));
            csvNext.setCSVValues("", controller.getElemSeperator(), dft, typeEditor,
                new TimestampFormat[] {new TimestampFormat(controller.getGuestTimestampFormat())}, "", "");

        }
        return true;
    }

    private void validateAll() {

        if (this.csvButton.isSelected()) {
            controller.setValidationController(DataSource.Type.CSV);
            controller.validateCSVElemSep(txfElemSeparator.getText());

            controller.setSourceType(0);
            this.controller.validateLocation(txfLocation.getText());
        }

    }

    /** Get the next panel to go to. */
    @Override
    public WizardPanel next() {

        WizardPanel next = null;

        switch (controller.getSelectedSourceType()) {
        case CSV:
            next = csvNext;
            break;
        }
        return next;
    }

    /**
     * Can this panel finish the wizard?
     * @return true if this panel can finish the wizard.
     */
    @Override
    public boolean canFinish() {
        return false;
    }

    /**
     * Called to validate the panel before finishing the wizard. Should return
     * false if canFinish returns false.
     * @param list a List of error messages to be displayed.
     * @return true if it is valid for this wizard to finish.
     */
    @Override
    public boolean validateFinish(List list) {
        return false;
    }

    /** Handle finishing the wizard. */
    @Override
    public void finish() {}

    public void setCSVValues(String lineSep, String valueSep, String loc,
        DefaultTableModel csvModel, TableCellEditor typeEditor, TimestampFormat[] tsfs,
        String previewStart, String previewEnd) {

        this.txfLocation.setText(loc);
        csvNext.setCSVValues(lineSep, valueSep, csvModel, typeEditor, tsfs, previewStart,
            previewEnd);
    }

}
