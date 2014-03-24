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
