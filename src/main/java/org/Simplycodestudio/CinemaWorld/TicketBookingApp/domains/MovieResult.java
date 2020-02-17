package org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieResult {
    private List<ResultsBean> results;

    @Getter
    public static class ResultsBean {
        private String title;
        private String release_date;
    }
}
