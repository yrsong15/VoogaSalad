package gameeditor.view.interfaces;

import gameeditor.view.ViewResources;
import javafx.scene.Parent;
/**
 * 
 * @author John Martin
 *
 */
public interface IGameEditorView {
    public static final String IMAGE_FILE_TYPE = ViewResources.IMAGE_FILE_TYPE.getResource();
    public static final String MUSIC_FILE_TYPE = ViewResources.MUSIC_FILE_TYPE.getResource();
    public static final String BG_IMAGE_LOCATION = ViewResources.BG_FILE_LOCATION.getResource();
    public static final String AVATAR_IMAGE_LOCATION = ViewResources.AVATAR_IMAGE_LOCATION.getResource();
    public static final String MUSIC_FILE_LOCATION = ViewResources.MUSIC_FILE_LOCATION.getResource();
    public static final String BACKGROUND_IMAGE_ID=ViewResources.BACKGROUND_IMAGE_ID.getResource();
    public static final String FILE_PREFIX=ViewResources.FILE_PREFIX.getResource();
    public static final String IMAGES_LOCATION=ViewResources.IMAGES_LOCATION.getResource();

    public static final  double SCENE_WIDTH = ViewResources.SCENE_WIDTH.getDoubleResource();
    public static final double SCENE_HEIGHT = ViewResources.SCENE_HEIGHT.getDoubleResource();
    Parent createRoot();
    
    Parent getRoot();

    void setBackground();

    void setAvatar();


}
