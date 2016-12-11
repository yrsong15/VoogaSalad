package gameeditor.objects;

import java.util.ArrayList;

import gameeditor.view.interfaces.IDesignArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoundingBox {
	
	private ArrayList<Rectangle> myCorners = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> myShapes = new ArrayList<Rectangle>();
	private GameObjectView mySprite;
	private IDesignArea myDesignArea;
	
	private Rectangle myBounds;
	private Rectangle myTopLeft;
	private Rectangle myTopRight;
	private Rectangle myBottomLeft;
	private Rectangle myBottomRight;
	
	private double previousX = 0;
	private double previousY = 0;
	private double myBoxSize = 5;
	
	private double xDistanceFromCorner = 0;
	private double yDistanceFromCorner = 0;
	
	private double myPressX = 0;
	private double myPressY = 0;
	private double myDeltaX = 0;
	private double myDeltaY = 0;


	public BoundingBox(GameObjectView sprite, IDesignArea da) {
		mySprite = sprite;
		myDesignArea = da;
		previousX = sprite.getX();
		previousY = sprite.getY();
		double lineWidth = 2;
		double spriteX = sprite.getX();
		double spriteY = sprite.getY();
		double spriteWidth = sprite.getWidth();
		double spriteHeight = sprite.getHeight();
		createLines(spriteX, spriteY, spriteWidth, spriteHeight, lineWidth);
		createCorners(spriteX, spriteY, spriteWidth, spriteHeight);
	}
	
	private void handleCenterPress(double x, double y){
		myPressX = x;
		myPressY = y;
		mySprite.setDistanceFromCorner(x, y);
		mySprite.handleDrag(x, y);
		updateLayout();
	}
	
	private void handleCenterDrag(double x, double y){
		if (x >= myPressX + myDeltaX + 20 && y >= myPressY + myDeltaY + 20){
			x -= 375;
			y -= 75;
			myDeltaX = myPressX - x;
			myDeltaY = myPressY - y;
		}
		mySprite.handleDrag(x, y);
		updateLayout();
	}
	
	private void handleCornerPress(double x, double y){
		xDistanceFromCorner = x;
		yDistanceFromCorner = y;
	}
	
	private void handleCornerDrag(Rectangle corner, double x, double y, double xMultiplier, double yMultiplier){
		double xDifference = x - xDistanceFromCorner;
		double yDifference = y - yDistanceFromCorner;
		double newWidth = mySprite.getWidth() + xMultiplier*xDifference;
		double newHeight = mySprite.getHeight() + yMultiplier*yDifference;
		mySprite.setDimensions(newWidth, newHeight);
		updateDimensions();	
		xDistanceFromCorner = x;
		yDistanceFromCorner = y;
	}
	
	public void createLines(double spriteX, double spriteY, double spriteWidth, double spriteHeight, double lineWidth){
		myBounds = new Rectangle(spriteX, spriteY, spriteWidth, spriteHeight);
		myBounds.setFill(Color.TRANSPARENT);
		myBounds.setStroke(Color.LIGHTGRAY);
		myBounds.setOnMouseDragged((e) -> handleCenterDrag(e.getX(), e.getY()));
		myBounds.setOnMousePressed((e) -> handleCenterPress(e.getX(), e.getY()));
		myShapes.add(myBounds);
	}
	
	public void createCorners(double spriteX, double spriteY, double spriteWidth, double spriteHeight){
		myTopLeft = createCorner(spriteX-myBoxSize/2, spriteY-myBoxSize/2, -1, -1);
		myTopRight = createCorner(spriteX+spriteWidth-myBoxSize/2, spriteY-myBoxSize/2, 1, -1);
		myBottomLeft = createCorner(spriteX-myBoxSize/2, spriteY+spriteHeight-myBoxSize/2, -1, 1);
		myBottomRight = createCorner(spriteX+spriteWidth-myBoxSize/2, spriteY+spriteHeight-myBoxSize/2, 1, 1);
		myShapes.addAll(myCorners);
	}
	
	public Rectangle createCorner(double x, double y, double xMultiplier, double yMultiplier){
		Rectangle r = new Rectangle(x, y, myBoxSize, myBoxSize);
		r.setOnMouseDragged((e) -> handleCornerDrag(r, e.getX(), e.getY(), xMultiplier, yMultiplier));
		r.setOnMousePressed((e) -> handleCornerPress(e.getX(), e.getY()));
		r.setFill(Color.LIGHTGRAY);
		myCorners.add(r);
		return r;
	}
	
	public void updateLayout(){
		double xDifference = mySprite.getX() - previousX;
		double yDifference = mySprite.getY() - previousY;
		for (Rectangle shape : myShapes){
			double currentX = shape.getLayoutX();
			double currentY = shape.getLayoutY();
			shape.setLayoutX(currentX+xDifference);
			shape.setLayoutY(currentY+yDifference);
		}
		previousX = mySprite.getX();
		previousY = mySprite.getY();
	}
	
	public void updateDimensions(){
	    
	    System.out.println(" Update Dimensions" );
	    
		double spriteWidth = mySprite.getWidth();
		double spriteHeight = mySprite.getHeight();
		myBounds.setWidth(spriteWidth);
		myBounds.setHeight(spriteHeight);
		double boundsX = myBounds.getX();
		double boundsY = myBounds.getY();
		updateCornerLocation(myTopLeft, boundsX-myBoxSize/2, boundsY-myBoxSize/2);
		updateCornerLocation(myTopRight, boundsX+spriteWidth-myBoxSize/2, boundsY-myBoxSize/2);
		updateCornerLocation(myBottomLeft, boundsX-myBoxSize/2, boundsY+spriteHeight-myBoxSize/2);
		updateCornerLocation(myBottomRight, boundsX+spriteWidth-myBoxSize/2, boundsY+spriteHeight-myBoxSize/2);
	}
	
	public void updateCornerLocation(Rectangle corner, double x, double y){
		corner.setX(x);
		corner.setY(y);
	}
	
	public void show(){
		myDesignArea.addBoundingBox(this);
	}
	
	public void hide(){
		myDesignArea.removeBoundingBox(this);
	}
	
	public ArrayList<Rectangle> getShapes(){
		return myShapes;
	}

}
