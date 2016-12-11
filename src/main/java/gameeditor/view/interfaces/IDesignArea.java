package gameeditor.view.interfaces;

import gameeditor.commanddetails.ISelectDetail;
import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.objects.BoundingBox;
import gameeditor.objects.GameObjectView;
import gameeditor.objects.MultiBoundingBox;
import gameeditor.view.ViewResources;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public interface IDesignArea {
    public static final String IMAGE_FILE_TYPE = ViewResources.IMAGE_FILE_TYPE.getResource();
    public static final String MUSIC_FILE_TYPE = ViewResources.MUSIC_FILE_TYPE.getResource();
    public static final String IMAGE_FILE_LOCATION = ViewResources.IMAGE_FILE_LOCATION.getResource();
    public static final  double SCENE_WIDTH = ViewResources.SCENE_WIDTH.getDoubleResource();
    public static final double SCENE_HEIGHT = ViewResources.SCENE_HEIGHT.getDoubleResource();
    public static final  double AREA_WIDTH = ViewResources.AREA_WIDTH.getDoubleResource();
    public static final double AREA_HEIGHT = ViewResources.AREA_HEIGHT.getDoubleResource();
    
    public ScrollPane getScrollPane();
    
    public void updateAvatar(Image newAvatar);
    
    public void addSprite(GameObjectView gameObject);
    
    public void removeSprite(GameObjectView gameObject);
    
    public void setBackground(ImageView bg);
    
    public void enableClick(ISelectDetail sd);
    
    public void disableClick();
    
    public void updateSpriteDetails(GameObjectView sprite, double x, double y, double width, double height);
        
    public void initSelectDetail2(GameObjectView sprite);
    
    public void addBoundingBox(BoundingBox bb);
    
    public void removeBoundingBox(BoundingBox bb);
    
    public void addDragIn(ImageView tempIV);

	public void removeDragIn(ImageView tempIV);
	
	public void addAvatar(GameObjectView avatar);

	public void addMultiBoundingBox(MultiBoundingBox multiBoundingBox);

	public void removeMultiBoundingBox();


}
