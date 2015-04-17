/**
 */
package edu.teco.tacet.meta;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.Project#getDatasources <em>Datasources</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Project#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Project#getLastTrackId <em>Last Track Id</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Project#getLastSourceId <em>Last Source Id</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Project#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends EObject {
    /**
     * Returns the value of the '<em><b>Datasources</b></em>' containment reference list.
     * The list contents are of type {@link edu.teco.tacet.meta.Datasource}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Datasources</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Datasources</em>' containment reference list.
     * @see edu.teco.tacet.meta.MetaPackage#getProject_Datasources()
     * @model containment="true"
     * @generated
     */
    EList<Datasource> getDatasources();

    /**
     * Returns the value of the '<em><b>Identifier</b></em>' attribute.
     * The default value is <code>"\"\""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Identifier</em>' attribute.
     * @see #setIdentifier(String)
     * @see edu.teco.tacet.meta.MetaPackage#getProject_Identifier()
     * @model default="\"\"" id="true"
     * @generated
     */
    String getIdentifier();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Project#getIdentifier <em>Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Identifier</em>' attribute.
     * @see #getIdentifier()
     * @generated
     */
    void setIdentifier(String value);

    /**
     * Returns the value of the '<em><b>Last Track Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Last Track Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Last Track Id</em>' attribute.
     * @see #setLastTrackId(long)
     * @see edu.teco.tacet.meta.MetaPackage#getProject_LastTrackId()
     * @model required="true"
     * @generated
     */
    long getLastTrackId();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Project#getLastTrackId <em>Last Track Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Last Track Id</em>' attribute.
     * @see #getLastTrackId()
     * @generated
     */
    void setLastTrackId(long value);

    /**
     * Returns the value of the '<em><b>Last Source Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Last Source Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Last Source Id</em>' attribute.
     * @see #setLastSourceId(long)
     * @see edu.teco.tacet.meta.MetaPackage#getProject_LastSourceId()
     * @model required="true"
     * @generated
     */
    long getLastSourceId();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Project#getLastSourceId <em>Last Source Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Last Source Id</em>' attribute.
     * @see #getLastSourceId()
     * @generated
     */
    void setLastSourceId(long value);

    /**
     * Returns the value of the '<em><b>Groups</b></em>' reference list.
     * The list contents are of type {@link edu.teco.tacet.meta.Group}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Groups</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Groups</em>' reference list.
     * @see edu.teco.tacet.meta.MetaPackage#getProject_Groups()
     * @model
     * @generated
     */
    EList<Group> getGroups();

} // Project
