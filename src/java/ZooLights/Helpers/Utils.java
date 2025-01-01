package ZooLights.Helpers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

import ZooLights.Main;
import ZooLights.Objects.*;

import static ZooLights.Main.debug;

public class Utils {

    public static final String dasshTag = """
            VERSION 1.
            //======================================\\\\
            ||  ____                _      ___  _   ||
            || |  _ \\  __ _ ___ ___| |__  / _ \\/ |  ||
            || | | | |/ _` / __/ __| '_ \\| | | | |  ||
            || | |_| | (_| \\__ \\__ \\ | | | |_| | |  ||
            || |____/ \\__,_|___/___/_| |_|\\___/|_|  ||
            \\\\======================================//
            AUTHORS:\s
            https://github.com/Dassh01 : https://github.com/J-moneyKR : https://github.com/Henwy-Hi : https://github.com/Lukewtye
           \s""";

    //TODO: Optimize for driver parties
    private static Guest generateGuest(Scanner scanner, int guestIteration, Date today, modeOfTransport transportMode, boolean isCreatingDriver) {
        //this is weird witchcraft bea cooked up
        //:3c

        Name name = new Name();
        name.setFirstname(askForThing("Enter guest " + guestIteration + "'s" + " first name: ", Scanner::nextLine, scanner,false));
        name.setLastname(askForThing("Enter guest " + guestIteration + "'s" + " last name: ", Scanner::nextLine, scanner,false));

        //Below runs if guest is walking
        double height = 0;
        double weight = 0;
        boolean isRidingTrain = false;
        if (transportMode == modeOfTransport.WALKING) { //If the guest is walking then they COULD be going on the train
            if (userInputBoolean(askForThing("Is this guest taking the train? (Y/N): ", Scanner::nextLine, scanner,false))) {
                //prompts weight and height because every
                height = askForThing("[TRAIN] Enter guest height (Inches): ", Scanner::nextDouble, scanner,false);
                weight = askForThing("[TRAIN] Enter guest weight (Pounds): ", Scanner::nextDouble, scanner,false);

                if (weight < 300 && height > 48) {
                    if (debug) { System.out.println(AnsiEscapeCodes.FG_RED + AnsiEscapeCodes.BG_BRIGHT_RED + "This guest passes the height and weight requirements" + AnsiEscapeCodes.RESET); }
                    isRidingTrain = true;

                } else {
                    if (debug) { System.out.println("This guest DOES NOT pass the height and weight requirements"); }
                }
            }
        }
        Date birthday = strToDate(askForThing("Enter Birthday (mm/dd/yyyy): ", Scanner::nextLine, scanner,false));
        return new Guest(birthday, name, isRidingTrain, height, weight, today);
    }

    public static Party generateParty (Scanner scanner, Date currentDate){
        int amountOfPeopleInParty = askForThing("How many people are in the party?: ", Scanner::nextInt, scanner,false);
        String partyName = askForThing("Assign a name to this party: ", Scanner::nextLine, scanner,false);
        /* Prompts user for the transportation method
        There are two options, driving and walking */
        modeOfTransport transportMode;
        String transportModeInputString = askForThing("""
                    What mode of transportation is the party taking? "\
                    Options - (Driving, Walking): \s""", Scanner::nextLine, scanner,false);

        transportMode = strToMode(transportModeInputString);
        //prompts and checks if the user inputs the correct discount code "MEMBER"
        boolean hasDiscount = askForThing("Enter discount code from party (cAse sEnSitIve): ", Scanner::nextLine, scanner,false).equals("MEMBER");
        Date dateOfAttendance = strToDate(askForThing("What date does the party want to attend? (mm/dd/yyyy): ", Scanner::nextLine, scanner,false));

        Party party = new Party(amountOfPeopleInParty, transportMode, currentDate, dateOfAttendance, partyName, hasDiscount, 1);

        //creates a new party member for the amount that was input
        if (transportMode == modeOfTransport.DRIVING) {
            party.addGuest(generateGuest(scanner,1,currentDate,transportMode,true));
        }
        else if (transportMode == modeOfTransport.WALKING) {
            for (int guestIteration = 1; guestIteration < amountOfPeopleInParty + 1; ++guestIteration) {
                party.addGuest(generateGuest(scanner, guestIteration, currentDate, transportMode, false));
            }
        }

        return party;
    }

    public static Party getDefaultParty() {
        Date currentDate = getCurrentDate();

        //Generate new guest that is riding the train
        Date joelBirthday = new Date(2, 30, 1987);
        Name joelName = new Name("Joel","Roberts");
        Guest joel = new Guest(joelBirthday, joelName, true, 70, 159, currentDate);

        //Generate another guest in the same party that is not riding the train
        Date janeBirthday = new Date(5, 7, 1995);
        Name janeName = new Name ("Jane","Roberts");
        Guest jane = new Guest(janeBirthday, janeName, false, 52, 124, currentDate);

        //Bob is a child, and he is riding the train with his father because he loves trains
        Date bobBirthday = new Date(2,23,2016);
        Name bobName = new Name ("Bob","Roberts");
        Guest bob = new Guest(bobBirthday,bobName,true,56,110, currentDate);

        //Generate new party
        Date dateOfAttendance = new Date(5, 20, 2025);
        Party party = new Party(1, modeOfTransport.WALKING, currentDate, dateOfAttendance, "Generic Party", true, -1);

        //Add joel to the party
        party.addGuest(joel);
        //Add jane to the party
        party.addGuest(jane);
        //Add bob to the party
        party.addGuest(bob);
        return party;
    }

