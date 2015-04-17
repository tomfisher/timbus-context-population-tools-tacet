package edu.teco.tacet.track;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;

public class UniqueDataDecorator<T> extends DefaultTrackDecorator<T> {

    private NavigableSet<T> uniqueData;

    public UniqueDataDecorator(Track<T> trackToWrap) {
        this(trackToWrap, null);
    }

    public UniqueDataDecorator(Track<T> trackToWrap, Comparator<T> comp) {
        super(trackToWrap);
        this.uniqueData = new TreeSet<T>(comp);
    }

    @Override
    protected T handleDatum(T datum) {
        uniqueData.add(datum);
        return super.handleDatum(datum);
    }

    public NavigableSet<T> getUniqueData() {
        return uniqueData;
    }
}
