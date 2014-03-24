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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import squirrel.controller.ValidationResult;

public class WarningView extends JPanel {

    public WarningView(){

    }

    public void showWarningDialog(List<ValidationResult> results) {
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
