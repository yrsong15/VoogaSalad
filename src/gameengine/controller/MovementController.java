package gameengine.controller;


import gameengine.controller.interfaces.MovementHandler;
import gameengine.controller.interfaces.MovementInterface;
import javafx.scene.Scene;
import objects.Game;
import objects.GameObject;



public class MovementController implements MovementInterface{
    private static final double movementSpeed = 30;// CHANGE LATER
    private Scene myScene;
    private MovementHandler handler;
    private Game currentGame;

    public void setGame(Game currentGame){
        this.currentGame = currentGame;
    }

    @Override
    public void moveUp(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getYPosition() - movementSpeed;
        mainChar.setYPosition(newPos);
    }

    @Override
    public void moveDown(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getYPosition() + movementSpeed;
        mainChar.setYPosition(newPos);
    }

    @Override
    public void moveRight(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getXPosition() + Math.abs(movementSpeed);
        mainChar.setYPosition(newPos);
    }

    @Override
    public void moveLeft(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getXPosition() - Math.abs(movementSpeed);
        mainChar.setXPosition(newPos);
    }

    @Override
    public void jump() {
    	GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
    	String jumpVelocity = mainChar.getProperty("jump");
    	if(jumpVelocity!=null){
    		mainChar.setProperty("speed", "-"+jumpVelocity);
    	}
    }

    @Override
    public void shootProjectile() {

    }
}
