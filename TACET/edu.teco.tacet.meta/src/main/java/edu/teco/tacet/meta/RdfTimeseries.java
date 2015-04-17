/**
 */
package edu.teco.tacet.meta;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rdf Timeseries</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.RdfTimeseries#getValuePath <em>Value Path</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.RdfTimeseries#getTimestampPath <em>Timestamp Path</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.RdfTimeseries#getIdentifierPath <em>Identifier Path</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.RdfTimeseries#getIdentifierValue <em>Identifier Value</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.RdfTimeseries#getMapping <em>Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getRdfTimeseries()
 * @model
 * @generated
 */
public interface RdfTimeseries extends Timeseries {
    /**
     * Returns the value of the '<em><b>Value Path</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value Path</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Value Path</em>' attribute list.
     * @see edu.teco.tacet.meta.MetaPackage#getRdfTimeseries_ValuePath()
     * @model unique="false"
     * @generated
     */
    EList<String> getValuePath();

    /**
     * Returns the value of the '<em><b>Timestamp Path</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Timestamp Path</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Timestamp Path</em>' attribute list.
     * @see edu.teco.tacet.meta.MetaPackage#getRdfTimeseries_TimestampPath()
     * @model unique="false"
     * @generated
     */
    EList<String> getTimestampPath();

    /**
     * Returns the value of the '<em><b>Identifier Path</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier Path</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifier Path</em>' attribute list.
     * @see edu.teco.tacet.meta.MetaPackage#getRdfTimeseries_IdentifierPath()
     * @model
     * @generated
     */
    EList<String> getIdentifierPath();

    /**
     * Returns the value of the '<em><b>Identifier Value</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifier Value</em>' attribute.
     * @see #setIdentifierValue(String)
     * @see edu.teco.tacet.meta.MetaPackage#getRdfTimeseries_IdentifierValue()
     * @model default=""
     * @generated
     */
    String getIdentifierValue();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.RdfTimeseries#getIdentifierValue <em>Identifier Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identifier Value</em>' attribute.
     * @see #getIdentifierValue()
     * @generated
     */
    void setIdentifierValue(String value);

    /**
     * Returns the value of the '<em><b>Mapping</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mapping</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Mapping</em>' attribute.
     * @see #setMapping(Map)
     * @see edu.teco.tacet.meta.MetaPackage#getRdfTimeseries_Mapping()
     * @model default="" dataType="edu.teco.tacet.meta.RdfToTimestampMapping"
     * @generated
     */
    Map<Object, Object> getMapping();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.RdfTimeseries#getMapping <em>Mapping</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mapping</em>' attribute.
     * @see #getMapping()
     * @generated
     */
    void setMapping(Map<Object, Object> value);

} // RdfTimeseries
