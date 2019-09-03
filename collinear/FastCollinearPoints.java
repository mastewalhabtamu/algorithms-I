/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class FastCollinearPoints {
    private LineSegment[] segments;

    private void validate_nulls(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point p: points) {
            if (p == null) throw new IllegalArgumentException();
        }
    }

    private void validate_duplicates(Point[] points) {
        int limit = points.length - 1;
        for (int i=0;i < limit;i++) {
            if (points[i].compareTo(points[i+1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    public FastCollinearPoints(Point[] points) {
        validate_nulls(points);
    }

    public int lineOfSegments() {
        ;
    }

    public LineSegment[] segments() {

    }

    public static void main(String[] args) {

    }
}
