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
import ZooLights.Objects.Guest;
import ZooLights.Objects.Party;
import ZooLights.Objects.TicketGroup;

public class Utils {

    public static final String dasshTag = """
            VERSION B.
            //======================================\\\\
            ||  ____                _      ___  _   ||
            || |  _ \\  __ _ ___ ___| |__  / _ \\/ |  ||
            || | | | |/ _` / __/ __| '_ \\| | | | |  ||
            || | |_| | (_| \\__ \\__ \\ | | | |_| | |  ||
            || |____/ \\__,_|___/___/_| |_|\\___/|_|  ||
            \\\\======================================//
            AUTHORS: 
            https://github.com/Dassh01 : https://github.com/J-moneyKR : https://github.com/Henwy-Hi : https://github.com/Lukewtye
            """;

    public static Party getDefaultParty() {
        Date currentDate = getCurrentDate();

        //Generate new guest that is riding the train
        Date joelBirthday = new Date(2,30,1987);
        String[] joelName = new String[2];
        joelName[0] = "Joel";
        joelName[1] = "Roberts";
        Guest joel = new Guest(joelBirthday,joelName,true,62,159,currentDate);

        //Generate another guest in the same party that is not riding the train
        Date janeBirthday = new Date(5, 7,1995);
        String[] janeName = new String[2];
        janeName[0] = "Jane";
        janeName[1] = "Roberts";
        Guest jane = new Guest(janeBirthday,janeName,false,45,124,currentDate);

        //Generate new party
        Date dateOfAttendance = new Date(5,20,2025);
        Party party = new Party(1,modeOfTransport.WALKING,currentDate,dateOfAttendance,"Generic Party",true,-1);

        //Add joel to the party
        party.addGuest(joel);
        //Add jane to the party
        party.addGuest(jane);
        return party;
    }

    public static String sanitize(String string) {
        return string.toLowerCase().replaceAll(" ","");
    }

    public static void displayTicketGroups(ArrayList<TicketGroup> ticketGroupsArray) {
        if (ticketGroupsArray.isEmpty()) {
            System.out.println("No ticket groups generated yet!");
            return;
        }
        System.out.print("Ticket groups: ");
        for (TicketGroup ticketGroup: ticketGroupsArray) {
            System.out.print(" [tg of party: " + ticketGroup.associatedParty +"]");
        }
        System.out.println();
    }

    public static void displayParties(ArrayList<Party> partiesArray) {
        if (partiesArray.isEmpty()) {
            System.out.println("No parties generated yet!");
            return;
        }
        System.out.print("\nParties: ");
        for (Party party : partiesArray) {
            System.out.print(party.getPartyName() + " ");
        }
        System.out.println();
    }

    public static modeOfTransport strToMode(String input) {
        input = input.toLowerCase().trim();
        return switch (input) {
            case "driving" -> modeOfTransport.DRIVING;
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
        if (Main.debug) {
            System.out.println("Current date requested!");
        }
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
            System.out.println("Parsing dateAsString to Date...");
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

    protected static <T> T askForThing(String askText, Function<Scanner, T> inputFunction, Scanner scanner, boolean noNewline) {
        if (noNewline) {
            System.out.print(askText);
        }
        else {
            System.out.println(askText);
        }
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