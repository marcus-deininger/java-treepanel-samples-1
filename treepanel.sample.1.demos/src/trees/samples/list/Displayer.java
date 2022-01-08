package trees.samples.list;

import javax.swing.JFrame;

import trees.panel.TreePanel;
import trees.style.Orientation;
import trees.style.Shape;
import trees.style.Size;
import trees.style.Style;

@SuppressWarnings("serial")
public class Displayer extends JFrame{

	public Displayer(Node root){
		super("Sample");

		Style style = new Style(0, 0, 75);
		style.setShape(Shape.RECTANGLE);
		style.setSize(Size.FIXED(50, 25));
		style.setRootPointer("start");
		style.setPointerBoxes(true);
		style.setOrientation(Orientation.WEST);		
		
		TreePanel<Node> treePanel = new TreePanel<Node>(style, root);

		this.add(treePanel);		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);
		this.pack();
		this.setVisible(true);		
	}
}
