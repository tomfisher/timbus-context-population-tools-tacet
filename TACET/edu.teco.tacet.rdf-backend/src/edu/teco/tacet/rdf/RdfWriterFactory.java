package edu.teco.tacet.rdf;

import edu.teco.tacet.writers.Writer;
import edu.teco.tacet.writers.WriterFactory;

public class RdfWriterFactory implements WriterFactory {

    @Override
    public Writer getWriterFor(Object sinkInformation) {
        return new RdfWriter((RdfWriterInfo) sinkInformation);
    }
}
