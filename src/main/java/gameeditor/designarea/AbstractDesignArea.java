package gameeditor.designarea;

//This entire file is part of my masterpiece.
//John Martin
//The abstract design area is key to the reflection pattern and inheritance hierarchies that enable the
//design area to extensible and flexible for different game types. I believe this is good design as it utilizes
//a variety of design patterns and implements inheritance structures that enable flexible design areas to be
//built for a variety of different game editors. By utilizing this abstract class I was able to implement an entirely
//new design area style (the RPG design area) in just a few hours, which really illustrated to me the power of extensible code.
//In my refactoring I altered this to house only the core code, with individual game type design areas handling the specifics,
//then switched the implementation to operate based on reflection to offer better extensibility for a future new game type to
//be added to the editor.

import java.util.ArrayList;

import gameeditor.commanddetails.ISelectDetail;
import gameeditor.objects.GameObjectView;
import gameeditor.view.interfaces.IGameEditorView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
    private ImageView myBG;

	protected boolean clickEnabled = false;
    protected ISelectDetail mySelectDetail;
    protected ArrayList<GameObjectView> myAvatars = new ArrayList<GameObjectView>();
    
	protected double startX = -1;
	protected double startY = -1;
	protected double endX = 0;
	protected double endY = 0;
	protected boolean dragged = false;
	protected Rectangle mySelectionArea;
	
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
    
    abstract public void init();
    
    abstract protected void handlePress(double x, double y);
    abstract protected void handleDrag(double x, double y);
    abstract protected void handleRelease(double x, double y);
    abstract protected void handleKeyPress(KeyCode code);
	abstract protected void handleKeyRelease(KeyCode code);
	
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
    
    public void setBackground(ImageView bg){
    	myBG = bg;
        ObservableList<Node> currentChildren = myPane.getChildren();
        ArrayList<Node> children = new ArrayList<Node>();
        for (Node child : currentChildren){
            if(child.getId()==null || !(child.getId().equals(IGameEditorView.BACKGROUND_IMAGE_ID))){
                children.add(child);
            }
        }
        myPane.getChildren().clear();
        myBG.setLayoutX(0);
        myBG.setLayoutY(0);
        myBG.setPreserveRatio(true);
        bgUpdate();
        myPane.widthProperty().addListener(e -> bgUpdate());
        myPane.heightProperty().addListener(e -> bgUpdate());
        myPane.getChildren().add(bg);
        myPane.getChildren().addAll(children);
    }
    
    private void bgUpdate(){
        double imgWidth = myBG.getImage().getWidth();
        double imgHeight = myBG.getImage().getWidth();
        double widthRatio = myPane.getWidth()/imgWidth;
        double heightRatio = myPane.getHeight()/imgHeight;
        double ratio = Math.max(widthRatio, heightRatio);
        double fitWidth = imgWidth*ratio;
        double fitHeight = imgHeight*ratio;
        myBG.setFitWidth(fitWidth);
        myBG.setFitHeight(fitHeight);
    	myBG.setFitHeight(myPane.getHeight());
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
}
