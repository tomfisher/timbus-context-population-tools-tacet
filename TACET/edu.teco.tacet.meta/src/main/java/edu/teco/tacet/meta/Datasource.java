/**
 */
package edu.teco.tacet.meta;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Datasource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.Datasource#getTimeseries <em>Timeseries</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Datasource#getId <em>Id</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Datasource#getName <em>Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Datasource#isIsInMemory <em>Is In Memory</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Datasource#getMetadata <em>Metadata</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getDatasource()
 * @model
 * @generated
 */
public interface Datasource extends EObject {
    /**
     * Returns the value of the '<em><b>Timeseries</b></em>' containment reference list.
     * The list contents are of type {@link edu.teco.tacet.meta.Timeseries}.
     * It is bidirectional and its opposite is '{@link edu.teco.tacet.meta.Timeseries#getDatasource <em>Datasource</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Timeseries</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Timeseries</em>' containment reference list.
     * @see edu.teco.tacet.meta.MetaPackage#getDatasource_Timeseries()
     * @see edu.teco.tacet.meta.Timeseries#getDatasource
     * @model opposite="datasource" containment="true" required="true"
     * @generated
     */
    EList<Timeseries> getTimeseries();

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(long)
     * @see edu.teco.tacet.meta.MetaPackage#getDatasource_Id()
     * @model id="true" required="true"
     * @generated
     */
    long getId();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Datasource#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(long value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDatasource_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Datasource#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Is In Memory</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is In Memory</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is In Memory</em>' attribute.
     * @see #setIsInMemory(boolean)
     * @see edu.teco.tacet.meta.MetaPackage#getDatasource_IsInMemory()
     * @model default="false" required="true"
     * @generated
     */
    boolean isIsInMemory();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Datasource#isIsInMemory <em>Is In Memory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is In Memory</em>' attribute.
     * @see #isIsInMemory()
     * @generated
     */
    void setIsInMemory(boolean value);

    /**
     * Returns the value of the '<em><b>Metadata</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Metadata</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Metadata</em>' attribute.
     * @see #setMetadata(Map)
     * @see edu.teco.tacet.meta.MetaPackage#getDatasource_Metadata()
     * @model dataType="edu.teco.tacet.meta.MetaData"
     * @generated
     */
    Map<Object, Object> getMetadata();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Datasource#getMetadata <em>Metadata</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Metadata</em>' attribute.
     * @see #getMetadata()
     * @generated
     */
    void setMetadata(Map<Object, Object> value);

} // Datasource
