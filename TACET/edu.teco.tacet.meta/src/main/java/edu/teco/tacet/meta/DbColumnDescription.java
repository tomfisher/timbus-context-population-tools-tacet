/**
 */
package edu.teco.tacet.meta;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Db Column Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.DbColumnDescription#getAttributeName <em>Attribute Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbColumnDescription#getTimeseriesId <em>Timeseries Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getDbColumnDescription()
 * @model
 * @generated
 */
public interface DbColumnDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attribute Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Attribute Name</em>' attribute.
     * @see #setAttributeName(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDbColumnDescription_AttributeName()
     * @model
     * @generated
     */
    String getAttributeName();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbColumnDescription#getAttributeName <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Attribute Name</em>' attribute.
     * @see #getAttributeName()
     * @generated
     */
    void setAttributeName(String value);

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
     * @see edu.teco.tacet.meta.MetaPackage#getDbColumnDescription_TimeseriesId()
     * @model
     * @generated
     */
    long getTimeseriesId();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbColumnDescription#getTimeseriesId <em>Timeseries Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Timeseries Id</em>' attribute.
     * @see #getTimeseriesId()
     * @generated
     */
    void setTimeseriesId(long value);

} // DbColumnDescription
