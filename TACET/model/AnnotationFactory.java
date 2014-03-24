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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import squirrel.util.Range;

/**
 * This class knows the allowed ranges for the different annotation types and
 * provides methods to create certain annotations. This assures, that no invalid
 * annotation is existent.
 *
 * @author Olivier
 *
 */
public class AnnotationFactory implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Integer: Track-Index, Double: value
     */
    private Map<Integer, Double> minValueFloating;
    private Map<Integer, Double> maxValueFloating;
    private Map<Integer, Integer> minValueInteger;
    private Map<Integer, Integer> maxValueInteger;
    /**
     * Integer: track-index, List: allowed values
     */
    private Map<Integer, List<String>> allowedValues;

    /**
     * Constructs a new AnnotationFactory() and initializes the private maps.
     */
    public AnnotationFactory() {
        minValueFloating = new HashMap<Integer, Double>();
        maxValueFloating = new HashMap<Integer, Double>();
        minValueInteger = new HashMap<Integer, Integer>();
        maxValueInteger = new HashMap<Integer, Integer>();
        allowedValues = new HashMap<Integer, List<String>>();
    }

    /**
     * Set the minimum floating point {@code value} for the track
     * {@code trackNo}.
     * @param trackNo track number
     * @param value min floating point value
     */
    public void setMinValueFloating(int trackNo, double value) {
        this.minValueFloating.put(trackNo, value);
    }

    public double getMinValueFloating(int trackNo) {
        return this.minValueFloating.get(trackNo);
    }

    /**
     * Set the maximum floating point {@code value} for the track
     * {@code trackNo}.
     * @param trackNo track number
     * @param value max floating point value
     */
    public void setMaxValueFloating(int trackNo, double value) {
        this.maxValueFloating.put(trackNo, value);
    }

    public double getMaxValueFloating(int trackNo) {
        return this.maxValueFloating.get(trackNo);
    }

    /**
     * Set the minimum Integer {@code value} for the track {@code trackNo}.
     * @param trackNo track number
     * @param value min integer value
     */
    public void setMinValueInteger(int trackNo, int value) {
        this.minValueInteger.put(trackNo, value);
    }

    public int getMinValueInteger(int trackNo) {
        return this.minValueInteger.get(trackNo);
    }

    /**
     * Set the maximum Integer {@code value} for the track {@code trackNo}.
     * @param trackNo track number
     * @param value max integer value
     */
    public void setMaxValueInteger(int trackNo, int value) {
        this.maxValueInteger.put(trackNo, value);
    }

    public int getMaxValueInteger(int trackNo) {
        return this.maxValueInteger.get(trackNo);
    }

    /**
     * Add a list of Strings which represent the allowed {@code values} to a
     * specific {@code trackNo}. If the given track number does not exist, it
     * will be created with the given list of string values.
     *
     * @param trackNo track number
     * @param values list of strings
     */
    public void addAllowedValues(int trackNo, List<String> values) {
        if (this.allowedValues.containsKey(trackNo)) {
            List<String> vals = this.allowedValues.get(trackNo);
            for (String s : values) {
                if (!vals.contains(s)) {
                    this.allowedValues.get(trackNo).add(s);
                }
            }
        } else {
            this.allowedValues.put(trackNo, values);
        }
    }

    /**
     * Returns a map which contains a list of allowed values for each track.
     * @return {@code Map<Integer, List<String>>}, a list of allowed values for
     *         each track.
     */
    public Map<Integer, List<String>> getAllowedValues() {
        return allowedValues;
    }

    /**
     * Returns a list of allowed values for the given {@code trackNo}.
     * @param trackNo track number
     * @return {@code List<String>} of allowed values for {@code trackNo}.
     */
    public List<String> getAllowedValues(int trackNo) {
        return this.allowedValues.get(trackNo);
    }

    /**
     * Checks whether the float is in a valid range and if the track number
     * exists.
     * @param trackNo track number
     * @param val float value
     * @return if {@code val} is in valid range and if {@code trackNo} exists
     */
    private boolean isValidFloat(int trackNo, double val) {
        return (minValueFloating.containsKey(trackNo) && val >= minValueFloating.get(trackNo)
                && maxValueFloating.containsKey(trackNo) && val <= maxValueFloating.get(trackNo));
    }

    /**
     * Checks whether the integer is in a valid range and if the track number
     * exists.
     * @param trackNo track number
     * @param val integer value
     * @return if {@code val} is in valid range and if {@code trackNo} exists
     */
    private boolean isValidInt(int trackNo, int val) {
        return (minValueInteger.containsKey(trackNo) && val >= minValueInteger.get(trackNo)
                && maxValueInteger.containsKey(trackNo) && val <= maxValueInteger.get(trackNo));
    }

    /**
     * Checks whether the string label is allowed and if the track number
     * exists.
     * @param trackNo track number
     * @param label string label
     * @return if {@code label} is an allowed label and if {@code trackNo}
     *         exists
     */
    private boolean isValidLabel(int trackNo, String label) {
        return (this.allowedValues.containsKey(trackNo)
        && this.allowedValues.get(trackNo).contains(label));

    }

    /**
     * Creates a floating point annotation of type
     * {@link FloatingpointAnnotation} with the given track number, start and
     * end range for the annotation and the floating point value as annotation.
     * @param trackNo track number
     * @param range start and end of the annotation
     * @param value value of the floating point annotation
     * @return {@code FloatingpointAnnotation}, {@code null} if invalid input
     */
    public FloatingpointAnnotation createFloatingpointAnnotation(int trackNo, Range range,
            double value) {
        FloatingpointAnnotation fpAnno = null;
        if (isValidFloat(trackNo, value)) {
            fpAnno = new FloatingpointAnnotation(range, value, trackNo);
        }
        return fpAnno;
    }

    /**
     * Creates an integer annotation of type {@link IntegerAnnotation} with the
     * given track number, start and end range for the annotation and the
     * integer value as annotation.
     * @param trackNo track number
     * @param range start and end of the annotation
     * @param value value of the integer annotation
     * @return {@code IntegerAnnotation}, {@code null} if invalid input
     */
    public IntegerAnnotation createIntegerAnnotation(int trackNo, Range range, int value) {
        IntegerAnnotation intAnno = null;
        if (isValidInt(trackNo, value)) {
            intAnno = new IntegerAnnotation(range, value, trackNo);
        }
        return intAnno;
    }

    /**
     * Creates a label annotation of type {@link LabelAnnotation} with the given
     * track number, start and end range for the annotation and the label as
     * annotation.
     * @param trackNo track number
     * @param range start and end of the annotation
     * @param value string value of the label annotation
     * @return {@code LabelAnnotation}, {@code null} if invalid input
     */
    public LabelAnnotation createLabelAnnotation(int trackNo, Range range, String label) {
        LabelAnnotation labelAnno = null;
        if (isValidLabel(trackNo, label)) {
            labelAnno = new LabelAnnotation(range, label, trackNo);
        }
        return labelAnno;
    }

    public TrainAnnotation createTrainAnnotation(int trackNo, Range range) {
        return new TrainAnnotation(range, trackNo);

    }

    @Override
    public String toString() {
        return String.format(
            "[AF> minF: %s | maxF: %s | minI: %s | maxI: %s | aVal: %s]",
            minValueFloating, maxValueFloating, minValueInteger, maxValueInteger, allowedValues);
    }

}
