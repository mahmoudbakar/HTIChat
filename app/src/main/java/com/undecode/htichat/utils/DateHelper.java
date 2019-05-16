package com.undecode.htichat.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {
    public long getTimeOfDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
        long unixTime = 0;
        try {
            unixTime = dateFormat.parse(date).getTime();
            unixTime = unixTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTime;
    }
}
