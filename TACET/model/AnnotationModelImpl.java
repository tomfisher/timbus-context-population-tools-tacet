/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
 */

package squirrel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import squirrel.model.io.Reader;
import squirrel.util.Range;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;

/**
 * This class represents an {@code AnnotationModelimpl}. It manages the
 * annotations.
 * @author Olivier
 *
 */
public class AnnotationModelImpl implements AnnotationModel, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2487986448132952443L;

    // Track //annotation auf den track
    private ArrayList<TreeSet<Annotation>> annotations;
    private transient Reader reader;
    private Range inserted;

    /**
     * Constructs a new AnnotationModelImpl.
     *
     * @param reader reader to get data from the source file
     * @param range range to read from the source file
     * @param numberOfTracks number of annotation tracks
     */
    public AnnotationModelImpl(Reader reader, Range range, int numberOfTracks) {
        annotations = new ArrayList<TreeSet<Annotation>>(numberOfTracks);
        for (int i = 0; i < numberOfTracks; i++) {
            annotations.add(i, new TreeSet<Annotation>(new myComp()));
        }
        this.reader = reader;
        inserted = range;
        initializeAnnotations((List<Annotation[]>) reader.readAnnotations(range));
    }

    /**
     * Constructs a new AnnotationModelImpl. <br>
     * The given annotations have to be in the same format like a Reader returns
     * it. In Annotation[] the index represents track. An annotation in such an
     * array has exactly one timestamp, so start and end time are equal.
     * Consequently, the higher the index of the list, the higher the timestamp
     * of the annotations in the corresponding Annotation[].
     *
     * @param annos existing annotations as they come from a Reader
     * @param numberOfTracks number of annotation tracks
     */
    public AnnotationModelImpl(List<Annotation[]> annos, int numberOfTracks) {
        annotations = new ArrayList<TreeSet<Annotation>>(numberOfTracks);
        for (int i = 0; i < numberOfTracks; i++) {
            annotations.add(i, new TreeSet<Annotation>(new myComp()));
        }
        initializeAnnotations(annos);
    }

    /**
     * Only for testing!
     *
     * @param annos
     * @param range
     * @param numberOfTracks
     */
    public AnnotationModelImpl(List<Annotation[]> annos, Range range, int numberOfTracks) {
        annotations = new ArrayList<TreeSet<Annotation>>(numberOfTracks);
        for (int i = 0; i < numberOfTracks; i++) {
            annotations.add(i, new TreeSet<Annotation>(new myComp()));
        }
        this.inserted = range;
        initializeAnnotations(annos);
    }

    /**
     * Keep annotations sorted by start timestamp.
     *
     * @author Olivier
     *
     */
    private class myComp implements Comparator<Annotation>, Serializable {

        /**
         *
         */
        private static final long serialVersionUID = -8174499191696238044L;

        @Override
        public int compare(Annotation a1, Annotation a2) {
            return Range.START_COMPARATOR.compare(a1.getRange(), a2.getRange());
        }

    }

    /**
     * Converts the annotations in a more compact way, by merging annotations.
     *
     * @param annos annotations to convert.
     */
    private void initializeAnnotations(List<Annotation[]> annos) {
        if (annos.size() == 0)
            return;
        Annotation[] temp = new Annotation[annos.get(0).length];

        int count = 0;
        for (Annotation[] a : annos) {
            for (int i = 0; i < a.length; i++) {
                if (temp[i] == null) {
                    temp[i] = a[i];
                } else if (a[i] == null) {
                    if (temp[i] != null) {
                        // end of annotation temp: add temp to annotations and
                        // reset temp
                        temp[i].setTrack(i);
                        if (!annotations.get(i).isEmpty() &&
                            annotations.get(i).last().isInRange(temp[i].getStart())) {
                            // merge last existing annotation with temp (should
                            // be the first annotation)
                            annotations.get(i).last().setEnd(temp[i].getEnd());
                        } else {
                            annotations.get(i).add(temp[i]);
                        }
                        temp[i] = null;
                    }
                } else {
                    if (temp[i].toValue().equals((a[i]).toValue())) {
                        // merge two sequent annotations with same annotations
                        temp[i].setNewRange(new Range(temp[i].getStart(), a[i].getEnd()));
                    } else {
                        // different annotation: add temp to annotations and set
                        // new annotation to temp
                        temp[i].setTrack(i);
                        if (!annotations.get(i).isEmpty() &&
                            annotations.get(i).last().isInRange(temp[i].getStart())) {
                            // merge last existing annotation with temp (should
                            // be the first annotation)
                            annotations.get(i).last().setEnd(temp[i].getEnd());
                        } else {
                            annotations.get(i).add(temp[i]);
                        }
                        temp[i] = a[i];
                    }
                }
                // if last element
                if (count >= annos.size() - 1 && temp[i] != null) {
                    temp[i].setTrack(i);

                    if (!annotations.get(i).isEmpty() &&
                        annotations.get(i).first().isInRange(temp[i].getEnd() - 1)) {
                        // merge first existing annotation with temp (should be
                        // the last annotation)
                        annotations.get(i).last().setStart(temp[i].getStart());
                    } else {
                        annotations.get(i).add(temp[i]);
                    }
                }
            }
            count++;
        }
    }

    /**
     * Returns all annotations, which are completely or partly in the
     * {@code range} from the given {@code track}.
     * @param range of annotations
     * @param track track of annotations
     * @return all annotations in the {@code range} from the given {@code track}
     */
    @Override
    public Iterable<Annotation> getAnnotations(Range range, int track) {
        if (range.getStart() < inserted.getStart()) {
            Range rt = new Range(range.getStart(), inserted.getStart());
            initializeAnnotations((List<Annotation[]>) reader.readAnnotations(rt));
            inserted = new Range(rt.getStart(), inserted.getEnd());
        }

        if (range.getEnd() > inserted.getEnd()) {
            Range rt = new Range(inserted.getEnd(), range.getEnd() + 131072);
            if (rt.getEnd() > reader.endTimeStamp())
                rt = new Range(rt.getStart(), reader.endTimeStamp());
            initializeAnnotations((List<Annotation[]>) reader.readAnnotations(rt));
            inserted = new Range(inserted.getStart(), rt.getEnd());
        }

        List<Annotation> output = new LinkedList<Annotation>();
        for (Annotation a : annotations.get(track)) {
            if (range.getStart() < a.getEnd() && range.getEnd() > a.getStart()) {
                output.add(a);
            }
        }


        return output;
    }

    /**
     * Deletes all annotations, which are completely or partly in the
     * {@code range} from the given {@code track}.
     * @param range of annotations to delete
     * @param track track of annotations to delete
     */
    @Override
    public void deleteAnnotations(Range range, int track) {
        List<Annotation> removeAnnos = new LinkedList<Annotation>();
        for (Annotation a : annotations.get(track)) {
            if (range.getStart() < a.getEnd() && range.getEnd() > a.getStart()) {
                removeAnnos.add(a);
            }
        }
        for (Annotation a : removeAnnos) {
            annotations.get(track).remove(a);
        }
    }

    /**
     * Inserts an annotation into a track, if there is free space in its range.
     * @param anno annotation to insert
     * @param track track number where to insert annotation
     */
    @Override
    public boolean insertAnnotation(Annotation anno, int track) {
        if (canInsertAt(anno.getRange(), track)) {
            annotations.get(track).add(anno);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns an annotation with the given timestamp on the given track.
     * Returns {@code null} if there is no annotation.
     * @param timestamp timestamp of annotation
     * @param track track number where to find annotation
     * @return annotation on the given track with given timestamp
     */
    @Override
    public Annotation getAnnotation(long timestamp, int track) {
        Range range = new Range(timestamp, timestamp + 1);
        if (range.getEnd() < inserted.getStart()) {
            Range rt = new Range(range.getStart(), inserted.getStart());
            initializeAnnotations((List<Annotation[]>) reader.readAnnotations(rt));
            inserted = new Range(rt.getStart(), inserted.getEnd());
        }

        if (range.getStart() > inserted.getEnd()) {
            Range rt = new Range(inserted.getEnd(), range.getEnd() + 131072);
            if (rt.getEnd() > reader.endTimeStamp())
                rt = new Range(rt.getStart(), reader.endTimeStamp());
            initializeAnnotations((List<Annotation[]>) reader.readAnnotations(rt));
            inserted = new Range(inserted.getStart(), rt.getEnd());
        }

        Annotation out = null;
        boolean found = false;
        Iterator<Annotation> it = annotations.get(track).iterator();
        while (!found && it.hasNext()) {
            Annotation tmp = it.next();
            if (tmp.isInRange(timestamp)) {
                out = tmp;
                found = true;
            }
        }
        return out;
    }

    /**
     * Checks, whether a range on a given track number is free of annotations.
     * @param range range to check
     * @param track track to check
     * @return {@code true} if range is free on the track, {@code false} if not
     */
    @Override
    public boolean canInsertAt(Range range, int track) {
        Iterable<Annotation> annos = getAnnotations(range, track);
        boolean yesWeCan = true;
        while (yesWeCan && annos.iterator().hasNext()) {
            if (annos.iterator().next() != null) {
                yesWeCan = false;
            }
        }
        return yesWeCan;
    }

    /**
     * Checks, whether a range on a given track number has overlapping
     * annotations.
     * @param range range to check
     * @param track track to check
     * @return {@code true} if range has overlaps on the track, {@code false} if
     *         not
     */
    @Override
    public boolean hasOverlap(Range range, int track) {
        boolean res = false;
        for (Annotation anno : getAnnotations(range, track)) {
            res = !(range.contains(anno.getRange()) || anno.getRange().contains(range));
            if (res) {
                return res;
            }
        }
        return res;
    }

    /**
     * Deletes all annotations in the range of the given annotation on the given
     * track and inserts the new given annotation. The operation can only be
     * done, if there are no overlaps.
     * @param Annotation annotation to insert
     * @param track track of the annotation to replace
     * @return {@code true} if range of annotation has no overlaps and all
     *         annotations have been deleted in the range of the new annotation
     *         and the new annotation has been inserted, {@code false} if not
     */
    @Override
    public boolean replace(Annotation anno, int track) {
        if (hasOverlap(anno.getRange(), track))
            return false;
        deleteAnnotations(anno.getRange(), track);
        insertAnnotation(anno, track);
        return true;
    }

    /**
     * Not Used
     * @param List<Annotation[]> annotations from load
     */
    private void insertAll(List<Annotation[]> annotations) {

        for (int i = 0; i < annotations.size(); i++) {
            this.annotations.add(i, new TreeSet<Annotation>(new myComp()));
            for (int j = 0; j < annotations.get(i).length; j++) {
                this.annotations.get(i).add(annotations.get(i)[j]);
            }

        }

    }

    @Override
    public void setReader(Reader reader) {
        this.reader = reader;

    }

    @Override
    public void updateAnnotations(InstancesAdapter classifierInstance,
        InstancesAdapter instancesToClassify, FilteredClassifier filteredClassifier)
        throws Exception {
        for (int i = 0; i < instancesToClassify.numInstances(); i++) {
            double pred = filteredClassifier.classifyInstance(instancesToClassify.instance(i));
            String prediction = instancesToClassify.classAttribute().value((int) pred);

            // System.out.println(prediction);
            if (!prediction.equals("?")) {
                long timeStamp =
                    (long) instancesToClassify.get(i).value(
                        classifierInstance.attribute("timeStamp"));
                Range newRange = new Range(timeStamp, timeStamp + 1);
                Annotation annotation = null;
                if (classifierInstance.getOrigClassType().isFloatingpointAnnotation()) {
                    annotation =
                        new FloatingpointAnnotation(newRange, Float.parseFloat(prediction));
                } else if (classifierInstance.getOrigClassType().isIntegerAnnotation()) {
                    annotation = new IntegerAnnotation(newRange, Integer.parseInt(prediction));
                } else if (classifierInstance.getOrigClassType().isLabelAnnotation()) {
                    annotation = new LabelAnnotation(newRange, prediction);
                }
                if (annotation != null) {
                    insertAnnotation(annotation, classifierInstance.getOrigAnnotIdx());
                    // System.out.println(annotation);
                } else {
                    // System.out.println("Something went wrong");
                }
            }
        }
    }

}
