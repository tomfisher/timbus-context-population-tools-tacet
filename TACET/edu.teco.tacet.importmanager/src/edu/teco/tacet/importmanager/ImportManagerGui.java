package edu.teco.tacet.importmanager;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.teco.tacet.backend.BackendSelectionPage;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.readers.ReaderFactory;
import edu.teco.tacet.track.DefaultIdGenerator;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.ui.project.ProjectView;

public class ImportManagerGui extends Wizard implements IWorkbenchWizard {

    private ImportManager backendManager;
    private BackendSelectionPage backendSelectionPage;

    public ImportManagerGui() {
        super();
        setNeedsProgressMonitor(true);
        this.backendManager = new ImportManager(this);
        this.backendSelectionPage = new BackendSelectionPage(backendManager);
        addPage(backendSelectionPage);
    }

    @Override
    public void addPages() {
        super.addPages();
    }

    @Override
    public boolean canFinish() {
        String readerType = backendSelectionPage.getSelectedBackend();
        if (readerType == null) {
            return false;
        }
        boolean canFinish = true;
        for (IWizardPage page : backendManager.getPagesFor(readerType)) {
            canFinish = canFinish && page != null && page.isPageComplete();
        }
        return canFinish;
    }

    @Override
    public boolean performFinish() {
        if (canFinish()) {
            String readerType = backendSelectionPage.getSelectedBackend();
            System.out.println("Finished Import Dialog!");
            if (!TrackManager.isInitialised()) {
                DefaultIdGenerator idGen = new DefaultIdGenerator();
                Project project = MetaFactory.eINSTANCE.createProject();
                TrackManager.initialise(project, idGen, idGen, (Reader[]) null);
            }
            TrackManager tm = TrackManager.getInstance();
            ReaderFactory factory = backendManager.getReaderFactoryFor(readerType);
            Iterable<? extends Object> configurations =
                backendManager.getConfigsFor(readerType, tm.getTrackIdGenerator(),
                    tm.getSourceIdGenerator(), tm.getCurrentProject());
            for (Object config : configurations) {
                tm.addReader(factory.getReaderFor(readerType, (Datasource) config));
            }

            ProjectView projectView = null;
            try {
                projectView = (ProjectView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().showView("edu.teco.tacet.project.ProjectView");
            } catch (PartInitException e) {
                e.printStackTrace();
            }

            projectView.setProject(tm.getCurrentProject());
            return true;
        }
        return false;
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {}

}
