package edu.teco.tacet.export;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.backend.BackendManager;
import edu.teco.tacet.backend.ExtensionsHelper;
import edu.teco.tacet.meta.Project;

public class ExportManager implements BackendManager {

    private static final String ATTR = "sink_type";
    private static final String WRITERS_EXT_POINT_ID = "edu.teco.tacet.writers";
    private static final String PROVIDERS_EXT_POINT_ID = "edu.teco.tacet.export.pageproviders";

    private Map<String, ExportPageProvider> pageProviders = new HashMap<>();
    private IWizard wizard;

    public ExportManager(IWizard wizard) {
        this.wizard = wizard;
    }
    
    @Override
    public Iterable<String> getAvailableBackends() {
        return ExtensionsHelper.getExtensionsGroupedByAttribute(ATTR,
            WRITERS_EXT_POINT_ID, PROVIDERS_EXT_POINT_ID);
    }

    @Override
    public Iterable<IWizardPage> getPagesFor(String backend) {
        if (pageProviders.get(backend) == null) {
            ExportPageProvider provider =
                ExtensionsHelper.getClassFor(ATTR, backend, PROVIDERS_EXT_POINT_ID);
            if (provider != null) {
                pageProviders.put(backend, provider);
                return provider.getWizardPages(wizard);
            }
            return null;
        } else {
            return pageProviders.get(backend).getWizardPages(wizard);
        }
    }

    public Iterable<? extends Object> getConfigsFor(String backend, Project currentProject) {
        return pageProviders.get(backend).getConfigurations(currentProject);
    }

    public Iterable<Long> getSelectedSensorTracks(String backend) {
        return pageProviders.get(backend).getSelectedSensorTracks();
    }

    public Iterable<Long> getSelectedAnnotationTracks(String backend) {
        return pageProviders.get(backend).getSelectedAnnotationTracks();
    }

}