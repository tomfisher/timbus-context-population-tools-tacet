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

import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

import squirrel.model.io.CSVDataSource;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.model.io.IOFactory;
import squirrel.view.ImportWizard.ImportWizard;

public class ImportController{
    private ValidationController validControll;
    private ImportWizard view;
    private MasterController masterController;
    private boolean debugMode = true;

    /**
     * Instantiates a new import controller.
     *
     * @param masterController the master controller
     */
    public ImportController(MasterController masterController) {
        this.masterController = masterController;
    }

    public void setValidationController(DataSource.Type type){
        if(type==DataSource.Type.CSV){
            validControll = new ValidationControllerCSV(false);
        }
    }

    /**
     * Sets the import view.
     *
     * @param view the new import view
     */
    public void setImportView(ImportWizard view) {
        this.view = view;
    }

    /**
     * Sets the master controller.
     *
     * @param master the new master controller
     */
    public void setMasterController(MasterController master) {
        this.masterController = master;
    }
    private int sourceType;

    public DefaultTableModel analyseLine(boolean header){
        return ((ValidationControllerCSV) validControll).anlyseLine(header);
    }

    public String getFirstLine(){
        return ((ValidationControllerCSV) validControll).getFirstLine();
    }

    public String getElemSeperator(){
        return ((ValidationControllerCSV) validControll).getCsvElemSeparator();
    }

    /**
     * Validates the location and readability of the csv file. Creates error if
     * the file does not exist or is not readable.
     *
     * @param loc the location of the csv file
     * @return the validation result
     */
    public ValidationResult validateLocation(String loc) {
        return validControll.validateLocation(loc);
    }

    /**
     * Validates the element separator of the csv file. Creates error if the
     * element separator is not 1 character long.
     *
     * @param elemSep the element separator
     * @return the validation result
     */
    public ValidationResult validateCSVElemSep(String elemSep) {
        return ((ValidationControllerCSV) validControll).validateCSVElemSep(elemSep);
    }

    /**
     * Validates the line separator of the csv file. Creates error if there is
     * no line separator given.
     *
     * @param lineSep the line separator
     * @return the validation result
     */
    public ValidationResult validateCSVLineSep(String lineSep) {
        return ((ValidationControllerCSV) validControll).validateCSVLineSep(lineSep);
    }

    /**
     * Validates the column descriptions of the csv file. Creates error if the
     * description of any column is missing or the description is malconfigured.
     *
     * @param tblModel the tbl model
     * @return the validation result
     */
    public ValidationResult validateCSVColumnDescriptions(DefaultTableModel tblModel) {
        return ((ValidationControllerCSV) validControll).validateCSVColumnDescriptions(tblModel);
    }

    /**
     * Validate csv date.
     *
     * @param date the date
     * @return the validation result
     */
    public ValidationResult validateCSVDate(int column, String date) {
        return ((ValidationControllerCSV) validControll).validateCSVDate(column, date);
    }

    /**
     * Validate csv custom units.
     *
     * @param customUnits the custom units
     * @return the validation result
     */
    public ValidationResult validateCSVCustomUnits(int column, String customUnits) {
        return ((ValidationControllerCSV) validControll).validateCSVCustomUnits(column, customUnits);
    }

    /**
     * Validate csv custom separator.
     *
     * @param customSeparator the custom separator
     * @return the validation result
     */
    public ValidationResult validateCSVCustomSeparator(int column, String customSeparator) {
        return ((ValidationControllerCSV) validControll).validateCSVCustomSeparator(column, customSeparator);
    }

    public ValidationResult validateCSVTimestampFormats(Map<Integer, TimestampFormat> formats) {
        return ((ValidationControllerCSV) validControll).validateCSVTimestampFormats(formats);
    }

    public String getGuestTimestampFormat(){
        return ((ValidationControllerCSV) validControll).getGuestTimeStampFormat();
    }

    /**
     * Validate csv range.
     *
     * @param from the from
     * @param to the to
     * @return the validation result
     */
    public ValidationResult validatePreviewRange(String from, String to) {
        return validControll.validatePreviewRange(from, to);
    }


    /**
     * Do refresh.
     */
    public void doRefresh() {
        switch (this.getSelectedSourceType()) {
        case CSV:
            CSVDataSource csvDataSource = (CSVDataSource) createDataSource();
            if (csvDataSource != null) {

                PreviewTableModelAdapter adapter =
                        new PreviewTableModelAdapter(validControll.getPreviewRange(),
                                IOFactory.createCSVReader(csvDataSource), csvDataSource);
                adapter.readData();
            }
            break;
        }
    }

    /**
     * Do import.
     */
    public void doImport() {
        DataSource<? extends DataColumn> dataSource = createDataSource();
        if (!validControll.isError()) {
            dataSource.setType(this.getSelectedSourceType());
            masterController.onImportFinish(dataSource);
        }
        validControll.setError(false);
    }

    private DataSource<? extends DataColumn> createDataSource() {
        DataSource<? extends DataColumn> ds = validControll.createDataSource(view);
        if(!validControll.isError()){
            ds.setType(this.getSelectedSourceType());
        }
        return ds;
    }

    /**
     * Present dialog.
     *
     * @param owner the owner
     */
    public void presentDialog(Frame owner) {
       // if (view == null)
            this.view = new ImportWizard(this);
    }


    /**
     * Validate video location.
     *
     * @param loc the loc
     * @return the validation result
     */
    public ValidationResult validateVideoLocation(String loc) {
        return validControll.validateVideoLocation(loc);
    }

    /**
     * Clear media paths.
     */
    public void clearMediaPaths() {
        validControll.clearMediaPaths();
    }


    public void emptyValidationList() {
        validControll.clearValidations();
    }

    /**
     * Get Selected Source type
     * @return type of source file
     */
    public DataSource.Type getSelectedSourceType() {
        switch (this.sourceType) {
        case 0:
            return DataSource.Type.CSV;
        default:
            throw new IllegalStateException("The selected tab/source is not supported.");
        }
    }

    public void setSourceType(int type) {
        this.sourceType = type;

    }

    public boolean getDebug(){
        return this.debugMode;
    }


    public List<ValidationResult> getErrorValidations() {
        return validControll.getErrorValidations();
    }

    public void clearErrors() {
        // TODO Auto-generated method stub
        emptyValidationList();
    }
}
