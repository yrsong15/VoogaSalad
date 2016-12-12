package gameeditor.view;
import java.util.ArrayList;
import gameeditor.commanddetails.ISelectDetail;
import gameeditor.objects.BoundingBox;
import gameeditor.objects.GameObjectView;
import gameeditor.objects.MultiBoundingBox;
import gameeditor.view.interfaces.IGameEditorView;
import gameeditor.view.interfaces.IStandardDesignArea;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author pratikshasharma, John Martin
 *
 */

public class DesignArea implements IStandardDesignArea {

    private Pane myPane;
    private ScrollPane myScrollPane;
    private ArrayList<GameObjectView> mySprites = new ArrayList<GameObjectView>();

    private boolean clickEnabled = false;
    private ISelectDetail mySelectDetail;
    private ArrayList<GameObjectView> myAvatars = new ArrayList<GameObjectView>();

    private GameObjectView myDuplicateSprite;

    private GameObjectView mySelectedSprite;
    private double startX = -1;
    private double startY = -1;
    private double endX = 0;
    private double endY = 0;
    private boolean dragged = false;
    private KeyCode myKeyCode;
    private Rectangle mySelectionArea;
    private MultiBoundingBox myMultiBoundingBox;

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
        myScrollPane.setOnKeyPressed((e) -> handleKeyPress(e.getCode()));
        myScrollPane.setOnKeyReleased((e) -> handleKeyRelease(e.getCode()));
        myPane = new Pane();
        myPane.setLayoutX(0);
        myPane.setLayoutY(0);
        myPane.setMinSize(AREA_WIDTH, AREA_HEIGHT);
        myPane.setOnMousePressed(e -> handlePress(e.getX(), e.getY()));
        myPane.setOnMouseDragged(e -> handleDrag(e.getX(), e.getY()));
        myPane.setOnMouseReleased(e -> handleRelease(e.getX(), e.getY()));
        myScrollPane.setContent(myPane);
    }   

    private void handlePress(double x, double y){
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

    private void handleRelease(double x, double y) {
        // TODO Auto-generated method stub
        if (dragged){
            ArrayList<GameObjectView> selectedSprites = findSprites(startX, startY, endX, endY);
            myMultiBoundingBox = new MultiBoundingBox(selectedSprites, this);
            myMultiBoundingBox.show();
            myPane.getChildren().remove(mySelectionArea);
            mySelectionArea = null;
        }
        dragged = false;
        startX = -1;
        startY = -1;
        endX = 0;
        endY = 0;
    }

    private void handleDrag(double x, double y) {
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

    private void handleKeyPress(KeyCode code){
        myKeyCode = code;
    }

    private void handleKeyRelease(KeyCode code){
        if (code == KeyCode.BACK_SPACE && mySelectedSprite != null){
            // TODO: Remove from backend
            mySelectedSprite.removeBound();
            mySelectedSprite.setOff();
            mySelectedSprite.removeSelf();
        }
        myKeyCode = null;
    }

    public ScrollPane getScrollPane(){
        return myScrollPane;
    }

    public void updateAvatar(Image newAvatar){

    }

    public void setBackground(ImageView bg){
        ObservableList<Node> currentChildren = myPane.getChildren();
        ArrayList<Node> children = new ArrayList<Node>();
        for (Node child : currentChildren){
            if(child.getId()==null || !(child.getId().equals(IGameEditorView.BACKGROUND_IMAGE_ID))){
                children.add(child);
            }
        }
        myPane.getChildren().clear();
        bg.setLayoutX(0);
        bg.setLayoutY(0);

        myPane.getChildren().add(bg);
        myPane.getChildren().addAll(children);
    }

    @Override
    public void addSprite(GameObjectView sprite) {
        mySprites.add(sprite);
        //		TODO: Remove the hardcoding of the image size proportions
        myPane.getChildren().add(sprite.getImageView());
    }

    @Override
    public void removeSprite(GameObjectView sprite) {
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

    public void initSelectDetail2(GameObjectView sprite){
        if (clickEnabled){
            mySelectDetail.switchSelectStyle(sprite);
            mySelectDetail.initLevel2(sprite);
        }
    }

    public void updateSpriteDetails(GameObjectView sprite, double x, double y, double width, double height){
        mySelectDetail.updateSpritePosition(x, y);
        mySelectDetail.updateSpriteDimensions(width, height);

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
