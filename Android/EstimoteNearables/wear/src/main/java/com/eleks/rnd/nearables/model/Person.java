package com.eleks.rnd.nearables.model;

/**
 * Created by bogdan.melnychuk on 09.07.2015.
 */
public class Person {
    private String name;
    private String location;
    private int id;
    private long lastActivity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }
}

