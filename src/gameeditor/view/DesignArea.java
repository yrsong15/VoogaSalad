package gameeditor.view;

import java.util.ArrayList;

import gameeditor.commanddetails.ISelectDetail;
import gameeditor.objects.GameObject;
import gameeditor.view.interfaces.IDesignArea;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author pratikshasharma, John
 *
 */

public class DesignArea implements IDesignArea {

    private Pane myPane;
    private ScrollPane myScrollPane;
    private ArrayList<GameObject> mySprites = new ArrayList<GameObject>();
    
    private boolean clickEnabled = false;
    private ISelectDetail mySelectDetail;
    
    private GameObject mySelectedSprite;
    private Rectangle myScaleRectangle = new Rectangle(20, 20);;
    private double mySelectX;
    private double mySelectY;
    
    private double xDistanceFromCorner = 0;
    private double yDistanceFromCorner = 0;
    
    private boolean scaling = false;
    private boolean moving = false;
    

    private ImageView myAvatar;

    public DesignArea() {
        myScrollPane = new ScrollPane();
        myScrollPane.setMinWidth(AREA_WIDTH);
        myScrollPane.setMinHeight(AREA_HEIGHT);
        myScrollPane.setMaxWidth(AREA_WIDTH);
        myScrollPane.setMaxHeight(AREA_HEIGHT);
        myScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        myScrollPane.setVmax(0);
        myScrollPane.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        myPane = new Pane();
//        myPane.setOnMouseClicked(e -> handleClick(e.getX(), e.getY()));
        myPane.setOnMousePressed(e -> handlePress(e.getX(), e.getY()));
        myPane.setOnMouseDragged(e -> handleDrag(e.getX(), e.getY()));
        myPane.setOnMouseReleased(e -> handleRelease(e.getX(), e.getY()));
        myScrollPane.setContent(myPane);
    }    
    
	public ScrollPane getScrollPane(){
        return myScrollPane;
    }

    public void updateAvatar(Image newAvatar){

    }
    
    public void setBackground(HBox bg){
    	ObservableList<Node> currentChildren = myPane.getChildren();
    	ArrayList<Node> children = new ArrayList<Node>();
    	for (Node child : currentChildren){
    		children.add(child);
    	}
    	myPane.getChildren().clear();
    	bg.setLayoutX(0);
    	bg.setLayoutY(0);
    	myPane.getChildren().add(bg);
    	myPane.getChildren().addAll(children);
    }

	@Override
	public void addSprite(GameObject sprite) {
		mySprites.add(sprite);
		//TODO: Remove the hardcoding of the image size proportions
		// Added image proportions for Demo
//		sprite.getImageView().setFitHeight(200);
//		sprite.getImageView().setFitWidth(300);
		myPane.getChildren().add(sprite.getImageView());
	}
	
	@Override
	public void removeSprite(GameObject sprite) {
		mySprites.remove(sprite);
		myPane.getChildren().remove(sprite.getImageView());
	}

	@Override
	public void enableClick(ISelectDetail sd) {
		mySelectDetail = sd;
		clickEnabled = true;	
	}
	
	@Override
	public void disableClick() {
		clickEnabled = true;	
	}
	
	private void handleRelease(double x, double y){
		scaling = false;
		moving = false;
		if (mySelectedSprite != null){
			myScaleRectangle.setLayoutX(mySelectedSprite.getX()+mySelectedSprite.getWidth()-20);
			myScaleRectangle.setLayoutY(mySelectedSprite.getY()+mySelectedSprite.getHeight()-20);
		}
		xDistanceFromCorner = 0;
		yDistanceFromCorner = 0;
		mySelectedSprite = null;
	}
	
	private void handlePress(double x, double y){
		if (!scaling){
			mySelectedSprite = null;
		}
		GameObject sprite = checkForSprite(x, y);
		if (clickEnabled && sprite != null){
			mySelectDetail.initLevel2(sprite);
			mySelectedSprite = sprite;
			mySelectX = x;
			mySelectY = y;
			xDistanceFromCorner = mySelectX - mySelectedSprite.getX();
			yDistanceFromCorner = mySelectY - mySelectedSprite.getY();
		}
	}
	
	private void moveSelectedSprite(double x, double y){
		double newX = x - xDistanceFromCorner;
		double newY = y - yDistanceFromCorner;
		mySelectDetail.updateSpritePosition(newX, newY);
		mySelectedSprite.setLayout(newX, newY);
		myScaleRectangle.setLayoutX(mySelectedSprite.getX()+mySelectedSprite.getWidth()-20);
		myScaleRectangle.setLayoutY(mySelectedSprite.getY()+mySelectedSprite.getHeight()-20);
	}
	
    private void handleDrag(double x, double y) {
    	if (!moving && checkForScale(x,y) && clickEnabled && mySelectedSprite != null){
    		myScaleRectangle.setLayoutX(mySelectedSprite.getX()+mySelectedSprite.getWidth()-20);
			myScaleRectangle.setLayoutY(mySelectedSprite.getY()+mySelectedSprite.getHeight()-20);
    		double spriteX = mySelectedSprite.getX();
    		double spriteY = mySelectedSprite.getY();
    		double newSpriteWidth = x - spriteX;
    		double newSpriteHeight = y - spriteY;
    		mySelectDetail.updateSpriteDimensions(newSpriteWidth, newSpriteHeight);
			mySelectedSprite.setDimensions(newSpriteWidth, newSpriteHeight);
		} 
    	else if (!scaling && clickEnabled && mySelectedSprite != null){
    		moveSelectedSprite(x, y);
		}    	
	}
    
    private GameObject checkForSprite(double x, double y){
		Rectangle test = new Rectangle(x, y, 1, 1);
		for (GameObject sprite : mySprites){
			if(sprite.getImageView().getBoundsInParent().intersects(test.getBoundsInParent()) && clickEnabled){
				myPane.getChildren().remove(myScaleRectangle);
				myScaleRectangle.setLayoutX(sprite.getX()+sprite.getWidth()-20);
				myScaleRectangle.setLayoutY(sprite.getY()+sprite.getHeight()-20);
				myScaleRectangle.setFill(Color.RED);
				myPane.getChildren().add(myScaleRectangle);
				return sprite;
			}
		}
		return null;
	}
    
	private boolean checkForScale(double x, double y){
		Rectangle test = new Rectangle(x, y, 1, 1);
		if (scaling){
			return true;
		}else if(myScaleRectangle != null && myScaleRectangle.getBoundsInParent().intersects(test.getBoundsInParent())){
			scaling = true;
			return true;
		}
		return false;
	}
}
