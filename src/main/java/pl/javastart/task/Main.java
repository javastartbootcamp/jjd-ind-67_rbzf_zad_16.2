package pl.javastart.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        // uzupełnij rozwiązanie. Korzystaj z przekazanego w parametrze scannera
        TimeZone localTimeZone = TimeZone.getDefault();
        List<String> listOfPatterns = Arrays.asList("yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy HH:mm:ss");
        String specialPattern = "yyyy-MM-dd";
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("Podaj datę:");
        String userInput = scanner.nextLine();

        boolean patternCheck = false;
        try {
            printForSpecialPattern(localTimeZone, userInput, specialPattern, formatter);
            patternCheck = true;
        } catch (DateTimeParseException e) {
//
        }

        for (String pat : listOfPatterns) {
            try {
                printForPattern(localTimeZone, userInput, pat, formatter);
                patternCheck = true;
                break;
            } catch (DateTimeParseException e) {
//
            }
        }

        if (!patternCheck) {
            System.out.println("Nieprawidlowy format daty.");
        }
    }

    private void printForPattern(TimeZone localTimeZone, String userInput, String pat, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = createLocalDateTimeFromPattern(userInput, pat);
        printAllTimeZones(localTimeZone, formatter, localDateTime);

    }

    private LocalDateTime createLocalDateTimeFromPattern(String userInput, String pat) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(pat);
        TemporalAccessor temporalAccessor = pattern.parse(userInput);
        return LocalDateTime.from(temporalAccessor);
    }

    private void printForSpecialPattern(TimeZone localTimeZone, String userInput, String specialPattern, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = createLocalDateTimeFromSpecialPattern(userInput, specialPattern);
        printAllTimeZones(localTimeZone, formatter, localDateTime);
    }

    private void printAllTimeZones(TimeZone localTimeZone, DateTimeFormatter formatter, LocalDateTime localDateTime) {
        printLocalTime(formatter, localDateTime);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(localTimeZone.getID()));
        printTimeInZone(zonedDateTime, "UTC", "UTC: ", formatter);
        printTimeInZone(zonedDateTime, "Europe/London", "Londyn: ", formatter);
        printTimeInZone(zonedDateTime, "America/Los_Angeles", "Los Angeles: ", formatter);
        printTimeInZone(zonedDateTime, "Australia/Sydney", "Sydney: ", formatter);
    }

    private static void printLocalTime(DateTimeFormatter formatter, LocalDateTime localDateTime) {
        System.out.print("Czas lokalny: ");
        System.out.println(localDateTime.format(formatter));
    }

    private LocalDateTime createLocalDateTimeFromSpecialPattern(String userInput, String specialPattern) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(specialPattern);
        TemporalAccessor temporalAccessor = pattern.parse(userInput);
        LocalDate localDate = LocalDate.from(temporalAccessor);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(specialPattern);
        return localDate.atTime(0, 0, 0);
    }

    private void printTimeInZone(ZonedDateTime zonedDateTime, String zoneId, String zoneName, DateTimeFormatter formatter) {
        ZonedDateTime inZone = zonedDateTime.withZoneSameInstant(ZoneId.of(zoneId));
        System.out.print(zoneName);
        System.out.println(inZone.format(formatter));
    }
}


