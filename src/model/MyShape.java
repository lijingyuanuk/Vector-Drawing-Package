package model;

import java.awt.Color;
import java.awt.Shape;
import java.io.Serializable;

public class MyShape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Color color;
	public Shape r;
	boolean isline;

	public MyShape(Color color, Shape r, boolean l) {
		this.color = color;
		this.r = r;
		isline = l;

	}
    //set colors for object
	public void setColor(Color c) {
		color = c;

	}
	//set shapes for object
	public void setShape(Shape s) {
		r = s;

	}

	//decide whether the object type is line 
	public boolean isLine() {
		return this.isline;
	}
	
    //get colors of object
	public Color getColor() {
		return color;

	}

	//get shapes of object
	public Shape getShape() {
		return r;

	}

}
