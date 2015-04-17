/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.MetaPackage;
import edu.teco.tacet.meta.Timeseries;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Datasource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.DatasourceImpl#getTimeseries <em>Timeseries</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DatasourceImpl#getId <em>Id</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DatasourceImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DatasourceImpl#isIsInMemory <em>Is In Memory</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.DatasourceImpl#getMetadata <em>Metadata</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DatasourceImpl extends MinimalEObjectImpl.Container implements Datasource {
    /**
     * The cached value of the '{@link #getTimeseries() <em>Timeseries</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeseries()
     * @generated
     * @ordered
     */
    protected EList<Timeseries> timeseries;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final long ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected long id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #isIsInMemory() <em>Is In Memory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsInMemory()
     * @generated
     * @ordered
     */
    protected static final boolean IS_IN_MEMORY_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsInMemory() <em>Is In Memory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsInMemory()
     * @generated
     * @ordered
     */
    protected boolean isInMemory = IS_IN_MEMORY_EDEFAULT;

    /**
     * The default value of the '{@link #getMetadata() <em>Metadata</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMetadata()
     * @generated
     * @ordered
     */
    protected static final Map<Object, Object> METADATA_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMetadata() <em>Metadata</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMetadata()
     * @generated
     * @ordered
     */
    protected Map<Object, Object> metadata = METADATA_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DatasourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.DATASOURCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Timeseries> getTimeseries() {
        if (timeseries == null) {
            timeseries = new EObjectContainmentWithInverseEList<Timeseries>(Timeseries.class, this, MetaPackage.DATASOURCE__TIMESERIES, MetaPackage.TIMESERIES__DATASOURCE);
        }
        return timeseries;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(long newId) {
        long oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DATASOURCE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DATASOURCE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsInMemory() {
        return isInMemory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIsInMemory(boolean newIsInMemory) {
        boolean oldIsInMemory = isInMemory;
        isInMemory = newIsInMemory;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DATASOURCE__IS_IN_MEMORY, oldIsInMemory, isInMemory));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Map<Object, Object> getMetadata() {
        return metadata;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMetadata(Map<Object, Object> newMetadata) {
        Map<Object, Object> oldMetadata = metadata;
        metadata = newMetadata;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.DATASOURCE__METADATA, oldMetadata, metadata));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case MetaPackage.DATASOURCE__TIMESERIES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getTimeseries()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case MetaPackage.DATASOURCE__TIMESERIES:
                return ((InternalEList<?>)getTimeseries()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MetaPackage.DATASOURCE__TIMESERIES:
                return getTimeseries();
            case MetaPackage.DATASOURCE__ID:
                return getId();
            case MetaPackage.DATASOURCE__NAME:
                return getName();
            case MetaPackage.DATASOURCE__IS_IN_MEMORY:
                return isIsInMemory();
            case MetaPackage.DATASOURCE__METADATA:
                return getMetadata();
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
            case MetaPackage.DATASOURCE__TIMESERIES:
                getTimeseries().clear();
                getTimeseries().addAll((Collection<? extends Timeseries>)newValue);
                return;
            case MetaPackage.DATASOURCE__ID:
                setId((Long)newValue);
                return;
            case MetaPackage.DATASOURCE__NAME:
                setName((String)newValue);
                return;
            case MetaPackage.DATASOURCE__IS_IN_MEMORY:
                setIsInMemory((Boolean)newValue);
                return;
            case MetaPackage.DATASOURCE__METADATA:
                setMetadata((Map<Object, Object>)newValue);
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
            case MetaPackage.DATASOURCE__TIMESERIES:
                getTimeseries().clear();
                return;
            case MetaPackage.DATASOURCE__ID:
                setId(ID_EDEFAULT);
                return;
            case MetaPackage.DATASOURCE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case MetaPackage.DATASOURCE__IS_IN_MEMORY:
                setIsInMemory(IS_IN_MEMORY_EDEFAULT);
                return;
            case MetaPackage.DATASOURCE__METADATA:
                setMetadata(METADATA_EDEFAULT);
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
            case MetaPackage.DATASOURCE__TIMESERIES:
                return timeseries != null && !timeseries.isEmpty();
            case MetaPackage.DATASOURCE__ID:
                return id != ID_EDEFAULT;
            case MetaPackage.DATASOURCE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case MetaPackage.DATASOURCE__IS_IN_MEMORY:
                return isInMemory != IS_IN_MEMORY_EDEFAULT;
            case MetaPackage.DATASOURCE__METADATA:
                return METADATA_EDEFAULT == null ? metadata != null : !METADATA_EDEFAULT.equals(metadata);
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
        result.append(" (id: ");
        result.append(id);
        result.append(", name: ");
        result.append(name);
        result.append(", isInMemory: ");
        result.append(isInMemory);
        result.append(", metadata: ");
        result.append(metadata);
        result.append(')');
        return result.toString();
    }

} //DatasourceImpl
