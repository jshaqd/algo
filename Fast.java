import java.util.Arrays;

public class Fast {
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

		for (int i = 0; i < N-2; i++) {
			Point p0 = points[i];
			Arrays.sort(points, i + 1, N - 1, p0.SLOPE_ORDER);

			int cnt = 0;
			double slope = Double.MIN_VALUE;
			for (int j = i + 1; j < N; j++) {
				double slope_To_P0 = p0.slopeTo(points[j]);
				
				//String op = String.format("Point0: %s; to Point: %s; Slope: %f; Slope to point: %f; cnt: %d",
						//p0.toString(), points[j].toString(), slope, slope_To_P0, cnt);
				//StdOut.println(op);
				
				if (slope_To_P0 != Double.MIN_VALUE) {					
					if (slope == Double.MIN_VALUE || slope != slope_To_P0) {
						slope = slope_To_P0;
						cnt= 1;
					}
					else if (slope == slope_To_P0) {
						cnt++;
					} 
					
					if (cnt == 3) {
						Point p1 = points[j-2];
						Point p2 = points[j-1];
						Point p3 = points[j];
						String output = String.format(
								"%s -> %s -> %s -> %s",
								p0.toString(), p1.toString(), p2.toString(),
								p3.toString());
						StdOut.println(output);
						Point start = getStart(p0, p1, p2, p3);
						Point end = getEnd(p0, p1, p2, p3);
						start.drawTo(end);
					}
				}
			}
		}

		// display to screen all at once
		StdDraw.show(0);
	}
	
	private static Point getStart(Point p0, Point p1, Point p2, Point p3) {
		Point start = p0;
		if (p1.compareTo(start) < 0) start = p1;
		if (p2.compareTo(start) < 0) start = p2;
		if (p3.compareTo(start) < 0) start = p3;
		return start;
	}
	
	private static Point getEnd(Point p0, Point p1, Point p2, Point p3) {
		Point end = p0;
		if (p1.compareTo(end) > 0) end = p1;
		if (p2.compareTo(end) > 0) end = p2;
		if (p3.compareTo(end) > 0) end = p3;
		return end;
	}
}
