package view;

public enum Constants {

    LARGE_CARD_WIDTH(140),
    LARGE_CARD_HEIGHT(265),
    PREVIEW_COUNT_ICON_WIDTH(29),
    PREVIEW_COUNT_ICON_HEIGHT(23);


    private final double value;

    Constants(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
