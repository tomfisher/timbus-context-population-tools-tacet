package edu.teco.tacet.importmanager;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.track.SourceIdGenerator;
import edu.teco.tacet.track.TrackIdGenerator;

public interface ImportPageProvider {

    /**
     * Returns the pages that are to be displayed in the import wizard. These pages are not added to
     * the Wizard. This requires them to set the Wizard instance themselves.
     * 
     * @param wizard the wizard the returned pages are managed by
     * @return wizard pages to be displayed by the import wizard.
     */
    Iterable<IWizardPage> getWizardPages(IWizard wizard);

    /**
     * Returns the information collected by the pages returned by
     * {@link ImportPageProvider#getWizardPages()}. This information is later passed to
     * {@link ReaderFactory#getReaderFor(String, Datasource)}. This method is only called after all
     * pages returned by this provider return {@code true} from {@link IWizardPage#isPageComplete()}
     * .
     * 
     * @param trackIdGen the TrackIdGenerator of the current project
     * @param sourceIdGen the SourceIdGenerator of the current project
     * @param currentProject the currentProject
     * @return the information collected by the pages returned from this provider.
     */
    Iterable<? extends Object> getConfigurations(TrackIdGenerator trackIdGen,
        SourceIdGenerator sourceIdGen, Project currentProject);
}
