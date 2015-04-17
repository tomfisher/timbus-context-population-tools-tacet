package edu.teco.tacet.rdf;

import static edu.teco.tacet.meta.nongen.DatasourceUtil.getTimeseries;
import static edu.teco.tacet.rdf.RdfUtil.getSubject;
import static edu.teco.tacet.rdf.RdfUtil.getObject;
import static edu.teco.tacet.rdf.RdfUtil.getAllObjects;
import static edu.teco.tacet.rdf.RdfUtil.getAllSubjects;
import static edu.teco.tacet.rdf.RdfUtil.greatestCommonPrefix;
import static edu.teco.tacet.rdf.RdfUtil.getValueSubjectFor;
import static edu.teco.tacet.util.collection.IterableAdditions.filter;
import static edu.teco.tacet.util.collection.ListAdditions.binaryFindLower;
import static edu.teco.tacet.util.collection.ListAdditions.binaryFindUpper;
import static edu.teco.tacet.util.collection.IterableAdditions.map;
import static edu.teco.tacet.util.collection.ListAdditions.map;
import static edu.teco.tacet.util.collection.ListAdditions.sort;
import static edu.teco.tacet.util.function.Cached.cached;
import static edu.teco.tacet.util.function.Functions.id;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.RdfTimeseries;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.util.function.F1;
import edu.teco.tacet.util.function.Predicate;

public class RdfReader implements Reader {

