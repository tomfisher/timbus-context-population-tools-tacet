/**
 */
package edu.teco.tacet.meta.impl;

import com.hp.hpl.jena.rdf.model.Model;

import edu.teco.tacet.meta.CSVDatasource;
import edu.teco.tacet.meta.Column;
import edu.teco.tacet.meta.ColumnDescription;
import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.DbColumnDescription;
import edu.teco.tacet.meta.DbDatasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.MediaDatasource;
import edu.teco.tacet.meta.MediaTimeseries;
import edu.teco.tacet.meta.MetaFactory;
import edu.teco.tacet.meta.MetaPackage;
import edu.teco.tacet.meta.Project;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.RdfTimeseries;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;

import edu.teco.tacet.meta.nongen.Unit;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetaPackageImpl extends EPackageImpl implements MetaPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass projectEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass datasourceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass rdfDatasourceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass rdfTimeseriesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dbColumnDescriptionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass dbDatasourceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass csvDatasourceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass columnDescriptionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass timeseriesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass groupEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass mediaDatasourceEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass mediaTimeseriesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum columnEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum timeseriesTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType metaDataEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType unitEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType rdfToTimestampMappingEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType modelEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see edu.teco.tacet.meta.MetaPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private MetaPackageImpl() {
        super(eNS_URI, MetaFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link MetaPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static MetaPackage init() {
        if (isInited) return (MetaPackage)EPackage.Registry.INSTANCE.getEPackage(MetaPackage.eNS_URI);

        // Obtain or create and register package
        MetaPackageImpl theMetaPackage = (MetaPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MetaPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MetaPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theMetaPackage.createPackageContents();

        // Initialize created meta-data
        theMetaPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theMetaPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(MetaPackage.eNS_URI, theMetaPackage);
        return theMetaPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getProject() {
        return projectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getProject_Datasources() {
        return (EReference)projectEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProject_Identifier() {
        return (EAttribute)projectEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProject_LastTrackId() {
        return (EAttribute)projectEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProject_LastSourceId() {
        return (EAttribute)projectEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getProject_Groups() {
        return (EReference)projectEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDatasource() {
        return datasourceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDatasource_Timeseries() {
        return (EReference)datasourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDatasource_Id() {
        return (EAttribute)datasourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDatasource_Name() {
        return (EAttribute)datasourceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDatasource_IsInMemory() {
        return (EAttribute)datasourceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDatasource_Metadata() {
        return (EAttribute)datasourceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRdfDatasource() {
        return rdfDatasourceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfDatasource_ResolveUri() {
        return (EAttribute)rdfDatasourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfDatasource_FileName() {
        return (EAttribute)rdfDatasourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfDatasource_RootResourceUri() {
        return (EAttribute)rdfDatasourceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfDatasource_Model() {
        return (EAttribute)rdfDatasourceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfDatasource_TimestampFormat() {
        return (EAttribute)rdfDatasourceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getRdfTimeseries() {
        return rdfTimeseriesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfTimeseries_ValuePath() {
        return (EAttribute)rdfTimeseriesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfTimeseries_TimestampPath() {
        return (EAttribute)rdfTimeseriesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfTimeseries_IdentifierPath() {
        return (EAttribute)rdfTimeseriesEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfTimeseries_IdentifierValue() {
        return (EAttribute)rdfTimeseriesEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRdfTimeseries_Mapping() {
        return (EAttribute)rdfTimeseriesEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDbColumnDescription() {
        return dbColumnDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbColumnDescription_AttributeName() {
        return (EAttribute)dbColumnDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbColumnDescription_TimeseriesId() {
        return (EAttribute)dbColumnDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDbDatasource() {
        return dbDatasourceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_Login() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_Password() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_Location() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_CoveredRangeStart() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_CoveredRangeEnd() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_Sid() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDbDatasource_ColumnDescriptions() {
        return (EReference)dbDatasourceEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_TimestampAttributeName() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_Query() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDbDatasource_JdbcDriver() {
        return (EAttribute)dbDatasourceEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCSVDatasource() {
        return csvDatasourceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCSVDatasource_ElementSeparator() {
        return (EAttribute)csvDatasourceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCSVDatasource_LineSeparator() {
        return (EAttribute)csvDatasourceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCSVDatasource_FilePath() {
        return (EAttribute)csvDatasourceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCSVDatasource_ColumnDescriptions() {
        return (EReference)csvDatasourceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCSVDatasource_TimestampFormat() {
        return (EAttribute)csvDatasourceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCSVDatasource_NoOfLinesToSkip() {
        return (EAttribute)csvDatasourceEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCSVDatasource_TreatTimestampAsMillis() {
        return (EAttribute)csvDatasourceEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCSVDatasource_TimeUnit() {
        return (EAttribute)csvDatasourceEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCSVDatasource_IsStartFrom1970() {
        return (EAttribute)csvDatasourceEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getColumnDescription() {
        return columnDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getColumnDescription_ColumnType() {
        return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getColumnDescription_TimeseriesId() {
        return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTimeseries() {
        return timeseriesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTimeseries_Id() {
        return (EAttribute)timeseriesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTimeseries_Datasource() {
        return (EReference)timeseriesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTimeseries_Name() {
        return (EAttribute)timeseriesEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getTimeseries_Type() {
        return (EAttribute)timeseriesEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTimeseries_Groups() {
        return (EReference)timeseriesEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGroup() {
        return groupEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGroup_Timeseries() {
        return (EReference)groupEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGroup_Name() {
        return (EAttribute)groupEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGroup_Id() {
        return (EAttribute)groupEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMediaDatasource() {
        return mediaDatasourceEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMediaTimeseries() {
        return mediaTimeseriesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMediaTimeseries_Filepath() {
        return (EAttribute)mediaTimeseriesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMediaTimeseries_StartTimestamp() {
        return (EAttribute)mediaTimeseriesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMediaTimeseries_PlaybackSpeed() {
        return (EAttribute)mediaTimeseriesEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getColumn() {
        return columnEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getTimeseriesType() {
        return timeseriesTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getMetaData() {
        return metaDataEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getUnit() {
        return unitEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getRdfToTimestampMapping() {
        return rdfToTimestampMappingEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getModel() {
        return modelEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MetaFactory getMetaFactory() {
        return (MetaFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        projectEClass = createEClass(PROJECT);
        createEReference(projectEClass, PROJECT__DATASOURCES);
        createEAttribute(projectEClass, PROJECT__IDENTIFIER);
        createEAttribute(projectEClass, PROJECT__LAST_TRACK_ID);
        createEAttribute(projectEClass, PROJECT__LAST_SOURCE_ID);
        createEReference(projectEClass, PROJECT__GROUPS);

        datasourceEClass = createEClass(DATASOURCE);
        createEReference(datasourceEClass, DATASOURCE__TIMESERIES);
        createEAttribute(datasourceEClass, DATASOURCE__ID);
        createEAttribute(datasourceEClass, DATASOURCE__NAME);
        createEAttribute(datasourceEClass, DATASOURCE__IS_IN_MEMORY);
        createEAttribute(datasourceEClass, DATASOURCE__METADATA);

        rdfDatasourceEClass = createEClass(RDF_DATASOURCE);
        createEAttribute(rdfDatasourceEClass, RDF_DATASOURCE__RESOLVE_URI);
        createEAttribute(rdfDatasourceEClass, RDF_DATASOURCE__FILE_NAME);
        createEAttribute(rdfDatasourceEClass, RDF_DATASOURCE__ROOT_RESOURCE_URI);
        createEAttribute(rdfDatasourceEClass, RDF_DATASOURCE__MODEL);
        createEAttribute(rdfDatasourceEClass, RDF_DATASOURCE__TIMESTAMP_FORMAT);

        rdfTimeseriesEClass = createEClass(RDF_TIMESERIES);
        createEAttribute(rdfTimeseriesEClass, RDF_TIMESERIES__VALUE_PATH);
        createEAttribute(rdfTimeseriesEClass, RDF_TIMESERIES__TIMESTAMP_PATH);
        createEAttribute(rdfTimeseriesEClass, RDF_TIMESERIES__IDENTIFIER_PATH);
        createEAttribute(rdfTimeseriesEClass, RDF_TIMESERIES__IDENTIFIER_VALUE);
        createEAttribute(rdfTimeseriesEClass, RDF_TIMESERIES__MAPPING);

        dbColumnDescriptionEClass = createEClass(DB_COLUMN_DESCRIPTION);
        createEAttribute(dbColumnDescriptionEClass, DB_COLUMN_DESCRIPTION__ATTRIBUTE_NAME);
        createEAttribute(dbColumnDescriptionEClass, DB_COLUMN_DESCRIPTION__TIMESERIES_ID);

        dbDatasourceEClass = createEClass(DB_DATASOURCE);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__LOGIN);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__PASSWORD);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__LOCATION);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__COVERED_RANGE_START);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__COVERED_RANGE_END);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__SID);
        createEReference(dbDatasourceEClass, DB_DATASOURCE__COLUMN_DESCRIPTIONS);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__TIMESTAMP_ATTRIBUTE_NAME);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__QUERY);
        createEAttribute(dbDatasourceEClass, DB_DATASOURCE__JDBC_DRIVER);

        csvDatasourceEClass = createEClass(CSV_DATASOURCE);
        createEAttribute(csvDatasourceEClass, CSV_DATASOURCE__ELEMENT_SEPARATOR);
        createEAttribute(csvDatasourceEClass, CSV_DATASOURCE__LINE_SEPARATOR);
        createEAttribute(csvDatasourceEClass, CSV_DATASOURCE__FILE_PATH);
        createEReference(csvDatasourceEClass, CSV_DATASOURCE__COLUMN_DESCRIPTIONS);
        createEAttribute(csvDatasourceEClass, CSV_DATASOURCE__TIMESTAMP_FORMAT);
        createEAttribute(csvDatasourceEClass, CSV_DATASOURCE__NO_OF_LINES_TO_SKIP);
        createEAttribute(csvDatasourceEClass, CSV_DATASOURCE__TREAT_TIMESTAMP_AS_MILLIS);
        createEAttribute(csvDatasourceEClass, CSV_DATASOURCE__TIME_UNIT);
        createEAttribute(csvDatasourceEClass, CSV_DATASOURCE__IS_START_FROM1970);

        columnDescriptionEClass = createEClass(COLUMN_DESCRIPTION);
        createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__COLUMN_TYPE);
        createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__TIMESERIES_ID);

        timeseriesEClass = createEClass(TIMESERIES);
        createEAttribute(timeseriesEClass, TIMESERIES__ID);
        createEReference(timeseriesEClass, TIMESERIES__DATASOURCE);
        createEAttribute(timeseriesEClass, TIMESERIES__NAME);
        createEAttribute(timeseriesEClass, TIMESERIES__TYPE);
        createEReference(timeseriesEClass, TIMESERIES__GROUPS);

        groupEClass = createEClass(GROUP);
        createEReference(groupEClass, GROUP__TIMESERIES);
        createEAttribute(groupEClass, GROUP__NAME);
        createEAttribute(groupEClass, GROUP__ID);

        mediaDatasourceEClass = createEClass(MEDIA_DATASOURCE);

        mediaTimeseriesEClass = createEClass(MEDIA_TIMESERIES);
        createEAttribute(mediaTimeseriesEClass, MEDIA_TIMESERIES__FILEPATH);
        createEAttribute(mediaTimeseriesEClass, MEDIA_TIMESERIES__START_TIMESTAMP);
        createEAttribute(mediaTimeseriesEClass, MEDIA_TIMESERIES__PLAYBACK_SPEED);

        // Create enums
        columnEEnum = createEEnum(COLUMN);
        timeseriesTypeEEnum = createEEnum(TIMESERIES_TYPE);

        // Create data types
        metaDataEDataType = createEDataType(META_DATA);
        unitEDataType = createEDataType(UNIT);
        rdfToTimestampMappingEDataType = createEDataType(RDF_TO_TIMESTAMP_MAPPING);
        modelEDataType = createEDataType(MODEL);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        rdfDatasourceEClass.getESuperTypes().add(this.getDatasource());
        rdfTimeseriesEClass.getESuperTypes().add(this.getTimeseries());
        dbDatasourceEClass.getESuperTypes().add(this.getDatasource());
        csvDatasourceEClass.getESuperTypes().add(this.getDatasource());
        mediaDatasourceEClass.getESuperTypes().add(this.getDatasource());
        mediaTimeseriesEClass.getESuperTypes().add(this.getTimeseries());

        // Initialize classes, features, and operations; add parameters
        initEClass(projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getProject_Datasources(), this.getDatasource(), null, "datasources", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getProject_Identifier(), ecorePackage.getEString(), "identifier", "\"\"", 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getProject_LastTrackId(), ecorePackage.getELong(), "lastTrackId", null, 1, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getProject_LastSourceId(), ecorePackage.getELong(), "lastSourceId", null, 1, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getProject_Groups(), this.getGroup(), null, "groups", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(datasourceEClass, Datasource.class, "Datasource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDatasource_Timeseries(), this.getTimeseries(), this.getTimeseries_Datasource(), "timeseries", null, 1, -1, Datasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDatasource_Id(), ecorePackage.getELong(), "id", null, 1, 1, Datasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDatasource_Name(), ecorePackage.getEString(), "name", null, 0, 1, Datasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDatasource_IsInMemory(), ecorePackage.getEBoolean(), "isInMemory", "false", 1, 1, Datasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDatasource_Metadata(), this.getMetaData(), "metadata", null, 0, 1, Datasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(rdfDatasourceEClass, RdfDatasource.class, "RdfDatasource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRdfDatasource_ResolveUri(), ecorePackage.getEString(), "resolveUri", "", 0, 1, RdfDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRdfDatasource_FileName(), ecorePackage.getEString(), "fileName", null, 0, 1, RdfDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRdfDatasource_RootResourceUri(), ecorePackage.getEString(), "rootResourceUri", null, 0, 1, RdfDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRdfDatasource_Model(), this.getModel(), "model", null, 0, 1, RdfDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRdfDatasource_TimestampFormat(), ecorePackage.getEString(), "timestampFormat", null, 0, 1, RdfDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(rdfTimeseriesEClass, RdfTimeseries.class, "RdfTimeseries", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getRdfTimeseries_ValuePath(), ecorePackage.getEString(), "valuePath", null, 0, -1, RdfTimeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRdfTimeseries_TimestampPath(), ecorePackage.getEString(), "timestampPath", null, 0, -1, RdfTimeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRdfTimeseries_IdentifierPath(), ecorePackage.getEString(), "identifierPath", null, 0, -1, RdfTimeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRdfTimeseries_IdentifierValue(), ecorePackage.getEString(), "identifierValue", "", 0, 1, RdfTimeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getRdfTimeseries_Mapping(), this.getRdfToTimestampMapping(), "mapping", "", 0, 1, RdfTimeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(dbColumnDescriptionEClass, DbColumnDescription.class, "DbColumnDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDbColumnDescription_AttributeName(), ecorePackage.getEString(), "attributeName", null, 0, 1, DbColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbColumnDescription_TimeseriesId(), ecorePackage.getELong(), "timeseriesId", null, 0, 1, DbColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(dbDatasourceEClass, DbDatasource.class, "DbDatasource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDbDatasource_Login(), ecorePackage.getEString(), "login", "", 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbDatasource_Password(), ecorePackage.getEString(), "password", null, 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbDatasource_Location(), ecorePackage.getEString(), "location", null, 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbDatasource_CoveredRangeStart(), ecorePackage.getELong(), "coveredRangeStart", null, 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbDatasource_CoveredRangeEnd(), ecorePackage.getELong(), "coveredRangeEnd", null, 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbDatasource_Sid(), ecorePackage.getEString(), "sid", null, 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDbDatasource_ColumnDescriptions(), this.getDbColumnDescription(), null, "columnDescriptions", null, 0, -1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbDatasource_TimestampAttributeName(), ecorePackage.getEString(), "timestampAttributeName", null, 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbDatasource_Query(), ecorePackage.getEString(), "query", null, 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDbDatasource_JdbcDriver(), ecorePackage.getEString(), "jdbcDriver", null, 0, 1, DbDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(csvDatasourceEClass, CSVDatasource.class, "CSVDatasource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCSVDatasource_ElementSeparator(), ecorePackage.getEChar(), "elementSeparator", null, 0, 1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCSVDatasource_LineSeparator(), ecorePackage.getEString(), "lineSeparator", null, 0, 1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCSVDatasource_FilePath(), ecorePackage.getEString(), "filePath", "\"\"", 0, 1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getCSVDatasource_ColumnDescriptions(), this.getColumnDescription(), null, "columnDescriptions", null, 0, -1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCSVDatasource_TimestampFormat(), ecorePackage.getEString(), "timestampFormat", null, 1, 1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCSVDatasource_NoOfLinesToSkip(), ecorePackage.getEInt(), "noOfLinesToSkip", null, 0, 1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCSVDatasource_TreatTimestampAsMillis(), ecorePackage.getEBoolean(), "treatTimestampAsMillis", null, 0, 1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCSVDatasource_TimeUnit(), this.getUnit(), "timeUnit", null, 0, 1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCSVDatasource_IsStartFrom1970(), ecorePackage.getEBoolean(), "isStartFrom1970", null, 0, 1, CSVDatasource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(columnDescriptionEClass, ColumnDescription.class, "ColumnDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getColumnDescription_ColumnType(), this.getColumn(), "columnType", null, 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getColumnDescription_TimeseriesId(), ecorePackage.getELong(), "timeseriesId", null, 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(timeseriesEClass, Timeseries.class, "Timeseries", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getTimeseries_Id(), ecorePackage.getELong(), "id", null, 0, 1, Timeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTimeseries_Datasource(), this.getDatasource(), this.getDatasource_Timeseries(), "datasource", null, 0, 1, Timeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTimeseries_Name(), ecorePackage.getEString(), "name", "", 0, 1, Timeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getTimeseries_Type(), this.getTimeseriesType(), "type", null, 0, 1, Timeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTimeseries_Groups(), this.getGroup(), this.getGroup_Timeseries(), "groups", null, 0, -1, Timeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(groupEClass, Group.class, "Group", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGroup_Timeseries(), this.getTimeseries(), this.getTimeseries_Groups(), "timeseries", null, 0, -1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGroup_Name(), ecorePackage.getEString(), "name", null, 1, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getGroup_Id(), ecorePackage.getELong(), "id", null, 1, 1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(mediaDatasourceEClass, MediaDatasource.class, "MediaDatasource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(mediaTimeseriesEClass, MediaTimeseries.class, "MediaTimeseries", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getMediaTimeseries_Filepath(), ecorePackage.getEString(), "filepath", null, 0, 1, MediaTimeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMediaTimeseries_StartTimestamp(), ecorePackage.getELong(), "startTimestamp", null, 0, 1, MediaTimeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getMediaTimeseries_PlaybackSpeed(), ecorePackage.getEDouble(), "playbackSpeed", null, 0, 1, MediaTimeseries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(columnEEnum, Column.class, "Column");
        addEEnumLiteral(columnEEnum, Column.ANNOTATION);
        addEEnumLiteral(columnEEnum, Column.SENSOR_DATA);
        addEEnumLiteral(columnEEnum, Column.TIMESTAMP);

        initEEnum(timeseriesTypeEEnum, TimeseriesType.class, "TimeseriesType");
        addEEnumLiteral(timeseriesTypeEEnum, TimeseriesType.ANNOTATION);
        addEEnumLiteral(timeseriesTypeEEnum, TimeseriesType.SENSOR);

        // Initialize data types
        initEDataType(metaDataEDataType, Map.class, "MetaData", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.Map<String, String>");
        initEDataType(unitEDataType, Unit.class, "Unit", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(rdfToTimestampMappingEDataType, Map.class, "RdfToTimestampMapping", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.Map<Object, Object>");
        initEDataType(modelEDataType, Model.class, "Model", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} //MetaPackageImpl
