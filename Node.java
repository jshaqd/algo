
public class Node<Item> {
	public Item Value;
	public Node<Item> Next;
	public Node<Item> Previous;
	
	public Node(Item item) {
		Value = item;
		Next = null;
		Previous = null;
	}
}
