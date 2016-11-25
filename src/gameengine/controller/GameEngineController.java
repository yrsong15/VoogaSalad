package gameengine.controller;

import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.CollisionChecker;
import gameengine.model.MovementChecker;
import gameengine.model.RandomGenFrame;
import gameengine.scrolling.LimitedScrolling;
import gameengine.scrolling.ScrollDirection;
import gameengine.view.GameEngineUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import objects.GameObject;
import objects.Level;
import objects.Game;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon
 *
 */
public class GameEngineController extends Observable implements RuleActionHandler, RGInterface {

	private String xmlData;
	private GameParser parser;
	private CollisionChecker collisionChecker;
	private MovementChecker movementChecker;
	private Game currentGame;
	private GameEngineUI gameEngineView;
	private Timeline animation;
	private MovementController movementController;
	private LimitedScrolling lim;
	private RandomGenFrame RGFrame;

	public static final double FRAMES_PER_SECOND = 60;
	public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1 / FRAMES_PER_SECOND;

	public GameEngineController() {
		parser = new GameParser();

		collisionChecker = new CollisionChecker(this);
		movementChecker = new MovementChecker();
		movementController = new MovementController();
		gameEngineView = new GameEngineUI(movementController);
		lim = new LimitedScrolling(ScrollDirection.Right, 30);
	}

	public void startGame() {
		currentGame = parser.convertXMLtoGame(xmlData);
		movementController.setGame(currentGame);
		gameEngineView.setLevel(currentGame.getCurrentLevel());
//        RGFrame = new RandomGenFrame(this,300,currentGame.getCurrentLevel());
		gameEngineView.setMusic(currentGame.getCurrentLevel().getViewSettings().getMusicFilePath());
		gameEngineView.setBackgroundImage(currentGame.getCurrentLevel().getViewSettings().getBackgroundFilePath());
		gameEngineView.mapKeys(currentGame.getCurrentLevel().getControls());
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
			 try {
                 updateGame();
             } catch (ClassNotFoundException e1) {
                 e1.printStackTrace();
             } catch (InstantiationException e1) {
                 e1.printStackTrace();
             } catch (NoSuchMethodException e1) {
             	e1.printStackTrace();
             }
             catch (Exception e1) {
					e1.printStackTrace();
				}
		});
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public void mapControls() {
		// NEED TO DO
	}

	/**
	 * Applies gravity and scrolls, checks for collisions
	 * 
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */

	public void updateGame() throws ClassNotFoundException, InstantiationException, IllegalArgumentException,
			InvocationTargetException, IllegalAccessException, NoSuchMethodException, SecurityException {
		GameObject mainChar = currentGame.getCurrentLevel().getMainCharacter();
		// movementController.scroll();
		lim.scrollScreen(currentGame.getCurrentLevel().getGameObjects(), mainChar);
		setChanged();
		notifyObservers();
		gameEngineView.update(currentGame.getCurrentLevel());
		movementChecker.updateMovement(currentGame.getCurrentLevel().getGameObjects());
//		RGFrame.possiblyGenerateNewFrame(0, currentGame.getCurrentLevel().getRandomGenRules(),
//				this.gwetClass().getMethod("setNewBenchmark"));

		// Level currLevel = currentGame.getCurrentLevel();
		collisionChecker.checkCollisions(currentGame.getCurrentLevel().getMainCharacter(),
				currentGame.getCurrentLevel().getGameObjects());
		// LossChecker.checkLossConditions((RuleActionHandler)this,
		// currLevel.getLoseConditions(), currLevel.getGameConditions());
		// WinChecker.checkWinConditions((RuleActionHandler)this,
		// currLevel.getWinConditions(), currLevel.getGameConditions());
	}

	public void setNewBenchmark() {
		List<GameObject> objects = currentGame.getCurrentLevel().getGameObjects();
		RGFrame.setNewBenchmark(new Integer((int) objects.get(objects.size() - 1).getXPosition() / 2));
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

	public Scene getScene() {
		currentGame = parser.convertXMLtoGame(xmlData);
		return gameEngineView.getScene();
	}
}
