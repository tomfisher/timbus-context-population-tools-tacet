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
