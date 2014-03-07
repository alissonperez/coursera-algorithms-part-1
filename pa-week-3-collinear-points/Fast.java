import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Fast {

    private Point[] points;

    public static void main(String args[]) throws FileNotFoundException {
        Fast f = new Fast();
        f.populateFromFile(args[0]);
        f.printLineSegments();
    }

    private void populateFromFile(String filepath) throws FileNotFoundException {
        Scanner s = new Scanner(new File(filepath));
        int size = s.nextInt();

        points = new Point[size];

        for (int i = 0; i < size; i++) {
            points[i] = new Point(s.nextInt(), s.nextInt());
        }

        Quick.sort(points);
    }

    private void printLineSegments() {
        int i, j, k, l;

        Point[] pointsToSort = new Point[points.length];
        for (i = 0; i < points.length; i++) {
            pointsToSort[i] = points[i];
        }

        Point p;
        Boolean first ;
        Point last;

        for (i = 0; i < points.length; i++) {
            p = points[i];
            Arrays.sort(pointsToSort, p.SLOPE_ORDER) ;
            first = true;

            for (j = 0, last = pointsToSort[j]; j < pointsToSort.length; last = pointsToSort[j], j++) {
                if (pointsToSort[j] == last) continue ;
                if (p.slopeTo(pointsToSort[j]) != p.slopeTo(last)) continue ;

                if (first) {
                    System.out.print(p) ;
                    System.out.print(" -> ") ;
                    System.out.print(last) ;
                }

                first = false ;

                System.out.print(" -> ") ;

                System.out.print(pointsToSort[j]) ;
            }

            System.out.println("");
        }
    }

}