package edu.teco.tacet.rdf.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.export.DefaultExportPageProvider;
import edu.teco.tacet.export.TrackSelectionPage;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.rdf.RdfWriterInfo;
import edu.teco.tacet.track.TrackManager;

public class RdfExportPageProvider extends DefaultExportPageProvider {

    private List<IWizardPage> pages = new ArrayList<>();
    private Project exportProject;

    public RdfExportPageProvider() {
        super();
        Project currentProject = TrackManager.getInstance().getCurrentProject();
        exportProject = MetaFactory.eINSTANCE.createProject();
        
        TargetFileSelectionPage targetFileSelectionPage =
            new TargetFileSelectionPage(exportProject);
        
        TrackSelectionPage trackSelectionPage = 
           new RdfTrackSelectionPage(targetFileSelectionPage, currentProject, exportProject);

        pages.add(trackSelectionPage);
        pages.add(targetFileSelectionPage);
        
        super.setTrackSelectionPage(trackSelectionPage);
    }

    @Override
    public Iterable<IWizardPage> getWizardPages(IWizard wizard) {
        for (IWizardPage page : pages) {
            page.setWizard(wizard);
        }
        return pages;
    }

    @Override
    public Iterable<?> getConfigurations(Project currentProject) {
        List<RdfWriterInfo> infos = new ArrayList<>();
        for (Datasource source : exportProject.getDatasources()) {
            infos.add(new RdfWriterInfo((RdfDatasource) source));
        }
        return infos;
    }

}
