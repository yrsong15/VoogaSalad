package gameeditor.rpg;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

//This entire file is part of my masterpiece.
//John Martin

/**
 * Cell class extends Polygon to be drawn to screen. Contains information on
 * its type and state, in a general way that can easily be extended to many
 * different situations. The Rule class for a given name is responsible for
 * properly interpreting and setting the Cell's type and state.
 */
public class Cell {
	
	private static final Color DEFAULT_COLOR = Color.DARKSEAGREEN;
	private int myRow, myCol, myArrayPos;
	private double myX, myY;
	private Rectangle myRectangle;
	private GameObjectView mySprite;
	private double mySize;
	private IGridDesignArea myDesignArea;
	

	public Cell(double size, int row, int col, double x, double y, int arrayPos, IGridDesignArea gda) {
		mySize = size;
		myX = x;
		myY = y;
		myArrayPos = arrayPos;
		myDesignArea = gda;
		myRectangle = new Rectangle(myX, myY, mySize, mySize);
		myRectangle.setFill(Color.MEDIUMSEAGREEN);
		myRectangle.setStroke(Color.DARKGREEN);
		myRectangle.setStrokeWidth(1);
		myRow = row;
		myCol = col;
	}
	
	public int getMyCol() {
		return myCol;
	}

	public int getMyRow() {
		return myRow;
	}
	
	public void addSprite(GameObjectView sprite){
		mySprite = sprite;
	}
	
	public Rectangle getRect(){
		return myRectangle;
	}
	
	public GameObjectView getSprite(){
		return mySprite;
	}
	
	public void setColor(){
		myRectangle.setFill(Color.DARKSEAGREEN);
	}
	
	public void resetColor(){
		myRectangle.setFill(Color.MEDIUMSEAGREEN);
	}

	public void removeSprite() {
		if (mySprite != null){
			myDesignArea.getPane().getChildren().remove(mySprite.getImageView());
		}
		
	}

	public double getSize() {
		return mySize;
	}
	
}
