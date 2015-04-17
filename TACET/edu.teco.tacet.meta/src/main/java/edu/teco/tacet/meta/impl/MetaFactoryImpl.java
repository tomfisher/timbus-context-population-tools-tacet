/**
 */
package edu.teco.tacet.meta.impl;

import com.hp.hpl.jena.rdf.model.Model;

import edu.teco.tacet.meta.*;

import edu.teco.tacet.meta.nongen.Unit;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetaFactoryImpl extends EFactoryImpl implements MetaFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static MetaFactory init() {
        try {
            MetaFactory theMetaFactory = (MetaFactory)EPackage.Registry.INSTANCE.getEFactory(MetaPackage.eNS_URI);
            if (theMetaFactory != null) {
                return theMetaFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new MetaFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MetaFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case MetaPackage.PROJECT: return createProject();
            case MetaPackage.DATASOURCE: return createDatasource();
            case MetaPackage.RDF_DATASOURCE: return createRdfDatasource();
            case MetaPackage.RDF_TIMESERIES: return createRdfTimeseries();
            case MetaPackage.DB_COLUMN_DESCRIPTION: return createDbColumnDescription();
            case MetaPackage.DB_DATASOURCE: return createDbDatasource();
            case MetaPackage.CSV_DATASOURCE: return createCSVDatasource();
            case MetaPackage.COLUMN_DESCRIPTION: return createColumnDescription();
            case MetaPackage.TIMESERIES: return createTimeseries();
            case MetaPackage.GROUP: return createGroup();
            case MetaPackage.MEDIA_DATASOURCE: return createMediaDatasource();
            case MetaPackage.MEDIA_TIMESERIES: return createMediaTimeseries();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case MetaPackage.COLUMN:
                return createColumnFromString(eDataType, initialValue);
            case MetaPackage.TIMESERIES_TYPE:
                return createTimeseriesTypeFromString(eDataType, initialValue);
            case MetaPackage.META_DATA:
                return createMetaDataFromString(eDataType, initialValue);
            case MetaPackage.UNIT:
                return createUnitFromString(eDataType, initialValue);
            case MetaPackage.RDF_TO_TIMESTAMP_MAPPING:
                return createRdfToTimestampMappingFromString(eDataType, initialValue);
            case MetaPackage.MODEL:
                return createModelFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case MetaPackage.COLUMN:
                return convertColumnToString(eDataType, instanceValue);
            case MetaPackage.TIMESERIES_TYPE:
                return convertTimeseriesTypeToString(eDataType, instanceValue);
            case MetaPackage.META_DATA:
                return convertMetaDataToString(eDataType, instanceValue);
            case MetaPackage.UNIT:
                return convertUnitToString(eDataType, instanceValue);
            case MetaPackage.RDF_TO_TIMESTAMP_MAPPING:
                return convertRdfToTimestampMappingToString(eDataType, instanceValue);
            case MetaPackage.MODEL:
                return convertModelToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Project createProject() {
        ProjectImpl project = new ProjectImpl();
        return project;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Datasource createDatasource() {
        DatasourceImpl datasource = new DatasourceImpl();
        return datasource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RdfDatasource createRdfDatasource() {
        RdfDatasourceImpl rdfDatasource = new RdfDatasourceImpl();
        return rdfDatasource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RdfTimeseries createRdfTimeseries() {
        RdfTimeseriesImpl rdfTimeseries = new RdfTimeseriesImpl();
        return rdfTimeseries;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DbColumnDescription createDbColumnDescription() {
        DbColumnDescriptionImpl dbColumnDescription = new DbColumnDescriptionImpl();
        return dbColumnDescription;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DbDatasource createDbDatasource() {
        DbDatasourceImpl dbDatasource = new DbDatasourceImpl();
        return dbDatasource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CSVDatasource createCSVDatasource() {
        CSVDatasourceImpl csvDatasource = new CSVDatasourceImpl();
        return csvDatasource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ColumnDescription createColumnDescription() {
        ColumnDescriptionImpl columnDescription = new ColumnDescriptionImpl();
        return columnDescription;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Timeseries createTimeseries() {
        TimeseriesImpl timeseries = new TimeseriesImpl();
        return timeseries;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Group createGroup() {
        GroupImpl group = new GroupImpl();
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MediaDatasource createMediaDatasource() {
        MediaDatasourceImpl mediaDatasource = new MediaDatasourceImpl();
        return mediaDatasource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MediaTimeseries createMediaTimeseries() {
        MediaTimeseriesImpl mediaTimeseries = new MediaTimeseriesImpl();
        return mediaTimeseries;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Column createColumnFromString(EDataType eDataType, String initialValue) {
        Column result = Column.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertColumnToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeseriesType createTimeseriesTypeFromString(EDataType eDataType, String initialValue) {
        TimeseriesType result = TimeseriesType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertTimeseriesTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public Map<Object, Object> createMetaDataFromString(EDataType eDataType, String initialValue) {
        return (Map<Object, Object>)super.createFromString(initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertMetaDataToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Unit createUnitFromString(EDataType eDataType, String initialValue) {
        return (Unit)super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertUnitToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public Map<Object, Object> createRdfToTimestampMappingFromString(EDataType eDataType, String initialValue) {
        return (Map<Object, Object>)super.createFromString(initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertRdfToTimestampMappingToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Model createModelFromString(EDataType eDataType, String initialValue) {
        return (Model)super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertModelToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MetaPackage getMetaPackage() {
        return (MetaPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static MetaPackage getPackage() {
        return MetaPackage.eINSTANCE;
    }

} //MetaFactoryImpl
