package edu.teco.tacet.backend;

import org.eclipse.jface.wizard.IWizardPage;

public interface BackendManager {

    Iterable<String> getAvailableBackends();

    Iterable<IWizardPage> getPagesFor(String backend);
    
}
