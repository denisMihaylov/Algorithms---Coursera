package programming.collinear;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] tempPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(tempPoints);
        for (int i = 0; i < tempPoints.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        computeSegments(tempPoints);
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segments.size()];
        return segments.toArray(result);
    }

    private void computeSegments(Point[] points) {
        segments = new ArrayList<>();
        for (int i1 = 0; i1 < points.length - 3; i1++) {
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                if (points[i1].compareTo(points[i2]) == 0) {
                    throw new IllegalArgumentException();
                }
                for (int i3 = i2 + 1; i3 < points.length; i3++) {
                    for (int i4 = i3 + 1; i4 < points.length; i4++) {
                        boolean temp1 = points[i1].slopeOrder().compare(points[i2], points[i3]) == 0;
                        boolean temp2 = points[i1].slopeOrder().compare(points[i3], points[i4]) == 0;
                        if (temp1 && temp2) {
                            segments.add(new LineSegment(points[i1], points[i4]));
                        }
                    }
                }

            }
        }
    }

}
