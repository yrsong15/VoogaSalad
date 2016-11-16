package gameEditorView;

import javafx.scene.layout.Pane;

public interface IEditorToolbar {
    public static final String IMAGE_FILE_TYPE = "Image";
    public static final String MUSIC_FILE_TYPE = "Music";
    public static final String IMAGE_FILE_LOCATION = "images/Toolbar";
    public static final  double SCENE_WIDTH = 1000;
    public static final double SCENE_HEIGHT = 600;
    
    public static final double TOOLBAR_WIDTH = 0.7*SCENE_WIDTH;
    public static final double TOOLBAR_HEIGHT = 75;
    
    public static final double BUTTON_IMAGE_HEIGHT = 50;
    public static final double BUTTON_IMAGE_YOFFSET = (TOOLBAR_HEIGHT - BUTTON_IMAGE_HEIGHT)/2;
    
    public static final double BG_IMAGE_WIDTH_RATIO = 1.15;
    public static final double BG_IMAGE_WIDTH = BUTTON_IMAGE_HEIGHT*BG_IMAGE_WIDTH_RATIO;
    public static final double BG_IMAGE_XOFFSET = TOOLBAR_WIDTH - 25 - BG_IMAGE_WIDTH;
    
    public static final double AVATAR_IMAGE_WIDTH_RATIO = 0.88;
    public static final double AVATAR_IMAGE_WIDTH = BUTTON_IMAGE_HEIGHT*AVATAR_IMAGE_WIDTH_RATIO;
    public static final double AVATAR_IMAGE_XOFFSET = TOOLBAR_WIDTH - 25 - BG_IMAGE_WIDTH - 25 - AVATAR_IMAGE_WIDTH;
    
    
    public Pane getPane();
}
