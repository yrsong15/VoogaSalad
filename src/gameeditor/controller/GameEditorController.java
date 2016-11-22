package gameeditor.controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import gameeditor.controller.interfaces.ICreateGame;
import gameeditor.controller.interfaces.ICreateGameObject;
import gameeditor.controller.interfaces.ICreateLevel;
import gameeditor.controller.interfaces.IGameEditorController;
import gameeditor.view.EditorLevels;
import gameeditor.view.GameEditorView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Game;
import objects.GameObject;
import objects.Level;

/**
 * This is the central class for the Game Editor backend that contains all the methods that can be called
 * by the Game Editor frontend.
 * 
 * @author Ray Song(ys101)
 *
 */
//TODO: Add functions that allow user to toggle between Maps, GObjects, and Levels
public class GameEditorController implements IGameEditorController, ICreateGame, ICreateLevel, ICreateGameObject{  
    private GameEditorView myGameEditor;
    private LevelManager myLevelManager;

    private EditorLevels myEditorLevels;
    private Game myGame;
    private Level myCurrentLevel;
    private GameObject go;
    private Map<String, String> properties;
    private Map<String,GameEditorView> myLevelEditorMap;
    private MapManager myMapManager;
    private String activeButtonId;
    private Scene myLevelScene;

    public GameEditorController(){
        myLevelManager = new LevelManager();
    }


    @Override

    public Game getGame(){
        return myGame;
    }


    public Map<String, String> getProperties(){
        return properties;
    }

    @Override
    public void createGame(String title) {
        Game game = new Game(title);	
        myGame = game;
    }

    @Override
    public void createLevel(int levelNumber) {
        myLevelManager.createLevel(levelNumber);
        myCurrentLevel = myLevelManager.getLevel();
    }

    @Override
    public void addCurrentLevelToGame() {
        myGame.addLevel(myCurrentLevel);
    }

    @Override
    public void createGameObject(double xPos, double yPos, double width, double height, 
                                 String imageFileName, Map<String, String> properties) {
        GameObject go = new GameObject(xPos, yPos, width, height, imageFileName, properties);
        this.go = go;
    }




    private Map<?, ?> createProperties() {
        Map<String, String> properties = new HashMap<String, String>();
        this.properties = properties;
        return properties;
    }

    @Override
    public Parent startEditor() {
        myEditorLevels= new EditorLevels();
        Parent parent = myEditorLevels.createRoot();
        //myEditorLevels.getNewLevelButton().setOnAction( e-> print());
        //myEditorLevels.setOnAddLevel(e -> addNewLevel());

        myEditorLevels.setOnAddLevel( e-> addLevelButton());


        return parent;

        //return myGameEditor.createRoot();
    }

    private void addLevelButton(){
        myLevelEditorMap = new HashMap<String,GameEditorView>();
        String buttonId = myEditorLevels.addNewLevel();
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
            
            //TODO: Change this later to the saved instance
            //myLevelScene.setRoot(myGameEditor.createRoot());
            
        } else{
            myGameEditor = new GameEditorView();
            myLevelEditorMap.put(activeButtonId, myGameEditor); 
            displayInitiallyOnSytage();
        }

    }

   

    private void displayInitiallyOnSytage(){
        Stage myLevelStage = new Stage();
        myLevelScene = new Scene(myGameEditor.createRoot(), GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        
myLevelStage.setScene(myLevelScene);
myLevelStage.show();
    }

    @Override
    public void addToProperties(String key, String value) {
        //if(myCurrentMap==null) myMapManager.createMap();  //This is just in case 
        //myCurrentMap = myMapManager.getCurrentMap();
        //myCurrentMap.put(key, value);
    }


    @Override
    public void addCurrentGameObjectToLevel() {
        //myCurrentLevel.addGameObject(myGameObject);
    }

    @Override
    public void addWinConditions(String type, String action) {
        myLevelManager.addWinConditions(type, action);
        myCurrentLevel = myLevelManager.getLevel();
    }

    @Override
    public void addLoseConditions(String type, String action) {
        myLevelManager.addLoseConditions(type, action);
        myCurrentLevel = myLevelManager.getLevel();
    }

    private Integer getId(){
        Random rand = new Random();
        int randomInteger = rand.nextInt();
        while(myLevelEditorMap.containsKey(randomInteger)){
            randomInteger = rand.nextInt();
        }
        return randomInteger;
    }


}
