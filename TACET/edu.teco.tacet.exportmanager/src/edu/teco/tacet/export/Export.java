package edu.teco.tacet.export;

import org.eclipse.core.runtime.jobs.Job;

import edu.teco.tacet.backend.ExtensionsHelper;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Track;
import edu.teco.tacet.writers.Writer;
import edu.teco.tacet.writers.WriterFactory;

public abstract class Export {
    private static final String ATTR_SOURCE_TYPE = "sink_type";
    private static final String WRITERS_EXT_POINT_ID = "edu.teco.tacet.writers";
    private static final String PROVIDERS_EXT_POINT_ID = "edu.teco.tacet.pageproviders";

    public abstract Iterable<Track<? extends Datum>> getSensorTracks();

    public abstract Iterable<Track<? extends Annotation>> getAnnotationTracks();

    public abstract String getSinkType();
    
    public abstract Object getSinkConfiguration();

    public static Export createFrom(
        final Iterable<Track<? extends Datum>> sensorTracks,
        final Iterable<Track<? extends Annotation>> annotTracks,
        final String sinkType,
        final Object sinkConfiguration) {
        return new Export() {
            @Override
            public Iterable<Track<? extends Datum>> getSensorTracks() {
                return sensorTracks;
            }

            @Override
            public Iterable<Track<? extends Annotation>> getAnnotationTracks() {
                return annotTracks;
            }

            @Override
            public String getSinkType() {
                return sinkType;
            }

            @Override
            public Object getSinkConfiguration() {
                return sinkConfiguration;
            }

        };
    }

    public static Iterable<String> getWriterTypes() {
        return ExtensionsHelper.getExtensionsGroupedByAttribute(ATTR_SOURCE_TYPE,
            WRITERS_EXT_POINT_ID, PROVIDERS_EXT_POINT_ID);
    }

    public static WriterFactory getWriterFactoryFor(String writerType) {
        return ExtensionsHelper.getClassFor(ATTR_SOURCE_TYPE, writerType,
            WRITERS_EXT_POINT_ID);
    }

    public void run() {
        WriterFactory factory = getWriterFactoryFor(getSinkType());
        Writer writer = factory.getWriterFor(getSinkConfiguration());
        Job job = writer.createJob(getSensorTracks(), getAnnotationTracks());
        job.schedule();
    }
}
