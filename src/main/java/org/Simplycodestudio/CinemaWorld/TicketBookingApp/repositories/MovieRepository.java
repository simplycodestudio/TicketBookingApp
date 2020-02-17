package org.Simplycodestudio.CinemaWorld.TicketBookingApp.repositories;


import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "select m from Movie m where m.projectionDate = ?1 and m.screeningStartTime = ?2 order by movie_title, screening_start_time")
    List<Movie> findMoviesByProjectionDate(String projectionDate, String time);
}
