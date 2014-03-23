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

package squirrel.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.*;

import squirrel.view.NewProjectView;

public class NewProjectController {

    private static NewProjectController instance;
    private NewProjectView view;

    /**
     * Constructor - Creates a new NewProject View and inits the ActionListener
     * @param master - Link to the MasterController
     */
    private NewProjectController(){

        this.view = NewProjectView.getInstance();
        this.view.setVisible(true);
        this.initListener();
    }

    /**
     * Inits all the ActionListner and registers them on the view components
     */
    private void initListener(){

        /**
         * Browse ActionListener
         */
        this.view.registerListenerToBrowseButton(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Opens FileChooser to find Annotation File
                JFileChooser annotationFilePath = new JFileChooser();
                annotationFilePath.setFileFilter( new FileFilter()
                {

                    @Override public String getDescription()
                    {
                      return "Texte";
                    }
                    @Override
                    public boolean accept(File f) {

                        return f.isDirectory() ||
                                f.getName().toLowerCase().endsWith( ".txt" );
                    }
                  } );
                  int state = annotationFilePath.showOpenDialog( null );
                  if ( state == JFileChooser.APPROVE_OPTION )
                  {
                    File file = annotationFilePath.getSelectedFile();
                   view.setAnnotationPath(file.getAbsolutePath());
                  }

            }
        });


        /**
         * Cancel ActionListener
         */
        this.view.registerListenerToCancelButton(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Opens FileChooser to find Annotation File
                view.resetFields();
                view.setVisible(false);

            }
        });
    }

    public static NewProjectController getInstance(){

        if(instance == null){

            instance = new NewProjectController();
        }

        return instance;
    }
}
