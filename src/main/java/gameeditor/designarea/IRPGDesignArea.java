package gameeditor.designarea;

//This entire file is part of my masterpiece.
//John Martin
//This interface enables components only relating to the rpg design area to access information relevant only to them 
//this along with the other parts of the RPGDesignArea's inheritance structure enables increased extensibility.

import java.util.ArrayList;

import javafx.scene.layout.Pane;
/**
 * @author John Martin
 *
 */
public interface IRPGDesignArea {
    
	public Pane getPane();
	
	public ArrayList<Cell> getSelectedCells();
	
}
