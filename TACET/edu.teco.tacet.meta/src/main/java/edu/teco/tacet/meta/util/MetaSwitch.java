/**
 */
package edu.teco.tacet.meta.util;

import edu.teco.tacet.meta.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see edu.teco.tacet.meta.MetaPackage
 * @generated
 */
public class MetaSwitch<T> extends Switch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static MetaPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MetaSwitch() {
        if (modelPackage == null) {
            modelPackage = MetaPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @parameter ePackage the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case MetaPackage.PROJECT: {
                Project project = (Project)theEObject;
                T result = caseProject(project);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.DATASOURCE: {
                Datasource datasource = (Datasource)theEObject;
                T result = caseDatasource(datasource);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.RDF_DATASOURCE: {
                RdfDatasource rdfDatasource = (RdfDatasource)theEObject;
                T result = caseRdfDatasource(rdfDatasource);
                if (result == null) result = caseDatasource(rdfDatasource);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.RDF_TIMESERIES: {
                RdfTimeseries rdfTimeseries = (RdfTimeseries)theEObject;
                T result = caseRdfTimeseries(rdfTimeseries);
                if (result == null) result = caseTimeseries(rdfTimeseries);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.DB_COLUMN_DESCRIPTION: {
                DbColumnDescription dbColumnDescription = (DbColumnDescription)theEObject;
                T result = caseDbColumnDescription(dbColumnDescription);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.DB_DATASOURCE: {
                DbDatasource dbDatasource = (DbDatasource)theEObject;
                T result = caseDbDatasource(dbDatasource);
                if (result == null) result = caseDatasource(dbDatasource);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.CSV_DATASOURCE: {
                CSVDatasource csvDatasource = (CSVDatasource)theEObject;
                T result = caseCSVDatasource(csvDatasource);
                if (result == null) result = caseDatasource(csvDatasource);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.COLUMN_DESCRIPTION: {
                ColumnDescription columnDescription = (ColumnDescription)theEObject;
                T result = caseColumnDescription(columnDescription);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.TIMESERIES: {
                Timeseries timeseries = (Timeseries)theEObject;
                T result = caseTimeseries(timeseries);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.GROUP: {
                Group group = (Group)theEObject;
                T result = caseGroup(group);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.MEDIA_DATASOURCE: {
                MediaDatasource mediaDatasource = (MediaDatasource)theEObject;
                T result = caseMediaDatasource(mediaDatasource);
                if (result == null) result = caseDatasource(mediaDatasource);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case MetaPackage.MEDIA_TIMESERIES: {
                MediaTimeseries mediaTimeseries = (MediaTimeseries)theEObject;
                T result = caseMediaTimeseries(mediaTimeseries);
                if (result == null) result = caseTimeseries(mediaTimeseries);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Project</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Project</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProject(Project object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Datasource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Datasource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDatasource(Datasource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rdf Datasource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rdf Datasource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRdfDatasource(RdfDatasource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rdf Timeseries</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rdf Timeseries</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRdfTimeseries(RdfTimeseries object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Db Column Description</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Db Column Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDbColumnDescription(DbColumnDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Db Datasource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Db Datasource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDbDatasource(DbDatasource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>CSV Datasource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>CSV Datasource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCSVDatasource(CSVDatasource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Column Description</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Column Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseColumnDescription(ColumnDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Timeseries</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Timeseries</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTimeseries(Timeseries object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Group</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Group</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGroup(Group object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Media Datasource</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Media Datasource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMediaDatasource(MediaDatasource object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Media Timeseries</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Media Timeseries</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMediaTimeseries(MediaTimeseries object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} //MetaSwitch
