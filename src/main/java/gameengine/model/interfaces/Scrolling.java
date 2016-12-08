package gameengine.model.interfaces;

import java.util.List;
import exception.ScrollDirectionNotFoundException;
import objects.GameObject;
import objects.Level;
import com.sun.javafx.scene.traversal.Direction;

/**
 * This interface should be implemented when adding a type of scrolling
 * @author Chalena Scholl
 */
public interface Scrolling {
	
	
	/**
	 * Sets how fast the screen will be scrolled
	 * @param speed
	 */
	void setSpeed(double speed);
	
	
	/**
	 * Sets in which direction the screen will be scrolled
	 * @param scrollDirection
	 */
	void setDirection(Direction scrollDirection);
	
	
	/**
	 * Completes the scrolling of the screen based on the speed
	 * @throws ScrollDirectionNotFoundException 
	 */
	void scrollScreen(List<GameObject> gameObjects, GameObject mainChar) throws ScrollDirectionNotFoundException;



}
