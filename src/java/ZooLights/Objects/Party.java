package ZooLights.Objects;

import ZooLights.Helpers.modeOfTransport;
import static ZooLights.Helpers.Utils.isWeekend;

import java.util.ArrayList;

public class Party {
    public ArrayList<Guest> guestList = new ArrayList<>();
    private Date today;
    private Date dateOfAttendance;
    private final double discount = .02;
    private final boolean hasDiscount;
    private final int guestsInParty;
    private final modeOfTransport transportMode;
    private final boolean isWeekend;
    private final String partyName;
    private final int partyID;
    public boolean compiled = false;

    public Party(int guestsInParty, modeOfTransport transportMode,
                 Date currentDate, Date dateOfAttendance,
                 String partyName, boolean hasDiscount, int partyID) {
        this.guestsInParty = guestsInParty; //AMOUNT of guests in party
        this.transportMode = transportMode;
        this.today = currentDate;
        this.isWeekend = isWeekend(today);
        this.partyName = partyName;
        this.partyID = partyID;
        this.hasDiscount = hasDiscount;
        this.dateOfAttendance = dateOfAttendance;
    }

    public modeOfTransport getTransportMode() {
        return transportMode;
    }
    public void addGuest(Guest guest) {
        guestList.add(guest);
    }

    public String getPartyName() {
        return partyName;
    }

    //TODO: Done??
    public double getPartyCost() {
        double cost = 0;

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

        cost = hasDiscount ? cost * discount : cost;
        return cost;
    }

    public void displayGuestsInParty() {
        if (guestList.isEmpty()) {
            System.out.println("Party has no guests!");
            return;
        }
        System.out.println();
        int i = 0;
        for (Guest guest : guestList) {
            ++i;
            Date guestBirthday = guest.getBirthday();
            System.out.println("Info of guest " + i +
                    "\n Guest name " + guest.getName() +
                    "\n Guest age: " + guest.getAge() +
                    "\n Guest birthday (mm/dd/yyyy): " + guestBirthday.getMonth() + "\\" + guestBirthday.getDay() + "\\" + guestBirthday.getYear() +
                    "\n Guest physique: Height = " + guest.getHeight() + "\" Weight = " + guest.getWeight() + " lbs" +
                    "\n Guest riding train = " + guest.isRidingTrain() + "\n");
        }
    }
}
