package org.Simplycodestudio.CinemaWorld.TicketBookingApp.retrofit;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Component
public class RetrofitClient {

    private static String BASE_URL = "https://api.themoviedb.org";

    public Retrofit getRetrofitClient() {
        OkHttpClient httpClient = new OkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

    }
}
