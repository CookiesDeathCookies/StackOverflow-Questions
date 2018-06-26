package com.example.azotx.stackoverflowquestions.Helpers;

import java.util.Date;

public final class DateHelper {
    final static long DAY = 24 * 60 * 60; // секунды в одном дне
    final static long WEEK = 7 * DAY;
    final static long OFFSET = WEEK;

    public static long getRangeStart() {
        long currentDate = (new Date()).getTime() / 1000;
        return currentDate - OFFSET;
    }
}
