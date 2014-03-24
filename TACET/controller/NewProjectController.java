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
