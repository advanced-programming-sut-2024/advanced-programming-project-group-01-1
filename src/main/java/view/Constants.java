package view;

public enum Constants {

    WIDTH(800);


    private final double value;

    Constants(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
