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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import squirrel.controller.ValidationResult;
import squirrel.model.Annotation;
import squirrel.model.AnnotationFactory;
import squirrel.model.AnnotationModel;
import squirrel.model.ModelFacade;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.util.Range;

public class CreateAnnotationView extends JPanel {

    /**
     *
     */
    private JDialog dialog = null;

    /**
     * Swing components of the ui
     */
    private JSpinner jspFrom, jspTo;
    private JList<DataColumn> lstAnnotationTracks;
    private JSpinner jspNumberValue;
    private JComboBox<String> cbbValues;
    private JPanel pnlCurrentValueComponent;
    private JButton btnAnnotate, btnCancel, btnDelete;

    /**
     * Data set
     */
    private DataSource<? extends DataColumn> dataSource;
    private AnnotationFactory annotFactory;

    /**
     * vector of all possible annotations
     */
    private Vector<DataColumn> annotationColumns;

    /**
     * Range of new annotation
     */
    private Range parsedRange;

    /**
     * the new annotation
     */
    private Annotation newAnnotation;

    private AnnotationModel annotModel;
    private boolean editing = false;

    /**
     * Property Listener
     */
    private PropertyChangeListener propertyListener = new PropertyChangeListener() {
        // this is only a dummy implementation
        @Override
        public void propertyChange(PropertyChangeEvent evt) {}
    };

    /**
     * Constructor of Create Annotation view
     * @param model - instance of ModelFacade representing the model of mvc
     */
    public CreateAnnotationView(ModelFacade model) {
        this(model, null);
    }

    /**
     * Constructor of Create Annotation view
     * @param model - instance of ModelFacade representing the model of mvc
     * @param annotation - pre selected with shortcuts
     */
    public CreateAnnotationView(ModelFacade model, Annotation annotation) {
        this.editing = annotation == null ? false : true;
        this.setDataSource(model.getCurrentDataSource());
        this.annotModel = model.getAnnotationModel();
        this.initGUI();
        this.setSelectedTrack(0);
        this.newAnnotation = annotation;
        if (editing) {
            setSelectedRange(annotation.getRange());
        }
    }

    /**
     * Sets a new data set
     * @param dataSource - new data set
     */
    public void setDataSource(DataSource<? extends DataColumn> dataSource) {
        this.dataSource = dataSource;
        this.annotFactory = dataSource.getAnnotationFactory();
        this.annotationColumns = new Vector<DataColumn>();
        for (DataColumn dc : dataSource.getAnnotationColumns()) {
            annotationColumns.add(dc);
        }
    }

    /**
     * Selects track no @code track
     * @param track - number of annotation track
     */
    public void setSelectedTrack(int track) {
        this.lstAnnotationTracks.setSelectedIndex(track);
        this.updateValueComponents();
    }

    /**
     * Sets the Range for the annotation
     * @param range - of annotation
     */
    public void setSelectedRange(Range range) {
        jspFrom.setValue(range.getStart());
        jspTo.setValue(range.getEnd());
    }

    /**
     * Sets the new annotation
     * @param annotation - new annotation
     */
    public void setAnnotation(Annotation annotation) {
        if (editing)
            this.newAnnotation = annotation;
    }

    /**
     * Get selected track
     * @return index of selected track
     */
    public int getSelectedTrack() {
        int idx = lstAnnotationTracks.getSelectedIndex();
        return idx;
    }

