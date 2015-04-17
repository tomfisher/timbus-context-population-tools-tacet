package edu.teco.tacet.track;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrackDataAnalyser extends DefaultTrackDecorator<Datum> {

    private static class Tuple<U, V> {
        public final U first;
        public final V second;

        public Tuple(U first, V second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }
    }

    static interface Analysis {
        void analyse(Datum datum);
    }

    private static class AnalysisTupleComparator implements Comparator<Tuple<Range, Analysis>> {
        @Override
        public int compare(Tuple<Range, Analysis> t1, Tuple<Range, Analysis> t2) {
            return Range.START_COMPARATOR.compare(t1.first, t2.first);
        }
    }

    private List<Tuple<Range, Analysis>> analyses = new ArrayList<>();
    private AnalysisTupleComparator comparator = new AnalysisTupleComparator();

    public TrackDataAnalyser(Track<Datum> trackToWrap) {
        super(trackToWrap);
    }

    public void register(Analysis analysis) {
        register(analysis, wrappedTrack.getCoveredRange());
    }

    public void register(Analysis analysis, Range range) {
        Tuple<Range, Analysis> toInsert = new Tuple<>(range, analysis);

        if (analyses.isEmpty()) {
            analyses.add(toInsert);
            return;
        }

        int start = 0;
        int end = analyses.size() - 1;

        while (true) {
            int mid = (end + start) / 2;
            int compResult = comparator.compare(toInsert, analyses.get(mid));

            if (compResult < 0) {
                end = mid;
            } else if (compResult > 0) {
                start = mid + 1;
            }

            if (start == end) {
                analyses.add(compResult < 0 ? start : start + 1, toInsert);
                break;
            }
        }
    }

    public Iterable<Analysis> getAnalyses() {
        List<Analysis> ret = new ArrayList<>();
        for (Tuple<Range, Analysis> analysis : analyses) {
            ret.add(analysis.second);
        }
        return ret;
    }

    @Override
    protected Datum handleDatum(Datum datum) {
        for (Tuple<Range, Analysis> analysis : analyses) {
            if (analysis.first.contains(datum.timestamp)) {
                analysis.second.analyse(datum);
            } else if (datum.timestamp >= analysis.first.getEnd()) {
                break;
            }
        }
        return datum;
    }

    public static class MinimumAnalysis implements Analysis {
        private double minimum = Double.MAX_VALUE;

        public double getMinimum() {
            return minimum;
        }

        @Override
        public void analyse(Datum datum) {
            if (minimum > datum.value) {
                minimum = datum.value;
            }
        }
    }

    public static class MaximumAnalysis implements Analysis {
        private double maximum = Double.MIN_VALUE;

        public double getMaximum() {
            return maximum;
        }

        @Override
        public void analyse(Datum datum) {
            if (maximum < datum.value) {
                maximum = datum.value;
            }
        }
    }

    public static class OutlierAnalysis implements Analysis {

        private Double lower, upper;

        private List<Datum> lowerOutlier, upperOutlier;

        public OutlierAnalysis(Double lower, Double upper) {
            this.lower = lower;
            this.upper = upper;
            lowerOutlier = new ArrayList<>();
            upperOutlier = new ArrayList<>();
        }

        public Iterable<Datum> getLowerOutliers() {
            return lowerOutlier;
        }

        public Iterable<Datum> getUpperOutliers() {
            return upperOutlier;
        }

        @Override
        public void analyse(Datum datum) {
            if (lower != null && datum.value < lower) {
                lowerOutlier.add(datum);
            }
            if (upper != null && datum.value > upper) {
                upperOutlier.add(datum);
            }
        }
    }
}
