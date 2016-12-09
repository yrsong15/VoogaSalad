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
 * @author Soravit, Chalena
 */

public class ControlManager implements ControlInterface{

    private Level level;
    private ScreenBoundary currBoundary;
    
    public ControlManager(Level level, ScreenBoundary currBoundary){
        this.level = level;
        this.currBoundary = currBoundary;
    }

    public void moveUp(GameObject mainChar, double speed){
        double newPos = mainChar.getYPosition() - speed;
        currBoundary.moveToYPos(mainChar, newPos);
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

    public void jump(GameObject mainChar, double speed) {
        String jumpVelocity = mainChar.getProperty("jump");
    	if(jumpVelocity!=null){
    		mainChar.setProperty("fallspeed", "-" + jumpVelocity);
    	}
    }

    public void shootProjectile(GameObject player, double speed) {
        if(player.getProjectileProperties() != null){
            ProjectileProperties properties = player.getProjectileProperties();
            GameObject projectile = new GameObject(player.getXPosition(), player.getYPosition(),
                    properties.getWidth(), properties.getHeight(), properties.getImageFileName(), new HashMap<>());
            if(properties.getDirection().equals(Direction.LEFT)){
                projectile.setProperty("horizontalmovement", String.valueOf(properties.getSpeed()*-1));
            }else if(properties.getDirection().equals(Direction.RIGHT)){
                projectile.setProperty("horizontalmovement", String.valueOf(properties.getSpeed()));
            }else if(properties.getDirection().equals(Direction.DOWN)){
                projectile.setProperty("gravity", String.valueOf(properties.getSpeed()));
            }else if(properties.getDirection().equals(Direction.UP)){
                projectile.setProperty("gravity", String.valueOf(properties.getSpeed() * -1));
            }
            projectile.setProperty("damage", String.valueOf(properties.getDamage()));
            level.getProjectiles().add(projectile);
        }
    }
}
