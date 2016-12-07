package gameengine.controller;

import com.sun.javafx.scene.traversal.Direction;
import gameengine.controller.interfaces.MovementInterface;
import gameengine.model.boundary.ScreenBoundary;
import objects.GameObject;
import objects.Level;
import objects.ProjectileProperties;
import java.util.HashMap;


public class ControlManager implements MovementInterface{

    private Level level;
    private ScreenBoundary currBoundary;

    public void setMainChar(Level level, ScreenBoundary currBoundary){
        this.level = level;
        this.currBoundary = currBoundary;
    }

    @Override
    public void moveUp(){
        GameObject mainChar = level.getMainCharacter();
        double newPos = mainChar.getYPosition() - Double.parseDouble(mainChar.getProperty("movespeed"));
        currBoundary.moveToYPos(mainChar, newPos);
    }
    

    @Override
    public void moveDown(){
        GameObject mainChar = level.getMainCharacter();
        double newPos = mainChar.getYPosition() + Double.parseDouble(mainChar.getProperty("movespeed"));
        currBoundary.moveToYPos(mainChar, newPos);    }

    @Override
    public void moveRight(){
        GameObject mainChar = level.getMainCharacter();
        double newPos = mainChar.getXPosition() + Math.abs(Double.parseDouble(mainChar.getProperty("movespeed")));
        currBoundary.moveToXPos(mainChar, newPos);
    }

    @Override
    public void moveLeft(){
        GameObject mainChar = level.getMainCharacter();
        double newPos = mainChar.getXPosition() - Math.abs(Double.parseDouble(mainChar.getProperty("movespeed")));
        currBoundary.moveToXPos(mainChar, newPos);
    }

    @Override
    public void jump() {
        GameObject mainChar = level.getMainCharacter();
        String jumpVelocity = mainChar.getProperty("jump");
    	if(jumpVelocity!=null){
    		mainChar.setProperty("fallspeed", "-" + jumpVelocity);
    	}
    }

    @Override
    public void shootProjectile() {
        if(level.getMainCharacter().getProjectileProperties() != null){
            ProjectileProperties properties = level.getMainCharacter().getProjectileProperties();
            GameObject projectile = new GameObject(level.getMainCharacter().getXPosition(), level.getMainCharacter().getYPosition(),
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
