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

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import squirrel.model.InstancesAdapter;
import squirrel.model.MetaClassifier;
import squirrel.model.ModelFacade;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.util.Range;
import squirrel.view.ClassificatorView;
import squirrel.view.SortedComboBox;
import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;

public class ClassificatorController {
    private ModelFacade modelFacade;
    private ClassificatorView classificatorView;
    private boolean isConfigured = false;
    private InstancesAdapter trainingInstance;
    private FilteredClassifier filteredClassifier;
    private List<MetaClassifier> classifierList;

    public ClassificatorController(ModelFacade modelFacade) {
        this.modelFacade = modelFacade;
        modelFacade.setClassificatorController(this);
        this.classificatorView = new ClassificatorView(this);
        this.classifierList = new ArrayList<MetaClassifier>();
        try {
            fillClassifierList();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setDefaultValues();
    }

    private void setDefaultValues() {
        DataSource<? extends DataColumn> dataSource = modelFacade.getCurrentDataSource();

        Vector<String> list = new Vector();
        for (DataColumn dc : dataSource.getAnnotationColumns()) {
            if(!dc.getType().isTrainAnnotation())
                list.add(dc.getName());
        }

        DefaultTableModel defModel =
            new DefaultTableModel(new String[] { "name", "Type", "Select" }, 0);
        for (int i = 0; i < dataSource.getNumberOfColumns(); i++) {
            defModel.addRow(new Object[] { dataSource.getDataColumn(i).getName(),
                modelFacade.getCurrentDataSource().getDataColumn(i).getType(), true });

        }
        classificatorView.setDefaultValues(new DefaultComboBoxModel(list), defModel);
        classificatorView.fillClassifierSelect(classifierList);
    }

    public void presentDialog(Frame owner) {
        classificatorView.showAsDialog(owner);
    }

    public boolean isConfigured() {
        return isConfigured;
    }

    public void configureClassifier() {
        for (int i : classificatorView.getSelectedTracks()) {
            modelFacade.getCurrentDataSource().getDataColumn(i).setSelected(true);
        }
        try {
            validate();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (isConfigured) {
            try {
                trainClassifier(modelFacade.getCurrentDataSource().annotationIndexToColumn(
                    classificatorView.getClassIdx()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void validate() throws Exception {
        if (validateClassifier() && validateClassTrack() && validateTrainingTrack()) {
            isConfigured = true;
        } else {
            isConfigured = false;
        }
    }

    private boolean validateClassTrack() {
        int idx =
            modelFacade.getCurrentDataSource().annotationIndexToColumn(
                classificatorView.getClassIdx());
        if (classificatorView.getSelectedTracks().contains(idx)) {
            return true;
        }
        JOptionPane.showMessageDialog(classificatorView,
            "The track to classify must be selected in the table below",
            "Classifier warning",
            JOptionPane.WARNING_MESSAGE);
        return false;
    }

    private boolean validateTrainingTrack() {
        for (int i : classificatorView.getSelectedTracks()) {
            if (modelFacade.getCurrentDataSource().getDataColumn(i).getType().isTrainAnnotation()) {
                return true;
            }
        }
        JOptionPane.showMessageDialog(classificatorView,
            "The trainings track must be selected in the table below",
            "Classifier warning",
            JOptionPane.WARNING_MESSAGE);
        return false;
    }

    private boolean validateClassifier() throws Exception {
        if (classificatorView.getClassifier() != null) {
            return true;
        }
        JOptionPane.showMessageDialog(classificatorView,
            "Select a classifier",
            "Classifier warning",
            JOptionPane.WARNING_MESSAGE);
        return false;
    }

    public void trainClassifier(int classIdx) throws Exception {
        trainingInstance = new InstancesAdapter(modelFacade, classIdx);
        trainingInstance.trainingsInstace();
        filteredClassifier = new FilteredClassifier();
        filteredClassifier.setClassifier(classificatorView.getClassifier());
        // if(classificatorView.normalizeValues()) {
        // Normalize norm = new Normalize();
        // norm.setInputFormat(trainingInstance);
        // Instances processed_train = Filter.useFilter(trainingInstance, norm);
        // processed_train.setClassIndex(classIdx);
        // filteredClassifier.buildClassifier(processed_train);
        // } else {
        filteredClassifier.buildClassifier(trainingInstance);

        // }
        classify();
    }

    public void classify() {
        //System.out.println("================================================== Its starting");
        Range fullRange = new Range(modelFacade.getStartTimeStamp(), modelFacade.getEndTimeStamp());
        InstancesAdapter instanceToClassify =
            new InstancesAdapter(modelFacade, trainingInstance.classIndex());

        long offset =
            (long) ((((modelFacade.getEndTimeStamp() - modelFacade.getStartTimeStamp()) / modelFacade
                .getAverageDistance()) / 100) * modelFacade.getAverageDistance());

        long start = modelFacade.getStartTimeStamp();
        long end = start + offset;
        for (int i = 0; i < 100; i++) {
            Range range = new Range(start, end);
            instanceToClassify.instanceToClassify(range);
            start = end;
            if (i == 98) {
                end = modelFacade.getEndTimeStamp();
            } else {
                end += offset;
            }
        }

        try {
            modelFacade.getAnnotationModel().updateAnnotations(trainingInstance,
                instanceToClassify, filteredClassifier);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        modelFacade.mergeAnnotations(trainingInstance.getOrigAnnotIdx(), fullRange);
    }

    private void fillClassifierList() throws URISyntaxException {
        CodeSource codeSource = ClassificatorController.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();
        String jar = jarDir + "/squirrel-1.0-SNAPSHOT-jar-with-dependencies.jar";

        try {
            JarInputStream jarIS = new JarInputStream(new FileInputStream(
                jar));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarIS.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if (jarEntry.getName().endsWith(".class")
                    && jarEntry.getName().startsWith("weka/classifiers")) {
                    URL u = new URL("jar", "", jar);
                    URLClassLoader jarLoader = new URLClassLoader(new URL[] { u });

                    String className =
                        jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                    className = className.replaceAll("/", "\\.");
                    Class c = jarLoader.loadClass(className);
                    String[] split = className.split("\\.");
                    String name = null;
                    String type = null;
                    if (split.length == 3) {
                        name = split[2];
                    } else if (split.length == 4) {
                        type = split[2];
                        name = split[3];
                    }
                    if (!Modifier.isAbstract(c.getModifiers())
                        && !Modifier.isInterface(c.getModifiers())) {
                        try {
                            Object o = c.newInstance();
                            if (o instanceof Classifier) {
                                classifierList.add(new MetaClassifier(name, className, type));
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            }
            jarIS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
