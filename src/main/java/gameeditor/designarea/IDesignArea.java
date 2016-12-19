package gameeditor.designarea;

//This entire file is part of my masterpiece.
//John Martin
//This interfaces is the interface used to enable other components of the gameeditor to interact with the design areas.
//All methods in this interface are universal to all design areas, I believe this is good design as it enables the 
//design areas to keep their individual, unique natures but still function with other areas of the code. This
//is achieved by an interface inheritance hierarchy, whereby more specific contracts are made for the individual design
//areas to interact with components unique to them.

import gameeditor.commanddetails.ISelectDetail;
import gameeditor.objects.GameObjectView;
import gameeditor.view.ViewResources;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
/**
 * 
 * @author John Martin
 *
 */
public interface IDesignArea {
    public static final String IMAGE_FILE_TYPE = ViewResources.IMAGE_FILE_TYPE.getResource();
    public static final String MUSIC_FILE_TYPE = ViewResources.MUSIC_FILE_TYPE.getResource();
    public static final String IMAGE_FILE_LOCATION = ViewResources.IMAGE_FILE_LOCATION.getResource();
    public static final  double SCENE_WIDTH = ViewResources.SCENE_WIDTH.getDoubleResource();
    public static final double SCENE_HEIGHT = ViewResources.SCENE_HEIGHT.getDoubleResource();
    public static final  double AREA_WIDTH = ViewResources.AREA_WIDTH.getDoubleResource();
    public static final double AREA_HEIGHT = ViewResources.AREA_HEIGHT.getDoubleResource();
        
    public ScrollPane getScrollPane();
        
    public void setBackground(ImageView bg);
    
    public void enableClick(ISelectDetail sd);
    
    public void disableClick();
    
    public void updateSpriteDetails(GameObjectView sprite, double x, double y, double width, double height);
        
    public void initSelectDetail2(GameObjectView sprite);
    
    public void addDragIn(ImageView tempIV);

	public void removeDragIn(ImageView tempIV);
	
	public void addAvatar(GameObjectView avatar);

	public void addSprite(GameObjectView gameObject);
    
    public void removeSprite(GameObjectView gameObject);
    
	
}
