package edu.teco.tacet.track;

import java.util.ArrayList;
import java.util.List;

public class MergeTrack<T> extends AbstractTrack<T> {

    private List<Track<T>> wrappedTracks;
    private Track<T> favouredTrack;
    private MergeStrategy<T> mergeStrategy;

    public MergeTrack(Iterable<Track<T>> wrappedTracks, Track<T> favouredTrack,
        MergeStrategy<T> mergeStrategy) {
        super(favouredTrack.getId(), favouredTrack.getSourceId(), null);

        this.wrappedTracks = new ArrayList<Track<T>>();

        for (Track<T> track : wrappedTracks)
            this.wrappedTracks.add(track);

        setFavouredTrack(favouredTrack);

        if (!this.wrappedTracks.contains(favouredTrack))
            throw new IllegalArgumentException(
                "favouredTrack must be a Track contained in wrappedTracks.");

        this.mergeStrategy = mergeStrategy;
    }

    public MergeTrack(Iterable<Track<T>> wrappedTracks, long trackId, long sourceId,
        MergeStrategy<T> mergeStrategy) {
        super(trackId, sourceId, null);
        super.getMetaData().setName(joinTrackNames(wrappedTracks, "|"));
        this.mergeStrategy = mergeStrategy;
    }

    private String joinTrackNames(Iterable<Track<T>> tracks, String separator) {
        String ret = "";
        for (Track<T> track : tracks)
            ret += track.getMetaData().getName() + separator;
        return ret.substring(0, ret.length() - separator.length());
    }

    public void addTrack(Track<T> track) {
        wrappedTracks.add(track);
    }

    public void removeTrack(long id) {
        for (int i = 0; i < wrappedTracks.size(); i++)
            if (wrappedTracks.get(i).getId() == id) {
                wrappedTracks.remove(i);
                break;
            }
    }

    public Track<T> getFavouredTrack() {
        return favouredTrack;
    }

    public void setFavouredTrack(Track<T> favouredTrack) {
        this.favouredTrack = favouredTrack;
        super.getMetaData().setName(favouredTrack.getMetaData().getName());
        super.getMetaData().setStartRange(favouredTrack.getMetaData().getStartRange());
        super.setSourceId(favouredTrack.getSourceId());
        if (!wrappedTracks.contains(favouredTrack)) {
            wrappedTracks.add(favouredTrack);
        }
    }

    @Override
    public Iterable<? extends T> getData(Range range) {
        List<Iterable<? extends T>> allData = new ArrayList<>();
        for (Track<T> track : wrappedTracks)
            allData.add(track.getData(range));
        return mergeStrategy.merge(allData);
    }

    @Override
    public Range getCoveredRange() {
        // This is a fold.
        Range acc = null;
        for (Track<T> track : wrappedTracks) {
            acc = track.getCoveredRange().union(acc);
        }
        return acc;
    }
}
