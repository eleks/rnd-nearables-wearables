package com.eleks.rnd.nearables.database.dao;

import com.eleks.rnd.nearables.model.Person;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class PersonDao extends BaseDao<Person> {
    public PersonDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Person.class);
    }

    public List<Person> findByName(String name) throws SQLException {
        final StringBuilder where = new StringBuilder();
        where.append("name like '%");
        where.append(name);
        where.append("%'");

        return queryBuilder().where().raw(where.toString()).query();
    }


}
