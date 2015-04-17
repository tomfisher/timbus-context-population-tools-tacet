/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.CSVDatasource;
import edu.teco.tacet.meta.ColumnDescription;
import edu.teco.tacet.meta.MetaPackage;

import edu.teco.tacet.meta.nongen.Unit;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CSV Datasource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#getElementSeparator <em>Element Separator</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#getLineSeparator <em>Line Separator</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#getFilePath <em>File Path</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#getColumnDescriptions <em>Column Descriptions</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#getTimestampFormat <em>Timestamp Format</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#getNoOfLinesToSkip <em>No Of Lines To Skip</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#isTreatTimestampAsMillis <em>Treat Timestamp As Millis</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#getTimeUnit <em>Time Unit</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.CSVDatasourceImpl#isIsStartFrom1970 <em>Is Start From1970</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CSVDatasourceImpl extends DatasourceImpl implements CSVDatasource {
    /**
     * The default value of the '{@link #getElementSeparator() <em>Element Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getElementSeparator()
     * @generated
     * @ordered
     */
    protected static final char ELEMENT_SEPARATOR_EDEFAULT = '\u0000';

    /**
     * The cached value of the '{@link #getElementSeparator() <em>Element Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getElementSeparator()
     * @generated
     * @ordered
     */
    protected char elementSeparator = ELEMENT_SEPARATOR_EDEFAULT;

    /**
     * The default value of the '{@link #getLineSeparator() <em>Line Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLineSeparator()
     * @generated
     * @ordered
     */
    protected static final String LINE_SEPARATOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLineSeparator() <em>Line Separator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLineSeparator()
     * @generated
     * @ordered
     */
    protected String lineSeparator = LINE_SEPARATOR_EDEFAULT;

    /**
     * The default value of the '{@link #getFilePath() <em>File Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilePath()
     * @generated
     * @ordered
     */
    protected static final String FILE_PATH_EDEFAULT = "\"\"";

    /**
     * The cached value of the '{@link #getFilePath() <em>File Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilePath()
     * @generated
     * @ordered
     */
    protected String filePath = FILE_PATH_EDEFAULT;

    /**
     * The cached value of the '{@link #getColumnDescriptions() <em>Column Descriptions</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnDescriptions()
     * @generated
     * @ordered
     */
    protected EList<ColumnDescription> columnDescriptions;

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
     * The default value of the '{@link #getNoOfLinesToSkip() <em>No Of Lines To Skip</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNoOfLinesToSkip()
     * @generated
     * @ordered
     */
    protected static final int NO_OF_LINES_TO_SKIP_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getNoOfLinesToSkip() <em>No Of Lines To Skip</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNoOfLinesToSkip()
     * @generated
     * @ordered
     */
    protected int noOfLinesToSkip = NO_OF_LINES_TO_SKIP_EDEFAULT;

    /**
     * The default value of the '{@link #isTreatTimestampAsMillis() <em>Treat Timestamp As Millis</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isTreatTimestampAsMillis()
     * @generated
     * @ordered
     */
    protected static final boolean TREAT_TIMESTAMP_AS_MILLIS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isTreatTimestampAsMillis() <em>Treat Timestamp As Millis</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isTreatTimestampAsMillis()
     * @generated
     * @ordered
     */
    protected boolean treatTimestampAsMillis = TREAT_TIMESTAMP_AS_MILLIS_EDEFAULT;

    /**
     * The default value of the '{@link #getTimeUnit() <em>Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeUnit()
     * @generated
     * @ordered
     */
    protected static final Unit TIME_UNIT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTimeUnit() <em>Time Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeUnit()
     * @generated
     * @ordered
     */
    protected Unit timeUnit = TIME_UNIT_EDEFAULT;

    /**
     * The default value of the '{@link #isIsStartFrom1970() <em>Is Start From1970</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsStartFrom1970()
     * @generated
     * @ordered
     */
    protected static final boolean IS_START_FROM1970_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsStartFrom1970() <em>Is Start From1970</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsStartFrom1970()
     * @generated
     * @ordered
     */
    protected boolean isStartFrom1970 = IS_START_FROM1970_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CSVDatasourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.CSV_DATASOURCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public char getElementSeparator() {
        return elementSeparator;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setElementSeparator(char newElementSeparator) {
        char oldElementSeparator = elementSeparator;
        elementSeparator = newElementSeparator;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.CSV_DATASOURCE__ELEMENT_SEPARATOR, oldElementSeparator, elementSeparator));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLineSeparator() {
        return lineSeparator;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLineSeparator(String newLineSeparator) {
        String oldLineSeparator = lineSeparator;
        lineSeparator = newLineSeparator;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.CSV_DATASOURCE__LINE_SEPARATOR, oldLineSeparator, lineSeparator));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFilePath(String newFilePath) {
        String oldFilePath = filePath;
        filePath = newFilePath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.CSV_DATASOURCE__FILE_PATH, oldFilePath, filePath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ColumnDescription> getColumnDescriptions() {
        if (columnDescriptions == null) {
            columnDescriptions = new EObjectContainmentEList<ColumnDescription>(ColumnDescription.class, this, MetaPackage.CSV_DATASOURCE__COLUMN_DESCRIPTIONS);
        }
        return columnDescriptions;
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
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.CSV_DATASOURCE__TIMESTAMP_FORMAT, oldTimestampFormat, timestampFormat));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getNoOfLinesToSkip() {
        return noOfLinesToSkip;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNoOfLinesToSkip(int newNoOfLinesToSkip) {
        int oldNoOfLinesToSkip = noOfLinesToSkip;
        noOfLinesToSkip = newNoOfLinesToSkip;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.CSV_DATASOURCE__NO_OF_LINES_TO_SKIP, oldNoOfLinesToSkip, noOfLinesToSkip));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isTreatTimestampAsMillis() {
        return treatTimestampAsMillis;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTreatTimestampAsMillis(boolean newTreatTimestampAsMillis) {
        boolean oldTreatTimestampAsMillis = treatTimestampAsMillis;
        treatTimestampAsMillis = newTreatTimestampAsMillis;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.CSV_DATASOURCE__TREAT_TIMESTAMP_AS_MILLIS, oldTreatTimestampAsMillis, treatTimestampAsMillis));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Unit getTimeUnit() {
        return timeUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimeUnit(Unit newTimeUnit) {
        Unit oldTimeUnit = timeUnit;
        timeUnit = newTimeUnit;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.CSV_DATASOURCE__TIME_UNIT, oldTimeUnit, timeUnit));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsStartFrom1970() {
        return isStartFrom1970;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIsStartFrom1970(boolean newIsStartFrom1970) {
        boolean oldIsStartFrom1970 = isStartFrom1970;
        isStartFrom1970 = newIsStartFrom1970;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.CSV_DATASOURCE__IS_START_FROM1970, oldIsStartFrom1970, isStartFrom1970));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case MetaPackage.CSV_DATASOURCE__COLUMN_DESCRIPTIONS:
                return ((InternalEList<?>)getColumnDescriptions()).basicRemove(otherEnd, msgs);
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
            case MetaPackage.CSV_DATASOURCE__ELEMENT_SEPARATOR:
                return getElementSeparator();
            case MetaPackage.CSV_DATASOURCE__LINE_SEPARATOR:
                return getLineSeparator();
            case MetaPackage.CSV_DATASOURCE__FILE_PATH:
                return getFilePath();
            case MetaPackage.CSV_DATASOURCE__COLUMN_DESCRIPTIONS:
                return getColumnDescriptions();
            case MetaPackage.CSV_DATASOURCE__TIMESTAMP_FORMAT:
                return getTimestampFormat();
            case MetaPackage.CSV_DATASOURCE__NO_OF_LINES_TO_SKIP:
                return getNoOfLinesToSkip();
            case MetaPackage.CSV_DATASOURCE__TREAT_TIMESTAMP_AS_MILLIS:
                return isTreatTimestampAsMillis();
            case MetaPackage.CSV_DATASOURCE__TIME_UNIT:
                return getTimeUnit();
            case MetaPackage.CSV_DATASOURCE__IS_START_FROM1970:
                return isIsStartFrom1970();
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
            case MetaPackage.CSV_DATASOURCE__ELEMENT_SEPARATOR:
                setElementSeparator((Character)newValue);
                return;
            case MetaPackage.CSV_DATASOURCE__LINE_SEPARATOR:
                setLineSeparator((String)newValue);
                return;
            case MetaPackage.CSV_DATASOURCE__FILE_PATH:
                setFilePath((String)newValue);
                return;
            case MetaPackage.CSV_DATASOURCE__COLUMN_DESCRIPTIONS:
                getColumnDescriptions().clear();
                getColumnDescriptions().addAll((Collection<? extends ColumnDescription>)newValue);
                return;
            case MetaPackage.CSV_DATASOURCE__TIMESTAMP_FORMAT:
                setTimestampFormat((String)newValue);
                return;
            case MetaPackage.CSV_DATASOURCE__NO_OF_LINES_TO_SKIP:
                setNoOfLinesToSkip((Integer)newValue);
                return;
            case MetaPackage.CSV_DATASOURCE__TREAT_TIMESTAMP_AS_MILLIS:
                setTreatTimestampAsMillis((Boolean)newValue);
                return;
            case MetaPackage.CSV_DATASOURCE__TIME_UNIT:
                setTimeUnit((Unit)newValue);
                return;
            case MetaPackage.CSV_DATASOURCE__IS_START_FROM1970:
                setIsStartFrom1970((Boolean)newValue);
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
            case MetaPackage.CSV_DATASOURCE__ELEMENT_SEPARATOR:
                setElementSeparator(ELEMENT_SEPARATOR_EDEFAULT);
                return;
            case MetaPackage.CSV_DATASOURCE__LINE_SEPARATOR:
                setLineSeparator(LINE_SEPARATOR_EDEFAULT);
                return;
            case MetaPackage.CSV_DATASOURCE__FILE_PATH:
                setFilePath(FILE_PATH_EDEFAULT);
                return;
            case MetaPackage.CSV_DATASOURCE__COLUMN_DESCRIPTIONS:
                getColumnDescriptions().clear();
                return;
            case MetaPackage.CSV_DATASOURCE__TIMESTAMP_FORMAT:
                setTimestampFormat(TIMESTAMP_FORMAT_EDEFAULT);
                return;
            case MetaPackage.CSV_DATASOURCE__NO_OF_LINES_TO_SKIP:
                setNoOfLinesToSkip(NO_OF_LINES_TO_SKIP_EDEFAULT);
                return;
            case MetaPackage.CSV_DATASOURCE__TREAT_TIMESTAMP_AS_MILLIS:
                setTreatTimestampAsMillis(TREAT_TIMESTAMP_AS_MILLIS_EDEFAULT);
                return;
            case MetaPackage.CSV_DATASOURCE__TIME_UNIT:
                setTimeUnit(TIME_UNIT_EDEFAULT);
                return;
            case MetaPackage.CSV_DATASOURCE__IS_START_FROM1970:
                setIsStartFrom1970(IS_START_FROM1970_EDEFAULT);
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
            case MetaPackage.CSV_DATASOURCE__ELEMENT_SEPARATOR:
                return elementSeparator != ELEMENT_SEPARATOR_EDEFAULT;
            case MetaPackage.CSV_DATASOURCE__LINE_SEPARATOR:
                return LINE_SEPARATOR_EDEFAULT == null ? lineSeparator != null : !LINE_SEPARATOR_EDEFAULT.equals(lineSeparator);
            case MetaPackage.CSV_DATASOURCE__FILE_PATH:
                return FILE_PATH_EDEFAULT == null ? filePath != null : !FILE_PATH_EDEFAULT.equals(filePath);
            case MetaPackage.CSV_DATASOURCE__COLUMN_DESCRIPTIONS:
                return columnDescriptions != null && !columnDescriptions.isEmpty();
            case MetaPackage.CSV_DATASOURCE__TIMESTAMP_FORMAT:
                return TIMESTAMP_FORMAT_EDEFAULT == null ? timestampFormat != null : !TIMESTAMP_FORMAT_EDEFAULT.equals(timestampFormat);
            case MetaPackage.CSV_DATASOURCE__NO_OF_LINES_TO_SKIP:
                return noOfLinesToSkip != NO_OF_LINES_TO_SKIP_EDEFAULT;
            case MetaPackage.CSV_DATASOURCE__TREAT_TIMESTAMP_AS_MILLIS:
                return treatTimestampAsMillis != TREAT_TIMESTAMP_AS_MILLIS_EDEFAULT;
            case MetaPackage.CSV_DATASOURCE__TIME_UNIT:
                return TIME_UNIT_EDEFAULT == null ? timeUnit != null : !TIME_UNIT_EDEFAULT.equals(timeUnit);
            case MetaPackage.CSV_DATASOURCE__IS_START_FROM1970:
                return isStartFrom1970 != IS_START_FROM1970_EDEFAULT;
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
        result.append(" (elementSeparator: ");
        result.append(elementSeparator);
        result.append(", lineSeparator: ");
        result.append(lineSeparator);
        result.append(", filePath: ");
        result.append(filePath);
        result.append(", timestampFormat: ");
        result.append(timestampFormat);
        result.append(", noOfLinesToSkip: ");
        result.append(noOfLinesToSkip);
        result.append(", treatTimestampAsMillis: ");
        result.append(treatTimestampAsMillis);
        result.append(", timeUnit: ");
        result.append(timeUnit);
        result.append(", isStartFrom1970: ");
        result.append(isStartFrom1970);
        result.append(')');
        return result.toString();
    }

} //CSVDatasourceImpl
