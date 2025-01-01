package ZooLights.Objects;

import ZooLights.Helpers.modeOfTransport;
import static ZooLights.Main.debug;
import java.util.Random;

//TODO: Make ticket class

/*
Information we need to put on ticket:
- 5 integer random ID
- Name
- Total Cost
- Age
- Can drink (<21)
- Can ride the train
- If driving:
    Print ticket with all personal info (of just the driver)

----- Also need to make sure the ticket can be looked up with the 5-digit ticket ID
 */
public class Ticket {
    //TODO: Derive from guest
    public Guest guest;
    Party party;
    String name;
    double cost;
    int age;
    boolean canDrink;
    boolean canRideTrain;
    boolean isDriving;
    public int id;

    public Ticket (Guest guest, Party party) {
        this.guest = guest;
        this.party = party;
        this.name = guest.getName();
        this.cost = party.getPartyCost();
        this.age = guest.getAge();
        this.canDrink = guest.getAge() > 21;
        this.canRideTrain = guest.eligibleToRideTrain();
        this.isDriving = party.getTransportMode() == modeOfTransport.DRIVING;
        this.id = makeTicketID();

        if (debug) {
            System.out.println("Ticket of " + guest.getName() + " ID = " + id);
        }
    }

    public String getTicketInfo() {
        String ticketInfo = (
                "Ticket of guest: " + name +
                "Guest age = " + age +
                "Guest eligibility to ride train = " + canRideTrain);
        return ticketInfo;
    }

    //TODO: Throw into Utils
    private static int makeTicketID() {
        StringBuilder idCompile = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 5; ++i) {
            int randInt = rand.nextInt(1, 9);
            idCompile.append(randInt);
        }
        return Integer.parseInt(idCompile.toString());
    }


}
