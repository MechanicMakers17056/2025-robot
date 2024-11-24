package org.firstinspires.ftc.teamcode.teleop;

import android.annotation.SuppressLint;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.SortOrder;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.camera.CustomColorBlobLocatorProcessor;
import org.firstinspires.ftc.teamcode.camera.CustomColorRange;
import org.firstinspires.ftc.teamcode.camera.CustomImageRegion;
import org.firstinspires.ftc.teamcode.registry_stuff.InitialableOp;
import org.firstinspires.ftc.teamcode.registry_stuff.RegistryLinearOpMode;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.RotatedRect;

import java.util.List;

@InitialableOp
@TeleOp(name = "Vision Color Locator", group = "TeleOp")
public class VisionColorLocator extends RegistryLinearOpMode
{
    private static final boolean showBlue = true;
    private static final boolean showRed = true;
    private static final boolean showYellow = true;

    @SuppressLint("DefaultLocale")
    @Override
    public void runCode()
    {
        /* Build a "Color Locator" vision processor based on the ColorBlobLocatorProcessor class.
         * - Specify the color range you are looking for.  You can use a predefined color, or create you own color range
         *     .setTargetColorRange(ColorRange.BLUE)                      // use a predefined color match
         *       Available predefined colors are: RED, BLUE YELLOW GREEN
         *     .setTargetColorRange(new ColorRange(ColorSpace.YCrCb,      // or define your own color match
         *                                           new Scalar( 32, 176,  0),
         *                                           new Scalar(255, 255, 132)))
         *
         * - Focus the color locator by defining a RegionOfInterest (ROI) which you want to search.
         *     This can be the entire frame, or a sub-region defined using:
         *     1) standard image coordinates or 2) a normalized +/- 1.0 coordinate system.
         *     Use one form of the ImageRegion class to define the ROI.
         *         ImageRegion.entireFrame()
         *         ImageRegion.asImageCoordinates(50, 50,  150, 150)  100x100 pixel square near the upper left corner
         *         ImageRegion.asUnityCenterCoordinates(-0.5, 0.5, 0.5, -0.5)  50% width/height square centered on screen
         *
         * - Define which contours are included.
         *     You can get ALL the contours, or you can skip any contours that are completely inside another contour.
         *        .setContourMode(ColorBlobLocatorProcessor.ContourMode.ALL_FLATTENED_HIERARCHY)  // return all contours
         *        .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)            // exclude contours inside other contours
         *        note: EXTERNAL_ONLY helps to avoid bright reflection spots from breaking up areas of solid color.
         *
         * - turn the display of contours ON or OFF.  Turning this on helps debugging but takes up valuable CPU time.
         *        .setDrawContours(true)
         *
         * - include any pre-processing of the image or mask before looking for Blobs.
         *     There are some extra processing you can include to improve the formation of blobs.  Using these features requires
         *     an understanding of how they may effect the final blobs.  The "pixels" argument sets the NxN kernel size.
         *        .setBlurSize(int pixels)    Blurring an image helps to provide a smooth color transition between objects, and smoother contours.
         *                                    The higher the number of pixels, the more blurred the image becomes.
         *                                    Note:  Even "pixels" values will be incremented to satisfy the "odd number" requirement.
         *                                    Blurring too much may hide smaller features.  A "pixels" size of 5 is good for a 320x240 image.
         *        .setErodeSize(int pixels)   Erosion removes floating pixels and thin lines so that only substantive objects remain.
         *                                    Erosion can grow holes inside regions, and also shrink objects.
         *                                    "pixels" in the range of 2-4 are suitable for low res images.
         *        .setDilateSize(int pixels)  Dilation makes objects more visible by filling in small holes, making lines appear thicker,
         *                                    and making filled shapes appear larger. Dilation is useful for joining broken parts of an
         *                                    object, such as when removing noise from an image.
         *                                    "pixels" in the range of 2-4 are suitable for low res images.
         */
        CustomColorBlobLocatorProcessor blueColorLocator = new CustomColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(CustomColorRange.BLUE)         // use a predefined color match
                .setContourMode(CustomColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // exclude blobs inside blobs
//                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.5, 0.5, 0.5, -0.5))  // search central 1/4 of camera view
                .setRoi(CustomImageRegion.entireFrame())  // search central 1/4 of camera view
                .setDrawContours(true)    // Show contours on the Stream Preview
                .setShowCenter(true)
                .setBlurSize(5)                               // Smooth the transitions between different colors in image
                .build();

        CustomColorBlobLocatorProcessor redColorLocator = new CustomColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(CustomColorRange.RED)         // use a predefined color match
                .setContourMode(CustomColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // exclude blobs inside blobs
//                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.5, 0.5, 0.5, -0.5))  // search central 1/4 of camera view
                .setRoi(CustomImageRegion.entireFrame())  // search central 1/4 of camera view
                .setDrawContours(false)    // Show contours on the Stream Preview
                .setShowCenter(true)
                .setBlurSize(5)                               // Smooth the transitions between different colors in image
                .build();

        CustomColorBlobLocatorProcessor yellowColorLocator = new CustomColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(CustomColorRange.YELLOW)         // use a predefined color match
                .setContourMode(CustomColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)    // exclude blobs inside blobs
//                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.5, 0.5, 0.5, -0.5))  // search central 1/4 of camera view
                .setRoi(CustomImageRegion.entireFrame())  // search central 1/4 of camera view
                .setDrawContours(false)    // Show contours on the Stream Preview
                .setShowCenter(true)
                .setBlurSize(5)                               // Smooth the transitions between different colors in image
                .build();


        /*
         * Build a vision portal to run the Color Locator process.
         *
         *  - Add the colorLocator process created above.
         *  - Set the desired video resolution.
         *      Since a high resolution will not improve this process, choose a lower resolution that is
         *      supported by your camera.  This will improve overall performance and reduce latency.
         *  - Choose your video source.  This may be
         *      .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))  .....   for a webcam
         *  or
         *      .setCamera(BuiltinCameraDirection.BACK)    ... for a Phone Camera
         */
        VisionPortal.Builder portalBuilder = new VisionPortal.Builder()
                .setCameraResolution(new Size(320, 240))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        if(showBlue)
            portalBuilder.addProcessor(blueColorLocator);
        if (showRed)
            portalBuilder.addProcessor(redColorLocator);
        if(showYellow)
            portalBuilder.addProcessor(yellowColorLocator);

        portalBuilder.build();

        telemetry.setMsTransmissionInterval(50);   // Speed up telemetry updates, Just use for debugging.
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        // WARNING:  To be able to view the stream preview on the Driver Station, this code runs in INIT mode.
        while (opModeIsActive() || opModeInInit())
        {
//            telemetry.addData("preview on/off", "... Camera Stream\n");

            // Read the current list
            List<CustomColorBlobLocatorProcessor.Blob> blueBlobs = blueColorLocator.getBlobs();
            List<CustomColorBlobLocatorProcessor.Blob> redBlobs = redColorLocator.getBlobs();
            List<CustomColorBlobLocatorProcessor.Blob> yellowBlobs = yellowColorLocator.getBlobs();

            /*
             * The list of Blobs can be filtered to remove unwanted Blobs.
             *   Note:  All contours will be still displayed on the Stream Preview, but only those that satisfy the filter
             *          conditions will remain in the current list of "blobs".  Multiple filters may be used.
             *
             * Use any of the following filters.
             *
             * ColorBlobLocatorProcessor.Util.filterByArea(minArea, maxArea, blobs);
             *   A Blob's area is the number of pixels contained within the Contour.  Filter out any that are too big or small.
             *   Start with a large range and then refine the range based on the likely size of the desired object in the viewfinder.
             *
             * ColorBlobLocatorProcessor.Util.filterByDensity(minDensity, maxDensity, blobs);
             *   A blob's density is an indication of how "full" the contour is.
             *   If you put a rubber band around the contour you would get the "Convex Hull" of the contour.
             *   The density is the ratio of Contour-area to Convex Hull-area.
             *
             * ColorBlobLocatorProcessor.Util.filterByAspectRatio(minAspect, maxAspect, blobs);
             *   A blob's Aspect ratio is the ratio of boxFit long side to short side.
             *   A perfect Square has an aspect ratio of 1.  All others are > 1
             */
            CustomColorBlobLocatorProcessor.Util.filterByArea(600, 10000, blueBlobs);  // filter out very small blobs.
            CustomColorBlobLocatorProcessor.Util.filterByArea(600, 10000, redBlobs);  // filter out very small blobs.
            CustomColorBlobLocatorProcessor.Util.filterByArea(600, 10000, yellowBlobs);  // filter out very small blobs.

            CustomColorBlobLocatorProcessor.Util.sortByArea(SortOrder.ASCENDING, blueBlobs);
            CustomColorBlobLocatorProcessor.Util.sortByArea(SortOrder.ASCENDING, redBlobs);
            CustomColorBlobLocatorProcessor.Util.sortByDensity(SortOrder.ASCENDING, yellowBlobs);

//            CustomColorBlobLocatorProcessor.Util.filterByAspectRatio(1.8, 2.2, blueBlobs);  // filter out very small blobs.
//            CustomColorBlobLocatorProcessor.Util.filterByAspectRatio(1.8, 2.2, redBlobs);  // filter out very small blobs.
//            CustomColorBlobLocatorProcessor.Util.filterByAspectRatio(1.8, 2.2, yellowBlobs);  // filter out very small blobs.

//            CustomColorBlobLocatorProcessor.Util.filterByAspectRatio(200, 10000, yellowBlobs);  // filter out very small blobs.

            /*
             * The list of Blobs can be sorted using the same Blob attributes as listed above.
             * No more than one sort call should be made.  Sorting can use ascending or descending order.
             *     ColorBlobLocatorProcessor.Util.sortByArea(SortOrder.DESCENDING, blobs);      // Default
             *     ColorBlobLocatorProcessor.Util.sortByDensity(SortOrder.DESCENDING, blobs);
             *     ColorBlobLocatorProcessor.Util.sortByAspectRatio(SortOrder.DESCENDING, blobs);
             */

//            telemetry.addLine(" Area Density Aspect  Center");

            // Display the size (area) and center location for each Blob.
            showDataFor(blueBlobs, "Blue");
            showDataFor(redBlobs, "Red");
            showDataFor(yellowBlobs, "Yellow");


            telemetry.update();
            sleep(50);
        }
    }

    @SuppressLint("DefaultLocale")
    private void showDataFor(List<CustomColorBlobLocatorProcessor.Blob> blobs, String color) {
        // Display the size (area) and center location for each Blob.
        if(!blobs.isEmpty())
            telemetry.addLine(color + " Sample: ");
        for(CustomColorBlobLocatorProcessor.Blob b : blobs)
        {
            RotatedRect boxFit = b.getBoxFit();
//            telemetry.addLine(String.format("%5d  %4.2f   %5.2f  (%3d,%3d)",
//                    b.getContourArea(), b.getDensity(), b.getAspectRatio(), (int) boxFit.center.x, (int) boxFit.center.y));
            telemetry.addLine(String.format("- Center: (%3d,%3d)",
                    (int) boxFit.center.x, (int) boxFit.center.y));

            telemetry.addLine(String.format("- Angle: %s",
                    (int) boxFit.angle));
        }
    }
}