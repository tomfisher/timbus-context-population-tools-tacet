/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.DbColumnDescription;
import edu.teco.tacet.meta.DbDatasource;
import edu.teco.tacet.meta.MetaPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Db Datasource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getLogin <em>Login</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getCoveredRangeStart <em>Covered Range Start</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getCoveredRangeEnd <em>Covered Range End</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getSid <em>Sid</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getColumnDescriptions <em>Column Descriptions</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getTimestampAttributeName <em>Timestamp Attribute Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getQuery <em>Query</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbDatasourceImpl#getJdbcDriver <em>Jdbc Driver</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DbDatasourceImpl extends DatasourceImpl implements DbDatasource {
    /**
     * The default value of the '{@link #getLogin() <em>Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLogin()
     * @generated
     * @ordered
     */
    protected static final String LOGIN_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getLogin() <em>Login</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLogin()
     * @generated
     * @ordered
     */
    protected String login = LOGIN_EDEFAULT;

    /**
     * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPassword()
     * @generated
     * @ordered
     */
    protected static final String PASSWORD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPassword()
     * @generated
     * @ordered
     */
    protected String password = PASSWORD_EDEFAULT;

    /**
     * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected static final String LOCATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected String location = LOCATION_EDEFAULT;

    /**
     * The default value of the '{@link #getCoveredRangeStart() <em>Covered Range Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCoveredRangeStart()
     * @generated
     * @ordered
     */
    protected static final long COVERED_RANGE_START_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getCoveredRangeStart() <em>Covered Range Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCoveredRangeStart()
     * @generated
     * @ordered
     */
    protected long coveredRangeStart = COVERED_RANGE_START_EDEFAULT;

    /**
     * The default value of the '{@link #getCoveredRangeEnd() <em>Covered Range End</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCoveredRangeEnd()
     * @generated
     * @ordered
     */
    protected static final long COVERED_RANGE_END_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getCoveredRangeEnd() <em>Covered Range End</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCoveredRangeEnd()
     * @generated
     * @ordered
     */
    protected long coveredRangeEnd = COVERED_RANGE_END_EDEFAULT;

    /**
     * The default value of the '{@link #getSid() <em>Sid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSid()
     * @generated
     * @ordered
     */
    protected static final String SID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSid() <em>Sid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSid()
     * @generated
     * @ordered
     */
    protected String sid = SID_EDEFAULT;

    /**
     * The cached value of the '{@link #getColumnDescriptions() <em>Column Descriptions</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnDescriptions()
     * @generated
     * @ordered
     */
    protected EList<DbColumnDescription> columnDescriptions;

    /**
     * The default value of the '{@link #getTimestampAttributeName() <em>Timestamp Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimestampAttributeName()
     * @generated
     * @ordered
     */
    protected static final String TIMESTAMP_ATTRIBUTE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTimestampAttributeName() <em>Timestamp Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimestampAttributeName()
     * @generated
     * @ordered
     */
    protected String timestampAttributeName = TIMESTAMP_ATTRIBUTE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getQuery() <em>Query</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQuery()
     * @generated
     * @ordered
     */
    protected static final String QUERY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getQuery() <em>Query</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQuery()
     * @generated
     * @ordered
     */
    protected String query = QUERY_EDEFAULT;

    /**
     * The default value of the '{@link #getJdbcDriver() <em>Jdbc Driver</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJdbcDriver()
     * @generated
     * @ordered
     */
    protected static final String JDBC_DRIVER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getJdbcDriver() <em>Jdbc Driver</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJdbcDriver()
     * @generated
     * @ordered
     */
    protected String jdbcDriver = JDBC_DRIVER_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DbDatasourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.DB_DATASOURCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLogin() {
        return login;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLogin(String newLogin) {
        String oldLogin = login;
        login = newLogin;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__LOGIN, oldLogin, login));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPassword(String newPassword) {
        String oldPassword = password;
        password = newPassword;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__PASSWORD, oldPassword, password));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLocation(String newLocation) {
        String oldLocation = location;
        location = newLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__LOCATION, oldLocation, location));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getCoveredRangeStart() {
        return coveredRangeStart;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCoveredRangeStart(long newCoveredRangeStart) {
        long oldCoveredRangeStart = coveredRangeStart;
        coveredRangeStart = newCoveredRangeStart;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__COVERED_RANGE_START, oldCoveredRangeStart, coveredRangeStart));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getCoveredRangeEnd() {
        return coveredRangeEnd;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCoveredRangeEnd(long newCoveredRangeEnd) {
        long oldCoveredRangeEnd = coveredRangeEnd;
        coveredRangeEnd = newCoveredRangeEnd;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__COVERED_RANGE_END, oldCoveredRangeEnd, coveredRangeEnd));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getSid() {
        return sid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSid(String newSid) {
        String oldSid = sid;
        sid = newSid;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__SID, oldSid, sid));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DbColumnDescription> getColumnDescriptions() {
        if (columnDescriptions == null) {
            columnDescriptions = new EObjectResolvingEList<DbColumnDescription>(DbColumnDescription.class, this, MetaPackage.DB_DATASOURCE__COLUMN_DESCRIPTIONS);
        }
        return columnDescriptions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTimestampAttributeName() {
        return timestampAttributeName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimestampAttributeName(String newTimestampAttributeName) {
        String oldTimestampAttributeName = timestampAttributeName;
        timestampAttributeName = newTimestampAttributeName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__TIMESTAMP_ATTRIBUTE_NAME, oldTimestampAttributeName, timestampAttributeName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getQuery() {
        return query;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setQuery(String newQuery) {
        String oldQuery = query;
        query = newQuery;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__QUERY, oldQuery, query));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getJdbcDriver() {
        return jdbcDriver;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJdbcDriver(String newJdbcDriver) {
        String oldJdbcDriver = jdbcDriver;
        jdbcDriver = newJdbcDriver;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_DATASOURCE__JDBC_DRIVER, oldJdbcDriver, jdbcDriver));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MetaPackage.DB_DATASOURCE__LOGIN:
                return getLogin();
            case MetaPackage.DB_DATASOURCE__PASSWORD:
                return getPassword();
            case MetaPackage.DB_DATASOURCE__LOCATION:
                return getLocation();
            case MetaPackage.DB_DATASOURCE__COVERED_RANGE_START:
                return getCoveredRangeStart();
            case MetaPackage.DB_DATASOURCE__COVERED_RANGE_END:
                return getCoveredRangeEnd();
            case MetaPackage.DB_DATASOURCE__SID:
                return getSid();
            case MetaPackage.DB_DATASOURCE__COLUMN_DESCRIPTIONS:
                return getColumnDescriptions();
            case MetaPackage.DB_DATASOURCE__TIMESTAMP_ATTRIBUTE_NAME:
                return getTimestampAttributeName();
            case MetaPackage.DB_DATASOURCE__QUERY:
                return getQuery();
            case MetaPackage.DB_DATASOURCE__JDBC_DRIVER:
                return getJdbcDriver();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case MetaPackage.DB_DATASOURCE__LOGIN:
                setLogin((String)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__PASSWORD:
                setPassword((String)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__LOCATION:
                setLocation((String)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__COVERED_RANGE_START:
                setCoveredRangeStart((Long)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__COVERED_RANGE_END:
                setCoveredRangeEnd((Long)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__SID:
                setSid((String)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__COLUMN_DESCRIPTIONS:
                getColumnDescriptions().clear();
                getColumnDescriptions().addAll((Collection<? extends DbColumnDescription>)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__TIMESTAMP_ATTRIBUTE_NAME:
                setTimestampAttributeName((String)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__QUERY:
                setQuery((String)newValue);
                return;
            case MetaPackage.DB_DATASOURCE__JDBC_DRIVER:
                setJdbcDriver((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case MetaPackage.DB_DATASOURCE__LOGIN:
                setLogin(LOGIN_EDEFAULT);
                return;
            case MetaPackage.DB_DATASOURCE__PASSWORD:
                setPassword(PASSWORD_EDEFAULT);
                return;
            case MetaPackage.DB_DATASOURCE__LOCATION:
                setLocation(LOCATION_EDEFAULT);
                return;
            case MetaPackage.DB_DATASOURCE__COVERED_RANGE_START:
                setCoveredRangeStart(COVERED_RANGE_START_EDEFAULT);
                return;
            case MetaPackage.DB_DATASOURCE__COVERED_RANGE_END:
                setCoveredRangeEnd(COVERED_RANGE_END_EDEFAULT);
                return;
            case MetaPackage.DB_DATASOURCE__SID:
                setSid(SID_EDEFAULT);
                return;
            case MetaPackage.DB_DATASOURCE__COLUMN_DESCRIPTIONS:
                getColumnDescriptions().clear();
                return;
            case MetaPackage.DB_DATASOURCE__TIMESTAMP_ATTRIBUTE_NAME:
                setTimestampAttributeName(TIMESTAMP_ATTRIBUTE_NAME_EDEFAULT);
                return;
            case MetaPackage.DB_DATASOURCE__QUERY:
                setQuery(QUERY_EDEFAULT);
                return;
            case MetaPackage.DB_DATASOURCE__JDBC_DRIVER:
                setJdbcDriver(JDBC_DRIVER_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case MetaPackage.DB_DATASOURCE__LOGIN:
                return LOGIN_EDEFAULT == null ? login != null : !LOGIN_EDEFAULT.equals(login);
            case MetaPackage.DB_DATASOURCE__PASSWORD:
                return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
            case MetaPackage.DB_DATASOURCE__LOCATION:
                return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
            case MetaPackage.DB_DATASOURCE__COVERED_RANGE_START:
                return coveredRangeStart != COVERED_RANGE_START_EDEFAULT;
            case MetaPackage.DB_DATASOURCE__COVERED_RANGE_END:
                return coveredRangeEnd != COVERED_RANGE_END_EDEFAULT;
            case MetaPackage.DB_DATASOURCE__SID:
                return SID_EDEFAULT == null ? sid != null : !SID_EDEFAULT.equals(sid);
            case MetaPackage.DB_DATASOURCE__COLUMN_DESCRIPTIONS:
                return columnDescriptions != null && !columnDescriptions.isEmpty();
            case MetaPackage.DB_DATASOURCE__TIMESTAMP_ATTRIBUTE_NAME:
                return TIMESTAMP_ATTRIBUTE_NAME_EDEFAULT == null ? timestampAttributeName != null : !TIMESTAMP_ATTRIBUTE_NAME_EDEFAULT.equals(timestampAttributeName);
            case MetaPackage.DB_DATASOURCE__QUERY:
                return QUERY_EDEFAULT == null ? query != null : !QUERY_EDEFAULT.equals(query);
            case MetaPackage.DB_DATASOURCE__JDBC_DRIVER:
                return JDBC_DRIVER_EDEFAULT == null ? jdbcDriver != null : !JDBC_DRIVER_EDEFAULT.equals(jdbcDriver);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (login: ");
        result.append(login);
        result.append(", password: ");
        result.append(password);
        result.append(", location: ");
        result.append(location);
        result.append(", coveredRangeStart: ");
        result.append(coveredRangeStart);
        result.append(", coveredRangeEnd: ");
        result.append(coveredRangeEnd);
        result.append(", sid: ");
        result.append(sid);
        result.append(", timestampAttributeName: ");
        result.append(timestampAttributeName);
        result.append(", query: ");
        result.append(query);
        result.append(", jdbcDriver: ");
        result.append(jdbcDriver);
        result.append(')');
        return result.toString();
    }

} //DbDatasourceImpl
