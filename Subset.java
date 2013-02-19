
public class Subset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] input = StdIn.readStrings();
		int k = Integer.parseInt(args[0]);		

		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		for (String item :input) {
			queue.enqueue(item);
		}
		
		for (int i = 0; i <k; i++) {
			String item = queue.dequeue();
			StdOut.println(item);
		}
	}
}
