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
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import squirrel.model.AnnotationModelImpl;
import squirrel.model.io.DataColumn;
import squirrel.model.io.DataSource;
import squirrel.model.io.ProjectIO;
import squirrel.view.LoadProjectView;
import squirrel.view.SaveProjectView;

public class ProjectController {

    private LoadProjectView loadProjectView;
    private SaveProjectView saveProjectView;
    private MasterController masterController;
    private Map<ValidationType, ValidationResult> validationResults = new HashMap<>();
    private URL url = null;

    /**
     * The Enum ValidationType.
     */
    public enum ValidationType {
        LOCATION,
        FILE,
        MODELEXIST
    }

    public ProjectController(MasterController masterController) {
        this.masterController = masterController;
    }

    public void presentDialogLoad(Frame owner) {
        if (loadProjectView == null)
            this.loadProjectView = new LoadProjectView(this);
        loadProjectView.showAsDialog(owner);
    }

    public void presentDialogSave(Frame owner) {
        if (saveProjectView == null)
            this.saveProjectView = new SaveProjectView(this);
        saveProjectView.showAsDialog(owner);
    }

    public void loadProjectResources() {
            List<ValidationResult> ret = new LinkedList<>();
            for (ValidationType key : validationResults.keySet()) {
                ValidationResult vRes = validationResults.get(key);
                if (vRes != null && vRes.type == ValidationResult.Type.ERROR) {
                    ret.add(vRes);
                }
            }
            if(ret.isEmpty()){
                ProjectIO prIO = new ProjectIO(url, "");
                loadProjectView.disposeDialog();
                masterController
                .onLoadFinish(prIO.readDataSourceIntern(), prIO.readModelIntern());

            } else {
                loadProjectView.showWarningDialog(ret);
            }
    }

    public ValidationResult validatePathLoad(String loc) {

        try {
            url = new URL(loc);
        } catch (MalformedURLException e) {
            return createError(ValidationType.LOCATION, e.getLocalizedMessage());
        }
        File file = new File(url.getFile());
        boolean exists = file.exists();
        boolean readable = file.canRead();
        if (exists && readable) {
            return createValid(ValidationType.LOCATION);
        } else if (!exists) {
            return createError(ValidationType.LOCATION, "\"" + loc + "\" does not exist.");
        } else if (!readable) {
            return createError(ValidationType.LOCATION, file + " is not readable.");
        }
        return createDummyError(ValidationType.LOCATION);

    }

    private ValidationResult createError(ValidationType validType, String msg) {
        ValidationResult res = ValidationResult.createErrorResult(msg);
        validationResults.put(validType, res);
        return res;
    }

    private ValidationResult createValid(ValidationType validType) {
        ValidationResult res = ValidationResult.createValidResult();
        validationResults.put(validType, res);
        return res;
    }

    private ValidationResult createDummyError(ValidationType validType) {
        ValidationResult res = ValidationResult.createDummyErrorResult();
        validationResults.put(validType, res);
        return res;
    }

    public void saveProject() {
        List<ValidationResult> ret = new LinkedList<>();
        for (ValidationType key : validationResults.keySet()) {
            ValidationResult vRes = validationResults.get(key);
            if (vRes != null && vRes.type == ValidationResult.Type.ERROR) {
                ret.add(vRes);
            }
        }
        if(ret.isEmpty()){
            ProjectIO proIO = new ProjectIO(url, "");
            saveProjectView.disposeDialog();
            proIO.saveProject(masterController.getModel().getCurrentDataSource(), (AnnotationModelImpl) masterController
                    .getModel().getAnnotationModel());

        } else {
            saveProjectView.showWarningDialog(ret);
        }



    }

    /**
     * Validates the location of a file path
     * @param loc - location of the file
     * @return valid - if location can be created as URL
     */
    public ValidationResult validateURL(String loc) {

        try {
            url = new URL(loc);
            url.toURI();
        } catch (MalformedURLException e) {
            return createError(ValidationType.LOCATION, e.getLocalizedMessage());
        } catch (URISyntaxException e) {
            return createError(ValidationType.LOCATION, e.getLocalizedMessage());
        }
        return createValid(ValidationType.LOCATION);
    }

    /**
     * Validates existence of model
     * @return valid - if Project controller has reference to model
     */
    public ValidationResult validModel() {
        if (masterController.getModel() == null) {
            return createError(ValidationType.MODELEXIST, "No existing project to Save");
        }
        return createValid(ValidationType.MODELEXIST);
    }
}
