package edu.teco.tacet.track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.readers.memory.InMemoryReader;

public class TrackManager {

    private static TrackManager INSTANCE = null;

    public synchronized static TrackManager getInstance() {
        if (INSTANCE == null)
            throw new IllegalStateException("TrackManager has not been initialised.");
        return INSTANCE;
    }

    public static boolean isInitialised() {
        return INSTANCE != null;
    }

    public synchronized static void initialise(
        Project project,
        SourceIdGenerator sourceIdGen, TrackIdGenerator trackIdGen,
        Reader... readers) {
        INSTANCE = new TrackManager(project, sourceIdGen, trackIdGen, readers);
    }

    public synchronized static void initialise(
        Project project,
        SourceIdGenerator sourceIdGen, TrackIdGenerator trackIdGen) {
        initialise(project, sourceIdGen, trackIdGen, (Reader[]) null);
    }

    private Project currentProject;
    private Datasource inMemoryDatasource;
    private SourceIdGenerator sourceIdGen;
    private TrackIdGenerator trackIdGen;

    private Map<Long, ReadableSensorTrack> sensorTracks = new HashMap<>();
    private Map<Long, AnnotationTrack> annotationTracks = new HashMap<>();
    private Map<Long, Reader> readers = new HashMap<>();

    private InMemoryReader trackBuffer;
    private Range globalRange = null;

    public TrackManager(Project project, SourceIdGenerator sourceIdGen,
        TrackIdGenerator trackIdGen, Reader... readers) {
        currentProject = project;
        if (readers != null) {
            for (Reader reader : readers) {
                this.readers.put(reader.getSourceId(), reader);
            }
        }

        this.sourceIdGen = sourceIdGen;
        this.trackIdGen = trackIdGen;

        inMemoryDatasource = MetaFactory.eINSTANCE.createDatasource();
        inMemoryDatasource.setName("Tracks in Memory");
        inMemoryDatasource.setId(sourceIdGen.generateSourceId());
        inMemoryDatasource.setIsInMemory(true);
        currentProject.getDatasources().add(inMemoryDatasource);
        trackBuffer = new InMemoryReader(inMemoryDatasource.getId(), trackIdGen);
    }

    public void addReader(Reader reader) {
        this.readers.put(reader.getSourceId(), reader);
    }

    /**
     * Returns the global range {@code g} with {@code Range g = new Range(minTs, maxTs + 1)}.
     * {@code minTs} is the smallest of all {@link Datum#timestamp} or {@link Annotation#getStart()}
     * on all tracks managed by this instance. {@code maxTs} is the largest of all
     * {@link Datum#timestamp} or {@link Annotation#getEnd()} on all tracks managed by this
     * instance. Note: it is possible for {@code g} to not contain any data in case this instance
     * does not manage any tracks, or no managed track contains any data.
     *
     * @return the global range
     */
    public Range getGlobalRange() {
        if (globalRange == null) {
            // the global range has not yet been computed, do it now
            updateGlobalRange();
        }
        return globalRange;
    }

    private void updateGlobalRange() {
        Range acc = null;
        for (Long id : getTrackIdsFromProject(TimeseriesType.SENSOR)) {
            acc = getSensorTrack(id).getCoveredRange().union(acc);
        }
        for (Long id : getTrackIdsFromProject(TimeseriesType.ANNOTATION)) {
            acc = getAnnotationTrack(id).getCoveredRange().union(acc);
        }
        globalRange = acc;

        StateObservable.getInstance().updateSchedulerRange(globalRange.getStart(), globalRange.getEnd());
    }

    public void updateGlobalRange(Range range) {
        globalRange = range.union(globalRange);

        StateObservable.getInstance().updateSchedulerRange(globalRange.getStart(), globalRange.getEnd());
    }

    public ReadableSensorTrack getSensorTrack(long trackId) {
        ReadableSensorTrack track = sensorTracks.get(trackId);
        if (track != null)
            return track;
        else {
            track = getSensorTrackFromProject(trackId);
            sensorTracks.put(trackId, track);
            return track;
        }
    }

    public AnnotationTrack getAnnotationTrack(long trackId) {
        AnnotationTrack track = annotationTracks.get(trackId);
        if (track != null)
            return track;
        else {
            track = getAnnotationTrackFromProject(trackId);
            annotationTracks.put(trackId, track);
            return track;
        }
    }

    public Datasource getDatasource(long sourceId) {
        for (Datasource source : currentProject.getDatasources()) {
            if (source.getId() == sourceId) {
                return source;
            }
        }
        return null;
    }

    private ReadableSensorTrack getSensorTrackFromProject(long trackId) {
        for (Datasource source : currentProject.getDatasources()) {
            for (Timeseries series : source.getTimeseries()) {
                if (series.getId() == trackId && series.getType() == TimeseriesType.SENSOR) {
                    ReadableSensorTrack track =
                        new ReadableSensorTrack(trackId, source.getId(), getReader(source));
                    track.getMetaData().setName(series.getName());
                    return track;
                }
            }
        }
        return null;
    }

