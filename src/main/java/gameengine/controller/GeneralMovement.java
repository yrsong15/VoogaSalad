package gameengine.controller;

import gameengine.model.boundary.GameBoundary;
import objects.GameObject;
import objects.Level;


/**
 * @author Soravit, Chalena Scholl
 */

public class GeneralMovement{

    private Level level;
    private GameBoundary currBoundary;
    
    public GeneralMovement(Level level, GameBoundary currBoundary){
        this.level = level;
        this.currBoundary = currBoundary;
    }

    public void moveUp(GameObject obj, double speed){
        double newPos = obj.getYPosition() - speed;
        moveY(obj, newPos, speed);
    }
    
    public void moveDown(GameObject obj, double speed){
        double newPos = obj.getYPosition() + speed;
        moveY(obj, newPos, speed);

    }

    public void moveRight(GameObject obj, double speed){
        double newPos = obj.getXPosition() + Math.abs(speed);
        moveX(obj, newPos, speed);
    }

    public void moveLeft(GameObject obj, double speed){
        double newPos = obj.getXPosition() - Math.abs(speed);
        moveX(obj, newPos, -speed);
    }
    
    private void moveX(GameObject obj, double newXPos, double speed){
    	double oldXPos = obj.getXPosition();
        if (currBoundary.moveToXPos(obj, newXPos, speed)){
        	if (oldXPos > newXPos){
        		obj.setXDistanceMoved(obj.getXDistanceMoved() - speed);
        	}
        	else {
        		obj.setXDistanceMoved(obj.getXDistanceMoved() + speed); 
        	}
        }
    }
    
    private void moveY(GameObject obj, double newYPos, double speed){
    	double oldYPos = obj.getYPosition();
        if (currBoundary.moveToYPos(obj, newYPos)){
        	if (oldYPos > newYPos){
                obj.setYDistanceMoved(obj.getYDistanceMoved() - speed);
        	}
        	else{
                obj.setYDistanceMoved(obj.getYDistanceMoved() + speed);
        	}
        }
    }
}
