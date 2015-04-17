/**
 */
package edu.teco.tacet.meta;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Column Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.ColumnDescription#getColumnType <em>Column Type</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.ColumnDescription#getTimeseriesId <em>Timeseries Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getColumnDescription()
 * @model
 * @generated
 */
public interface ColumnDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Column Type</b></em>' attribute.
     * The literals are from the enumeration {@link edu.teco.tacet.meta.Column}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Column Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Column Type</em>' attribute.
     * @see edu.teco.tacet.meta.Column
     * @see #setColumnType(Column)
     * @see edu.teco.tacet.meta.MetaPackage#getColumnDescription_ColumnType()
     * @model
     * @generated
     */
    Column getColumnType();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.ColumnDescription#getColumnType <em>Column Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Column Type</em>' attribute.
     * @see edu.teco.tacet.meta.Column
     * @see #getColumnType()
     * @generated
     */
    void setColumnType(Column value);

    /**
     * Returns the value of the '<em><b>Timeseries Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Timeseries Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Timeseries Id</em>' attribute.
     * @see #setTimeseriesId(long)
     * @see edu.teco.tacet.meta.MetaPackage#getColumnDescription_TimeseriesId()
     * @model
     * @generated
     */
    long getTimeseriesId();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.ColumnDescription#getTimeseriesId <em>Timeseries Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Timeseries Id</em>' attribute.
     * @see #getTimeseriesId()
     * @generated
     */
    void setTimeseriesId(long value);

} // ColumnDescription
