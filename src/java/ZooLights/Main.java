package ZooLights;
import ZooLights.Helpers.Util;
import ZooLights.Objects.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/*
Name: Luke Tye, Jack Fryer, Henry Felsted, Beatrice Gilfix
Date: 12/17/24
Assignment: Zoo Lights Ticket Generator
 */
public class Main extends Util {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //how you want to format the date, it can also do exact time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy");
        //this grabs the date in the given format and stores it in currentDate
        LocalDateTime ldt = LocalDateTime.now();
        int dateAsNum = Integer.parseInt(dtf.format(ldt));
        Date currentDate = formatCurrentDate(dateAsNum);
        System.out.println("Current day = " + currentDate.getDay());
        System.out.println("""
        -----------------------------------------------\
        Welcome to the ZooLights ticket maker terminal!\
        -----------------------------------------------\
        To start please enter the amount of party members
        """
        );
        int partyNumber = scanner.nextInt();

        //Arraylist<Guest> guests = new ArrayList();
        int cost = 0;
        for (int i = 0; i < partyNumber; ++i) {
            //this is weird witchcraft bea cooked up, but it makes things very efficient
            //:3c
            boolean doingDrive = userInputBoolean(askForThing("Is user driving? (Y/N): ", Scanner::nextLine, scanner));
            boolean hasCode = askForThing("Enter code from guest: ", Scanner::nextLine, scanner).equals("MEMBER");
            if (doingDrive) {

                if (partyNumber > 8) {
                    cost = (65 + (12 * (partyNumber - 8)));
                }
            boolean canDrink = false;
                /*
                if (2024 - ageYear >= 21){
                    canDrink = true;
                    break;
                }if ( ageMonth < timeMonth){
                    canDrink = true;
                    break;
                }if ( ageDay <= timeDay){
                    canDrink = true;
                    break;
                }

                 */
                int month = askForThing("Enter birth month: ", Scanner::nextInt, scanner);
                int day = askForThing("Enter birth day (just the date of the month in /dd/: ", Scanner::nextInt, scanner);
                int year = askForThing("Enter birth year: ", Scanner::nextInt, scanner);
                Date birthday = new Date(month, day, year);
            }
            //ok to format it you need to run this method
            System.out.println("Current date: " + currentDate);
        }
    }
}
