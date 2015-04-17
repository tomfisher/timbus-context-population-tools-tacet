/**
 */
package edu.teco.tacet.meta;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Db Datasource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getLogin <em>Login</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getPassword <em>Password</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getLocation <em>Location</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getCoveredRangeStart <em>Covered Range Start</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getCoveredRangeEnd <em>Covered Range End</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getSid <em>Sid</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getColumnDescriptions <em>Column Descriptions</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getTimestampAttributeName <em>Timestamp Attribute Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getQuery <em>Query</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.DbDatasource#getJdbcDriver <em>Jdbc Driver</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource()
 * @model
 * @generated
 */
public interface DbDatasource extends Datasource {
    /**
     * Returns the value of the '<em><b>Login</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Login</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Login</em>' attribute.
     * @see #setLogin(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_Login()
     * @model default=""
     * @generated
     */
    String getLogin();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getLogin <em>Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Login</em>' attribute.
     * @see #getLogin()
     * @generated
     */
    void setLogin(String value);

    /**
     * Returns the value of the '<em><b>Password</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Password</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Password</em>' attribute.
     * @see #setPassword(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_Password()
     * @model
     * @generated
     */
    String getPassword();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getPassword <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Password</em>' attribute.
     * @see #getPassword()
     * @generated
     */
    void setPassword(String value);

    /**
     * Returns the value of the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Location</em>' attribute.
     * @see #setLocation(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_Location()
     * @model
     * @generated
     */
    String getLocation();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getLocation <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location</em>' attribute.
     * @see #getLocation()
     * @generated
     */
    void setLocation(String value);

    /**
     * Returns the value of the '<em><b>Covered Range Start</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Covered Range Start</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Covered Range Start</em>' attribute.
     * @see #setCoveredRangeStart(long)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_CoveredRangeStart()
     * @model
     * @generated
     */
    long getCoveredRangeStart();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getCoveredRangeStart <em>Covered Range Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Covered Range Start</em>' attribute.
     * @see #getCoveredRangeStart()
     * @generated
     */
    void setCoveredRangeStart(long value);

    /**
     * Returns the value of the '<em><b>Covered Range End</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Covered Range End</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Covered Range End</em>' attribute.
     * @see #setCoveredRangeEnd(long)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_CoveredRangeEnd()
     * @model
     * @generated
     */
    long getCoveredRangeEnd();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getCoveredRangeEnd <em>Covered Range End</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Covered Range End</em>' attribute.
     * @see #getCoveredRangeEnd()
     * @generated
     */
    void setCoveredRangeEnd(long value);

    /**
     * Returns the value of the '<em><b>Sid</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sid</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sid</em>' attribute.
     * @see #setSid(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_Sid()
     * @model
     * @generated
     */
    String getSid();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getSid <em>Sid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Sid</em>' attribute.
     * @see #getSid()
     * @generated
     */
    void setSid(String value);

    /**
     * Returns the value of the '<em><b>Column Descriptions</b></em>' reference list.
     * The list contents are of type {@link edu.teco.tacet.meta.DbColumnDescription}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Column Descriptions</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Column Descriptions</em>' reference list.
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_ColumnDescriptions()
     * @model
     * @generated
     */
    EList<DbColumnDescription> getColumnDescriptions();

    /**
     * Returns the value of the '<em><b>Timestamp Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Timestamp Attribute Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Timestamp Attribute Name</em>' attribute.
     * @see #setTimestampAttributeName(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_TimestampAttributeName()
     * @model
     * @generated
     */
    String getTimestampAttributeName();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getTimestampAttributeName <em>Timestamp Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Timestamp Attribute Name</em>' attribute.
     * @see #getTimestampAttributeName()
     * @generated
     */
    void setTimestampAttributeName(String value);

    /**
     * Returns the value of the '<em><b>Query</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Query</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Query</em>' attribute.
     * @see #setQuery(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_Query()
     * @model
     * @generated
     */
    String getQuery();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getQuery <em>Query</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Query</em>' attribute.
     * @see #getQuery()
     * @generated
     */
    void setQuery(String value);

    /**
     * Returns the value of the '<em><b>Jdbc Driver</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Jdbc Driver</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Jdbc Driver</em>' attribute.
     * @see #setJdbcDriver(String)
     * @see edu.teco.tacet.meta.MetaPackage#getDbDatasource_JdbcDriver()
     * @model
     * @generated
     */
    String getJdbcDriver();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.DbDatasource#getJdbcDriver <em>Jdbc Driver</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Jdbc Driver</em>' attribute.
     * @see #getJdbcDriver()
     * @generated
     */
    void setJdbcDriver(String value);

} // DbDatasource
