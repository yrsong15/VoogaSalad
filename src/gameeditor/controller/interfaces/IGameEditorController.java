package gameeditor.controller.interfaces;

import javafx.scene.Parent;
import objects.Game;


/**
 * 
 * @author Ray Song(ys101)
 *
 */
public interface IGameEditorController {
	public Parent startEditor();
	public Game getGame();
}
