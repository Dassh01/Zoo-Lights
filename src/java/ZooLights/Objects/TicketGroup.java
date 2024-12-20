package ZooLights.Objects;

import java.util.ArrayList;

public class TicketGroup {

    private Party party;
    private ArrayList<Ticket> tickets = new ArrayList<>();
    public String associatedParty;

    public TicketGroup(Party party) {
        this.party = party;
        this.associatedParty = party.getPartyName();
    }

    public void compileTickets() {
        for (Guest guest : party.guestList) {
            tickets.add(new Ticket(guest,party));
        }
    }


}
