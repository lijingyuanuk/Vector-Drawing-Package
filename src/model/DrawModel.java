package model;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class DrawModel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//create linkedlists to store current shapes and deleted shapes
	LinkedList<MyShape> myshapes = new LinkedList<MyShape>();
	LinkedList<MyShape> deleted_shapes = new LinkedList<MyShape>();

	public int order;
	public Color color;
	private BufferedImage image;
	private ObjectInputStream oos;
	private ObjectOutputStream oos2;
	Point startDrag, endDrag;
	
	public DrawModel() {
		//initialize color as black
		color=Color.BLACK;
		//add mouse listener when drawing
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startDrag = new Point(e.getX(), e.getY());
				endDrag = startDrag;
				repaint();
			}
        //set actions when mouse is released
		public void mouseReleased(MouseEvent e) {
			if (order == 1) {
				Shape r = makeLine(startDrag.x, startDrag.y, e.getX(), e.getY());
				MyShape myshape0 = new MyShape(color, r, true);
				myshapes.add(myshape0);

			}
			if (order == 2) {
				Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
				MyShape myshape0 = new MyShape(color, r, false);
				myshapes.add(myshape0);
			}
			if (order == 3) {
				Shape r = makeEllipse(startDrag.x, startDrag.y, e.getX(), e.getY());
				MyShape myshape0 = new MyShape(color, r, false);
				myshapes.add(myshape0);
			}
			if (order == 4) {
				Shape r = parallelogram(startDrag.x, startDrag.y, e.getX(), e.getY());
				MyShape myshape0 = new MyShape(color, r, false);
				myshapes.add(myshape0);
			}
			startDrag = null;
			endDrag = null;
			repaint();
		  }
	    });

		//add mouse motion listener when drawing
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				endDrag = new Point(e.getX(), e.getY());
				repaint();
			}
		});
	}

	//draw 
	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		paintBackground(g2);
		g2.drawImage(image, null, null);
		g2.setStroke(new BasicStroke(2));
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

		//traverse linkedlist to draw shapes already exist 
		for (MyShape s : myshapes) {
			if(s.isLine()){
				g2.setPaint(s.getColor());
				g2.draw(s.getShape());
			    }
			else{
				g2.setPaint(Color.BLACK);
				g2.draw(s.getShape());
				g2.setPaint(s.getColor());
				g2.fill(s.getShape());	
			    }		
		    }	
        //draw shapes when mouse drag
		if (startDrag != null && endDrag != null) {
			if (order == 1) {
				Shape r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
				g2.draw(r);
			}
			if (order == 2) {
				Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
				g2.draw(r);
			}
			if (order == 3) {
				Shape r = makeEllipse(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
				g2.draw(r);
			}
			if (order == 4) {
				Shape r = parallelogram(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
				g2.draw(r);
			}
		
		}
	}

	//make line
	private Line2D.Float makeLine(int x1, int y1, int x2, int y2) {
		return new Line2D.Float(x1, y1, x2, y2);
	}

	//make rectangle
	private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
		return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	//make ellipse
	private Ellipse2D.Float makeEllipse(int x1, int y1, int x2, int y2) {
		return new Ellipse2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}
	
	//make parallelogram
	private Path2D.Double parallelogram (int x1, int y1, int x2, int y2){
		int length = Math.abs (x2-x1)*3/2;
		int xx1 = Math.min(x1, x2) + length;
		int xx2 = Math.max(x1, x2) - length;
		Path2D.Double parallelogram = new Path2D.Double();
		parallelogram.moveTo(Math.min(x1, x2), Math.min(y1, y2));
		parallelogram.lineTo(xx1, Math.min(y1, y2));
		parallelogram.lineTo(Math.max(x1, x2), Math.max(y1, y2));
		parallelogram.lineTo(xx2, Math.max(y1, y2));
		parallelogram.closePath();
		return parallelogram;		
	}

	//make the background with grid 
	public void paintBackground(Graphics2D g2) {
		g2.setPaint(Color.LIGHT_GRAY);
		for (int i = 0; i < getSize().width; i += 10) {
			Shape line = new Line2D.Float(i, 0, i, getSize().height);
			g2.draw(line);
		}

		for (int i = 0; i < getSize().height; i += 10) {
			Shape line = new Line2D.Float(0, i, getSize().width, i);
			g2.draw(line);
		}

	}

	//remove the last shape
	public void undo() {
		if (!myshapes.isEmpty()) {
			MyShape r = myshapes.getLast();
			myshapes.removeLast();
			deleted_shapes.add(r);
			repaint();
		}
	}

	//add the last deleted shape
	public void redo() {
		if (!deleted_shapes.isEmpty()) {
			MyShape r = deleted_shapes.getLast();
			deleted_shapes.removeLast();
			myshapes.add(r);
			repaint();
		}
	}
	
	//save shapes as serialized file
	public void saveFile() throws IOException{		
		JFileChooser fileChooser = new JFileChooser();
        if ( fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {       	
          File selectedFile = fileChooser.getSelectedFile();
          System.out.println(selectedFile.getName()); 
          FileOutputStream fout = new FileOutputStream(selectedFile.getPath());
          oos2 = new ObjectOutputStream(fout);
          oos2.writeObject(myshapes);        
        }				
	}
	
	//open a serialized file and display shapes
	public void openFile() throws IOException, ClassNotFoundException{
		JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          FileInputStream fout = new FileInputStream(selectedFile.getPath());
          oos = new ObjectInputStream(fout);
          LinkedList<MyShape> readObject = (LinkedList<MyShape>) oos.readObject();
		  myshapes = readObject;
          repaint();
        }		
	}
		
	//open an image file and display the image
	public void openImage() throws IOException, ClassNotFoundException{		
		JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();                
          try {                
              image = ImageIO.read(new File(selectedFile.getPath()));
           } catch (IOException ex) {
           }         
          repaint();                   
        }				
	}
	
	
}
