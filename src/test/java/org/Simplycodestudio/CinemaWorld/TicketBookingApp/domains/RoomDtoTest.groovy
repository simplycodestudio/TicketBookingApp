package org.Simplycodestudio.CinemaWorld.TicketBookingApp.domains


import spock.lang.Specification

class RoomDtoTest extends Specification {

    def "Test should pass when OutputHashMap has the same value as InputStringMap"() {

        when:
        String inputStringMap = "{2:[1,2,5,6,9,10,13,14]}"
        Map<Integer, List<Integer>> outputHashMap = new HashMap<>()
        outputHashMap.put(2, Arrays.asList(1,2,5,6,9,10,13,14));

        def result = RoomDto.convertStringMaptoHashMap(inputStringMap as String)

        then:
        result == outputHashMap
    }



}
