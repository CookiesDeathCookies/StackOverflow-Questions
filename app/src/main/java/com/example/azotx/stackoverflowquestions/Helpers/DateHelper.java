package com.example.azotx.stackoverflowquestions.Helpers;

import java.util.Date;

public final class DateHelper {
    private final static long DAY = 24 * 60 * 60; // секунды в одном дне
    private final static long WEEK = 7 * DAY;
    private final static long OFFSET = WEEK;

    public static long getRangeStart() {
        long currentDate = (new Date()).getTime() / 1000;
        return currentDate - OFFSET;
    }
}
