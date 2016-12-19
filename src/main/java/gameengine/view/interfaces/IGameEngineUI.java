// This entire file is part of my masterpiece.
// Noel Moon

package gameengine.view.interfaces;

import java.util.List;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import objects.Level;
import objects.Player;

/**
 * The purpose of this interface is to provide a powerful API for the front-end of the game engine. It offers a variety of functionalities like
 * update() which animates the visuals, and setBackgroundImage, which does as its name suggests.
 * 
 * This is well designed because there are many versatile yet simple methods provided by this API, the most important of which is 
 * update(). update() is the method that is in charge of all of the animation of the visuals, yet its complicated implementation is 
 * encapsulated in this API, and all the user has to call is update() in the controller.
 * 
 * @author Noel Moon (nm142)
 *
 */
public interface IGameEngineUI {

	public void startClient(String serverName);
	
	public void initLevel(Map<Long, List<Player>> playerMapping);
	
	public Scene getScene();
	
	public void update();
	
	public void setBackgroundImage(String imageFileLocation);
	
	public void playMusic(String musicFileName);
	
	public void stopMusic();
	
	public void mapKeys(Player player, Map<KeyCode, String> mappings);
	
	public void endGame();

	public void pause();
	
	public void saveGame();
	
	public void stop();
	
	public void resetGameScreen();

	public Stage getMyLevelStage();
	
	
}
