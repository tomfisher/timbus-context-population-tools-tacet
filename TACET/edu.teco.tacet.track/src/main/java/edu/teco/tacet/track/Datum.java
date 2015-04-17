package edu.teco.tacet.track;

import java.util.Comparator;

public class Datum {
    public final long timestamp;
    public final double value;

    public Datum(long timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + timestamp + ", " + value + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Datum) {
            Datum oDatum = (Datum) other;
            return timestamp == oDatum.timestamp && value == oDatum.value;
        }
        return false;
    }

    public static class TimestampComparator implements Comparator<Datum> {
        @Override
        public int compare(Datum d1, Datum d2) {
            return Long.compare(d1.timestamp, d2.timestamp);
        }
    }

    public static final Comparator<Datum> TIMESTAMP_COMPARATOR = new TimestampComparator();
}
