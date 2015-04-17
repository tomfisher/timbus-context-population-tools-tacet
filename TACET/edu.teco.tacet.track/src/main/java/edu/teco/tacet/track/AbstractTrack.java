package edu.teco.tacet.track;

import edu.teco.tacet.readers.Reader;

public abstract class AbstractTrack<T> implements Track<T> {
    private MetaData metaData;
    private final long id;
    private long sourceId;
    private final Reader reader;

    public AbstractTrack(long id, long sourceId, Reader reader) {
        super();
        this.id = id;
        this.sourceId = sourceId;
        this.reader = reader;
        this.metaData = new MetaData();
    }

    public AbstractTrack(Reader reader, long id) {
        this(id, -1L, reader);
    }

    @Override
    public Track.MetaData getMetaData() {
        return metaData;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getSourceId() {
        return this.sourceId;
    }

    AbstractTrack<T> setSourceId(long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    AbstractTrack<T> setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    protected Reader getReader() {
        return this.reader;
    }

    @Override
    public Range getCoveredRange() {
        return reader.getCoveredRange(getId());
    }
}
