package gameengine.scrolling;

import java.util.List;
import exception.ScrollDirectionNotFoundException;
import objects.GameObject;

/**
 * This interface should be implemented when adding a type of scrolling
 * @author Chalena Scholl
 */
public interface Scrolling {
	
	void scrollDirection(List<GameObject> gameObjects, double speed);

	/**
	 * Sets how fast the screen will be scrolled
	 * @param speed
	 */
	void setScrollingSpeed(double speed);
	
	
	/**
	 * Sets in which direction the screen will be scrolled
	 * @param scrollDirection
	 */
	void setDirection(ScrollDirection scrollDirection);
	
	
	/**
	 * @param requested direction of scrolling requested
	 * @param player main character of game
	 * @return whether or not scrolling is currently allowed
	 */
	boolean allowedToScroll(ScrollDirection requested, GameObject player);
	
	
	/**
	 * Completes the scrolling of the screen based on speed in class
	 * @throws ScrollDirectionNotFoundException 
	 */
	void scrollScreen(List<GameObject> gameObjects, GameObject mainChar) throws ScrollDirectionNotFoundException;
	
	
	/**
	 * Completes the scrolling of the screen based on the speed given as parameter
	 * @throws ScrollDirectionNotFoundException 
	 */
	void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed) throws ScrollDirectionNotFoundException;



}
