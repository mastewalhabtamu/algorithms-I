/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        validateNulls(points);

        // sort points and check duplicates
        Arrays.sort(points);
        validateDuplicates(points);

        List<LineSegment> lineSegments = new LinkedList<LineSegment>();
        int n = points.length;
        Point[] otherPoints = new Point[n - 1];
        for (int i = 0; i < n; i++) {
            // collect other points
            int otherI = 0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    otherPoints[otherI] = points[j];
                    otherI++;
                }
            }

            // sort other points based on ith point slopeOrder comparater
            Point slopePoint = points[i];
            Arrays.sort(otherPoints, points[i].slopeOrder());

            // checkup consecutive points have the same slope
            Point otherCurrentPoint, otherNextPoint;
            Double skipSlope = null;
            Point minPoint = slopePoint;
            Point maxPoint = slopePoint;
            int equalSlopePoints = 0;
            int otherComparingLimit = otherPoints.length - 1;   // to check up consecutive points
            for (int other = 0; other < otherComparingLimit; other++) {
                // set up consecutive points
                otherCurrentPoint = otherPoints[other];
                otherNextPoint = otherPoints[other + 1];
                double otherCurrentSlope = slopePoint.slopeTo(otherCurrentPoint);
                double otherNextSlope = slopePoint.slopeTo(otherNextPoint);

                if (skipSlope == null || skipSlope != otherCurrentSlope) {
                    if (Double.compare(otherCurrentSlope, otherNextSlope) == 0) {
                        // check if slopePoint is minimum, otherwise skip such slopeValues from further processing
                        // only when slopePoint is minimum line segments will be processed to avoid duplicate line segments
                        if (otherNextPoint.compareTo(slopePoint) < 0
                                || (equalSlopePoints == 0
                                && otherCurrentPoint.compareTo(slopePoint) < 0)) {
                            // slopePoint not minimum when compared to consecutive points
                            // reset if minimum point not found
                            equalSlopePoints = 0;
                            minPoint = slopePoint;
                            maxPoint = slopePoint;

                            skipSlope = otherCurrentSlope;  // otherNextSlope is same value
                        }
                        else {  // slopePoint is minimum
                            // update max point
                            if (maxPoint.compareTo(otherNextPoint) < 0) {
                                maxPoint = otherNextPoint;
                            }

                            if (equalSlopePoints == 0
                                    && maxPoint.compareTo(otherCurrentPoint) < 0) {
                                maxPoint = otherCurrentPoint;
                            }

                            // if first equal consecutive points found increment by 2, otherwise by 1
                            equalSlopePoints += (equalSlopePoints == 0) ? 2 : 1;
                        }
                    }
                    else {  // consecutive slopes are not equal
                        if (equalSlopePoints >= 3) {
                            lineSegments.add(new LineSegment(minPoint, maxPoint));
                        }

                        // reset slope searching
                        equalSlopePoints = 0;
                        minPoint = slopePoint;
                        maxPoint = slopePoint;
                        skipSlope = null;
                    }
                }
            }

            if (equalSlopePoints >= 3) {
                lineSegments.add(new LineSegment(minPoint, maxPoint));
            }
        }

        // populate segments array
        this.segments = lineSegments.toArray(new LineSegment[0]);
    }

    private void validateNulls(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
    }

    private void validateDuplicates(Point[] points) {
        int limit = points.length - 1;
        for (int i = 0; i < limit; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        /* empty body */
    }
}
