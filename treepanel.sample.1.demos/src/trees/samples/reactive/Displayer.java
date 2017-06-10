package trees.samples.reactive;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import trees.panel.TreePanel;
import trees.style.Alignment;
import trees.style.Shape;
import trees.style.Style;
import trees.synchronization.MonitorEntryEvent;
import trees.synchronization.MonitorExitEvent;
import trees.synchronization.MonitorListener;

import static trees.synchronization.Monitor.monitor;

@SuppressWarnings("serial")
public class Displayer extends JFrame{

	private static final int HEIGHT = 450;
	private static final int WIDTH = 600;
	
	private Node root;
	private TreePanel<Node> treePanel;
	private Style style;
	private Node selection;
	private JPopupMenu menu;
	private JDialog dialog;
	private JButton next, resume;
	private boolean menuEnabled = true;
	
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
		dialog.pack();		
		this.setVisible(true);		
	}

	private void initializeWidgets() {
		style = new Style(20, 20, 45);
		style.setShape(Shape.ROUNDED_RECTANGLE);
		style.setRootPointer("root");
		style.setHorizontalAlignment(Alignment.TREE_CENTER);
		treePanel = new TreePanel<Node>(style, root);
		
		next = new JButton("Next");
		resume = new JButton("Resume");
		dialog = new JDialog(this, "Trace", false);
	}
	
	private JPanel createWidgetLayout() {
		JPanel dialogPanel = new JPanel();
		dialogPanel.add(next);
		dialogPanel.add(resume);
		dialog.setPreferredSize(new Dimension(200, 100));
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.add(dialogPanel);
		
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
		
		JMenuItem traceDeleteItem = new JMenuItem("trace delete");
		traceDeleteItem.addActionListener((event)->{
			treePanel.clearNodeColor();
			monitor.enableBreakpoints();
			if(selection == root){
				root = null;
				treePanel.setTree(null);
			}else{
				menuEnabled = false;
				dialog.setLocationRelativeTo(treePanel);
				dialog.setVisible(true);
				monitor.invokeAsync(root, "delete", selection);
				// root.delete(selection);
			}
		});
		menu.add(traceDeleteItem);		        
	}

	private void createWidgetInteraction() {
		treePanel.addNodeSelectionListener((event) -> {			
			if(!menuEnabled)
				return;
			
			selection = event.getNode();

			treePanel.clearNodeColor();
			if(selection == null && root != null)
				return;
			
			treePanel.setNodeColor(Color.RED, selection);
			if(event.isPopUpTriggered())
				menu.show(event.getComponent(), event.getX(), event.getY());
		});
		
		monitor.addMonitorListener(new MonitorListener() {
			
			@Override
			public void monitorEntered(MonitorEntryEvent event) {
				treePanel.setNodeColor(Color.GREEN, event.getSource());
			}
			
			@Override
			public void monitorExited(MonitorExitEvent event) {
				monitor.disableBreakpoints();
				dialog.setVisible(false);
				selection = null;
				treePanel.repaint();
				menuEnabled = true;
			}
		});
		
		next.addActionListener((e)->{
			monitor.resume();
		});
		
		resume.addActionListener((e)->{
			monitor.disableBreakpoints();
			monitor.resume();			
		});
		

	}
	
}
