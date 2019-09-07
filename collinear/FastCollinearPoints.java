/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private LineSegment[] segments;

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

    public FastCollinearPoints(Point[] points) {
        Point[] clonedPoints = points.clone();
        validateNulls(clonedPoints);

        Arrays.sort(clonedPoints);
        validateDuplicates(clonedPoints);

        List<LineSegment> lineSegments = new LinkedList<>();

        int n = clonedPoints.length;
        Point[] otherPoints = new Point[n - 1];
        for (int i = 0; i < n; i++) {
            for (int other = 0; other < n - 1; other++) {
                if (i != other) {
                    otherPoints[other] = points[i];
                }
            }
            Arrays.sort(otherPoints, clonedPoints[i].slopeOrder());

            int collinearStart = 0;
            int collinearEnd = 0;
            for (int other = 0; other < n - 1; other++) {
                if (clonedPoints[i].slopeTo(otherPoints[other]) == clonedPoints[i]
                        .slopeTo(otherPoints[other + 1])) {
                    collinearEnd += 1;
                }
                else {
                    if (collinearEnd - collinearStart >= 3) {
                        lineSegments.add(new LineSegment(otherPoints[collinearStart],
                                                         otherPoints[collinearEnd]));
                    }
                    collinearStart = other + 1;
                    collinearEnd = other + 1;
                }
            }
        }

        this.segments = lineSegments.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {

    }
}
