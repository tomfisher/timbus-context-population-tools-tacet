package edu.teco.tacet.timeseriesview;
public interface ITrackViewController {

    public void showTracks(String groupName, Iterable<Long> sensorTracks,
        Iterable<Long> annotationTracks, Iterable<Long> mediaTracks);
}
