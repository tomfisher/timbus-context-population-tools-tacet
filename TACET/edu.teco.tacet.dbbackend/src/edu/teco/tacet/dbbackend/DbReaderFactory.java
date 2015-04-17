package edu.teco.tacet.dbbackend;


import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.readers.ReaderFactory;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.DbDatasource;

public class DbReaderFactory implements ReaderFactory {

	@Override
	public Reader getReaderFor(String type, Datasource source) {
		return new DtbaseReader((DbDatasource)source);
	}

}
