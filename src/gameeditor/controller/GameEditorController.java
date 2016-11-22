package gameeditor.controller;

import java.util.Map;

import gameeditor.controller.interfaces.ICreateGame;
import gameeditor.controller.interfaces.ICreateGameObject;
import gameeditor.controller.interfaces.ICreateLevel;
import gameeditor.controller.interfaces.IGameEditorController;
import gameeditor.view.GameEditorView;
import javafx.scene.Parent;
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
//TODO: Add rules/key controls to the XML
public class GameEditorController implements IGameEditorController, ICreateGame, ICreateLevel, ICreateGameObject{  
    private GameEditorView myGameEditor;
    private LevelManager myLevelManager;
    private MapManager myMapManager;
    
    private Game myGame;
    private Level myCurrentLevel;
    private GameObject myGameObject;
    private Map<String, String> myCurrentMap;
    
    public GameEditorController(){
    	myGameEditor = new GameEditorView();
    	myLevelManager = new LevelManager();
    	myMapManager = new MapManager();
    }
    
    @Override
    public Game getGame(){
    	return myGame;
    }
    
	@Override
	public Parent startEditor() {
		return myGameEditor.createRoot();
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
		myGameObject = go;
	}

	@Override
	public void addToProperties(String key, String value) {
		if(myCurrentMap==null) myMapManager.createMap();  //This is just in case 
		myCurrentMap = myMapManager.getCurrentMap();
		myCurrentMap.put(key, value);
	}

	@Override
	public void addCurrentGameObjectToLevel() {
		myCurrentLevel.addGameObject(myGameObject);
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
	
	//TODO: Should I append a map, or add each property one-by-one? Depends on whether the map is used for other purposes.
	@Override
	public void addCurrentPropertiesToGameObject() {
		myGameObject.setPropertiesList(myCurrentMap);	
	}

	@Override
	public void addScore(double score) {
		myLevelManager.addScore(score);
	}

	@Override
	public void addTime(double time) {
		myLevelManager.addTime(time);
	}

	@Override
	public void addBackgroundMusic(String musicFilePath) {
		myLevelManager.addBackgroundMusic(musicFilePath);
	}

	@Override
	public void addBackgroundImage(String backgroundFilePath) {
		myLevelManager.addBackgroundImage(backgroundFilePath);
	}

	@Override
	public void setCurrentLevelToGame() {
		myGame.setCurrentLevel(myCurrentLevel);
	}

	@Override
	public void setCurrentGameObjectToMainCharacter() {
		myCurrentLevel.setMainCharacter(myGameObject);
	}
}
