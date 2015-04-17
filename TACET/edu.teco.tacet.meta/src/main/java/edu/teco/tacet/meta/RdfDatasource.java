/**
 */
package edu.teco.tacet.meta;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rdf Datasource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.RdfDatasource#getResolveUri <em>Resolve Uri</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.RdfDatasource#getFileName <em>File Name</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.RdfDatasource#getRootResourceUri <em>Root Resource Uri</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.RdfDatasource#getModel <em>Model</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.RdfDatasource#getTimestampFormat <em>Timestamp Format</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getRdfDatasource()
 * @model
 * @generated
 */
public interface RdfDatasource extends Datasource {
    /**
     * Returns the value of the '<em><b>Resolve Uri</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resolve Uri</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resolve Uri</em>' attribute.
     * @see #setResolveUri(String)
     * @see edu.teco.tacet.meta.MetaPackage#getRdfDatasource_ResolveUri()
     * @model default=""
     * @generated
     */
    String getResolveUri();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.RdfDatasource#getResolveUri <em>Resolve Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resolve Uri</em>' attribute.
     * @see #getResolveUri()
     * @generated
     */
    void setResolveUri(String value);

    /**
     * Returns the value of the '<em><b>File Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>File Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>File Name</em>' attribute.
     * @see #setFileName(String)
     * @see edu.teco.tacet.meta.MetaPackage#getRdfDatasource_FileName()
     * @model
     * @generated
     */
    String getFileName();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.RdfDatasource#getFileName <em>File Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>File Name</em>' attribute.
     * @see #getFileName()
     * @generated
     */
    void setFileName(String value);

    /**
     * Returns the value of the '<em><b>Root Resource Uri</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Root Resource Uri</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Root Resource Uri</em>' attribute.
     * @see #setRootResourceUri(String)
     * @see edu.teco.tacet.meta.MetaPackage#getRdfDatasource_RootResourceUri()
     * @model
     * @generated
     */
    String getRootResourceUri();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.RdfDatasource#getRootResourceUri <em>Root Resource Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Root Resource Uri</em>' attribute.
     * @see #getRootResourceUri()
     * @generated
     */
    void setRootResourceUri(String value);

    /**
     * Returns the value of the '<em><b>Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Model</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Model</em>' attribute.
     * @see #setModel(Model)
     * @see edu.teco.tacet.meta.MetaPackage#getRdfDatasource_Model()
     * @model dataType="edu.teco.tacet.meta.Model"
     * @generated
     */
    Model getModel();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.RdfDatasource#getModel <em>Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Model</em>' attribute.
     * @see #getModel()
     * @generated
     */
    void setModel(Model value);

    /**
     * Returns the value of the '<em><b>Timestamp Format</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Timestamp Format</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Timestamp Format</em>' attribute.
     * @see #setTimestampFormat(String)
     * @see edu.teco.tacet.meta.MetaPackage#getRdfDatasource_TimestampFormat()
     * @model
     * @generated
     */
    String getTimestampFormat();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.RdfDatasource#getTimestampFormat <em>Timestamp Format</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Timestamp Format</em>' attribute.
     * @see #getTimestampFormat()
     * @generated
     */
    void setTimestampFormat(String value);

} // RdfDatasource
