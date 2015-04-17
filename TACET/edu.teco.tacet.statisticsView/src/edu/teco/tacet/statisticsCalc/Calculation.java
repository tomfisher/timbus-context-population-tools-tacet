package edu.teco.tacet.statisticsCalc;

import org.apache.commons.math3.stat.StatUtils;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Datum;
import edu.teco.tacet.track.Track;
import static edu.teco.tacet.util.collection.IterableAdditions.toArray;

public class Calculation {

    private Calculation(Track<Datum> rdbST, Track<Annotation> anno) {}

    static class CachedAnnotationData {
        private final long id;
        private final String[] data;

        private CachedAnnotationData(long id, String[] data) {
            this.id = id;
            this.data = data;
        }

        public long getId() {
            return id;
        }
    }

    public static class StatisticsContext {
        private final long id;
        private final double[] data;

        private StatisticsContext(long id, double[] data) {
            this.id = id;
            this.data = data;
        }

        public long getId() {
            return id;
        }
    }

    /**
     * Puts the given track into a certain context. Said context has to be passed to every method in
     * this class to compute a meaningful value. The context in itself is not interesting to
     * anything outside this class.
     * 
     * @param sensorTrack the track to be contextualized
     * @return returns the newly created context
     */
    public static StatisticsContext contextualize(Track<Datum> sensorTrack) {
        // this cast is save, as we cannot write to this iterable
        Datum[] data = toArray(sensorTrack.getData(sensorTrack.getCoveredRange()));
        double[] values = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            values[i] = data[i].value;
        }
        return new StatisticsContext(sensorTrack.getId(), values);
    }

    public static CachedAnnotationData cacheAnnotationTrack(Track<Annotation> annotationTrack) {
        // this cast is save, as we cannot write to this iterable
        Annotation[] data = 
            toArray(annotationTrack.getData(annotationTrack.getCoveredRange()));
        String[] labels = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            labels[i] = data[i].getLabel();
        }
        return new CachedAnnotationData(annotationTrack.getId(), labels);
    }

    public static double mean(StatisticsContext sensorData) {
        return StatUtils.mean(sensorData.data);
    }

    public static double min(StatisticsContext sensorData) {
        return StatUtils.min(sensorData.data);
    }

    public static double max(StatisticsContext sensorData) {
        return StatUtils.max(sensorData.data);
    }

    public static double standardDeviation(StatisticsContext sensorData) {
        return Math.sqrt(StatUtils.variance(sensorData.data));
    }

    public static double median(StatisticsContext sensorData) {
        return StatUtils.percentile(sensorData.data, 50);
    }

    public static double[] mode(StatisticsContext sensorData) {
        return StatUtils.mode(sensorData.data);
    }

    public static double percentageIsAnnotation(
        CachedAnnotationData annotationData, StatisticsContext sensorData) {
        return annotationData.data.length / sensorData.data.length;
    }

}
