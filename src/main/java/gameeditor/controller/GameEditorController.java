package gameeditor.controller;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objects.Game;
import objects.Level;
import objects.interfaces.IGame;
import objects.interfaces.ILevel;
/**
 * @author pratikshasharma, Ray Song, John Martin
 *
 */



public class GameEditorController implements IGameEditorController{
    private EditorLevels myEditorLevels;
    private Map<String,GameEditorView> myLevelEditorMap ;
    private String activeButtonId;
    private GameEditorView myGameEditorView;
    private Scene myLevelScene;
    private GameEditorBackendController myGameEditorBackEndController;
    private LevelManager myLevelManager;
    private Stage myLevelStage;
    private Parent myRoot;
    private IGame myGameInterface;
    private String myGameType;


    public GameEditorController(String gameType){
        myGameType = gameType;
    }

    public GameEditorController(){
        this(EditorControllerResources.DEFAULT_GAME_TYPE.getResource());
    }

    public void startEditor(IGame game) {
        myLevelManager = new LevelManager();
        if(game==null){
            myGameEditorBackEndController = new GameEditorBackendController();
            myGameEditorBackEndController.createGame(DEFAULT_GAME_TITLE);
            myGameInterface = game;
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
        chooser.saveFile(EditorControllerResources.XML_DATA_TYPE.getResource(), EditorControllerResources.DEFAULT_SAVE_FOLDER.getResource(), getGameFile(), EditorControllerResources.DEFAULT_SAVING_NAME.getResource());
    }

    private void displayInitialStage(){  
        myLevelStage = new Stage();
        myLevelStage.setResizable(false);
        myLevelStage.setTitle(EditorControllerResources.GAME_EDITOR_TITLE.getResource());
        myLevelScene = new Scene(myRoot, EDITOR_LEVELS_SPLASH_WIDTH, EDITOR_LEVELS_SPLASH_HEIGHT);
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
        Level level;
        if(myLevelEditorMap.containsKey(activeButtonId)){
            myGameEditorView=myLevelEditorMap.get(activeButtonId);
            level = myGameInterface.getLevelByIndex(Integer.parseInt(activeButtonId)+1);
            myGameInterface.setCurrentLevel(level);
            setSavedLevelRoot();
            myGameEditorView.setSaveProperty(false);
            addSaveLevelListener();
        } else{
            if(myGameInterface!=null && myGameInterface.getLevelByIndex(Integer.parseInt(activeButtonId)+1)!=null){
                level = myGameInterface.getLevelByIndex(Integer.parseInt(activeButtonId)+1);
            }else {
                level = new Level(Integer.parseInt(activeButtonId) + 1); 
            }

            ILevel levelInterface = (ILevel) level;      
            myLevelManager.createLevel(level);
            myLevelManager.setLeveltitle(myEditorLevels.getGameTitle().get());
            myGameEditorBackEndController.setCurrentLevel(level);
            myGameEditorBackEndController.addCurrentLevelToGame();
            myGameEditorView = new GameEditorView(levelInterface, myGameInterface, myGameType);
            myLevelEditorMap.put(activeButtonId, myGameEditorView);             

            setNewLevelSceneRoot();    
            addSaveLevelListener();
        }     
    }

    private void addSaveLevelListener(){
        myGameEditorView.getSaveLevelProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed (ObservableValue<? extends Boolean> observable,
                                 Boolean oldValue,
                                 Boolean newValue) {
                if(newValue.booleanValue()){
                    myGameInterface.setGameName(myEditorLevels.getGameTitle().get());
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
        myLevelStage.setResizable(false);
        myLevelScene.getStylesheets().remove(CSS_STYLING_EDITOR_LEVELS);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        myLevelStage.setX((screenBounds.getWidth() - myLevelStage.getWidth()) / 2); 
        myLevelStage.setY((screenBounds.getHeight() - myLevelStage.getHeight()) / 2);
    }

    public String getGameFile(){
        myGameInterface.setGameName(myEditorLevels.getGameTitle().get());
        return myGameEditorBackEndController.serializeGame();
    }

    private void setSavedLevelRoot(){  
        myLevelScene.setRoot(myGameEditorView.getRoot());
        myGameEditorView.addStuffFromOtherFiles();
        resizeToLevelStage();
    }

    public void setOnSaveGame(EventHandler<MouseEvent> handler){
        if(myEditorLevels!=null){
            myEditorLevels.setOnSaveGame(handler);
        }
    }

    public String getGameTitle(){
        return myEditorLevels.getGameTitle().get();
    }

    public Image getGameCoverImage(){
        return myEditorLevels.getGameCoverImage();
    }

    public void setGame(Game game){
        myGameEditorBackEndController = new GameEditorBackendController();
        myGameEditorBackEndController.setGame(game);
    }

    public void setOnLoadGame(EventHandler<MouseEvent> handler){
        myEditorLevels.setOnLoadGameButton(handler);
    }
}