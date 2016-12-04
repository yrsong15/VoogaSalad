package gameengine.controller;


import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.MovementHandler;
import gameengine.controller.interfaces.MovementInterface;
import javafx.scene.Scene;
import objects.Game;
import objects.GameObject;



public class MovementController implements MovementInterface{

    private Game currentGame;
    private CommandInterface commandInterface;
    
    public MovementController(CommandInterface commandInterface){
    	this.commandInterface = commandInterface;
    }

    public void setGame(Game currentGame){
        this.currentGame = currentGame;
    }

    @Override
    public void moveUp(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getYPosition() - Double.parseDouble(mainChar.getProperty("movespeed"));
        mainChar.setYPosition(newPos);
    }

    @Override
    public void moveDown(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getYPosition() + Double.parseDouble(mainChar.getProperty("movespeed"));
        mainChar.setYPosition(newPos);
    }

    @Override
    public void moveRight(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getXPosition() + Math.abs(Double.parseDouble(mainChar.getProperty("movespeed")));
        mainChar.setYPosition(newPos);
    }

    @Override
    public void moveLeft(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getXPosition() - Math.abs(Double.parseDouble(mainChar.getProperty("movespeed")));
        mainChar.setXPosition(newPos);
    }

    @Override
    public void jump() {
    	GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
    	String jumpVelocity = mainChar.getProperty("jump");
    	if(jumpVelocity!=null){
    		mainChar.setProperty("fallspeed", "-"+jumpVelocity);
    	}
    	else{
    	}
    }

    @Override
    public void shootProjectile() {

    }

	@Override
	public void reset() {

		commandInterface.reset();
	}
}
