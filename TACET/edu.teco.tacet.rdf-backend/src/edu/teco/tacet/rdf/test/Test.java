package edu.teco.tacet.rdf.test;

import static edu.teco.tacet.meta.TimeseriesType.ANNOTATION;
import static edu.teco.tacet.meta.TimeseriesType.SENSOR;
import static edu.teco.tacet.meta.nongen.FactoryUtil.createGroup;
import static edu.teco.tacet.meta.nongen.FactoryUtil.setDatasource;
import static edu.teco.tacet.meta.nongen.FactoryUtil.setTimeseries;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.RdfTimeseries;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.rdf.ui.RdfExportPageProvider;
import edu.teco.tacet.track.DefaultIdGenerator;
import edu.teco.tacet.track.TrackManager;

public class Test {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        
        DefaultIdGenerator idGen = new DefaultIdGenerator();
        TrackManager.initialise(createMockProject(), idGen, idGen);
        
        WizardDialog dialog = new WizardDialog(shell, new ImportWizard());
        dialog.open();
    }

    static class ImportWizard extends Wizard {

        @Override
        public void addPages() {
            RdfExportPageProvider provider = new RdfExportPageProvider();
            for (IWizardPage page : provider.getWizardPages(this)) {
                addPage(page);
            }
        }

        @Override
        public boolean performFinish() {
            return false;
        }

    }

    static Project createMockProject() {
        MetaFactory factory = MetaFactory.eINSTANCE;
        Project p = factory.createProject();
        Timeseries sensor1 = setTimeseries(factory.createTimeseries(), "Sensor 1", 1, SENSOR);
        Timeseries annotation1 =
            setTimeseries(factory.createTimeseries(), "Annotation 1", 2, ANNOTATION);
        Datasource source =
            setDatasource(factory.createDatasource(), "The Source", 1, false, sensor1, annotation1);
        p.getDatasources().add(source);
        p.getGroups().add(createGroup(3, "Test group", sensor1, annotation1));

        RdfTimeseries sensor2 =
            setTimeseries(factory.createRdfTimeseries(), "Rdf Sensor", 3, SENSOR);
        RdfTimeseries annotation2 =
            setTimeseries(factory.createRdfTimeseries(), "Rdf Annotation", 4, ANNOTATION);
        RdfDatasource rdfSource =
            setDatasource(factory.createRdfDatasource(), "Rdf Source", 5, sensor2, annotation2);
        p.getDatasources().add(rdfSource);
        p.getGroups().add(createGroup(4, "Rdf Group", sensor2, annotation2));

        return p;
    }
}
