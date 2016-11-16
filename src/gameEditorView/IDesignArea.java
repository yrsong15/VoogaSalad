package gameEditorView;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

public interface IDesignArea {
	// TODO: Move to AppResources
    public static final String IMAGE_FILE_TYPE = "Image";
    public static final String MUSIC_FILE_TYPE = "Music";
    public static final String IMAGE_FILE_LOCATION = "images/Background";
    public static final  double SCENE_WIDTH = 1000;
    public static final double SCENE_HEIGHT = 600;
    
    public ScrollPane getScrollPane();
    
    public void updateAvatar(Image newAvatar);
    
}
