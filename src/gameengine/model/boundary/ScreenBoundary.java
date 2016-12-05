package gameengine.model.boundary;

import objects.GameObject;

public interface ScreenBoundary {
	
	/**
	 * This is an interface that implements the boundaries for a game. Given an object and it's desired 
	 * position, it decides whether the object can move there or not.
	 */
	
	public void moveGameObject(GameObject toMove, double newXPos, double newYPos);
}
