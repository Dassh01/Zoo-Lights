package ZooLights.Objects;

import ZooLights.Helpers.modeOfTransport;

public class Guest {
    private final double discount = .02;

    //Ignore the suggestion telling you to make these variables inline!! -Bea
    private final Date birthday;
    private final Date today;
    private final String[] name;
    private final modeOfTransport transportMode;
    private final boolean hasDiscount;
    private final int height;
    private final int weight;

    public Guest(Date birthday, String[] name, modeOfTransport transportMode, boolean hasDiscount, int height, int weight, Date today) {
        this.birthday = birthday;
        this.name = name;
        this.transportMode = transportMode;
        this.hasDiscount = hasDiscount;
        this.height = height;
        this.weight = weight;
        this.today = today;
    }

    public int getAge() {
        return today.getYear() - birthday.getYear();
    }

}