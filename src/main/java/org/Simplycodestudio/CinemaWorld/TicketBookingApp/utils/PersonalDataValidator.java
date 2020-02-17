package org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils;


import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalDataValidator {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("variables");
    private static String firstNameExpression = resourceBundle.getString("first.name.regex");
    private static String lastNameExpression = resourceBundle.getString("last.name.regex");

    public static boolean validateFirstName(String firstName) {
        Pattern pattern = Pattern.compile(firstNameExpression);
        Matcher matcher = pattern.matcher(firstName);
        return  matcher.matches();
    }

    public static boolean validateLastName(String lastName) {
        Pattern pattern = Pattern.compile(lastNameExpression);
        Matcher matcher = pattern.matcher(lastName);
        return  matcher.matches();
    }

    public static double chooseTicketByAge(Integer age){
        switch (AgeRange.getAgeGroup(age)) {
            case CHILD:
                return 12.5;

            case STUDENT:
                return 18;

            case ADULT:
                return 25;
        }
      return 0;
    }

    public static double getTotalTicketAmount(int quantity, double ticketAmount){
        return quantity*ticketAmount;
    }

}
