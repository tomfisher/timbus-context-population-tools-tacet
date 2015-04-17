package edu.teco.tacet.track;

public interface Track<T> {

    MetaData getMetaData();

    long getId();

    long getSourceId();

    Iterable<? extends T> getData(Range range);

    /**
     * Return a range {@code r} containing every datum that can be returned by
     * {@link #getData(Range)}. Note: it is possible for this range to not contain any data, in case
     * this track does not contain any data. In this case {@code r.getStart()} and
     * {@code r.getEnd()} return arbitrary values.
     *
     * @return {@code Range r} with {@code r = new Range(min, max + 1)} where {@code min} is the
     *         location of the smallest datum and {@code max} the location of the largest datum.
     */
    Range getCoveredRange();

    class MetaData {

        private String name;
        private double averageDistance;
        private Range startRange;

        public MetaData(String name, long averageDistance, Range startRange) {
            super();
            this.name = name;
            this.averageDistance = averageDistance;
            this.startRange = startRange;
        }

        public MetaData() {
            this("", -1L, null);
        }

        public MetaData setName(String name) {
            this.name = name;
            return this;
        }

        public MetaData setAverageDistance(double d) {
            this.averageDistance = d;
            return this;
        }

        public MetaData setStartRange(Range startRange) {
            this.startRange = startRange;
            return this;
        }

        public String getName() {
            return name;
        }

        public double getAverageDistance() {
            return averageDistance;
        }

        public Range getStartRange() {
            return startRange;
        }
    }
}
