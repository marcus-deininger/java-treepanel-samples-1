package trees.samples.large;

public class Main {
	
	private static int count = 0;
	
	private static Node generate(int n, int level){
		Node node = new Node(Integer.toString(count++));
		if(level > 0)
			for(int i = 0; i < n; i++)
				node.add(generate(n, level - 1));
		return node;
	}
	
	public static void main(String[] args) {
				
		Node root = generate(2, 16);
		System.out.println((count + 1) + " Elements generated");
		
		new Displayer(root);
	}
}
