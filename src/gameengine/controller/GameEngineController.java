package gameengine.controller;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.sun.javafx.scene.traversal.Direction;

import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.CollisionChecker;
import gameengine.model.MovementChecker;
import gameengine.model.RandomGenFrame;
import gameengine.model.interfaces.Scrolling;
import gameengine.scrolling.LimitedScrolling;
import gameengine.view.GameEngineUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import objects.*;
import utils.ReflectionUtil;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon
 *
 */
public class GameEngineController extends Observable implements RuleActionHandler, RGInterface {
	private ArrayList<RandomGenFrame> RGFrames;
	private String xmlData;
	private GameParser parser;
	private CollisionChecker collisionChecker;
	private MovementChecker movementChecker;
	private Game currentGame;
	private GameEngineUI gameEngineView;
	private Timeline animation;
	private MovementController movementController;
	private Scrolling gameScrolling;
	public static final double FRAMES_PER_SECOND = 10;
	public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1 / FRAMES_PER_SECOND;
	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker(this);
		movementChecker = new MovementChecker();
		movementController = new MovementController();
		gameEngineView = new GameEngineUI(movementController);
		RGFrames = new ArrayList<>();
	}
	public void startGame() {
		currentGame = parser.convertXMLtoGame(xmlData);
		movementController.setGame(currentGame);
		gameEngineView.setLevel(currentGame.getCurrentLevel());
		addRGFrames();
		gameEngineView.setMusic(currentGame.getCurrentLevel().getViewSettings().getMusicFilePath());
		gameEngineView.setBackgroundImage(currentGame.getCurrentLevel().getViewSettings().getBackgroundFilePath());
		gameEngineView.mapKeys(currentGame.getCurrentLevel().getControls());
		setScrolling();
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
	
	private void addRGFrames(){
        List<RandomGeneration> randomGenerations = currentGame.getCurrentLevel().getRandomGenRules();
		for(RandomGeneration randomGeneration : randomGenerations) {
			RGFrames.add(new RandomGenFrame(this, 300, currentGame.getCurrentLevel(), randomGeneration.getGameObject().getImageFileName()));
		}
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
		//lim.scrollScreen(currentGame.getCurrentLevel().getGameObjects(), mainChar);
		gameScrolling.scrollScreen(currentGame.getCurrentLevel().getGameObjects(), mainChar);

		setChanged();
		notifyObservers();
		gameEngineView.update(currentGame.getCurrentLevel());
		movementChecker.updateMovement(currentGame.getCurrentLevel().getGameObjects());
		for(RandomGenFrame elem: RGFrames){
            for(RandomGeneration randomGeneration : currentGame.getCurrentLevel().getRandomGenRules()) {
                elem.possiblyGenerateNewFrame(0, randomGeneration,
                        this.getClass().getMethod("setNewBenchmark"));
            }
		}
		
		// Level currLevel = currentGame.getCurrentLevel();
//		collisionChecker.checkCollisions(currentGame.getCurrentLevel().getMainCharacter(),
//				currentGame.getCurrentLevel().getGameObjects());
		// LossChecker.checkLossConditions((RuleActionHandler)this,
		// currLevel.getLoseConditions(), currLevel.getGameConditions());
		// WinChecker.checkWinConditions((RuleActionHandler)this,
		// currLevel.getWinConditions(), currLevel.getGameConditions());
	}
	public void setNewBenchmark() {
		List<GameObject> objects = currentGame.getCurrentLevel().getGameObjects();
		for(RandomGenFrame elem: RGFrames){
			elem.setNewBenchmark(new Integer((int) objects.get(objects.size() - 1).getXPosition() / 2));
		}
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
	public void stopMusic() {
		gameEngineView.stopMusic();
	}
	@Override
	public void modifyScore(int score) {
		// TODO Auto-generated method stub
	}
	public Scene getScene() {
		currentGame = parser.convertXMLtoGame(xmlData);
		return gameEngineView.getScene();
	}
	
	private void setScrolling(){
		ScrollType gameScroll = currentGame.getCurrentLevel().getscrollType();
		System.out.println(gameScroll);
		gameScroll.getDirections();
		Class<?> cl = null;
		try {
			cl = Class.forName("gameengine.scrolling." + gameScroll.getScrollType());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Constructor<?> cons = null;
		try {
			cons = cl.getConstructor(Direction.class, double.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			gameScrolling = (Scrolling) cons.newInstance(gameScroll.getDirections().get(0), 30);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}