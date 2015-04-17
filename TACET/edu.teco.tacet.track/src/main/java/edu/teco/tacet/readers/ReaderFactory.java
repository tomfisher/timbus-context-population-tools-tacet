package edu.teco.tacet.readers;

import edu.teco.tacet.meta.Datasource;


public interface ReaderFactory {
    Reader getReaderFor(String type, Datasource source);
}
