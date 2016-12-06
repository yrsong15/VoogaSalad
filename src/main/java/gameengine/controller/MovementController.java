package gameengine.controller;


import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.MovementHandler;
import gameengine.controller.interfaces.MovementInterface;
import gameengine.model.boundary.ScreenBoundary;
import javafx.scene.Scene;
import objects.Game;
import objects.GameObject;



public class MovementController implements MovementInterface{

    private Game currentGame;
    private CommandInterface commandInterface;
    private ScreenBoundary currBoundary;
    
    public MovementController(CommandInterface commandInterface){
    	this.commandInterface = commandInterface;
    }

    public void setGame(Game currentGame, ScreenBoundary currBoundary){
        this.currentGame = currentGame;
        this.currBoundary = currBoundary;
    }

    @Override
    public void moveUp(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getYPosition() - Double.parseDouble(mainChar.getProperty("movespeed"));
        currBoundary.moveToYPos(mainChar, newPos);
    }
    

    @Override
    public void moveDown(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getYPosition() + Double.parseDouble(mainChar.getProperty("movespeed"));
        currBoundary.moveToYPos(mainChar, newPos);    }

    @Override
    public void moveRight(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getXPosition() + Math.abs(Double.parseDouble(mainChar.getProperty("movespeed")));
        currBoundary.moveToXPos(mainChar, newPos);
    }

    @Override
    public void moveLeft(){
        GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
        double newPos = mainChar.getXPosition() - Math.abs(Double.parseDouble(mainChar.getProperty("movespeed")));
        currBoundary.moveToXPos(mainChar, newPos);
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
