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

    public static void runTUI (Scanner scanner, Date currentDate, ArrayList <Party> parties, ArrayList <TicketGroup> ticketGroups){
        //initializing variables used in the interface
        String command;
        boolean running = true;
        while (running) {
            command = askForThing("> ", Scanner::nextLine, scanner, true);

            /* console commands by using a switch case
            If it matches the string in the case it is a valid command, and it will run the code */ //what??
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
                    lookatParty(scanner, parties);
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
                    compileTicketGroup(scanner, parties, ticketGroups);
                    break;

                case ("lookat:ticketindex"):
                case ("ls:t"):
                    displayTicketGroups(ticketGroups);
                    break;
                case("ls"):
                    displayParties(parties);
                    displayTicketGroups(ticketGroups);
                    break;
                case ("clear"):
                case ("clr"):
                    for (int i = 50; i > 0; --i) {
                        System.out.println("\n");
                    }
                    break;
                case ("dump"):

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