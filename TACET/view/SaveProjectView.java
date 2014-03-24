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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import squirrel.controller.ProjectController;
import squirrel.controller.ValidationResult;
import squirrel.view.LoadProjectView.ActionListenerImpl;

public class SaveProjectView extends JPanel {
    ProjectController controller;
    private JButton btnCancel, btnSave;
    private JTextField txfPath;
    private JButton btnBrowse;
    private JFileChooser fchBrowse;
    private JDialog dialog;
    private JLabel lblLocation;

    public SaveProjectView(ProjectController projectController) {
        this.controller = projectController;
        initGUI();
    }

    private void initGUI() {
        btnCancel = new JButton("Cancel");
        btnCancel.setMnemonic('C');
        btnSave = new JButton("Save");
        btnSave.setMnemonic('S');
        fchBrowse = new JFileChooser(System.getProperty("user.home"));
        fchBrowse.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
        txfPath = new JTextField(20);
        lblLocation = new JLabel("Location:");

        btnBrowse = new JButton("Browse");
        btnBrowse.setMnemonic('B');
        creatActionListener();
        this.setLayout(new MigLayout("", "", "[][nogrid]"));
        this.add(lblLocation);
        this.add(txfPath);
        this.add(btnBrowse, "gapbottom unrel, wrap");
        this.add(btnSave, "tag apply");
        this.add(btnCancel, "tag cancel");
    }

    private void creatActionListener() {
        ActionListener al = new ActionListenerImpl();
        btnBrowse.addActionListener(al);
        btnCancel.addActionListener(al);
        btnSave.addActionListener(al);
    }

    public class ActionListenerImpl implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnSave) {
                    validateAll();
                    controller.saveProject();
            } else if(e.getSource() == btnCancel){
                dialog.dispose();
            } else if(e.getSource() == btnBrowse){
                int result = fchBrowse.showSaveDialog(SaveProjectView.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = fchBrowse.getSelectedFile();
                    txfPath.setText("file://" + fileToOpen.getPath());
                }
            }
        }

    }

    public void showAsDialog(Frame owner) {
        dialog = new JDialog(owner, "Save Dialog", true);
        dialog.add(this, BorderLayout.CENTER);
        dialog.setSize(new Dimension(350, 150));
        dialog.setVisible(true);

    }

    public void showWarningDialog(List<ValidationResult> results) {
        final JDialog dialog = new JDialog((Frame) null, "Warning", true);
        dialog.setLayout(new MigLayout("flowy"));
        JLabel label0 = new JLabel("Save is not possible.");
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

    /**
     *
     */
    void validateAll() {
        controller.validModel();
        controller.validateURL(txfPath.getText());

    }

    public void disposeDialog() {
        if (this.dialog != null)
            dialog.dispose();
    }
}
