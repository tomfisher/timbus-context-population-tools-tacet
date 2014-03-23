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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;


import squirrel.model.AnnotationFactory;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.model.io.DataColumn.Unit;
import squirrel.util.Range;
import squirrel.view.ExportView;
import squirrel.view.WarningView;

public abstract class ValidationController {
    protected Map<ValidationType, ValidationResult> validationResults = new HashMap<>();

    protected URL url = null;
    protected boolean error = false;
    protected int startColumns;

    protected AnnotationFactory annotationFactory;


    protected Unit[] customUnits;
    protected DateFormat dateFormat;
    /**
     * The Enum ValidationType.
     */
    public enum ValidationType {
        COL_DESCS,
        CSV_DATE,
        CSV_IS_CUSTOM_DATE,
        UNITS,
        CSV_CUSTOM_SEPARATOR,
        CSV_DATE_FORMATS,
        CSV_ELEM_SEPARATOR,
        CSV_LINE_SEPARATOR,
        CSV_CUSTOM_DATE,
        BINARY_DATE_FORMATS,
        PREVIEW_RANGE,
        BINARY_ENDIAN,
        LOCATION,
        NULL_MODEL,
        NOT_IGNORE,
        DB_COL_DESCS,
        DB_LOCATION,
        DB_Login,
        VIDEO_LOCATION,
        DB_URL,
        DB_Connection,
        DB_Statement,
        DB_Vendor, DB_PREVIEW_RANGE, DB_UNITS, DB_DATE_FORMATS, DB_Date, ;
    }

    protected Range previewRange;
    // private Map<Integer, ParsedTimestampFormat> binaryParsedTimeFormats;
    // private SimpleDateFormat binaryDateFormat;
    boolean export;

    public ValidationController(boolean export) {
        this.export = export;
    }

    // Only for Import
    protected List<String> mediaPaths = new ArrayList<>();

    public ValidationController(Map<ValidationType, ValidationResult> validationResults, URL url,
        boolean error, int startColumns,
        AnnotationFactory annotationFactory, boolean export) {
        super();
        this.validationResults = validationResults;
        this.url = url;
        this.error = error;
        this.startColumns = startColumns;
        this.annotationFactory = annotationFactory;
        this.export = export;
    }


    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public Map<ValidationType, ValidationResult> getValidationResults() {
        return validationResults;
    }

    public AnnotationFactory getAnnotationFactory() {
        return annotationFactory;
    }

