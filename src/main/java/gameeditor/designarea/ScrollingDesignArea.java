package gameeditor.designarea;

//This entire file is part of my masterpiece.
//JOHN MARTIN
//This is the standard design area for scrolling platforms. For my masterpiece I refactored the design area to implement
//reflection. This combined with the inheritance hierarchy structure I've implemented enables the design area to operate
//in a unique way according to the game type but still operate regularly within the larger project. It is the ideal compromise
//between power and flexibility. In addition, it is does not utilize hard coding but instead uses resources files and properties
//resource bundles. All in all I believe this demonstrates my understanding of how good design is both flexible and extensible,
//with adding a new very specific feature to this solely for a scrolling platformer being simple, and the code being easy to navigate.

import java.util.ArrayList;
import gameeditor.objects.BoundingBox;
import gameeditor.objects.GameObjectView;
import gameeditor.objects.MultiBoundingBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author John Martin
 *
 */

public class ScrollingDesignArea extends AbstractDesignArea implements IScrollingDesignArea {

    private GameObjectView myDuplicateSprite;
    private MultiBoundingBox myMultiBoundingBox;

    public ScrollingDesignArea() {
        super();
    }
    
    @Override
    public void init(){
//      myScrollPane.setPannable(true);
      myScrollPane.setOnKeyPressed((e) -> handleKeyPress(e.getCode()));
      myScrollPane.setOnKeyReleased((e) -> handleKeyRelease(e.getCode()));
      myPane.setOnMousePressed(e -> handlePress(e.getX(), e.getY()));
      myPane.setOnMouseDragged(e -> handleDrag(e.getX(), e.getY()));
      myPane.setOnMouseReleased(e -> handleRelease(e.getX(), e.getY()));
      myPane.widthProperty().addListener(e -> handleWidthUpdate());
      myPane.heightProperty().addListener(e -> handleHeightUpdate());
      myScrollPane.setContent(myPane);
    }
    
    private void handleWidthUpdate(){
    	myScrollPane.setHvalue(1.0);
    }
    
    private void handleHeightUpdate(){
    	myScrollPane.setVvalue(1.0);
    }

    protected void handlePress(double x, double y){
        GameObjectView sprite = checkForSprite(x, y);
        resetPress(x, y);
        if (checkForMultibox(x, y)){
        } else if (myKeyCode == KeyCode.ALT && sprite != null){
            sprite.initBound();
            sprite.setOn(x, y);
            mySelectedSprite = sprite;
            myDuplicateSprite = new GameObjectView(sprite, x, y);
            this.addSprite(myDuplicateSprite);
        } else if (clickEnabled && sprite != null){
            sprite.initBound();
            sprite.setOn(x, y);
            mySelectedSprite = sprite;
        } else if (clickEnabled){
            startX = x;
            startY = y;
            mySelectionArea = new Rectangle(x, y, 0, 0);
            mySelectionArea.setFill(Color.AQUAMARINE);
            mySelectionArea.setOpacity(0.1);
            myPane.getChildren().add(mySelectionArea);
        }
    }

    private void resetPress(double x, double y){
        if (mySelectedSprite != null){
            mySelectedSprite.removeBound();
            mySelectedSprite.setOff();
            mySelectedSprite = null;
        }
        if (!checkForMultibox(x, y) && myMultiBoundingBox != null){
            myMultiBoundingBox.hide();
            myMultiBoundingBox = null;
        }
    }

    protected void handleRelease(double x, double y) {
        if (dragged){
            ArrayList<GameObjectView> selectedSprites = findSprites(startX, startY, endX, endY);
            if (selectedSprites.size() > 1){
            	myMultiBoundingBox = new MultiBoundingBox(selectedSprites, this);
                myMultiBoundingBox.show();
            } else if (selectedSprites.size() == 1) {
            	mySelectedSprite = selectedSprites.get(0);
            	mySelectedSprite.initBound();
            	mySelectedSprite.setOn(x, y);
            }
            myPane.getChildren().remove(mySelectionArea);
            mySelectionArea = null;
        }
        dragged = false;
        startX = -1;
        startY = -1;
        endX = 0;
        endY = 0;
    }

    protected void handleDrag(double x, double y) {
        if (myMultiBoundingBox == null && startX != -1 && startY != -1){
            startX = Math.min(startX, x);
            startY = Math.min(startY, y);
            endX = Math.max(endX, x);
	        endY = Math.max(endY, y);
            mySelectionArea.setX(startX);
            mySelectionArea.setY(startY);
            mySelectionArea.setWidth(endX-startX);
            mySelectionArea.setHeight(endY-startY);
            if (!dragged){
                dragged = true;
            }
        }
    }

