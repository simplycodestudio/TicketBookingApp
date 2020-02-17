package org.Simplycodestudio.CinemaWorld.TicketBookingApp.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class CinemaUtils {

    private static final Integer MAX_SEATS_IN_ROW = 15;
    private static final Integer MAX_ROWS_SIZE = 6;

    public static Map<Integer, List<Integer>> getMapWithFreeSeats(Map<Integer, List<Integer>> reservedSeats) {
        Map<Integer, List<Integer>> resultMap = new HashMap<>();
        reservedSeats.forEach((row, reservedSeatsInRow) -> {
            Collections.sort(reservedSeatsInRow);
            List<Integer> resultOfFreeSlotsInRow = new ArrayList<>();
            Iterator<Integer> iterator = reservedSeatsInRow.iterator();
            boolean isFirstIteration = true;
            Integer currentSeatNumber = null;
            while (iterator.hasNext()) {
                if (isFirstIteration) {
                    currentSeatNumber = iterator.next();
                }
                if (currentSeatNumber > 2 && isFirstIteration) {
                    IntStream.range(1, currentSeatNumber).boxed().forEach(resultOfFreeSlotsInRow::add);
                }
                isFirstIteration = false;
                if (iterator.hasNext()) {
                    Integer nextSeatNumber = iterator.next();
                    if (nextSeatNumber - currentSeatNumber > 2) {
                        IntStream.range(currentSeatNumber + 1, nextSeatNumber).boxed().forEach(resultOfFreeSlotsInRow::add);
                    }
                    currentSeatNumber = nextSeatNumber;
                }
            }
            if (Objects.nonNull(currentSeatNumber) && MAX_SEATS_IN_ROW - currentSeatNumber > 1) {
                IntStream.rangeClosed(currentSeatNumber + 1, MAX_SEATS_IN_ROW).boxed().forEach(resultOfFreeSlotsInRow::add);
            }
            if (reservedSeatsInRow.isEmpty()) {
                IntStream.rangeClosed(1, MAX_SEATS_IN_ROW).boxed().forEach(resultOfFreeSlotsInRow::add);
            }
            resultMap.put(row, resultOfFreeSlotsInRow);
        });
        return resultMap;

    }

    public static boolean canReserveSeats(Map<Integer, List<Integer>> mapOfFreeSeats, int row, int... seats) {
        List<Integer> freeSeatsToReserve = mapOfFreeSeats.get(row);
        List<Integer> reservingSeats = Arrays.stream(seats).boxed().collect(Collectors.toList());
        return canReserveSeats(freeSeatsToReserve, reservingSeats);
    }

    public static boolean canReserveSeats(Map<Integer, List<Integer>> mapOfFreeSeats, Map<Integer, List<Integer>> mapOfReservingSeats) {
        return mapOfReservingSeats.entrySet().stream()
                .allMatch(entry -> canReserveSeats(mapOfFreeSeats.get(entry.getKey()), entry.getValue()));
    }

    public static boolean canReserveSeats(List<Integer> freeSeats, List<Integer> reservingSeats) {

        if (Objects.isNull(freeSeats) || freeSeats.isEmpty() || Objects.isNull(reservingSeats)) {
            return false;
        } else if (!freeSeats.containsAll(reservingSeats)) {
            return false;
        }

        freeSeats.removeAll(reservingSeats);
        if (freeSeats.isEmpty()) {
            return true;
        }

        Collections.sort(freeSeats);
        Collections.sort(reservingSeats);

        Iterator<Integer> iterator = freeSeats.iterator();

        while (iterator.hasNext()) {
            Integer currentFreeSeatNumber = iterator.next();
            if (iterator.hasNext()) {
                Integer nextFreeSeatNumber = iterator.next();
                boolean isFreeSlotsContainsPreviousSeatNumber = !freeSeats.contains(currentFreeSeatNumber - 1);
                boolean isNextSeatDifferentFromNextFree = currentFreeSeatNumber + 1 != nextFreeSeatNumber;
                if (isFreeSlotsContainsPreviousSeatNumber && isNextSeatDifferentFromNextFree) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Map<Integer, List<Integer>> copyMapToMap(Map<Integer, List<Integer>> mapFrom, Map<Integer, List<Integer>> mapTo) {
        return Stream.of(mapFrom, mapTo)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, CinemaUtils::collectToLists));
    }

    private static List<Integer> collectToLists(List<Integer> valueMapFrom, List<Integer> valueMapTo) {
        TreeSet<Integer> integers = new TreeSet<>(valueMapFrom);
        integers.addAll(valueMapTo);
        return new ArrayList<>(integers);
    }

    public static Map<Integer, List<Integer>> mockMapOfFreeSeats() {
        List<Integer> caseRow1 = Arrays.asList(1, 2, 3, 4);
        List<Integer> caseRow2 = Arrays.asList(5, 6, 7, 8);
        List<Integer> caseRow3 = Arrays.asList(9, 10, 11, 12);
        List<Integer> caseRow4 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        List<Integer> caseRow5 = Arrays.asList(1, 2, 5, 6, 9, 10);
        List<Integer> caseRow6 = Arrays.asList(3, 4, 7, 8, 11, 12, 15);
        List<Integer> caseRow7 = Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        List<Integer> caseRow8 = Arrays.asList(1, 4, 7, 10, 13);
        List<Integer> caseRow9 = Arrays.asList(3, 6, 9, 12, 15);
        List<Integer> caseRow10 = Arrays.asList(3, 4, 5, 6, 7);
        List<Integer> caseRow11 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15);
        List<Integer> caseRow12 = Arrays.asList(3, 7, 10, 15);
        List<Integer> caseRow13 = Arrays.asList(4, 7, 8, 10, 11);
        List<Integer> caseRow14 = Arrays.asList(1 ,2, 9, 10, 11, 12, 13, 14, 15);
        List<Integer> caseRow15 = Arrays.asList(5, 6, 7, 8);
        List<List<Integer>> listOfOccupiedSeats = Arrays
                .asList(caseRow1, caseRow2, caseRow3, caseRow4, caseRow5, caseRow6, caseRow7, caseRow8, caseRow9, caseRow10, caseRow11,
                        caseRow12, caseRow13, caseRow14, caseRow15);
        Random random = new Random();
        int rowsSize = random.nextInt(MAX_ROWS_SIZE) + 1;
        Map<Integer, List<Integer>> reservedSeats = new HashMap<>();
        Map<Integer, List<Integer>> mapWithFreeSeats = new HashMap<>();

        for (int i = 1; i <= rowsSize; i++) {
            reservedSeats.put(i, listOfOccupiedSeats.get(random.nextInt(listOfOccupiedSeats.size() - 1)));
        }
        mapWithFreeSeats = getMapWithFreeSeats(reservedSeats);
        return mapWithFreeSeats;
    }



}
