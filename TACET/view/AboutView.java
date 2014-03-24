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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;

public class AboutView extends JPanel{

    private JLabel lablogo;
    private JTextPane txpdeveloped;
    private JButton btnClose;
    private JDialog dialog;
    public AboutView() {

        initGUI();
    }

    /**
     * Init AboutView
     */
    private void initGUI() {
        lablogo = new JLabel();
        lablogo.setSize(390, 100);
        lablogo.setIcon(new javax.swing.ImageIcon(AboutView.class.getClassLoader().getResource("squirrel/resources/teco.png")));
        txpdeveloped = new JTextPane();
        txpdeveloped.setContentType("text/html");
        txpdeveloped.setText("<html><h2>Time Series Annotation and Context Extraction Tool (TACET)</h2>"
        		+ "<h3><p>This tool was funded by the European Commission under "
        		+ "the ICT project \"TIMBUS\" (Project No.269940, FP7-ICT-2009-6) "
        		+ "within the 7th Framework Programme. "
        		+ "It was developed at KIT, TECO. </p></h3>"
                + "<p>Supervisors:<br><ul>"
                + "<li>Anja Bachmann</li>"
                + "<li>Till Riedel</li></ul></p>"
        		+ "<p>Contributors:<br><ul> " +
        		"<li>Alexander Baier</li> " +
        		"<li>Sebastian Gieße</li> " +
        		"<li>Christian Herlemann</li> " +
        		"<li>Olivier Boder</li> " +
        		"<li>Gergely S&oacute;ti</li> " +
        		"<li>Marc Gassenschmidt</li></ul></p>"
        		+ "</html>");
        txpdeveloped.setEditable(false);
        btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                disposeDialog();
            }
        });
        this.setLayout(new MigLayout(""));
        this.add(lablogo, "wrap,growx,pushx");
        this.add(txpdeveloped, "wrap,grow,push");
        this.add(btnClose, "tag cancel");

    }

    public void showAsDialog(Frame owner) {
        dialog = new JDialog(owner, "About", true);
        dialog.add(this, BorderLayout.CENTER);
        dialog.setSize(new Dimension(650, 600));
        dialog.setVisible(true);
    }

    public void disposeDialog() {
        if (this.dialog != null)
            dialog.dispose();
    }



}
