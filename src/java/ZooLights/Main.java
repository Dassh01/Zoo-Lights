package ZooLights;

import ZooLights.Helpers.Util;
import ZooLights.Helpers.modeOfTransport;
import ZooLights.Objects.Date;
import ZooLights.Objects.Guest;
import ZooLights.Objects.Party;

import java.util.Scanner;

/*
Name: Luke Tye, Jack Fryer, Henry Felsted, Beatrice Gilfix
Date: 12/17/24
Assignment: Zoo Lights Ticket Generator
 */

public class Main extends Util {

    public static Guest generateGuest(Scanner scanner, int guestIteration, Date today) {
        //this is weird witchcraft bea cooked up, but it makes things very efficient
        //:3c

        //Name [0] = firstname
        //Name [1] = lastname
        String[] name = new String[2];
        name[0] = askForThing("Enter guest " + guestIteration + "'s" + " first name: ", Scanner::nextLine, scanner);
        name[1] = askForThing("Enter guest " + guestIteration + "'s" + " last name: ", Scanner::nextLine, scanner);

        modeOfTransport transportMode;
        int height = 0;
        int weight = 0;
        if (userInputBoolean(askForThing("Is user driving? (Y/N): ", Scanner::nextLine, scanner))) {
            transportMode = modeOfTransport.DRIVING;
        } else if (userInputBoolean(askForThing("Guest riding the train? (Y/N): ", Scanner::nextLine, scanner))) {
            transportMode = modeOfTransport.TRAIN;
            height = askForThing("Enter guest height: ",Scanner::nextInt,scanner);
            weight = askForThing("Enter guest weight: ",Scanner::nextInt,scanner);
        } else {
            transportMode = modeOfTransport.WALKING;
        }

        boolean hasDiscount = askForThing("Enter discount code from guest: ", Scanner::nextLine, scanner).equals("MEMBER");

        Date birthday = strToDate(askForThing("Enter Date (mm/dd/yyyy): ", Scanner::nextLine, scanner));

        return new Guest(birthday, name, transportMode, hasDiscount, height, weight, today);
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //Remember to pass this through to party and guest
        Date currentDate = getCurrentDate();


        System.out.println("""
        -----------------------------------------------\
        Welcome to the ZooLights ticket maker terminal!\
        -----------------------------------------------\
        """
        );

        //TODO: Implement guest - party system
        int amountOfPeopleInParty = askForThing("How many people are in the party?: ",Scanner::nextInt,scanner);
        //TODO: Satisfy Party constructor params of modeOfTransport
        modeOfTransport transPortMode;

        Party party = new Party(amountOfPeopleInParty, ,currentDate);

        for (int i = 1; i < amountOfPeopleInParty+1; ++i) {
            party.addGuest(generateGuest(scanner,i,currentDate));
        }
    }
}
