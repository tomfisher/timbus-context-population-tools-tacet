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
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import net.miginfocom.swing.MigLayout;
import jwf.Wizard;
import jwf.WizardListener;
import jwf.WizardPanel;
import squirrel.controller.ImportController;
import squirrel.controller.TimestampFormat;
import squirrel.controller.ValidationResult;
import squirrel.view.WarningView;
import squirrel.view.ImportWizard.FileFormatPanel;

public class ImportWizard extends WarningView implements WizardListener {

    private static JFrame frame;
    private ImportController controller;
    private FileFormatPanel nextPanel;
    private Wizard importWizard;

    public ImportWizard(ImportController controller){
        this.controller = controller;
        frame = new JFrame("Import Wizard");
        frame.addWindowListener(createAppCloser());
        importWizard = new Wizard();
        importWizard.addWizardListener(this);
        frame.setContentPane(importWizard);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(550,400));
        nextPanel = new FileFormatPanel(controller, frame);
        importWizard.start(nextPanel);

    }



    private static WindowListener createAppCloser() {
        return new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                frame.dispose();
            }
        };
    }

    /** Called when the wizard finishes.
     * @param wizard the wizard that finished.
     */
    @Override
    public void wizardFinished(Wizard wizard) {
        this.frame.dispose();
    }


    /** Called when the wizard is cancelled.
     * @param wizard the wizard that was cancelled.
     */
    @Override
    public void wizardCancelled(Wizard wizard) {
        this.frame.dispose();
    }


    /** Called when a new panel has been displayed in the wizard.
     * @param wizard the wizard that was updated
     */
    @Override
    public void wizardPanelChanged(Wizard wizard) {
    }


//("\\n", ",", url.toString(), csvModel, typeEditor, tsfs, "1000", "1500");
    public void setCSVValues(String lineSep, String valueSep, String loc,
            DefaultTableModel csvModel, TableCellEditor typeEditor, TimestampFormat[] tsfs,
            String previewStart, String previewEnd) {

        nextPanel.setCSVValues(lineSep, valueSep, loc, csvModel, typeEditor, tsfs, previewStart, previewEnd);


    }

}
