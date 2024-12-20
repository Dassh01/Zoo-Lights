package ZooLights;

import ZooLights.Helpers.AnsiEscapeCodes;
import ZooLights.Helpers.Utils;
import ZooLights.Helpers.modeOfTransport;
import ZooLights.Objects.*;

import java.util.ArrayList;
import java.util.Scanner;

/*
Name: Luke Tye, Jack Fryer, Henry Felsted, Beatrice Gilfix
Date: 12/17/24
Assignment: Zoo Lights Ticket Generator
 */

public class Main extends Utils {

    public static boolean debug;

    public static Guest generateGuest(Scanner scanner, int guestIteration, Date today, modeOfTransport transportMode) {
        //this is weird witchcraft bea cooked up, but it makes things very efficient
        //:3c

        //Name [0] = firstname
        //Name [1] = lastname
        String[] name = new String[2];
        name[0] = askForThing("Enter guest " + guestIteration + "'s" + " first name: ", Scanner::nextLine, scanner);
        name[1] = askForThing("Enter guest " + guestIteration + "'s" + " last name: ", Scanner::nextLine, scanner);

        double height = 0;
        double weight = 0;
        boolean isRidingTrain = false;
        if (transportMode == modeOfTransport.WALKING) { //If the guest is walking then they COULD be going on the train
            if (userInputBoolean(askForThing("Is this guest taking the train? (Y/N): ", Scanner::nextLine, scanner))) {
                //prompts weight and height because every
                height = askForThing("[TRAIN] Enter guest height (Inches): ", Scanner::nextDouble, scanner);
                weight = askForThing("[TRAIN] Enter guest weight (Pounds): ", Scanner::nextDouble, scanner);

                if (weight < 300 && height > 48) {
                    if (debug) { System.out.println(AnsiEscapeCodes.FG_RED + AnsiEscapeCodes.BG_BRIGHT_RED + "This guest passes the height and weight requirements" + AnsiEscapeCodes.RESET); }
                    isRidingTrain = true;

                } else {
                    if (debug) { System.out.println("This guest DOES NOT pass the height and weight requirements"); }
                }
            }
        }

        Date birthday = strToDate(askForThing("Enter Birthday (mm/dd/yyyy): ", Scanner::nextLine, scanner));

        return new Guest(birthday, name, isRidingTrain, height, weight, today);
    }

    public static Party generateParty (Scanner scanner, Date currentDate){
        int amountOfPeopleInParty = askForThing("How many people are in the party?: ", Scanner::nextInt, scanner);
        String partyName = askForThing("Assign a name to this party: ", Scanner::nextLine, scanner);
        /* Prompts user for the transportation method
        There are two options, driving and walking */
        modeOfTransport transportMode;
        String transportModeInputString = askForThing("""
                    What mode of transportation is the party taking? "\
                    Options - (Driving, Walking): \s""", Scanner::nextLine, scanner);

        transportMode = strToMode(transportModeInputString);
        //prompts and checks if the user inputs the correct discount code "MEMBER"
        boolean hasDiscount = askForThing("Enter discount code from party: ", Scanner::nextLine, scanner).equalsIgnoreCase("MEMBER");
        Date dateOfAttendance = strToDate(askForThing("What date does the party want to attend? (mm/dd/yyyy): ", Scanner::nextLine, scanner));

        Party party = new Party(amountOfPeopleInParty, transportMode, currentDate, dateOfAttendance, partyName, hasDiscount, 1);
        //creates a new party member for the amount that was input
        for (int i = 1; i < amountOfPeopleInParty + 1; ++i) {
            party.addGuest(generateGuest(scanner, i, currentDate, transportMode));
        }

        return party;
    }

    public static void runTUI (Scanner scanner, Date
            currentDate, ArrayList < Party > parties, ArrayList < TicketGroup > tickets){
        //initializing variables used in the interface
        String command;
        boolean running = true;
        while (running) {
            command = askForThing("> ", Scanner::nextLine, scanner, true);

            /* console commands by using a switch case
            If it matches the string in the case it is a valid command, and it will run the code */
            switch (sanitize(command)) {
                case ("generateparty"):
                    parties.add(generateParty(scanner, currentDate));
                    break;

                case ("listparties"):
                case ("ls:p"):
                    displayParties(parties);
                    break;

                case ("help"):
                case ("cmds"):
                    System.out.print("""
                        ------------------------- Commands -------------------------
                        Functions: Generate party, End, Compile Ticket Group
                        Viewing: List parties, Lookat : party, Lookat : ticketindex, ls
                        Debug: debug, Current date, gendefaultparty
                        Misc: Cmds/Help, Version
                        """ );
                    break;

                case ("end"):
                    running = false;
                    break;

                case ("debug"):
                    debug = !debug;
                    System.out.println("Debug: (" + !debug + ")" + " -> " + "(" + debug + ")");
                    break;

                case ("currentdate"):
                    System.out.println(currentDate.getDateAsString());
                    break;
                case ("version"):
                case ("ver"):
                    System.out.println(Utils.dasshTag);
                    break;

                case ("lookat:party"):
                    boolean partyFound_lookat = false;
                    String partyToLookAtCMD = askForThing("Look at which party?: ",Scanner::nextLine,scanner);
                    for (Party party : parties) {
                        if (sanitize(party.getPartyName()).equalsIgnoreCase(sanitize(partyToLookAtCMD))) {
                            party.displayGuestsInParty();
                            partyFound_lookat = true;
                        }
                    }
                    if (!partyFound_lookat) {
                        System.out.println("No party with that name was found");
                    }
                    break;

                case ("generatedefaultparty"):
                case ("gendefaultparty"):
                case ("gdp"):
                    if (debug) {
                        parties.add(getDefaultParty()); //For testing!
                    }
                    else {
                        System.out.println("Please enable debug mode to access this function");
                    }
                    break;

                case ("compileticketgroup"):
                case ("ctg"):
                    boolean partyFound_compiletix = false;
                    String partyToCompileCMD = askForThing("Compile which party?: ",Scanner::nextLine,scanner);
                    for (Party party : parties) {
                        if (party.getPartyName().equalsIgnoreCase(partyToCompileCMD) && !party.compiled) {
                            if (debug) {
                                System.out.println("Matched party!..");
                            }
                            TicketGroup ticketGroup = new TicketGroup(party);
                            tickets.add(ticketGroup);
                            if (debug) {
                                System.out.println("Ticket group compiled and appended to tickets.");
                            }
                            partyFound_compiletix = true;
                            party.compiled = true;
                        }
                    }
                    if (!partyFound_compiletix) {
                        System.out.println("No party to compile was found");
                    }
                    break;

                case ("lookat:ticketindex"):
                case ("ls:t"):
                    displayTicketGroups(tickets);
                    break;
                case("ls"):
                    displayParties(parties);
                    displayTicketGroups(tickets);
                    break;
                default:
                    System.out.println("Command not recognized");
            }

        }
    }

    //TODO: Seperate useful and "redundant" comments
    //TODO: Leave formatting to Jack & Henry?
    //starting the system up
    public static void main (String[]args) {
        Scanner scanner = new Scanner(System.in);
        Date currentDate = getCurrentDate();
        ArrayList<Party> parties = new ArrayList<>();
        ArrayList<TicketGroup> ticketGroups = new ArrayList<>();

        System.out.println("""
                    ---------------------------------------------------\
                    | Welcome to the ZooLights ticket maker terminal! |\
                    ---------------------------------------------------\
                    """);
        //runs the terminal user interface
        runTUI(scanner, currentDate, parties, ticketGroups);
    }
}