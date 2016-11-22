/**
 * 
 */
package gameengine.view.interfaces;

import javafx.scene.layout.Pane;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 */
public interface IGameScreen {

	public Pane getScreen();

	public void update(Level level);

	public void setBackgroundImage(String imageFileLocation);
}
