package trees.samples.list;

public class Main {
	
	public static void main(String[] args) {
		
		int n = 10;
				
		Node start = new Node("0");
		Node p = start;
		for(int i = 1; i < n; i++, p = p.getNext())
			p.setNext(new Node(Integer.toString(i)));
		
		new Displayer(start);
	}
}
