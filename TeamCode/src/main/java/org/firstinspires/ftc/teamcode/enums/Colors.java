package org.firstinspires.ftc.teamcode.enums;

public enum Colors {
    RED(1.0, 0.0, 0.0),
    GREEN(0.0, 1.0, 0.0),
    BLUE(0.0, 0.0, 1.0),
    WHITE(1.0, 1.0, 1.0),
    BLACK(0.0, 0.0, 0.0),
    YELLOW(1.0, 1.0, 0.0),
    CYAN(0.0, 1.0, 1.0),
    MAGENTA(1.0, 0.0, 1.0),
    ORANGE(1.0, 0.65, 0.0),
    PINK(1.0, 0.75, 0.8),
    PURPLE(0.5, 0.0, 0.5),
    BROWN(0.6, 0.3, 0.0),
    GRAY(0.5, 0.5, 0.5);

    private final double r;
    private final double g;
    private final double b;

    Colors(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getRed() {
        return r;
    }

    public double getGreen() {
        return g;
    }

    public double getBlue() {
        return b;
    }

    public double[] getRGB() {
        return new double[]{r, g, b};
    }

}
