import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node<Item> head;
	private Node<Item> tail;
	private int size;

	public Deque() {
		head = new Node(null);
		tail = new Node(null);
		head.Next = tail;
		tail.Previous = head;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException("client attempts to add a null item");
		Node<Item> node = new Node(item);
		node.Next = head.Next;
		head.Next.Previous = node;
		head.Next = node;
		node.Previous = head;
		size++;
	}

	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException("client attempts to add a null item");
		Node<Item> node = new Node(item);
		tail.Previous.Next = node;
		node.Next = tail;
		node.Previous = tail.Previous;
		tail.Previous = node;
		size++;
	}

	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException(
					"client attempts to remove an item from an empty deque");
		Node<Item> node = head.Next;
		head.Next = node.Next;
		node.Next.Previous = head;
		Item item = node.Value;
		node = null;
		size--;
		return item;
	}

	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException(
					"client attempts to remove an item from an empty deque");
		Node<Item> node = tail.Previous;
		head.Previous = node.Previous;
		node.Previous.Next = tail;
		Item item = node.Value;
		node = null;
		size--;
		return item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator<Item> implements Iterator<Item> {
		private Node<Item> current = (Node<Item>) head;

		public boolean hasNext() {
			return current.Next != tail;
		}

		public void remove() {
			throw new UnsupportedOperationException(
					"client calls the remove() method in the iterator");
		}

		public Item next() {			
			if (hasNext()) {
				Item item = current.Next.Value;
				current = current.Next;
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
		// TODO Auto-generated method stub

		Deque<Integer> deque = new Deque<Integer>();
		deque.addFirst(5);
		deque.addFirst(6);
		StdOut.println(deque.size());
		deque.removeLast();
		StdOut.println(deque.size());
		deque.removeLast();
	
		//Iterator<Integer> iterator = deque.iterator();
		//iterator.remove();		

		for (Integer i : deque) {
			StdOut.println(i);
		}

	}

}
