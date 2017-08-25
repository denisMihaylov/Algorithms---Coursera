package programming.collinear;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
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
    
    private class SlopeAndNaturalComparator implements Comparator<Point> {
        
        private final Point startingPoint;
        
        public SlopeAndNaturalComparator(Point startingPoint) {
            this.startingPoint = startingPoint;
        }

        @Override
        public int compare(Point o1, Point o2) {
            int temp = startingPoint.slopeOrder().compare(o1, o2);
            if (temp == 0) {
                return o1.compareTo(o2);
            }
            return temp;
        }
        
    }

    private void computeSegments(Point[] points) {
        segments = new ArrayList<>();
        Point[] initialPoints = Arrays.copyOf(points, points.length);
        for (int i = 0; i < initialPoints.length; i++) {
            Point startingPoint = initialPoints[i];
            Arrays.sort(points, new SlopeAndNaturalComparator(startingPoint));
            boolean isAdded = false;
            int pointCount, j;
            for (pointCount = 2, j = 1; j < points.length - 1; j++) {
                if (startingPoint.compareTo(points[j]) == 0 || startingPoint.compareTo(points[j + 1]) == 0) {
                    continue;
                }
                double slope1 = startingPoint.slopeTo(points[j]);
                double slope2 = startingPoint.slopeTo(points[j + 1]);
                if (Double.doubleToLongBits(slope1) != Double.doubleToLongBits(slope2)) {
                    if (pointCount > 3 && !isAdded) {
                        segments.add(new LineSegment(startingPoint, points[j]));
                    }
                    isAdded = false;
                    pointCount = 2;
                } else {
                    pointCount++;
                    if (startingPoint.compareTo(points[j]) >= 0 || startingPoint.compareTo(points[j + 1]) >= 0) {
                        isAdded = true;
                    }
                }
            }
            if (pointCount > 3 && !isAdded) {
                segments.add(new LineSegment(startingPoint, points[j]));
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.05);
        StdDraw.setXscale(0, 25);
        StdDraw.setYscale(0, 25);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.setPenRadius(0.02);
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
