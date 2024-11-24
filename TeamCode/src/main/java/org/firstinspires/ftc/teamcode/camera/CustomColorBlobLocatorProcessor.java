package org.firstinspires.ftc.teamcode.camera;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import com.qualcomm.robotcore.util.SortOrder;

import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link CustomColorBlobLocatorProcessor} finds "blobs" of a user-specified color
 * in the image. You can restrict the search area to a specified Region
 * of Interest (ROI).
 */
public abstract class CustomColorBlobLocatorProcessor implements VisionProcessor
{
    /**
     * Class supporting construction of a {@link CustomColorBlobLocatorProcessor}
     */
    public static class Builder
    {
        private CustomColorRange colorRange;
        private CustomColorBlobLocatorProcessor.ContourMode contourMode;
        private CustomImageRegion imageRegion;
        private int erodeSize = -1;
        private int dilateSize = -1;
        private boolean drawContours = false;
        private boolean showCenter = false;
        private int blurSize = -1;
        private int boundingBoxColor = Color.rgb(31, 255, 251);
        private int roiColor = Color.rgb(255, 255, 255);
        private int contourColor = Color.rgb(3, 227, 252);

        /**
         * Sets whether to draw the contour outline for the detected
         * blobs on the camera preview. This can be helpful for debugging
         * thresholding.
         * @param drawContours whether to draw contours on the camera preview
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setDrawContours(boolean drawContours)
        {
            this.drawContours = drawContours;
            return this;
        }

        public CustomColorBlobLocatorProcessor.Builder setShowCenter(boolean showCenter)
        {
            this.showCenter = showCenter;
            return this;
        }

        /**
         * Set the color used to draw the "best fit" bounding boxes for blobs
         * @param color Android color int
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setBoxFitColor(@ColorInt int color)
        {
            this.boundingBoxColor = color;
            return this;
        }

        /**
         * Set the color used to draw the ROI on the camera preview
         * @param color Android color int
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setRoiColor(@ColorInt int color)
        {
            this.roiColor = color;
            return this;
        }

        /**
         * Set the color used to draw blob contours on the camera preview
         * @param color Android color int
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setContourColor(@ColorInt int color)
        {
            this.contourColor = color;
            return this;
        }

        /**
         * Set the color range used to find blobs
         * @param colorRange the color range used to find blobs
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setTargetColorRange(CustomColorRange colorRange)
        {
            this.colorRange = colorRange;
            return this;
        }

        /**
         * Set the contour mode which will be used when generating
         * the results provided by {@link #getBlobs()}
         * @param contourMode contour mode which will be used when generating
         *                    the results provided by {@link #getBlobs()}
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setContourMode(CustomColorBlobLocatorProcessor.ContourMode contourMode)
        {
            this.contourMode = contourMode;
            return this;
        }

        /**
         * Set the Region of Interest on which to perform blob detection
         * @param roi region of interest
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setRoi(CustomImageRegion roi)
        {
            this.imageRegion = roi;
            return this;
        }

        /**
         * Set the size of the blur kernel. Blurring can improve
         * color thresholding results by smoothing color variation.
         * @param blurSize size of the blur kernel
         *                 0 to disable
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setBlurSize(int blurSize)
        {
            this.blurSize = blurSize;
            return this;
        }

        /**
         * Set the size of the Erosion operation performed after applying
         * the color threshold. Erosion eats away at the mask, reducing
         * noise by eliminating super small areas, but also reduces the
         * contour areas of everything a little bit.
         * @param erodeSize size of the Erosion operation
         *                  0 to disable
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setErodeSize(int erodeSize)
        {
            this.erodeSize = erodeSize;
            return this;
        }

        /**
         * Set the size of the Dilation operation performed after applying
         * the Erosion operation. Dilation expands mask areas, making up
         * for shrinkage caused during erosion, and can also clean up results
         * by closing small interior gaps in the mask.
         * @param dilateSize the size of the Dilation operation performed
         *                   0 to disable
         * @return Builder object, to allow for method chaining
         */
        public CustomColorBlobLocatorProcessor.Builder setDilateSize(int dilateSize)
        {
            this.dilateSize = dilateSize;
            return this;
        }

