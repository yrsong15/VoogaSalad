package gameeditor.designarea;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import gameeditor.objects.GameObjectView;

/**
 * @author John Martin
 *
 */
public class Cell {
	
	private static final Color DEFAULT_OFF_COLOR = Color.TRANSPARENT;
	private static final Color DEFAULT_ON_COLOR = Color.DARKSEAGREEN;
	private static final Color DEFAULT_LINE_COLOR = Color.DARKGREEN;
	@SuppressWarnings("unused")
	private int myRow, myCol, myArrayPos;
	private double myX, myY;
	private Rectangle myRectangle;
	private GameObjectView mySprite;
	private double mySize;
	private IRPGDesignArea myDesignArea;
	

	public Cell(double size, int row, int col, double x, double y, int arrayPos, IRPGDesignArea gda) {
		mySize = size;
		myX = x;
		myY = y;
		myArrayPos = arrayPos;
		myDesignArea = gda;
		myRectangle = new Rectangle(myX, myY, mySize, mySize);
		myRectangle.setFill(DEFAULT_OFF_COLOR);
		myRectangle.setStroke(DEFAULT_LINE_COLOR);
		myRectangle.setOpacity(0.5);
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
		if (mySprite != null){
			removeSprite();
		}
		mySprite = sprite;
		mySprite.setLayout(myX, myY);
		mySprite.setDimensions(mySize, mySize);
		myDesignArea.getPane().getChildren().add(sprite.getImageView());
	}
	
	public Rectangle getRect(){
		return myRectangle;
	}
	
	public GameObjectView getSprite(){
		return mySprite;
	}
	
	public void setColor(){
		myRectangle.setFill(DEFAULT_ON_COLOR);
	}
	
	public void resetColor(){
		myRectangle.setFill(DEFAULT_OFF_COLOR);
	}

	public void removeSprite() {
		if (mySprite != null){
			myDesignArea.getPane().getChildren().remove(mySprite.getImageView());
			mySprite = null;
		}
		
	}

	public double getSize() {
		return mySize;
	}
	
}
