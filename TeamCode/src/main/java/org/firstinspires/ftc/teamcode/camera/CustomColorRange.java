package org.firstinspires.ftc.teamcode.camera;

import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ColorSpace;
import org.opencv.core.Scalar;

/**
 * An {@link CustomColorRange represents a 3-channel minimum/maximum
 * range for a given color space}
 */
public class CustomColorRange {
    protected final ColorSpace colorSpace;
    protected final Scalar min;
    protected final Scalar max;

    // -----------------------------------------------------------------------------
    // DEFAULT OPTIONS
    // -----------------------------------------------------------------------------

        public static final CustomColorRange BLUE = new CustomColorRange(
            ColorSpace.YCrCb,
            new Scalar( 16,   0, 155),
            new Scalar(255, 127, 255)
    );

    public static final CustomColorRange RED = new CustomColorRange(
            ColorSpace.YCrCb,
            new Scalar( 32, 176,  0),
            new Scalar(255, 255, 132)
    );

    public static final CustomColorRange YELLOW = new CustomColorRange(
            ColorSpace.YCrCb,
            new Scalar(32, 128, 0),
            new Scalar(255, 169, 100)
    );
//
//    public static final CustomColorRange GREEN = new CustomColorRange(
//            ColorSpace.YCrCb,
//            new Scalar( 32,   0,   0),
//            new Scalar(255, 120, 133)
//    );

//    public static final CustomColorRange BLUE = new CustomColorRange(
//            ColorSpace.HSV,
//            new Scalar(100, 150, 50),
//            new Scalar(140, 255, 255)
//    );
//
//    public static final CustomColorRange RED = new CustomColorRange(
//            ColorSpace.HSV,
//            new Scalar(0, 76, 98),
//            new Scalar(0, 99, 67)
//    );
//
//    public static final CustomColorRange YELLOW = new CustomColorRange(
//            ColorSpace.HSV,
//            new Scalar(20, 100, 177),
//            new Scalar(39, 255, 255)
//    );

    public static final CustomColorRange GREEN = new CustomColorRange(
            ColorSpace.YCrCb,
            new Scalar(32, 0, 0),
            new Scalar(255, 120, 133)
    );

    // -----------------------------------------------------------------------------
    // ROLL YOUR OWN
    // -----------------------------------------------------------------------------

    public CustomColorRange(ColorSpace colorSpace, Scalar min, Scalar max) {
        this.colorSpace = colorSpace;
        this.min = min;
        this.max = max;
    }
}
