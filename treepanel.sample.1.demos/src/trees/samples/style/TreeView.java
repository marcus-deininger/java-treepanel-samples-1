package trees.samples.style;

import static trees.style.Alignment.*;
import static trees.style.Orientation.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import trees.panel.TreePanel;
import trees.style.Alignment;
import trees.style.Orientation;
import trees.style.Shape;
import trees.style.Size;
import trees.style.Style;
import trees.style.sizes.Fixed;
import trees.style.sizes.MaxVariable;
import trees.style.sizes.MinVariable;
import trees.style.sizes.RestrictedVariable;
import trees.style.sizes.Variable;


@SuppressWarnings("serial")
public class TreeView extends JFrame{
	
	private static final int WIDTH = 600, HEIGHT = 450;

	private static final int INITIAL_DEPTH = 10;
	
	// see: en.wikipedia.org/wiki/Core_fonts_for_the_Web
	// "Default" is an internally used java name
	private static final String[] CORE_FONTS = {"Default", "Arial", "Arial Black", "Comic Sans MS", 
		"Courier New", "Georgia", "Impact", "Times New Roman", "Trebuchet MS",
		"Verdana"};

	private static final int INITIAL_FONT_SIZE = 12;
	private static final int INITIAL_FONT = 0; // Default;

	private static final int INITIAL_SIBLING_SEPARATION = 40;
	private static final int INITIAL_SUBTREE_SEPARATION = 40;
	private static final int INITIAL_LEVEL_SEPARATION = 60;
	
	private static final int INITIAL_BOX_SIZE = 20;
	

	// Define functionals widgets here
	private JButton clearButton, addNodeButton, addBinaryNodeButton, deleteButton;
	private SpinnerNumberModel fontSizeModel;
	private SpinnerNumberModel minSizeModelWidth, minSizeModelHeight; 
	private SpinnerNumberModel maxSizeModelWidth, maxSizeModelHeight;
	

	private Font initialFont;

	private Node root, selection;
	private TreePanel<Node> treePanel;
	private boolean nodeColoring = true;

