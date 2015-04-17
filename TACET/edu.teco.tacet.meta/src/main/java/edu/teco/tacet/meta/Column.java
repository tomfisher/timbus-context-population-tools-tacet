/**
 */
package edu.teco.tacet.meta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Column</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.teco.tacet.meta.MetaPackage#getColumn()
 * @model
 * @generated
 */
public enum Column implements Enumerator {
    /**
     * The '<em><b>Annotation</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ANNOTATION_VALUE
     * @generated
     * @ordered
     */
    ANNOTATION(0, "Annotation", "Annotation"),

    /**
     * The '<em><b>Sensor Data</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SENSOR_DATA_VALUE
     * @generated
     * @ordered
     */
    SENSOR_DATA(1, "SensorData", "SensorData"),

    /**
     * The '<em><b>Timestamp</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #TIMESTAMP_VALUE
     * @generated
     * @ordered
     */
    TIMESTAMP(2, "Timestamp", "");

    /**
     * The '<em><b>Annotation</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Annotation</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ANNOTATION
     * @model name="Annotation"
     * @generated
     * @ordered
     */
    public static final int ANNOTATION_VALUE = 0;

    /**
     * The '<em><b>Sensor Data</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Sensor Data</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SENSOR_DATA
     * @model name="SensorData"
     * @generated
     * @ordered
     */
    public static final int SENSOR_DATA_VALUE = 1;

    /**
     * The '<em><b>Timestamp</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Timestamp</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #TIMESTAMP
     * @model name="Timestamp" literal=""
     * @generated
     * @ordered
     */
    public static final int TIMESTAMP_VALUE = 2;

    /**
     * An array of all the '<em><b>Column</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final Column[] VALUES_ARRAY =
        new Column[] {
            ANNOTATION,
            SENSOR_DATA,
            TIMESTAMP,
        };

    /**
     * A public read-only list of all the '<em><b>Column</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<Column> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Column</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Column get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Column result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Column</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Column getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Column result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Column</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Column get(int value) {
        switch (value) {
            case ANNOTATION_VALUE: return ANNOTATION;
            case SENSOR_DATA_VALUE: return SENSOR_DATA;
            case TIMESTAMP_VALUE: return TIMESTAMP;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private Column(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
      return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
      return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral() {
      return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }
    
} //Column
