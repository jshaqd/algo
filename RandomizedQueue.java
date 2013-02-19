import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	int size = 4, count;
	Item[] array;
	int first, last;

	public RandomizedQueue() {
		array = (Item[]) new Object[size];
	}

	private void resize(Item[] old, int capacity) {
		Item[] newArray = (Item[]) new Object[capacity];
		for (int i = 0; i < count; i++) {
			newArray[i] = old[getArrayIndex(first + i, size)];
		}

		size = capacity;
		array = newArray;
	}

	private int getArrayIndex(int i, int size) {
		return i % size;
	}

	public Boolean isEmpty() {
		return count == 0;
	}

	public int size() {
		return count;
	}

	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException(
					"the client attempts to add a null item");
		array[last] = item;
		last++;
		count++;

		if (count == size) {
			resize(array, size * 2);
		}
	}

	public Item dequeue() {
		if (count == 0)
			throw new NoSuchElementException(
					"the client attempts to deque an item from an empty randomized queue");

		int index = getRandomIndex(count);
		int pos = getArrayIndex(first + index, size);
		Item item = (Item) array[pos];
		array[pos] = array[first];
		first++;
		first = first % size;

		count--;

		if (count >= 4 && count == size / 4) {
			resize(array, size / 4);
		}

		return item;
	}

	public Item sample() {
		if (count == 0)
			throw new NoSuchElementException(
					"the client attempts to sample an item from an empty randomized queue");

		Iterator<Item> iterator = this.iterator();
		Item item = iterator.next();
		return item;
	}

	private int getRandomIndex(int number) {
		return StdRandom.uniform(number);
	}

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator<Item> implements Iterator<Item> {
		private int current = 0;
		private Item[] randomizedArray;

		public RandomizedQueueIterator() {
			randomizedArray = (Item[]) array.clone();
			for (int i = 0; i < count; i++) {
				int random = StdRandom.uniform(i + 1);
				StdOut.println("Exchange " + randomizedArray[i] + "  with random index " + randomizedArray[random]);
				//int pos = getArrayIndex(first + i, size);
				//int posRandom = getArrayIndex(first + pos, size);
				Item item = randomizedArray[i];
				randomizedArray[i] = randomizedArray[random];
				randomizedArray[random] = item;
			}

			for (int i = 0; i < size; i++) {
				StdOut.println(randomizedArray[i]);
			}
		}

		public boolean hasNext() {
			return (current >= first && (current > first || current < last))
					|| (current >= first && current < last);
		}

		public void remove() {
			throw new UnsupportedOperationException(
					"client calls the remove() method in the iterator");
		}

		public Item next() {
			if (hasNext()) {
				Item item = (Item) randomizedArray[current];
				current++;
				current = getArrayIndex(current, size);
				return item;
			} else
				throw new NoSuchElementException(
						"there are no more items to return in the iterator");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		for (int i = 1; i <= 10; i++)
			queue.enqueue(i);

		StdOut.println(queue.size());

		//for (int i = 1; i < 10; i++)
			//StdOut.println(queue.sample());

		 for(Integer i: queue) {
		 StdOut.println(i);
		 }
	}
}
