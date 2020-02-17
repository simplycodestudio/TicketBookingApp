package org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils.CinemaUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RoomDto {

    @JsonIgnore
    private final Long id;
    private final String roomName;
    private final String freeSeatsOfRoom;
    @JsonIgnore
    private final List<Movie> movie;

    public RoomDto(Room room) {
        this.id = room.getId();
        this.roomName = room.getRoomName();
        this.freeSeatsOfRoom = String.valueOf(CinemaUtils.getMapWithFreeSeats(convertStringMaptoHashMap(room.getReservingSeatsMap())));
        this.movie = room.getMovie();
    }

    public static Map<Integer, List<Integer>> convertStringMaptoHashMap(String stringMap) {
        Type empMapType = new TypeToken<Map<Integer, List<Integer>>>() {}.getType();
        return new Gson().fromJson(stringMap, empMapType);
    }
}
