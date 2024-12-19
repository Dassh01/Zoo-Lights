package ZooLights.Objects;

import ZooLights.Helpers.modeOfTransport;
import static ZooLights.Helpers.Util.isWeekend;

import java.util.ArrayList;

public class Party {
    ArrayList<Guest> guestList = new ArrayList<>();
    Date today;
    private final int guestsInParty;
    private final modeOfTransport transportMode;
    private final boolean isWeekend;
    private final String partyName;
    private final int partyID;

    public Party(int guestsInParty, modeOfTransport transportMode, Date currentDate, String partyName, int partyID) {
        this.guestsInParty = guestsInParty; //AMOUNT of guests in party
        this.transportMode = transportMode;
        this.today = currentDate;
        this.isWeekend = isWeekend(today);
        this.partyName = partyName;
        this.partyID = partyID;
    }

    public void addGuest(Guest guest) {
        guestList.add(guest);
    }

    public String getPartyName() {
        return partyName;
    }
    public int getPartyCost() {
        int cost = 0;
        if (transportMode == modeOfTransport.DRIVING) { //If the party is driving
            //If the number of people in the party is over 8, the per-person cost goes down to 12.
            cost = guestsInParty > 8 ? (65 + (12 * (guestsInParty - 8))) : 65;
        }
        else if (transportMode == modeOfTransport.WALKING) { //If the party is walking
            for (Guest guest : guestList) {
                if (guest.getAge() > 18) { //Charge as an adult
                    cost = isWeekend ? cost + 25 : cost + 16;
                }
                else if (18 >= guest.getAge()  && guest.getAge() >= 15) { //Charge as age = 18 to 15
                    cost = isWeekend ? cost + 12 : cost + 8;
                }
                else if (15 >= guest.getAge() && guest.getAge() >= 2) { //Charge as age = 15 to 2
                    cost = isWeekend ? cost + 12 : cost + 8;
                }
            }
        }
        else if (transportMode == modeOfTransport.TRAIN) {
            //TODO: Ask Smith what costs for train are...
        }
        return cost;
    }

    public void displayGuestsInParty() {
        int i = 0;
        for (Guest guest : guestList) {
            ++i;
            System.out.println("Info of guest " + i +
                    "\n Guest name " + guest.getName() +
                    "\n Guest age: " + guest.getAge() );
        }
    }
}
