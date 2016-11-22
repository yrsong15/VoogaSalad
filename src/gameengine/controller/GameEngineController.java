package gameengine.controller;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.CollisionChecker;
import gameengine.model.MovementChecker;
import gameengine.view.GameEngineUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import objects.GameObject;
import objects.Game;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel Moon
 *
 */
public class GameEngineController extends Observable implements RuleActionHandler {

	private String xmlData;
    private GameParser parser;
    private CollisionChecker collisionChecker;
    private MovementChecker movementChecker;
	private Game currentGame;
	private GameEngineUI gameEngineView;
	private Timeline animation;
    private MovementController movementController;
	
    public static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker(this);
        movementChecker = new MovementChecker();
		movementController = new MovementController();
        gameEngineView = new GameEngineUI(movementController);
	}

	public void startGame() {
        currentGame = parser.convertXMLtoGame(xmlData);
        movementController.setGame(currentGame);
        gameEngineView.setLevel(currentGame.getCurrentLevel());
        gameEngineView.setMusic(currentGame.getCurrentLevel().getViewSettings().getMusicFilePath());
        gameEngineView.setBackgroundImage(currentGame.getCurrentLevel().getViewSettings().getBackgroundFilePath());
        gameEngineView.mapKeys(currentGame.getCurrentLevel().getControls());
        KeyFrame frame = new KeyFrame(Duration.millis(200),
                e -> {
                    try {
                        updateGame();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    }
                });
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public void mapControls(){
        //NEED TO DO
    }
	
	
	/**
	 * Applies gravity and scrolls, checks for collisions
	 */
	public void updateGame() throws ClassNotFoundException, InstantiationException {
        movementController.scroll();
        setChanged();
        notifyObservers();
        gameEngineView.update(currentGame.getCurrentLevel());
        movementChecker.updateMovement(currentGame.getCurrentLevel().getGameObjects());
//		Level currLevel = currentGame.getCurrentLevel();
//		collisionChecker.checkCollisions(currLevel.getMainCharacter(), currLevel.getGameObjects(), (RuleActionHandler)this);
//		LossChecker.checkLossConditions((RuleActionHandler)this, currLevel.getLoseConditions(), currLevel.getGameConditions());
//		WinChecker.checkWinConditions((RuleActionHandler)this, currLevel.getWinConditions(), currLevel.getGameConditions());
	}

	public void setCurrentXML(String xmlData) {
		this.xmlData = xmlData;
	}

	@Override
	public void removeObject(GameObject obj) {
		currentGame.getCurrentLevel().removeGameObject(obj);
	}

	@Override
	public void endGame() {
		animation.stop();
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

