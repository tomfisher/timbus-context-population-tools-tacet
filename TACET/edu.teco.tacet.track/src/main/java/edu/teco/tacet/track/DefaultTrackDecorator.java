package edu.teco.tacet.track;

import java.util.Iterator;

public class DefaultTrackDecorator<T> implements Track<T> {

    protected final Track<? extends T> wrappedTrack;

    public DefaultTrackDecorator(Track<T> trackToWrap) {
        this.wrappedTrack = trackToWrap;
    }

    @Override
    public Iterable<? extends T> getData(final Range range) {
        return new Iterable<T>() {
            final Iterator<? extends T> wrappedIter =
                wrappedTrack.getData(range).iterator();

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {

                    @Override
                    public boolean hasNext() {
                        return wrappedIter.hasNext();
                    }

                    @Override
                    public T next() {
                        return handleDatum(wrappedIter.next());
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            };
        };
    }

    protected T handleDatum(T datum) {
        return datum;
    }

    @Override
    public MetaData getMetaData() {
        return wrappedTrack.getMetaData();
    }

    @Override
    public long getId() {
        return wrappedTrack.getId();
    }

    @Override
    public long getSourceId() {
        return wrappedTrack.getSourceId();
    }

    @Override
    public Range getCoveredRange() {
        return wrappedTrack.getCoveredRange();
    }

    /**
     * Request the complete covered Range from the underlying track and iterates through every
     * single datum.
     */
    public void force() {
        // just an empty loop to force the lazy Iterables.
        Range coveredRange = wrappedTrack.getCoveredRange();
        if (coveredRange != null) {
            for (@SuppressWarnings("unused") T datum : getData(coveredRange));
        }
    }
}