    public static String sanitize(String string) {
        return string.toLowerCase().replaceAll(" ", "");
    }

    public static void lookatParty(Scanner scanner, ArrayList<Party> parties) {
        String partyToLookAtCMD = askForThing("Look at which party?: ", Scanner::nextLine, scanner,false);
        for (Party party : parties) {

            boolean partyFound = sanitize(party.getPartyName()).equals(sanitize(partyToLookAtCMD));

            if (partyFound) {
                party.displayGuestsInParty();
            }
            return;
        }
        System.out.println("No party with that name was found");

    }

    public static void lookForTicket(Scanner scanner, ArrayList <TicketGroup> ticketGroups) {
        //TODO: Someone underline "integer" in the askText
        int ticketIdToSearchFor = askForThing("Enter the 5 digit ticket integer code",Scanner::nextInt,scanner,false);
        for (TicketGroup ticketGroup : ticketGroups) {
            for (Ticket ticket : ticketGroup.tickets) {
                if (ticketIdToSearchFor == ticket.id) {
                    System.out.println(
                            "Ticket with ID provided found." +
                                    "\nTicket holder's name: " + ticket.guest.getName() +
                                    "\nTicket holder's age: " + ticket.guest.getAge()
                    );
                    return;
                }
            }
        }
        System.out.println("No ticket with that ID has been registered");
    }

    public static void lookatTicketGroup(Scanner scanner, ArrayList <TicketGroup> ticketGroups) {
        String ticketGroupToFind = sanitize(askForThing("Look at the ticket group of what party?",Scanner::nextLine,scanner,false));
        for (TicketGroup ticketGroup : ticketGroups) {
            if (sanitize(ticketGroup.associatedParty).equals(ticketGroupToFind)) {
                ticketGroup.displayTicketsInGroup();
                return;
            }
        }
        System.out.println("No ticket group associated to that party was found");
    }
    public static void compileTicketGroup(Scanner scanner, ArrayList<Party> parties, ArrayList<TicketGroup> ticketGroups) {
        String partyToCompileCMD = askForThing("Compile which party?: ", Scanner::nextLine, scanner,false);

        for (Party party : parties) {

            boolean partyFound = sanitize(party.getPartyName()).equals(sanitize(partyToCompileCMD));

            if (partyFound && !party.compiled) {

                if (debug) {
                    System.out.println("Matched party!..");
                }

                //Form a ticketgroup off of party information then append it to ticketgroup arraylist
                TicketGroup ticketGroup = new TicketGroup(party);
                ticketGroup.compileTickets();
                ticketGroups.add(ticketGroup);

                System.out.println("Ticket group compiled and appended to tickets.");

                //Tell the party internally that it is compiled (to prevent recompiling the same party accidentally)
                party.compiled = true;
                return;

            } else if (partyFound) {
                System.out.println("Party is already compiled!");
                return;
            }
        }

        System.out.println("No party with that name could be found");
    }

    public static void displayTicketGroups(ArrayList<TicketGroup> ticketGroupsArray) {
        if (ticketGroupsArray.isEmpty()) {
            System.out.println("No ticket groups generated yet!");
            return;
        }
        System.out.print("Ticket groups: ");
        for (TicketGroup ticketGroup : ticketGroupsArray) {
            System.out.print(" [tg of party: " + ticketGroup.associatedParty + "]");
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
        dateAsString = dateAsString.replaceAll("/", "");

        String month = dateAsString.substring(0, 2);
        String day = dateAsString.substring(2, 4);
        String year = dateAsString.substring(4, 8);
        if (Main.debug) {
            System.out.println("Parsing dateAsString to Date...");
        }
        return new Date(Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
    }

    /**
     * Comes from retyping user prompts and scanner methods over and over in APCSA projects :C
     * Used to simplify above-mentioned process, does the prompt and scan all in one (now with an embedded try catch!) (InputMismatch be gone!)
     *
     * @param askText       [String] Prompt the user is asked in console
     * @param inputFunction [Scanner] lambda method (i.e: Scanner::nextLine, Scanner::nextInt), dependent on what data type you want the function to return as its return type is generic
     * @param scanner       [Scanner] (hopefully connected to System input...)
     * @return Returns data type dependent on the scanner lambda inserted
     */
    protected static <T> T askForThing(String askText, Function<Scanner, T> inputFunction, Scanner scanner, boolean noNewline) {
        if (noNewline) {
            System.out.print(askText);
        } else {
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
     *
     * @param userInput [String] Raw user input as answer to a prompt similar to the ones listed above
     * @return [Boolean] true IF user inputted something that looks like a yes, false if user inputted something that looks something other than a yes
     */
    protected static boolean userInputBoolean(String userInput) {
        String formattedUserInput = userInput.toLowerCase().trim();
        return formattedUserInput.equals("y") || formattedUserInput.contains("yes");
    }
}