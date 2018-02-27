package pl.sda.finalProject.myOrganizer.entity;

public enum PriorityType {

    HIGH(0),MEDIUM(1),LOW(2),NONE(3);

    private int value;

    PriorityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
