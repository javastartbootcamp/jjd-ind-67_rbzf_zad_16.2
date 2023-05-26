package pl.javastart.task;

import javax.swing.text.DateFormatter;
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
        System.out.println("Podaj datę:");
        String userInput = scanner.nextLine();

        TimeZone localTimeZone = TimeZone.getDefault();

        List<String> listOfPatterns = Arrays.asList("yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy HH:mm:ss", "yyyy-MM-dd");

        boolean patternCheck = false;
        for (String pat : listOfPatterns) {
            try {
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern(pat);
                TemporalAccessor temporalAccessor = pattern.parse(userInput);
                LocalDateTime localDateTime;
                DateTimeFormatter formatter;
                if (pat.equals("yyyy-MM-dd")) {
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.from(temporalAccessor);
                    System.out.print("Czas lokalny: ");
                    System.out.println(localDate.format(formatter) + " 00:00:00");
                    localDateTime = localDate.atTime(0, 0, 0);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                } else {
                    System.out.print("Czas lokalny: ");
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    localDateTime = LocalDateTime.from(temporalAccessor);
                    System.out.println(localDateTime.format(formatter));
                }
                ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(localTimeZone.getID()));

                timeInZone(zonedDateTime, "UTC", "UTC: ", formatter);
                timeInZone(zonedDateTime, "Europe/London", "Londyn: ", formatter);
                timeInZone(zonedDateTime, "America/Los_Angeles", "Los Angeles: ", formatter);
                timeInZone(zonedDateTime, "Australia/Sydney", "Sydney: ", formatter);

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

    private static void timeInZone(ZonedDateTime zonedDateTime, String zoneId, String zoneName, DateTimeFormatter formatter) {
        ZonedDateTime inZone = zonedDateTime.withZoneSameInstant(ZoneId.of(zoneId));
        System.out.print(zoneName);
        System.out.println(inZone.format(formatter));
    }
}


