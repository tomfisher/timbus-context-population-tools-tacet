/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package squirrel.model.io;

import java.io.Serializable;

/**
 * This class represents a description of a column in a file.
 * @author Olivier
 *
 */
public class DataColumn implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents all allowed Types of a column. (Timestamp, Annotation, Sensor,
     * Ignore...)
     * @author Olivier
     *
     */
    public enum Type {
        TIMESTAMP, FLOATING_POINT_ANNOTATION, INTEGER_ANNOTATION, LABEL_ANNOTATION, SENSOR, IGNORE,
        NEW_FLOATING_POINT_ANNOTATION, NEW_INTEGER_ANNOTATION, NEW_LABEL_ANNOTATION, NEW_TRAIN_ANNOTATION;

        public boolean isAnnotation() {
            return (this == Type.FLOATING_POINT_ANNOTATION ||
                    this == Type.INTEGER_ANNOTATION ||
                    this == Type.LABEL_ANNOTATION ||
                    this == Type.NEW_FLOATING_POINT_ANNOTATION ||
                    this == Type.NEW_INTEGER_ANNOTATION || this == Type.NEW_LABEL_ANNOTATION ||
                    this == Type.NEW_TRAIN_ANNOTATION);
        }

        public boolean isOldAnnotation() {
            return (this == Type.FLOATING_POINT_ANNOTATION ||
                    this == Type.INTEGER_ANNOTATION || this == Type.LABEL_ANNOTATION);
        }

        public boolean isRangedAnnotation() {
            return (this.isFloatingpointAnnotation() || this.isIntegerAnnotation());
        }

        public boolean isIntegerAnnotation() {
            return (this == Type.NEW_INTEGER_ANNOTATION || this == Type.INTEGER_ANNOTATION);
        }

        public boolean isFloatingpointAnnotation() {
            return (this == Type.NEW_FLOATING_POINT_ANNOTATION ||
                    this == Type.FLOATING_POINT_ANNOTATION);
        }

        public boolean isLabelAnnotation() {
            return (this == Type.LABEL_ANNOTATION || this == Type.NEW_LABEL_ANNOTATION);
        }

        public boolean isTrainAnnotation() {
            return (this == Type.NEW_TRAIN_ANNOTATION);
        }

        /**
         * Returns whether this column contains sensordata.
         * @return {@code true} if is a sensordata, {@code false} if not
         */
        public boolean isSensor() {
            return this == Type.SENSOR;
        }

        /**
         * Returns whether this column contains a timestamp or is part of a
         * timestamp.
         * @return {@code true} if is timestamp, {@code false} if not
         */
        public boolean isTimeStamp() {
            return this == Type.TIMESTAMP;
        }

        /**
         * Returns whether this column shall be ignored.
         * @return {@code true} if this column shall be ignored, {@code false}
         *         if not
         */
        public boolean ignore() {
            return this == Type.IGNORE;
        }

        /**
         * Returns if this column is reserved for a new annotation.
         * @return {@code true} if this column is reserved for a new annotation,
         *         {@code false} if not
         */
        public boolean isNewAnnotation() {
            return (this == Type.NEW_FLOATING_POINT_ANNOTATION ||
                    this == Type.NEW_INTEGER_ANNOTATION || this == Type.NEW_LABEL_ANNOTATION || this == Type.NEW_TRAIN_ANNOTATION);
        }
    }

    /**
     * This class represents all allowed timeunits.
     * @author Olivier
     *
     */
    public enum Unit {
        YEARS {
            @Override
            public long toMillis(long d) {
                return 0;
            }

        },
        MONTHS {
            @Override
            public long toMillis(long d) {
                return 0;
            }
        },
        WEEKS {
            @Override
            public long toMillis(long d) {
                return x(d, C7 / C2, MAX / (C7 / C2));
            }
        },
        DAYS {
            @Override
            public long toMillis(long d) {
                return x(d, C6 / C2, MAX / (C6 / C2));
            }
        },
        HOURS {
            @Override
            public long toMillis(long d) {
                return x(d, C5 / C2, MAX / (C5 / C2));
            }

            @Override
            public long toOldTime(long milli) {
                return (long) (milli / (1000 * 60 * 60))%24;
            }

            @Override
            public long toOldTimeMax(long milli){
                return (long)(milli / (1000*60*60));
            }
        },
        MINUTES {
            @Override
            public long toMillis(long d) {
                return x(d, C4 / C2, MAX / (C4 / C2));
            }

            @Override
            public long toOldTime(long milli) {
                return (milli / (1000 * 60)
                ) % (60);
            }

            @Override
            public long toOldTimeMax(long milli){
                return (long)(milli / (1000*60));
            }
        },
        SECONDS {
            @Override
            public long toMillis(long d) {
                return x(d, C3 / C2, MAX / (C3 / C2));
            }

            @Override
            public long toOldTime(long milli) {
                return ((milli / 1000) % (60));
            }

            @Override
            public long toOldTimeMax(long milli){
                return (long)(milli / (1000));
            }
        },
        TENTH_SECONDS {
            @Override
            public long toMillis(long d) {
                return x(d, (100L * C2) / C2, MAX / ((100 * C2) / C2));
            }

            @Override
            public long toOldTime(long milli) {
                return ((milli / (100)
                ) % (60));
            }

            @Override
            public long toOldTimeMax(long milli){
                return (long)(milli / (100));
            }


        },
        HUNDRETH_SECONDS {
            @Override
            public long toMillis(long d) {
                return x(d, (10L * C2) / C2, MAX / ((10 * C2) / C2));
            }

            @Override
            public long toOldTime(long milli) {
                return ((milli / (10)
                ) % (60));
            }

            @Override
            public long toOldTimeMax(long milli){
                return (long)(milli / (10));
            }

        },
        MILLISECONDS {
            @Override
            public long toMillis(long d) {
                return d;
            }

            @Override
            public long toOldTime(long milli) {
                return (milli % 1000);
            }

            @Override
            public long toOldTimeMax(long milli){
                return milli;
            }
        },
        NONE {
            @Override
            public long toMillis(long d) {
                return 0;
            }
        };

        // Handy constants for conversion methods
        static final long C0 = 1L;
        static final long C1 = C0 * 1000L;
        static final long C2 = C1 * 1000L;
        static final long C3 = C2 * 1000L;
        static final long C4 = C3 * 60L;
        static final long C5 = C4 * 60L;
        static final long C6 = C5 * 24L;
        static final long C7 = C6 * 7L;

        static final long MAX = Long.MAX_VALUE;

        /*
         * Scale d by m, checking for overflow. This has a short name to make
         * above code more readable.
         */
        private static long x(long d, long m, long over) {
            if (d > over)
                return Long.MAX_VALUE;
            if (d < -over)
                return Long.MIN_VALUE;
            return d * m;
        }

        /**
         * Converts the duration to milliseconds.
         * @param duration the duration
         * @return the converted duration, or Long.MIN_VALUE if conversion would
         *         negatively overflow, or Long.MAX_VALUE if it would positively
         *         overflow.
         */
        public long toMillis(long duration) {
            throw new AbstractMethodError();
        }

        public long toOldTime(long milli) {
            throw new AbstractMethodError();
        }

        public long toOldTimeMax(long milli){
            throw new AbstractMethodError();
        }




    }

    private Type type;
    private String name;
    private Unit unit;

    private int timestampIdx, annotationIdx, sensorIdx;
    private boolean isSelected = false;

    /**
     * Constructs a new DataColumn.
     */
    public DataColumn() {}

    /**
     * Creates a new DataColumn.
     * @param type Type of Annotation.
     * @param name
     */
    public DataColumn(String name, Type type, Unit unit) {
        super();
        this.type = type;
        this.name = name;
        this.unit = unit;
        this.timestampIdx = -1;
        this.annotationIdx = -1;
        this.sensorIdx = -1;

        if(type == Type.TIMESTAMP)
            isSelected = true;
    }

    public int getTimestampIdx() {
        return timestampIdx;
    }

    public void setTimestampIdx(int timestampIdx) {
        this.timestampIdx = timestampIdx;
    }

    public int getAnnotationIdx() {
        return annotationIdx;
    }

    public void setAnnotationIdx(int annotationIdx) {
        this.annotationIdx = annotationIdx;
    }

    public int getSensorIdx() {
        return sensorIdx;
    }

    public void setSensorIdx(int sensorIdx) {
        this.sensorIdx = sensorIdx;
    }

    /**
     * Returns the {@link Type} of this column.
     * @return type of this column.
     */
    public Type getType() {
        return type;
    }

    /**
     * Set the {@link Type} of this column.
     * @param type type of this column
     */
    public void setType(Type type) {
        this.type = type;
        if(type == Type.TIMESTAMP)
            isSelected = true;
    }

    /**
     * Returns the name of this column.
     * @return name of this column
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this column.
     * @param name name of this column
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the {@link Unit} of this column.
     * @return unit of this column
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Sets the {@link Unit} of this column.
     * @param unit unit of this column
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return String.format(
            "[DC> Name: %s | Type: %22s | Unit: %s | #A: %2d | #S: %2d | #T: %2d",
            name, type, unit, annotationIdx, sensorIdx, timestampIdx);
    }

    @Override
    public DataColumn clone() {
        DataColumn theClone = null;
        try {
            theClone = (DataColumn) super.clone();
        }
        catch(CloneNotSupportedException e) {
        }
        return theClone;
      }

    public boolean isSelected() {
        return isSelected ;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if(type == Type.TIMESTAMP)
                isSelected = true;
    }
}
