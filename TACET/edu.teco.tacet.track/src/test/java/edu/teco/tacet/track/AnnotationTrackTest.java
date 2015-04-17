package edu.teco.tacet.track;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import edu.teco.tacet.readers.memory.InMemoryReader;
import static edu.teco.tacet.test.TestHelper.*;

public class AnnotationTrackTest {
    AnnotationTrack aTrack;

    InMemoryReader fakeSource = new InMemoryReader(42, new TrackIdGenerator() {
        long id = 0;

        @Override
        public long generateTrackId() {
            return id++;
        }
    });

    Annotation[] annots = new Annotation[] {
        Annotation.createDummy(0, 30),
        Annotation.createDummy(30, 50),
        Annotation.createDummy(55, 110),
        Annotation.createDummy(200, 1000)
    };

    @Before
    public void setup() {
        long id = fakeSource.createNewAnnotationTimeseries();
        aTrack = new AnnotationTrack(id, 42, fakeSource, fakeSource);
        aTrack.getMetaData().setName("test");

        for (Annotation annotation : annots)
            fakeSource.insertAnnotation(id, annotation);
    }

    @Test
    public void deleteThenInsert(){
        Annotation annot0_70 = Annotation.createDummy(0, 70);
        aTrack.insertData(annot0_70);
        assertArrayEquals(annots, iterableToArray(aTrack.getData(new Range(0, 1001))));

        aTrack.insertData(annot0_70);
        assertArrayEquals(annots, iterableToArray(aTrack.getData(new Range(0, 1001))));

        aTrack.deleteData(new Range(0,70));
        assertArrayEquals(new Annotation[] { annots[3] }, iterableToArray(aTrack.getData(new Range(0, 1001))));

        aTrack.insertData(annot0_70);
        assertArrayEquals(new Annotation[] {annot0_70, annots[3]}, iterableToArray(aTrack.getData(new Range(0, 1001))));
    }

    @Test
    public void noInsNoDelTest() {
        assertArrayEquals(annots, iterableToArray(aTrack.getData(new Range(0, 1000))));
    }

    @Test
    public void insNoDelTest() {
        Annotation annotation = Annotation.createDummy(1100, 1150);
        aTrack.insertData(annotation);
        assertArrayEquals(new Annotation[] {
            annots[0], annots[1], annots[2], annots[3], annotation
        }, iterableToArray(aTrack.getData(new Range(0, 1150))));
    }

    @Test
    public void delNoInsert() {
        aTrack.deleteData(new Range(0,70));
        assertArrayEquals(new Annotation[] {
            annots[3]
        }, iterableToArray(aTrack.getData(new Range(0, 1000))));
    }



    @Test
    public void delInsideSourceTest() {
        Annotation annotation = Annotation.createDummy(1100, 1150);
        aTrack.insertData(annotation);
        aTrack.deleteData(new Range(15, 55));
        assertArrayEquals(new Annotation[] {
            annots[2], annots[3], annotation
        }, iterableToArray(aTrack.getData(new Range(0, 1200))));
    }

    @Test
    public void delInsideAndOutsideSourceTest() {
        Annotation annotation = Annotation.createDummy(1100, 1200);
        aTrack.insertData(annotation);
        aTrack.deleteData(new Range(800, 1200));
        assertArrayEquals(new Annotation[] {
            annots[0], annots[1], annots[2]
        }, iterableToArray(aTrack.getData(new Range(0, 1200))));
    }
}
