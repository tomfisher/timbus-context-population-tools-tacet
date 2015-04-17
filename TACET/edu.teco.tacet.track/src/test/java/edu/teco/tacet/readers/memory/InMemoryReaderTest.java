package edu.teco.tacet.readers.memory;

import static edu.teco.tacet.test.TestHelper.iterableToArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.teco.tacet.readers.memory.InMemoryReader;
import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.track.TrackIdGenerator;

public class InMemoryReaderTest {

    InMemoryReader reader;

    private <T> List<T> fromIterable(Iterable<T> iter) {
        List<T> ret = new ArrayList<T>();
        for (T t : iter) {
            ret.add(t);
        }
        return ret;
    }

    @Before
    public void setUp() {
        reader = new InMemoryReader(0L, new TrackIdGenerator() {
            long id = 0;

            @Override
            public long generateTrackId() {
                return id++;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertAnnotationTest() {
        long id = reader.createNewAnnotationTimeseries();

        Annotation[] annots = {
            Annotation.createDummy(0, 100),
            Annotation.createDummy(200, 300),
            Annotation.createDummy(50, 150),
            Annotation.createDummy(100, 130),
            Annotation.createDummy(50, 250),
            Annotation.createDummy(100, 150),
            Annotation.createDummy(100, 200),
            Annotation.createDummy(200, 250),
            Annotation.createDummy(110, 190)
        };

        for (int i = 0; i < annots.length; i++) {
            reader.insertAnnotation(id, annots[i]);
        }

        Annotation[] result =
            fromIterable(reader.readAnnotations(id, new Range(0, 300))).toArray(new Annotation[0]);
        assertArrayEquals(new Annotation[] { annots[0], annots[3], annots[1] }, result);

        reader.insertAnnotation(42, Annotation.createDummy(Range.point(3)));
    }

    @Test
    public void readAnnotationsTest() {
        long id = reader.createNewAnnotationTimeseries();

        Annotation[] annots = {
            Annotation.createDummy(0, 100),
            Annotation.createDummy(200, 300),
            Annotation.createDummy(330, 370),
            Annotation.createDummy(370, 420),
            Annotation.createDummy(450, 600)
        };

        assertArrayEquals(new Annotation[0],
            iterableToArray(reader.readAnnotations(id, new Range(0, 100))));

        for (Annotation a : annots) {
            reader.insertAnnotation(id, a);
        }

        assertArrayEquals(annots, iterableToArray(reader.readAnnotations(id, new Range(0, 600))));

        assertArrayEquals(new Annotation[] {
            annots[0], annots[1], annots[2], annots[3]
        }, iterableToArray(reader.readAnnotations(id, new Range(50, 450))));

        assertArrayEquals(new Annotation[] {
            annots[0], annots[1], annots[2]
        }, iterableToArray(reader.readAnnotations(id, new Range(30, 370))));

        assertArrayEquals(new Annotation[0],
            iterableToArray(reader.readAnnotations(id, new Range(600, 1000))));

        assertArrayEquals(new Annotation[] { annots[0] },
            iterableToArray(reader.readAnnotations(id, new Range(10, 50))));
    }

    @Test
    public void deleteAnnotationsTest() {
        long id = reader.createNewAnnotationTimeseries();

        Annotation[] annots = {
            Annotation.createDummy(0, 100),
            Annotation.createDummy(200, 300),
            Annotation.createDummy(330, 370),
            Annotation.createDummy(370, 420),
            Annotation.createDummy(450, 600)
        };

        reader.deleteAnnotations(id, Range.point(0));
        assertArrayEquals(new Annotation[0],
            iterableToArray(reader.readAnnotations(id, new Range(0, 100))));

        for (Annotation a : annots)
            reader.insertAnnotation(id, a);

        reader.deleteAnnotations(id, new Range(0, 600));
        assertArrayEquals(new Annotation[0],
            iterableToArray(reader.readAnnotations(id, new Range(0, 600))));

        for (Annotation a : annots)
            reader.insertAnnotation(id, a);

        reader.deleteAnnotations(id, new Range(50, 340));
        assertArrayEquals(new Annotation[] {
            annots[3], annots[4]
        }, iterableToArray(reader.readAnnotations(id, new Range(0, 600))));

        for (Annotation a : new Annotation[] { annots[0], annots[1], annots[2] })
            reader.insertAnnotation(id, a);

        reader.deleteAnnotations(id, new Range(100, 200));
        assertArrayEquals(annots,
            iterableToArray(reader.readAnnotations(id, new Range(0, 600))));

        reader.deleteAnnotations(id, Range.point(200));
        reader.deleteAnnotations(id, Range.point(370));
        assertArrayEquals(new Annotation[] {
            annots[0], annots[2], annots[4]
        }, iterableToArray(reader.readAnnotations(id, new Range(0, 600))));
    }

    @Test
    public void startAndEndTimestampTest() {
        Annotation a1 = Annotation.createDummy(0, 100);
        Annotation a2 = Annotation.createDummy(200, 300);

        long id = reader.createNewAnnotationTimeseries();
        reader.insertAnnotation(id, a1);
        assertEquals(new Range(0L, 100L), reader.getCoveredRange(id));
        reader.insertAnnotation(id, a2);
        assertEquals(new Range(0L, 300L), reader.getCoveredRange(id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void coveredRangeWithNonExistingTimeseries() {
        reader.getCoveredRange(42);
    }

    public void coveredRangeWithEmptyTimeseries() {
        long id = reader.createNewAnnotationTimeseries();
        assertEquals(null, reader.getCoveredRange(id));
    }

    @Test
    public void readSensorDataWithResolution() {
        long id = reader.createNewSensorTimeseries();
        Datum[] data = new Datum[10];
        for (long ts = 0; ts < 10; ts++) {
            data[(int) ts] = new Datum(ts, Math.random() * 300);
            reader.insertSensorDatum(id, data[(int) ts]);
        }

        assertArrayEquals(data, iterableToArray(reader.readSensorData(id, new Range(0, 10), 1)));

        assertArrayEquals(new Datum[] { data[0], data[2], data[4], data[6], data[8] },
            iterableToArray(reader.readSensorData(id, new Range(0, 10), 2)));

        assertArrayEquals(new Datum[] { data[0], data[3], data[6], data[9] },
            iterableToArray(reader.readSensorData(id, new Range(0, 10), 3)));

        assertArrayEquals(new Datum[] { data[0], data[7] },
            iterableToArray(reader.readSensorData(id, new Range(0, 10), 7)));
    }
}
