package org.Simplycodestudio.CinemaWorld.TicketBookingApp.retrofit;

import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.MovieResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("/3/movie/{category}")
    Call<MovieResult> getMovieItems(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page);

}
