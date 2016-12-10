/**
 * 
 */
package gameengine.view.interfaces;

import javafx.scene.layout.HBox;

/**
 * @author Noel Moon (nm142), Ray Song
 *
 */
public interface IToolbar {

	public HBox getToolbar();
	public void resume();
	public void pause();
	public void mute();
	public void unmute();
	public void saveGame();
}
