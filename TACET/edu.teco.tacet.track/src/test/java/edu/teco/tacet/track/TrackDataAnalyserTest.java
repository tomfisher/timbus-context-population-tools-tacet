package edu.teco.tacet.track;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.teco.tacet.test.TestHelper;
import edu.teco.tacet.track.TrackDataAnalyser.Analysis;
import edu.teco.tacet.track.TrackDataAnalyser.MaximumAnalysis;
import edu.teco.tacet.track.TrackDataAnalyser.MinimumAnalysis;
import edu.teco.tacet.track.TrackDataAnalyser.OutlierAnalysis;

public class TrackDataAnalyserTest {

    private TrackDataAnalyser createAnalyser(Datum[] data) {
        return new TrackDataAnalyser(TestHelper.arrayToSensorTrack(data));
    }

    private static class DummyAnalysis implements Analysis {
        public final int id;

        public DummyAnalysis(int id) {
            this.id = id;
        }

        @Override
        public void analyse(Datum datum) {}

        public String toString() {
            return "DummyAnalysis(" + id + ")";
        }
    }

    @Test
    public void registerTest() {
        TrackDataAnalyser analyser = new TrackDataAnalyser(null);

        // Using DummyAnalysis makes it easier to compare the results
        Analysis[] analyses = new Analysis[] {
            new DummyAnalysis(0),
            new DummyAnalysis(1),
            new DummyAnalysis(2),
            new DummyAnalysis(3),
            new DummyAnalysis(4),
        };

        analyser.register(analyses[0], new Range(3, 10));
        analyser.register(analyses[1], new Range(1, 10));
        analyser.register(analyses[2], new Range(5, 10));
        analyser.register(analyses[3], new Range(-2, 10));
        analyser.register(analyses[4], new Range(9, 10));

        List<Analysis> result = new ArrayList<>(5);
        for (Analysis ana : analyser.getAnalyses()) {
            result.add(ana);
        }

        assertArrayEquals(new Analysis[] {
            analyses[3], analyses[1], analyses[0], analyses[2], analyses[4]
        }, result.toArray());
    }

    @Test
    public void minAndMaxTest() {
        TrackDataAnalyser analyser = createAnalyser(new Datum[] {
            new Datum(0, 12.3),
            new Datum(1, 14.3),
            new Datum(2, 0),
            new Datum(3, -12),
            new Datum(4, 14.3),
        });

        MinimumAnalysis minAnalysis = new MinimumAnalysis();
        MaximumAnalysis maxAnalysis = new MaximumAnalysis();
        analyser.register(minAnalysis);
        analyser.register(maxAnalysis);
        analyser.force();
        // I have no idea if this is a reasonable delta
        assertEquals(-12, minAnalysis.getMinimum(), 10e-30);
        assertEquals(14.3, maxAnalysis.getMaximum(), 10e-30);

        minAnalysis = new MinimumAnalysis();
        maxAnalysis = new MaximumAnalysis();
        analyser.register(minAnalysis, new Range(0, 3));
        analyser.register(maxAnalysis, new Range(2, 4));
        analyser.force();
        assertEquals(0, minAnalysis.getMinimum(), 10e-30);
        assertEquals(0, maxAnalysis.getMaximum(), 10e-30);
    }

    @Test
    public void outlierTest() {
        Datum[] data = new Datum[] {
            new Datum(0, 1), new Datum(1, 3), new Datum(2, 4), new Datum(3, 4),
            new Datum(4, 5), new Datum(5, 3), new Datum(6, 2), new Datum(7, 9),
            new Datum(8, 7), new Datum(9, 6), new Datum(10, 3), new Datum(11, 4),
            new Datum(12, 2), new Datum(13, 234), new Datum(14, 6), new Datum(15, 732),
            new Datum(16, 4), new Datum(17, 8), new Datum(18, -11), new Datum(19, -12),
            new Datum(20, -234), new Datum(21, 2), new Datum(22, 4), new Datum(23, 6),
            new Datum(24, 2), new Datum(25, 5),
        };
        TrackDataAnalyser analyser = createAnalyser(data);
        OutlierAnalysis outlierAna = new OutlierAnalysis(-10.0, 10.0);
        analyser.register(outlierAna);
        analyser.force();

        assertArrayEquals(new Datum[] { data[18], data[19], data[20] },
            TestHelper.iterableToArray((outlierAna.getLowerOutliers())));
        assertArrayEquals(new Datum[] { data[13], data[15] },
            TestHelper.iterableToArray((outlierAna.getUpperOutliers())));
    }
}
