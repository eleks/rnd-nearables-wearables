package com.eleks.rnd.nearables.util;

import com.eleks.rnd.nearables.model.Person;

import java.util.Collection;

/**
 * Created by bogdan.melnychuk on 22.07.2015.
 */
public class PersonUtils {
    public static boolean containsFavorite(Collection<Person> collection) {
        for(Person p : collection) {
            if(p.isFavorite()) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsNonFavorite(Collection<Person> collection) {
        for(Person p : collection) {
            if(!p.isFavorite()) {
                return true;
            }
        }
        return false;
    }
}
