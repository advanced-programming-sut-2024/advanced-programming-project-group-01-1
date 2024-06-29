package view;

public enum Constants {

    STAGE_WIDTH(1920),
    STAGE_HEIGHT(1080);

    private final int value;

    Constants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
