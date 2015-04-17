/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.MetaPackage;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Timeseries</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.TimeseriesImpl#getId <em>Id</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.TimeseriesImpl#getDatasource <em>Datasource</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.TimeseriesImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.TimeseriesImpl#getType <em>Type</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.TimeseriesImpl#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TimeseriesImpl extends MinimalEObjectImpl.Container implements Timeseries {
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
    protected static final String NAME_EDEFAULT = "";

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
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final TimeseriesType TYPE_EDEFAULT = TimeseriesType.ANNOTATION;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected TimeseriesType type = TYPE_EDEFAULT;

    /**
     * The cached value of the '{@link #getGroups() <em>Groups</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroups()
     * @generated
     * @ordered
     */
    protected EList<Group> groups;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TimeseriesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.TIMESERIES;
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
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.TIMESERIES__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Datasource getDatasource() {
        if (eContainerFeatureID() != MetaPackage.TIMESERIES__DATASOURCE) return null;
        return (Datasource)eInternalContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDatasource(Datasource newDatasource, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newDatasource, MetaPackage.TIMESERIES__DATASOURCE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDatasource(Datasource newDatasource) {
        if (newDatasource != eInternalContainer() || (eContainerFeatureID() != MetaPackage.TIMESERIES__DATASOURCE && newDatasource != null)) {
            if (EcoreUtil.isAncestor(this, newDatasource))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newDatasource != null)
                msgs = ((InternalEObject)newDatasource).eInverseAdd(this, MetaPackage.DATASOURCE__TIMESERIES, Datasource.class, msgs);
            msgs = basicSetDatasource(newDatasource, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.TIMESERIES__DATASOURCE, newDatasource, newDatasource));
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
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.TIMESERIES__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeseriesType getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(TimeseriesType newType) {
        TimeseriesType oldType = type;
        type = newType == null ? TYPE_EDEFAULT : newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.TIMESERIES__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Group> getGroups() {
        if (groups == null) {
            groups = new EObjectWithInverseResolvingEList.ManyInverse<Group>(Group.class, this, MetaPackage.TIMESERIES__GROUPS, MetaPackage.GROUP__TIMESERIES);
        }
        return groups;
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
            case MetaPackage.TIMESERIES__DATASOURCE:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetDatasource((Datasource)otherEnd, msgs);
            case MetaPackage.TIMESERIES__GROUPS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getGroups()).basicAdd(otherEnd, msgs);
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
            case MetaPackage.TIMESERIES__DATASOURCE:
                return basicSetDatasource(null, msgs);
            case MetaPackage.TIMESERIES__GROUPS:
                return ((InternalEList<?>)getGroups()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
        switch (eContainerFeatureID()) {
            case MetaPackage.TIMESERIES__DATASOURCE:
                return eInternalContainer().eInverseRemove(this, MetaPackage.DATASOURCE__TIMESERIES, Datasource.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MetaPackage.TIMESERIES__ID:
                return getId();
            case MetaPackage.TIMESERIES__DATASOURCE:
                return getDatasource();
            case MetaPackage.TIMESERIES__NAME:
                return getName();
            case MetaPackage.TIMESERIES__TYPE:
                return getType();
            case MetaPackage.TIMESERIES__GROUPS:
                return getGroups();
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
            case MetaPackage.TIMESERIES__ID:
                setId((Long)newValue);
                return;
            case MetaPackage.TIMESERIES__DATASOURCE:
                setDatasource((Datasource)newValue);
                return;
            case MetaPackage.TIMESERIES__NAME:
                setName((String)newValue);
                return;
            case MetaPackage.TIMESERIES__TYPE:
                setType((TimeseriesType)newValue);
                return;
            case MetaPackage.TIMESERIES__GROUPS:
                getGroups().clear();
                getGroups().addAll((Collection<? extends Group>)newValue);
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
            case MetaPackage.TIMESERIES__ID:
                setId(ID_EDEFAULT);
                return;
            case MetaPackage.TIMESERIES__DATASOURCE:
                setDatasource((Datasource)null);
                return;
            case MetaPackage.TIMESERIES__NAME:
                setName(NAME_EDEFAULT);
                return;
            case MetaPackage.TIMESERIES__TYPE:
                setType(TYPE_EDEFAULT);
                return;
            case MetaPackage.TIMESERIES__GROUPS:
                getGroups().clear();
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
            case MetaPackage.TIMESERIES__ID:
                return id != ID_EDEFAULT;
            case MetaPackage.TIMESERIES__DATASOURCE:
                return getDatasource() != null;
            case MetaPackage.TIMESERIES__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case MetaPackage.TIMESERIES__TYPE:
                return type != TYPE_EDEFAULT;
            case MetaPackage.TIMESERIES__GROUPS:
                return groups != null && !groups.isEmpty();
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
        result.append(", type: ");
        result.append(type);
        result.append(')');
        return result.toString();
    }

} //TimeseriesImpl
