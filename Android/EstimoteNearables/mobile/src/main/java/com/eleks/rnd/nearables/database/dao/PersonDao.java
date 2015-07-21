package com.eleks.rnd.nearables.database.dao;

import com.eleks.rnd.nearables.model.Person;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class PersonDao extends BaseDao<Person> {
    public PersonDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Person.class);
    }


}
