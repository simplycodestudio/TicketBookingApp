package org.Simplycodestudio.CinemaWorld.TicketBookingApp;



import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.Movie;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.Room;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.RoomDto;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.TempReservationDetail;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.exceptions.ComponentNotFoundException;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.exceptions.IncorrectGivenDataException;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.exceptions.TimesUpExcepion;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.repositories.MovieRepository;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.repositories.RoomRepository;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils.CinemaUtils;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils.DateTimeUtil;
import org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils.PersonalDataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
public class BookingResource {

    private Logger logger = LoggerFactory.getLogger(BookingResource.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;


    @GetMapping("/movies")
    public List<Movie> retrieveAllMovies() throws ParseException {
        return movieRepository.findAll();
    }

    @GetMapping("/movies/selected")
    public List<Movie> retrieveSelectedMovies(@RequestHeader(name = "date") String date,
                                              @RequestHeader(name = "time") String time) throws ParseException {
        return movieRepository.findMoviesByProjectionDate(date, time);
        //
    }


    @GetMapping("/screeningdetails/{movieId}")
    public RoomDto giveInformationsAboutScreeningRoomAndAvailableSeats(@PathVariable long movieId) {

        Optional<Movie> movieById = movieRepository.findById(movieId);

        if (!movieById.isPresent()) {
            throw new ComponentNotFoundException("movie with id: " + movieId + " not exists");
        }
        Room roomByName = roomRepository.findRoomByName(movieById.get().getRoom().getRoomName());
        return new RoomDto(roomByName);

    }

    @PutMapping("/ticketbooking/{movieId}")
    public TempReservationDetail bookATicket(@PathVariable(required = true) long movieId,
                                             @RequestHeader(name = "firstname", required = true) String firstName,
                                             @RequestHeader(name = "lastname", required = true) String lastName,
                                             @RequestHeader(name = "age", required = false, defaultValue = "30") Integer age,
                                             @RequestHeader(name = "row", required = true) Integer row,
                                             @RequestHeader(name = "seat", required = true) Integer... seat) {

        Optional<Movie> movie = movieRepository.findById(movieId);

        if (!movie.isPresent()) {
            throw new ComponentNotFoundException("movie with id: " + movieId + " not exists");
        }

        if (!PersonalDataValidator.validateFirstName(firstName)) {
            throw new IncorrectGivenDataException("First name form is incorrect");
        } else if (!PersonalDataValidator.validateLastName(lastName)) {
            throw new IncorrectGivenDataException("Last name form is incorrect");
        }

        double singleTicketPrice = PersonalDataValidator.chooseTicketByAge(age);
        int quantityOfBookingSeats = seat.length;
        double totalTicketAmount = PersonalDataValidator.getTotalTicketAmount(quantityOfBookingSeats, singleTicketPrice);

        Map<Integer, List<Integer>> mapOfBookingRequest = new HashMap<>();
        mapOfBookingRequest.put(row, Arrays.asList(seat));

        Optional<Room> room = roomRepository.findById(movie.get().getRoom().getId());

        if (room.isPresent()) {
            Map<Integer, List<Integer>> reservingMap = RoomDto.convertStringMaptoHashMap(room.get().getReservingSeatsMap());

            boolean canReserveSeats = CinemaUtils.canReserveSeats(CinemaUtils.getMapWithFreeSeats(reservingMap), mapOfBookingRequest);
            logger.info("Can do reservation?" + String.valueOf(canReserveSeats));

            if (canReserveSeats) {
                Map<Integer, List<Integer>> yetReservedRow = CinemaUtils.copyMapToMap(mapOfBookingRequest, reservingMap);

                room.get().setReservingSeatsMap(String.valueOf(yetReservedRow));
                roomRepository.save(room.get());

                long minutesLeft = DateTimeUtil.checkRemainingTime(movie.get().getScreeningStartTime());
                long minutesToEndReservation = minutesLeft - 15;
                long hoursLeft = minutesToEndReservation /60;
                long minutesRemaining = minutesToEndReservation % 60;


                if (minutesLeft < 15 && minutesLeft > 0) {
                    throw new TimesUpExcepion("Reservation time expired");
                }
                if (minutesLeft <= 0) {
                    throw new TimesUpExcepion("The movie has already begun");
                }

                String leftTimeMessage = "Seat reservation expires 15 minutes before the screening begins. The remaining time to end of ticket reservation is: "
                        + String.valueOf(hoursLeft > 0 ? hoursLeft + " hours " : "")
                        + minutesRemaining + " minutes";

                logger.info("reserved seats for room: " + room.get().getId() + ": " + RoomDto.convertStringMaptoHashMap(room.get().getReservingSeatsMap()));
                logger.info("free seats for room: " + room.get().getId() + ": " + CinemaUtils.getMapWithFreeSeats(RoomDto.convertStringMaptoHashMap(room.get().getReservingSeatsMap())));
                return new TempReservationDetail(totalTicketAmount, leftTimeMessage);
            }
            throw new IncorrectGivenDataException("You can't book this seat");
        }
        return null;
    }


}