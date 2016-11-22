package gameengine.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import gameengine.controller.interfaces.MovementHandler;
import gameengine.model.CollisionChecker;
import gameengine.model.LossChecker;
import gameengine.model.WinChecker;
import gameengine.model.interfaces.Rule;
import gameengine.model.settings.Music;
import gameengine.view.GameEngineUI;
import javafx.scene.Scene;
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
	private GameEngineUI gameEngineView;

	private Map<String, KeyCode> controls;
	private MovementController movementController;

	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker(this);
		movementController = new MovementController();
        gameEngineView = new GameEngineUI(movementController);
		controls = new HashMap<String, KeyCode>();
	}

	public void startGame() {
        gameOver = false;
        currentGame = parser.convertXMLtoGame(xmlData);
        movementController.setGame(currentGame);
        gameEngineView.setLevel(currentGame.getCurrentLevel());
        gameEngineView.setMusic(currentGame.getCurrentLevel().getViewSettings().getMusicFilePath());
        gameEngineView.setBackgroundImage(currentGame.getCurrentLevel().getViewSettings().getBackgroundFilePath());
//        while (!gameOver){
        	loopGame();
//        }
	}

	public void mapControls(){
        //NEED TO DO
    }
	
	
	/**
	 * Applies gravity and scrolls, checks for collisions
	 */
	public void loopGame(){
		//Level currLevel = ;
		gameEngineView.update(currentGame.getCurrentLevel());
//		collisionChecker.checkCollisions(currLevel.getMainCharacter(), currLevel.getGameObjects(), (RuleActionHandler)this);
//		LossChecker.checkLossConditions((RuleActionHandler)this, currLevel.getLoseConditions(), currLevel.getGameConditions());
//		WinChecker.checkWinConditions((RuleActionHandler)this, currLevel.getWinConditions(), currLevel.getGameConditions());
		
		//TO-DO: apply movement and scroll screen

	}

	public void setCurrentXML(String xmlData) {
		this.xmlData = xmlData;
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
        gameEngineView.update(currentGame.getCurrentLevel());
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
	
	public Scene getScene(){
		currentGame = parser.convertXMLtoGame(xmlData);
		return gameEngineView.getScene();
	}
}

