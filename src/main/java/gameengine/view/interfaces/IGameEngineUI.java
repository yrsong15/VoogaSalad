/**
 * 
 */
package gameengine.view.interfaces;

import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 */
public interface IGameEngineUI {

	Scene getScene();
	
	void setBackgroundImage(String imageFileLocation);
	
	void stopMusic();

	void pause();

	Stage getMyLevelStage();
}
