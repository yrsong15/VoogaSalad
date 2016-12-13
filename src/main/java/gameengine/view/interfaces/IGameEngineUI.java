/**
 * 
 */
package gameengine.view.interfaces;

import javafx.scene.Scene;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 */
public interface IGameEngineUI {

	public Scene getScene();
	
	public void setBackgroundImage(String imageFileLocation);
	
	public void stopMusic();
	
	public void pause();
}
