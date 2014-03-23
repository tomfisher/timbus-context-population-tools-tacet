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

package squirrel.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import squirrel.model.Annotation;
import squirrel.model.AnnotationModelImpl;

/**
 * To save and load the Project
 * @author marc
 *
 */
public class ProjectIO {
    private URL projectLocation;
    private String name;
    /**
     * Construct the ProjectIO class with parameter to save
     * @param projectLocation the path to the project
     */
    public ProjectIO(URL projectLocation, String name) {
        this.projectLocation = projectLocation;
        this.name = name;

    }

    /**
     * To Save the Project
     * @param dataSource the dataSource of the Project
     * @param annotations the Annotations of the Project
     */
    public void saveProject(DataSource dataSource, AnnotationModelImpl model) {
        ProjectSaveHolder sh = new ProjectSaveHolder(dataSource, model);
        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream(projectLocation.getPath()+"/"+name);

            out = new ObjectOutputStream(fos);
            out.writeObject(sh);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To read the saved DataSource
     * @return DataSource dataSource to reload the data
     */
    public DataSource readDataSourceIntern() {
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = new FileInputStream(projectLocation.getPath()+"/"+name);
            in = new ObjectInputStream(fis);
            ProjectSaveHolder sh = (ProjectSaveHolder) in.readObject();
            DataSource<DataColumn> ds = sh.getDataSource();
            in.close();
            return ds;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * Reads all annotations from the file with the given filename in this
     * project location.
     * @return annotations from the file with given filename
     */
    public AnnotationModelImpl readModelIntern() {
        List<Annotation[]> anno = null;
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = new FileInputStream(projectLocation.getPath()+"/"+name);
            in = new ObjectInputStream(fis);
            ProjectSaveHolder sh = (ProjectSaveHolder) in.readObject();
            in.close();
            return sh.getAnnotations();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
