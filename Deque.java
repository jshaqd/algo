import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
 private class Node<Item> {
  private Item value;
  private Node<Item> next;
  private Node<Item> previous;
  
  public Node(Item item) {
   value = item;
   next = null;
   previous = null;
  }
 }
 private Node<Item> head;
 private Node<Item> tail;
 private int size;

 public Deque() {
  head = new Node(null);
  tail = new Node(null);
  head.next = tail;
  tail.previous = head;
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
  node.next = head.next;
  head.next.previous = node;
  head.next = node;
  node.previous = head;
  size++;
 }

 public void addLast(Item item) {
  if (item == null)
   throw new NullPointerException(
       "client attempts to add a null item");
  Node<Item> node = new Node(item);
  tail.previous.next = node;
  node.next = tail;
  node.previous = tail.previous;
  tail.previous = node;
  size++;
 }

 public Item removeFirst() {
  if (isEmpty())
   throw new NoSuchElementException(
     "client attempts to remove an item from an empty deque");
  Node<Item> node = head.next;
  head.next = node.next;
  node.next.previous = head;
  Item item = node.value;
  node = null;
  size--;
  return item;
 }

 public Item removeLast() {
  if (isEmpty())
   throw new NoSuchElementException(
     "client attempts to remove an item from an empty deque");
  Node<Item> node = tail.previous;
  tail.previous = node.previous;
  node.previous.next = tail;
  Item item = node.value;
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
   return current.next != tail;
  }

  public void remove() {
   throw new UnsupportedOperationException(
     "client calls the remove() method in the iterator");
  }

  public Item next() {   
   if (hasNext()) {
    Item item = current.next.value;
    current = current.next;
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
  int item = deque.removeLast();
  StdOut.println(item);
  item = deque.removeLast();
  StdOut.println(item);
 
  //Iterator<Integer> iterator = deque.iterator();
  //iterator.remove();  

  //for (Integer i : deque) {
   //StdOut.println(i);
  //}

 }

}
