package gameeditor.rpg;

import java.util.ArrayList;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.rpg.commanddetails.ISelectDetail;
import gameeditor.view.ViewResources;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public interface IGridDesignArea {
	public static final String IMAGE_FILE_TYPE = ViewResources.IMAGE_FILE_TYPE.getResource();
    public static final String MUSIC_FILE_TYPE = ViewResources.MUSIC_FILE_TYPE.getResource();
    public static final String IMAGE_FILE_LOCATION = ViewResources.IMAGE_FILE_LOCATION.getResource();
    public static final  double SCENE_WIDTH = ViewResources.SCENE_WIDTH.getDoubleResource();
    public static final double SCENE_HEIGHT = ViewResources.SCENE_HEIGHT.getDoubleResource();
    public static final  double AREA_WIDTH = ViewResources.AREA_WIDTH.getDoubleResource();
    public static final double AREA_HEIGHT = ViewResources.AREA_HEIGHT.getDoubleResource();
    
    public ScrollPane getScrollPane();
    
    public void updateAvatar(Image newAvatar);
    
    public void addSprite(GameObjectView gameObject, Cell cell);
    
    public void removeSprite(GameObjectView gameObject);
    
    public void removeSpriteFromCell(Cell cell);
    
    public void setBackground(ImageView bg);
    
    public void enableClick(ISelectDetail sd);
    
    public void disableClick();
    
    public void updateSpriteDetails(GameObjectView sprite, double x, double y, double width, double height);
        
    public void initSelectDetail2(GameObjectView sprite);
    
    public void addDragIn(ImageView tempIV);

	public void removeDragIn(ImageView tempIV);
	
	public void addAvatar(GameObjectView avatar);

	public Pane getPane();

	public Cell getHoverCell();
	
	public ArrayList<Cell> getSelectedCells();
	
}
