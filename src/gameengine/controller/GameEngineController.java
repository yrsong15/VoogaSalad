package gameengine.controller;

import java.util.Observable;

import gameengine.model.CollisionChecker;
import gameengine.view.GameEngineUI;
import objects.GameObject;
import objects.Game;
import objects.Level;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl
 *
 */
public class GameEngineController extends Observable implements GameOverHandler, GameObjectRemoveHandler{

	private String xmlData;
    private GameParser parser;
    private CollisionChecker collisionChecker;
    private boolean gameOver;
	private Game currentGame;
	private GameEngineUI GameEngineView = new GameEngineUI();

	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker();
		movementController = new ScrollerController();
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
		Level currLevel = currentGame.getCurrentLevel();
		collisionChecker.checkCollisions(currLevel.getMainCharacter(), currLevel.getGameObjects());
		//TO-DO: apply movement and scroll screen
	}

	public void setCurrentXML(String xmlData) {
		this.xmlData = xmlData;
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
        //Update the View in some way
	}

	@Override
	public void removeObject(GameObject obj) {
		currentGame.getCurrentLevel().removeGameObject(obj);
	}

	@Override
	public void endGame() {
		gameOver = true;
	}
}

