/**
 */
package edu.teco.tacet.meta;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.teco.tacet.meta.MetaPackage
 * @generated
 */
public interface MetaFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    MetaFactory eINSTANCE = edu.teco.tacet.meta.impl.MetaFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Project</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Project</em>'.
     * @generated
     */
    Project createProject();

    /**
     * Returns a new object of class '<em>Datasource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Datasource</em>'.
     * @generated
     */
    Datasource createDatasource();

    /**
     * Returns a new object of class '<em>Rdf Datasource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Rdf Datasource</em>'.
     * @generated
     */
    RdfDatasource createRdfDatasource();

    /**
     * Returns a new object of class '<em>Rdf Timeseries</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Rdf Timeseries</em>'.
     * @generated
     */
    RdfTimeseries createRdfTimeseries();

    /**
     * Returns a new object of class '<em>Db Column Description</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Db Column Description</em>'.
     * @generated
     */
    DbColumnDescription createDbColumnDescription();

    /**
     * Returns a new object of class '<em>Db Datasource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Db Datasource</em>'.
     * @generated
     */
    DbDatasource createDbDatasource();

    /**
     * Returns a new object of class '<em>CSV Datasource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>CSV Datasource</em>'.
     * @generated
     */
    CSVDatasource createCSVDatasource();

    /**
     * Returns a new object of class '<em>Column Description</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Column Description</em>'.
     * @generated
     */
    ColumnDescription createColumnDescription();

    /**
     * Returns a new object of class '<em>Timeseries</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Timeseries</em>'.
     * @generated
     */
    Timeseries createTimeseries();

    /**
     * Returns a new object of class '<em>Group</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Group</em>'.
     * @generated
     */
    Group createGroup();

    /**
     * Returns a new object of class '<em>Media Datasource</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Media Datasource</em>'.
     * @generated
     */
    MediaDatasource createMediaDatasource();

    /**
     * Returns a new object of class '<em>Media Timeseries</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Media Timeseries</em>'.
     * @generated
     */
    MediaTimeseries createMediaTimeseries();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    MetaPackage getMetaPackage();

} //MetaFactory
