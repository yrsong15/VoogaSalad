package gameengine.controller;

import java.util.Observable;

import gameengine.model.CollisionChecker;
import objects.Game;
import objects.Level;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou
 *
 */
public class GameEngineController extends Observable{

	private String xmlData;
    private GameParser parser;
    private CollisionChecker collisionChecker;
    private boolean gameOver;
	private Game currentGame;

	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker();
	}

	public void startGame() {
        currentGame = parser.convertXMLtoGame(xmlData);
        gameOver = false;
        while (!gameOver){
        	loopGame();
        }
	}
	
	/**
	 * Applies gravity and scrolls, checks for collisions
	 */
	public void loopGame(){
		Game mainGame = null;
		Level currLevel = mainGame.getCurrentLevel();
		collisionChecker.checkCollisions(currLevel.getMainCharacter(), currLevel.getGameObjects());
	}

	public void setCurrentXML(String xmlData) {
		this.xmlData = xmlData;
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
        //Update the View in some way
	}
}

