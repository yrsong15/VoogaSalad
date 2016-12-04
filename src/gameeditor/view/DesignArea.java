package gameeditor.view;

import java.io.File;
import java.util.ArrayList;

import gameeditor.commanddetails.ISelectDetail;
import gameeditor.objects.BoundingBox;
import gameeditor.objects.GameObject;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IGameEditorView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        myPane.addEventFilter(KeyEvent.KEY_TYPED, (e) -> handleKeyType(e.getCode()));
        myPane.setOnMousePressed(e -> handlePress(e.getX(), e.getY()));
        myScrollPane.setContent(myPane);
    }    

    //TODO: get keytyped working
    private void handleKeyType(KeyCode code) {
        System.out.println("key typed");
        if (code == KeyCode.BACK_SPACE){
            // TODO: Remove from backend
            removeSprite(mySelectedSprite);
        }
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
        
         
        bg.setLayoutX(0);
        bg.setLayoutY(0);

        myPane.getChildren().add(bg);
        myPane.getChildren().addAll(children);
        
    }

    @Override
    public void addSprite(GameObject sprite) {
        mySprites.add(sprite);
        //		TODO: Remove the hardcoding of the image size proportions

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

    private void handlePress(double x, double y){
        GameObject sprite = checkForSprite(x, y);
        if (clickEnabled && sprite != null && mySelectedSprite != null && sprite != mySelectedSprite){
            mySelectedSprite.removeBound();
            mySelectedSprite.setOff();
            sprite.initBound();
            sprite.setOn(x, y);
            mySelectedSprite = sprite;
        } else if (clickEnabled && sprite != null && mySelectedSprite == null){
            sprite.initBound();
            sprite.setOn(x, y);
            mySelectedSprite = sprite;
        }
    }

    public void initSelectDetail2(GameObject sprite){
        if (clickEnabled){
            mySelectDetail.initLevel2(sprite);
        }
    }

    public void updateSpriteDetails(GameObject sprite, double x, double y, double width, double height){
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

    private GameObject checkForSprite(double x, double y){
        Rectangle test = new Rectangle(x, y, 1, 1);
        GameObject selectedSprite = null;
        for (GameObject sprite : mySprites){
            if(sprite.getImageView().getBoundsInParent().intersects(test.getBoundsInParent()) && clickEnabled && mySelectedSprite == sprite){
                return sprite;
            } else if (sprite.getImageView().getBoundsInParent().intersects(test.getBoundsInParent()) && clickEnabled){
                selectedSprite = sprite;
            }
        }
        return selectedSprite;
    }

    @Override
    public void addDragIn(ImageView tempIV) {
        myPane.getChildren().add(tempIV);
    }

    @Override
    public void removeDragIn(ImageView tempIV) {
        myPane.getChildren().remove(tempIV);
    }
}
