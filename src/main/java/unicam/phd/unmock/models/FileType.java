package unicam.phd.unmock.models;

public enum FileType {
    UNIT(1),
    SUT(2),
    DEPENDENCIES(3);

    private final int value;

    FileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}