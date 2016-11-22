package gameeditor.controller;

import java.util.HashMap;

import gameeditor.xml.XMLSerializer;


/**
 * This is test code that is meant to show how the backend Game Editor is to be used.
 * This specific code creates the exact same rendition of the example XML code that is in the data directory.
 * 
 * @author Ray Song(ys101)
 *
 */
public class GameEditorTestMain {
	public static void main(String[] args){
		GameEditorController myController = new GameEditorController();
		XMLSerializer mySerializer = new XMLSerializer();
		
		myController.createGame("flappy bird");
		
		myController.createLevel(1);
		
		myController.createGameObject(15, 30, 100, 50, "bird.jpg", new HashMap<String, String>());
		myController.addToProperties("collidable", "die");
		myController.addToProperties("removeobject", "xxxxxx Whatever lololol hahahaha xxxxxx");
		myController.addToProperties("damage", "50");
		myController.addCurrentPropertiesToGameObject();
		
		myController.addCurrentGameObjectToLevel();
		
		myController.addWinConditions("score", "10");
		myController.addLoseConditions("time", "30");
		myController.addScore(0);
		myController.addTime(0);
		
		myController.addCurrentLevelToGame();
		
		
		String testResult = mySerializer.serializeGame(myController.getGame());
		System.out.println(testResult);
		mySerializer.getGameFromString(testResult);
	}
}