    private AnnotationTrack getAnnotationTrackFromProject(long trackId) {
        for (Datasource source : currentProject.getDatasources()) {
            for (Timeseries series : source.getTimeseries()) {
                if (series.getId() == trackId && series.getType() == TimeseriesType.ANNOTATION) {
                    AnnotationTrack track =
                        new AnnotationTrack(trackId, source.getId(), getReader(source), trackBuffer);
                    track.getMetaData().setName(series.getName());
                    return track;
                }
            }
        }
        return null;
    }

    private Reader getReader(Datasource source) {
        return readers.get(source.getId());
    }

    public Iterable<ReadableSensorTrack> getAllSensorTracks() {
        // make sure all sensor tracks from project are loaded
        for (Long id : getTrackIdsFromProject(TimeseriesType.SENSOR)) {
            getSensorTrack(id);
        }
        return sensorTracks.values();
    }

    public Iterable<AnnotationTrack> getAllAnnotationTracks() {
        // make sure all annotation tracks from project are loaded
        for (Long id : getTrackIdsFromProject(TimeseriesType.ANNOTATION)) {
            getAnnotationTrack(id);
        }
        return annotationTracks.values();
    }

    private Iterable<Long> getTrackIdsFromProject(TimeseriesType timeseriesType) {
        List<Long> ret = new ArrayList<>();
        for (Datasource source : currentProject.getDatasources()) {
            for (Timeseries series : source.getTimeseries()) {
                if (series.getType() == timeseriesType)
                    ret.add(series.getId());
            }
        }
        return ret;
    }

    public long createSensorTrack(String name) {
        long trackId = trackBuffer.createNewSensorTimeseries();
        buildInMemoryTimeseries(name, trackId, TimeseriesType.SENSOR);

        ReadableSensorTrack sensorTrack =
            new ReadableSensorTrack(trackId, inMemoryDatasource.getId(), trackBuffer);
        sensorTrack.getMetaData().setName(name);
        sensorTracks.put(trackId, sensorTrack);

        return trackId;
    }

    public long createAnnotationTrack(String name) {
        long trackId = trackBuffer.createNewAnnotationTimeseries();
        buildInMemoryTimeseries(name, trackId, TimeseriesType.ANNOTATION);

        AnnotationTrack annotationTrack =
            new AnnotationTrack(trackId, inMemoryDatasource.getId(), trackBuffer, trackBuffer);
        annotationTrack.getMetaData().setName(name);
        annotationTracks.put(trackId, annotationTrack);

        return trackId;
    }

    /**
     * Create a new sensor track using {@code data} as its underlying data. Given {@code name} is
     * the name of the resulting track. The created track is not associated with any datasource.
     *
     * @param iterable the data the track represents
     * @param name the name of the track to be created
     *
     * @return the id of the created track
     */
    public long createSensorTrackFrom(Iterable<? extends Datum> iterable, String name) {
        long id = createSensorTrack(name);
        for (Datum datum : iterable) {
            trackBuffer.insertSensorDatum(id, datum);
        }
        return id;
    }

    /**
     * Create a new annotation track using {@code annots} as its underlying data. Given {@code name}
     * is the name of the resulting track. The created track is not associated with any datasource.
     *
     * @param iterable the data the track represents
     * @param name the name of the track to be created
     *
     * @return the id of the created track
     */
    public long createAnnotationTrackFrom(Iterable<? extends Annotation> iterable, String name) {
        long id = createAnnotationTrack(name);
        for (Annotation annot : iterable) {
            trackBuffer.insertAnnotation(id, annot);
        }
        return id;
    }

    private Timeseries buildInMemoryTimeseries(String name, long trackId, TimeseriesType type) {
        Timeseries series = MetaFactory.eINSTANCE.createTimeseries();
        series.setId(trackId);
        series.setType(type);
        series.setName(name);
        series.setDatasource(inMemoryDatasource);
        return series;
    }

    public TrackIdGenerator getTrackIdGenerator() {
        return trackIdGen;
    }

    public SourceIdGenerator getSourceIdGenerator() {
        return sourceIdGen;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        currentProject.eAdapters().add(new ProjectListener());
        this.currentProject = currentProject;
    }

    private class ProjectListener extends EContentAdapter {

        @Override
        public void notifyChanged(Notification notification) {
            super.notifyChanged(notification);
            switch (notification.getEventType()) {
            case Notification.ADD:
            case Notification.ADD_MANY:
                Object feature = notification.getFeature();
                if (feature instanceof Project) {
                    Project project = (Project) feature;
                    if (!project.getDatasources().isEmpty()) {
                        updateGlobalRange();
                    } else {
                        for (Datasource source : project.getDatasources()) {
                            source.eAdapters().add(this);
                        }
                    }
                } else if (feature instanceof Datasource
                    && (((Datasource) feature).getTimeseries().isEmpty())) {
                    updateGlobalRange();
                }
            }
        }

    }
}
