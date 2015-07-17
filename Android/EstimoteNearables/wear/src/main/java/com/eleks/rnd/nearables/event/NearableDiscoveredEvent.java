package com.eleks.rnd.nearables.event;

import com.estimote.sdk.Nearable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bogdan.melnychuk on 16.07.2015.
 */
public class NearableDiscoveredEvent {
    private List<Nearable> nearables;

    public NearableDiscoveredEvent(List<Nearable> nearables) {
        this.nearables = nearables;
    }

    public int getTotal() {
        return getNearables().size();
    }

    public List<Nearable> getNearables() {
        if (nearables == null) {
            nearables = new ArrayList<>();
        }
        return nearables;
    }
}
