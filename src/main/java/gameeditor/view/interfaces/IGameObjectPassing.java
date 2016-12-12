package gameeditor.view.interfaces;

import java.util.Map;
/**
 * 
 * @author John Martin
 *
 */
public interface IGameObjectPassing {
	public String getImagePath();
	
	public double getX(); // Within the pane
	
	public double getY();
	
	public double getWidth();
	
	public double getHeight();
	
	@SuppressWarnings("rawtypes")
	public Map properties();
	
	// Map for win conditions, map for lose conditions, map in each game object
	// Map for each game object refers to collisions involving the main char and that object
	// An example for an entry in the win map would be a key of score and a value of 50
	// an example for a collision rule would be a key of damage and a value of removeObject
	
}
