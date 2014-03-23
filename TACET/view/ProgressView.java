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
