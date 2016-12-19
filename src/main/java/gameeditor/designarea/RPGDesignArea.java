package gameeditor.designarea;

//This entire file is part of my masterpiece.
//John Martin
//This is the custom design area for an RPG game editor, as with the scrolling design area, I believe the core
//to this as good design is its use of inheritance hierarchies, reflection and lack of hardcoded values that
//make it both flexible and focused, whilst being easy to navigate and understand.

import java.util.ArrayList;

import gameeditor.objects.GameObjectView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author John Martin
 *
 */

public class RPGDesignArea extends AbstractDesignArea implements IRPGDesignArea {
    
    private CellGrid myCellGrid;
    private ArrayList<Cell> myCells = new ArrayList<Cell>();
    private Cell myClickCell;
    private ArrayList<Cell> mySelectedCells = new ArrayList<Cell>();
    private ArrayList<Cell> myNewSelectedCells = new ArrayList<Cell>();
    
    public RPGDesignArea() {
    	super();
    }   
    
    @Override
    public void init(){
        myScrollPane.setOnKeyPressed((e) -> handleKeyPress(e.getCode()));
        myScrollPane.setOnKeyReleased((e) -> handleKeyRelease(e.getCode()));
        myCellGrid = new CellGrid(0, 0, 40, (int) (1.5*AREA_WIDTH/40), (int) (1.5*AREA_HEIGHT/40), false, this);
        myCells = myCellGrid.getCells();
        for (Cell cell : myCells){
        	myPane.getChildren().add(cell.getRect());
        }
        myPane.setOnMousePressed(e -> handlePress(e.getX(), e.getY()));
        myPane.setOnMouseDragged(e -> handleDrag(e.getX(), e.getY()));
        myPane.setOnMouseReleased(e -> handleRelease(e.getX(), e.getY()));
        myScrollPane.setContent(myPane);
    }
    
    @Override
    protected void handlePress(double x, double y){
    	Cell cell = findCell(x, y);
    	if (myKeyCode != KeyCode.SHIFT){
        	resetCells();
    	}
    	if (clickEnabled){
    		myClickCell = cell;
    		startX = x;
            startY = y;
            mySelectionArea = new Rectangle(x, y, 0, 0);
            mySelectionArea.setFill(Color.AQUAMARINE);
            mySelectionArea.setOpacity(0.1);
            myPane.getChildren().add(mySelectionArea);
    	}
    }
    
    private void invertSelection(Cell cell){
    	if (cell != null && mySelectedCells.contains(cell)){
    		mySelectedCells.remove(cell);
    		myNewSelectedCells.add(cell);
    		cell.resetColor();
    	} else if (cell != null && !mySelectedCells.contains(cell)){
    		mySelectedCells.add(cell);
    		myNewSelectedCells.add(cell);
    		cell.setColor();
    	}
    }
    
    @Override
    protected void handleRelease(double x, double y) {
    	Cell myReleaseCell = findCell(x, y);
        if (dragged){
        	selectCells(startX, startY, endX, endY);
            myPane.getChildren().remove(mySelectionArea);
            mySelectionArea = null;
        }
        if (myClickCell == myReleaseCell && !myNewSelectedCells.contains(myClickCell)){
        	invertSelection(myClickCell);
        }
        dragged = false;
        startX = -1;
        startY = -1;
        endX = 0;
        endY = 0;
        myClickCell = null;
    }
    
    @Override
    protected void handleDrag(double x, double y) {
    	if (startX != -1 && startY != -1){
    		startX = Math.min(startX, x);
            startY = Math.min(startY, y);
            endX = Math.max(endX, x);
	        endY = Math.max(endY, y);
            mySelectionArea.setX(startX);
            mySelectionArea.setY(startY);
            mySelectionArea.setWidth(endX-startX);
            mySelectionArea.setHeight(endY-startY);
            initDragged();
        }
    }
    
    private void initDragged(){
    	if (!dragged){
            dragged = true;
        }
    }

    @Override
    protected void handleKeyPress(KeyCode code){
        myKeyCode = code;
    }
    
    @Override
    protected void handleKeyRelease(KeyCode code){
        if (code == KeyCode.BACK_SPACE && mySelectedCells.size() > 0){
            for (Cell c : mySelectedCells){
            	removeSpriteFromCell(c);
            }
        }
        myKeyCode = null;
    }
    
	private void resetCells(){
    	for (Cell cell : mySelectedCells){
    		cell.resetColor();
    	}
    	mySelectedCells.clear();
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
    
    private void removeSpriteFromCell(Cell cell) {
    	cell.removeSprite();
    }
    
    public void initSelectDetail2(GameObjectView sprite){
        if (clickEnabled){
            mySelectDetail.switchSelectStyle(sprite);
            mySelectDetail.initLevel2(sprite);
        }
    }

    private void selectCells(double minX, double minY, double maxX, double maxY){
        Rectangle test = new Rectangle(minX, minY, maxX-minX, maxY-minY);
        for (Cell cell : myCells){
            if(test.getBoundsInParent().intersects(cell.getRect().getBoundsInParent())){
                invertSelection(cell);
            }
        }
    }
    
    public Pane getPane(){
    	return myPane;
    }

	public ArrayList<Cell> getSelectedCells() {
		return mySelectedCells;
	}

	@Override
	public void addSprite(GameObjectView gameObject) {
		if (mySelectedCells != null){
			for (int i = 0; i < mySelectedCells.size(); i++){
				Cell cell = mySelectedCells.get(i);
				if (i == 0){
					addSprite(gameObject, cell);
				} else {
					addSprite(new GameObjectView(gameObject, 0, 0), cell);
				}
			}
		}
	}
	
    private void addSprite(GameObjectView sprite, Cell cell) {
    	cell.addSprite(sprite);
    }
    

    @Override
    public void addAvatar(GameObjectView gov) {
        myAvatars.add(gov);
        mySprites.add(gov);
        ArrayList<Cell> myCells = myCellGrid.getCells();
        int index = (myCells.size()-1)/2;
        if (myCells.size() > 0){
            addSprite(gov, myCells.get(index));
        }
    }

}
