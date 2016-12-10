package gameengine.controller;

import gameengine.model.boundary.ScreenBoundary;
import objects.GameObject;
import objects.Level;


/**
 * @author Soravit, Chalena Scholl
 */

public class GeneralMovement{

    private Level level;
    private ScreenBoundary currBoundary;
    
    public GeneralMovement(Level level, ScreenBoundary currBoundary){
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
        moveX(obj, newPos, speed);
    }
    
    private void moveX(GameObject obj, double newXPos, double speed){
        if (currBoundary.moveToXPos(obj, newXPos)){
            obj.setXDistanceMoved(obj.getXDistanceMoved() + speed);
        }
    }
    
    private void moveY(GameObject obj, double newYPos, double speed){
        if (currBoundary.moveToYPos(obj, newYPos)){
            obj.setYDistanceMoved(obj.getYDistanceMoved() + speed);
        }
    }
}
