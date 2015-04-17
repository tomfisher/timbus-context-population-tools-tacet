package edu.teco.tacet.rdf;

import edu.teco.tacet.track.Annotation;
import edu.teco.tacet.track.Range;

public class RdfAnnotation extends Annotation {

    private final String uri;

    public RdfAnnotation(Range range, String label, String description, String uri) {
        super(range, label, description);
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
