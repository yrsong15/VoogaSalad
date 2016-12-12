package gameeditor.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Group;

//This entire file is part of my masterpiece.
//John Martin

/**
 * CellGrid is responsible for holding the current Cells within the Cell Society
 * game. It stores the position of the cells and uses the specified Rule class
 * to update the Cells when specified.
 */

public class CellGrid extends Group {
    private double myCellSize;
    private boolean isToroidal;
    private int myGridWidth, myGridHeight;
    private ArrayList<Cell> cells = new ArrayList<>();
    private IGridDesignArea myDesignArea;

    public CellGrid(double xPos, double yPos, double cellSize, int gridWidth, 
    		int gridHeight, boolean toroidal, IGridDesignArea gda) {
        setLayoutX(xPos);
        setLayoutY(yPos);
        myCellSize = cellSize;
        myGridWidth = gridWidth;
        myGridHeight = gridHeight;
        isToroidal = toroidal;
        myDesignArea = gda;
        createGrid();
    }
    
    public void createGrid(){
    	for (int row = 0; row < myGridHeight; row++) {
            for (int col = 0; col < myGridWidth; col++) {
                int arrayPos = row * myGridWidth + col;
                double cellXPos = col * myCellSize;
                double cellYPos = row * myCellSize;
                Cell cell = new Cell(myCellSize, row, col, cellXPos, cellYPos, arrayPos, myDesignArea);
                cells.add(cell);
                getChildren().add(cell.getRect());
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (isToroidal && ((col >= myGridWidth || (col < 0)) || (row >= myGridHeight) || (row < 0))) {
                while (col < 0) {
                    col += myGridWidth;
                }
                while (row < 0) {
                    row += myGridHeight;
                }
                col %= myGridWidth;
                row %= myGridHeight;
        } else if (((col >= myGridWidth || (col < 0)) || (row >= myGridHeight) || (row < 0))) {
                return null;
        }
		int arrayPos = row * myGridWidth + col;
	    return cells.get(arrayPos);
    }


    public ArrayList<Cell> getCells() {
        return cells;
    }
    
    public double getCellSize(){
    	return myCellSize;
    }
    
    public int getGridWidth() {
        return myGridWidth;
    }

    public int getGridHeight() {
        return myGridHeight;
    }

    
}
