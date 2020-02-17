package org.Simplycodestudio.CinemaWorld.TicketBookingApp

import org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils.CinemaUtils
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CinemaUtilsTest extends Specification {

    CinemaUtils sut = []

    @Shared
    Map<Integer, List<Integer>> reservedSeatsMap = createReservedSeatsMap()
    @Shared
    Map<Integer, List<Integer>> freeSeatsMap = createFreeSeatsMap()

    def "getMapWithFreeSeats"() {

        when:
        Map<Integer, List<Integer>> result = CinemaUtils.getMapWithFreeSeats(reservedSeatsMap as Map<Integer, List<Integer>>)

        then:
        result == freeSeatsMap

    }


    @Unroll
    def "Test should return #expected when reserve #seats seats in #row row"() {

        when:
        def result = sut.canReserveSeats(freeSeatsMap as Map<Integer, List<Integer>>, row as int, seats as int[])

        then:
        result == expected

        where:
        row | seats                                 || expected
        8   | [1, 2, 3]                             || true
        8   | [1, 3]                                || false
        8   | [1, 2, 3, 5]                          || false
        3   | []                                    || true
        3   | [2, 3]                                || true
        3   | [2]                                   || false
        3   | [3]                                   || false
        9   | [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13] || true

    }

    def "Test should return true when reserving all free seats"() {
        setup:
        Map<Integer, List<Integer>> extendFreeSeatsMap = createFreeSeatsMap()
        extendFreeSeatsMap.put(1, [1, 2])
        extendFreeSeatsMap.put(2, [3, 4])

        when:
        def result = sut.canReserveSeats(extendFreeSeatsMap as Map<Integer, List<Integer>>, extendFreeSeatsMap as Map<Integer, List<Integer>>)

        then:
        result
    }

    @Unroll
    @Ignore
    def "Test should return #expected when free seats are #freeSeats and reserving seats: #reservingSeats"() {

        when:
        def result = sut.canReserveSeats(freeSeats as List<Integer>, reservingSeats as List<Integer>)

        then:
        result == expected

        where:
        freeSeats            | reservingSeats       || expected
        [1, 2, 3]            | [1, 3]               || false
        [1, 2, 3]            | [1]                  || true
        [1, 2, 3]            | [2]                  || false
        [1, 2, 3]            | [3]                  || true
        [1, 2, 3]            | [1, 2]               || false
        [1, 2, 3]            | [2, 3]               || false
        [1, 2, 3]            | [1, 3]               || false
        [1, 2, 3, 4]         | [1, 3]               || false
        [1, 2, 3, 5, 6]      | [1, 3]               || false
        [1, 2, 3, 5, 6]      | [1, 2, 3]            || true
        [15, 3, 7, 2, 8, 14] | [7, 14, 8, 15, 3, 2] || true
        [1, 2]               | []                   || true
        []                   | []                   || false
        null                 | []                   || false
        [1]                  | null                 || false

    }


    private static HashMap<Integer, List<Integer>> createFreeSeatsMap() {
        Map<Integer, List<Integer>> resultMap
        resultMap = new HashMap<>();
        resultMap.put(1, [])
        resultMap.put(2, [])
        resultMap.put(3, [2, 3, 5, 6, 8, 9, 11, 12, 14, 15])
        resultMap.put(4, [2, 3, 4, 6, 7, 8, 10, 11, 12, 14, 15])
        resultMap.put(5, [2, 3, 4, 5, 7, 8, 9, 10, 12, 13, 14, 15])
        resultMap.put(6, [2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 14, 15])
        resultMap.put(7, [2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14])
        resultMap.put(8, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15])
        resultMap.put(9, [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13])
        resultMap.put(10, [1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15])
        resultMap.put(11, [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15])
        return resultMap
    }

    private static HashMap<Integer, List<Integer>> createReservedSeatsMap() {
        Map<Integer, List<Integer>> reservedSlots
        reservedSlots = new HashMap<>()
        reservedSlots.put(1, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15])
        reservedSlots.put(2, [1, 3, 5, 7, 9, 11, 13, 15])
        reservedSlots.put(3, [1, 4, 7, 10, 13])
        reservedSlots.put(4, [1, 5, 9, 13])
        reservedSlots.put(5, [1, 6, 11])
        reservedSlots.put(6, [1, 7, 13])
        reservedSlots.put(7, [1, 8, 15])
        reservedSlots.put(8, [])
        reservedSlots.put(9, [2, 14])
        reservedSlots.put(10, [3, 13])
        reservedSlots.put(11, [1])
        return reservedSlots
    }
}