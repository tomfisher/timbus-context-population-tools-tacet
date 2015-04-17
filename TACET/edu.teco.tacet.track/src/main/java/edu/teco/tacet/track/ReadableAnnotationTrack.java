package edu.teco.tacet.track;

import edu.teco.tacet.readers.Reader;

public class ReadableAnnotationTrack
    extends AbstractTrack<Annotation> implements Track<Annotation> {

    public ReadableAnnotationTrack(long id, long sourceId,
        Reader reader) {
        super(id, sourceId, reader);
    }

    public ReadableAnnotationTrack(Reader reader, long id) {
        super(reader, id);
    }

    @Override
    public Iterable<? extends Annotation> getData(Range range) {
        return super.getReader()
            .readAnnotations(super.getId(), range);
    }
}
