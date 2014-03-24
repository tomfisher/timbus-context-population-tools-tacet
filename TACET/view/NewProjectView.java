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

import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class NewProjectView extends JFrame {

    /**
     * The instance of NewProjectView
     */
    private static NewProjectView instance;

    /**
     * The layoutmanager for the gui
     */
    private MigLayout layout = new MigLayout();

    /**
     * List of sensors
     */
    private JList sensorList;

    /**
     * TextField to give the new Project a name
     */
    JTextField projectName = new JTextField();

    /**
     * Path to the File of the annotations
     */
    JTextField annotationFile = new JTextField();

    /**
     * Button to select an Annotation File
     */
    private JButton selectAnnotationFile = new JButton("Browse");

    /**
     * Button to add the selected sensor to the sensorlist
     */
    private JButton addSensor = new JButton("Add Sensor");

    /**
     * Button to delete the selected sensor of the sensorlsit
     */
    private JButton deleteSensor = new JButton("Delete Sensor");

    /**
     * Button to create the new Project
     */
    private JButton createProject = new JButton("Create Project");

    /**
     * Button to cancel and go back to main window
     */
    private JButton cancel = new JButton("Cancel");

    /**
     * private constructor of NewProjectView
     */
    private NewProjectView() {

        this.setTitle("New Project");
        this.setLayout(layout);
        this.setSize(600, 500);
        this.initComponents();

    }

    /**
     * inits the components of the gui
     */
    private void initComponents() {

        this.add(new Label("Project Name:"), "split 2");
        //     projectName.setPreferredSize(new Dimension(this.getWidth()-100, 20));
        this.add(projectName, "span 4, pushx ,growx, wrap 50");

        //Annotation File
        this.add(new JLabel("Annotation File"), "wrap 15");
        this.add(annotationFile, "span 2, growx, pushx, height pref!");
        this.add(selectAnnotationFile, "wrap 50");

        //Sensor List
        this.add(new JLabel("Sensorlist"), "wrap 15");
        sensorList = new JList();
        //        sensorList.setPreferredSize(new Dimension(this.getWidth()-50, 100));
        sensorList.setToolTipText("sensorlist");

        this.add(sensorList, "span 2 2, grow");
        this.add(addSensor, " span, grow, wrap");
        this.add(deleteSensor, "span, grow, wrap 100");

        // this.add(new JPanel(), "wrap 20");
        // this.add(new JPanel(), "span 2");


        //Create and Cancel Button
        this.add(createProject, "align right");
        this.add(cancel, "span, grow");

    }


    /**
     * Register Methods for ActionListener
     **/
    public void registerListenerToBrowseButton(ActionListener listener){

        this.selectAnnotationFile.addActionListener(listener);
    }

    public void registerListenerToAddSensorButton(ActionListener listener){

        this.cancel.addActionListener(listener);
    }

    public void registerListenerToDeleteSensorButton(ActionListener listener){

        this.cancel.addActionListener(listener);
    }

    public void registerListenerToCreateProjectButton(ActionListener listener){

        this.cancel.addActionListener(listener);
    }

    public void registerListenerToCancelButton(ActionListener listener){

        this.cancel.addActionListener(listener);
    }

    /**
     * resets all fields in UI
     */
    public void resetFields() {
        // TODO Auto-generated method stub
        this.projectName.setText("");
        this.annotationFile.setText("");
        this.sensorList.removeAll();

    }

    /**
     * Retruns the one and only instance of NewProjectView
     * @return instance
     */
    public static NewProjectView getInstance(){

        if(instance == null){

            instance = new NewProjectView();
        }

        return instance;
    }

    public void setAnnotationPath(String name) {
        this.annotationFile.setText(name);

    }

}
