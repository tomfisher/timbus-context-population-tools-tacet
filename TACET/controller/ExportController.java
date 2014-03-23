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

import java.awt.Frame;
import java.net.URL;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import squirrel.controller.ValidationController.ValidationType;
import squirrel.model.AnnotationFactory;
import squirrel.model.io.CSVDataSource;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSink;
import squirrel.model.io.DataSource;
import squirrel.view.ExportView;
import squirrel.view.Gui;

public class ExportController {
    private ValidationController validControll;

    private ExportView view;
    private MasterController masterController;

    public ExportController(MasterController masterController) {
        this.masterController = masterController;

    }

    public void setValidationController(DataSource.Type type) {
        if (type == DataSource.Type.CSV) {
            validControll = new ValidationControllerCSV(true);
        }
    }

    public void doExport(DataSink.Type type) {
        DataSink ds =
            new DataSink(validControll.createDataSource(view), type, validControll.getURL());
        if (!validControll.isError()) {
            masterController.getModel().export(ds, view.getDialog());
        }
        validControll.setError(false);
    }

    /**
     * To set the tabel with the Values of the DataSource Only for Export
     */
    public void defaultValues() {
        DataSource dataSource = null;

        if (masterController.getModel() == null) {
            validControll.createError(ValidationType.NULL_MODEL, "Nothing to export exist");
            System.out.println("No Modell");
        } else {
            dataSource = masterController.getModel().getCurrentDataSource();
            validControll.setStartColumns((dataSource.getNumberOfColumns()));

            TableCellEditor typeEditor =
                new DefaultCellEditor(new JComboBox<>(DataColumn.Type.values()));

            DefaultTableModel defModel =
                new DefaultTableModel(new String[] { "#", "Name", "Type" }, 0);
            for (int i = 0; i < dataSource.getNumberOfColumns(); i++) {
                defModel.addRow(new Object[] { i, "col" + (i + 1),
                    dataSource.getDataColumn(i).getType() });
            }
            if (dataSource.getType() == DataSource.Type.CSV) {
                view.setCSVValues(((CSVDataSource) dataSource).getLineSeparator(),
                    ((CSVDataSource) dataSource).getElemSeparator(), "", defModel,
                    typeEditor,
                    "",
                    "",
                    "", "0",
                    "1000");
            } else {
                view.setCSVValues("",
                    "", "", defModel, typeEditor,
                    "",
                    "",
                    "", "0",
                    "1000");
            }
        }
    }

    /**
     * Sets the import view.
     *
     * @param view the new import view
     */
    public void setExportView(ExportView view) {
        this.view = view;
    }

    /**
     * Present dialog.
     *
     * @param owner the owner
     */
    public void presentDialog(Frame owner) {
        if (view == null)
            this.view = new ExportView(this);
        view.showAsDialog(owner);
    }

    /**
     * Only for testing
     * @param master
     */
    public void setMasterController(MasterController master) {
        this.masterController = masterController;
    }

    public void clearValidations() {
        validControll.clearValidations();
    }

    public void validateLocation(String text) {
        validControll.validateLocation(text);
    }

    public void validateCSVElemSep(String elemSep) {
        ((ValidationControllerCSV) validControll).validateCSVElemSep(elemSep);
    }

    public void validateCSVLineSep(String lineSep) {
        ((ValidationControllerCSV) validControll).validateCSVLineSep(lineSep);
    }

    public void validateCSVColumnDescriptions(DefaultTableModel tblModel, boolean export) {
        ((ValidationControllerCSV) validControll).validateCSVColumnDescriptions(tblModel);
    }

    public void validateCSVTimestampFormats(Map<Integer, TimestampFormat> formats) {
        ((ValidationControllerCSV) validControll).validateCSVTimestampFormats(formats);
    }

    public boolean validateDeleteColumn(int selectedRow) {
        return validControll.validateDeleteColumn(selectedRow);

    }

    public void setSelectedDate(boolean isCustomDate) {
        ((ValidationControllerCSV) validControll).setSelectedDate(isCustomDate);
    }

}