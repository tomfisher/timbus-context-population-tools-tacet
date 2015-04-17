package edu.teco.tacet.writers;


public interface WriterFactory {

    Writer getWriterFor(Object sinkConfiguration);
}
