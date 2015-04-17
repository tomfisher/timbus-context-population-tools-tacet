/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.MetaPackage;
import edu.teco.tacet.meta.Project;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.ProjectImpl#getDatasources <em>Datasources</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.ProjectImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.ProjectImpl#getLastTrackId <em>Last Track Id</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.ProjectImpl#getLastSourceId <em>Last Source Id</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.ProjectImpl#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectImpl extends MinimalEObjectImpl.Container implements Project {
    /**
     * The cached value of the '{@link #getDatasources() <em>Datasources</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDatasources()
     * @generated
     * @ordered
     */
    protected EList<Datasource> datasources;

    /**
     * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifier()
     * @generated
     * @ordered
     */
    protected static final String IDENTIFIER_EDEFAULT = "\"\"";

    /**
     * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifier()
     * @generated
     * @ordered
     */
    protected String identifier = IDENTIFIER_EDEFAULT;

    /**
     * The default value of the '{@link #getLastTrackId() <em>Last Track Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLastTrackId()
     * @generated
     * @ordered
     */
    protected static final long LAST_TRACK_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getLastTrackId() <em>Last Track Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLastTrackId()
     * @generated
     * @ordered
     */
    protected long lastTrackId = LAST_TRACK_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getLastSourceId() <em>Last Source Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLastSourceId()
     * @generated
     * @ordered
     */
    protected static final long LAST_SOURCE_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getLastSourceId() <em>Last Source Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLastSourceId()
     * @generated
     * @ordered
     */
    protected long lastSourceId = LAST_SOURCE_ID_EDEFAULT;

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
    protected ProjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.PROJECT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Datasource> getDatasources() {
        if (datasources == null) {
            datasources = new EObjectContainmentEList<Datasource>(Datasource.class, this, MetaPackage.PROJECT__DATASOURCES);
        }
        return datasources;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdentifier(String newIdentifier) {
        String oldIdentifier = identifier;
        identifier = newIdentifier;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.PROJECT__IDENTIFIER, oldIdentifier, identifier));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getLastTrackId() {
        return lastTrackId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLastTrackId(long newLastTrackId) {
        long oldLastTrackId = lastTrackId;
        lastTrackId = newLastTrackId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.PROJECT__LAST_TRACK_ID, oldLastTrackId, lastTrackId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getLastSourceId() {
        return lastSourceId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLastSourceId(long newLastSourceId) {
        long oldLastSourceId = lastSourceId;
        lastSourceId = newLastSourceId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.PROJECT__LAST_SOURCE_ID, oldLastSourceId, lastSourceId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Group> getGroups() {
        if (groups == null) {
            groups = new EObjectResolvingEList<Group>(Group.class, this, MetaPackage.PROJECT__GROUPS);
        }
        return groups;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case MetaPackage.PROJECT__DATASOURCES:
                return ((InternalEList<?>)getDatasources()).basicRemove(otherEnd, msgs);
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
            case MetaPackage.PROJECT__DATASOURCES:
                return getDatasources();
            case MetaPackage.PROJECT__IDENTIFIER:
                return getIdentifier();
            case MetaPackage.PROJECT__LAST_TRACK_ID:
                return getLastTrackId();
            case MetaPackage.PROJECT__LAST_SOURCE_ID:
                return getLastSourceId();
            case MetaPackage.PROJECT__GROUPS:
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
            case MetaPackage.PROJECT__DATASOURCES:
                getDatasources().clear();
                getDatasources().addAll((Collection<? extends Datasource>)newValue);
                return;
            case MetaPackage.PROJECT__IDENTIFIER:
                setIdentifier((String)newValue);
                return;
            case MetaPackage.PROJECT__LAST_TRACK_ID:
                setLastTrackId((Long)newValue);
                return;
            case MetaPackage.PROJECT__LAST_SOURCE_ID:
                setLastSourceId((Long)newValue);
                return;
            case MetaPackage.PROJECT__GROUPS:
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
            case MetaPackage.PROJECT__DATASOURCES:
                getDatasources().clear();
                return;
            case MetaPackage.PROJECT__IDENTIFIER:
                setIdentifier(IDENTIFIER_EDEFAULT);
                return;
            case MetaPackage.PROJECT__LAST_TRACK_ID:
                setLastTrackId(LAST_TRACK_ID_EDEFAULT);
                return;
            case MetaPackage.PROJECT__LAST_SOURCE_ID:
                setLastSourceId(LAST_SOURCE_ID_EDEFAULT);
                return;
            case MetaPackage.PROJECT__GROUPS:
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
            case MetaPackage.PROJECT__DATASOURCES:
                return datasources != null && !datasources.isEmpty();
            case MetaPackage.PROJECT__IDENTIFIER:
                return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
            case MetaPackage.PROJECT__LAST_TRACK_ID:
                return lastTrackId != LAST_TRACK_ID_EDEFAULT;
            case MetaPackage.PROJECT__LAST_SOURCE_ID:
                return lastSourceId != LAST_SOURCE_ID_EDEFAULT;
            case MetaPackage.PROJECT__GROUPS:
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
        result.append(" (identifier: ");
        result.append(identifier);
        result.append(", lastTrackId: ");
        result.append(lastTrackId);
        result.append(", lastSourceId: ");
        result.append(lastSourceId);
        result.append(')');
        return result.toString();
    }

} //ProjectImpl
