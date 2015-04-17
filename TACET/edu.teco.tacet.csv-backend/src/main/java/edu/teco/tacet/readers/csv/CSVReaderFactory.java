package edu.teco.tacet.readers.csv;

import edu.teco.tacet.meta.CSVDatasource;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.readers.ReaderFactory;

public class CSVReaderFactory implements ReaderFactory {


    @Override
    public Reader getReaderFor(String type, Datasource source) {
        return new CSVReader((CSVDatasource) source);
    }
}
