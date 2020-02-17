package org.Simplycodestudio.CinemaWorld.TicketBookingApp

import org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils.PersonalDataValidator
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class PersonalDataValidatorTest extends Specification {

    @Shared
    PersonalDataValidator personalDataValidator = new PersonalDataValidator();


    @Unroll
    def "Test should return #expected when first name is #firstName"() {

        when:
        def result = personalDataValidator.validateFirstName(firstName as String)

        then:
        result == expected

        where:
        firstName || expected
        "Jan"     || true
        "Jacek"   || true
        "Ja"      || false
        "janek"   || false
        "ja"      || false
        "ja1"     || false
        " Jan"    || false
    }

    @Unroll
    def "Test should return #expected when last name is #lastName"() {

        when:
        def result = personalDataValidator.validateLastName(lastName as String)

        then:
        result == expected

        where:
        lastName           || expected
        "Wójcik"           || true
        "wójcik"           || false
        "Wójcik-Zielińska" || true
        "wójcik-zielińska" || false
        "Wó-Zi"            || false
        "Wójcik-"          || false

    }

    @Unroll
    def "Test should return #expected when age is #age"() {
        when:
        def result = personalDataValidator.chooseTicketByAge(age as int)

        then:
        result == expected

        where:
        age || expected
        10  || 12.5
        20  || 18
        30  || 25
    }
}
