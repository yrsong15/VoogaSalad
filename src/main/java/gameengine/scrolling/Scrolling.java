package gameengine.scrolling;

import java.util.List;
import exception.ScrollDirectionNotFoundException;
import objects.GameObject;

//This entire file is part of my masterpiece.
//Chalena Scholl

/**
 * This shows that I've learned the importance of abstraction and using interfaces. By using an interface, I've specified what 
 * a class needs to implement that wants to implement a certain way of scrolling a screen. This way, 
 * an implementation can do it whichever way it likes as long as the end result is the same and the screen is scrolled in a certain
 * direction, giving whichever objects are not scrolled the appearance of movement. Thus, it allows flexibility in class 
 * definitions. Additionally, this interface is small and client-specific so it follows the interface segregation principle.
 * This principle states that it's important to make interfaces as client-specific as possible so that classes don't have
 * to implement unnecessary methods.
 */
public interface Scrolling {
	
	/**
	 * @param gameObjects objects to scroll in set direction
	 * @param speed at which to scroll the objects
	 */
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
	 * Completes the scrolling of the screen based on set speed
	 */
	void scrollScreen(List<GameObject> gameObjects, GameObject mainChar);
	
	
	/**
	 * Completes the scrolling of the screen based on the speed given as parameter
	 */
	void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed);



}
