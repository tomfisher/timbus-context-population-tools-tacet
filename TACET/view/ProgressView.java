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
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class ProgressView implements PropertyChangeListener{

    private JDialog dialog;

    private Dialog parent;

    private JPanel panel;
    private JProgressBar pgbProgress;
    private JLabel lblText;
    public JButton btnCancel, btnClose;

    private String title;
    private String text;

    public ProgressView(String title, String text) {
        this.title = title;
        this.text = text;
        initGUI();
        addActionListener();
    }

    private void initGUI() {
        lblText = new JLabel(text);
        pgbProgress = new JProgressBar(SwingConstants.HORIZONTAL);
        pgbProgress.setValue(0);
        pgbProgress.setStringPainted(true);
        btnClose = new JButton("Close");
        btnClose.setToolTipText("Close this dialog, but do not interrupt the process.");
//        btnCancel = new JButton("Cancel");
//        btnCancel.setToolTipText("Cancel the current process and close this dialog.");

        panel = new JPanel(new MigLayout());
        panel.add(lblText, "span, wrap");
        panel.add(pgbProgress, "span, growx, pushx, wrap");
        panel.add(btnClose, "tag ok, wrap");
       // panel.add(btnCancel, "tag cancel, wrap");
    }

    public void presentAsDialog(Frame owner) {
        dialog = new JDialog(owner, title);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setVisible(true);
    }

    public void presentAsDialog(Dialog owner) {
        parent = owner;
        dialog = new JDialog(owner, title);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int state = (Integer)evt.getNewValue() + 1;
            pgbProgress.setValue(state);
            if(state == 100) {
                lblText.setText("Export finished.");
            }
        }

    }

    private void addActionListener() {
        this.btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dialog.dispose();
                if(!(parent == null)) {
                    parent.dispose();
                }

            }

        });
    }
}
