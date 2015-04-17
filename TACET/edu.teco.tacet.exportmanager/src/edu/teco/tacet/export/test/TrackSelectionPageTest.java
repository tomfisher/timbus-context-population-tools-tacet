package edu.teco.tacet.export.test;

import static edu.teco.tacet.meta.TimeseriesType.ANNOTATION;
import static edu.teco.tacet.meta.TimeseriesType.SENSOR;
import static edu.teco.tacet.meta.nongen.FactoryUtil.setDatasource;
import static edu.teco.tacet.meta.nongen.FactoryUtil.createGroup;
import static edu.teco.tacet.meta.nongen.FactoryUtil.setTimeseries;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import edu.teco.tacet.export.TrackSelectionPage;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.track.DefaultIdGenerator;
import edu.teco.tacet.track.TrackManager;

public class TrackSelectionPageTest {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);       
        WizardDialog dialog = new WizardDialog(shell, new ImportWizard());
        dialog.open();
    }
    
    static class ImportWizard extends Wizard implements INewWizard {

        @Override
        public void addPages() {
            DefaultIdGenerator idGen = new DefaultIdGenerator();
            Project project = createMockProject();
            TrackManager.initialise(project, idGen, idGen);
            addPage(new TrackSelectionPage(null, project));
        }

        @Override
        public boolean performFinish() {
            return false;
        }

        @Override
        public void init(IWorkbench workbench, IStructuredSelection selection) {}
    }
    
    static Project createMockProject() {
        MetaFactory factory = MetaFactory.eINSTANCE;
        Project p = factory.createProject();
        Timeseries sensor1 = setTimeseries(factory.createTimeseries(), "Sensor 1", 1, SENSOR);
        Timeseries annotation1 = setTimeseries(factory.createTimeseries(), "Annotation 1", 2, ANNOTATION);
        Datasource source = setDatasource(factory.createDatasource(), "The Source", 1, true, sensor1, annotation1);
        p.getDatasources().add(source);
        p.getGroups().add(createGroup(3, "Test group", sensor1, annotation1));
        return p;
    }
}
