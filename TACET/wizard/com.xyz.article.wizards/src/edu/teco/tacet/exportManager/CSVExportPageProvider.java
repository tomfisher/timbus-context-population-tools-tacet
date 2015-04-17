package edu.teco.tacet.exportManager;

import java.util.ArrayList;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.export.DefaultExportPageProvider;
import edu.teco.tacet.export.TrackSelectionPage;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Track;
import edu.teco.tacet.track.TrackManager;
import edu.teco.tacet.wizard.ExportPageOne;
import edu.teco.tacet.wizard.ExportPageTwo;
import edu.teco.tacet.writer.csv.CsvExportDataSource;
import static edu.teco.tacet.util.collection.IterableAdditions.iterable;

public class CSVExportPageProvider extends DefaultExportPageProvider {

    private ArrayList<IWizardPage> csv_pages;
    public Iterable<Track<Datum>> sensorTracks;
    public Iterable<Track<Annotation>> annotTracks;
    private ExportPageOne pageOne;
    private ExportPageTwo pageTwo;

    public CSVExportPageProvider() {
        csv_pages = new ArrayList<IWizardPage>();
        ExportPageTwo pageTwo = new ExportPageTwo();
        ExportPageOne pageOne = new ExportPageOne(pageTwo);
        TrackSelectionPage trackSelectionPage = super.getTrackSelectionPage();
        trackSelectionPage.setNextPage(pageOne);
        csv_pages.add(trackSelectionPage);
        csv_pages.add(pageOne);
        csv_pages.add(pageTwo);
    }

    public ExportPageOne getPageOne() {
        return pageOne;
    }

    public void setPageOne(ExportPageOne pageOne) {
        this.pageOne = pageOne;
    }

    public ExportPageTwo getPageTwo() {
        return pageTwo;
    }

    public void setPageTwo(ExportPageTwo pageTwo) {
        this.pageTwo = pageTwo;
    }

    @Override
    public Iterable<IWizardPage> getWizardPages(IWizard wizard) {
        for (IWizardPage page : csv_pages) {
            page.setWizard(wizard);
        }
        return this.csv_pages;
    }

    @Override
    public Iterable<? extends Object> getConfigurations(Project currentProject) {
        CsvExportDataSource config = new CsvExportDataSource(
            TrackManager.getInstance().getGlobalRange(),
            pageOne.getSeperator(), pageOne.getNewPath(), "\n");
        return iterable(config);
    }

}
