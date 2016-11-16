package gameEditorView;

import javafx.scene.control.ScrollPane;

public interface IDesignArea {
    public static final String IMAGE_FILE_TYPE = "Image";
    public static final String MUSIC_FILE_TYPE = "Music";
    public static final String IMAGE_FILE_LOCATION = "images/Background";
    public static final  double SCENE_WIDTH = 1000;
    public static final double SCENE_HEIGHT = 600;
    
    public ScrollPane getScrollPane();
}
