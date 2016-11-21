package gameengine.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import gameengine.model.CollisionChecker;
import gameengine.model.interfaces.Rule;
import gameengine.model.settings.Music;
import gameengine.view.GameEngineUI;
import javafx.scene.input.KeyCode;
import objects.GameObject;
import objects.Game;
import objects.Level;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel Moon
 *
 */
public class GameEngineController extends Observable implements RuleActionHandler{

	private String xmlData;
    private GameParser parser;
    private CollisionChecker collisionChecker;
    private boolean gameOver;
	private Game currentGame;
	private GameEngineUI GameEngineView;
	private Map<String, KeyCode> controls;
	private FreeRoamScrollerController movementController;

	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker(this);
		movementController = new FreeRoamScrollerController();
        controls = new HashMap<String, KeyCode>();
	}

	public void startGame() {
        currentGame = parser.convertXMLtoGame(xmlData);
        GameEngineView = new GameEngineUI(currentGame.getCurrentLevel());
        //Change music
        //Change background
        gameOver = false;
        while (!gameOver){
        	loopGame();
        }
	}

	public void mapControls(){
        //NEED TO DO
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
        GameEngineView.update(currentGame.getCurrentLevel());
	}

	@Override
	public void removeObject(GameObject obj) {
		currentGame.getCurrentLevel().removeGameObject(obj);
	}

	@Override
	public void endGame() {
		gameOver = true;
	}

	@Override
	public void modifyScore(int score) {
		// TODO Auto-generated method stub
		
	}
}

