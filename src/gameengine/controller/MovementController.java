package gameengine.controller;


import gameengine.controller.interfaces.MovementHandler;
import gameengine.view.interfaces.MovementInterface;
import javafx.scene.Scene;
import objects.Game;
import objects.GameObject;



public class MovementController implements MovementInterface{
	private static final double movementSpeed = 10;// CHANGE LATER
	private Scene myScene;
	private MovementHandler handler;
	private Game currentGame;
	
//	public MovementController(Game currentGame){
//		this.currentGame = currentGame;
//	}
	
	
//	public void setScene(Scene myScene){
//		this.myScene = myScene;
//	}
	
	public void setGame(Game currentGame){
		this.currentGame = currentGame;
	}
	
	public void UPKeyPressed(){
		
		/**for(GameObject obstacle:currentGame.getCurrentLevel().getGameObjects()){
			double newPos = obstacle.getYPosition() + movementSpeed;
			obstacle.setYPosition(newPos);
		}**/
		GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
		double newPos = mainChar.getYPosition() - movementSpeed;
		mainChar.setYPosition(newPos);
	}
	
	public void DOWNKeyPressed(){
	/**	for(GameObject obstacle:currentGame.getCurrentLevel().getGameObjects()){
			double newPos = obstacle.getYPosition() - movementSpeed;
			obstacle.setYPosition(newPos);
			
		}**/
		GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
		double newPos = mainChar.getYPosition() + movementSpeed;
		mainChar.setYPosition(newPos);
	}
	public void RIGHTKeyPressed(){
		for(GameObject obstacle:currentGame.getCurrentLevel().getGameObjects()){
			if (obstacle==currentGame.getCurrentLevel().getMainCharacter()){
				continue;
			}
			double newPos = obstacle.getXPosition() - movementSpeed;
			obstacle.setXPosition(newPos);
		}	
	}
	public void LEFTKeyPressed(){
		for(GameObject obstacle:currentGame.getCurrentLevel().getGameObjects()){	
			if (obstacle==currentGame.getCurrentLevel().getMainCharacter()){
				continue;
			}
			double newPos = obstacle.getXPosition() + movementSpeed;
			obstacle.setXPosition(newPos);
		}
			}
	
	public void scroll(){
		RIGHTKeyPressed();
	}
}
