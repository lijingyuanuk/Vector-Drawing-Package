package view.guiview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import model.DrawModel;
import controller.DrawController;

public class DrawGUIView implements ActionListener {

	private DrawModel model;
	private DrawController controller;
	
	public JFrame mainFrame;
	private JPanel controlPanel;
	private JMenuBar drawMenu;

	private static int DEFAULT_FRAME_WIDTH = 800;
	private static int DEFAULT_FRAME_HEIGHT = 600;

	protected static String BUTTON_COLOUR_COMMAND = "Colour";
	protected static String BUTTON_LINE_COMMAND = "Line";
	protected static String BUTTON_RECTANGLE_COMMAND = "Rectangle";
	protected static String BUTTON_ELLIPSE_COMMAND = "Ellipse";
	protected static String BUTTON_PARALLELOGRAM_COMMAND = "Parallelogram";
	protected static String BUTTON_UNDO_COMMAND = "Undo";
	protected static String BUTTON_REDO_COMMAND = "Redo";

	private JButton colorButton;
	private JButton lineButton;
	private JButton rectangleButton;
	private JButton ellipseButton;
	private JButton parallelogramButton;
	private JButton undoButton;
	private JButton redoButton;

	// create color as global variable
	Color c;

	public DrawGUIView(DrawModel model, DrawController controller) {
		
		this.model = model;
		this.controller = controller;
		
		controlPanel = new JPanel();
		drawMenu = new JMenuBar();
		// create frame for view of drawing board and control buttons
		mainFrame = new JFrame("Vector Drawing Package");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set frame size
		mainFrame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
		// display frame
		mainFrame.setVisible(true);

		// add grid to center of frame
		// add controlPanel to top of frame
		mainFrame.getContentPane().add(this.model, BorderLayout.CENTER);
		mainFrame.getContentPane().add(this.controlPanel, BorderLayout.NORTH);

		// add button elements to panel
		addControlElements();
		// add menu to panel
		addMenu();
		// add the GUI as a listener for the GUI buttons
		addActionListenerForButtons(this);

	}

	private void addControlElements() {
		colorButton = new JButton(BUTTON_COLOUR_COMMAND);
		lineButton = new JButton(BUTTON_LINE_COMMAND);
		rectangleButton = new JButton(BUTTON_RECTANGLE_COMMAND);
		ellipseButton = new JButton(BUTTON_ELLIPSE_COMMAND);
		parallelogramButton = new JButton(BUTTON_PARALLELOGRAM_COMMAND);
		undoButton = new JButton(BUTTON_UNDO_COMMAND);
		redoButton = new JButton(BUTTON_REDO_COMMAND);
		controlPanel.add(colorButton);
		controlPanel.add(lineButton);
		controlPanel.add(rectangleButton);
		controlPanel.add(ellipseButton);
		controlPanel.add(parallelogramButton);
		controlPanel.add(undoButton);
		controlPanel.add(redoButton);
	}

	private void addMenu() {
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem openImage = new JMenuItem("OpenImage");
		file.add(open);
		file.add(save);
		file.add(openImage);
		drawMenu.add(file);
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				try {
					model.openFile();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					model.saveFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		openImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					model.openImage();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});

		mainFrame.setJMenuBar(drawMenu);
	}

	public void addActionListenerForButtons(ActionListener al) {
		colorButton.addActionListener(al);
		lineButton.addActionListener(al);
		rectangleButton.addActionListener(al);
		ellipseButton.addActionListener(al);
		parallelogramButton.addActionListener(al);
		undoButton.addActionListener(al);
		redoButton.addActionListener(al);
	}

	//use a single listener and check the source of the event to decide what to do
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == colorButton) {
			model.color = JColorChooser.showDialog(null, "color", null);
		} else if (e.getSource() == lineButton) {
			model.order = 1;
		} else if (e.getSource() == rectangleButton) {
			model.order = 2;
		} else if (e.getSource() == ellipseButton) {
			model.order = 3;
		} else if (e.getSource() == parallelogramButton) {
			model.order = 4;
		} else if (e.getSource() == undoButton) {
			controller.controlUndo();
		} else if (e.getSource() == redoButton) {
			controller.controlRedo();
		}
	}

}