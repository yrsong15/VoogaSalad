package gameeditor.controller;

import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import gameeditor.controller.interfaces.IGameEditorFrontEndController;
import gameeditor.view.EditorLevels;
import gameeditor.view.GameEditorView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import objects.Level;
import objects.interfaces.ILevel;
/**
 * @author pratikshasharma
 *
 */
public class GameEditorFrontEndController implements IGameEditorFrontEndController{
    private EditorLevels myEditorLevels;
    private HashMap<String,GameEditorView> myLevelEditorMap ;
    private String activeButtonId;
    private GameEditorView myGameEditor;
    private Scene myLevelScene;
    private GameEditorBackendController myGameEditorBackEndController;
    private LevelManager myLevelManager;
    private boolean isInitialStage;
    
    public static final String DEFAULT_GAME_TITLE = "Untitled";

    
    public Parent startEditor() {
        myLevelManager = new LevelManager();
        
        myGameEditorBackEndController = new GameEditorBackendController();
       
        myGameEditorBackEndController.createGame(DEFAULT_GAME_TITLE);
         
        myEditorLevels= new EditorLevels();
        Parent parent = myEditorLevels.createRoot();
        myEditorLevels.setOnAddLevel( e-> addLevelButton());
        
        // Check for the Load Game Button
        //myEditorLevels.setOnLoadGameButton(e -> loadGame());
        
        // addListenerForGameTitle
        addGameTitleListener();
        return parent;
    }
    
    
    private void addGameTitleListener(){
        myEditorLevels.getGameTitle().addListener(new ChangeListener<String>(){
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) { 
               //System.out.println(" Game Title Changed: " );
               myGameEditorBackEndController.setGameName(newValue.toString()); 
            }
        });
    }
    
    private void addLevelButton(){
        myLevelEditorMap = new HashMap<String,GameEditorView>();
        myEditorLevels.addNewLevel();
        addActiveLevelButtonListener();
        myEditorLevels.setOnLevelClicked((e -> displayLevel()));
    }
    
    private void addActiveLevelButtonListener(){
        myEditorLevels.getActiveLevelButtonID().addListener(new ChangeListener<String>(){
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) {
                activeButtonId = newValue;
            }
        });
    }
    
    private void displayLevel(){
        if(myLevelEditorMap.containsKey(activeButtonId)){
            myGameEditor=myLevelEditorMap.get(activeButtonId);
            setSavedLevelRoot();
        } else{
        	//TODO: create level here, or in LevelManager?
            Level level = new Level(Integer.parseInt(activeButtonId) + 1); // +1 to avoid zero-indexing on level number
            ILevel levelInterface = (ILevel) level;
            myLevelManager.createLevel(level,levelInterface);
            myGameEditor = new GameEditorView(levelInterface);
            myLevelEditorMap.put(activeButtonId, myGameEditor);  
            setNewLevelSceneRoot();
   
            // Create new Level in back end
            myGameEditorBackEndController.setCurrentLevel(level);
            myGameEditorBackEndController.addCurrentLevelToGame();
            
        }
        displayInitialStage();
    }
    
    private void displayInitialStage(){
        Stage myLevelStage;
        if(!isInitialStage){
        myLevelStage = new Stage();
        myLevelScene = new Scene(myGameEditor.createRoot(), GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        myLevelStage.setScene(myLevelScene);
        isInitialStage = true;
        myLevelStage.show();  
        }
    }
    
    private void setNewLevelSceneRoot(){
        if(myLevelScene!=null){
       myLevelScene.setRoot(myGameEditor.createRoot());
        }
    } 
    
    public String getGameFile(){
        // TODO: How is the Game object going to be passed onto the Game Engine?
//    	System.out.println(myGameEditorBackEndController.serializeGame());  //prints out XML on console
    	//myGameEditorBackEndController.getGame();
        return myGameEditorBackEndController.serializeGame();
    }
    
    private void setSavedLevelRoot(){
        myLevelScene.setRoot(myGameEditor.getRoot());
    }
    
    public void setOnLoadGame(EventHandler<MouseEvent> handler){
        myEditorLevels.getLoadButton().setOnMouseClicked( handler);  
    }
    
    public String getGameTitle(){
        return myEditorLevels.getGameTitle().get();
    }
}