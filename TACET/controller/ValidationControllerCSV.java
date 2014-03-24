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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;


import squirrel.model.AnnotationFactory;
import squirrel.model.io.CSVDataColumn;
import squirrel.model.io.CSVDataColumn.DataType;
import squirrel.model.io.CSVDataColumn.Encoding;
import squirrel.model.io.CSVDataSource;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataColumn.Type;
import squirrel.model.io.DataSource;
import squirrel.view.WarningView;

public class ValidationControllerCSV extends ValidationController {

    public ValidationControllerCSV(boolean export) {
        super(export);
    }

    private boolean csvIsCustomDate;
    private String csvCustomSeparator;
    private String csvElemSeparator;
    private String csvLineSeparator;
    private Map<Integer, ParsedTimestampFormat> parsedTimeFormats = new HashMap<>();
    private CSVDataColumn[] colDescs;
    private boolean header = false;
    private int headerLength;
    private String firstLine = "";
    private int counter = 0;
    private String[] collectionOfPossibelFormats = { "yyyy-MM-dd HH:mm:ss", "HH:mm:ss",
        "yyyy-MM-dd", "hh:mm:ss:S", "MM/dd/yyyy",
        "MM/dd/yyyy HH:mm:ss", "HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy","yyyy-MM-dd HH:mm:ss.S",
        "yyyy-MM-dd HH:mm:ss.S"};
    private String guestTimestampFormat = "";

    /**
     * Sets the selected date.
     *
     * @param isCustomDate the new selected date
     */
    public void setSelectedDate(boolean isCustomDate) {
        this.csvIsCustomDate = isCustomDate;
    }

    public String getGuestTimeStampFormat() {
        return guestTimestampFormat;
    }

    public DefaultTableModel anlyseLine(boolean header) {
        this.header = header;
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(new File(super.url.toURI())));
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            String headerline = "";
            if (header) {
                headerline = bReader.readLine();
                headerLength = headerline.length();
            }
            String[] rowheader = headerline.split(csvElemSeparator);
            String valueline = bReader.readLine();
            String[] rowvalue = valueline.split(csvElemSeparator);
            firstLine = valueline;
            System.out.println(valueline);
            if (rowvalue.length == 1) {
                rowvalue = valueline.split(",");
                if (rowvalue.length == 1) {
                    rowvalue = valueline.split(";");
                    if (rowvalue.length != 1) {
                        csvElemSeparator = ";";
                    }
                } else {
                    csvElemSeparator = ",";
                }
            }

            for (int i = 0; i < rowvalue.length; i++) {
                for (int j = 0; j < collectionOfPossibelFormats.length; j++) {
                    DateFormat formatter = new SimpleDateFormat(collectionOfPossibelFormats[j]);


                        try {
                            Date d = formatter.parse(rowvalue[i]);
                        } catch (ParseException e) {
                            continue;
                        }
                    guestTimestampFormat = collectionOfPossibelFormats[j];
                    break;

                }
            }

            DefaultTableModel csvModel =
                new DefaultTableModel(new String[] { "#", "Name", "Type", "Annotation Values" }, 0);
            for (int i = 0; header && i < rowheader.length || i < rowvalue.length
                && rowvalue.length != 1; i++) {
                if (header) {
                    if (i == 0) {
                        csvModel
                            .addRow(new Object[] { i, rowheader[i], DataColumn.Type.TIMESTAMP });
                    } else {
                        csvModel.addRow(new Object[] { i, rowheader[i], getType(rowvalue[i]) });
                    }
                } else {
                    if (i == 0) {
                        csvModel
                            .addRow(new Object[] { i, "column" + i, DataColumn.Type.TIMESTAMP });
                    } else {
                        csvModel.addRow(new Object[] { i, "column" + i, getType(rowvalue[i]) });
                    }
                }
            }
            bReader.close();
            return csvModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DataColumn.Type getType(String column) {
        Pattern p = Pattern.compile("[-+]?[0-9]*\\.?[0-9]*");
        if (p.matcher(column).matches()) {

            return DataColumn.Type.SENSOR;
        } else {
            return DataColumn.Type.LABEL_ANNOTATION;
        }
    }

    public String getCsvElemSeparator() {
        return csvElemSeparator;
    }

    public String getCsvLineSeparator() {
        return csvLineSeparator;
    }

    public String getFirstLine() {
        return firstLine;
    }

    /**
     * Validates the element separator of the csv file. Creates error if the
     * element separator is not 1 character long.
     *
     * @param elemSep the element separator
     * @return the validation result
     */
    public ValidationResult validateCSVElemSep(String elemSep) {
        if (nonEmpty(elemSep) && elemSep.length() == 1) {
            csvElemSeparator = elemSep;
            return createValid(ValidationType.CSV_ELEM_SEPARATOR);
        }
        return createError(ValidationType.CSV_ELEM_SEPARATOR, "Element separator \"" + elemSep
            + "\" is not one character long.");
    }

