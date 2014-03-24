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