    protected void handleKeyPress(KeyCode code){
        myKeyCode = code;
    }

    protected void handleKeyRelease(KeyCode code){
        if (code == KeyCode.BACK_SPACE && mySelectedSprite != null && myMultiBoundingBox == null ){
            mySelectedSprite.removeBound();
            mySelectedSprite.setOff();
            mySelectedSprite.removeSelf();
        } else if (code == KeyCode.BACK_SPACE && myMultiBoundingBox != null){
        	for (GameObjectView sprite : myMultiBoundingBox.getSprites()){
        		sprite.removeSelf();
        	}
        	myMultiBoundingBox.hide();
            myMultiBoundingBox = null;
        }
        myKeyCode = null;
    }

    public ScrollPane getScrollPane(){
        return myScrollPane;
    }

    public void initSelectDetail2(GameObjectView sprite){
        if (clickEnabled){
            mySelectDetail.switchSelectStyle(sprite);
            mySelectDetail.initLevel2(sprite);
        }
    }

    public void addBoundingBox(BoundingBox bb){
        for(Rectangle rect : bb.getShapes()){
            myPane.getChildren().add(rect);
        }
    }

    public void removeBoundingBox(BoundingBox bb){
        for(Rectangle rect : bb.getShapes()){
            myPane.getChildren().remove(rect);
        }
    }

    private GameObjectView checkForSprite(double x, double y){
        Rectangle test = new Rectangle(x, y, 1, 1);
        GameObjectView selectedSprite = null;
        for (GameObjectView sprite : mySprites){
            if(sprite.getImageView().getBoundsInParent().intersects(test.getBoundsInParent())
                    && clickEnabled && mySelectedSprite == sprite){
                return sprite;
            } else if (sprite.getImageView().getBoundsInParent().intersects(test.getBoundsInParent()) && clickEnabled){
                selectedSprite = sprite;
            }
        }
        return selectedSprite;
    }

    private ArrayList<GameObjectView> findSprites(double minX, double minY, double maxX, double maxY){
        Rectangle test = new Rectangle(minX, minY, maxX-minX, maxY-minY);
        ArrayList<GameObjectView> selectedSprites = new ArrayList<GameObjectView>();
        for (GameObjectView sprite : mySprites){
            if(test.getBoundsInParent().intersects(sprite.getImageView().getBoundsInParent())){
                selectedSprites.add(sprite);
            }
        }
        return selectedSprites;
    }

    @Override
    public void addSprite(GameObjectView sprite) {
        mySprites.add(sprite);
        myPane.getChildren().add(sprite.getImageView());
        if (sprite.getIsRandomGen()){
        	addRandomGen(sprite);
        }
    }
    
    private void addRandomGen(GameObjectView sprite){
    	for (ImageView iv : sprite.getRandomPreviews()){
    		myPane.getChildren().add(iv);
    	}
    }
    
    private void removeRandomGen(GameObjectView sprite){
    	for (ImageView iv : sprite.getRandomPreviews()){
    		myPane.getChildren().remove(iv);
    	}
    }

    @Override
    public void removeSprite(GameObjectView sprite) {
        mySprites.remove(sprite);
        myPane.getChildren().remove(sprite.getImageView());
        if (sprite.getIsRandomGen()){
        	removeRandomGen(sprite);
        }
    }

    @Override
    public void addAvatar(GameObjectView gov) {
        myAvatars.add(gov);
        mySprites.add(gov);
        myPane.getChildren().add(gov.getImageView());
    }

    @Override
    public void addDragIn(ImageView tempIV) {
        myPane.getChildren().add(tempIV);
    }

    @Override
    public void removeDragIn(ImageView tempIV) {
        myPane.getChildren().remove(tempIV);
    }

    @Override
    public void addMultiBoundingBox(MultiBoundingBox mbb) {
        myMultiBoundingBox = mbb;
        myPane.getChildren().add(myMultiBoundingBox.getBound());
    }

    @Override
    public void removeMultiBoundingBox() {
        myPane.getChildren().remove(myMultiBoundingBox.getBound());
    }
    
    private boolean checkForMultibox(double x, double y){
    	if (myMultiBoundingBox == null){
    		return false;
    	} else {
    		Rectangle test = new Rectangle(x, y, 1, 1);
        	boolean check = test.getBoundsInParent().intersects(myMultiBoundingBox.getBound().getBoundsInParent());
        	return check;
    	}
    }

}
