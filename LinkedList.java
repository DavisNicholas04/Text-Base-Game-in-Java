public class LinkedList {
	private Node first;
	private Node last;
	private int length = -1;

	private void makeOne(Object data) {
		first = last = new Node(data);

		incrementSize();
	}

	public LinkedList() {
		first = last = null;
	}

	public LinkedList(Object data) {
		makeOne(data);
	}

	public void append(Object data) {
		if (isEmpty()) {
			makeOne(data);
		} else {
			last.setNext(new Node(data));
			last = last.getNext();
			last.setNext(null);

			incrementSize();
		}
	}

	public boolean isEmpty() {
		return first == null;
	}

	public void prepend(Object data) {
		if (isEmpty()) {
			makeOne(data);
		} else {
			Node temp = new Node(data);
			temp.setNext(first);
			first = temp;

			incrementSize();
		}
	}

	public Object get(int index) {
		if (isEmpty()) {
			throw new LinkedListException("There are no elements in this list. Index out of bounds!");
		}
		int counter = 0;
		Node temp = first;
		while (counter < index) {
			if (temp == null) {
				throw new LinkedListException("Index out of bounds!");
			}
			counter = counter + 1;
			temp = temp.getNext();
		}
		return temp.getData();
	}
	public void incrementSize(){
		length++;
	}

	public void decrementSize(){
		length--;
	}
	public int length() {
		return length;
	}

	public void insert(Node prev_node, Object new_data) {
		if (prev_node == null) {
			prepend(new_data);
		} else {
			Node new_node = new Node(new_data);
			new_node.setNext(prev_node.getNext());
			prev_node.setNext(new_node);

			incrementSize();
		}
	}

	public void delete(Object key) {
		Node temp = first, prev = null;

		if (temp != null && temp.getData() == key) {
			first = temp.getNext();
			decrementSize();
			return;
		}


		while (temp != null && temp.getData() != key) {
			prev = temp;
			temp = temp.getNext();
		}

		if (temp == null){
			return;
		}

		prev.setNext(temp.getNext());
		decrementSize();
		setLast();
	}
	public void setLast(){
		Node temp = first;
		for(int x = 0; x <= length(); x++){
			if (x == length()){
				last = temp;
				return;
			}
			temp = temp.getNext();

		}
	}
	public void sort() {
		if (length() >= 1) {
			Creature temp = null;
			for (int i = 0; i < length() - 1; i++ ) {
				Node currentNode = first;
				Node next = first.getNext();
				Creature currentCreature = (Creature) currentNode.getData();
				Creature nextCreature = (Creature) next.getData();
				for (int j = 0; j <= length() - 1; j++) {
					if (currentCreature.CREATURE_NAME.compareTo(nextCreature.CREATURE_NAME) > 0) {
						temp = (Creature) currentNode.getData();
						currentNode.setData(next.getData());
						next.setData(temp);
					}
					if (next != null) {
						currentNode = next;
						next = next.getNext();
						if (next != null) {
							currentCreature = (Creature) currentNode.getData();
							nextCreature = (Creature) next.getData();
						}
					}
				}
				setLast();
			}
		}
	}
}