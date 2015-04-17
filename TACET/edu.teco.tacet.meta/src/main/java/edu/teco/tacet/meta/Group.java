/**
 */
package edu.teco.tacet.meta;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.Group#getTimeseries <em>Timeseries</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Group#getName <em>Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Group#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends EObject {
    /**
     * Returns the value of the '<em><b>Timeseries</b></em>' reference list.
     * The list contents are of type {@link edu.teco.tacet.meta.Timeseries}.
     * It is bidirectional and its opposite is '{@link edu.teco.tacet.meta.Timeseries#getGroups <em>Groups</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Timeseries</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Timeseries</em>' reference list.
     * @see edu.teco.tacet.meta.MetaPackage#getGroup_Timeseries()
     * @see edu.teco.tacet.meta.Timeseries#getGroups
     * @model opposite="groups"
     * @generated
     */
    EList<Timeseries> getTimeseries();

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
     * @see edu.teco.tacet.meta.MetaPackage#getGroup_Name()
     * @model required="true"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Group#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

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
     * @see edu.teco.tacet.meta.MetaPackage#getGroup_Id()
     * @model id="true" required="true"
     * @generated
     */
    long getId();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Group#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(long value);

} // Group
