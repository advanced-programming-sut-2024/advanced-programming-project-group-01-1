package view;

public enum Constants {

    SCREEN_WIDTH(1366),
    SCREEN_HEIGHT(768),

    PREVIEW_CARD_WIDTH(140),
    PREVIEW_CARD_HEIGHT(265),
    PREVIEW_COUNT_ICON_WIDTH(29),
    PREVIEW_COUNT_ICON_HEIGHT(23),

    LARGE_CARD_WIDTH(264),
    LARGE_CARD_HEIGHT(500),

    SMALL_CARD_WIDTH(75),
    SMALL_CARD_HEIGHT(95);

    private final double value;

    Constants(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
