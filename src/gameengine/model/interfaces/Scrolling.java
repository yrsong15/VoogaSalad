package gameengine.model.interfaces;

import java.util.List;

import objects.GameObject;
import objects.Level;

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
	 * Completes the scrolling of the screen based on the speed
	 */
	void scrollScreen(List<GameObject> gameObjects, GameObject mainChar);



}
