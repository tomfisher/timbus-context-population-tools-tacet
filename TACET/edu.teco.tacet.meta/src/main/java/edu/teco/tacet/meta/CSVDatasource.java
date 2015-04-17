/**
 */
package edu.teco.tacet.meta;

import edu.teco.tacet.meta.nongen.Unit;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CSV Datasource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#getElementSeparator <em>Element Separator</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#getLineSeparator <em>Line Separator</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#getFilePath <em>File Path</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#getColumnDescriptions <em>Column Descriptions</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#getTimestampFormat <em>Timestamp Format</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#getNoOfLinesToSkip <em>No Of Lines To Skip</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#isTreatTimestampAsMillis <em>Treat Timestamp As Millis</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#getTimeUnit <em>Time Unit</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.CSVDatasource#isIsStartFrom1970 <em>Is Start From1970</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource()
 * @model
 * @generated
 */
public interface CSVDatasource extends Datasource {
    /**
     * Returns the value of the '<em><b>Element Separator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Element Separator</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Element Separator</em>' attribute.
     * @see #setElementSeparator(char)
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_ElementSeparator()
     * @model
     * @generated
     */
    char getElementSeparator();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.CSVDatasource#getElementSeparator <em>Element Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Element Separator</em>' attribute.
     * @see #getElementSeparator()
     * @generated
     */
    void setElementSeparator(char value);

    /**
     * Returns the value of the '<em><b>Line Separator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Line Separator</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Line Separator</em>' attribute.
     * @see #setLineSeparator(String)
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_LineSeparator()
     * @model
     * @generated
     */
    String getLineSeparator();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.CSVDatasource#getLineSeparator <em>Line Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Line Separator</em>' attribute.
     * @see #getLineSeparator()
     * @generated
     */
    void setLineSeparator(String value);

    /**
     * Returns the value of the '<em><b>File Path</b></em>' attribute.
     * The default value is <code>"\"\""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>File Path</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>File Path</em>' attribute.
     * @see #setFilePath(String)
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_FilePath()
     * @model default="\"\"" unique="false"
     * @generated
     */
    String getFilePath();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.CSVDatasource#getFilePath <em>File Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>File Path</em>' attribute.
     * @see #getFilePath()
     * @generated
     */
    void setFilePath(String value);

    /**
     * Returns the value of the '<em><b>Column Descriptions</b></em>' containment reference list.
     * The list contents are of type {@link edu.teco.tacet.meta.ColumnDescription}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Column Descriptions</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Column Descriptions</em>' containment reference list.
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_ColumnDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<ColumnDescription> getColumnDescriptions();

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
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_TimestampFormat()
     * @model required="true"
     * @generated
     */
    String getTimestampFormat();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.CSVDatasource#getTimestampFormat <em>Timestamp Format</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Timestamp Format</em>' attribute.
     * @see #getTimestampFormat()
     * @generated
     */
    void setTimestampFormat(String value);

    /**
     * Returns the value of the '<em><b>No Of Lines To Skip</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>No Of Lines To Skip</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>No Of Lines To Skip</em>' attribute.
     * @see #setNoOfLinesToSkip(int)
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_NoOfLinesToSkip()
     * @model
     * @generated
     */
    int getNoOfLinesToSkip();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.CSVDatasource#getNoOfLinesToSkip <em>No Of Lines To Skip</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>No Of Lines To Skip</em>' attribute.
     * @see #getNoOfLinesToSkip()
     * @generated
     */
    void setNoOfLinesToSkip(int value);

    /**
     * Returns the value of the '<em><b>Treat Timestamp As Millis</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Treat Timestamp As Millis</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Treat Timestamp As Millis</em>' attribute.
     * @see #setTreatTimestampAsMillis(boolean)
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_TreatTimestampAsMillis()
     * @model
     * @generated
     */
    boolean isTreatTimestampAsMillis();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.CSVDatasource#isTreatTimestampAsMillis <em>Treat Timestamp As Millis</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Treat Timestamp As Millis</em>' attribute.
     * @see #isTreatTimestampAsMillis()
     * @generated
     */
    void setTreatTimestampAsMillis(boolean value);

    /**
     * Returns the value of the '<em><b>Time Unit</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time Unit</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Unit</em>' attribute.
     * @see #setTimeUnit(Unit)
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_TimeUnit()
     * @model dataType="edu.teco.tacet.meta.Unit"
     * @generated
     */
    Unit getTimeUnit();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.CSVDatasource#getTimeUnit <em>Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time Unit</em>' attribute.
     * @see #getTimeUnit()
     * @generated
     */
    void setTimeUnit(Unit value);

    /**
     * Returns the value of the '<em><b>Is Start From1970</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Start From1970</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is Start From1970</em>' attribute.
     * @see #setIsStartFrom1970(boolean)
     * @see edu.teco.tacet.meta.MetaPackage#getCSVDatasource_IsStartFrom1970()
     * @model
     * @generated
     */
    boolean isIsStartFrom1970();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.CSVDatasource#isIsStartFrom1970 <em>Is Start From1970</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Start From1970</em>' attribute.
     * @see #isIsStartFrom1970()
     * @generated
     */
    void setIsStartFrom1970(boolean value);

} // CSVDatasource
