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
        validateNulls(points);

        Arrays.sort(points);
        validateDuplicates(points);

        List<LineSegment> lineSegments = new LinkedList<>();

        int n = points.length;
        double[] slopes = new double[n];
        for (int i = 0; i < n; i++) {
            Arrays.sort(points, points[i].slopeOrder());

            int collinearStart = 0;
            int collinearEnd = 0;
            for (int j = 0; j < n - 1; j++) {
                if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[j + 1])) {
                    collinearEnd += 1;
                }
                else {
                    if (collinearEnd - collinearStart >= 4) {
                        lineSegments.add(new LineSegment(points[collinearStart],
                                                         points[collinearEnd]));
                    }
                    collinearStart = j + 1;
                    collinearEnd = j + 1;
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
