package gameeditor.controller;

import gameeditor.view.GameEditorView;
import javafx.scene.Parent;
import objects.Game;

/**
 * @author pratikshasharma
 */
public class GameEditorController {  
    private LevelManager myLevelManager;
    private Game myGame;
    private GameEditorView myGameEditor;
    
    public GameEditorController(){
        myLevelManager = new LevelManager();
        myGameEditor = new GameEditorView();
        
        // DO WE NEED THIS?? 
        myGame = new Game("game" );
    }
    
    public Parent startEditor(){
        return myGameEditor.createRoot();
    }
    
    
    
}
