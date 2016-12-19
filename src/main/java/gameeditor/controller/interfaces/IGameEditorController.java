package gameeditor.controller.interfaces;


import gameeditor.controller.EditorControllerResources;
import gameeditor.controller.GameEditorBackendController;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import objects.Game;
import objects.interfaces.IGame;
/**
 * 
 * @author Pratiksha Sharma, John Martin
 *
 */
public interface IGameEditorController {
    public static final String CSS_STYLING_EDITOR_LEVELS= "gameEditorSplash.css";
    public static final double EDITOR_LEVELS_SPLASH_HEIGHT=600;
    public static final double EDITOR_LEVELS_SPLASH_WIDTH=700;
    public static final String DEFAULT_GAME_TITLE = EditorControllerResources.DEFAULT_GAME_TITLE.getResource();

    public void startEditor(IGame game);

    public String getGameFile();
       
    public void setOnSaveGame(EventHandler<MouseEvent> handler);

    public String getGameTitle();

    public Image getGameCoverImage();


    public void setGame(Game game);

    public void setOnLoadGame(EventHandler<MouseEvent> handler);
  
}