package edu.teco.tacet.importWizard.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;

import edu.teco.tacet.importmanager.ImportPageProvider;
import edu.teco.tacet.meta.CSVDatasource;
import edu.teco.tacet.meta.Column;
import edu.teco.tacet.meta.ColumnDescription;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;
import edu.teco.tacet.track.SourceIdGenerator;
import edu.teco.tacet.track.TrackIdGenerator;
import edu.teco.tacet.util.TableEntry;

public class CsvPageProvider implements ImportPageProvider {
	CSVPageThree pageThree;
    CSVPageTwo pageTwo;
    CSVPageOne pageOne;
    List<IWizardPage> pages = new ArrayList<>(3);

    @Override
    public Iterable<IWizardPage> getWizardPages(IWizard wizard) {
        if (pageOne == null) {
            pageThree = new CSVPageThree("Columns specification");
            pageTwo = new CSVPageTwo(pageThree);
            pageOne = new CSVPageOne(pageTwo, pageThree);
            pages.add(pageOne);
            pages.add(pageTwo);
            pages.add(pageThree);
            for (IWizardPage page : pages) {
                page.setWizard(wizard);
            }
        }
        return pages;
    }

    @Override
    public Iterable<? extends Object> getConfigurations(TrackIdGenerator trackIdGen,
        SourceIdGenerator sourceIdGen,
        Project currentProject) {
        CSVDatasource csvDatasource = createCSVDatasource(trackIdGen, sourceIdGen);
        currentProject.getDatasources().add(csvDatasource);
        return Arrays.asList(new Object[] { csvDatasource });
    }

    private CSVDatasource createCSVDatasource(TrackIdGenerator trackIdGen,
        SourceIdGenerator sourceIdGen) {
        MetaFactory factory = MetaFactory.eINSTANCE;
        CSVDatasource source = factory.createCSVDatasource();
        source.setId(sourceIdGen.generateSourceId());
        source.setLineSeparator(pageTwo.getLineSeperator());
        source.setElementSeparator(pageTwo.getElementSeparator().toCharArray()[0]);        
        source.setName(pageOne.getPath());
        source.setFilePath(pageOne.getPath());
        source.setTimestampFormat(pageTwo.getTimeStampFormat());
        source.setTreatTimestampAsMillis(pageTwo.isTreatTimestampAsMillis());
        source.setIsStartFrom1970(pageTwo.isStartFrom1970());
        if(pageTwo.isStartFrom1970()){
        	source.setTimeUnit(pageTwo.getUnit());        	
        }
        for (TableEntry entry : pageThree.getTableEntries()) {
            Timeseries series = factory.createTimeseries();
            series.setName(entry.getName());
            series.setId(trackIdGen.generateTrackId());

            ColumnDescription columnDescription = factory.createColumnDescription();
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

        if (pageOne.isHeader()) {
            source.setNoOfLinesToSkip(1);
        }
        return source;
    }
}
