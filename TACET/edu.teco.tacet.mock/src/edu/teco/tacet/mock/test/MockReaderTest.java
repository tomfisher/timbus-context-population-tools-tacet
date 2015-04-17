package edu.teco.tacet.mock.test;

import org.junit.Test;

import edu.teco.tacet.mock.MockReader;
import edu.teco.tacet.readers.Reader;
import edu.teco.tacet.track.Range;
import edu.teco.tacet.util.collection.IterableAdditions;

public class MockReaderTest {
    @Test
    public void test1() {
        Reader reader = new MockReader(1, new Range(0, 10000));
        for (int i = 0; i < 10; i++) {
            System.out.println(IterableAdditions.toString(
                reader.readAnnotations(1, new Range(i * 1000, (i + 1) * 1000))));
        }
    }
    
    @Test
    public void disappearingAnnotations() {
        Reader reader = new MockReader(1, new Range(0, 10000));
        System.out.println(IterableAdditions.toString(
            reader.readAnnotations(1, new Range(615, 927))));
    }
}
