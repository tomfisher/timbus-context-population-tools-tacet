package edu.teco.tacet.rdf.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.importmanager.ImportPageProvider;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.RdfTimeseries;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.track.SourceIdGenerator;
import edu.teco.tacet.track.TrackIdGenerator;

public class RdfImportPageProvider implements ImportPageProvider {

    RdfDatasource rdfDatasource = MetaFactory.eINSTANCE.createRdfDatasource();

    private List<IWizardPage> pages = new ArrayList<>();
    private TimeseriesIdentificationPage timeseriesIdentificationPage; 

    public RdfImportPageProvider() {
        timeseriesIdentificationPage = new TimeseriesIdentificationPage(rdfDatasource);

        ModelSelectionPage modelSelectionPage = new ModelSelectionPage(rdfDatasource);
        modelSelectionPage.setNextPage(timeseriesIdentificationPage);

        pages.add(modelSelectionPage);
        pages.add(timeseriesIdentificationPage);
    }

    @Override
    public Iterable<IWizardPage> getWizardPages(IWizard wizard) {
        for (IWizardPage page : pages) {
            page.setWizard(wizard);
        }
        return pages;
    }

    @Override
    public Iterable<? extends Object> getConfigurations(TrackIdGenerator trackIdGen,
        SourceIdGenerator sourceIdGen, Project currentProject) {
        
        List<String> rootResourceUris = timeseriesIdentificationPage.getRootResources();
        List<RdfDatasource> newDatasources = new ArrayList<>(rootResourceUris.size());
        
        for (String uri : rootResourceUris) {
            RdfDatasource newDatasource = MetaFactory.eINSTANCE.createRdfDatasource();
            for (Timeseries s : rdfDatasource.getTimeseries()) {
                RdfTimeseries series = (RdfTimeseries) s;
                RdfTimeseries newSeries = MetaFactory.eINSTANCE.createRdfTimeseries();
                newSeries.setDatasource(newDatasource);
                newSeries.setId(trackIdGen.generateTrackId());
                newSeries.setName(series.getName());
                newSeries.setType(series.getType());
                newSeries.setIdentifierValue(series.getIdentifierValue());
                for (String element : series.getIdentifierPath()) {
                    newSeries.getIdentifierPath().add(element);
                }
                for (String element : series.getTimestampPath()) {
                    newSeries.getTimestampPath().add(element);
                }
                for (String element : series.getValuePath()){
                    newSeries.getValuePath().add(element);
                }
                newSeries.setMapping(series.getMapping());
                newDatasource.getTimeseries().add(newSeries);
            }
            newDatasource.setResolveUri(rdfDatasource.getResolveUri());
            newDatasource.setName(uri);
            newDatasource.setFileName(rdfDatasource.getFileName());
            newDatasource.setModel(rdfDatasource.getModel());
            newDatasource.setTimestampFormat(timeseriesIdentificationPage.getTimestampFormat());
            newDatasource.setRootResourceUri(uri);
            newDatasource.setId(sourceIdGen.generateSourceId());
            currentProject.getDatasources().add(newDatasource);
            newDatasources.add(newDatasource);
        }
        
        return newDatasources;
    }

}
