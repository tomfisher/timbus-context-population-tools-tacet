package edu.teco.tacet.writers;

import org.eclipse.core.runtime.jobs.Job;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Track;

public interface Writer {

    Job createJob(Iterable<Track<? extends Datum>> sensorTracks,
        Iterable<Track<? extends Annotation>> annotationTracks);

}
