package org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils;



import org.Simplycodestudio.CinemaWorld.TicketBookingApp.exceptions.IncorrectGivenDataException;

import java.util.Arrays;

public enum AgeRange {

    CHILD(1, 18),
    STUDENT(19, 25),
    ADULT(26,120);

    private final int minValue;
    private final int maxValue;

    AgeRange(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
    }

    public static AgeRange getAgeGroup(int age){
        return Arrays.stream(AgeRange.values())
                .filter(t -> (age >= t.minValue && age <= t.maxValue))
                .findAny().orElseThrow(() -> new IncorrectGivenDataException("Age: " + age + " is not match to any age group"));
    }
}
