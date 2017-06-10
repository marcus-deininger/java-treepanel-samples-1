package trees.samples.clickable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import trees.panel.TreePanel;
import trees.style.Alignment;
import trees.style.Shape;
import trees.style.Style;

@SuppressWarnings("serial")
public class Displayer extends JFrame{

	private static final int HEIGHT = 450;
	private static final int WIDTH = 600;
	
	private Node root;
	private TreePanel<Node> treePanel;
	private Style style;
	private Node selection;
	private JPopupMenu menu;
	
	public Displayer(Node root){
		super("Sample");
		
		this.root = root;

		this.initializeWidgets();
		this.createMenuLayout();
		JPanel panel = this.createWidgetLayout();
		this.createWidgetInteraction();

		this.setContentPane(panel);	

		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);
		this.pack();
		this.setVisible(true);		
	}

	private void initializeWidgets() {
		style = new Style(20, 20, 45);
		style.setShape(Shape.ROUNDED_RECTANGLE);
		style.setRootPointer("root");
		style.setHorizontalAlignment(Alignment.TREE_CENTER);
		treePanel = new TreePanel<Node>(style, root);
	}
	
	private JPanel createWidgetLayout() {
		JPanel panel = new JPanel(new GridLayout());
		panel.add(treePanel.addScrollPane());
		return panel;
	}

	private void createMenuLayout(){
		menu = new JPopupMenu();
		
		JPanel addItem = new JPanel();
		JTextField labelField = new JTextField(10);
		addItem.add(labelField);
		JButton addButton = new JButton("add");
		addButton.setPreferredSize(new Dimension(addButton.getPreferredSize().width, 20));
		addItem.add(addButton);
		addButton.addActionListener((event)->{
			String label = labelField.getText();
			labelField.setText("");
			Node node = new Node(label);
			if(root == null){
				root = node;
				treePanel.setTree(root);
			}else
				selection.add(node);
			menu.setVisible(false);
			treePanel.repaint();
		});
		menu.add(addItem);
		
		JMenuItem deleteItem = new JMenuItem("delete");
		deleteItem.addActionListener((event)->{
			if(selection == root){
				root = null;
				treePanel.setTree(null);
			}else
				root.delete(selection);
			selection = null;
			treePanel.repaint();
		});
		menu.add(deleteItem);
	}

	private void createWidgetInteraction() {
		treePanel.addNodeSelectionListener((event) -> {			
			selection = event.getNode();

			treePanel.clearNodeColor();
			if(selection == null && root != null)
				return;
			
			treePanel.setNodeColor(Color.RED, selection);
			if(event.isPopUpTriggered())
				menu.show(event.getComponent(), event.getX(), event.getY());
		});
		
	}	
}