	public TreeView(){
		super("TreePanel Demo");
		
		// The Model
		
		root = null;

		// create instances of functional widgets here
		addNodeButton = new JButton("Add Node");
		addBinaryNodeButton = new JButton("Add Binary Node");
		deleteButton = new JButton("Delete");
		clearButton = new JButton("Clear");
		

		initializeWidgets();
		JPanel panel = createWidgetLayout();
		JMenuBar menuBar = createMenuLayout();
		createWidgetInteraction();
	
		this.setJMenuBar(menuBar);
		this.setContentPane(panel);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);
		this.pack();
		this.setVisible(true);
	}
	
	
	private void initializeWidgets() {
		// initialize functional widgets here
				
		Style style = new Style(INITIAL_SIBLING_SEPARATION, INITIAL_SUBTREE_SEPARATION, INITIAL_LEVEL_SEPARATION);
		style.setMaxDepth(INITIAL_DEPTH);
		
		fontSizeModel = new SpinnerNumberModel(INITIAL_FONT_SIZE, 1, 128, 1);
		initialFont = new Font(CORE_FONTS[INITIAL_FONT], 0, INITIAL_FONT_SIZE);
		style.setFont(initialFont);	
		
		minSizeModelWidth = new SpinnerNumberModel(INITIAL_BOX_SIZE, 1, 1000, 1);
		minSizeModelHeight = new SpinnerNumberModel(INITIAL_BOX_SIZE, 1, 1000, 1);
		maxSizeModelWidth = new SpinnerNumberModel(INITIAL_BOX_SIZE, 1, 1000, 1);
		maxSizeModelHeight = new SpinnerNumberModel(INITIAL_BOX_SIZE, 1, 1000, 1);
		
		treePanel = new TreePanel<>(style, root);		
	}
	
	private JMenuBar createMenuLayout(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		ButtonGroup group;
		JCheckBoxMenuItem cbMenuItem;
		JPanel panel;
		
		//////////////////////////////////////////////////////////////

		menu = new JMenu("Samples");
		menuBar.add(menu);

		submenu = new JMenu("Sample 1 (J.Q. Walker's Sample)");
		menu.add(submenu);
		final String about1 = "This is the layout example as described in the paper:\n" + 
				"Walker, J.Q. II: A node-positioning algorithm for general trees.\n" + 
				"Software-Practice and Experience 1990; 20(7):685-705.";

		menuItem = new JMenuItem("About ...");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(TreeView.this, about1,
					    "About this Sample", JOptionPane.PLAIN_MESSAGE);			
			}		
		});
		submenu.add(menuItem);

		menuItem = new JMenuItem("Run");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sample1();
				treePanel.repaint();
			}
			
		});
		submenu.add(menuItem);

		//*********************

		submenu = new JMenu("Sample2 (abego-Sample)");
		menu.add(submenu);
		final String about2 = "This is the layout example as implemented for:\n" + 
				"abego TreeLayout (code.google.com/p/treelayout)\n";


		menuItem = new JMenuItem("About ...");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(TreeView.this, about2,
					    "About this Sample", JOptionPane.PLAIN_MESSAGE);			
			}		
		});
		submenu.add(menuItem);

		menuItem = new JMenuItem("Run");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sample2();
				treePanel.repaint();
			}
			
		});
		submenu.add(menuItem);		
		
		//*********************
		
		submenu = new JMenu("Sample3 (Binary Tree)");
		menu.add(submenu);
		final String about3 = "This is a sample for using binary trees\nwith different node classes.";


		menuItem = new JMenuItem("About ...");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(TreeView.this, about3,
					    "About this Sample", JOptionPane.PLAIN_MESSAGE);			
			}		
		});
		submenu.add(menuItem);

		menuItem = new JMenuItem("Run");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sample3();
				treePanel.repaint();
			}
			
		});
		submenu.add(menuItem);		
		
		//////////////////////////////////////////////////////////////
		
		menu = new JMenu("Placement");	
		menuBar.add(menu);
		
		submenu = new JMenu("Orientation");
		group = new ButtonGroup();
		this.addOrientationMenuRadioButton("North", NORTH, true, group, submenu);
		this.addOrientationMenuRadioButton("South", SOUTH, false, group, submenu);
		this.addOrientationMenuRadioButton("East", EAST, false, group, submenu);
		this.addOrientationMenuRadioButton("West", WEST, false, group, submenu);
		menu.add(submenu);

		submenu = new JMenu("Horizontal Alignment");
		group = new ButtonGroup();
		this.addAlignmentMenuRadioButton("Left", false, LEFT, true, group, submenu);
		this.addAlignmentMenuRadioButton("Tree centered", false, TREE_CENTER, false, group, submenu);
		this.addAlignmentMenuRadioButton("Root centered", false, ROOT_CENTER, false, group, submenu);
		this.addAlignmentMenuRadioButton("Right", false, RIGHT, false, group, submenu);
		menu.add(submenu);

		
		submenu = new JMenu("Vertical Alignment");
		group = new ButtonGroup();
		this.addAlignmentMenuRadioButton("Top", true, TOP, true, group, submenu);
		this.addAlignmentMenuRadioButton("Tree centered", true, TREE_CENTER, false, group, submenu);
		this.addAlignmentMenuRadioButton("Root centered", true, ROOT_CENTER, false, group, submenu);
		this.addAlignmentMenuRadioButton("Bottom", true, BOTTOM, false, group, submenu);
		menu.add(submenu);

		menuBar.add(menu);

		//////////////////////////////////////////////////////////////
		
		menu = new JMenu("Decoration");	
		menuBar.add(menu);
		
