package gameeditor.controller;

import java.util.HashMap;
import java.util.Map;

import gameeditor.controller.interfaces.ICreateGame;
import gameeditor.controller.interfaces.ICreateGameObject;
import gameeditor.controller.interfaces.ICreateLevel;
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
public class GameEditorController implements ICreateGame, ICreateLevel, ICreateGameObject{  
    private Game game;
    private Level level;
    private GameObject go;
    private Map<String, String> properties;
    
    public Game getGame(){
    	return game;
    }
    
    public Map<String, String> getProperties(){
    	return properties;
    }

	@Override
	public void createGame(String title) {
		Game game = new Game(title);	
		this.game = game;
	}

	@Override
	public void createLevel(int levelNumber) {
		Level level = new Level(levelNumber);
		this.level = level;
	}

	@Override
	public void addCurrentLevelToGame() {
		game.addLevel(level);
	}

	@Override
	public void createGameObject(double xPos, double yPos, double width, double height, 
			String imageFileName, Map<String, String> properties) {
		GameObject go = new GameObject(xPos, yPos, width, height, imageFileName, properties);
		this.go = go;
	}

	@Override
	public void addToProperties(String key, String value) {
		if(properties==null) createProperties();
		properties.put(key, value);
	}

	@Override
	public void addCurrentGameObjectToLevel() {
		level.addGameObject(go);
	}
	
	private Map<?, ?> createProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		this.properties = properties;
		return properties;
	}
    
    
    
}
