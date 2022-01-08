package trees.samples.reactive;

import java.util.ArrayList;
import java.util.List;

import static trees.synchronization.Monitor.monitor;

public class Node {
	
	private String label;
	private List<Node> children = new ArrayList<>();

	public Node(String label, Node ... nodes) {
		this.label = label;
		this.add(nodes);
	}
	
	public void add(Node ... nodes){
		for(Node node : nodes)
			children.add(node);
	}

	public boolean delete(Node node) {
		monitor.breakpoint(this, "entered");
		
		if(children.remove(node))
			return true;

		for(Node child : children)
			if(child.delete(node))
				return true;
		
		return false;
	}

	@Override
	public String toString() {
		return label;
	}
}
