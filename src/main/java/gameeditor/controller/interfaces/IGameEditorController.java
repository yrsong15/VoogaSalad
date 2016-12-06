package gameeditor.controller.interfaces;

import objects.Game;

public interface IGameEditorController {
   public static final String CSS_STYLING_EDITOR_LEVELS= "gameEditorSplash.css";
   public static final double EDITOR_LEVELS_SPLASH_HEIGHT=600;
   public static final double EDITOR_LEVELS_SPLASH_WIDTH=700;
    public void startEditor(Game game);
    
    
}