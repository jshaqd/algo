import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size = 4, count;
	private Item[] array;
	private int first, last;

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
		first = 0;
		last = first + count;
	}

	private int getArrayIndex(int i, int size) {
		return i % size;
	}

	public boolean isEmpty() {
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
		last = getArrayIndex(last, size);
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
		array[first] = null;
		first++;
		first = first % size;

		count--;

		if (count >= 4 && count < size / 4) {
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
		private int current = first;
		private Item[] randomizedArray;

		public RandomizedQueueIterator() {
			// StdOut.println("iterator constructor");
			randomizedArray = (Item[]) array.clone();
			for (int i = 0; i < count; i++) {
				int random = StdRandom.uniform(i + 1);
				int pos = getArrayIndex(first + i, size);
				int posRandom = getArrayIndex(first + random, size);
				
				Item item = randomizedArray[pos];
				randomizedArray[pos] = randomizedArray[posRandom];
				randomizedArray[posRandom] = item;
			}
		}

		public boolean hasNext() {
			return (first > last && (current >= first || current < last) || (first < last && current < last));
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

		for (int i = 1; i <= 20; i++) {
			queue.enqueue(i);
		}

		for (int i = 1; i <= 19; i++) {
			int item = queue.dequeue();
			queue.enqueue(item + 1);
		}
	}
}
