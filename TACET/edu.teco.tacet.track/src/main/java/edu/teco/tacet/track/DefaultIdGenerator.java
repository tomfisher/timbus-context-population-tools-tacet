package edu.teco.tacet.track;

public class DefaultIdGenerator implements SourceIdGenerator, TrackIdGenerator {

    private long sourceIdCounter = 0;
    private long trackIdCounter = 0;

    @Override
    public long generateTrackId() {
        return trackIdCounter++;
    }

    @Override
    public long generateSourceId() {
        return sourceIdCounter++;
    }

}
