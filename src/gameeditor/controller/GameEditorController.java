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
    
    //TODO: move all hard-coded strings into a resource bundle
    public static final String DEFAULT_GAME_TITLE = "Untitled";

    
    public Parent startEditor() {
        myLevelManager = new LevelManager();
        
        myGameEditorBackEndController = new GameEditorBackendController();
       
        myGameEditorBackEndController.createGame(DEFAULT_GAME_TITLE);
         
        myEditorLevels= new EditorLevels();
        myRoot = myEditorLevels.createRoot(myGameEditorBackEndController.getGame().getGameName());
        
        if(myGameEditorBackEndController.getGame().getNumberOfLevels()!=0){
            for(int i=0;i<myGameEditorBackEndController.getGame().getNumberOfLevels();i++){
                addLevelButton();
            }
        }
        myEditorLevels.setOnAddLevel( e-> addLevelButton());

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
        } else{
            Level level;
            if(myGameInterface!=null && myGameInterface.getLevelByIndex(Integer.parseInt(activeButtonId)+1)!=null){
                level = myGameInterface.getLevelByIndex(Integer.parseInt(activeButtonId)+1);
            }else {
                level = new Level(Integer.parseInt(activeButtonId) + 1); // +1 to avoid zero-indexing on level number
            }
            ILevel levelInterface = (ILevel) level;
            myLevelManager.createLevel(level);
            myGameEditorView = new GameEditorView(levelInterface);
            
            myLevelEditorMap.put(activeButtonId, myGameEditorView);  
            
            setNewLevelSceneRoot();
   
            myGameEditorBackEndController.setCurrentLevel(level);
       
            myGameEditorBackEndController.addCurrentLevelToGame();
            
        }
        displayInitialStage();
    }
    
    private void displayInitialStage(){
        Stage myLevelStage;
        if(!isInitialStage){
        myLevelStage = new Stage();
        myLevelScene = new Scene(myGameEditorView.createRoot(), GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        
        
        myLevelStage.setScene(myLevelScene);
        isInitialStage = true;
        myLevelStage.show();  
        addSaveLevelListener( myLevelStage);
        }
    }

    
    
    private void addSaveLevelListener(Stage myLevelStage){
        myGameEditorView.getSaveLevelProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed (ObservableValue<? extends Boolean> observable,
                                 Boolean oldValue,
                                 Boolean newValue) {
                if(newValue.booleanValue()==true){
                    myLevelStage.close();
                }
            }
            
        });
    }
    private void setNewLevelSceneRoot(){
        if(myLevelScene!=null){
       myLevelScene.setRoot(myGameEditorView.createRoot());
        }
    } 
    
    public String getGameFile(){
    	System.out.println(myGameEditorBackEndController.serializeGame());  //prints Game as XML on console
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