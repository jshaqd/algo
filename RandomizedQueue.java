import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int arraySize = 4, itemCount;
	private Item[] array;
	private int first, last;

	public RandomizedQueue() {
		array = (Item[]) new Object[arraySize];
	}

	private void resize(Item[] oldArray, int capacity) {
		Item[] newArray = (Item[]) new Object[capacity];
		for (int i = 0; i < itemCount; i++) {
			newArray[i] = oldArray[getArrayIndex(first + i, arraySize)];
		}

		arraySize = capacity;
		array = newArray;
		first = 0;
		last = first + itemCount;
	}

	private int getArrayIndex(int i, int sz) {
		return i % sz;
	}

	public boolean isEmpty() {
		return itemCount == 0;
	}

	public int size() {
		return itemCount;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException(
					"unable to add a null item");
		}
		array[last] = item;
		last++;
		last = getArrayIndex(last, arraySize);
		itemCount++;

		if (itemCount == arraySize) {
			resize(array, arraySize * 2);
		}
	}

	public Item dequeue() {
		if (itemCount == 0) {
			throw new NoSuchElementException(
					"unable to deque an item from an empty randomized queue");
		}

		int index = getRandomIndex(itemCount);
		int pos = getArrayIndex(first + index, arraySize);
		Item item = (Item) array[pos];
		array[pos] = array[first];
		array[first] = null;
		first++;
		first = first % arraySize;

		itemCount--;

		if (itemCount >= 4 && itemCount < arraySize / 4) {
			resize(array, arraySize / 4);
		}

		return item;
	}

	public Item sample() {
		if (itemCount == 0) {
			throw new NoSuchElementException(
					"unable to sample an item from an empty randomized queue");
		}

		int index = getRandomIndex(itemCount);
		int pos = getArrayIndex(first + index, arraySize);
		Item item = (Item) array[pos];
		return item;
	}

	private int getRandomIndex(int number) {
		return StdRandom.uniform(number);
	}

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator<Item>();
	}

	private class RandomizedQueueIterator<Item> implements Iterator<Item> {
		private int current = first;
		private Item[] randomizedArray;

		public RandomizedQueueIterator() {
			// StdOut.println("iterator constructor");
			randomizedArray = (Item[]) array.clone();
			for (int i = 0; i < itemCount; i++) {
				int random = StdRandom.uniform(i + 1);
				int pos = getArrayIndex(first + i, arraySize);
				int posRandom = getArrayIndex(first + random, arraySize);

				Item item = randomizedArray[pos];
				randomizedArray[pos] = randomizedArray[posRandom];
				randomizedArray[posRandom] = item;
			}
		}

		public boolean hasNext() {
			return (first > last 
					&& (current >= first || current < last) 
					|| (first < last && current < last));
		}

		public void remove() {
			throw new UnsupportedOperationException(
					"the remove() method is not supported in the iterator");
		}

		public Item next() {
			if (hasNext()) {
				Item item = (Item) randomizedArray[current];
				current++;
				current = getArrayIndex(current, arraySize);
				return item;
			} else {
				throw new NoSuchElementException(
						"no more items to return in the iterator");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		for (int i = 0; i < 50; i++) {
			queue.enqueue(i);
		}
		for (int i = 0; i < 50; i++) {
			int item = queue.sample();
			StdOut.println(item);
		}
	}
}
