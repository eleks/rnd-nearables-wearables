package com.eleks.rnd.nearables.loader.result;

import com.eleks.rnd.nearables.loader.BaseLoader;
import com.eleks.rnd.nearables.model.Person;

import java.util.List;

/**
 * Created by bogdan.melnychuk on 21.07.2015.
 */
public class PersonLoaderResult extends BaseLoader.LoaderResult {
    private List<Person> persons;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
