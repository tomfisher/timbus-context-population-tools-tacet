package edu.teco.tacet.rdf;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.readers.ReaderFactory;

public class RdfReaderFactory implements ReaderFactory {

    @Override
    public Reader getReaderFor(String type, Datasource source) {
        return new RdfReader((RdfDatasource) source);
    }

}
