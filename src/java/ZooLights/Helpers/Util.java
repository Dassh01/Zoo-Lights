package ZooLights.Helpers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.function.Function;

import ZooLights.Main;
import ZooLights.Objects.Date;
import ZooLights.Objects.Party;

public class Util {
    public static void arraylistToStr (ArrayList<Party> arrayList) {
        if (arrayList.isEmpty()) {
            System.out.println("No parties generated yet!");
        }
        for (Party party : arrayList) {
            System.out.println(party.getPartyName());
        }
    }

    public static modeOfTransport strToMode(String input) {
        input = input.toLowerCase().trim();
        return switch (input) {
            case "driving" -> modeOfTransport.DRIVING;
            case "train" -> modeOfTransport.TRAIN;
            case "walking" -> modeOfTransport.WALKING;
            default -> throw new IllegalArgumentException("""
                    Input should look like "driving", "train" or "walking"
                    (it isn't case sensitive or whitespace reliant!!)
                    """);
        };
    }
    //TODO: Documentation
    public static boolean isWeekend(Date date) {
        final int year = date.getYear(), month = date.getMonth(), day = date.getDay();
        LocalDate localDate = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static Date getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDateTime ldt = LocalDateTime.now();
        int dateAsNum = Integer.parseInt(dtf.format(ldt));
        String dateAsString = Integer.toString(dateAsNum);
        return strToDate(dateAsString); //Conversion is performed inline here
    }

    protected static Date strToDate(String dateAsString) {
        dateAsString = dateAsString.replaceAll("/","");
        String month = dateAsString.substring(0,2);
        String day = dateAsString.substring(2,4);
        String year = dateAsString.substring(4,8);
        if (Main.debug) {
            System.out.println("Sending back date with month = " + month + ", day = " + day + ", year = " + year);
        }
        return new Date(Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
    }

    /**
     * Comes from retyping user prompts and scanner methods over and over in APCSA projects :C
     * Used to simplify above-mentioned process, does the prompt and scan all in one (now with an embedded try catch!) (InputMismatch be gone!)
     * @param askText [String] Prompt the user is asked in console
     * @param inputFunction [Scanner] lambda method (i.e: Scanner::nextLine, Scanner::nextInt), dependent on what data type you want the function to return as its return type is generic
     * @param scanner [Scanner] (hopefully connected to System input...)
     * @return Returns data type dependent on the scanner lambda inserted
     */
    protected static <T> T askForThing(String askText, Function<Scanner, T> inputFunction, Scanner scanner) {
        System.out.println(askText);
        T returningInformation;
        while (true) {
            try {
                returningInformation = inputFunction.apply(scanner);
                if (returningInformation instanceof Integer) {
                    scanner.nextLine();
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println(askText);
                continue;
            }
            break;
        }
        return returningInformation;
    }

    /**
     * Interprets a string into a boolean. Worked with a (yes/no) or a (y/n) prompt, case-insensitive
     * @param userInput [String] Raw user input as answer to a prompt similar to the ones listed above
     * @return [Boolean] true IF user inputted something that looks like a yes, false if user inputted something that looks something other than a yes
     */
    protected static boolean userInputBoolean(String userInput) {
        String formattedUserInput = userInput.toLowerCase().trim();
        return formattedUserInput.equals("y") || formattedUserInput.contains("yes");
    }

    /**
     * Interprets a string into a boolean. Worked with a (yes/no) or a (y/n) prompt, case-insensitive.
     * The more manual/specific version that allows you to choose values that will return true
     * @param UserInput [String] Raw user input as answer to a prompt similar to the ones listed above
     * @param trueValues [HashSet] Values that, if user has entered the same, will make the method return true
     * @return [Boolean] true IF user inputted something that looks like a yes by the standards of the HashSet, false if user inputted something that looks something other than a yes
     */
    protected static boolean userInputBoolean(String UserInput, HashSet<String> trueValues) {
        String formattedUserInput = UserInput.toLowerCase().trim();
        return trueValues.contains(formattedUserInput);
    }
}