    /**
     * Validates the line separator of the csv file. Creates error if there is
     * no line separator given.
     *
     * @param lineSep the line separator
     * @return the validation result
     */
    public ValidationResult validateCSVLineSep(String lineSep) {
        return createValid(ValidationType.CSV_LINE_SEPARATOR);
        // if (nonEmpty(lineSep)) {
        // this.csvLineSeparator = lineSep;
        // return createValid(ValidationType.CSV_LINE_SEPARATOR);
        // }
        // return createError(ValidationType.CSV_LINE_SEPARATOR,
        // "Line separator must not be empty.");
    }

    public ValidationResult validateCSVTimestampFormats(Map<Integer, TimestampFormat> formats) {
        if (formats.size() == 0) {
            return createError(ValidationType.CSV_CUSTOM_DATE, "Custom date must be set.");
        }
        for (Integer key : formats.keySet()) {
            TimestampFormat format = formats.get(key);
            if (format.isCustomDate) {
                ValidationResult sep = validateCSVCustomSeparator(key, format.customSeparator);
                if (!sep.result) {
                    return createError(ValidationType.CSV_ELEM_SEPARATOR, sep.reason);
                }

                ValidationResult units = validateCSVCustomUnits(key, format.customUnits);
                if (!units.result) {
                    return createError(ValidationType.CSV_DATE_FORMATS, units.reason);

                }
                this.parsedTimeFormats.put(key,
                    new ParsedTimestampFormat(csvCustomSeparator, customUnits));
            } else {
                ValidationResult dateFormat = validateCSVDate(key, format.date);
                if (!dateFormat.result)
                    return dateFormat;

                this.parsedTimeFormats.put(key, new ParsedTimestampFormat(super.dateFormat));
            }
        }
        return createValid(ValidationType.CSV_DATE_FORMATS);
    }

    @Override
    public DataSource<? extends DataColumn> createDataSource(WarningView view) {
        List<ValidationResult> errorResults = getErrorValidations();
        DataSource result = null;

        if (errorResults.isEmpty()) {
            CSVDataSource csvDataSource =
                new CSVDataSource(url, Arrays.asList(colDescs), mediaPaths,
                    annotationFactory, csvElemSeparator, csvLineSeparator,
                    Encoding.UTF8, header, headerLength);
            for (Integer key : parsedTimeFormats.keySet()) {
                ParsedTimestampFormat pTimeFrmt = parsedTimeFormats.get(key);
                if (pTimeFrmt.isCustomDate) {
                    csvDataSource.getDataColumn(key).setCustomDateSeparator(
                        pTimeFrmt.customSeparator);
                    csvDataSource.getDataColumn(key).setCustomDateUnits(pTimeFrmt.customUnits);
                } else {
                    csvDataSource.getDataColumn(key).setDate(true);

                    csvDataSource.getDataColumn(key).setDateFormat(pTimeFrmt.date);
                }
            }
            result = csvDataSource;
        } else {
            error = true;
            view.showWarningDialog(errorResults);
        }
        return result;
    }

    /**
     * Validate csv date.
     *
     * @param date the date
     * @return the validation result
     */
    public ValidationResult validateCSVDate(int column, String date) {
        if (!nonEmpty(date)) {
            return createError(ValidationType.CSV_DATE, "The date for column " + column
                + " must not be empty.");
        }
        DateFormat formatter = new SimpleDateFormat(date);

        csvIsCustomDate = false;
        this.dateFormat = formatter;
        return createValid(ValidationType.CSV_DATE);
    }

    public ValidationResult validateCSVCustomUnits(int column, String customUnits) {
        if (nonEmpty(customUnits)) {
            ValidationResult res = validationResults.get(ValidationType.CSV_CUSTOM_SEPARATOR);
            if (res != null && res.type == ValidationResult.Type.VALID) {
                String[] sUnits;
                if ((sUnits = customUnits.split(csvCustomSeparator)).length == 0) {
                    sUnits = new String[] { customUnits };
                }
                return validateUnit(column, sUnits);
            }
        }
        return createError(ValidationType.UNITS, "Units may not be empty.");
    }

    /**
     * Validate csv custom separator.
     *
     * @param customSeparator the custom separator
     * @return the validation result
     */
    public ValidationResult validateCSVCustomSeparator(int column, String customSeparator) {
        if (nonEmpty(customSeparator)) {
            this.csvCustomSeparator = customSeparator;
            return createValid(ValidationType.CSV_CUSTOM_SEPARATOR);
        }
        return createError(ValidationType.CSV_CUSTOM_SEPARATOR, "Separator may not be empty.");
    }

