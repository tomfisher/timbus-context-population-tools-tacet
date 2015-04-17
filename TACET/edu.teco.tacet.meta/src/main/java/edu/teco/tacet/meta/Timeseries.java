/**
 */
package edu.teco.tacet.meta;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Timeseries</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.Timeseries#getId <em>Id</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Timeseries#getDatasource <em>Datasource</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Timeseries#getName <em>Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Timeseries#getType <em>Type</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.Timeseries#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getTimeseries()
 * @model
 * @generated
 */
public interface Timeseries extends EObject {
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
     * @see edu.teco.tacet.meta.MetaPackage#getTimeseries_Id()
     * @model id="true"
     * @generated
     */
    long getId();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Timeseries#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(long value);

    /**
     * Returns the value of the '<em><b>Datasource</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link edu.teco.tacet.meta.Datasource#getTimeseries <em>Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Datasource</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Datasource</em>' container reference.
     * @see #setDatasource(Datasource)
     * @see edu.teco.tacet.meta.MetaPackage#getTimeseries_Datasource()
     * @see edu.teco.tacet.meta.Datasource#getTimeseries
     * @model opposite="timeseries" transient="false"
     * @generated
     */
    Datasource getDatasource();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Timeseries#getDatasource <em>Datasource</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Datasource</em>' container reference.
     * @see #getDatasource()
     * @generated
     */
    void setDatasource(Datasource value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see edu.teco.tacet.meta.MetaPackage#getTimeseries_Name()
     * @model default=""
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Timeseries#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * The literals are from the enumeration {@link edu.teco.tacet.meta.TimeseriesType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see edu.teco.tacet.meta.TimeseriesType
     * @see #setType(TimeseriesType)
     * @see edu.teco.tacet.meta.MetaPackage#getTimeseries_Type()
     * @model
     * @generated
     */
    TimeseriesType getType();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.Timeseries#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see edu.teco.tacet.meta.TimeseriesType
     * @see #getType()
     * @generated
     */
    void setType(TimeseriesType value);

    /**
     * Returns the value of the '<em><b>Groups</b></em>' reference list.
     * The list contents are of type {@link edu.teco.tacet.meta.Group}.
     * It is bidirectional and its opposite is '{@link edu.teco.tacet.meta.Group#getTimeseries <em>Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Groups</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Groups</em>' reference list.
     * @see edu.teco.tacet.meta.MetaPackage#getTimeseries_Groups()
     * @see edu.teco.tacet.meta.Group#getTimeseries
     * @model opposite="timeseries"
     * @generated
     */
    EList<Group> getGroups();

} // Timeseries
