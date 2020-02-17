package org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String projectionDate;

    @NonNull
    private String movieTitle;
    

    @NonNull
    private String screeningStartTime;

    @JsonIgnore
    @ManyToOne
    private Room room;






}