        /**
         * Construct a {@link CustomColorBlobLocatorProcessor} object using previously
         * set parameters
         * @return a {@link  CustomColorBlobLocatorProcessor} object which can be attached
         * to your {@link org.firstinspires.ftc.vision.VisionPortal}
         */
        public CustomColorBlobLocatorProcessor build()
        {
            if (colorRange == null)
            {
                throw new IllegalArgumentException("You must set a color range!");
            }

            if (contourMode == null)
            {
                throw new IllegalArgumentException("You must set a contour mode!");
            }

            return new CustomColorBlobLocatorProcessorImpl(colorRange, imageRegion, contourMode, erodeSize, dilateSize, drawContours, showCenter, blurSize, boundingBoxColor, roiColor, contourColor);
        }
    }

    /**
     * Determines what you get in {@link #getBlobs()}
     */
    public enum ContourMode
    {
        /**
         * Only return blobs from external contours
         */
        EXTERNAL_ONLY,

        /**
         * Return blobs which may be from nested contours
         */
        ALL_FLATTENED_HIERARCHY
    }

    /**
     * The criteria used for filtering and sorting.
     */
    public enum BlobCriteria
    {
        BY_CONTOUR_AREA,
        BY_DENSITY,
        BY_ASPECT_RATIO,
    }

    /**
     * Class describing how to filter blobs.
     */
    public static class BlobFilter {
        public final CustomColorBlobLocatorProcessor.BlobCriteria criteria;
        public final double minValue;
        public final double maxValue;

        public BlobFilter(CustomColorBlobLocatorProcessor.BlobCriteria criteria, double minValue, double maxValue)
        {
            this.criteria = criteria;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }
    }

    /**
     * Class describing how to sort blobs.
     */
    public static class BlobSort
    {
        public final CustomColorBlobLocatorProcessor.BlobCriteria criteria;
        public final SortOrder sortOrder;

        public BlobSort(CustomColorBlobLocatorProcessor.BlobCriteria criteria, SortOrder sortOrder)
        {
            this.criteria = criteria;
            this.sortOrder = sortOrder;
        }
    }

    /**
     * Class describing a Blob of color found inside the image
     */
    public static abstract class Blob
    {
        /**
         * Get the OpenCV contour for this blob
         * @return OpenCV contour
         */
        public abstract MatOfPoint getContour();

        /**
         * Get the contour points for this blob
         * @return contour points for this blob
         */
        public abstract Point[] getContourPoints();

        /**
         * Get the area enclosed by this blob's contour
         * @return area enclosed by this blob's contour
         */
        public abstract int getContourArea();

        /**
         * Get the density of this blob, i.e. ratio of
         * contour area to convex hull area
         * @return density of this blob
         */
        public abstract double getDensity();

        /**
         * Get the aspect ratio of this blob, i.e. the ratio
         * of longer side of the bounding box to the shorter side
         * @return aspect ratio of this blob
         */
        public abstract double getAspectRatio();

        /**
         * Get a "best fit" bounding box for this blob
         * @return "best fit" bounding box for this blob
         */
        public abstract RotatedRect getBoxFit();
    }

    /**
     * Add a filter.
     */
    public abstract void addFilter(CustomColorBlobLocatorProcessor.BlobFilter filter);

    /**
     * Remove a filter.
     */
    public abstract void removeFilter(CustomColorBlobLocatorProcessor.BlobFilter filter);

    /**
     * Remove all filters.
     */
    public abstract void removeAllFilters();

    /**
     * Sets the sort.
     */
    public abstract void setSort(CustomColorBlobLocatorProcessor.BlobSort sort);

    /**
     * Get the results of the most recent blob analysis
     * @return results of the most recent blob analysis
     */
    public abstract List<CustomColorBlobLocatorProcessor.Blob> getBlobs();

    /**
     * Utility class for post-processing results from {@link #getBlobs()}
     */
    public static class Util
    {
        /**
         * Remove from a List of Blobs those which fail to meet an area criteria
         * @param minArea minimum area
         * @param maxArea maximum area
         * @param blobs List of Blobs to operate on
         */
        public static void filterByArea(double minArea, double maxArea, List<CustomColorBlobLocatorProcessor.Blob> blobs)
        {
            ArrayList<CustomColorBlobLocatorProcessor.Blob> toRemove = new ArrayList<>();

            for(CustomColorBlobLocatorProcessor.Blob b : blobs)
            {
                if (b.getContourArea() > maxArea || b.getContourArea() < minArea)
                {
                    toRemove.add(b);
                }
            }

            blobs.removeAll(toRemove);
        }

