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

        if (transportMode == modeOfTransport.TRAIN) {
            height = askForThing("[TRAIN] Enter guest height: ", Scanner::nextInt, scanner);
            weight = askForThing("[TRAIN] Enter guest weight: ", Scanner::nextInt, scanner);
        }

        boolean hasDiscount = askForThing("Enter discount code from guest: ", Scanner::nextLine, scanner).equals("MEMBER");

        Date birthday = strToDate(askForThing("Enter Birthday (mm/dd/yyyy): ", Scanner::nextLine, scanner));

        return new Guest(birthday, name, transportMode, hasDiscount, height, weight, today);
    }

    public static Party generateParty(Scanner scanner, Date currentDate) {
        //TODO: Implement guest - party system
        int amountOfPeopleInParty = askForThing("How many people are in the party?: ",Scanner::nextInt,scanner);
        String partyName = askForThing("Assign a name to this party: ",Scanner::nextLine,scanner);

        modeOfTransport transportMode;
        String transportModeInputString = askForThing("""
               What mode of transportation is the party taking? "\
               Options - (Driving, Train, Walking): \s""",Scanner::nextLine,scanner);
        transportMode = strToMode(transportModeInputString);

        Party party = new Party(amountOfPeopleInParty,transportMode,currentDate,partyName, 1);

        for (int i = 1; i < amountOfPeopleInParty+1; ++i) {
            party.addGuest(generateGuest(scanner,i,currentDate,transportMode));
        }

        return party;
    }

    public static void runTUI(Scanner scanner, Date currentDate, ArrayList<Party> parties, ArrayList<Ticket> tickets) {
        String command;
        boolean running = true;
        String help = "Commands: generateparty, listparties, cmds, end, currentdate";
        while(running) {
            command = askForThing("> ",Scanner::nextLine,scanner);

            switch (command.toLowerCase().trim()) {
                case ("generateparty"):
                    parties.add(generateParty(scanner,currentDate));
                    break;

                case ("listparties"):
                    arraylistToStr(parties);
                    break;

                case ("help"):
                case ("cmds"):
                    System.out.println(help);
                    break;

                case ("end"):
                    running = false;
                    break;

                case ("debug"):
                    debug = true;
                    break;

                case ("currentdate"):
                    System.out.println(currentDate.getDateAsString());
                    break;
            }

        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Date currentDate = getCurrentDate();
        ArrayList<Party> parties = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();

        System.out.println("""
        ---------------------------------------------------\
        | Welcome to the ZooLights ticket maker terminal! |\
        ---------------------------------------------------\
        """);

        runTUI(scanner,currentDate, parties, tickets);
    }
}
