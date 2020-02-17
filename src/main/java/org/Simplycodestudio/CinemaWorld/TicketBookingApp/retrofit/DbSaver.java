package org.Simplycodestudio.CinemaWorld.TicketBookingApp.retrofit;

import com.google.gson.Gson;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.Movie;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.Room;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.repositories.MovieRepository;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.repositories.RoomRepository;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils.CinemaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class DbSaver {
  @Autowired
  private MovieRepository movieRepository;
  @Autowired
  private RoomRepository roomRepository;


  public void saveMoviesToDB(Map<String, String> moviesDetails){
    List<Movie> movies = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();
    List<String> screeningStartTime = Arrays.asList("12:00","15:00","18:00","21:00");
    List<String> roomNames = Arrays.asList("North Room","South Room","East Room","West Room");

    for (String roomName :roomNames) {
      Map<Integer, List<Integer>> mapOfReservedSeat = CinemaUtils.mockMapOfFreeSeats();
      Room room = new Room(roomName, new Gson().toJson(mapOfReservedSeat));
      rooms.add(room);
    }
    roomRepository.saveAll(rooms);

    for (Map.Entry<String, String> entry: moviesDetails.entrySet()) {
      for (int i=0; i<screeningStartTime.size(); i++) {
        Movie movie = new Movie(entry.getValue(),entry.getKey(), screeningStartTime.get(i));
        movie.setRoom(rooms.get(i));
        movies.add(movie);
      }
    }
    movieRepository.saveAll(movies);
  }

}
