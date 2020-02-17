package org.Simplycodestudio.CinemaWorld.TicketBookingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TimesUpExcepion extends RuntimeException {

    public TimesUpExcepion(String message) {
        super(message);

    }
}
