package edu.teco.tacet.writer.csv;

import edu.teco.tacet.writers.Writer;
import edu.teco.tacet.writers.WriterFactory;

public class CsvWriterFactory implements WriterFactory {

    @Override
    public Writer getWriterFor(Object sinkConfiguration) {
        return new CsvWriter((CsvExportDataSource) sinkConfiguration);
    }

}
