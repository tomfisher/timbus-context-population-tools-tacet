package edu.teco.tacet.export;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import edu.teco.tacet.backend.BackendSelectionPage;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Track;
import edu.teco.tacet.track.TrackManager;

public class ExportManagerGui extends Wizard implements INewWizard {

    private ExportManager backendManager;
    private BackendSelectionPage backendSelectionPage;

    public ExportManagerGui() {
        super();
        setNeedsProgressMonitor(true);
        setHelpAvailable(false);
        this.backendManager = new ExportManager(this);
        this.backendSelectionPage = new BackendSelectionPage(backendManager);
        addPage(backendSelectionPage);
    }

    @Override
    public void addPages() {
        super.addPages();
    }

    @Override
    public boolean canFinish() {
        String backend = backendSelectionPage.getSelectedBackend();
        if (backend == null) {
            return false;
        }
        boolean canFinish = true;
        for (IWizardPage page : backendManager.getPagesFor(backend)) {
            canFinish = canFinish && page != null && page.isPageComplete();
        }
        return canFinish;
    }

    @Override
    public boolean performFinish() {
        if (canFinish()) {
            String sinkType = backendSelectionPage.getSelectedBackend();
            System.out.println("Finished Export Dialog!");

            TrackManager tm = TrackManager.getInstance();
            Iterable<? extends Object> configurations =
                backendManager.getConfigsFor(sinkType, tm.getCurrentProject());

            List<Track<? extends Datum>> sensorTracks = new ArrayList<>();
            for (Long id : backendManager.getSelectedSensorTracks(sinkType)) {
                sensorTracks.add(tm.getSensorTrack(id));
            }

            List<Track<? extends Annotation>> annotationTracks = new ArrayList<>();
            for (Long id : backendManager.getSelectedAnnotationTracks(sinkType)) {
                annotationTracks.add(tm.getAnnotationTrack(id));
            }

            for (Object config : configurations) {
                Export export = Export.createFrom(sensorTracks,
                    annotationTracks, sinkType, config);

                export.run();
            }

            return true;
        }
        return false;
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {}

}