//		menuItem = new JMenuItem("<Placeholder>");
//		menu.add(menuItem);
//		menu.addSeparator();		
		
		// Root pointer and Boxes

		cbMenuItem = new JCheckBoxMenuItem("Show Root Pointer");
		cbMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				if(button.isSelected())
					treePanel.getStyle().setRootPointer("root");
				else
					treePanel.getStyle().setRootPointer(null);
				treePanel.repaint();
			}
			
		});
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Show Pointer Boxes");
		cbMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				treePanel.getStyle().setPointerBoxes(button.isSelected());
				treePanel.repaint();
			}
			
		});
		menu.add(cbMenuItem);
		
		// Use of Placeholders

		cbMenuItem = new JCheckBoxMenuItem("Use Placeholders");
		cbMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				if(selection == null)
					treePanel.getStyle().setPlaceHolder(button.isSelected());
				else
					treePanel.getStyle().setPlaceHolder(selection.getClass(), button.isSelected());
				treePanel.repaint();
			}
			
		});
		menu.add(cbMenuItem);
		
		// Shape
		
		menu.addSeparator();

		group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("Reactangle");
		rbMenuItem.setSelected(treePanel.getStyle().getShape() == Shape.RECTANGLE);
		rbMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				if(button.isSelected()){
					if(selection == null)
						treePanel.getStyle().setShape(Shape.RECTANGLE);
					else
						treePanel.getStyle().setShape(selection.getClass(), Shape.RECTANGLE);
					treePanel.repaint();
				}
			}			
		});
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Rounded Rectangle");
		rbMenuItem.setSelected(treePanel.getStyle().getShape() == Shape.ROUNDED_RECTANGLE);
		rbMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				if(button.isSelected()){
					if(selection == null)
						treePanel.getStyle().setShape(Shape.ROUNDED_RECTANGLE);
					else
						treePanel.getStyle().setShape(selection.getClass(), Shape.ROUNDED_RECTANGLE);
					treePanel.repaint();
				}
			}			
		});
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		// Node/Subtree coloring
		
		menu.addSeparator();

		group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("Color Nodes");
		rbMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				if(button.isSelected()){
					nodeColoring = true;
					treePanel.clearSubtreeColor();
					if(selection != null)
						treePanel.setNodeColor(Color.GREEN, selection);
					treePanel.repaint();
				}
			}			
		});
		rbMenuItem.setSelected(true);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Color Subtrees");
		rbMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				if(button.isSelected()){
					nodeColoring = false;
					treePanel.clearNodeColor();
					if(selection != null)
						treePanel.setSubtreeColor(Color.RED, selection);
					treePanel.repaint();
				}
			}			
		});
		rbMenuItem.setSelected(false);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		// Fonts
		menu.addSeparator();
				
		panel = new JPanel(new GridLayout(1, 2));
		panel.add(new JLabel("      Size"));
		fontSizeModel.setValue(treePanel.getStyle().getFont().getSize());
		JSpinner spinner = new JSpinner(fontSizeModel);
        spinner.getModel().addChangeListener(new ChangeListener() {           
            @Override public void stateChanged(ChangeEvent e) {
				int fontSize = fontSizeModel.getNumber().intValue();
				if(selection == null){
					Font font = treePanel.getStyle().getFont();
					String fontfamily = font.getFamily();
					font = new Font(fontfamily, 0, fontSize); 
					treePanel.getStyle().setFont(font);
				}else{
					Font font = treePanel.getStyle().getFont(selection.getClass());
					String fontfamily = font.getFamily();
					font = new Font(fontfamily, 0, fontSize); 
					treePanel.getStyle().setFont(selection.getClass(), font);
				}
				treePanel.repaint();
        }});

		panel.add(spinner);
		menu.add(panel);

		submenu = new JMenu("Font");
		group = new ButtonGroup();
		for(String fontname : CORE_FONTS)
			addFontMenuRadioButton(fontname, fontname.equals(treePanel.getStyle().getFont().getName()), group, submenu);
		menu.add(submenu);
		
		
		//////////////////////////////////////////////////////////////
		
		menu = new JMenu("Layout");	
		menuBar.add(menu);
		
		menu.add(getLayoutItem("Sibling Sep. ", 1, treePanel.getStyle().getSiblingSeparation(), 
				(value) -> { treePanel.getStyle().setSiblingSeparation(value); }));
	
		menu.add(getLayoutItem("Subtree Sep. ", 1, treePanel.getStyle().getSubtreeSeparation(), 
				(value) -> { treePanel.getStyle().setSubtreeSeparation(value); }));
	
		menu.add(getLayoutItem("Level Sep. ", 1, treePanel.getStyle().getLevelSepartion(), 
				(value) -> { treePanel.getStyle().setLevelSeparation(value); }));
	
		menu.add(getLayoutItem("Depth", -1, treePanel.getStyle().getMaxDepth(), 
				(value) -> { treePanel.getStyle().setMaxDepth(value); }));
		
		
		// Sizes
		menu.addSeparator();
			
		submenu = new JMenu("Sizes");
		group = new ButtonGroup();

		SpinnerGetter w = () -> { return (int)minSizeModelWidth.getValue(); };
		SpinnerGetter h = () -> { return (int)minSizeModelHeight.getValue(); };
		SpinnerGetter W = () -> { return (int)maxSizeModelWidth.getValue(); };
		SpinnerGetter H = () -> { return (int)maxSizeModelHeight.getValue(); };
		
		menu.add(getMinSizeSpinners());
		menu.add(getMaxSizeSpinners());

		boolean isVariable = type() == SizeType.VARIABLE;
		boolean isMinVariable = type() == SizeType.MIN_VARIABLE;
		boolean isMaxVariable = type() == SizeType.MAX_VARIABLE;
		boolean isRestrictedVariable = type() == SizeType.RESTRICTED_VARIABLE;
		boolean isFixed = type() == SizeType.FIXED;

		addSizeMenuRadioButton("Variable", isVariable, group, submenu, () -> { return Size.VARIABLE();});
		addSizeMenuRadioButton("Variable w/Min", isMinVariable, group, submenu, () -> { return Size.MIN_VARIABLE(w.value(), h.value());});
		addSizeMenuRadioButton("Variable w/Max", isMaxVariable, group, submenu,  () -> { return Size.MAX_VARIABLE(W.value(), H.value());});
		addSizeMenuRadioButton("Variable w/Min & Max", isRestrictedVariable, group, submenu, () -> { return Size.RESTRICTED_VARIABLE(w.value(), h.value(), W.value(), H.value());});
		addSizeMenuRadioButton("Fixed", isFixed, group, submenu, () -> { return Size.FIXED(W.value(), H.value());});
		menu.add(submenu);

		return menuBar;
	}
	
	private void addOrientationMenuRadioButton(String title, final Orientation orientation,
			boolean selected, ButtonGroup group, JMenu menu) {
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem(title);
		ActionListener orientationListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				if(button.isSelected()){
					Style style = treePanel.getStyle();
					style.setOrientation(orientation);
					treePanel.repaint();
				}
			}
		};
		rbMenuItem.addActionListener(orientationListener);
		rbMenuItem.setSelected(selected);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
	}
	
	private void addAlignmentMenuRadioButton(String title, final boolean vertical, final Alignment alignment,
			boolean selected, ButtonGroup group, JMenu menu) {
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem(title);
		ActionListener alignmentListener = new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton)e.getSource();
				if(button.isSelected()){
					Style style = treePanel.getStyle();
					if(vertical)
						style.setVerticalAlignment(alignment);
					else
						style.setHorizontalAlignment(alignment);
					treePanel.repaint();
				}
			}
			
		};

		rbMenuItem.addActionListener(alignmentListener);
		rbMenuItem.setSelected(selected);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
	}

	private void addFontMenuRadioButton(final String fontFamilyName,
			boolean selected, ButtonGroup group, JMenu menu){
		
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem(fontFamilyName);
		ActionListener actionListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int fontSize = fontSizeModel.getNumber().intValue();
				Font font = new Font(fontFamilyName, 0, fontSize);
				if(selection == null)
					treePanel.getStyle().setFont(font);
				else
					treePanel.getStyle().setFont(selection.getClass(), font);
				treePanel.repaint();
			}
		};

		rbMenuItem.addActionListener(actionListener);
		rbMenuItem.setSelected(selected);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
	}
	
	
	@FunctionalInterface
	interface Setter{
		void set(int value);
	}
	
	private JPanel getLayoutItem(String text, int minimum, int initialValue, final Setter setter){
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel = new JPanel(new GridLayout(1, 2));
		panel.add(new JLabel(text));
		
		final SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(minimum);
		JSpinner spinner = new JSpinner(model);
		model.setValue(initialValue);
		panel.add(spinner);
	
		spinner.addChangeListener(new ChangeListener(){
			@Override public void stateChanged(ChangeEvent e) {
				setter.set((int) model.getValue());
				treePanel.repaint();
			}			
		});
		return panel;
	}
	
	private enum SizeType{
		FIXED, MAX_VARIABLE, MIN_VARIABLE, RESTRICTED_VARIABLE, VARIABLE
	}
	
	private SizeType type(){
		Size size = treePanel.getStyle().getSize(null);		
		if(size.getClass() == MinVariable.class) return SizeType.MIN_VARIABLE;
		if(size.getClass() == MaxVariable.class) return SizeType.MAX_VARIABLE;
		if(size.getClass() == RestrictedVariable.class) return SizeType.RESTRICTED_VARIABLE;
		if(size.getClass() == Fixed.class) return SizeType.FIXED;
		if(size.getClass() == Variable.class) return SizeType.VARIABLE;
		throw new RuntimeException("Undefinied Size Type");
	}

	@FunctionalInterface
	private interface SpinnerGetter{
		int value();
	}
	
	@FunctionalInterface
	private interface TreeSize{
		Size size();
	}
	
	private void addSizeMenuRadioButton(String sizeName, boolean selected, ButtonGroup group, JMenu menu, TreeSize tree) {
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem(sizeName);
		ActionListener actionListener = (e)-> {
				if(selection == null)
					treePanel.getStyle().setSize(tree.size());
				else
					treePanel.getStyle().setSize(selection.getClass(), tree.size());
				treePanel.repaint();			
		};

		rbMenuItem.addActionListener(actionListener);
		rbMenuItem.setSelected(selected);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
	}

	private static final Dimension SPINNER_SIZE = new Dimension(4, 20);

	private JPanel getMinSizeSpinners(){
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(new JLabel("   Min"));
		
		JSpinner widthSpinner = new JSpinner(minSizeModelWidth);
		widthSpinner.setPreferredSize(SPINNER_SIZE);
		panel.add(widthSpinner);

		JSpinner heightSpinner = new JSpinner(minSizeModelHeight);
		heightSpinner.setPreferredSize(SPINNER_SIZE);
		panel.add(heightSpinner);
	
		ChangeListener listener = (e) -> {
			Size size = null;
			switch(type()){
			case MIN_VARIABLE:
					size = Size.MIN_VARIABLE((int)minSizeModelWidth.getValue(), (int)minSizeModelHeight.getValue());
				break;
			case RESTRICTED_VARIABLE:
					size = Size.RESTRICTED_VARIABLE((int)minSizeModelWidth.getValue(), (int)minSizeModelHeight.getValue(), (int)maxSizeModelWidth.getValue(), (int)maxSizeModelHeight.getValue());
				break;
			default: return;
			}
			if(selection == null)
				treePanel.getStyle().setSize(size);
			else
				treePanel.getStyle().setSize(selection.getClass(), size);
			treePanel.repaint();			
		};

		widthSpinner.addChangeListener(listener);
		heightSpinner.addChangeListener(listener);

		return panel;
	}

	private JPanel getMaxSizeSpinners(){
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(new JLabel("   Max"));
		
		JSpinner widthSpinner = new JSpinner(maxSizeModelWidth);
		widthSpinner.setPreferredSize(SPINNER_SIZE);
		panel.add(widthSpinner);

		JSpinner heightSpinner = new JSpinner(maxSizeModelHeight);
		heightSpinner.setPreferredSize(SPINNER_SIZE);
		panel.add(heightSpinner);
	
		ChangeListener listener = (e) -> {
			Size size = null;
			switch(type()){
			case MAX_VARIABLE:
					size = Size.MIN_VARIABLE((int)maxSizeModelWidth.getValue(), (int)maxSizeModelHeight.getValue());
				break;
			case RESTRICTED_VARIABLE:
					size = Size.RESTRICTED_VARIABLE((int)minSizeModelWidth.getValue(), (int)minSizeModelHeight.getValue(), (int)maxSizeModelWidth.getValue(), (int)maxSizeModelHeight.getValue());
				break;
			case FIXED:
				size = Size.FIXED((int)maxSizeModelWidth.getValue(), (int)maxSizeModelHeight.getValue());
			break;
			default: return;
			}
			if(selection == null)
				treePanel.getStyle().setSize(size);
			else
				treePanel.getStyle().setSize(selection.getClass(), size);
			treePanel.repaint();			
		};

		widthSpinner.addChangeListener(listener);
		heightSpinner.addChangeListener(listener);

		return panel;
	}

	
	private JPanel createWidgetLayout() {
		JPanel panel = new JPanel(new BorderLayout());
		// create the layout here - if needed define supporting widgets like labels, etc.
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		panel.add(treePanel.addScrollPane(), BorderLayout.CENTER);
	
		JPanel controls = new JPanel();
		controls.add(addNodeButton);
		controls.add(addBinaryNodeButton);
		controls.add(deleteButton);
		controls.add(clearButton);
		panel.add(controls, BorderLayout.SOUTH);
		
		return panel;
	}
	
	
	private void createWidgetInteraction() {
		// add Listeners, etc. here
		
		final ActionListener addAction = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(root != null && selection == null){
					JOptionPane.showMessageDialog(TreeView.this, "Select element first");
					return;
				}
				
				String value = JOptionPane.showInputDialog(TreeView.this, "New Element");
				if(value == null || value.trim().equals(""))
					return;
				
				value = value.trim();
				if(root == null){
					root = new Node(value);
					treePanel.setTree(root);
				}else{
					selection.add(new Node(value));
					treePanel.repaint();
				}
			}			
		};
		
		final ActionListener addBinaryAction = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(root != null && selection == null){
					JOptionPane.showMessageDialog(TreeView.this, "Select element first");
					return;
				}
				
				String value = JOptionPane.showInputDialog(TreeView.this, "New Element");
				if(value == null || value.trim().equals(""))
					return;
				
				value = value.trim();
				if(root == null){
					root = new BinaryNode(value);
					treePanel.setTree(root);
				}else{
					selection.add(new BinaryNode(value));
					treePanel.repaint();
				}
			}			
		};
		
		final ActionListener deleteAction = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(selection == null){
					JOptionPane.showMessageDialog(TreeView.this, "Select element first");
					return;
				}
				if(selection == root){
					root = null;
					treePanel.setTree(root);
				}else{
					root.delete(selection);
					treePanel.repaint();
				}
			}			
		};
		
		final ActionListener clearAction = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				treePanel.clear();
				root = null;
				selection = null;
				treePanel.repaint();
			}			
		};

		addNodeButton.addActionListener(addAction);
		addBinaryNodeButton.addActionListener(addBinaryAction);
		deleteButton.addActionListener(deleteAction);
		clearButton.addActionListener(clearAction);
		
		treePanel.setComponentPopupMenu(new JPopupMenu (){
		    {	// initializing Block
		    	JMenuItem item;
		    	item = new JMenuItem("Add general Node");
		        item.addActionListener(addAction);
		        add(item);

		    	item = new JMenuItem("Add binary Node");
		        item.addActionListener(addBinaryAction);
		        add(item);

		    	item = new JMenuItem("Delete");
		        item.addActionListener(deleteAction);
		        add(item);

		        item = new JMenuItem("Clear");
		        item.addActionListener(clearAction);
		        add(item);
		    }});
		
		treePanel.addNodeSelectionListener((e)->{
			selection = e.getNode();

			treePanel.clearNodeColor();
			treePanel.clearSubtreeColor();
			if(selection == null)
				return;
			
			if(nodeColoring)
				treePanel.setNodeColor(Color.GREEN, selection);
			else
				treePanel.setSubtreeColor(Color.RED, selection);				
		});
 	}
	
	private void sample1(){
		selection = null;
		root = new Node("O", 
				new Node("E",
						new Node("A"),
						new Node("D",
								new Node("B"),
								new Node("C"))), 
				new Node("F"),
				new Node("N",
						new Node("G"),
						new Node("M",
								new Node("H"),
								new Node("I"),
								new Node("J"),
								new Node("K"),
								new Node("L")
						)));
		
		Style style = treePanel.getStyle();
		style.setFont(initialFont);
		style.setShape(Shape.RECTANGLE);

		style.setSiblingSeparation(40);
		style.setSubtreeSeparation(40);
		style.setLevelSeparation(50);
		style.setSize(Size.FIXED(20, 25));
		
		style.setRootPointer(null);
		style.setPointerBoxes(false);
		style.setPlaceHolder(false);
		
		minSizeModelWidth.setValue(20);
		maxSizeModelWidth.setValue(20);
		minSizeModelHeight.setValue(25);
		maxSizeModelHeight.setValue(25);

		treePanel.setTree(root);
		
		reInitMenu();
	}
	
	private void sample2(){
		selection = null;
		root = new Node("root", 
				new Node("n1",
						new Node("n1.1"),
						new Node("n1.2"),
						new Node("n1.3")), 
				new Node("n2", 
						new Node("n2.1")),
				new Node("n3"));

		Style style = treePanel.getStyle();
		style.setFont(initialFont);

		style.setSiblingSeparation(20);
		style.setSubtreeSeparation(20);
		style.setLevelSeparation(45);
		style.setSize(Size.VARIABLE());
		style.setShape(Shape.ROUNDED_RECTANGLE);

		style.setRootPointer(null);
		style.setPointerBoxes(false);
		style.setPlaceHolder(false);
		
		treePanel.setTree(root);

		reInitMenu();
	}

	private void sample3(){
		selection = null;
		root = new Node("root", 
				new BinaryNode("A", new BinaryNode("B", null, null), new BinaryNode("C", null, null)), 
				new BinaryNode("D", 
							new BinaryNode("E", new BinaryNode("G", null, null), null), 
							new BinaryNode("F", null, null)), 
				new BinaryNode("H", new BinaryNode("I", null, null), null)
				);

		Style style = treePanel.getStyle();
		style.setFont(initialFont);

		style.setSiblingSeparation(40);
		style.setSubtreeSeparation(40);
		style.setLevelSeparation(50);
		style.setSize(Size.MIN_VARIABLE(20, 25));

		style.setRootPointer(null);
		style.setPointerBoxes(false);
		style.setPlaceHolder(false);
		
		treePanel.setTree(root);

		reInitMenu();
	}

	private void reInitMenu() {
		// Redo the Menu
		JMenuBar menuBar = createMenuLayout();
		this.setJMenuBar(menuBar);
		this.revalidate();
	}

	public static void main(String[] args) {
		new TreeView();	
	}
}
