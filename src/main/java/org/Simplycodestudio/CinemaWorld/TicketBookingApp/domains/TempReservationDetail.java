package org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TempReservationDetail {

    @NonNull
    private double totalAmount;
    @NonNull
    private String leftTime;
}
