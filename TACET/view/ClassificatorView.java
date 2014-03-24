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
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;
import squirrel.controller.ClassificatorController;
import squirrel.model.MetaClassifier;
import weka.classifiers.Classifier;

public class ClassificatorView extends JPanel {

    private JLabel classTrackLbl;
    private JComboBox<?> classTrackSelect;
    private JLabel tracksLbl;
    private JTable table;
    private JButton classifyBtn;
    private JButton cancelBtn;
    private JDialog dialog;
    private JLabel classifierSelectLbl;
    private SortedComboBox<MetaClassifier> classifierSelect;
    private JLabel normalizeLbl;
    private JCheckBox normalizeBox;

    private ClassificatorController controller;

    public ClassificatorView(ClassificatorController controller) {
        this.controller = controller;
        initGui();
        addListeners();
    }

    private void initGui() {
        this.classTrackLbl = new JLabel("Select Classification Track:");
        this.classTrackSelect = new JComboBox();
        this.tracksLbl = new JLabel("Select Tracks:");
        this.table = new JTable(new DefaultTableModel(new String[] {"name", "Type", "Select"}, 0)) {
            @Override
            public Class getColumnClass(int column) {
                if(column == 2){
                    return Boolean.class;
                }
                return super.getColumnClass(column);
            }
        };
        this.classifyBtn = new JButton("Classify");
        this.cancelBtn = new JButton("Cancel");
        this.classifierSelectLbl = new JLabel("Select classifier:");
        this.classifierSelect = new SortedComboBox<MetaClassifier>();
        this.normalizeLbl = new JLabel("Normalize numeric values:");
        this.normalizeBox = new JCheckBox();

        this.setLayout(new MigLayout("", "", "[][pref][]"));
        this.add(classTrackLbl, "growx, pushx");
        this.add(classifyBtn, "growx, wrap");
        this.add(classTrackSelect, "growX, pushX");
        this.add(cancelBtn, "growx, wrap");
        this.add(tracksLbl, "wrap");
        this.add(table, "growX, pushX, growY, pushY, wrap");
        this.add(classifierSelectLbl, "wrap");
        this.add(classifierSelect, "growX, pushX, wrap");
//        this.add(normalizeLbl);
//        this.add(normalizeBox);
    }

    public void fillClassifierSelect(List<MetaClassifier> list){
        for(MetaClassifier m : list) {
            classifierSelect.addClassifier(m);
        }

//        CodeSource codeSource = SortedComboBox.class.getProtectionDomain().getCodeSource();
//        File jarFile = new File(codeSource.getLocation().toURI().getPath());
//        String jarDir = jarFile.getParentFile().getPath();
//        String jar = jarDir + "/squirrel-1.0-SNAPSHOT-jar-with-dependencies.jar";
//
//        try {
//            JarInputStream jarIS = new JarInputStream(new FileInputStream(
//                jar));
//            JarEntry jarEntry;
//
//            while (true) {
//                jarEntry = jarIS.getNextJarEntry();
//                if (jarEntry == null) {
//                    break;
//                }
//                if (jarEntry.getName().endsWith(".class")
//                    && jarEntry.getName().startsWith("weka/classifiers")) {
//                    URL u = new URL("jar", "", jar);
//                    URLClassLoader jarLoader = new URLClassLoader(new URL[] { u });
//
//                    String className =
//                        jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
//                    className = className.replaceAll("/", "\\.");
//                    Class c = jarLoader.loadClass(className);
//                    if (!Modifier.isAbstract(c.getModifiers())
//                        && !Modifier.isInterface(c.getModifiers())) {
//                        try {
//                            Object o = c.newInstance();
//                            if (o instanceof Classifier) {
//                                Classifier classifier = (Classifier) o;
//                                classifierSelect.addClassifier(classifier);
//                            }
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }
//            }
//            jarIS.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void showAsDialog(Frame owner) {
        dialog = new JDialog(owner, "Classificator Dialog", true);
        dialog.add(this, BorderLayout.CENTER);
        dialog.setSize(new Dimension(500, 400));
        dialog.setVisible(true);
    }

    public void setDefaultValues(ComboBoxModel annotationTrackList, TableModel tableModel) {
        classTrackSelect.setModel(annotationTrackList);
        table.setModel(tableModel);
    }

    public void disposeDialog() {
        if(dialog != null) {
            dialog.dispose();
        }
    }

    public void addListeners() {
        cancelBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
               disposeDialog();
            }

        });
        classifyBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                controller.configureClassifier();
                if(controller.isConfigured())
                    disposeDialog();
            }

        });
    }

    public int getClassIdx() {
        return classTrackSelect.getSelectedIndex();
    }

    public ArrayList<Integer> getSelectedTracks() {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < table.getRowCount(); i++) {
            if((boolean) table.getValueAt(i, 2)) {
                list.add(i);
            }
        }
        return list;
    }

    public Classifier getClassifier() throws Exception {
        MetaClassifier metaClassifier = (MetaClassifier) classifierSelect.getSelectedItem();
        CodeSource codeSource = ClassificatorController.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();
        String jar = jarDir + "/squirrel-1.0-SNAPSHOT-jar-with-dependencies.jar";

        URL u = new URL("jar", "", jar);
        URLClassLoader jarLoader = new URLClassLoader(new URL[] { u });

        Class c = jarLoader.loadClass(metaClassifier.getClassName());

        Classifier classifier = (Classifier)c.newInstance();

        return classifier;
    }

    public boolean normalizeValues() {
        return normalizeBox.isSelected();
    }
}
