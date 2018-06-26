package com.example.azotx.stackoverflowquestions.Helpers;

import android.util.Pair;

import java.util.Date;

public final class DateHelper {
    final static long DAY = 24 * 60 * 60; // секунды в одном дне
    final static long WEEK = 7 * DAY;
    final static long OFFSET = WEEK;

    public static Pair<Long, Long> getRange() {
        long currentDate = (new Date()).getTime() / 1000;  // Секунды
        return new Pair<Long, Long>(currentDate - OFFSET, currentDate);
    }
}
