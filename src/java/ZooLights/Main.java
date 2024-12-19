package ZooLights;

import ZooLights.Helpers.Util;
import ZooLights.Helpers.modeOfTransport;
import ZooLights.Objects.Date;
import ZooLights.Objects.Guest;
import ZooLights.Objects.Party;
import ZooLights.Objects.Ticket;

import java.util.ArrayList;
import java.util.Scanner;

/*
Name: Luke Tye, Jack Fryer, Henry Felsted, Beatrice Gilfix
Date: 12/17/24
Assignment: Zoo Lights Ticket Generator
 */

public class Main extends Util {

    public static boolean debug;

    public static Guest generateGuest(Scanner scanner, int guestIteration, Date today, modeOfTransport transportMode) {
        //this is weird witchcraft bea cooked up, but it makes things very efficient
        //:3c

        //Name [0] = firstname
        //Name [1] = lastname
        String[] name = new String[2];
        name[0] = askForThing("Enter guest " + guestIteration + "'s" + " first name: ", Scanner::nextLine, scanner);
        name[1] = askForThing("Enter guest " + guestIteration + "'s" + " last name: ", Scanner::nextLine, scanner);

        int height = 0;
        int weight = 0;
        boolean isRidingTrain = false;
        if (transportMode == modeOfTransport.WALKING) { //If the guest is walking then they COULD be going on the train
            if (userInputBoolean(askForThing("Is this guest taking the train? (Y/N): ", Scanner::nextLine, scanner))) {
                height = askForThing("[TRAIN] Enter guest height (Inches): ", Scanner::nextInt, scanner);
                weight = askForThing("[TRAIN] Enter guest weight (Pounds): ", Scanner::nextInt, scanner);

                if (weight < 300 && height > 48) {
                    System.out.println("This guest passes the height and weight requirements");
                    isRidingTrain = true;

                } else {
                    System.out.println("This guest DOES NOT pass the height and weight requirements");
                }
            }
        }

        Date birthday = strToDate(askForThing("Enter Birthday (mm/dd/yyyy): ", Scanner::nextLine, scanner));

        return new Guest(birthday, name, isRidingTrain, height, weight, today);
    }

    public static Party generateParty (Scanner scanner, Date currentDate){
        int amountOfPeopleInParty = askForThing("How many people are in the party?: ", Scanner::nextInt, scanner);
        String partyName = askForThing("Assign a name to this party: ", Scanner::nextLine, scanner);

        modeOfTransport transportMode;
        String transportModeInputString = askForThing("""
                    What mode of transportation is the party taking? "\
                    Options - (Driving, Train, Walking): \s""", Scanner::nextLine, scanner);

        transportMode = strToMode(transportModeInputString);
        boolean hasDiscount = askForThing("Enter discount code from party: ", Scanner::nextLine, scanner).equalsIgnoreCase("MEMBER");
        Date dateOfAttendance = strToDate(askForThing("What date does the party want to attend? (mm/dd/yyyy): ", Scanner::nextLine, scanner));

        Party party = new Party(amountOfPeopleInParty, transportMode, currentDate, dateOfAttendance, partyName, hasDiscount, 1);

        for (int i = 1; i < amountOfPeopleInParty + 1; ++i) {
            party.addGuest(generateGuest(scanner, i, currentDate, transportMode));
        }

        return party;
    }

    public static void runTUI (Scanner scanner, Date
            currentDate, ArrayList < Party > parties, ArrayList < Ticket > tickets){
        String command;
        boolean running = true;
        String help = "Commands: generateparty, listparties, cmds, end, currentdate, debug";
        while (running) {
            command = askForThing("> ", Scanner::nextLine, scanner, true);

            switch (command.toLowerCase().replaceAll(" ", "")) {
                case ("generateparty"):
                    parties.add(generateParty(scanner, currentDate));
                    break;

                case ("listparties"):
                    arraylistToStr(parties);
                    break;

                case ("help"):
                case ("cmds"):
                case ("cmd"):
                    System.out.println(help);
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
                    System.out.println(Util.dasshTag);
                    break;
                default:
                    System.out.println("Command not recognized");
            }

        }
    }
    public static void main (String[]args) {
        Scanner scanner = new Scanner(System.in);
        Date currentDate = getCurrentDate();
        ArrayList<Party> parties = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();

        System.out.println("""
                    ---------------------------------------------------\
                    | Welcome to the ZooLights ticket maker terminal! |\
                    ---------------------------------------------------\
                    """);

        runTUI(scanner, currentDate, parties, tickets);
    }
}