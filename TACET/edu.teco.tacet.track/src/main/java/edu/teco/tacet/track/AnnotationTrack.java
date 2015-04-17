package edu.teco.tacet.track;

import java.util.Arrays;
import java.util.List;

import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.readers.memory.InMemoryReader;

public class AnnotationTrack
    extends ReadableAnnotationTrack implements EditableTrack<Annotation> {

    private InMemoryReader timeseriesBuffer;

    private Track<Annotation> sourceTrack;
    private MergeTrack<Annotation> mergeTrack;
    private AbstractTrack<Annotation> inMemTrack;

    private final State noModsState = new NoModifications();
    private final State firstInsState = new FirstInsertHappened();
    private final State delInsideOrigSource = new DeletedInsideOriginalSource();
    private State currentState = noModsState;

    public AnnotationTrack(long id, long sourceId, Reader reader,
        InMemoryReader timeseriesBuffer) {
        super(id, sourceId, reader);
        this.timeseriesBuffer = timeseriesBuffer;
        long im = timeseriesBuffer.createNewAnnotationTimeseries();
        this.inMemTrack = new ReadableAnnotationTrack(timeseriesBuffer, im);
    }

    @Override
    public Iterable<? extends Annotation> getData(Range range) {
        return currentState.getData(range);
    }

    @Override
    public boolean insertData(Annotation data) {
        return currentState.insertData(data);
    }

    @Override
    public void deleteData(Range range) {
        currentState.deleteData(range);
    }

    @Override
    public Range getCoveredRange() {
        return currentState.getCoveredRange();
    }

    @Override
    public Track<? extends Annotation> getSourceTrack() {
        return currentState.getSourceTrack();
    }

    private interface State {
        Iterable<? extends Annotation> getData(Range range);

        boolean insertData(Annotation data);

        void deleteData(Range range);

        Track<? extends Annotation> getSourceTrack();

        Range getCoveredRange();
    }

    private class NoModifications implements State {

        @Override
        public Iterable<Annotation> getData(Range range) {
            return getReader().readAnnotations(getId(), range);
        }

        @Override
        public boolean insertData(Annotation data) {
            // Initialize the new tracks, one pulling data from the original reader, the other one
            // pulling data from an InMemoryReader instance.
            sourceTrack = new ReadableAnnotationTrack(getReader(), getId());
            List<Track<Annotation>> tracksToMerge = Arrays.asList(sourceTrack, inMemTrack);
            NoConflictMerge<Annotation> mergeStrategy =
                new NoConflictMerge<Annotation>(Annotation.START_COMPARATOR);

            mergeTrack = new MergeTrack<Annotation>(tracksToMerge, sourceTrack, mergeStrategy);

            // Transition to the next state
            currentState = firstInsState;
            return currentState.insertData(data);
        }

        @Override
        public void deleteData(Range range) {
            Range sourceRange = getReader().getCoveredRange(getId());

            // In case the track in the original source is empty, do nothing.
            if (sourceRange == null)
                return;

            if (copyIfInsideOrigSource(range, sourceRange)) {
                (currentState = delInsideOrigSource).deleteData(range);
            }
        }

        @Override
        public Range getCoveredRange() {
            return getReader().getCoveredRange(getId());
        }

        @Override
        public Track<? extends Annotation> getSourceTrack() {
            return AnnotationTrack.this;
        }
    }

    private class FirstInsertHappened implements State {

        @Override
        public Iterable<? extends Annotation> getData(Range range) {
            return mergeTrack.getData(range);
        }

        @Override
        public boolean insertData(Annotation data) {
            for (Annotation annot : mergeTrack.getData(data.getRange())) {
                if (Annotation.OVERLAP_IS_EQUAL_COMPARATOR.compare(annot, data) == 0) {
                    return false;
                }
            }
            return timeseriesBuffer.insertAnnotation(inMemTrack.getId(), data);
        }

        @Override
        public void deleteData(Range range) {
            Range sourceRange = getReader().getCoveredRange(getId());
            if (sourceRange != null)
                copyIfInsideOrigSource(range, sourceRange);
            (currentState = delInsideOrigSource).deleteData(range);
        }

        @Override
        public Range getCoveredRange() {
            Range readerRange = getReader().getCoveredRange(getId());
            Range memoryRange = inMemTrack.getCoveredRange();

            return readerRange.union(memoryRange);
        }

        @Override
        public Track<? extends Annotation> getSourceTrack() {
            return sourceTrack;
        }
    }

    private class DeletedInsideOriginalSource implements State {

        @Override
        public Iterable<? extends Annotation> getData(Range range) {
            return inMemTrack.getData(range);
        }

        @Override
        public boolean insertData(Annotation data) {
            for (Annotation annot : inMemTrack.getData(data.getRange())) {
                if (Annotation.OVERLAP_IS_EQUAL_COMPARATOR.compare(annot, data) == 0) {
                    return false;
                }
            }
            return timeseriesBuffer.insertAnnotation(inMemTrack.getId(), data);
        }

        @Override
        public void deleteData(Range range) {
            timeseriesBuffer.deleteAnnotations(inMemTrack.getId(), range);
        }

        @Override
        public Range getCoveredRange() {
            return inMemTrack.getCoveredRange();
        }

        @Override
        public Track<? extends Annotation> getSourceTrack() {
            return sourceTrack;
        }

    }

    private boolean copyIfInsideOrigSource(Range toDelete, Range sourceRange) {
        if (toDelete.getStart() < sourceRange.getEnd() ||
            sourceRange.getStart() < toDelete.getEnd()) {
            for (Annotation annot : getData(sourceRange)) {
                timeseriesBuffer.insertAnnotation(inMemTrack.getId(), annot);
            }
            return true;
        }
        return false;
    }

}