    public static Model readModelFromFile(String fileName, String resolveUri) {
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(fileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + fileName + " not found");
        }
        if (resolveUri == null) {
            model.read(in, "");
        } else {
            model.read(in, resolveUri);
        }
        return model;
    }

    public static Model readOntologyModelFromFile(String fileName, String resolveUri) {
        Model model = ModelFactory.createOntologyModel();
        InputStream in = FileManager.get().open(fileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + fileName + " not found");
        }
        if (resolveUri == null) {
            model.read(in, "");
        } else {
            model.read(in, resolveUri);
        }
        return model;
    }

    // this range is used by getCoveredRange() if the requested track does not contain any data
    private static final Range noDataRange = Range.point(0);

    private Model model;
    private RdfDatasource datasource;
    private Resource root;

    private Map<Long, TrackBuffer<Datum>> sensorData = new HashMap<>();
    private Map<Long, TrackBuffer<Annotation>> annotations = new HashMap<>();

    private class TrackBuffer<T> {
        List<T> data = new ArrayList<>();
        List<Property> timestampPath;
        List<Property> valuePath;
        List<Property> identifierPath;
        List<Property> gcp;
        final F1<RDFNode, Long> nodeToTimestamp =
            cached(new F1<RDFNode, Long>() {
                final DateTimeFormatter fmt =
                    DateTimeFormat.forPattern(datasource.getTimestampFormat());

                @Override
                public Long apply(RDFNode subject) {
                    String timestamp = getSubject(subject, timestampPath).asResource()
                        .getProperty(last(timestampPath))
                        .getObject().asLiteral().getString();
                    return fmt.parseMillis(timestamp);
                }
            });
    }

    public RdfReader(RdfDatasource datasource) {
        this.datasource = datasource;
        if ((model = datasource.getModel()) == null) {
            model = readModelFromFile(datasource.getFileName(), datasource.getResolveUri());
        }
        root = model.getResource(datasource.getRootResourceUri());
    }

    @Override
    public Iterable<Datum> readSensorData(long trackId, Range range, long resolution) {
        TrackBuffer<Datum> trackBuffer = getSensorTrackBuffer(trackId);

        F1<Datum, Long> datumToTimestamp = new F1<Datum, Long>() {
            @Override
            public Long apply(Datum t) {
                return t.timestamp;
            }
        };

        if (range.intersection(getCoveredRange(trackId)) == null) {
            return Collections.emptyList();
        }

        int start = binaryFindUpper(trackBuffer.data, range.getStart(), datumToTimestamp);
        int end = binaryFindLower(trackBuffer.data, range.getEnd() - 1, datumToTimestamp);

        if (end < start) { // no datum with a timestamp between range.getStart() and range.getEnd()
            return Collections.emptyList();
        }

        List<Datum> subList = trackBuffer.data.subList(start, end + 1);
        // filter out datum instances to fit the resolution
        long currentTs = subList.get(0).timestamp;
        for (int i = 1; i < subList.size(); i++) {
            if (subList.get(i).timestamp - currentTs < resolution) {
                subList.remove(i);
            }
            currentTs = subList.get(i).timestamp;
        }

        return subList;
    }

    private TrackBuffer<Datum> getSensorTrackBuffer(long trackId) {
        TrackBuffer<Datum> trackBuffer = sensorData.get(trackId);
        if (trackBuffer == null) {
            trackBuffer = makeTrackBuffer(trackId);
            List<RDFNode> subjects = getAllObjects(root, trackBuffer.gcp);
            // sort all subjects ascending by timestamp
            sort(subjects, trackBuffer.nodeToTimestamp);
            trackBuffer.data = convertToSensorData(trackId, subjects, trackBuffer);
            sensorData.put(trackId, trackBuffer);
        }
        return trackBuffer;
    }

    private List<Datum> convertToSensorData(long trackId, List<RDFNode> subjects,
        final TrackBuffer<Datum> buffer) {
        final RdfTimeseries timeseries = (RdfTimeseries) getTimeseries(datasource, trackId);
        // convert the information in the subtrees into Datum instances
        List<Datum> sensorData = map(cached(new F1<RDFNode, Datum>() {
            @Override
            public Datum apply(RDFNode subject) {
                long ts = buffer.nodeToTimestamp.apply(subject);
                
                RDFNode valueSubject = getValueSubjectFor(
                    timeseries.getIdentifierValue(), subject, buffer.valuePath, buffer.identifierPath);
                
                Object valueObject = valueSubject.asResource()
                    .getProperty(last(buffer.valuePath)).getLiteral().getValue();
                
                // We can handle plain literals and literals typed as either a number or a string.
                // In the latter case, we try to parse a double from it.
                double value = Double.NaN;
                if (valueObject instanceof Number) {
                    value = ((Number) valueObject).doubleValue();
                } else if (valueObject instanceof String) {
                    try {
                        value = Double.parseDouble((String) valueObject);
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException(
                            valueObject + " can not be interpreted as a decimal number.");
                    }
                }
                
                String uri = subject.asResource().getURI();
                return new RdfDatum(ts, value, uri);
            }
        }), subjects);
        return sensorData;
    }

    @Override
    public Iterable<Annotation> readAnnotations(long trackId, Range range) {
        TrackBuffer<Annotation> trackBuffer = getAnnotationTrackBuffer(trackId);

        F1<Annotation, Annotation> id = id();
        int start =
            binaryFindLower(trackBuffer.data,
                Annotation.createDummy(Range.point(range.getStart())), id,
                Annotation.OVERLAP_IS_EQUAL_COMPARATOR);
        
        start = start < 0 ? 0 : start; 
        
        int end =
            binaryFindUpper(trackBuffer.data,
                Annotation.createDummy(Range.point(range.getEnd())), id,
                Annotation.OVERLAP_IS_EQUAL_COMPARATOR);

        if (!getCoveredRange(trackId).overlapsWith(range)) {
            return Collections.emptyList();
        }

        return trackBuffer.data.subList(start, end);
    }

    private TrackBuffer<Annotation> getAnnotationTrackBuffer(long trackId) {
        TrackBuffer<Annotation> trackBuffer = annotations.get(trackId);
        if (trackBuffer == null) {
            trackBuffer = makeTrackBuffer(trackId);
            List<RDFNode> subjects = getAllObjects(root, trackBuffer.gcp);
            // sort all subjects ascending by timestamp
            sort(subjects, trackBuffer.nodeToTimestamp);
            trackBuffer.data = convertToAnnotations(trackId, subjects, trackBuffer);
            annotations.put(trackId, trackBuffer);
        }
        return trackBuffer;
    }

    private List<Annotation> convertToAnnotations(long trackId, List<RDFNode> subjects,
        final TrackBuffer<Annotation> buffer) {
        final RdfTimeseries timeseries = (RdfTimeseries) getTimeseries(datasource, trackId);
        // convert the information in the subtrees into Annotation instances
        Iterable<? extends Annotation> unmerged = map(new F1<RDFNode, Annotation>() {
            @Override
            public Annotation apply(RDFNode subject) {
                long ts = buffer.nodeToTimestamp.apply(subject);
                String value = "";

                for (RDFNode valueSubject : getAllSubjects(subject, buffer.valuePath)) {
                    RDFNode identifier = getObject(valueSubject, buffer.identifierPath);
                    if (identifier != null &&
                        (identifier.isLiteral() &&
                        identifier.asLiteral().getString().equals(timeseries.getIdentifierValue()))
                        ||
                        (identifier.isResource() &&
                        identifier.asResource().getURI().equals(timeseries.getIdentifierValue()))) {
                        value = valueSubject.asResource()
                            .getProperty(last(buffer.valuePath)).getString();
                    }
                }
                String uri = subject.asResource().getURI();
                return new RdfAnnotation(Range.point(ts), value, "", uri);
            }
        }, (Iterable<RDFNode>) subjects);

        // not every subject has an annotation, take only those that are annotated.
        unmerged = filter(new Predicate<Annotation>() {
            @Override
            public Boolean apply(Annotation annotation) {
                return !annotation.getLabel().equals("");
            }
        }, unmerged);

        // merge Annotations
        List<Annotation> annotations = new ArrayList<>();
        List<Annotation> toMerge = new ArrayList<>();
        for (Annotation annot : unmerged) {
            // if there's nothing to merge jet, put annotation in unconditionally
            if (toMerge.isEmpty()) {
                toMerge.add(annot);
                continue;
            }

            boolean sensorDatumBetweenAnnotations = false;
            if (last(toMerge).getLabel().equals(annot.getLabel())) { // same label
                Range difference = new Range(last(toMerge).getEnd(), annot.getStart());
                for (Timeseries series : datasource.getTimeseries()) {
                    if (series.getType() == TimeseriesType.SENSOR &&
                        !readSensorData(series.getId(), difference, 1).iterator().hasNext()) {
                        // there is at least one sensor datum on at least one track between the two
                        // annotations
                        toMerge.add(annot);
                        sensorDatumBetweenAnnotations = true;
                        break;
                    }
                }
            }
            if (!sensorDatumBetweenAnnotations) {
                // the two annotations do not belong together, merge all previously buffered
                // annotations and clear buffer
                Range range = new Range(toMerge.get(0).getStart(), last(toMerge).getEnd());
                annotations.add(new Annotation(range, toMerge.get(0).getLabel()));
                toMerge.clear();
                toMerge.add(annot);
            }

        }

        // the last annotation may not have been merged
        if (!toMerge.isEmpty()) {
            annotations.add(toMerge.get(0));
        }

        return annotations;
    }

    private <T> T last(List<T> list) {
        return list.get(list.size() - 1);
    }

    private <T> TrackBuffer<T> makeTrackBuffer(long trackId) {
        final RdfTimeseries timeseries = (RdfTimeseries) getTimeseries(datasource, trackId);

        // convert the strings into properties
        TrackBuffer<T> buffer = new TrackBuffer<>();
        buffer.timestampPath = RdfUtil.stringPathToPropertyPath(timeseries.getTimestampPath(), model);
        buffer.valuePath = RdfUtil.stringPathToPropertyPath(timeseries.getValuePath(), model);
        buffer.identifierPath = RdfUtil.stringPathToPropertyPath(timeseries.getIdentifierPath(), model);
        List<List<Property>> paths =
            Arrays.asList(buffer.timestampPath, buffer.valuePath, buffer.identifierPath);

        buffer.gcp = greatestCommonPrefix(paths);
        // remove gcp from the paths
        for (List<Property> path : paths) {
            path.subList(0, buffer.gcp.size()).clear();
        }
        // identifierPath is sibling or child of valuePath
        List<Property> gcp =
            greatestCommonPrefix(Arrays.asList(buffer.valuePath, buffer.identifierPath));
        buffer.identifierPath.subList(0, gcp.size()).clear();

        return buffer;
    }

    @Override
    public long getSourceId() {
        return datasource.getId();
    }

    @Override
    public double getAverageDistance(long trackId) {
        long sum = 0;
        long previous = 0;
        switch (getTimeseries(datasource, trackId).getType()) {
        case SENSOR:
            List<Datum> sensorTrack = getSensorTrackBuffer(trackId).data;
            previous = sensorTrack.get(0).timestamp;
            for (int i = 1; i < sensorTrack.size(); i++) {
                sum += sensorTrack.get(i).timestamp - previous;
                previous = sensorTrack.get(i).timestamp;
            }
            return sum / ((double) sensorTrack.size());
        case ANNOTATION:
            List<Annotation> annotationTrack = getAnnotationTrackBuffer(trackId).data;
            previous = annotationTrack.get(0).getEnd();
            for (int i = 1; i < annotationTrack.size(); i++) {
                sum += annotationTrack.get(i).getStart() - previous;
                previous = annotationTrack.get(i).getEnd();
            }
            return sum / ((double) annotationTrack.size());
        }

        return 1.0;
    }

    @Override
    public Range getCoveredRange(long trackId) {
        switch (getTimeseries(datasource, trackId).getType()) {
        case SENSOR:
            TrackBuffer<Datum> sensorTrackBuffer = getSensorTrackBuffer(trackId);
            List<Datum> sensorData = sensorTrackBuffer.data;
            if (!sensorData.isEmpty()) {
                return new Range(sensorData.get(0).timestamp, last(sensorData).timestamp + 1);
            }
            break;
        case ANNOTATION:
            TrackBuffer<Annotation> annotTrackBuffer = getAnnotationTrackBuffer(trackId);
            List<Annotation> annotations = annotTrackBuffer.data;
            if (!annotations.isEmpty()) {
                return new Range(annotations.get(0).getStart(), last(annotations).getEnd() + 1);
            }
            break;
        }
        return noDataRange;
    }
}
