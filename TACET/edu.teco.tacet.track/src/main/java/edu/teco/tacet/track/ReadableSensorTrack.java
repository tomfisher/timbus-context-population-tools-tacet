package edu.teco.tacet.track;

import edu.teco.tacet.readers.Reader;

public class ReadableSensorTrack extends AbstractTrack<Datum> {

    private long resolution;

    public ReadableSensorTrack(long id, long sourceId, Reader reader) {
        super(id, sourceId, reader);
        this.resolution = 1;
    }

    @Override
    public Iterable<? extends Datum> getData(Range range) {
        return super.getReader().readSensorData(super.getId(), range, resolution);
    }

    public long getResolution() {
        return resolution;
    }

    public void setResolution(long resolution) {
        this.resolution = resolution;
    }

}
