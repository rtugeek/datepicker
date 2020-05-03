package com.rtugeek.datepicker.bizs.calendars;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Date;

/**
 * Created by leon on 16-3-22.
 */
public class Solar {
    public int dayOfMonth;
    public int month;
    public int year;

    public Solar() {
    }

    public Solar(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + dayOfMonth;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date toDate() {
        return LunarSolarConverter.toDate(year, month, dayOfMonth);
    }

    public DateTime toDateTime() {
        return new LocalDate(year, month, dayOfMonth).toDateTimeAtStartOfDay();
    }

    public LocalDate toLocalDate() {
        return new LocalDate(year, month, dayOfMonth);
    }
}
