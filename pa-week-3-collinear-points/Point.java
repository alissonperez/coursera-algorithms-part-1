/*************************************************************************
 * Name: Alisson R. Perez
 * Email: alissonperez@outlook.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;

    private final int x;
    private final int y;

    // unit test
    public static void main(String[] args) {
        Point p1 = new Point( 3 , 1 ) ;
        p1.draw() ;

        Point p = new Point( 1 , 1 ) ;
        p.draw() ;

        Point p2 = new Point( 5 , 5 ) ;
        p.drawTo(p2) ;
    }

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;

        SLOPE_ORDER = new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            if (p1 == null || p2 == null)
                throw new NullPointerException();

            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);

            if (slope1 < slope2) return -1;
            if (slope1 > slope2) return 1;
            else return 0;
        }
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that.x == this.x && that.y == this.y)
            return Double.NEGATIVE_INFINITY;

        if ((that.x - this.x) == 0)
            return Double.POSITIVE_INFINITY;

        return (that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y || this.y == that.y && this.x < that.x)
            return -1;

        if (this.y == that.y && this.x == that.x)
            return 0;

        return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}
