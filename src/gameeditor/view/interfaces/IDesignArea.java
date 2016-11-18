package gameeditor.view.interfaces;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

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
    
}
