package gameEditorView;

import javafx.scene.Parent;

public interface IGameEditorView {
    public static final String IMAGE_FILE_TYPE = "Image";
    public static final String MUSIC_FILE_TYPE = "Music";
    public static final String BG_IMAGE_LOCATION = "images/Background";
    public static final String AVATAR_IMAGE_LOCATION = "images/Sprite";

    public static final  double SCENE_WIDTH = 1000;
    public static final double SCENE_HEIGHT = 600;
    
    public Parent createRoot();
    
}
