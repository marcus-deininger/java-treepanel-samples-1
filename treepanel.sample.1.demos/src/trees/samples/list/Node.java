package trees.samples.list;

public class Node {
	
	private String label;
	private Node next;

	public Node(String label) {
		this.label = label;
		this.next = null;
	}
	
	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return label;
	}
}
