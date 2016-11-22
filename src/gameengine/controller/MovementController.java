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
        for(GameObject obstacle:currentGame.getCurrentLevel().getGameObjects()){
            if (obstacle==currentGame.getCurrentLevel().getMainCharacter()){
                continue;
            }
            double newPos = obstacle.getXPosition() - movementSpeed;
            obstacle.setXPosition(newPos);
        }
    }

    @Override
    public void moveLeft(){
        for(GameObject obstacle:currentGame.getCurrentLevel().getGameObjects()){
            if (obstacle==currentGame.getCurrentLevel().getMainCharacter()){
                continue;
            }
            double newPos = obstacle.getXPosition() + movementSpeed;
            obstacle.setXPosition(newPos);
        }
    }

    @Override
    public void jump() {

    }

    @Override
    public void shootProjectile() {

    }


    public void scroll(){
        moveRight();
    }
}
