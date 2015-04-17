package edu.tedo.tacet.media.importmanager;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MediaDatasource;
import edu.teco.tacet.meta.MediaTimeseries;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.DefaultIdGenerator;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.ui.project.ProjectView;

public class MediaImportGui extends Wizard implements IImportWizard {

	private MediaImportPage page;

	public MediaImportGui() {
		super();
		setNeedsProgressMonitor(true);
		page = new MediaImportPage();
		addPage(page);
		this.setHelpAvailable(false);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canFinish() {
		return page.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		if (canFinish()) {
            System.out.println("Finished Import Dialog!");
            if (!TrackManager.isInitialised()) {
                DefaultIdGenerator idGen = new DefaultIdGenerator();
                Project project = MetaFactory.eINSTANCE.createProject();
                TrackManager.initialise(project, idGen, idGen, (Reader[]) null);

                TrackManager.getInstance().updateGlobalRange(new Range(0, page.getLength()));
            }
            TrackManager tm = TrackManager.getInstance();
            MediaDatasource datasource = null;
            boolean exists = false;
            
            for(Datasource d: tm.getCurrentProject().getDatasources()) {
            	if (d instanceof MediaDatasource) {
            		datasource = (MediaDatasource) d;
            		exists = true;
            		break;
            	}
            }
            
            MediaTimeseries mTimeseries =  MetaFactory.eINSTANCE.createMediaTimeseries();
            mTimeseries.setFilepath(page.gettPath());
            mTimeseries.setName(page.getName());
            mTimeseries.setId(tm.getSourceIdGenerator().generateSourceId());
            
            if(!exists) {
            	datasource = MetaFactory.eINSTANCE.createMediaDatasource();
            	datasource.setName("MediaDatasource");
            	datasource.setId(tm.getSourceIdGenerator().generateSourceId());
            	tm.getCurrentProject().getDatasources().add(datasource);
            }
            

        	datasource.getTimeseries().add(mTimeseries);
            
//            exists = false;
//            
//            for(Group g : tm.getCurrentProject().getGroups()) {
//            	if(g.getName().equals("Media")) {
//            		g.getTimeseries().add(mTimeseries);
//            		exists = true;
//            		break;
//            	}
//            }
//            
//            if(!exists) {
//            	Group group = MetaFactory.eINSTANCE.createGroup();
//        		group.setName("Media");
//        		group.setId(TrackManager.getInstance().getSourceIdGenerator().generateSourceId());
//            	tm.getCurrentProject().getGroups().add(group);
//            	group.getTimeseries().add(mTimeseries);
//            }

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

}
