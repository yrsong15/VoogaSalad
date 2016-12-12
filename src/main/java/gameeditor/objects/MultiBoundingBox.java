package gameeditor.objects;

import java.util.ArrayList;

import gameeditor.view.interfaces.IDesignArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MultiBoundingBox {
	
	private ArrayList<GameObjectView> mySprites = new ArrayList<GameObjectView>();
	private IDesignArea myDesignArea;
	
	private Rectangle myBounds;
	
	private double minX = 0;
	private double minY = 0;
	private double maxX = 0;
	private double maxY = 0;
	
	private double xDistanceFromCorner = 0;
	private double yDistanceFromCorner = 0;
	
	private double myPressX = 0;
	private double myPressY = 0;
	private double myDeltaX = 0;
	private double myDeltaY = 0;


	public MultiBoundingBox(ArrayList<GameObjectView> sprites, IDesignArea da) {
		mySprites.addAll(sprites);
		myDesignArea = da;
		setMinMax();
		double lineWidth = 2;
		createLines(minX, minY, maxX-minX, maxY-minY, lineWidth);
	}
	
	private void setMinMax(){
		if (mySprites.size() > 0){
			minX = mySprites.get(0).getX();
			minY = mySprites.get(0).getY();
		}
		for (GameObjectView sprite : mySprites){
			minX = Math.min(minX, sprite.getX());
			minY = Math.min(minY, sprite.getY());
			maxX = Math.max(maxX, sprite.getX()+sprite.getWidth());
			maxY = Math.max(maxY, sprite.getY()+sprite.getHeight());
		}
	}
	
	private void handleCenterPress(double x, double y){
		myPressX = x;
		myPressY = y;
		for (GameObjectView sprite : mySprites){
			sprite.setDistanceFromCorner(x, y);
			sprite.setMultiBoxOrigin();
			updateLayout();
		}		
	}
	
	private void handleCenterDrag(double x, double y){
		myDeltaX = x - myPressX;
		myDeltaY = y - myPressY;
		for (GameObjectView sprite : mySprites){
			sprite.handleMultiboxDrag(myDeltaX, myDeltaY);
			updateLayout();
		}
	}
	
	public void createLines(double spriteX, double spriteY, double spriteWidth, double spriteHeight, double lineWidth){
		myBounds = new Rectangle(spriteX, spriteY, spriteWidth, spriteHeight);
		myBounds.setFill(Color.LIGHTGRAY);
		myBounds.setOpacity(0.3);
		myBounds.setStroke(Color.BLACK);
		myBounds.setStrokeWidth(lineWidth);
		myBounds.setOnMousePressed((e) -> handleCenterPress(e.getX(), e.getY()));
		myBounds.setOnMouseDragged((e) -> handleCenterDrag(e.getX(), e.getY()));
	}
	
	public void updateLayout(){
		setMinMax();
		myBounds.setX(minX);
		myBounds.setY(minY);
	}
	
	public void addSprite(GameObjectView sprite){
		mySprites.add(sprite);
		updateLayout();
	}
	
	public void removeSprite(GameObjectView sprite){
		mySprites.remove(sprite);
		updateLayout();
	}
	
	public void show(){
		myDesignArea.addMultiBoundingBox(this);
	}
	
	public void hide(){
		myDesignArea.removeMultiBoundingBox();
	}
	
	public Rectangle getBound(){
		return myBounds;
	}

}
