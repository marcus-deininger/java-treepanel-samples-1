package basic;

public class Main {
	
	public static void main(String[] args) {
				
		Node root = new Node("root", 
				new Node("n1",
						new Node("n1.1\n(first node)"),
						new Node("n1.2"),
						new Node("n1.3\n(last node)")), 
				new Node("n2", 
						new Node("n2.1")),
				new Node("n3"));
		
		new Displayer(root);
	}
}
