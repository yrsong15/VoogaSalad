package gameeditor.rpg;

import java.util.ArrayList;

import gameeditor.commanddetails.ISelectDetail;
import gameeditor.objects.GameObjectView;
import gameeditor.view.interfaces.IDesignArea;
import javafx.scene.layout.Pane;

/**
 * @author John Martin
 */
public interface IGridDesignArea extends IDesignArea {

    public void removeSpriteFromCell(Cell cell);

    public Pane getPane();

    public Cell getHoverCell();

    public ArrayList<Cell> getSelectedCells();

    public void removeSprite(GameObjectView gameObjectView);

    public void updateSpriteDetails(GameObjectView gameObjectView, double x, double y, double width, double height);

    public void enableClick(ISelectDetail sd);

    public void addAvatar(GameObjectView gov);

    public void addSprite(GameObjectView sprite, Cell cell);

}
