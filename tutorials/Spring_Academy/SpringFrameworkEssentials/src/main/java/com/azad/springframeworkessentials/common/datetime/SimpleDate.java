package com.azad.springframeworkessentials.common.datetime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimpleDate implements Serializable {

    private static final long serialVersionUID = -1L;

    private GregorianCalendar base;

    public SimpleDate(int month, int day, int year) {
        init(new GregorianCalendar(year, month - 1, day));
    }

    SimpleDate(long time) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        init(cal);
    }

    private SimpleDate() {
        init(new GregorianCalendar());
    }

    public Date asDate() {
        return base.getTime();
    }

    public long inMilliseconds() {
        return asDate().getTime();
    }

    public int compareTo(Object date) {
        SimpleDate other = (SimpleDate) date;
        return asDate().compareTo(other.asDate());
    }

    private void init(GregorianCalendar cal) {
        this.base = trimToDays(cal);
    }

    private GregorianCalendar trimToDays(GregorianCalendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    @Override
    public boolean equals(Object day) {
        if (!(day instanceof SimpleDate)) {
            return false;
        }
        SimpleDate other = (SimpleDate) day;
        return base.equals(other.base);
    }

    public int hashCode() {
        return 29 * base.hashCode();
    }

    public static SimpleDate today() {
        return new SimpleDate();
    }

    public static SimpleDate valueOf(Date date) {
        return valueOf(date.getTime());
    }

    public static SimpleDate valueOf(long time) {
        return new SimpleDate(time);
    }

    public String toString() {
        return new SimpleDateFormat().format(base.getTime());
    }
}
