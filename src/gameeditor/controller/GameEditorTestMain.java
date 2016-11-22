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
		GameEditorBackendController myController = new GameEditorBackendController();
		XMLSerializer mySerializer = new XMLSerializer();

		myController.createGame("flappy bird");

		myController.createLevel(1);

		myController.createGameObject(15, 30, 100, 50, "bird3.png", new HashMap<String, String>());
		myController.addCurrentGameObjectToLevel();
		myController.setCurrentGameObjectToMainCharacter(myController.getCurrentGameObject());

		myController.createGameObject(100, 200, 300, 400, "Pipes.png", new HashMap<String, String>());
		myController.addToProperties("damage", "30");
		myController.addToProperties("removeobject", "xxxxxx");
		myController.addToProperties("points", "50");
		myController.addCurrentPropertiesToGameObject();
		myController.addCurrentGameObjectToLevel();

		myController.createGameObject(100, 200, 300, 400, "Pipes.png", new HashMap<String, String>());
		myController.addToProperties("damage", "30");
		myController.addToProperties("removeobject", "xxxxxx");
		myController.addToProperties("points", "50");
		myController.addCurrentPropertiesToGameObject();
		myController.addCurrentGameObjectToLevel();

		myController.addWinConditions("score", "10");
		myController.addLoseConditions("time", "30");
		myController.addScore(0);
		myController.addTime(0);

		myController.addBackgroundImage("Background/bg.png");
		myController.addBackgroundMusic("FlappyBirdThemeSong.mp3");

		myController.addCurrentLevelToGame();
		myController.setCurrentLevelToGame();

		String testResult = mySerializer.serializeGame(myController.getGame());
		System.out.println(testResult);
		mySerializer.getGameFromString(testResult);
	}
}