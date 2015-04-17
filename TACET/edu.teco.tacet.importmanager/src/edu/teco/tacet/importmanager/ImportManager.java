package edu.teco.tacet.importmanager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.backend.BackendManager;
import edu.teco.tacet.backend.ExtensionsHelper;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.readers.ReaderFactory;
import edu.teco.tacet.track.SourceIdGenerator;
import edu.teco.tacet.track.TrackIdGenerator;

public class ImportManager implements BackendManager {

    private static final String ATTR_SOURCE_TYPE = "source_type";
    private static final String READERS_EXT_POINT_ID = "edu.teco.tacet.readers";
    private static final String PROVIDERS_EXT_POINT_ID = "edu.teco.tacet.import.pageproviders";

    private Map<String, ImportPageProvider> pageProviders = new HashMap<>();
    private IWizard wizard;

    public ImportManager(IWizard wizard) {
        this.wizard = wizard;
    }

    public Iterable<String> getAvailableBackends() {
        return ExtensionsHelper.getExtensionsGroupedByAttribute(ATTR_SOURCE_TYPE,
            READERS_EXT_POINT_ID, PROVIDERS_EXT_POINT_ID);
    }

    public Iterable<IWizardPage> getPagesFor(String readerType) {
        if (pageProviders.get(readerType) == null) {
            ImportPageProvider provider =
                ExtensionsHelper.getClassFor(ATTR_SOURCE_TYPE, readerType, PROVIDERS_EXT_POINT_ID);
            if (provider != null) {
                pageProviders.put(readerType, provider);
                return provider.getWizardPages(wizard);
            }
            return null;
        } else {
            return pageProviders.get(readerType).getWizardPages(wizard);
        }
    }

    public Iterable<? extends Object> getConfigsFor(String backendType, TrackIdGenerator trackIdGen,
        SourceIdGenerator sourceIdGen, Project currentProject) {
        return pageProviders.get(backendType).getConfigurations(trackIdGen, sourceIdGen,
            currentProject);
    }

    public ReaderFactory getReaderFactoryFor(String readerType) {
        return ExtensionsHelper.getClassFor(ATTR_SOURCE_TYPE, readerType, READERS_EXT_POINT_ID);
    }
}
