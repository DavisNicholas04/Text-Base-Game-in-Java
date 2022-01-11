public class Node {
	private Object data;
	private Node next;

	public Node(Object d) {
		this(d, null);
	}

	public Node(Object d, Node n) {
		setData(d);
		setNext(n);
	}

	public Object getData() {
		return data;
	}

	public Object setData(Object d) {
		Object temp = data;
		data = d;
		return temp;
	}

	public Node getNext() {
		return next;
	}

	public Node setNext(Node n) {
		Node temp = next;
		next = n;
		return temp;
	}
}