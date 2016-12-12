package gameeditor.rpg;

import java.util.ArrayList;

import gameeditor.objects.GameObjectView;
import gameeditor.view.interfaces.IDesignArea;
import javafx.scene.layout.Pane;

public interface IGridDesignArea extends IDesignArea {
    
	public void addSprite(GameObjectView gameObject, Cell cell);
    
    public void removeSpriteFromCell(Cell cell);

	public Pane getPane();

	public Cell getHoverCell();
	
	public ArrayList<Cell> getSelectedCells();
	
}