    public Range getPreviewRange() {
        return previewRange;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public URL getURL() {
        return url;
    }



    /**
     * Validates the location and readability of the csv file. Creates error if
     * the file does not exist or is not readable.
     *
     * @param loc the location of the csv file
     * @return the validation result
     */
    public ValidationResult validateLocation(String loc) {
        File file = new File(loc.replaceAll("^file://", ""));
        if (!export) {

            boolean exists = file.exists();
            boolean readable = file.canRead();
            if (!exists) {
                return createError(ValidationType.LOCATION, "\"" + loc + "\" does not exist.");
            } else if (!readable) {
                return createError(ValidationType.LOCATION, file + " is not readable.");
            }
        }
        try {
            url = new URL(loc);
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            return createError(ValidationType.LOCATION, "Not a valid url");
        }
        return createValid(ValidationType.LOCATION);
    }


    protected boolean nonEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public int getStartColumns() {
        return startColumns;
    }

    public void setStartColumns(int startColumns) {
        this.startColumns = startColumns;
    }

    public abstract DataSource<? extends DataColumn> createDataSource(WarningView view);


    public List<ValidationResult> getErrorValidations() {
        List<ValidationResult> ret = new LinkedList<>();
        for (ValidationType key : validationResults.keySet()) {
            ValidationResult vRes = validationResults.get(key);
            if (vRes != null && vRes.type == ValidationResult.Type.ERROR) {
                ret.add(vRes);
            }
        }
        return ret;
    }

    protected ValidationResult createDummyError(ValidationType validType) {
        ValidationResult res = ValidationResult.createDummyErrorResult();
        validationResults.put(validType, res);
        return res;
    }

    protected ValidationResult createError(ValidationType validType, String msg) {
        ValidationResult res = ValidationResult.createErrorResult(msg);
        validationResults.put(validType, res);
        // setError(true);
        return res;
    }

    protected ValidationResult createValid(ValidationType validType) {
        ValidationResult res = ValidationResult.createValidResult();
        validationResults.put(validType, res);
        return res;
    }

    protected boolean validateDeleteColumn(int number) {
        return startColumns < number;
    }

    protected void validateChangeColumn(ExportView view, MasterController masterController) {
        DataSource ds = createDataSource(view);
        if (ds != null) {
            for (int i = 0; i < ds.getNumberOfColumns(); i++) {
                if (masterController.getModel().getCurrentDataSource().getNumberOfColumns() >= i
                    && ds.getDataColumn(i).getType() != masterController.getModel()
                        .getCurrentDataSource().getDataColumn(i).getType()
                    && ds.getDataColumn(i).getType() != DataColumn.Type.IGNORE) {
                    createError(ValidationType.NOT_IGNORE, "You can only change to Ignore");

                } else if (masterController.getModel().getCurrentDataSource().getNumberOfColumns() < i
                    && (ds.getDataColumn(i).getType() == DataColumn.Type.IGNORE ||
                    ds.getDataColumn(i).getType() == DataColumn.Type.TIMESTAMP)) {
                    createError(ValidationType.NOT_IGNORE, "You can only add Timestamps");
                }
            }
        }

    }

    public void clearValidations() {
        this.validationResults.clear();

    }

    /**
     * Validate video location.
     *
     * @param loc the loc
     * @return the validation result
     */
    public ValidationResult validateVideoLocation(String loc) {
        URL url = null;
        try {
            url = new URL("file://" + loc);
        } catch (MalformedURLException e) {
            return createError(ValidationType.VIDEO_LOCATION, e.getLocalizedMessage());
        }
        File file = new File(url.getFile());
        boolean exists = file.exists();
        boolean readable = file.canRead();
        if (loc == null) {
            return createValid(ValidationType.VIDEO_LOCATION);
        } else if (exists && readable) {
            mediaPaths.add(loc);
            return createValid(ValidationType.VIDEO_LOCATION);
        } else if (!exists) {
            return createError(ValidationType.VIDEO_LOCATION, "\"" + loc + "\" does not exist.");
        } else if (!readable) {
            return createError(ValidationType.VIDEO_LOCATION, file + " is not readable.");
        }
        return createDummyError(ValidationType.VIDEO_LOCATION);
    }

    /**
     * Clear media paths.
     */
    public void clearMediaPaths() {
        mediaPaths.clear();
    }

    public ValidationResult validatePreviewRange(String from, String to) {
        Long parsedFrom = null;
        Long parsedTo = null;
        try {
            parsedFrom = Long.parseLong(from);
        } catch (NumberFormatException nfe) {
            return createError(ValidationType.PREVIEW_RANGE, "From: '" + from
                    + "' is not a valid integer.");
        }
        try {
            parsedTo = Long.parseLong(to);
        } catch (NumberFormatException nfe) {
            return createError(ValidationType.PREVIEW_RANGE, "To: '" + to
                    + "' is not a valid integer.");
        }
        if (parsedFrom <= 0)
            return createError(ValidationType.PREVIEW_RANGE, "From may not be negative.");
        if (parsedTo < 0)
            return createError(ValidationType.PREVIEW_RANGE, "To may not be negative.");
        if (parsedFrom > parsedTo)
            return createError(ValidationType.PREVIEW_RANGE,
                    "From may not be greater than To.");

        this.previewRange = new Range(parsedFrom, parsedTo);
        return createValid(ValidationType.PREVIEW_RANGE);
    }


    public ValidationResult validateUnit(int column, String[] timeUnits) {
        if (timeUnits.length > 0) {
            Unit[] units = new Unit[timeUnits.length];
            for (int i = 0; i < timeUnits.length; i++) {
                switch (timeUnits[i]) {
                case "h":
                    units[i] = Unit.HOURS;
                    break;
                case "m":
                    units[i] = Unit.MINUTES;
                    break;
                case "s":
                    units[i] = Unit.SECONDS;
                    break;
                case "hs":
                    units[i] = Unit.HUNDRETH_SECONDS;
                    break;
                case "S":
                    units[i] = Unit.MILLISECONDS;
                    break;
                default:
                    return createError(ValidationType.UNITS, "\"" + timeUnits[i]
                            + "\" is not a valid unit.");
                }
            }


            this.customUnits = units;
            return createValid(ValidationType.UNITS);
        }
        return createError(ValidationType.UNITS, "Units may not be empty.");
    }






}
