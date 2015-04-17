/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.DbColumnDescription;
import edu.teco.tacet.meta.MetaPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Db Column Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.DbColumnDescriptionImpl#getAttributeName <em>Attribute Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DbColumnDescriptionImpl#getTimeseriesId <em>Timeseries Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DbColumnDescriptionImpl extends MinimalEObjectImpl.Container implements DbColumnDescription {
    /**
     * The default value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeName()
     * @generated
     * @ordered
     */
    protected static final String ATTRIBUTE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeName()
     * @generated
     * @ordered
     */
    protected String attributeName = ATTRIBUTE_NAME_EDEFAULT;

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
    protected DbColumnDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.DB_COLUMN_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeName(String newAttributeName) {
        String oldAttributeName = attributeName;
        attributeName = newAttributeName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_COLUMN_DESCRIPTION__ATTRIBUTE_NAME, oldAttributeName, attributeName));
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
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DB_COLUMN_DESCRIPTION__TIMESERIES_ID, oldTimeseriesId, timeseriesId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MetaPackage.DB_COLUMN_DESCRIPTION__ATTRIBUTE_NAME:
                return getAttributeName();
            case MetaPackage.DB_COLUMN_DESCRIPTION__TIMESERIES_ID:
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
            case MetaPackage.DB_COLUMN_DESCRIPTION__ATTRIBUTE_NAME:
                setAttributeName((String)newValue);
                return;
            case MetaPackage.DB_COLUMN_DESCRIPTION__TIMESERIES_ID:
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
            case MetaPackage.DB_COLUMN_DESCRIPTION__ATTRIBUTE_NAME:
                setAttributeName(ATTRIBUTE_NAME_EDEFAULT);
                return;
            case MetaPackage.DB_COLUMN_DESCRIPTION__TIMESERIES_ID:
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
            case MetaPackage.DB_COLUMN_DESCRIPTION__ATTRIBUTE_NAME:
                return ATTRIBUTE_NAME_EDEFAULT == null ? attributeName != null : !ATTRIBUTE_NAME_EDEFAULT.equals(attributeName);
            case MetaPackage.DB_COLUMN_DESCRIPTION__TIMESERIES_ID:
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
        result.append(" (attributeName: ");
        result.append(attributeName);
        result.append(", timeseriesId: ");
        result.append(timeseriesId);
        result.append(')');
        return result.toString();
    }

} //DbColumnDescriptionImpl
