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

    public void moveUp(Player player){
        GameObject mainChar = player.getMainChar();
        double newPos = mainChar.getYPosition() - Double.parseDouble(mainChar.getProperty("movespeed"));
        currBoundary.moveToYPos(mainChar, newPos);
    }
    
    public void moveDown(Player player){
        GameObject mainChar = player.getMainChar();
        double newPos = mainChar.getYPosition() + Double.parseDouble(mainChar.getProperty("movespeed"));
        currBoundary.moveToYPos(mainChar, newPos);    }

    public void moveRight(Player player){
        GameObject mainChar = player.getMainChar();
        double newPos = mainChar.getXPosition() + Math.abs(Double.parseDouble(mainChar.getProperty("movespeed")));
        currBoundary.moveToXPos(mainChar, newPos);
    }

    public void moveLeft(Player player){
        GameObject mainChar = player.getMainChar();
        double newPos = mainChar.getXPosition() - Math.abs(Double.parseDouble(mainChar.getProperty("movespeed")));
        currBoundary.moveToXPos(mainChar, newPos);
    }

    public void jump(Player player) {
        String jumpVelocity = player.getMainChar().getProperty("jump");
    	if(jumpVelocity!=null){
    		player.getMainChar().setProperty("fallspeed", "-" + jumpVelocity);
    	}
    }

    public void shootProjectile(Player player) {
        if(player.getProjectileProperties() != null){
            ProjectileProperties properties = player.getProjectileProperties();
            GameObject projectile = new GameObject(player.getMainChar().getXPosition(), player.getMainChar().getYPosition(),
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
