/**
 */
package edu.teco.tacet.meta.impl;

import com.hp.hpl.jena.rdf.model.Model;

import edu.teco.tacet.meta.MetaPackage;
import edu.teco.tacet.meta.RdfDatasource;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rdf Datasource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfDatasourceImpl#getResolveUri <em>Resolve Uri</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfDatasourceImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfDatasourceImpl#getRootResourceUri <em>Root Resource Uri</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfDatasourceImpl#getModel <em>Model</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.RdfDatasourceImpl#getTimestampFormat <em>Timestamp Format</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RdfDatasourceImpl extends DatasourceImpl implements RdfDatasource {
    /**
     * The default value of the '{@link #getResolveUri() <em>Resolve Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResolveUri()
     * @generated
     * @ordered
     */
    protected static final String RESOLVE_URI_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getResolveUri() <em>Resolve Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResolveUri()
     * @generated
     * @ordered
     */
    protected String resolveUri = RESOLVE_URI_EDEFAULT;

    /**
     * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFileName()
     * @generated
     * @ordered
     */
    protected static final String FILE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFileName()
     * @generated
     * @ordered
     */
    protected String fileName = FILE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getRootResourceUri() <em>Root Resource Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRootResourceUri()
     * @generated
     * @ordered
     */
    protected static final String ROOT_RESOURCE_URI_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRootResourceUri() <em>Root Resource Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRootResourceUri()
     * @generated
     * @ordered
     */
    protected String rootResourceUri = ROOT_RESOURCE_URI_EDEFAULT;

    /**
     * The default value of the '{@link #getModel() <em>Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModel()
     * @generated
     * @ordered
     */
    protected static final Model MODEL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getModel() <em>Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModel()
     * @generated
     * @ordered
     */
    protected Model model = MODEL_EDEFAULT;

    /**
     * The default value of the '{@link #getTimestampFormat() <em>Timestamp Format</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimestampFormat()
     * @generated
     * @ordered
     */
    protected static final String TIMESTAMP_FORMAT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTimestampFormat() <em>Timestamp Format</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimestampFormat()
     * @generated
     * @ordered
     */
    protected String timestampFormat = TIMESTAMP_FORMAT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RdfDatasourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.RDF_DATASOURCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getResolveUri() {
        return resolveUri;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResolveUri(String newResolveUri) {
        String oldResolveUri = resolveUri;
        resolveUri = newResolveUri;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.RDF_DATASOURCE__RESOLVE_URI, oldResolveUri, resolveUri));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFileName(String newFileName) {
        String oldFileName = fileName;
        fileName = newFileName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.RDF_DATASOURCE__FILE_NAME, oldFileName, fileName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRootResourceUri() {
        return rootResourceUri;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRootResourceUri(String newRootResourceUri) {
        String oldRootResourceUri = rootResourceUri;
        rootResourceUri = newRootResourceUri;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.RDF_DATASOURCE__ROOT_RESOURCE_URI, oldRootResourceUri, rootResourceUri));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Model getModel() {
        return model;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModel(Model newModel) {
        Model oldModel = model;
        model = newModel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.RDF_DATASOURCE__MODEL, oldModel, model));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTimestampFormat() {
        return timestampFormat;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimestampFormat(String newTimestampFormat) {
        String oldTimestampFormat = timestampFormat;
        timestampFormat = newTimestampFormat;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.RDF_DATASOURCE__TIMESTAMP_FORMAT, oldTimestampFormat, timestampFormat));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MetaPackage.RDF_DATASOURCE__RESOLVE_URI:
                return getResolveUri();
            case MetaPackage.RDF_DATASOURCE__FILE_NAME:
                return getFileName();
            case MetaPackage.RDF_DATASOURCE__ROOT_RESOURCE_URI:
                return getRootResourceUri();
            case MetaPackage.RDF_DATASOURCE__MODEL:
                return getModel();
            case MetaPackage.RDF_DATASOURCE__TIMESTAMP_FORMAT:
                return getTimestampFormat();
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
            case MetaPackage.RDF_DATASOURCE__RESOLVE_URI:
                setResolveUri((String)newValue);
                return;
            case MetaPackage.RDF_DATASOURCE__FILE_NAME:
                setFileName((String)newValue);
                return;
            case MetaPackage.RDF_DATASOURCE__ROOT_RESOURCE_URI:
                setRootResourceUri((String)newValue);
                return;
            case MetaPackage.RDF_DATASOURCE__MODEL:
                setModel((Model)newValue);
                return;
            case MetaPackage.RDF_DATASOURCE__TIMESTAMP_FORMAT:
                setTimestampFormat((String)newValue);
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
            case MetaPackage.RDF_DATASOURCE__RESOLVE_URI:
                setResolveUri(RESOLVE_URI_EDEFAULT);
                return;
            case MetaPackage.RDF_DATASOURCE__FILE_NAME:
                setFileName(FILE_NAME_EDEFAULT);
                return;
            case MetaPackage.RDF_DATASOURCE__ROOT_RESOURCE_URI:
                setRootResourceUri(ROOT_RESOURCE_URI_EDEFAULT);
                return;
            case MetaPackage.RDF_DATASOURCE__MODEL:
                setModel(MODEL_EDEFAULT);
                return;
            case MetaPackage.RDF_DATASOURCE__TIMESTAMP_FORMAT:
                setTimestampFormat(TIMESTAMP_FORMAT_EDEFAULT);
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
            case MetaPackage.RDF_DATASOURCE__RESOLVE_URI:
                return RESOLVE_URI_EDEFAULT == null ? resolveUri != null : !RESOLVE_URI_EDEFAULT.equals(resolveUri);
            case MetaPackage.RDF_DATASOURCE__FILE_NAME:
                return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
            case MetaPackage.RDF_DATASOURCE__ROOT_RESOURCE_URI:
                return ROOT_RESOURCE_URI_EDEFAULT == null ? rootResourceUri != null : !ROOT_RESOURCE_URI_EDEFAULT.equals(rootResourceUri);
            case MetaPackage.RDF_DATASOURCE__MODEL:
                return MODEL_EDEFAULT == null ? model != null : !MODEL_EDEFAULT.equals(model);
            case MetaPackage.RDF_DATASOURCE__TIMESTAMP_FORMAT:
                return TIMESTAMP_FORMAT_EDEFAULT == null ? timestampFormat != null : !TIMESTAMP_FORMAT_EDEFAULT.equals(timestampFormat);
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
        result.append(" (resolveUri: ");
        result.append(resolveUri);
        result.append(", fileName: ");
        result.append(fileName);
        result.append(", rootResourceUri: ");
        result.append(rootResourceUri);
        result.append(", model: ");
        result.append(model);
        result.append(", timestampFormat: ");
        result.append(timestampFormat);
        result.append(')');
        return result.toString();
    }

} //RdfDatasourceImpl