    /**
     * Validates the column descriptions of the csv file. Creates error if the
     * description of any column is missing or the description is malconfigured.
     *
     * @param tblModel the tbl model
     * @return the validation result
     */
    public ValidationResult validateCSVColumnDescriptions(DefaultTableModel tblModel) {
        Vector tblRows = tblModel.getDataVector();
        CSVDataColumn[] descriptions = new CSVDataColumn[tblRows.size()];
        annotationFactory = new AnnotationFactory();
        int counter = 0;
        int timeStampCounter = 0;
        for (int i = 0; i < tblRows.size(); i++) {
            String name = (String) ((Vector) tblRows.get(i)).get(1);
            Type desc = (Type) ((Vector) tblRows.get(i)).get(2);

            if (desc == null) {
                return createError(ValidationType.COL_DESCS, "The description of track " + i
                    + " must not be empty.");
            } else if (!nonEmpty(name)) {
                return createError(ValidationType.COL_DESCS, "The name of track " + i
                    + " must not be empty.");
            } else if (desc == Type.TIMESTAMP) {
                timeStampCounter++;
            } else if (!export) {
                // To validate the Annotations not need in Export
                validateCSVColumnDescriptionsImportHelp(i, tblRows, desc);
            }
            if ((desc != null) && (nonEmpty(name))) {
                descriptions[i] = new CSVDataColumn(name, desc, DataType.NONE);
            }
        }
        if (timeStampCounter != 1) {
            return createError(ValidationType.COL_DESCS,
                "There must be exactly one timestamp");
        }

        this.colDescs = descriptions;
        return createValid(ValidationType.COL_DESCS);
    }

    @Override
    public List<ValidationResult> getErrorValidations() {
        List<ValidationResult> ret = new LinkedList<>();
        for (ValidationType key : validationResults.keySet()) {
            ValidationResult vRes = validationResults.get(key);
            if (vRes != null && vRes.type == ValidationResult.Type.ERROR) {
                if ((csvIsCustomDate && key == ValidationType.CSV_DATE)
                    || (!csvIsCustomDate && (key == ValidationType.CSV_CUSTOM_SEPARATOR
                    || key == ValidationType.UNITS))) {
                    continue; // TODO temporary hack for the custom date thing.
                              // This
                              // should be solved with some kind of validation
                              // dependencies.
                }
                ret.add(vRes);
            }
        }
        return ret;
    }

    private ValidationResult validateCSVColumnDescriptionsImportHelp(int i, Vector tblRows,
        Type desc) {

        String annotationValues = (String) ((Vector) tblRows.get(i)).get(3);
        if (desc.isAnnotation()) {
            if (!nonEmpty(annotationValues)) {
                return createError(ValidationType.COL_DESCS,
                    "The annotation values of track " + i + " must not be empty.");
            }
            if (desc.isRangedAnnotation()) {
                String[] sValues = annotationValues.replaceAll(" ", "").split(",");
                if (sValues.length != 2)
                    return createError(ValidationType.COL_DESCS,
                        "The floatingpoint range in track " + i + " has the wrong format.");
                if (desc == Type.NEW_FLOATING_POINT_ANNOTATION
                    || desc == Type.FLOATING_POINT_ANNOTATION) {
                    Double[] dValues = new Double[2];
                    for (int j = 0; j < sValues.length; j++) {
                        try {
                            dValues[j] = Double.parseDouble(sValues[j]);
                        } catch (NumberFormatException nfe) {
                            return createError(ValidationType.COL_DESCS, "The "
                                + ((j == 0) ? "start" : "end") + " of the range in track "
                                + i
                                + "does not seem to be a floatingpoint number.");
                        }
                    }
                    annotationFactory.setMaxValueFloating(counter, dValues[0]);
                    annotationFactory.setMaxValueFloating(counter, dValues[1]);
                } else if (desc == Type.NEW_INTEGER_ANNOTATION
                    || desc == Type.INTEGER_ANNOTATION) {
                    Integer[] iValues = new Integer[2];
                    for (int j = 0; j < sValues.length; j++) {
                        try {
                            iValues[j] = Integer.parseInt(sValues[j]);
                        } catch (NumberFormatException nfe) {
                            return createError(ValidationType.COL_DESCS, "The "
                                + ((j == 0) ? "start" : "end") + " of the range in track "
                                + i
                                + "does not seem to be an integer.");
                        }
                    }
                    annotationFactory.setMinValueInteger(counter, iValues[0]);
                    annotationFactory.setMaxValueInteger(counter, iValues[1]);
                }
                counter++;
            } else if (desc.isLabelAnnotation()) {
                String[] sValues = annotationValues.split(",");
                if (sValues.length == 0) {
                    return createError(ValidationType.COL_DESCS,
                        "The list of allowed values in track " + counter
                            + " has the wrong format.");
                }

                List<String> list = new ArrayList();
                for (int j = 0; j < sValues.length; j++) {
                    list.add(sValues[j]);
                }

                annotationFactory.addAllowedValues(counter, list);
                counter++;
            } else if (desc.isTrainAnnotation()) {
                String[] sValues = { "training" };
                annotationFactory.addAllowedValues(counter, Arrays.asList(sValues));
                counter++;
            }
        }
        return createDummyError(ValidationType.COL_DESCS);
    }

}
