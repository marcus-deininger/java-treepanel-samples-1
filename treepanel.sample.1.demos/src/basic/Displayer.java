package basic;

import java.awt.Dimension;

import javax.swing.JFrame;

import trees.panel.TreePanel;
import trees.style.Shape;
import trees.style.Style;

@SuppressWarnings("serial")
public class Displayer extends JFrame{

	public Displayer(Node root){
		super("Sample");

		Style style = new Style(20, 20, 45);
		style.setShape(Shape.ROUNDED_RECTANGLE);
		TreePanel<Node> treePanel = new TreePanel<Node>(style, root);

		this.add(treePanel);		

		this.setPreferredSize(new Dimension(325, 200));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);
		this.pack();
		this.setVisible(true);		
	}
}