    /**
     * initalise the ui of @code CreateAnnotationView
     */
    private void initGUI() {
        JLabel lblFrom = new JLabel("From: ");
        JLabel lblTo = new JLabel("To: ");
        this.jspFrom = new JSpinner();
        this.jspTo = new JSpinner();
        this.lstAnnotationTracks = new JList<DataColumn>();
        this.lstAnnotationTracks.setListData(annotationColumns);
        this.lstAnnotationTracks.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
                label.setText(value instanceof DataColumn ?
                    ((DataColumn) value).getName() : value.toString());
                return label;
            }
        });
        this.jspNumberValue = new JSpinner();
        this.cbbValues = new JComboBox<String>();
        this.pnlCurrentValueComponent = new JPanel(new BorderLayout());
        this.btnAnnotate = new JButton("Annotate");
        this.btnDelete = new JButton("Delete");
        this.btnCancel = new JButton("Cancel");

        this.setLayout(new MigLayout("", "", "[nogrid][][][nogrid]"));
        this.add(lblFrom);
        this.add(jspFrom);
        this.add(lblTo);
        this.add(jspTo, "wrap, gapbottom unrel");

        this.add(new JLabel("Track:"));
        this.add(new JLabel("Select a value:"), "wrap");
        this.add(new JScrollPane(lstAnnotationTracks), "grow, push, gapright unrel");
        this.add(pnlCurrentValueComponent, "aligny top, growx, wrap");

        this.add(btnAnnotate, " tag apply");
        if (editing)
            this.add(btnDelete, "tag apply");
        this.add(btnCancel, "tag cancel");

        CAActionListener al = new CAActionListener();
        this.btnAnnotate.addActionListener(al);
        this.btnCancel.addActionListener(al);
        this.btnDelete.addActionListener(al);
        this.lstAnnotationTracks.addListSelectionListener(new CAListSelectionListener());
        if (editing)
            this.lstAnnotationTracks.disable();
    }

    /**
     * Depending on {@code type} the correct component is initialized with a new
     * model.
     * @param type the selected annotation track's type
     * @param selectedTrack the selected track's number
     */
    private void updateValueComponents() {
        DataColumn.Type type = this.dataSource.getDataColumn(
            dataSource.annotationIndexToColumn(this.getSelectedTrack())).getType();
        if (type.isFloatingpointAnnotation()) {
            jspNumberValue.setModel(new SpinnerNumberModel(
                annotFactory.getMinValueFloating(this.getSelectedTrack()),
                annotFactory.getMinValueFloating(this.getSelectedTrack()),
                annotFactory.getMaxValueFloating(this.getSelectedTrack()), 1.0));
            this.pnlCurrentValueComponent.removeAll();
            this.pnlCurrentValueComponent.add(jspNumberValue, BorderLayout.CENTER);
        } else if (type.isIntegerAnnotation()) {
            jspNumberValue.setModel(new SpinnerNumberModel(
                annotFactory.getMinValueInteger(this.getSelectedTrack()),
                annotFactory.getMinValueInteger(this.getSelectedTrack()),
                annotFactory.getMaxValueInteger(this.getSelectedTrack()), 1));
            this.pnlCurrentValueComponent.removeAll();
            this.pnlCurrentValueComponent.add(jspNumberValue, BorderLayout.CENTER);
        } else if (type.isLabelAnnotation()) {
            cbbValues.setModel(new DefaultComboBoxModel<String>(
                annotFactory.getAllowedValues(this.getSelectedTrack())
                    .toArray(new String[] {})));
            this.pnlCurrentValueComponent.removeAll();
            this.pnlCurrentValueComponent.add(cbbValues, BorderLayout.CENTER);
        } else if (type.isTrainAnnotation()) {
            cbbValues.setModel(new DefaultComboBoxModel<String>(new String[] {"training"}));
            this.pnlCurrentValueComponent.removeAll();
            this.pnlCurrentValueComponent.add(cbbValues, BorderLayout.CENTER);
        }
        this.validate();
    }

    // Action Listener
    public void setPropertyChangedListener(PropertyChangeListener l) {
        this.propertyListener = l;
    }

    private class CAActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src == btnAnnotate && !editing) {
                newAnnotation = null;
                ValidationResult vr = validateRange(((Number) jspFrom.getValue()).longValue(),
                    ((Number) jspTo.getValue()).longValue());
                if (vr.result && annotModel.canInsertAt(parsedRange, getSelectedTrack())) {
                    DataColumn.Type type = dataSource.getDataColumn(
                        dataSource.annotationIndexToColumn(getSelectedTrack())).getType();
                    if (type.isIntegerAnnotation()) {
                        newAnnotation = annotFactory.createIntegerAnnotation(getSelectedTrack(),
                            parsedRange, (Integer) jspNumberValue.getValue());
                    } else if (type.isFloatingpointAnnotation()) {
                        newAnnotation =
                            annotFactory.createFloatingpointAnnotation(getSelectedTrack(),
                                parsedRange, (Double) jspNumberValue.getValue());
                    } else if (type.isLabelAnnotation()) {
                        newAnnotation = annotFactory.createLabelAnnotation(getSelectedTrack(),
                            parsedRange, (String) cbbValues.getSelectedItem());
                    } else if (type.isTrainAnnotation()) {
                        newAnnotation = annotFactory.createTrainAnnotation(getSelectedTrack(),
                            parsedRange);
                    }

                    if (newAnnotation != null) {
                        CreateAnnotationView.this.propertyListener.propertyChange(
                            new PropertyChangeEvent(CreateAnnotationView.this, "newAnnotation",
                                newAnnotation, newAnnotation));
                        CreateAnnotationView.this.dialog.dispose();
                    }
                } else if (!vr.result) {
                    presentErrorDialog(vr);
                } else if (!annotModel.canInsertAt(parsedRange, getSelectedTrack())) {
                    JOptionPane.showMessageDialog(CreateAnnotationView.this,
                        "Annotations may not overlap.", "Wrong Input",
                        JOptionPane.ERROR_MESSAGE);
                }
            } else if (src == btnAnnotate && editing) {
                newAnnotation = null;
                ValidationResult vr = validateRange(((Number) jspFrom.getValue()).longValue(),
                    ((Number) jspTo.getValue()).longValue());
                if (vr.result) {
                    DataColumn.Type type = dataSource.getDataColumn(
                        dataSource.annotationIndexToColumn(getSelectedTrack())).getType();
                    if (type.isIntegerAnnotation()) {
                        newAnnotation = annotFactory.createIntegerAnnotation(getSelectedTrack(),
                            parsedRange, (Integer) jspNumberValue.getValue());
                    } else if (type.isFloatingpointAnnotation()) {
                        newAnnotation =
                            annotFactory.createFloatingpointAnnotation(getSelectedTrack(),
                                parsedRange, (Double) jspNumberValue.getValue());
                    } else if (type.isLabelAnnotation()) {
                        newAnnotation = annotFactory.createLabelAnnotation(getSelectedTrack(),
                            parsedRange, (String) cbbValues.getSelectedItem());
                    }

                    if (newAnnotation != null) {
                        CreateAnnotationView.this.propertyListener.propertyChange(
                            new PropertyChangeEvent(CreateAnnotationView.this,
                                "editAnnotation",
                                newAnnotation, newAnnotation));
                        CreateAnnotationView.this.dialog.dispose();
                    }
                } else if (!vr.result) {
                    presentErrorDialog(vr);
                }
            } else if (src == btnDelete) {
                ValidationResult vr = validateRange(((Number) jspFrom.getValue()).longValue(),
                    ((Number) jspTo.getValue()).longValue());
                if (newAnnotation != null && vr.result) {
                    CreateAnnotationView.this.propertyListener.propertyChange(
                        new PropertyChangeEvent(CreateAnnotationView.this,
                            "deleteAnnotation",
                            parsedRange, parsedRange));
                    CreateAnnotationView.this.dialog.dispose();
                } else if (!vr.result) {
                    presentErrorDialog(vr);
                }
            } else if (src == btnCancel) {
                if (CreateAnnotationView.this.dialog != null) {
                    CreateAnnotationView.this.dialog.dispose();
                }
            }
        }
    }

    private class CAListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting())
                updateValueComponents();
        }
    }

    public void presentAsDialog(Frame owner) {
        dialog = new JDialog(owner, "Create an Annotation", false);
        dialog.add(this, BorderLayout.CENTER);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void presentErrorDialog(ValidationResult vr) {
        JOptionPane.showMessageDialog(this, vr.reason, "Wrong Input", JOptionPane.ERROR_MESSAGE);
    }

    public Annotation getCreatedAnnotation() {
        return newAnnotation;
    }

    /**
     * Validates the range of the new annotation
     * @param from - start of annotation
     * @param to - end of annotation
     * @return valid - if Range can be created
     */
    public ValidationResult validateRange(Long from, Long to) {
        try {
            this.parsedRange = new Range(from, to);
        } catch (IllegalArgumentException e) {
            return ValidationResult.createErrorResult(
                String.format("The range from %d to %d is not valid.", from, to));
        }
        return ValidationResult.createValidResult();
    }
}
