import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private class Node<Item> {
		private Item value;
		private Node<Item> previous;
		private Node<Item> next;

		public Node(Item item) {
			value = item;
			next = null;
			previous = null;
		}
	}

	private Node<Item> first;
	private Node<Item> last;
	private int size;

	public Deque() {
		first = new Node<Item>(null);
		last = new Node<Item>(null);
		first.next = last;
		last.previous = first;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null) {
			throw new NullPointerException(
					"unable to add a null item");
		}
		Node<Item> node = new Node<Item>(item);
		node.next = first.next;
		first.next.previous = node;
		first.next = node;
		node.previous = first;
		size++;
	}

	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException(
					"unable to add a null item");
		}
		Node<Item> node = new Node<Item>(item);
		last.previous.next = node;
		node.next = last;
		node.previous = last.previous;
		last.previous = node;
		size++;
	}

	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException(
					"unable to remove an item "
					+ "from an empty deque");
		}
		Node<Item> node = first.next;
		first.next = node.next;
		node.next.previous = first;
		Item item = node.value;
		node = null;
		size--;
		return item;
	}

	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException(
					"unable to remove an " +
					"item from an empty deque");
		}
		Node<Item> node = last.previous;
		last.previous = node.previous;
		node.previous.next = last;
		Item item = node.value;
		node = null;
		size--;
		return item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator<Item>();
	}

	private class DequeIterator<Item> implements Iterator<Item> {
		private Node<Item> current = (Node<Item>) first;

		public boolean hasNext() {
			return current.next != last;
		}

		public void remove() {
			throw new UnsupportedOperationException(
					"the remove() method is " +
					"not supported in the iterator");
		}

		public Item next() {
			if (hasNext()) {
				Item item = current.next.value;
				current = current.next;
				return item;
			} else {
				throw new NoSuchElementException(
						"no more items to return " +
						"in the iterator");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Deque<Integer> deque = new Deque<Integer>();
		deque.addFirst(3);
		deque.addFirst(4);
		StdOut.println(deque.size());
		int item = deque.removeLast();
		StdOut.println(item);
		item = deque.removeLast();
		StdOut.println(item);
	}
}
