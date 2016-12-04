package gameeditor.controller;

import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import gameeditor.controller.interfaces.IGameEditorFrontEndController;
import gameeditor.view.EditorLevels;
import gameeditor.view.GameEditorView;
import gameengine.view.GameEngineUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import objects.Level;
import objects.RandomGeneration;
import objects.interfaces.ILevel;
/**
 * @author pratikshasharma, Ray Song
 *
 */
public class GameEditorController implements IGameEditorFrontEndController{
    private EditorLevels myEditorLevels;
    private HashMap<String,GameEditorView> myLevelEditorMap ;
    private String activeButtonId;
    private GameEditorView myGameEditorView;
    private Scene myLevelScene;
    private GameEditorBackendController myGameEditorBackEndController;
    private LevelManager myLevelManager;
    private boolean isInitialStage;
    private Stage myLevelStage;
    private Parent myRoot;
    
    //TODO: move all hard-coded strings into a resource bundle
    public static final String DEFAULT_GAME_TITLE = "Untitled";

    
    public void startEditor() {
        myLevelManager = new LevelManager();
        
        myGameEditorBackEndController = new GameEditorBackendController();
        myGameEditorBackEndController.createGame(DEFAULT_GAME_TITLE);
         
        myEditorLevels= new EditorLevels();
        myRoot = myEditorLevels.createRoot();
        myEditorLevels.setOnAddLevel( e-> addLevelButton());
        addGameTitleListener();
        displayInitialStage(); 
        addActiveLevelButtonListener();
    }
    
    private void displayInitialStage(){  
        myLevelStage = new Stage();
        myLevelScene = new Scene(myRoot, GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        myLevelStage.setScene(myLevelScene);
        isInitialStage = true;
        myLevelStage.show();  
    }
    
    
    private void addGameTitleListener(){
        myEditorLevels.getGameTitle().addListener(new ChangeListener<String>(){
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) { 
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
            myGameEditorView=myLevelEditorMap.get(activeButtonId);
            setSavedLevelRoot();
            myGameEditorView.setSaveProperty(false);
            addSaveLevelListener();
        } else{
            Level level = new Level(Integer.parseInt(activeButtonId) + 1); // +1 to avoid zero-indexing on level number
            ILevel levelInterface = (ILevel) level;
            myLevelManager.createLevel(level);         
            myGameEditorView = new GameEditorView(levelInterface);          
            myLevelEditorMap.put(activeButtonId, myGameEditorView);             
            setNewLevelSceneRoot();         
            myGameEditorBackEndController.setCurrentLevel(level);
            myGameEditorBackEndController.addCurrentLevelToGame();  
            addSaveLevelListener();
        }     
    }
    
    private void addSaveLevelListener(){
        myGameEditorView.getSaveLevelProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed (ObservableValue<? extends Boolean> observable,
                                 Boolean oldValue,
                                 Boolean newValue) {
                if(newValue.booleanValue()==true){
                  myLevelScene.setRoot(myEditorLevels.getRoot());
                }
            }   
        });
    }
    
    private void setNewLevelSceneRoot(){
       myLevelScene.setRoot(myGameEditorView.createRoot());     
    } 
    
    public String getGameFile(){
        return myGameEditorBackEndController.serializeGame();
    }
    
    private void setSavedLevelRoot(){
        myLevelScene.setRoot(myGameEditorView.getRoot());
    }
    
    public void setOnLoadGame(EventHandler<MouseEvent> handler){
        myEditorLevels.getLoadButton().setOnMouseClicked( handler);  
    }
    
    public String getGameTitle(){
        return myEditorLevels.getGameTitle().get();
    }
}