package gameengine.model.boundary;

import gameengine.scrolling.Scrolling;
import objects.GameObject;

public interface GameBoundary {
	
	/**
	 * This is an interface that implements the boundaries for a game. Given an object and it's desired 
	 * position, it decides whether the object can move there or not.
	 */
	public boolean moveToXPos(GameObject toMove, double newXPos, double speed);
	
	public boolean moveToYPos(GameObject toMove, double newYPos);
	
	public double getViewHeight();
	
	public double getViewWidth();
	
	public double getWorldHeight();
	
	public double getWorldWidth();

	public void setScrolling(Scrolling gameScrolling);
}
