package edu.teco.tacet.util.test;

import static edu.teco.tacet.util.collection.ListAdditions.binaryFind;
import static edu.teco.tacet.util.collection.ListAdditions.binaryFindLower;
import static edu.teco.tacet.util.collection.ListAdditions.binaryFindUpper;
import static edu.teco.tacet.util.collection.ListAdditions.sort;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import edu.teco.tacet.util.function.F1;

public class ListAdditionsTest {
    @Test
    public void binaryFindTest() {
        // sorted by the second character
        List<String> strings = Arrays.asList(new String[] { "hello", "there", "you", "over" });

        // compare two characters
        Comparator<Character> comp = new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return o1.compareTo(o2);
            }
        };

        // extract the second character
        F1<String, Character> extractor = new F1<String, Character>() {
            @Override
            public Character apply(String string) {
                return string.charAt(1);
            }
        };

        assertEquals(binaryFind(strings, 'h', extractor, comp), 1);
        assertEquals(binaryFind(strings, 'y', extractor, comp), 4);
        assertEquals(binaryFind(strings, 'g', extractor, comp), 4);
    }

    @Test
    public void binaryFindComparableTest() {
        // sorted by the second character
        List<String> strings = Arrays.asList(new String[] { "hello", "there", "you", "over" });
        // extract the second character
        F1<String, Character> extractor = new F1<String, Character>() {
            @Override
            public Character apply(String string) {
                return string.charAt(1);
            }
        };

        assertEquals(binaryFind(strings, 'h', extractor), 1);
        assertEquals(binaryFind(strings, 'y', extractor), 4);
        assertEquals(binaryFind(strings, 'g', extractor), 4);
        assertEquals(binaryFind(strings, 'a', extractor), 4);
    }

    @Test
    public void binaryFindUpperTest() {
        // sorted by the second character
        List<String> strings = Arrays.asList(new String[] { "hello", "there", "you", "over" });
        // extract the second character
        F1<String, Character> extractor = new F1<String, Character>() {
            @Override
            public Character apply(String string) {
                return string.charAt(1);
            }
        };

        assertEquals(binaryFindUpper(strings, 'h', extractor), 1);
        assertEquals(binaryFindUpper(strings, 'y', extractor), 4);
        assertEquals(binaryFindUpper(strings, 'g', extractor), 1);
        assertEquals(binaryFindUpper(strings, 'a', extractor), 0);
    }

    @Test
    public void binaryFindLowerTest() {
        // sorted by the second character
        List<String> strings = Arrays.asList(new String[] { "hello", "there", "you", "over" });
        // extract the second character
        F1<String, Character> extractor = new F1<String, Character>() {
            @Override
            public Character apply(String string) {
                return string.charAt(1);
            }
        };

        assertEquals(binaryFindLower(strings, 'h', extractor), 1);
        assertEquals(binaryFindLower(strings, 'y', extractor), 3);
        assertEquals(binaryFindLower(strings, 'g', extractor), 0);
        assertEquals(binaryFindLower(strings, 'a', extractor), -1);
    }

    @Test
    public void sortComparableTest() {
        // sorted by the second character
        List<String> strings = Arrays.asList(new String[] { "hello", "there", "you", "over" });
        // sort by last character
        sort(strings, new F1<String, Character>() {
            @Override
            public Character apply(String t) {
                return t.charAt(t.length() - 1);
            }
        });

        assertArrayEquals(new String[] { "there", "hello", "over", "you" }, strings.toArray());
    }

}
