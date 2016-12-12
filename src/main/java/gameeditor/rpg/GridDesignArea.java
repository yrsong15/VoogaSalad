package gameeditor.rpg;

import java.util.ArrayList;import java.util.List;

import gameeditor.commanddetails.DetailResources;
import gameeditor.rpg.commanddetails.ISelectDetail;

import gameeditor.controller.interfaces.IGameEditorData;

import gameeditor.view.interfaces.IGameEditorView;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author pratikshasharma, John Martin
 *
 */

public class GridDesignArea implements IGridDesignArea {

    private Pane myPane;
    private ScrollPane myScrollPane;
    private ArrayList<GameObjectView> mySprites = new ArrayList<GameObjectView>();

    private boolean clickEnabled = false;
    private ISelectDetail mySelectDetail;
    private ArrayList<GameObjectView> myAvatars = new ArrayList<GameObjectView>();
    
    private CellGrid myCellGrid;
    private ArrayList<Cell> myCells = new ArrayList<Cell>();
    private Cell myHoverCell;
    private ArrayList<Cell> mySelectedCells = new ArrayList<Cell>();

    private GameObjectView mySelectedSprite;
    private KeyCode myKeyCode;

    public GridDesignArea() {
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
        myPane.setMinWidth(AREA_WIDTH);
        myPane.setMinWidth(AREA_HEIGHT);
        myCellGrid = new CellGrid(0, 0, 15, 500, 500, false, this);
        myCells = myCellGrid.getCells();
        for (Cell cell : myCells){
        	myPane.getChildren().add(cell.getRect());
        }
        myPane.setOnMouseMoved(e -> handleHover(e.getX(), e.getY()));
        myPane.setOnMousePressed(e -> handlePress(e.getX(), e.getY()));
        myPane.setOnMouseDragged(e -> handleDrag(e.getX(), e.getY()));
        myPane.setOnMouseReleased(e -> handleRelease(e.getX(), e.getY()));
        myScrollPane.setContent(myPane);
    }   
    
    private void handleHover(double x, double y){
    	Cell cellOver = findCell(x, y);
    	if (cellOver != null && myHoverCell != null && cellOver != myHoverCell){
    		myHoverCell.resetColor();
    		myHoverCell = cellOver;
    		myHoverCell.setColor();
    	} else if (cellOver != null){
    		myHoverCell = cellOver;
    		myHoverCell.setColor();
    	}
    }

    private void handlePress(double x, double y){
    	
    }

    private void handleRelease(double x, double y) {

    }

    private void handleDrag(double x, double y) {

    }

    private void handleKeyPress(KeyCode code){
        myKeyCode = code;
    }

    private void handleKeyRelease(KeyCode code){
        if (code == KeyCode.BACK_SPACE && mySelectedSprite != null){
            // TODO: Remove from backend
            mySelectedSprite.removeSelf();
        }
        myKeyCode = null;
    }
    
    private Cell findCell(double x, double y){
    	Rectangle test = new Rectangle(x, y, 1, 1);
    	for (Cell cell : myCells){
    		if (test.getBoundsInParent().intersects(cell.getRect().getBoundsInParent())){
    			return cell;
    		}
    	}
    	return null;
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
    public void addSprite(GameObjectView sprite, Cell cell) {
    	cell.addSprite(sprite);
    }

    @Override
    public void removeSprite(GameObjectView sprite) {
    	for (Cell cell : myCells){
    		if (cell.getSprite() == sprite){
    			cell.removeSprite();
    		}
    	}
        mySprites.remove(sprite);
        myPane.getChildren().remove(sprite.getImageView());
    }
    
    @Override
    public void removeSpriteFromCell(Cell cell) {
    	cell.removeSprite();
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
    }

    @Override
    public void addDragIn(ImageView tempIV) {
        myPane.getChildren().add(tempIV);
    }

    @Override
    public void removeDragIn(ImageView tempIV) {
        myPane.getChildren().remove(tempIV);
    }
    
    public Pane getPane(){
    	return myPane;
    }

	@Override
	public Cell getHoverCell() {
		return myHoverCell;
	}

	@Override
	public ArrayList<Cell> getSelectedCells() {
		return mySelectedCells;
	}

}
