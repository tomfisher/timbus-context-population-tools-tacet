package edu.teco.tacet.export;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.meta.Project;
import edu.teco.tacet.writers.WriterFactory;

public interface ExportPageProvider {

    /**
     * Returns the pages that are to be displayed in the export wizard. These pages are not added to
     * the Wizard. This requires them to set the Wizard instance themselves.
     * 
     * @param wizard the wizard the returned pages are managed by
     * @return wizard pages to be displayed by the export wizard.
     */
    Iterable<IWizardPage> getWizardPages(IWizard wizard);

    /**
     * Returns the information collected by the pages returned by
     * {@link ExportPageProvider#getWizardPages()}. This information is later passed to
     * {@link WriterFactory#getWriterFor(Object)}. This method is only called after all pages
     * returned by this provider return {@code true} from a call to
     * {@link IWizardPage#isPageComplete()}.
     * 
     * @param currentProject the currentProject
     * @return the information collected by the pages returned from this provider.
     */
    Iterable<?> getConfigurations(Project currentProject);

    /**
     * Returns the sensor tracks to be exported. This method is only called after all pages returned
     * by this provider return {@code true} from a call to {@link IWizardPage#isPageComplete()}.
     * 
     * @return the sensor tracks to be exported.
     */
    Iterable<Long> getSelectedSensorTracks();

    /**
     * Returns the annotation tracks to be exported. This method is only called after all pages
     * returned by this provider return {@code true} from a call to
     * {@link IWizardPage#isPageComplete()}.
     * 
     * @return the annotation tracks to be exported.
     */
    Iterable<Long> getSelectedAnnotationTracks();
}
