package org.Simplycodestudio.CinemaWorld.TicketBookingApp.retrofit;


import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.MovieResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class RetrofitSession implements ApplicationListener<ContextRefreshedEvent> {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("variables");
    private static int PAGE = 12;
    private static String API_KEY = resourceBundle.getString("tmdb.key");
    private static String LANGUAGE = "en-US";
    private static String CATEGORY = "upcoming";
    private DbSaver dbSaver;
    private RetrofitClient retrofitClient;
    private Logger logger = LoggerFactory.getLogger(RetrofitSession.class);

    @Autowired
    public RetrofitSession(DbSaver dbSaver, RetrofitClient retrofitClient) {
        this.dbSaver = dbSaver;
        this.retrofitClient = retrofitClient;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        MovieService movieService = retrofitClient.getRetrofitClient().create(MovieService.class);

        Call<MovieResult> call = movieService.getMovieItems(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                MovieResult results = response.body();
                List<MovieResult.ResultsBean> listOfMovies = results.getResults();

                Map<String, String> moviesDetails = new HashMap<>();
                for (MovieResult.ResultsBean result :
                        listOfMovies) {
                    moviesDetails.put(result.getTitle(), result.getRelease_date());
                }
                dbSaver.saveMoviesToDB(moviesDetails);

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable throwable) {
                String message = throwable.getMessage();
                logger.info("failure with Retrofit session: " + message);
            }
        });
    }
}
