package com.eleks.rnd.nearables.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Bogdan Melnychuk on 7/20/15.
 */
public class Person {
    private String name;
    private long timestamp;
    private String location;
    private int empId;
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public static List<Person> getPersons() {
        List<Person> people = new ArrayList<>();

        Person p = new Person();
        p.name = "Bogdan Melnychuk";
        p.timestamp = new Date().getTime();
        p.location = "Kitchen";

        Person p1 = new Person();
        p1.name = "Bogdan Shubravyi";
        p1.timestamp = new Date().getTime();
        p1.location = "Gym";

        Person p2 = new Person();
        p2.name = "Iryna Pantel";
        p2.timestamp = new Date().getTime();
        p2.location = "Smoking room";

        Person p3 = new Person();
        p3.name = "Bruce Wayne";
        p3.timestamp = new Date().getTime();
        p3.location = "BatMobile";
        p3.isFavorite = true;

        Person p4 = new Person();
        p4.name = "Superman";
        p4.timestamp = new Date().getTime();
        p4.location = "Metropolis";

        people.add(p);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);

        people.add(p);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);

        people.add(p);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);

        Collections.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                boolean p1Fav = p1.isFavorite;
                boolean p2Fav = p2.isFavorite;
                if (p1Fav && !p2Fav) {
                    return -1;
                } else if (p2Fav && !p1Fav) {
                    return 1;
                } else {
                    return p1.getName().compareTo(p2.getName());
                }


            }
        });

        return people;
    }
}
