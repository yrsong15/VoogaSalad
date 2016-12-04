package gameeditor.view;

import gameeditor.commanddetails.DetailResources;
import javafx.scene.Parent;

public interface IEditorLevels {

    public static final String LEVEL_LABEL ="Level";
    public static final double ADD_LEVELS_WIDTH = 400;
    public static final double ADD_LEVELS_HEIGHT=350;
    public static final double LEVEL_PANE_X_POSITION = 200;
    public static final double LEVEL_PANE_Y_POSITION = 80;
    public static final double BUTTON_ICON_PROPORTION = 50;
    public static final String DEFAULT_GAME_TITLE = "Untitled";
    public static final String XML_FILE_TYPE="XML";
    public static final String SAVE_LABEL=DetailResources.SAVE_BUTTON_TEXT.getResource();
    public Parent createRoot(String gameName);
    
}
