package edu.teco.tacet.track;

import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import edu.teco.tacet.test.TestHelper;

public class UniqueDataDecoratorTest {

    UniqueDataDecorator<Annotation> utd;

    @Test
    public void test() {
        Track<Annotation> trackToWrap = TestHelper.arrayToAnnotationTrack(new Annotation[] {
            new Annotation(new Range(0, 30), "rennen"),
            new Annotation(new Range(40, 80), "gehen"),
            new Annotation(new Range(100, 120), "laufen"),
            new Annotation(new Range(150, 300), "laufen"),
            new Annotation(new Range(310, 350), "gehen"),
        });

        utd = new UniqueDataDecorator<>(trackToWrap, Annotation.LABEL_COMPARATOR);

        // This is just to pull the data through once.
        utd.force();

        Set<Annotation> uniques = utd.getUniqueData();

        Set<Annotation> expected = new TreeSet<>(Annotation.LABEL_COMPARATOR);
        expected.add(new Annotation(new Range(0, 1), "rennen"));
        expected.add(new Annotation(new Range(0, 1), "laufen"));
        expected.add(new Annotation(new Range(0, 1), "gehen"));

        assertTrue(uniques.size() == expected.size() && uniques.containsAll(expected));
    }
}
