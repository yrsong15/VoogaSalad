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

public class ControlManager{

    private Level level;
    private ScreenBoundary currBoundary;
    
    public ControlManager(Level level, ScreenBoundary currBoundary){
        this.level = level;
        this.currBoundary = currBoundary;
    }

    public void moveUp(GameObject obj, double speed){
        double newPos = obj.getYPosition() - speed;
        currBoundary.moveToYPos(obj, newPos);
    }
    
    public void moveDown(GameObject mainChar, double speed){
        double newPos = mainChar.getYPosition() + speed;
        currBoundary.moveToYPos(mainChar, newPos);    
    }

    public void moveRight(GameObject mainChar, double speed){
        double newPos = mainChar.getXPosition() + Math.abs(speed);
        currBoundary.moveToXPos(mainChar, newPos);
    }

    public void moveLeft(GameObject mainChar, double speed){
        double newPos = mainChar.getXPosition() - Math.abs(speed);
        currBoundary.moveToXPos(mainChar, newPos);
    }
}
