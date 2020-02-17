package org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"id"})
public class Room {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String roomName;

    @NonNull
    private String reservingSeatsMap;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Movie> movie;
}
