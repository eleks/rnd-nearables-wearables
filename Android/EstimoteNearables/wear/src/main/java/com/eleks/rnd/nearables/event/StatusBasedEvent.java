package com.eleks.rnd.nearables.event;

/**
 * Created by bogdan.melnychuk on 16.07.2015.
 */
public class StatusBasedEvent {
    private Exception exception;

    public boolean wasSuccessful() {
        return exception == null;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
