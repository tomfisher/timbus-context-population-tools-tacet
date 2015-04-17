package edu.teco.tacet.rdf;

import edu.teco.tacet.track.Datum;

public class RdfDatum extends Datum {

    public final String uri;
    
    public RdfDatum(long timestamp, double value, String uri) {
        super(timestamp, value);
        this.uri = uri;
    }
}
