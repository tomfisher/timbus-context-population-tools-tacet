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
