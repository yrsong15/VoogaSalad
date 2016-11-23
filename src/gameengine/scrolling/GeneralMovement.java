package gameengine.scrolling;

import objects.GameObject;

/**
 * This is a general movement class that moves one GameObject in a direction.
 * @author Chalena Scholl, 
 */
public class GeneralMovement {
	private double movementSpeed;
	
	public void moveUp(GameObject mainChar){
	    double newPos = mainChar.getYPosition() - movementSpeed;
	    mainChar.setYPosition(newPos);
	}
	
	public void moveDown(GameObject mainChar){
	    double newPos = mainChar.getYPosition() + movementSpeed;
	    mainChar.setYPosition(newPos);
	}
	
	public void moveLeft(GameObject mainChar){
	    double newPos = mainChar.getXPosition() - movementSpeed;
	    mainChar.setYPosition(newPos);
	}
	
	public void moveRight(GameObject mainChar){
	    double newPos = mainChar.getXPosition() + movementSpeed;
	    mainChar.setYPosition(newPos);
	}

}
