package edu.teco.tacet.track;

public class ForbiddenEditException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ForbiddenEditException(String message) {
        super(message);
    }
}
