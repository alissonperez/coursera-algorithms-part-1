import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Brute {

	private Point[] points;

	public static void main(String args[]) throws FileNotFoundException {
		Brute b = new Brute();
		b.populateFromFile(args[0]);
		b.printLineSegments();
	}

	private void populateFromFile(String filepath) throws FileNotFoundException {
		Scanner s = new Scanner(new File(filepath));
		int size = s.nextInt();

		points = new Point[size];

		for (int i = 0; i < size; i++) {
			points[i] = new Point(s.nextInt(), s.nextInt());
			// System.out.println(points[i]);
		}

		Quick.sort(points);
	}

	private void printLineSegments() {
		int i, j, k, l;

		Point[] pointsToSort = new Point[4];

		for (i = 0; i < points.length; i++) {
			for (j = i+1; j < points.length; j++) {
				for (k = j+1; k < points.length; k++) {
					for (l = k+1; l < points.length; l++) {
						if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
								&& points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
							pointsToSort[0] = points[i];
							pointsToSort[1] = points[j];
							pointsToSort[2] = points[k];
							pointsToSort[3] = points[l];

							Quick.sort(pointsToSort);
							System.out.println(pointsToSort[0] + " -> " + pointsToSort[1] + " -> " + pointsToSort[2] + " -> " + pointsToSort[3]);
						}
					}
				}
			}
		}
	}

}