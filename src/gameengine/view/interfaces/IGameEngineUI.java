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
	
	public void update(Level level);
	
	public void setMusic(String musicFileLocation);
	
	public void setBackgroundImage(String imageFileLocation);
}
