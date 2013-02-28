public class Brute {
	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);

		// read in the input
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			p.draw();
			points[i] = p;
		}

		for (int i = 0; i < N; i++) {
			Point p0 = points[i];
			for (int j = i + 1; j < N; j++) {
				Point p1 = points[j];
				for (int n = j + 1; n < N; n++) {
					Point p2 = points[n];
					for (int m = n + 1; m < N; m++) {
						Point p3 = points[m];
						if (p0.slopeTo(p1) == p0.slopeTo(p2)
								&& p0.slopeTo(p2) == p0.slopeTo(p3)) {
							String output = String.format("%s: %s %s %s "
									+ "slope %f, %f, %f", p0.toString(),
									p1.toString(), p2.toString(),
									p3.toString(), p0.slopeTo(p1),
									p0.slopeTo(p2), p0.slopeTo(p3));
							StdOut.println(output);
							p0.drawTo(p1);
							p1.drawTo(p2);
							p2.drawTo(p3);
						}
					}
				}
			}
		}

		// display to screen all at once
		StdDraw.show(0);
	}

}
