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
package squirrel.demo;

import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import squirrel.controller.ImportController;
import squirrel.controller.MasterController;
import squirrel.controller.TimestampFormat;
import squirrel.model.io.DataColumn;
import squirrel.view.ImportWizard.ImportWizard;

public class DemoStart {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ImportController ctrl = new ImportController(null);
        MasterController master = new MasterController(ctrl);
        ctrl.setMasterController(master);
        ImportWizard view = new ImportWizard(ctrl);
        ctrl.setImportView(view);
        DefaultTableModel csvModel =
                new DefaultTableModel(new String[] { "#", "Name", "Type", "Annotation Values" }, 0);
        csvModel.addRow(new Object[] { 0, "col1", DataColumn.Type.TIMESTAMP });
        csvModel.addRow(new Object[] { 1, "col2", DataColumn.Type.SENSOR });
        csvModel.addRow(new Object[] { 2, "col3", DataColumn.Type.SENSOR });
        csvModel.addRow(new Object[] { 3, "col4", DataColumn.Type.SENSOR });
        csvModel.addRow(new Object[] { 4, "col5", DataColumn.Type.SENSOR });
        csvModel.addRow(new Object[] { 5, "col6", DataColumn.Type.SENSOR });
        csvModel.addRow(new Object[] { 6, "col7", DataColumn.Type.SENSOR });
        csvModel.addRow(new Object[] { 7, "col8", DataColumn.Type.SENSOR });
        csvModel.addRow(new Object[] { 8, "col9", DataColumn.Type.SENSOR });

        TableCellEditor typeEditor =
            new DefaultCellEditor(new JComboBox<>(DataColumn.Type.values()));



        TimestampFormat[] tsfs = new TimestampFormat[] { new TimestampFormat("yyyy-mm-dd HH:mm:ss") };

        view.setCSVValues("\\n", ";",
            "", csvModel, typeEditor, tsfs, "1", "250");

        master.onStart();
    }

}
