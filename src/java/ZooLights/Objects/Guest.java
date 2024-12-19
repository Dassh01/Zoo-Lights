package ZooLights.Objects;

import ZooLights.Helpers.modeOfTransport;

public class Guest {

    //Ignore the suggestion telling you to make these variables inline!! -Bea
    private final int FIRST_NAME_INDEX = 0, LAST_NAME_INDEX = 1;
    private final Date birthday;
    private final Date today;
    private final String[] name;
    private final boolean isRidingTrain;
    private final int height;
    private final int weight;

    public Guest(Date birthday, String[] name, boolean isRidingTrain, int height, int weight, Date today) {
        this.birthday = birthday;
        this.name = name;
        this.isRidingTrain = isRidingTrain;
        this.height = height;
        this.weight = weight;
        this.today = today;
    }

    public boolean isRidingTrain() {
        return isRidingTrain;
    }
    public int getAge() {
        return today.getYear() - birthday.getYear();
    }

    public String getName() {
        return name[FIRST_NAME_INDEX] + " " + name[LAST_NAME_INDEX];
    }

}