public class Subset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] input = StdIn.readStrings();
		int k = Integer.parseInt(args[0]);

		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		for (String item : input) {
			rq.enqueue(item);
		}

		for (int i = 0; i < k; i++) {
			String item = rq.dequeue();
			StdOut.println(item);
		}
	}
}
