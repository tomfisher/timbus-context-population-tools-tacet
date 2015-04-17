/**
 */
package edu.teco.tacet.meta.util;

import edu.teco.tacet.meta.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.teco.tacet.meta.MetaPackage
 * @generated
 */
public class MetaAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static MetaPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MetaAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = MetaPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MetaSwitch<Adapter> modelSwitch =
        new MetaSwitch<Adapter>() {
            @Override
            public Adapter caseProject(Project object) {
                return createProjectAdapter();
            }
            @Override
            public Adapter caseDatasource(Datasource object) {
                return createDatasourceAdapter();
            }
            @Override
            public Adapter caseRdfDatasource(RdfDatasource object) {
                return createRdfDatasourceAdapter();
            }
            @Override
            public Adapter caseRdfTimeseries(RdfTimeseries object) {
                return createRdfTimeseriesAdapter();
            }
            @Override
            public Adapter caseDbColumnDescription(DbColumnDescription object) {
                return createDbColumnDescriptionAdapter();
            }
            @Override
            public Adapter caseDbDatasource(DbDatasource object) {
                return createDbDatasourceAdapter();
            }
            @Override
            public Adapter caseCSVDatasource(CSVDatasource object) {
                return createCSVDatasourceAdapter();
            }
            @Override
            public Adapter caseColumnDescription(ColumnDescription object) {
                return createColumnDescriptionAdapter();
            }
            @Override
            public Adapter caseTimeseries(Timeseries object) {
                return createTimeseriesAdapter();
            }
            @Override
            public Adapter caseGroup(Group object) {
                return createGroupAdapter();
            }
            @Override
            public Adapter caseMediaDatasource(MediaDatasource object) {
                return createMediaDatasourceAdapter();
            }
            @Override
            public Adapter caseMediaTimeseries(MediaTimeseries object) {
                return createMediaTimeseriesAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.Project <em>Project</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.Project
     * @generated
     */
    public Adapter createProjectAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.Datasource <em>Datasource</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.Datasource
     * @generated
     */
    public Adapter createDatasourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.RdfDatasource <em>Rdf Datasource</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.RdfDatasource
     * @generated
     */
    public Adapter createRdfDatasourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.RdfTimeseries <em>Rdf Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.RdfTimeseries
     * @generated
     */
    public Adapter createRdfTimeseriesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.DbColumnDescription <em>Db Column Description</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.DbColumnDescription
     * @generated
     */
    public Adapter createDbColumnDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.DbDatasource <em>Db Datasource</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.DbDatasource
     * @generated
     */
    public Adapter createDbDatasourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.CSVDatasource <em>CSV Datasource</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.CSVDatasource
     * @generated
     */
    public Adapter createCSVDatasourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.ColumnDescription <em>Column Description</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.ColumnDescription
     * @generated
     */
    public Adapter createColumnDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.Timeseries <em>Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.Timeseries
     * @generated
     */
    public Adapter createTimeseriesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.Group <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.Group
     * @generated
     */
    public Adapter createGroupAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.MediaDatasource <em>Media Datasource</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.MediaDatasource
     * @generated
     */
    public Adapter createMediaDatasourceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.teco.tacet.meta.MediaTimeseries <em>Media Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.teco.tacet.meta.MediaTimeseries
     * @generated
     */
    public Adapter createMediaTimeseriesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //MetaAdapterFactory
