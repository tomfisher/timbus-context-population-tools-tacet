/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.MetaPackage;
import edu.teco.tacet.meta.RdfTimeseries;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rdf Timeseries</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfTimeseriesImpl#getValuePath <em>Value Path</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfTimeseriesImpl#getTimestampPath <em>Timestamp Path</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfTimeseriesImpl#getIdentifierPath <em>Identifier Path</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfTimeseriesImpl#getIdentifierValue <em>Identifier Value</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfTimeseriesImpl#getMapping <em>Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RdfTimeseriesImpl extends TimeseriesImpl implements RdfTimeseries {
    /**
     * The cached value of the '{@link #getValuePath() <em>Value Path</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValuePath()
     * @generated
     * @ordered
     */
    protected EList<String> valuePath;

    /**
     * The cached value of the '{@link #getTimestampPath() <em>Timestamp Path</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimestampPath()
     * @generated
     * @ordered
     */
    protected EList<String> timestampPath;

    /**
     * The cached value of the '{@link #getIdentifierPath() <em>Identifier Path</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierPath()
     * @generated
     * @ordered
     */
    protected EList<String> identifierPath;

    /**
     * The default value of the '{@link #getIdentifierValue() <em>Identifier Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierValue()
     * @generated
     * @ordered
     */
    protected static final String IDENTIFIER_VALUE_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getIdentifierValue() <em>Identifier Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdentifierValue()
     * @generated
     * @ordered
     */
    protected String identifierValue = IDENTIFIER_VALUE_EDEFAULT;

    /**
     * The cached value of the '{@link #getMapping() <em>Mapping</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMapping()
     * @generated
     * @ordered
     */
    protected Map<Object, Object> mapping;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RdfTimeseriesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.RDF_TIMESERIES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getValuePath() {
        if (valuePath == null) {
            valuePath = new EDataTypeEList<String>(String.class, this, MetaPackage.RDF_TIMESERIES__VALUE_PATH);
        }
        return valuePath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getTimestampPath() {
        if (timestampPath == null) {
            timestampPath = new EDataTypeEList<String>(String.class, this, MetaPackage.RDF_TIMESERIES__TIMESTAMP_PATH);
        }
        return timestampPath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getIdentifierPath() {
        if (identifierPath == null) {
            identifierPath = new EDataTypeUniqueEList<String>(String.class, this, MetaPackage.RDF_TIMESERIES__IDENTIFIER_PATH);
        }
        return identifierPath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIdentifierValue() {
        return identifierValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdentifierValue(String newIdentifierValue) {
        String oldIdentifierValue = identifierValue;
        identifierValue = newIdentifierValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.RDF_TIMESERIES__IDENTIFIER_VALUE, oldIdentifierValue, identifierValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Map<Object, Object> getMapping() {
        return mapping;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMapping(Map<Object, Object> newMapping) {
        Map<Object, Object> oldMapping = mapping;
        mapping = newMapping;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.RDF_TIMESERIES__MAPPING, oldMapping, mapping));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MetaPackage.RDF_TIMESERIES__VALUE_PATH:
                return getValuePath();
            case MetaPackage.RDF_TIMESERIES__TIMESTAMP_PATH:
                return getTimestampPath();
            case MetaPackage.RDF_TIMESERIES__IDENTIFIER_PATH:
                return getIdentifierPath();
            case MetaPackage.RDF_TIMESERIES__IDENTIFIER_VALUE:
                return getIdentifierValue();
            case MetaPackage.RDF_TIMESERIES__MAPPING:
                return getMapping();
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
            case MetaPackage.RDF_TIMESERIES__VALUE_PATH:
                getValuePath().clear();
                getValuePath().addAll((Collection<? extends String>)newValue);
                return;
            case MetaPackage.RDF_TIMESERIES__TIMESTAMP_PATH:
                getTimestampPath().clear();
                getTimestampPath().addAll((Collection<? extends String>)newValue);
                return;
            case MetaPackage.RDF_TIMESERIES__IDENTIFIER_PATH:
                getIdentifierPath().clear();
                getIdentifierPath().addAll((Collection<? extends String>)newValue);
                return;
            case MetaPackage.RDF_TIMESERIES__IDENTIFIER_VALUE:
                setIdentifierValue((String)newValue);
                return;
            case MetaPackage.RDF_TIMESERIES__MAPPING:
                setMapping((Map<Object, Object>)newValue);
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
            case MetaPackage.RDF_TIMESERIES__VALUE_PATH:
                getValuePath().clear();
                return;
            case MetaPackage.RDF_TIMESERIES__TIMESTAMP_PATH:
                getTimestampPath().clear();
                return;
            case MetaPackage.RDF_TIMESERIES__IDENTIFIER_PATH:
                getIdentifierPath().clear();
                return;
            case MetaPackage.RDF_TIMESERIES__IDENTIFIER_VALUE:
                setIdentifierValue(IDENTIFIER_VALUE_EDEFAULT);
                return;
            case MetaPackage.RDF_TIMESERIES__MAPPING:
                setMapping((Map<Object, Object>)null);
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
            case MetaPackage.RDF_TIMESERIES__VALUE_PATH:
                return valuePath != null && !valuePath.isEmpty();
            case MetaPackage.RDF_TIMESERIES__TIMESTAMP_PATH:
                return timestampPath != null && !timestampPath.isEmpty();
            case MetaPackage.RDF_TIMESERIES__IDENTIFIER_PATH:
                return identifierPath != null && !identifierPath.isEmpty();
            case MetaPackage.RDF_TIMESERIES__IDENTIFIER_VALUE:
                return IDENTIFIER_VALUE_EDEFAULT == null ? identifierValue != null : !IDENTIFIER_VALUE_EDEFAULT.equals(identifierValue);
            case MetaPackage.RDF_TIMESERIES__MAPPING:
                return mapping != null;
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
        result.append(" (valuePath: ");
        result.append(valuePath);
        result.append(", timestampPath: ");
        result.append(timestampPath);
        result.append(", identifierPath: ");
        result.append(identifierPath);
        result.append(", identifierValue: ");
        result.append(identifierValue);
        result.append(", mapping: ");
        result.append(mapping);
        result.append(')');
        return result.toString();
    }

} //RdfTimeseriesImpl
