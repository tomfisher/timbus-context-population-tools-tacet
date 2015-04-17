package edu.teco.tacet.track;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.teco.tacet.track.NoConflictMerge;

public class AnnotationNoConflictMergeTest {

    NoConflictMerge<Annotation> merger;

    @Before
    public void setup() {
        merger = new NoConflictMerge<>(Annotation.START_COMPARATOR);
    }

    List<Iterable<? extends Annotation>> arraysToInput(Long[]... arrayInput) {
        List<Iterable<? extends Annotation>> ret = new ArrayList<>(arrayInput.length);
        for (Long[] array : arrayInput) {
            List<Annotation> track = new ArrayList<>(array.length);
            for (Long point : array) {
                track.add(Annotation.createDummy(Range.point(point)));
            }
            ret.add(track);
        }
        return ret;
    }

    Long[] outputToArray(Iterable<? extends Annotation> data) {
        List<Long> ret = new ArrayList<>();
        for (Annotation a : data)
            ret.add(a.getStart());
        return ret.toArray(new Long[] {});
    }

    @Test
    public void sameLength() {
        Long[][] input = new Long[][] { new Long[] { 0l, 1l, 2l, 3l } };
        Long[] expected = new Long[] { 0l, 1l, 2l, 3l };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));

        input = new Long[][] {
            new Long[] { 0l, 2l },
            new Long[] { 1l, 3l } };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));

        input = new Long[][] {
            new Long[] { 0l, 5l },
            new Long[] { 1l, 2l },
            new Long[] { 3l, 4l } };
        expected = new Long[] { 0l, 1l, 2l, 3l, 4l, 5l };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));
    }

    @Test
    public void differingLength() {
        Long[][] input = new Long[][] {
            new Long[] { 0l, 2l, 5l, 10l },
            new Long[] { 1l, 3l } };
        Long[] expected = new Long[] { 0l, 1l, 2l, 3l, 5l, 10l };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));

        input = new Long[][] {
            new Long[] { 0l, 4l, 5l },
            new Long[] { 1l, 2l, 7l, 12l },
            new Long[] { 3l } };
        expected = new Long[] { 0l, 1l, 2l, 3l, 4l, 5l, 7l, 12l };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));
    }

    @Test
    public void edgeCases() {
        Long[][] input = new Long[][] { new Long[] { 0l } };
        Long[] expected = new Long[] { 0l };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));

        input = new Long[][] { new Long[] {} };
        expected = new Long[] {};
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));
    }

    @Test
    public void conflict() {
        Long[][] input = new Long[][] {
            new Long[] { 0l, 1l },
            new Long[] { 1l, 2l },
            new Long[] { 3l, 4l },
        };
        Long[] expected = new Long[] { 0l, 1l, 1l, 2l, 3l, 4l };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));

        input = new Long[][] {
            new Long[] { 0l, 1l },
            new Long[] { 0l, 1l },
            new Long[] { 0l, 1l },
        };
        expected = new Long[] { 0l, 0l, 0l, 1l, 1l, 1l };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));
    }

    @Test
    public void oneOfMoreItersIsEmpty() {
        // one of more tracks is empty
        Long[][] input = new Long[][] {
            new Long[] {},
            new Long[] { 0l, 1l }
        };
        Long[] expected = new Long[] { 0l, 1l };
        assertArrayEquals(expected, outputToArray(merger.merge(arraysToInput(input))));
    }
}
