/**
 */
package edu.teco.tacet.meta.impl;

import edu.teco.tacet.meta.MediaTimeseries;
import edu.teco.tacet.meta.MetaPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Media Timeseries</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.impl.MediaTimeseriesImpl#getFilepath <em>Filepath</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.MediaTimeseriesImpl#getStartTimestamp <em>Start Timestamp</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.impl.MediaTimeseriesImpl#getPlaybackSpeed <em>Playback Speed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MediaTimeseriesImpl extends TimeseriesImpl implements MediaTimeseries {
    /**
     * The default value of the '{@link #getFilepath() <em>Filepath</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilepath()
     * @generated
     * @ordered
     */
    protected static final String FILEPATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFilepath() <em>Filepath</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilepath()
     * @generated
     * @ordered
     */
    protected String filepath = FILEPATH_EDEFAULT;

    /**
     * The default value of the '{@link #getStartTimestamp() <em>Start Timestamp</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartTimestamp()
     * @generated
     * @ordered
     */
    protected static final long START_TIMESTAMP_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getStartTimestamp() <em>Start Timestamp</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartTimestamp()
     * @generated
     * @ordered
     */
    protected long startTimestamp = START_TIMESTAMP_EDEFAULT;

    /**
     * The default value of the '{@link #getPlaybackSpeed() <em>Playback Speed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPlaybackSpeed()
     * @generated
     * @ordered
     */
    protected static final double PLAYBACK_SPEED_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getPlaybackSpeed() <em>Playback Speed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPlaybackSpeed()
     * @generated
     * @ordered
     */
    protected double playbackSpeed = PLAYBACK_SPEED_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MediaTimeseriesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MetaPackage.Literals.MEDIA_TIMESERIES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFilepath(String newFilepath) {
        String oldFilepath = filepath;
        filepath = newFilepath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.MEDIA_TIMESERIES__FILEPATH, oldFilepath, filepath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getStartTimestamp() {
        return startTimestamp;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartTimestamp(long newStartTimestamp) {
        long oldStartTimestamp = startTimestamp;
        startTimestamp = newStartTimestamp;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.MEDIA_TIMESERIES__START_TIMESTAMP, oldStartTimestamp, startTimestamp));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public double getPlaybackSpeed() {
        return playbackSpeed;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPlaybackSpeed(double newPlaybackSpeed) {
        double oldPlaybackSpeed = playbackSpeed;
        playbackSpeed = newPlaybackSpeed;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, MetaPackage.MEDIA_TIMESERIES__PLAYBACK_SPEED, oldPlaybackSpeed, playbackSpeed));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case MetaPackage.MEDIA_TIMESERIES__FILEPATH:
                return getFilepath();
            case MetaPackage.MEDIA_TIMESERIES__START_TIMESTAMP:
                return getStartTimestamp();
            case MetaPackage.MEDIA_TIMESERIES__PLAYBACK_SPEED:
                return getPlaybackSpeed();
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
            case MetaPackage.MEDIA_TIMESERIES__FILEPATH:
                setFilepath((String)newValue);
                return;
            case MetaPackage.MEDIA_TIMESERIES__START_TIMESTAMP:
                setStartTimestamp((Long)newValue);
                return;
            case MetaPackage.MEDIA_TIMESERIES__PLAYBACK_SPEED:
                setPlaybackSpeed((Double)newValue);
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
            case MetaPackage.MEDIA_TIMESERIES__FILEPATH:
                setFilepath(FILEPATH_EDEFAULT);
                return;
            case MetaPackage.MEDIA_TIMESERIES__START_TIMESTAMP:
                setStartTimestamp(START_TIMESTAMP_EDEFAULT);
                return;
            case MetaPackage.MEDIA_TIMESERIES__PLAYBACK_SPEED:
                setPlaybackSpeed(PLAYBACK_SPEED_EDEFAULT);
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
            case MetaPackage.MEDIA_TIMESERIES__FILEPATH:
                return FILEPATH_EDEFAULT == null ? filepath != null : !FILEPATH_EDEFAULT.equals(filepath);
            case MetaPackage.MEDIA_TIMESERIES__START_TIMESTAMP:
                return startTimestamp != START_TIMESTAMP_EDEFAULT;
            case MetaPackage.MEDIA_TIMESERIES__PLAYBACK_SPEED:
                return playbackSpeed != PLAYBACK_SPEED_EDEFAULT;
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
        result.append(" (filepath: ");
        result.append(filepath);
        result.append(", startTimestamp: ");
        result.append(startTimestamp);
        result.append(", playbackSpeed: ");
        result.append(playbackSpeed);
        result.append(')');
        return result.toString();
    }

} //MediaTimeseriesImpl
