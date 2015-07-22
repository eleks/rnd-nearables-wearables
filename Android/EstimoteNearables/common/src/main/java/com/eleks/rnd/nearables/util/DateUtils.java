package com.eleks.rnd.nearables.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by bogdan.melnychuk on 09.07.2015.
 */
public class DateUtils {
    /**
     * Get a diff between two dates
     *
     * @param date1    the oldest date
     * @param date2    the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Map<TimeUnit, Long> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        Map<TimeUnit, Long> result = new LinkedHashMap<>();
        long milliesRest = diffInMillies;
        for (TimeUnit unit : units) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit, diff);
        }
        return result;
    }

    public static String getDateDiffMessage(Date prevDate) {
        Map<TimeUnit, Long> timeDiff = DateUtils.computeDiff(prevDate, new Date());
        StringBuilder s = new StringBuilder();
        long hrs = timeDiff.get(TimeUnit.HOURS);
        if (hrs > 0) {
            s.append(timeDiff.get(TimeUnit.HOURS));
            s.append(" h, ");
        }
        long min = timeDiff.get(TimeUnit.MINUTES);
        if (min > 0) {
            s.append(timeDiff.get(TimeUnit.MINUTES));
            s.append(" m ago");
        }
        if (s.length() == 0) {
            s.append("Just Now");
        }
        return s.toString();
    }
}
