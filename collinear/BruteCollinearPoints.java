/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
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

    public BruteCollinearPoints(Point[] points) {
        validateNulls(points);

        Arrays.sort(points);
        validateDuplicates(points);

        int n = points.length;
        List<LineSegment> lineSegments = new LinkedList<>();
        for (int a = 0; a < n - 3; a++) {
            Point pointA = points[a];

            for (int b = a + 1; b < n - 2; b++) {
                Point pointB = points[b];
                double slopeAB = pointA.slopeTo(pointB);

                for (int c = b + 1; c < n - 1; c++) {
                    Point pointC = points[c];
                    double slopeAC = pointA.slopeTo(pointC);

                    if (slopeAB == slopeAC) {
                        for (int d = c + 1; d < n; d++) {
                            Point pointD = points[d];
                            double slopeAD = pointA.slopeTo(pointD);

                            if (slopeAB == slopeAD) {
                                lineSegments.add(new LineSegment(pointA, pointD));
                            }
                        }
                    }
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
        /* empty body */
    }
}
