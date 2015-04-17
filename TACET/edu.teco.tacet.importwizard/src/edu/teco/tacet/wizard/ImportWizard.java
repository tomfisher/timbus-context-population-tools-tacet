package edu.teco.tacet.wizard;


import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.teco.tacet.importWizard.csv.CSVPageOne;
import edu.teco.tacet.importWizard.csv.CSVPageThree;
import edu.teco.tacet.importWizard.csv.CSVPageTwo;
import edu.teco.tacet.meta.CSVDatasource;
import edu.teco.tacet.meta.Column;
import edu.teco.tacet.meta.ColumnDescription;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.readers.csv.CSVReaderFactory;
import edu.teco.tacet.track.DefaultIdGenerator;
import edu.teco.tacet.track.SourceIdGenerator;
import edu.teco.tacet.track.TrackIdGenerator;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.ui.project.ProjectView;
import edu.teco.tacet.util.TablePage;
import edu.teco.tacet.util.TableEntry;


public class ImportWizard extends Wizard implements INewWizard {

	protected CSVPageOne one;
	protected CSVPageTwo two;
	protected CSVPageThree three;
	private Iterable<WizardPage> pages;
	public ImportWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	public void setPages(Iterable<WizardPage> pages){
		this.pages = pages;
	}

	@Override
	public void addPages() {		
		if(pages!=null){
			for (WizardPage page : pages) {
				addPage(page);
			}
		}

		addPage(one);
		addPage(two);
		addPage(three);
	}

	@Override
    public boolean performFinish() {
        // Print the result to the console
        System.out.println(one.getPath());

        TrackManager tm = null;
        try {
            tm = TrackManager.getInstance();
        } catch (IllegalStateException ise) {
            // This exception is caught, if we do not have a TrackManager jet.
            
            DefaultIdGenerator idGen = new DefaultIdGenerator();
            Project project = MetaFactory.eINSTANCE.createProject();
            TrackManager.initialise(project, idGen, idGen, (Reader) null);
            tm = TrackManager.getInstance();
        }
        
        createCSVDatasource(tm.getTrackIdGenerator(), tm.getSourceIdGenerator(),
            tm.getCurrentProject());
        
        ProjectView projectView = null; 
        try {
            projectView = (ProjectView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().showView("edu.teco.tacet.project.ProjectView");
        } catch (PartInitException e) {
            e.printStackTrace();
        }

        projectView.setProject(tm.getCurrentProject());
        
        return one.isPageComplete() && two.isPageComplete() && three.isPageComplete();
    }

    private void createCSVDatasource(TrackIdGenerator trackIdGen, SourceIdGenerator sourceIdGen, Project project) {
        MetaFactory factory = MetaFactory.eINSTANCE;
        CSVDatasource source = factory.createCSVDatasource();
        source.setId(sourceIdGen.generateSourceId());
        source.setLineSeparator("\n");
        source.setElementSeparator(two.getElementSeparator().toCharArray()[0]);
        source.setName(one.getPath());
        source.setFilePath(one.getPath());
        source.setTimestampFormat(two.getTimeStampFormat());
        source.setTreatTimestampAsMillis(two.isTreatTimestampAsMillis());
        for (TableEntry entry : three.getTableEntries()) {
            Timeseries series = factory.createTimeseries();
            series.setName(entry.getName());
            series.setId(trackIdGen.generateTrackId());
            
			ColumnDescription columnDescription = MetaFactory.eINSTANCE.createColumnDescription();
			columnDescription.setTimeseriesId(series.getId());
            switch (entry.getType()) {
            case ANNOTATION:
            	series.setType(TimeseriesType.ANNOTATION);
            	columnDescription.setColumnType(Column.ANNOTATION);
            	source.getColumnDescriptions().add(columnDescription);
            	break;
            case SENSOR:
            	columnDescription.setColumnType(Column.SENSOR_DATA);
                series.setType(TimeseriesType.SENSOR);
                source.getColumnDescriptions().add(columnDescription);
                break;
            default:
            	columnDescription.setColumnType(Column.TIMESTAMP);
            	source.getColumnDescriptions().add(columnDescription);
            	continue;
            }
            source.getTimeseries().add(series);
        }
       
        if (one.isHeader()) {
            source.setNoOfLinesToSkip(1);
        }
        project.getDatasources().add(source);
    }
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {			
		three = new CSVPageThree("Columns specification");
		two = new CSVPageTwo(three);
		one = new CSVPageOne(two, three);		
	}
}
