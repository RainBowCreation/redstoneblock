package net.rainbowcreation.serverExtension.utils;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Time {
    public static long[] secondToMinute(long second) {
        long[] lst = new long[2];

        lst[0] = second/60;
        lst[1] = second%60;
        return (lst);
    }

    public static int[] getCurrentTime() {
        int[] lst = new int[3];

        ZonedDateTime time = ZonedDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.of("+07:00")));
        lst[0] = time.getHour();
        lst[1] = time.getMinute();
        lst[2] = time.getSecond();
        return lst;
    }
}