        /**
         * Sort a list of Blobs based on area
         * @param sortOrder sort order
         * @param blobs List of Blobs to operate on
         */
        public static void sortByArea(SortOrder sortOrder, List<CustomColorBlobLocatorProcessor.Blob> blobs)
        {
            blobs.sort(new Comparator<CustomColorBlobLocatorProcessor.Blob>()
            {
                public int compare(CustomColorBlobLocatorProcessor.Blob c1, CustomColorBlobLocatorProcessor.Blob c2)
                {
                    int tmp = (int)Math.signum(c2.getContourArea() - c1.getContourArea());

                    if (sortOrder == SortOrder.ASCENDING)
                    {
                        tmp = -tmp;
                    }

                    return tmp;
                }
            });
        }

        /**
         * Remove from a List of Blobs those which fail to meet a density criteria
         * @param minDensity minimum density
         * @param maxDensity maximum desnity
         * @param blobs List of Blobs to operate on
         */
        public static void filterByDensity(double minDensity, double maxDensity, List<CustomColorBlobLocatorProcessor.Blob> blobs)
        {
            ArrayList<CustomColorBlobLocatorProcessor.Blob> toRemove = new ArrayList<>();

            for(CustomColorBlobLocatorProcessor.Blob b : blobs)
            {
                if (b.getDensity() > maxDensity || b.getDensity() < minDensity)
                {
                    toRemove.add(b);
                }
            }

            blobs.removeAll(toRemove);
        }

        /**
         * Sort a list of Blobs based on density
         * @param sortOrder sort order
         * @param blobs List of Blobs to operate on
         */
        public static void sortByDensity(SortOrder sortOrder, List<CustomColorBlobLocatorProcessor.Blob> blobs)
        {
            blobs.sort(new Comparator<CustomColorBlobLocatorProcessor.Blob>()
            {
                public int compare(CustomColorBlobLocatorProcessor.Blob c1, CustomColorBlobLocatorProcessor.Blob c2)
                {
                    int tmp = (int)Math.signum(c2.getDensity() - c1.getDensity());

                    if (sortOrder == SortOrder.ASCENDING)
                    {
                        tmp = -tmp;
                    }

                    return tmp;
                }
            });
        }

        /**
         * Remove from a List of Blobs those which fail to meet an aspect ratio criteria
         * @param minAspectRatio minimum aspect ratio
         * @param maxAspectRatio maximum aspect ratio
         * @param blobs List of Blobs to operate on
         */
        public static void filterByAspectRatio(double minAspectRatio, double maxAspectRatio, List<CustomColorBlobLocatorProcessor.Blob> blobs)
        {
            ArrayList<CustomColorBlobLocatorProcessor.Blob> toRemove = new ArrayList<>();

            for(CustomColorBlobLocatorProcessor.Blob b : blobs)
            {
                if (b.getAspectRatio() > maxAspectRatio || b.getAspectRatio() < minAspectRatio)
                {
                    toRemove.add(b);
                }
            }

            blobs.removeAll(toRemove);
        }

        /**
         * Sort a list of Blobs based on aspect ratio
         * @param sortOrder sort order
         * @param blobs List of Blobs to operate on
         */
        public static void sortByAspectRatio(SortOrder sortOrder, List<CustomColorBlobLocatorProcessor.Blob> blobs)
        {
            blobs.sort(new Comparator<CustomColorBlobLocatorProcessor.Blob>()
            {
                public int compare(CustomColorBlobLocatorProcessor.Blob c1, CustomColorBlobLocatorProcessor.Blob c2)
                {
                    int tmp = (int)Math.signum(c2.getAspectRatio() - c1.getAspectRatio());

                    if (sortOrder == SortOrder.ASCENDING)
                    {
                        tmp = -tmp;
                    }

                    return tmp;
                }
            });
        }
    }
}