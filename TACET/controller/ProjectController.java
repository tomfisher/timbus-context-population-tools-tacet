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
