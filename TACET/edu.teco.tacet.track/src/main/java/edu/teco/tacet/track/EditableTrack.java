package edu.teco.tacet.track;

public interface EditableTrack<T> extends Track<T> {

    boolean insertData(T data);

    void deleteData(Range range);

    /**
     * Returns the original source track of this Track. The source track represents the data on this
     * track before any editing took place.
     *
     * @return the source track
     */
    Track<? extends T> getSourceTrack();
}
