/**
 */
package edu.teco.tacet.meta;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.teco.tacet.meta.MetaFactory
 * @model kind="package"
 * @generated
 */
public interface MetaPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "meta";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://tacet.teco.edu";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "edu.teco.tacet.meta";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    MetaPackage eINSTANCE = edu.teco.tacet.meta.impl.MetaPackageImpl.init();

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.ProjectImpl <em>Project</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.ProjectImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getProject()
     * @generated
     */
    int PROJECT = 0;

    /**
     * The feature id for the '<em><b>Datasources</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT__DATASOURCES = 0;

    /**
     * The feature id for the '<em><b>Identifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT__IDENTIFIER = 1;

    /**
     * The feature id for the '<em><b>Last Track Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT__LAST_TRACK_ID = 2;

    /**
     * The feature id for the '<em><b>Last Source Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT__LAST_SOURCE_ID = 3;

    /**
     * The feature id for the '<em><b>Groups</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT__GROUPS = 4;

    /**
     * The number of structural features of the '<em>Project</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Project</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.DatasourceImpl <em>Datasource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.DatasourceImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getDatasource()
     * @generated
     */
    int DATASOURCE = 1;

    /**
     * The feature id for the '<em><b>Timeseries</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATASOURCE__TIMESERIES = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATASOURCE__ID = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATASOURCE__NAME = 2;

    /**
     * The feature id for the '<em><b>Is In Memory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATASOURCE__IS_IN_MEMORY = 3;

    /**
     * The feature id for the '<em><b>Metadata</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATASOURCE__METADATA = 4;

    /**
     * The number of structural features of the '<em>Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATASOURCE_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATASOURCE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.RdfDatasourceImpl <em>Rdf Datasource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.RdfDatasourceImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getRdfDatasource()
     * @generated
     */
    int RDF_DATASOURCE = 2;

    /**
     * The feature id for the '<em><b>Timeseries</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__TIMESERIES = DATASOURCE__TIMESERIES;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__ID = DATASOURCE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__NAME = DATASOURCE__NAME;

    /**
     * The feature id for the '<em><b>Is In Memory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__IS_IN_MEMORY = DATASOURCE__IS_IN_MEMORY;

    /**
     * The feature id for the '<em><b>Metadata</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__METADATA = DATASOURCE__METADATA;

    /**
     * The feature id for the '<em><b>Resolve Uri</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__RESOLVE_URI = DATASOURCE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>File Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__FILE_NAME = DATASOURCE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Root Resource Uri</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__ROOT_RESOURCE_URI = DATASOURCE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__MODEL = DATASOURCE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Timestamp Format</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE__TIMESTAMP_FORMAT = DATASOURCE_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Rdf Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE_FEATURE_COUNT = DATASOURCE_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Rdf Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_DATASOURCE_OPERATION_COUNT = DATASOURCE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.TimeseriesImpl <em>Timeseries</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.TimeseriesImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getTimeseries()
     * @generated
     */
    int TIMESERIES = 8;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIMESERIES__ID = 0;

    /**
     * The feature id for the '<em><b>Datasource</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIMESERIES__DATASOURCE = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIMESERIES__NAME = 2;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIMESERIES__TYPE = 3;

    /**
     * The feature id for the '<em><b>Groups</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIMESERIES__GROUPS = 4;

    /**
     * The number of structural features of the '<em>Timeseries</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIMESERIES_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Timeseries</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIMESERIES_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.RdfTimeseriesImpl <em>Rdf Timeseries</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.RdfTimeseriesImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getRdfTimeseries()
     * @generated
     */
    int RDF_TIMESERIES = 3;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__ID = TIMESERIES__ID;

    /**
     * The feature id for the '<em><b>Datasource</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__DATASOURCE = TIMESERIES__DATASOURCE;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__NAME = TIMESERIES__NAME;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__TYPE = TIMESERIES__TYPE;

    /**
     * The feature id for the '<em><b>Groups</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__GROUPS = TIMESERIES__GROUPS;

    /**
     * The feature id for the '<em><b>Value Path</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__VALUE_PATH = TIMESERIES_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Timestamp Path</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__TIMESTAMP_PATH = TIMESERIES_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Identifier Path</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__IDENTIFIER_PATH = TIMESERIES_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Identifier Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__IDENTIFIER_VALUE = TIMESERIES_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Mapping</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES__MAPPING = TIMESERIES_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Rdf Timeseries</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES_FEATURE_COUNT = TIMESERIES_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Rdf Timeseries</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int RDF_TIMESERIES_OPERATION_COUNT = TIMESERIES_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.DbColumnDescriptionImpl <em>Db Column Description</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.DbColumnDescriptionImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getDbColumnDescription()
     * @generated
     */
    int DB_COLUMN_DESCRIPTION = 4;

    /**
     * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_COLUMN_DESCRIPTION__ATTRIBUTE_NAME = 0;

    /**
     * The feature id for the '<em><b>Timeseries Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_COLUMN_DESCRIPTION__TIMESERIES_ID = 1;

    /**
     * The number of structural features of the '<em>Db Column Description</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_COLUMN_DESCRIPTION_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Db Column Description</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_COLUMN_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.DbDatasourceImpl <em>Db Datasource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.DbDatasourceImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getDbDatasource()
     * @generated
     */
    int DB_DATASOURCE = 5;

    /**
     * The feature id for the '<em><b>Timeseries</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__TIMESERIES = DATASOURCE__TIMESERIES;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__ID = DATASOURCE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__NAME = DATASOURCE__NAME;

    /**
     * The feature id for the '<em><b>Is In Memory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__IS_IN_MEMORY = DATASOURCE__IS_IN_MEMORY;

    /**
     * The feature id for the '<em><b>Metadata</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__METADATA = DATASOURCE__METADATA;

    /**
     * The feature id for the '<em><b>Login</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__LOGIN = DATASOURCE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Password</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__PASSWORD = DATASOURCE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__LOCATION = DATASOURCE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Covered Range Start</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__COVERED_RANGE_START = DATASOURCE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Covered Range End</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__COVERED_RANGE_END = DATASOURCE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Sid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__SID = DATASOURCE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Column Descriptions</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__COLUMN_DESCRIPTIONS = DATASOURCE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Timestamp Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__TIMESTAMP_ATTRIBUTE_NAME = DATASOURCE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Query</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__QUERY = DATASOURCE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Jdbc Driver</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE__JDBC_DRIVER = DATASOURCE_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Db Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE_FEATURE_COUNT = DATASOURCE_FEATURE_COUNT + 10;

    /**
     * The number of operations of the '<em>Db Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DB_DATASOURCE_OPERATION_COUNT = DATASOURCE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl <em>CSV Datasource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.CSVDatasourceImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getCSVDatasource()
     * @generated
     */
    int CSV_DATASOURCE = 6;

    /**
     * The feature id for the '<em><b>Timeseries</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__TIMESERIES = DATASOURCE__TIMESERIES;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__ID = DATASOURCE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__NAME = DATASOURCE__NAME;

    /**
     * The feature id for the '<em><b>Is In Memory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__IS_IN_MEMORY = DATASOURCE__IS_IN_MEMORY;

    /**
     * The feature id for the '<em><b>Metadata</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__METADATA = DATASOURCE__METADATA;

    /**
     * The feature id for the '<em><b>Element Separator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__ELEMENT_SEPARATOR = DATASOURCE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Line Separator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__LINE_SEPARATOR = DATASOURCE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>File Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__FILE_PATH = DATASOURCE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Column Descriptions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__COLUMN_DESCRIPTIONS = DATASOURCE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Timestamp Format</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__TIMESTAMP_FORMAT = DATASOURCE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>No Of Lines To Skip</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__NO_OF_LINES_TO_SKIP = DATASOURCE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Treat Timestamp As Millis</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__TREAT_TIMESTAMP_AS_MILLIS = DATASOURCE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Time Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__TIME_UNIT = DATASOURCE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Is Start From1970</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE__IS_START_FROM1970 = DATASOURCE_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>CSV Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE_FEATURE_COUNT = DATASOURCE_FEATURE_COUNT + 9;

    /**
     * The number of operations of the '<em>CSV Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CSV_DATASOURCE_OPERATION_COUNT = DATASOURCE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.ColumnDescriptionImpl <em>Column Description</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.ColumnDescriptionImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getColumnDescription()
     * @generated
     */
    int COLUMN_DESCRIPTION = 7;

    /**
     * The feature id for the '<em><b>Column Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__COLUMN_TYPE = 0;

    /**
     * The feature id for the '<em><b>Timeseries Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__TIMESERIES_ID = 1;

    /**
     * The number of structural features of the '<em>Column Description</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Column Description</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.GroupImpl <em>Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.GroupImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getGroup()
     * @generated
     */
    int GROUP = 9;

    /**
     * The feature id for the '<em><b>Timeseries</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__TIMESERIES = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__NAME = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__ID = 2;

    /**
     * The number of structural features of the '<em>Group</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Group</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.MediaDatasourceImpl <em>Media Datasource</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.MediaDatasourceImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getMediaDatasource()
     * @generated
     */
    int MEDIA_DATASOURCE = 10;

    /**
     * The feature id for the '<em><b>Timeseries</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_DATASOURCE__TIMESERIES = DATASOURCE__TIMESERIES;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_DATASOURCE__ID = DATASOURCE__ID;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_DATASOURCE__NAME = DATASOURCE__NAME;

    /**
     * The feature id for the '<em><b>Is In Memory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_DATASOURCE__IS_IN_MEMORY = DATASOURCE__IS_IN_MEMORY;

    /**
     * The feature id for the '<em><b>Metadata</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_DATASOURCE__METADATA = DATASOURCE__METADATA;

    /**
     * The number of structural features of the '<em>Media Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_DATASOURCE_FEATURE_COUNT = DATASOURCE_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Media Datasource</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_DATASOURCE_OPERATION_COUNT = DATASOURCE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.impl.MediaTimeseriesImpl <em>Media Timeseries</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.impl.MediaTimeseriesImpl
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getMediaTimeseries()
     * @generated
     */
    int MEDIA_TIMESERIES = 11;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES__ID = TIMESERIES__ID;

    /**
     * The feature id for the '<em><b>Datasource</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES__DATASOURCE = TIMESERIES__DATASOURCE;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES__NAME = TIMESERIES__NAME;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES__TYPE = TIMESERIES__TYPE;

    /**
     * The feature id for the '<em><b>Groups</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES__GROUPS = TIMESERIES__GROUPS;

    /**
     * The feature id for the '<em><b>Filepath</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES__FILEPATH = TIMESERIES_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Start Timestamp</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES__START_TIMESTAMP = TIMESERIES_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Playback Speed</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES__PLAYBACK_SPEED = TIMESERIES_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Media Timeseries</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES_FEATURE_COUNT = TIMESERIES_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Media Timeseries</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MEDIA_TIMESERIES_OPERATION_COUNT = TIMESERIES_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.Column <em>Column</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.Column
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getColumn()
     * @generated
     */
    int COLUMN = 12;

    /**
     * The meta object id for the '{@link edu.teco.tacet.meta.TimeseriesType <em>Timeseries Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.TimeseriesType
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getTimeseriesType()
     * @generated
     */
    int TIMESERIES_TYPE = 13;

    /**
     * The meta object id for the '<em>Data</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.util.Map
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getMetaData()
     * @generated
     */
    int META_DATA = 14;

    /**
     * The meta object id for the '<em>Unit</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.teco.tacet.meta.nongen.Unit
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getUnit()
     * @generated
     */
    int UNIT = 15;

    /**
     * The meta object id for the '<em>Rdf To Timestamp Mapping</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.util.Map
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getRdfToTimestampMapping()
     * @generated
     */
    int RDF_TO_TIMESTAMP_MAPPING = 16;

    /**
     * The meta object id for the '<em>Model</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.hp.hpl.jena.rdf.model.Model
     * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getModel()
     * @generated
     */
    int MODEL = 17;


    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.Project <em>Project</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Project</em>'.
     * @see edu.teco.tacet.meta.Project
     * @generated
     */
    EClass getProject();

    /**
     * Returns the meta object for the containment reference list '{@link edu.teco.tacet.meta.Project#getDatasources <em>Datasources</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Datasources</em>'.
     * @see edu.teco.tacet.meta.Project#getDatasources()
     * @see #getProject()
     * @generated
     */
    EReference getProject_Datasources();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Project#getIdentifier <em>Identifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Identifier</em>'.
     * @see edu.teco.tacet.meta.Project#getIdentifier()
     * @see #getProject()
     * @generated
     */
    EAttribute getProject_Identifier();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Project#getLastTrackId <em>Last Track Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Last Track Id</em>'.
     * @see edu.teco.tacet.meta.Project#getLastTrackId()
     * @see #getProject()
     * @generated
     */
    EAttribute getProject_LastTrackId();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Project#getLastSourceId <em>Last Source Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Last Source Id</em>'.
     * @see edu.teco.tacet.meta.Project#getLastSourceId()
     * @see #getProject()
     * @generated
     */
    EAttribute getProject_LastSourceId();

    /**
     * Returns the meta object for the reference list '{@link edu.teco.tacet.meta.Project#getGroups <em>Groups</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Groups</em>'.
     * @see edu.teco.tacet.meta.Project#getGroups()
     * @see #getProject()
     * @generated
     */
    EReference getProject_Groups();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.Datasource <em>Datasource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Datasource</em>'.
     * @see edu.teco.tacet.meta.Datasource
     * @generated
     */
    EClass getDatasource();

    /**
     * Returns the meta object for the containment reference list '{@link edu.teco.tacet.meta.Datasource#getTimeseries <em>Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Timeseries</em>'.
     * @see edu.teco.tacet.meta.Datasource#getTimeseries()
     * @see #getDatasource()
     * @generated
     */
    EReference getDatasource_Timeseries();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Datasource#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see edu.teco.tacet.meta.Datasource#getId()
     * @see #getDatasource()
     * @generated
     */
    EAttribute getDatasource_Id();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Datasource#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see edu.teco.tacet.meta.Datasource#getName()
     * @see #getDatasource()
     * @generated
     */
    EAttribute getDatasource_Name();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Datasource#isIsInMemory <em>Is In Memory</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is In Memory</em>'.
     * @see edu.teco.tacet.meta.Datasource#isIsInMemory()
     * @see #getDatasource()
     * @generated
     */
    EAttribute getDatasource_IsInMemory();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Datasource#getMetadata <em>Metadata</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Metadata</em>'.
     * @see edu.teco.tacet.meta.Datasource#getMetadata()
     * @see #getDatasource()
     * @generated
     */
    EAttribute getDatasource_Metadata();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.RdfDatasource <em>Rdf Datasource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Rdf Datasource</em>'.
     * @see edu.teco.tacet.meta.RdfDatasource
     * @generated
     */
    EClass getRdfDatasource();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.RdfDatasource#getResolveUri <em>Resolve Uri</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Resolve Uri</em>'.
     * @see edu.teco.tacet.meta.RdfDatasource#getResolveUri()
     * @see #getRdfDatasource()
     * @generated
     */
    EAttribute getRdfDatasource_ResolveUri();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.RdfDatasource#getFileName <em>File Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>File Name</em>'.
     * @see edu.teco.tacet.meta.RdfDatasource#getFileName()
     * @see #getRdfDatasource()
     * @generated
     */
    EAttribute getRdfDatasource_FileName();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.RdfDatasource#getRootResourceUri <em>Root Resource Uri</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Root Resource Uri</em>'.
     * @see edu.teco.tacet.meta.RdfDatasource#getRootResourceUri()
     * @see #getRdfDatasource()
     * @generated
     */
    EAttribute getRdfDatasource_RootResourceUri();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.RdfDatasource#getModel <em>Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Model</em>'.
     * @see edu.teco.tacet.meta.RdfDatasource#getModel()
     * @see #getRdfDatasource()
     * @generated
     */
    EAttribute getRdfDatasource_Model();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.RdfDatasource#getTimestampFormat <em>Timestamp Format</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timestamp Format</em>'.
     * @see edu.teco.tacet.meta.RdfDatasource#getTimestampFormat()
     * @see #getRdfDatasource()
     * @generated
     */
    EAttribute getRdfDatasource_TimestampFormat();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.RdfTimeseries <em>Rdf Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Rdf Timeseries</em>'.
     * @see edu.teco.tacet.meta.RdfTimeseries
     * @generated
     */
    EClass getRdfTimeseries();

    /**
     * Returns the meta object for the attribute list '{@link edu.teco.tacet.meta.RdfTimeseries#getValuePath <em>Value Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Value Path</em>'.
     * @see edu.teco.tacet.meta.RdfTimeseries#getValuePath()
     * @see #getRdfTimeseries()
     * @generated
     */
    EAttribute getRdfTimeseries_ValuePath();

    /**
     * Returns the meta object for the attribute list '{@link edu.teco.tacet.meta.RdfTimeseries#getTimestampPath <em>Timestamp Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Timestamp Path</em>'.
     * @see edu.teco.tacet.meta.RdfTimeseries#getTimestampPath()
     * @see #getRdfTimeseries()
     * @generated
     */
    EAttribute getRdfTimeseries_TimestampPath();

    /**
     * Returns the meta object for the attribute list '{@link edu.teco.tacet.meta.RdfTimeseries#getIdentifierPath <em>Identifier Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Identifier Path</em>'.
     * @see edu.teco.tacet.meta.RdfTimeseries#getIdentifierPath()
     * @see #getRdfTimeseries()
     * @generated
     */
    EAttribute getRdfTimeseries_IdentifierPath();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.RdfTimeseries#getIdentifierValue <em>Identifier Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Identifier Value</em>'.
     * @see edu.teco.tacet.meta.RdfTimeseries#getIdentifierValue()
     * @see #getRdfTimeseries()
     * @generated
     */
    EAttribute getRdfTimeseries_IdentifierValue();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.RdfTimeseries#getMapping <em>Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Mapping</em>'.
     * @see edu.teco.tacet.meta.RdfTimeseries#getMapping()
     * @see #getRdfTimeseries()
     * @generated
     */
    EAttribute getRdfTimeseries_Mapping();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.DbColumnDescription <em>Db Column Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Db Column Description</em>'.
     * @see edu.teco.tacet.meta.DbColumnDescription
     * @generated
     */
    EClass getDbColumnDescription();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbColumnDescription#getAttributeName <em>Attribute Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Name</em>'.
     * @see edu.teco.tacet.meta.DbColumnDescription#getAttributeName()
     * @see #getDbColumnDescription()
     * @generated
     */
    EAttribute getDbColumnDescription_AttributeName();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbColumnDescription#getTimeseriesId <em>Timeseries Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timeseries Id</em>'.
     * @see edu.teco.tacet.meta.DbColumnDescription#getTimeseriesId()
     * @see #getDbColumnDescription()
     * @generated
     */
    EAttribute getDbColumnDescription_TimeseriesId();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.DbDatasource <em>Db Datasource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Db Datasource</em>'.
     * @see edu.teco.tacet.meta.DbDatasource
     * @generated
     */
    EClass getDbDatasource();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getLogin <em>Login</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Login</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getLogin()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_Login();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getPassword <em>Password</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Password</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getPassword()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_Password();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Location</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getLocation()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_Location();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getCoveredRangeStart <em>Covered Range Start</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Covered Range Start</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getCoveredRangeStart()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_CoveredRangeStart();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getCoveredRangeEnd <em>Covered Range End</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Covered Range End</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getCoveredRangeEnd()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_CoveredRangeEnd();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getSid <em>Sid</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Sid</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getSid()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_Sid();

    /**
     * Returns the meta object for the reference list '{@link edu.teco.tacet.meta.DbDatasource#getColumnDescriptions <em>Column Descriptions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Column Descriptions</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getColumnDescriptions()
     * @see #getDbDatasource()
     * @generated
     */
    EReference getDbDatasource_ColumnDescriptions();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getTimestampAttributeName <em>Timestamp Attribute Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timestamp Attribute Name</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getTimestampAttributeName()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_TimestampAttributeName();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getQuery <em>Query</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Query</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getQuery()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_Query();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.DbDatasource#getJdbcDriver <em>Jdbc Driver</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Jdbc Driver</em>'.
     * @see edu.teco.tacet.meta.DbDatasource#getJdbcDriver()
     * @see #getDbDatasource()
     * @generated
     */
    EAttribute getDbDatasource_JdbcDriver();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.CSVDatasource <em>CSV Datasource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>CSV Datasource</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource
     * @generated
     */
    EClass getCSVDatasource();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.CSVDatasource#getElementSeparator <em>Element Separator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Element Separator</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#getElementSeparator()
     * @see #getCSVDatasource()
     * @generated
     */
    EAttribute getCSVDatasource_ElementSeparator();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.CSVDatasource#getLineSeparator <em>Line Separator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Line Separator</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#getLineSeparator()
     * @see #getCSVDatasource()
     * @generated
     */
    EAttribute getCSVDatasource_LineSeparator();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.CSVDatasource#getFilePath <em>File Path</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>File Path</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#getFilePath()
     * @see #getCSVDatasource()
     * @generated
     */
    EAttribute getCSVDatasource_FilePath();

    /**
     * Returns the meta object for the containment reference list '{@link edu.teco.tacet.meta.CSVDatasource#getColumnDescriptions <em>Column Descriptions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Column Descriptions</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#getColumnDescriptions()
     * @see #getCSVDatasource()
     * @generated
     */
    EReference getCSVDatasource_ColumnDescriptions();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.CSVDatasource#getTimestampFormat <em>Timestamp Format</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timestamp Format</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#getTimestampFormat()
     * @see #getCSVDatasource()
     * @generated
     */
    EAttribute getCSVDatasource_TimestampFormat();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.CSVDatasource#getNoOfLinesToSkip <em>No Of Lines To Skip</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>No Of Lines To Skip</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#getNoOfLinesToSkip()
     * @see #getCSVDatasource()
     * @generated
     */
    EAttribute getCSVDatasource_NoOfLinesToSkip();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.CSVDatasource#isTreatTimestampAsMillis <em>Treat Timestamp As Millis</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Treat Timestamp As Millis</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#isTreatTimestampAsMillis()
     * @see #getCSVDatasource()
     * @generated
     */
    EAttribute getCSVDatasource_TreatTimestampAsMillis();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.CSVDatasource#getTimeUnit <em>Time Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Time Unit</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#getTimeUnit()
     * @see #getCSVDatasource()
     * @generated
     */
    EAttribute getCSVDatasource_TimeUnit();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.CSVDatasource#isIsStartFrom1970 <em>Is Start From1970</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Start From1970</em>'.
     * @see edu.teco.tacet.meta.CSVDatasource#isIsStartFrom1970()
     * @see #getCSVDatasource()
     * @generated
     */
    EAttribute getCSVDatasource_IsStartFrom1970();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.ColumnDescription <em>Column Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Column Description</em>'.
     * @see edu.teco.tacet.meta.ColumnDescription
     * @generated
     */
    EClass getColumnDescription();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.ColumnDescription#getColumnType <em>Column Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Column Type</em>'.
     * @see edu.teco.tacet.meta.ColumnDescription#getColumnType()
     * @see #getColumnDescription()
     * @generated
     */
    EAttribute getColumnDescription_ColumnType();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.ColumnDescription#getTimeseriesId <em>Timeseries Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timeseries Id</em>'.
     * @see edu.teco.tacet.meta.ColumnDescription#getTimeseriesId()
     * @see #getColumnDescription()
     * @generated
     */
    EAttribute getColumnDescription_TimeseriesId();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.Timeseries <em>Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Timeseries</em>'.
     * @see edu.teco.tacet.meta.Timeseries
     * @generated
     */
    EClass getTimeseries();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Timeseries#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see edu.teco.tacet.meta.Timeseries#getId()
     * @see #getTimeseries()
     * @generated
     */
    EAttribute getTimeseries_Id();

    /**
     * Returns the meta object for the container reference '{@link edu.teco.tacet.meta.Timeseries#getDatasource <em>Datasource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Datasource</em>'.
     * @see edu.teco.tacet.meta.Timeseries#getDatasource()
     * @see #getTimeseries()
     * @generated
     */
    EReference getTimeseries_Datasource();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Timeseries#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see edu.teco.tacet.meta.Timeseries#getName()
     * @see #getTimeseries()
     * @generated
     */
    EAttribute getTimeseries_Name();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Timeseries#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see edu.teco.tacet.meta.Timeseries#getType()
     * @see #getTimeseries()
     * @generated
     */
    EAttribute getTimeseries_Type();

    /**
     * Returns the meta object for the reference list '{@link edu.teco.tacet.meta.Timeseries#getGroups <em>Groups</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Groups</em>'.
     * @see edu.teco.tacet.meta.Timeseries#getGroups()
     * @see #getTimeseries()
     * @generated
     */
    EReference getTimeseries_Groups();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.Group <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Group</em>'.
     * @see edu.teco.tacet.meta.Group
     * @generated
     */
    EClass getGroup();

    /**
     * Returns the meta object for the reference list '{@link edu.teco.tacet.meta.Group#getTimeseries <em>Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Timeseries</em>'.
     * @see edu.teco.tacet.meta.Group#getTimeseries()
     * @see #getGroup()
     * @generated
     */
    EReference getGroup_Timeseries();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Group#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see edu.teco.tacet.meta.Group#getName()
     * @see #getGroup()
     * @generated
     */
    EAttribute getGroup_Name();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.Group#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see edu.teco.tacet.meta.Group#getId()
     * @see #getGroup()
     * @generated
     */
    EAttribute getGroup_Id();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.MediaDatasource <em>Media Datasource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Media Datasource</em>'.
     * @see edu.teco.tacet.meta.MediaDatasource
     * @generated
     */
    EClass getMediaDatasource();

    /**
     * Returns the meta object for class '{@link edu.teco.tacet.meta.MediaTimeseries <em>Media Timeseries</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Media Timeseries</em>'.
     * @see edu.teco.tacet.meta.MediaTimeseries
     * @generated
     */
    EClass getMediaTimeseries();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.MediaTimeseries#getFilepath <em>Filepath</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Filepath</em>'.
     * @see edu.teco.tacet.meta.MediaTimeseries#getFilepath()
     * @see #getMediaTimeseries()
     * @generated
     */
    EAttribute getMediaTimeseries_Filepath();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.MediaTimeseries#getStartTimestamp <em>Start Timestamp</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Start Timestamp</em>'.
     * @see edu.teco.tacet.meta.MediaTimeseries#getStartTimestamp()
     * @see #getMediaTimeseries()
     * @generated
     */
    EAttribute getMediaTimeseries_StartTimestamp();

    /**
     * Returns the meta object for the attribute '{@link edu.teco.tacet.meta.MediaTimeseries#getPlaybackSpeed <em>Playback Speed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Playback Speed</em>'.
     * @see edu.teco.tacet.meta.MediaTimeseries#getPlaybackSpeed()
     * @see #getMediaTimeseries()
     * @generated
     */
    EAttribute getMediaTimeseries_PlaybackSpeed();

    /**
     * Returns the meta object for enum '{@link edu.teco.tacet.meta.Column <em>Column</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Column</em>'.
     * @see edu.teco.tacet.meta.Column
     * @generated
     */
    EEnum getColumn();

    /**
     * Returns the meta object for enum '{@link edu.teco.tacet.meta.TimeseriesType <em>Timeseries Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Timeseries Type</em>'.
     * @see edu.teco.tacet.meta.TimeseriesType
     * @generated
     */
    EEnum getTimeseriesType();

    /**
     * Returns the meta object for data type '{@link java.util.Map <em>Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Data</em>'.
     * @see java.util.Map
     * @model instanceClass="java.util.Map<java.lang.Object, java.lang.Object>"
     * @generated
     */
    EDataType getMetaData();

    /**
     * Returns the meta object for data type '{@link edu.teco.tacet.meta.nongen.Unit <em>Unit</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Unit</em>'.
     * @see edu.teco.tacet.meta.nongen.Unit
     * @model instanceClass="edu.teco.tacet.meta.nongen.Unit"
     * @generated
     */
    EDataType getUnit();

    /**
     * Returns the meta object for data type '{@link java.util.Map <em>Rdf To Timestamp Mapping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Rdf To Timestamp Mapping</em>'.
     * @see java.util.Map
     * @model instanceClass="java.util.Map<java.lang.Object, java.lang.Object>"
     * @generated
     */
    EDataType getRdfToTimestampMapping();

    /**
     * Returns the meta object for data type '{@link com.hp.hpl.jena.rdf.model.Model <em>Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Model</em>'.
     * @see com.hp.hpl.jena.rdf.model.Model
     * @model instanceClass="com.hp.hpl.jena.rdf.model.Model"
     * @generated
     */
    EDataType getModel();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    MetaFactory getMetaFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.ProjectImpl <em>Project</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.ProjectImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getProject()
         * @generated
         */
        EClass PROJECT = eINSTANCE.getProject();

        /**
         * The meta object literal for the '<em><b>Datasources</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROJECT__DATASOURCES = eINSTANCE.getProject_Datasources();

        /**
         * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROJECT__IDENTIFIER = eINSTANCE.getProject_Identifier();

        /**
         * The meta object literal for the '<em><b>Last Track Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROJECT__LAST_TRACK_ID = eINSTANCE.getProject_LastTrackId();

        /**
         * The meta object literal for the '<em><b>Last Source Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROJECT__LAST_SOURCE_ID = eINSTANCE.getProject_LastSourceId();

        /**
         * The meta object literal for the '<em><b>Groups</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROJECT__GROUPS = eINSTANCE.getProject_Groups();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.DatasourceImpl <em>Datasource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.DatasourceImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getDatasource()
         * @generated
         */
        EClass DATASOURCE = eINSTANCE.getDatasource();

        /**
         * The meta object literal for the '<em><b>Timeseries</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DATASOURCE__TIMESERIES = eINSTANCE.getDatasource_Timeseries();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATASOURCE__ID = eINSTANCE.getDatasource_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATASOURCE__NAME = eINSTANCE.getDatasource_Name();

        /**
         * The meta object literal for the '<em><b>Is In Memory</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATASOURCE__IS_IN_MEMORY = eINSTANCE.getDatasource_IsInMemory();

        /**
         * The meta object literal for the '<em><b>Metadata</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DATASOURCE__METADATA = eINSTANCE.getDatasource_Metadata();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.RdfDatasourceImpl <em>Rdf Datasource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.RdfDatasourceImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getRdfDatasource()
         * @generated
         */
        EClass RDF_DATASOURCE = eINSTANCE.getRdfDatasource();

        /**
         * The meta object literal for the '<em><b>Resolve Uri</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_DATASOURCE__RESOLVE_URI = eINSTANCE.getRdfDatasource_ResolveUri();

        /**
         * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_DATASOURCE__FILE_NAME = eINSTANCE.getRdfDatasource_FileName();

        /**
         * The meta object literal for the '<em><b>Root Resource Uri</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_DATASOURCE__ROOT_RESOURCE_URI = eINSTANCE.getRdfDatasource_RootResourceUri();

        /**
         * The meta object literal for the '<em><b>Model</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_DATASOURCE__MODEL = eINSTANCE.getRdfDatasource_Model();

        /**
         * The meta object literal for the '<em><b>Timestamp Format</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_DATASOURCE__TIMESTAMP_FORMAT = eINSTANCE.getRdfDatasource_TimestampFormat();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.RdfTimeseriesImpl <em>Rdf Timeseries</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.RdfTimeseriesImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getRdfTimeseries()
         * @generated
         */
        EClass RDF_TIMESERIES = eINSTANCE.getRdfTimeseries();

        /**
         * The meta object literal for the '<em><b>Value Path</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_TIMESERIES__VALUE_PATH = eINSTANCE.getRdfTimeseries_ValuePath();

        /**
         * The meta object literal for the '<em><b>Timestamp Path</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_TIMESERIES__TIMESTAMP_PATH = eINSTANCE.getRdfTimeseries_TimestampPath();

        /**
         * The meta object literal for the '<em><b>Identifier Path</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_TIMESERIES__IDENTIFIER_PATH = eINSTANCE.getRdfTimeseries_IdentifierPath();

        /**
         * The meta object literal for the '<em><b>Identifier Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_TIMESERIES__IDENTIFIER_VALUE = eINSTANCE.getRdfTimeseries_IdentifierValue();

        /**
         * The meta object literal for the '<em><b>Mapping</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute RDF_TIMESERIES__MAPPING = eINSTANCE.getRdfTimeseries_Mapping();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.DbColumnDescriptionImpl <em>Db Column Description</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.DbColumnDescriptionImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getDbColumnDescription()
         * @generated
         */
        EClass DB_COLUMN_DESCRIPTION = eINSTANCE.getDbColumnDescription();

        /**
         * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_COLUMN_DESCRIPTION__ATTRIBUTE_NAME = eINSTANCE.getDbColumnDescription_AttributeName();

        /**
         * The meta object literal for the '<em><b>Timeseries Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_COLUMN_DESCRIPTION__TIMESERIES_ID = eINSTANCE.getDbColumnDescription_TimeseriesId();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.DbDatasourceImpl <em>Db Datasource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.DbDatasourceImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getDbDatasource()
         * @generated
         */
        EClass DB_DATASOURCE = eINSTANCE.getDbDatasource();

        /**
         * The meta object literal for the '<em><b>Login</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__LOGIN = eINSTANCE.getDbDatasource_Login();

        /**
         * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__PASSWORD = eINSTANCE.getDbDatasource_Password();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__LOCATION = eINSTANCE.getDbDatasource_Location();

        /**
         * The meta object literal for the '<em><b>Covered Range Start</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__COVERED_RANGE_START = eINSTANCE.getDbDatasource_CoveredRangeStart();

        /**
         * The meta object literal for the '<em><b>Covered Range End</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__COVERED_RANGE_END = eINSTANCE.getDbDatasource_CoveredRangeEnd();

        /**
         * The meta object literal for the '<em><b>Sid</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__SID = eINSTANCE.getDbDatasource_Sid();

        /**
         * The meta object literal for the '<em><b>Column Descriptions</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DB_DATASOURCE__COLUMN_DESCRIPTIONS = eINSTANCE.getDbDatasource_ColumnDescriptions();

        /**
         * The meta object literal for the '<em><b>Timestamp Attribute Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__TIMESTAMP_ATTRIBUTE_NAME = eINSTANCE.getDbDatasource_TimestampAttributeName();

        /**
         * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__QUERY = eINSTANCE.getDbDatasource_Query();

        /**
         * The meta object literal for the '<em><b>Jdbc Driver</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DB_DATASOURCE__JDBC_DRIVER = eINSTANCE.getDbDatasource_JdbcDriver();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl <em>CSV Datasource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.CSVDatasourceImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getCSVDatasource()
         * @generated
         */
        EClass CSV_DATASOURCE = eINSTANCE.getCSVDatasource();

        /**
         * The meta object literal for the '<em><b>Element Separator</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CSV_DATASOURCE__ELEMENT_SEPARATOR = eINSTANCE.getCSVDatasource_ElementSeparator();

        /**
         * The meta object literal for the '<em><b>Line Separator</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CSV_DATASOURCE__LINE_SEPARATOR = eINSTANCE.getCSVDatasource_LineSeparator();

        /**
         * The meta object literal for the '<em><b>File Path</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CSV_DATASOURCE__FILE_PATH = eINSTANCE.getCSVDatasource_FilePath();

        /**
         * The meta object literal for the '<em><b>Column Descriptions</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CSV_DATASOURCE__COLUMN_DESCRIPTIONS = eINSTANCE.getCSVDatasource_ColumnDescriptions();

        /**
         * The meta object literal for the '<em><b>Timestamp Format</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CSV_DATASOURCE__TIMESTAMP_FORMAT = eINSTANCE.getCSVDatasource_TimestampFormat();

        /**
         * The meta object literal for the '<em><b>No Of Lines To Skip</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CSV_DATASOURCE__NO_OF_LINES_TO_SKIP = eINSTANCE.getCSVDatasource_NoOfLinesToSkip();

        /**
         * The meta object literal for the '<em><b>Treat Timestamp As Millis</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CSV_DATASOURCE__TREAT_TIMESTAMP_AS_MILLIS = eINSTANCE.getCSVDatasource_TreatTimestampAsMillis();

        /**
         * The meta object literal for the '<em><b>Time Unit</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CSV_DATASOURCE__TIME_UNIT = eINSTANCE.getCSVDatasource_TimeUnit();

        /**
         * The meta object literal for the '<em><b>Is Start From1970</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CSV_DATASOURCE__IS_START_FROM1970 = eINSTANCE.getCSVDatasource_IsStartFrom1970();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.ColumnDescriptionImpl <em>Column Description</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.ColumnDescriptionImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getColumnDescription()
         * @generated
         */
        EClass COLUMN_DESCRIPTION = eINSTANCE.getColumnDescription();

        /**
         * The meta object literal for the '<em><b>Column Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__COLUMN_TYPE = eINSTANCE.getColumnDescription_ColumnType();

        /**
         * The meta object literal for the '<em><b>Timeseries Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__TIMESERIES_ID = eINSTANCE.getColumnDescription_TimeseriesId();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.TimeseriesImpl <em>Timeseries</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.TimeseriesImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getTimeseries()
         * @generated
         */
        EClass TIMESERIES = eINSTANCE.getTimeseries();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TIMESERIES__ID = eINSTANCE.getTimeseries_Id();

        /**
         * The meta object literal for the '<em><b>Datasource</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TIMESERIES__DATASOURCE = eINSTANCE.getTimeseries_Datasource();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TIMESERIES__NAME = eINSTANCE.getTimeseries_Name();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TIMESERIES__TYPE = eINSTANCE.getTimeseries_Type();

        /**
         * The meta object literal for the '<em><b>Groups</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TIMESERIES__GROUPS = eINSTANCE.getTimeseries_Groups();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.GroupImpl <em>Group</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.GroupImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getGroup()
         * @generated
         */
        EClass GROUP = eINSTANCE.getGroup();

        /**
         * The meta object literal for the '<em><b>Timeseries</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GROUP__TIMESERIES = eINSTANCE.getGroup_Timeseries();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GROUP__NAME = eINSTANCE.getGroup_Name();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute GROUP__ID = eINSTANCE.getGroup_Id();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.MediaDatasourceImpl <em>Media Datasource</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.MediaDatasourceImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getMediaDatasource()
         * @generated
         */
        EClass MEDIA_DATASOURCE = eINSTANCE.getMediaDatasource();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.impl.MediaTimeseriesImpl <em>Media Timeseries</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.impl.MediaTimeseriesImpl
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getMediaTimeseries()
         * @generated
         */
        EClass MEDIA_TIMESERIES = eINSTANCE.getMediaTimeseries();

        /**
         * The meta object literal for the '<em><b>Filepath</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MEDIA_TIMESERIES__FILEPATH = eINSTANCE.getMediaTimeseries_Filepath();

        /**
         * The meta object literal for the '<em><b>Start Timestamp</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MEDIA_TIMESERIES__START_TIMESTAMP = eINSTANCE.getMediaTimeseries_StartTimestamp();

        /**
         * The meta object literal for the '<em><b>Playback Speed</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MEDIA_TIMESERIES__PLAYBACK_SPEED = eINSTANCE.getMediaTimeseries_PlaybackSpeed();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.Column <em>Column</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.Column
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getColumn()
         * @generated
         */
        EEnum COLUMN = eINSTANCE.getColumn();

        /**
         * The meta object literal for the '{@link edu.teco.tacet.meta.TimeseriesType <em>Timeseries Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.TimeseriesType
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getTimeseriesType()
         * @generated
         */
        EEnum TIMESERIES_TYPE = eINSTANCE.getTimeseriesType();

        /**
         * The meta object literal for the '<em>Data</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.util.Map
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getMetaData()
         * @generated
         */
        EDataType META_DATA = eINSTANCE.getMetaData();

        /**
         * The meta object literal for the '<em>Unit</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see edu.teco.tacet.meta.nongen.Unit
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getUnit()
         * @generated
         */
        EDataType UNIT = eINSTANCE.getUnit();

        /**
         * The meta object literal for the '<em>Rdf To Timestamp Mapping</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.util.Map
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getRdfToTimestampMapping()
         * @generated
         */
        EDataType RDF_TO_TIMESTAMP_MAPPING = eINSTANCE.getRdfToTimestampMapping();

        /**
         * The meta object literal for the '<em>Model</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.hp.hpl.jena.rdf.model.Model
         * @see edu.teco.tacet.meta.impl.MetaPackageImpl#getModel()
         * @generated
         */
        EDataType MODEL = eINSTANCE.getModel();

    }

} //MetaPackage
