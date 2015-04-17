/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.Column;
import edu.teco.tacet.meta.ColumnDescription;
import edu.teco.tacet.meta.MetaPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Column Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.ColumnDescriptionImpl#getColumnType <em>Column Type</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.ColumnDescriptionImpl#getTimeseriesId <em>Timeseries Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ColumnDescriptionImpl extends MinimalEObjectImpl.Container implements ColumnDescription {
    /**
     * The default value of the '{@link #getColumnType() <em>Column Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnType()
     * @generated
     * @ordered
     */
    protected static final Column COLUMN_TYPE_EDEFAULT = Column.ANNOTATION;

    /**
     * The cached value of the '{@link #getColumnType() <em>Column Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnType()
     * @generated
     * @ordered
     */
    protected Column columnType = COLUMN_TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #getTimeseriesId() <em>Timeseries Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeseriesId()
     * @generated
     * @ordered
     */
    protected static final long TIMESERIES_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getTimeseriesId() <em>Timeseries Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeseriesId()
     * @generated
     * @ordered
     */
    protected long timeseriesId = TIMESERIES_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ColumnDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.COLUMN_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Column getColumnType() {
        return columnType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setColumnType(Column newColumnType) {
        Column oldColumnType = columnType;
        columnType = newColumnType == null ? COLUMN_TYPE_EDEFAULT : newColumnType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.COLUMN_DESCRIPTION__COLUMN_TYPE, oldColumnType, columnType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getTimeseriesId() {
        return timeseriesId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimeseriesId(long newTimeseriesId) {
        long oldTimeseriesId = timeseriesId;
        timeseriesId = newTimeseriesId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.COLUMN_DESCRIPTION__TIMESERIES_ID, oldTimeseriesId, timeseriesId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MetaPackage.COLUMN_DESCRIPTION__COLUMN_TYPE:
                return getColumnType();
            case MetaPackage.COLUMN_DESCRIPTION__TIMESERIES_ID:
                return getTimeseriesId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case MetaPackage.COLUMN_DESCRIPTION__COLUMN_TYPE:
                setColumnType((Column)newValue);
                return;
            case MetaPackage.COLUMN_DESCRIPTION__TIMESERIES_ID:
                setTimeseriesId((Long)newValue);
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
            case MetaPackage.COLUMN_DESCRIPTION__COLUMN_TYPE:
                setColumnType(COLUMN_TYPE_EDEFAULT);
                return;
            case MetaPackage.COLUMN_DESCRIPTION__TIMESERIES_ID:
                setTimeseriesId(TIMESERIES_ID_EDEFAULT);
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
            case MetaPackage.COLUMN_DESCRIPTION__COLUMN_TYPE:
                return columnType != COLUMN_TYPE_EDEFAULT;
            case MetaPackage.COLUMN_DESCRIPTION__TIMESERIES_ID:
                return timeseriesId != TIMESERIES_ID_EDEFAULT;
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
        result.append(" (columnType: ");
        result.append(columnType);
        result.append(", timeseriesId: ");
        result.append(timeseriesId);
        result.append(')');
        return result.toString();
    }

} //ColumnDescriptionImpl
