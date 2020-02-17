package org.Simplycodestudio.CinemaWorld.TicketBookingApp.repositories;


import org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "select r from Room r where r.roomName = ?1")
    Room findRoomByName(String name);
}
