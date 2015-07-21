package com.eleks.rnd.nearables.loader;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.eleks.rnd.nearables.database.DatabaseHelper;
import com.eleks.rnd.nearables.database.dao.PersonDao;
import com.eleks.rnd.nearables.loader.result.PersonLoaderResult;
import com.eleks.rnd.nearables.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import timber.log.Timber;

/**
 * Created by bogdan.melnychuk on 21.07.2015.
 */
public class PersonLoader extends BaseLoader<PersonLoaderResult> {
    public static final String USE_SERVER_DATA = "_argServerData";
    public static final String CONSTRAINT = "_argConstraint";

    public PersonLoader(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    public PersonLoaderResult loadInBackground() {
        PersonLoaderResult result = new PersonLoaderResult();
        Timber.d("Loading mates...");
        String constraint = getBundle().getString(CONSTRAINT);
        boolean useServerData = getBundle().getBoolean(USE_SERVER_DATA, false);
        Timber.d("Constraint: " + constraint);

        try {
            final PersonDao pDao = (PersonDao) DatabaseHelper.getHelper().getDaoInstance(PersonDao.class);
            if (useServerData) {
                final List<Person> serverData = loadDataFromServer();
                if(serverData != null && !serverData.isEmpty()) {
                    pDao.callInTransaction(new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            pDao.clearAll();
                            for (Person p : serverData) {
                                pDao.create(p);
                            }
                            return null;
                        }
                    });
                }
            }


            if (TextUtils.isEmpty(constraint)) {
                result.setPersons(pDao.queryForAll());
                return result;
            }
        } catch (Exception e) {
            result.setException(e);
        }
        return result;
    }

    private List<Person> loadDataFromServer() {
        List<Person> people = new ArrayList<>();

        Person p = new Person();
        p.setName("Bogdan Melnychuk");
        p.setTimestamp(new Date().getTime());
        p.setLocation("Kitchen");

        Person p1 = new Person();
        p1.setName("Bogdan Shubravyi");
        p1.setTimestamp(new Date().getTime());
        p1.setLocation("Gym");

        Person p2 = new Person();
        p2.setName("Iryna Pantel");
        p2.setTimestamp(new Date().getTime());
        p2.setLocation("Smoking room");

        Person p3 = new Person();
        p3.setName("Bruce Wayne");
        p3.setTimestamp(new Date().getTime());
        p3.setLocation("BatMobile");
        p3.setIsFavorite(true);

        Person p4 = new Person();
        p4.setName("Superman");
        p4.setTimestamp(new Date().getTime());
        p4.setLocation("Metropolis");

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
                boolean p1Fav = p1.isFavorite();
                boolean p2Fav = p2.isFavorite();
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
