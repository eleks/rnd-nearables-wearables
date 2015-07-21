package com.eleks.rnd.nearables.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Bogdan Melnychuk on 7/20/15.
 */
@DatabaseTable(tableName = "Person")
public class Person {
    @DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = "_id", generatedId = true)
    private long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private long timestamp;
    @DatabaseField
    private String location;
    @DatabaseField
    private int empId;
    @DatabaseField
    private boolean isFavorite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
