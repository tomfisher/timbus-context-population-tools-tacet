/**
 */
package edu.teco.tacet.meta;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Media Timeseries</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.teco.tacet.meta.MediaTimeseries#getFilepath <em>Filepath</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.MediaTimeseries#getStartTimestamp <em>Start Timestamp</em>}</li>
 *   <li>{@link edu.teco.tacet.meta.MediaTimeseries#getPlaybackSpeed <em>Playback Speed</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.teco.tacet.meta.MetaPackage#getMediaTimeseries()
 * @model
 * @generated
 */
public interface MediaTimeseries extends Timeseries {
    /**
     * Returns the value of the '<em><b>Filepath</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filepath</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Filepath</em>' attribute.
     * @see #setFilepath(String)
     * @see edu.teco.tacet.meta.MetaPackage#getMediaTimeseries_Filepath()
     * @model
     * @generated
     */
    String getFilepath();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.MediaTimeseries#getFilepath <em>Filepath</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Filepath</em>' attribute.
     * @see #getFilepath()
     * @generated
     */
    void setFilepath(String value);

    /**
     * Returns the value of the '<em><b>Start Timestamp</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Timestamp</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Timestamp</em>' attribute.
     * @see #setStartTimestamp(long)
     * @see edu.teco.tacet.meta.MetaPackage#getMediaTimeseries_StartTimestamp()
     * @model
     * @generated
     */
    long getStartTimestamp();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.MediaTimeseries#getStartTimestamp <em>Start Timestamp</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Timestamp</em>' attribute.
     * @see #getStartTimestamp()
     * @generated
     */
    void setStartTimestamp(long value);

    /**
     * Returns the value of the '<em><b>Playback Speed</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Playback Speed</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Playback Speed</em>' attribute.
     * @see #setPlaybackSpeed(double)
     * @see edu.teco.tacet.meta.MetaPackage#getMediaTimeseries_PlaybackSpeed()
     * @model
     * @generated
     */
    double getPlaybackSpeed();

    /**
     * Sets the value of the '{@link edu.teco.tacet.meta.MediaTimeseries#getPlaybackSpeed <em>Playback Speed</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Playback Speed</em>' attribute.
     * @see #getPlaybackSpeed()
     * @generated
     */
    void setPlaybackSpeed(double value);

} // MediaTimeseries
