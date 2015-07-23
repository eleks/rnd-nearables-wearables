package com.eleks.rnd.nearables.model;

/**
 * Created by bogdan.melnychuk on 09.07.2015.
 */
public class Movement {
    private int employeeId;
    private String stickerUuid;
    private String location;
    private long timestamp;
    private String employeeName;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getStickerUuid() {
        return stickerUuid;
    }

    public void setStickerUuid(String stickerUuid) {
        this.stickerUuid = stickerUuid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movement movement = (Movement) o;

        return employeeId == movement.employeeId;

    }

    @Override
    public int hashCode() {
        return employeeId;
    }
}

