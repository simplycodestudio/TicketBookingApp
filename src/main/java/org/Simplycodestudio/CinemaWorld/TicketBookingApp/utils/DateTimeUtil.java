package org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils;


import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ResourceBundle;

public class DateTimeUtil {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("variables");
    private static Boolean isMockTime = Boolean.parseBoolean(resourceBundle.getString("mock.time"));
    private static String mockedTimeValue = resourceBundle.getString("mocked.time.value");

    public static long checkRemainingTime(String screeningStartTimeFromDb) {

        LocalTime currentTime = new LocalTime();
        DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("HH:mm").toFormatter();
        LocalTime parsedScreeningStartTime = LocalTime.parse(screeningStartTimeFromDb, parseFormat);
        if (isMockTime) {
            currentTime = LocalTime.parse(mockedTimeValue, parseFormat);
        }

        long diff = parsedScreeningStartTime.getMillisOfDay() - currentTime.getMillisOfDay();

        return new Duration(diff).getStandardMinutes();
    }
}
