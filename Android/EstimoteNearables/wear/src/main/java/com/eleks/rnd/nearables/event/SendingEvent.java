package com.eleks.rnd.nearables.event;

import com.estimote.sdk.Nearable;

/**
 * Created by bogdan.melnychuk on 16.07.2015.
 */
public class SendingEvent extends StatusBasedEvent {
    private Nearable nearable;

    public SendingEvent(Nearable nearable) {
        this.nearable = nearable;
    }

    public SendingEvent(Exception e, Nearable nearable) {
        this.nearable = nearable;
        setException(e);
    }

    public Nearable getNearable() {
        return nearable;
    }
}
