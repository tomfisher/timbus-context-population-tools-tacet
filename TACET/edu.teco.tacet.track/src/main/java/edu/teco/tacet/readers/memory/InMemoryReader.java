package edu.teco.tacet.readers.memory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.TrackIdGenerator;

/**
 * This Reader has no conventional data source, but holds all its data in memory. It is modifiable
 * so data can be inserted and deleted.
 */
public class InMemoryReader implements Reader {

    // this range is used by getCoveredRange() if the requested track does not contain any data
    private static final Range noDataRange = Range.point(0);

    private long sourceId;
    private TrackIdGenerator idGen;

    // The sets are sorted in ascending order by time stamps of their elements.
    private Map<Long, NavigableSet<Datum>> sensorData = new HashMap<>();
    private Map<Long, NavigableSet<Annotation>> annotationData = new HashMap<>();

    /**
     * Creates a new {@code InMemoryReader} with {@code sourceId} as its source id.
     *
     * @param sourceId the source id of this data source.
     */
    public InMemoryReader(long sourceId, TrackIdGenerator idGen) {
        this.sourceId = sourceId;
        this.idGen = idGen;
    }

    /**
     * Creates a new timeseries that holds {@link Annotation}s.
     *
     * @return the id of the new timeseries.
     */
    public long createNewAnnotationTimeseries() {
        long newId = idGen.generateTrackId();
        annotationData.put(newId,
            new TreeSet<>(Annotation.OVERLAP_IS_EQUAL_COMPARATOR));
        return newId;
    }

    /**
     * Creates a new timeseries that holds sensor data.
     *
     * @return the id of the new timeseries.
     */
    public long createNewSensorTimeseries() {
        long newId = idGen.generateTrackId();
        sensorData.put(newId, new TreeSet<Datum>(Datum.TIMESTAMP_COMPARATOR));
        return newId;
    }

    /**
     * Insert {@code annot} into the timeseries denoted by {@code trackId}. If there is no such
     * timeseries, an {@link IllegalArgumentException} is thrown. In case {@code annot} can not be
     * added, because it overlaps another {@link Annotation} that is already present, nothing is
     * changed.
     *
     * @param trackId denotes the timeseries
     * @param annot the datum to be inserted
     *
     * @return {@code false} if {@code annot} could not be inserted, {@code true} otherwise.
     */
    public boolean insertAnnotation(long trackId, Annotation annot) {
        NavigableSet<Annotation> track = annotationData.get(trackId);
        if (track == null)
            throw new IllegalArgumentException("No track with ID: " + trackId + "exists.");

        return track.add(annot);
    }

    /**
     * Insert {@code datum} into the timeseries denoted by {@code trackId}. If there is no such
     * timeseries, an {@link IllegalArgumentException} is thrown. In case {@code datum} can not be
     * added, because there already is a datum with the same timestamp, nothing is changed.
     *
     * @param trackId denotes the timeseries
     * @param datum the datum to be inserted
     *
     * @return {@code false} if {@code datum} could not be inserted, {@code true} otherwise.
     */
    public boolean insertSensorDatum(long trackId, Datum datum) {
        NavigableSet<Datum> track = sensorData.get(trackId);
        if (track == null)
            throw new IllegalArgumentException("No track with ID: " + trackId + "exists.");
        return track.add(datum);
    }

    public void deleteAnnotations(long trackId, Range toDelete) {
        NavigableSet<Annotation> track = annotationData.get(trackId);
        if (track == null)
            throw new IllegalArgumentException("No track with ID: " + trackId + "exists.");
        Annotation annot = null;
        Iterator<Annotation> iter = track.iterator();
        while (iter.hasNext()) {
            annot = iter.next();
            if (annot.getStart() >= toDelete.getEnd())
                break;
            if (annot.getRange().overlapsWith(toDelete)) {
                iter.remove();
            }
        }
    }

    @Override
    public Iterable<Datum> readSensorData(long trackId, Range range, long resolution) {
        NavigableSet<Datum> data = sensorData.get(trackId);
        if (data != null) {
            // Data in the requested range
            SortedSet<Datum> subset =
                data.subSet(new Datum(range.getStart(), 0d), new Datum(range.getEnd(), 0d));

            if (subset.isEmpty())
                return Collections.emptyList();

            // Data that is filtered according to the given resolution
            NavigableSet<Datum> filteredSet = new TreeSet<>(Datum.TIMESTAMP_COMPARATOR);
            filteredSet.add(subset.first());
            long lastTimestamp = subset.first().timestamp;
            for (Datum datum : subset) {
                if (datum.timestamp - lastTimestamp >= resolution) {
                    filteredSet.add(datum);
                    lastTimestamp = datum.timestamp;
                }
            }
            return filteredSet;
        }
        return Collections.emptyList();
    }

    @Override
    public Iterable<Annotation> readAnnotations(long trackId, Range range) {
        NavigableSet<Annotation> data = annotationData.get(trackId);
        SortedSet<Annotation> subSet = data.subSet(
            Annotation.createDummy(Range.point(range.getStart())),
            true, // including start
            Annotation.createDummy(Range.point(range.getEnd() - 1)),
            true); // including end
        return subSet;
    }

    @Override
    public Range getCoveredRange(long trackId) {
        NavigableSet<Datum> sensorTrack = sensorData.get(trackId);
        if (sensorTrack != null && !sensorTrack.isEmpty())
            return new Range(sensorTrack.first().timestamp, sensorTrack.last().timestamp + 1);

        NavigableSet<Annotation> annotTrack = annotationData.get(trackId);
        if (annotTrack != null && !annotTrack.isEmpty())
            return new Range(annotTrack.first().getStart(), annotTrack.last().getEnd() + 1);

        if (annotTrack == null && sensorTrack == null)
            throw new IllegalArgumentException("There is no timeseries with id: " + trackId);

        return noDataRange;
    }

    @Override
    public double getAverageDistance(long trackId) {
        NavigableSet<Datum> sensorTrack = sensorData.get(trackId);
        if (sensorTrack != null) {
            double avg = 0;
            double size = sensorTrack.size();
            long lastTimestamp = 0;
            for (Datum d : sensorTrack) {
                avg += (d.timestamp - lastTimestamp) / size;
            }
            return avg;
        }

        NavigableSet<Annotation> annotTrack = annotationData.get(trackId);
        if (annotTrack != null) {
            return 1;
        }

        throw new IllegalArgumentException("There is no timeseries with id: " + trackId);
    }

    @Override
    public long getSourceId() {
        return this.sourceId;
    }

    public TrackIdGenerator getIdGen() {
        return idGen;
    }

    public void setIdGen(TrackIdGenerator idGen) {
        this.idGen = idGen;
    }
}
