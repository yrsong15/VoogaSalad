package gameeditor.view;

import java.util.ArrayList;

import gameeditor.commanddetails.ISelectDetail;
import gameeditor.objects.BoundingBox;
import gameeditor.objects.GameObjectView;
import gameeditor.objects.MultiBoundingBox;
import gameeditor.view.interfaces.IDesignArea;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
 * @author John Martin
 *
 */
abstract public class AbstractDesignArea implements IDesignArea {

	protected Pane myPane;
	protected ScrollPane myScrollPane;
	protected ArrayList<GameObjectView> mySprites = new ArrayList<GameObjectView>();
	protected GameObjectView mySelectedSprite;
	protected KeyCode myKeyCode;

	protected boolean clickEnabled = false;
    protected ISelectDetail mySelectDetail;
    protected ArrayList<GameObjectView> myAvatars = new ArrayList<GameObjectView>();
    
	protected double startX = -1;
	protected double startY = -1;
	protected double endX = 0;
	protected double endY = 0;
	protected boolean dragged = false;
	protected Rectangle mySelectionArea;
	protected MultiBoundingBox myMultiBoundingBox;
	
	public AbstractDesignArea() {
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
        myPane.setLayoutX(0);
        myPane.setLayoutY(0);
        myPane.setMinSize(AREA_WIDTH, AREA_HEIGHT);
	}
	
    public void updateSpriteDetails(GameObjectView sprite, double x, double y, double width, double height){
        if (mySelectDetail != null){
        	mySelectDetail.updateSpritePosition(x, y);
            mySelectDetail.updateSpriteDimensions(width, height);
        }
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

    @Override
    public void addBoundingBox(BoundingBox bb){
        for(Rectangle rect : bb.getShapes()){
            myPane.getChildren().add(rect);
        }
    }

    @Override
    public void removeBoundingBox(BoundingBox bb){
        for(Rectangle rect : bb.getShapes()){
            myPane.getChildren().remove(rect);
        }
    }

}
