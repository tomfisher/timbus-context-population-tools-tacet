package edu.teco.tacet.ui.mock;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;
import edu.teco.tacet.meta.nongen.FactoryUtil;
import edu.teco.tacet.mock.MockReader;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.DefaultIdGenerator;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.ui.project.ProjectView;

public class MockHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        System.out.println("Handler is executed.");
        DefaultIdGenerator idGen = new DefaultIdGenerator();
        MetaFactory factory = MetaFactory.eINSTANCE;
        if (!TrackManager.isInitialised()) {
            TrackManager.initialise(factory.createProject(), idGen, idGen);
        }
        long mockSourceId = idGen.generateSourceId();
        Reader mockReader = new MockReader(mockSourceId, new Range(0, 10000));
        TrackManager.getInstance().addReader(mockReader);

        Datasource mockSource = FactoryUtil.setDatasource(factory.createDatasource(), mockSourceId);
        Timeseries sensor1 =
            FactoryUtil.setTimeseries(factory.createTimeseries(), "Mocked Sensor 1",
                idGen.generateTrackId(), TimeseriesType.SENSOR);
        Timeseries annotation1 =
            FactoryUtil.setTimeseries(factory.createTimeseries(), "Mocked Annotations 1",
                idGen.generateTrackId(), TimeseriesType.ANNOTATION);

        mockSource.getTimeseries().add(sensor1);
        mockSource.getTimeseries().add(annotation1);

        Project currentProject = TrackManager.getInstance().getCurrentProject();
        currentProject.getDatasources().add(mockSource);

        ProjectView projectView = null;
        try {
            projectView = (ProjectView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().showView("edu.teco.tacet.project.ProjectView");
        } catch (PartInitException e) {
            e.printStackTrace();
        }

        projectView.setProject(currentProject);

        return null;
    }

}
