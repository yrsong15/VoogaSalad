package gameeditor.objects;

import java.util.ArrayList;

import gameeditor.view.interfaces.IDesignArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoundingBox {
	
	private ArrayList<Rectangle> myCorners = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> myShapes = new ArrayList<Rectangle>();
	private GameObject mySprite;
	private IDesignArea myDesignArea;
	
	private Rectangle myBounds;
	private Rectangle myTopLeft;
	private Rectangle myTopRight;
	private Rectangle myBottomLeft;
	private Rectangle myBottomRight;
	
	private double previousX = 0;
	private double previousY = 0;
	private double myBoxSize = 5;

	public BoundingBox(GameObject sprite, IDesignArea da) {
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
	
	private void handleCenterDrag(double x, double y){
		mySprite.handleDrag(x, y);
		updateLayout();
	}
	
	private void handleCornerDrag(double x, double y, double xMultiplier, double yMultiplier){
		double xDifference = x - mySprite.getX();
		double yDifference = y - mySprite.getY();
		double newWidth = mySprite.getWidth() + xMultiplier*xDifference;
		double newHeight = mySprite.getHeight() + yMultiplier*yDifference;
		mySprite.setDimensions(newWidth, newHeight);
		System.out.println("new");
		System.out.println(newWidth);
		System.out.println(newHeight);
		updateDimensions();		
	}
	
	public void createLines(double spriteX, double spriteY, double spriteWidth, double spriteHeight, double lineWidth){
		myBounds = new Rectangle(spriteX, spriteY, spriteWidth, spriteHeight);
		myBounds.setFill(Color.TRANSPARENT);
		myBounds.setStroke(Color.LIGHTGRAY);
		myBounds.setOnMouseDragged((e) -> handleCenterDrag(e.getX(), e.getY()));
		myShapes.add(myBounds);
	}
	
	public void createCorners(double spriteX, double spriteY, double spriteWidth, double spriteHeight){
		myTopLeft = createCorner(spriteX-myBoxSize/2, spriteY-myBoxSize/2, myBoxSize, -1, -1);
		myTopRight = createCorner(spriteX+spriteWidth-myBoxSize/2, spriteY-myBoxSize/2, myBoxSize, 1, -1);
		myBottomLeft = createCorner(spriteX-myBoxSize/2, spriteY+spriteHeight-myBoxSize/2, myBoxSize, -1, 1);
		myBottomRight = createCorner(spriteX+spriteWidth-myBoxSize/2, spriteY+spriteHeight-myBoxSize/2, myBoxSize, 1, 1);
		myShapes.addAll(myCorners);
	}
	
	public Rectangle createCorner(double x, double y, double myBoxSize, double xMultiplier, double yMultiplier){
		Rectangle r = new Rectangle(x, y, myBoxSize, myBoxSize);
		r.setOnMouseDragged((e) -> handleCornerDrag(e.getX(), e.getY(), xMultiplier, yMultiplier));
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
		double spriteX = mySprite.getX();
		double spriteY = mySprite.getY();
		double spriteWidth = mySprite.getWidth();
		double spriteHeight = mySprite.getHeight();
		System.out.println("Sprite width" + spriteWidth);
		System.out.println("Sprite height" + spriteHeight);
		myBounds.setWidth(spriteWidth);
		myBounds.setHeight(spriteHeight);
		updateCornerLocation(myTopLeft, spriteX-myBoxSize/2, spriteY-myBoxSize/2);
		updateCornerLocation(myTopRight, spriteX+spriteWidth-myBoxSize/2, spriteY-myBoxSize/2);
		updateCornerLocation(myBottomLeft, spriteX-myBoxSize/2, spriteY+spriteHeight-myBoxSize/2);
		updateCornerLocation(myBottomRight, spriteX+spriteWidth-myBoxSize/2, spriteY+spriteHeight-myBoxSize/2);
	}
	
	public void updateCornerLocation(Rectangle corner, double x, double y){
		corner.setLayoutX(x);
		corner.setLayoutY(y);
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
