package gameengine.controller;

import com.sun.javafx.scene.traversal.Direction;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.boundary.ScreenBoundary;
import objects.GameObject;
import objects.Level;
import objects.Player;
import objects.ProjectileProperties;
import java.util.HashMap;


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
        if (currBoundary.moveToYPos(obj, newPos)){
            obj.setYDistanceMoved(obj.getYDistanceMoved() + speed);
        }
    }
    
    public void moveDown(GameObject obj, double speed){
        double newPos = obj.getYPosition() + speed;
        if (currBoundary.moveToYPos(obj, newPos)){
            obj.setYDistanceMoved(obj.getYDistanceMoved() + speed);
        }
    }

    public void moveRight(GameObject obj, double speed){
        double newPos = obj.getXPosition() + Math.abs(speed);
        if (currBoundary.moveToXPos(obj, newPos)){
        	if (obj.isPlayer()) System.out.println(obj.getXDistanceMoved());
            obj.setXDistanceMoved(obj.getXDistanceMoved() + speed);
        }
    }

    public void moveLeft(GameObject obj, double speed){
        double newPos = obj.getXPosition() - Math.abs(speed);
        if (currBoundary.moveToXPos(obj, newPos)){
            obj.setXDistanceMoved(obj.getXDistanceMoved() + speed);
        }
    }
}
