package gameeditor.controller;

import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import frontend.util.FileOpener;
import gameeditor.controller.interfaces.IGameEditorController;
import gameeditor.view.EditorLevels;
import gameeditor.view.GameEditorView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objects.Game;
import objects.Level;
import objects.interfaces.IGame;
/**
 * @author pratikshasharma, Ray Song
 *
 */
public class GameEditorController implements IGameEditorController{
    private EditorLevels myEditorLevels;
    private HashMap<String,GameEditorView> myLevelEditorMap ;
    private String activeButtonId;
    private GameEditorView myGameEditorView;
    private Scene myLevelScene;
    private GameEditorBackendController myGameEditorBackEndController;
    private LevelManager myLevelManager;
    private Stage myLevelStage;
    private Parent myRoot;
    private IGame myGameInterface;

    //TODO: move all hard-coded strings into a resource bundle
    public static final String DEFAULT_GAME_TITLE = "Untitled";


    public void startEditor(Game game) {
        myLevelManager = new LevelManager();
        myGameEditorBackEndController = new GameEditorBackendController();
        if(game==null){
            myGameEditorBackEndController.createGame(DEFAULT_GAME_TITLE);
        }else{
            myGameEditorBackEndController.setGame(game);
            myGameInterface = (IGame) game;
        }
        myEditorLevels= new EditorLevels();
        myRoot = myEditorLevels.createRoot(myGameEditorBackEndController.getGame().getGameName());
        
        if(myGameEditorBackEndController.getGame().getNumberOfLevels()!=0){
            for(int i=0;i<myGameEditorBackEndController.getGame().getNumberOfLevels();i++){
                addLevelButton();
            }
        }
        myEditorLevels.setOnAddLevel( e-> addLevelButton());
        myEditorLevels.setOnSaveGame(e-> saveGameToFile());
        addGameTitleListener();
        displayInitialStage(); 
        addActiveLevelButtonListener();
    }

    private void saveGameToFile(){
        FileOpener chooser = new FileOpener();
        chooser.saveFile("XML", "data", getGameFile(), "vooga");
    }

    private void displayInitialStage(){  
        myLevelStage = new Stage();
        myLevelScene = new Scene(myRoot, EDITOR_LEVELS_SPLASH_WIDTH, EDITOR_LEVELS_SPLASH_HEIGHT);

        //myLevelScene = new Scene(myRoot, GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        myLevelStage.setScene(myLevelScene);
        myLevelStage.show();  
        myLevelScene.getStylesheets().add(CSS_STYLING_EDITOR_LEVELS);
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
            Level level;
            if(myGameInterface!=null && myGameInterface.getLevelByIndex(Integer.parseInt(activeButtonId)+1)!=null){
                level = myGameInterface.getLevelByIndex(Integer.parseInt(activeButtonId)+1);
            }else {
                level = new Level(Integer.parseInt(activeButtonId) + 1); // +1 to avoid zero-indexing on level number
            }
            myLevelManager.createLevel(level);   

            myGameEditorView = new GameEditorView(level);
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
                    resizeStageToSplashScreen();
                }
            }   
        });
    }

    private void resizeStageToSplashScreen(){
        myLevelStage.setHeight(EDITOR_LEVELS_SPLASH_HEIGHT);
        myLevelStage.setWidth(EDITOR_LEVELS_SPLASH_WIDTH);
        myLevelScene.getStylesheets().add(CSS_STYLING_EDITOR_LEVELS);
    }

    private void setNewLevelSceneRoot(){
        myLevelScene.setRoot(myGameEditorView.createRoot()); 

        resizeToLevelStage();
    } 

    private void resizeToLevelStage(){
        myLevelStage.setHeight(GameEditorView.SCENE_HEIGHT+20);
        myLevelStage.setWidth(GameEditorView.SCENE_WIDTH);
        myLevelScene.getStylesheets().remove(CSS_STYLING_EDITOR_LEVELS);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        myLevelStage.setX((screenBounds.getWidth() - myLevelStage.getWidth()) / 2); 
        myLevelStage.setY((screenBounds.getHeight() - myLevelStage.getHeight()) / 2);
    }

    public String getGameFile(){
        //System.out.println (myGameEditorBackEndController.serializeGame());

        return myGameEditorBackEndController.serializeGame();
    }

    private void setSavedLevelRoot(){
        myLevelScene.setRoot(myGameEditorView.getRoot());
        resizeToLevelStage();
    }

    public void setOnLoadGame(EventHandler<MouseEvent> handler){
        if(myEditorLevels!=null){
            myEditorLevels.getLoadButton().setOnMouseClicked( handler);  
        }
    }

    public String getGameTitle(){
        return myEditorLevels.getGameTitle().get();
    }


}