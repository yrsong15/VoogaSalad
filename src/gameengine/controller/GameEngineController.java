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

import frontend.util.ButtonTemplate;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.*;
import gameengine.model.interfaces.Scrolling;
import gameengine.scrolling.LimitedScrolling;
import gameengine.view.GameEngineUI;
import gameengine.view.HighScoreScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import objects.*;
import utils.ReflectionUtil;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon
 *
 */
public class GameEngineController extends Observable implements RuleActionHandler, RGInterface, CommandInterface {
	public static final String STYLESHEET = "default.css";
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
		movementController = new MovementController(this);
		gameEngineView = new GameEngineUI(movementController, event -> reset());

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
			RGFrames.add(new RandomGenFrame(this, 300, currentGame.getCurrentLevel()));
		}
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
		gameScrolling.scrollScreen(currentGame.getCurrentLevel().getGameObjects(), mainChar);
		setChanged();
		notifyObservers();
		removeOffscreenElements();
		gameEngineView.update(currentGame.getCurrentLevel());
		movementChecker.updateMovement(currentGame.getCurrentLevel().getGameObjects());
		for(RandomGenFrame elem: RGFrames){
            for(RandomGeneration randomGeneration : currentGame.getCurrentLevel().getRandomGenRules()) {
                elem.possiblyGenerateNewFrame(0, randomGeneration, this.getClass().getMethod("setNewBenchmark"));
            }
		}
		 Level currLevel = currentGame.getCurrentLevel();
		 collisionChecker.checkCollisions(currentGame.getCurrentLevel().getMainCharacter(), currentGame.getCurrentLevel().getGameObjects());
		 LossChecker.checkLossConditions((RuleActionHandler)this,
		 currentGame.getCurrentLevel().getLoseConditions(), currentGame.getCurrentLevel().getGameConditions());
		 WinChecker.checkWinConditions((RuleActionHandler)this,
		 currLevel.getWinConditions(), currLevel.getGameConditions());
	}
	
	private void removeOffscreenElements() {
		List<GameObject> objects = currentGame.getCurrentLevel().getGameObjects();
		if(objects.size() == 0 || objects == null) return;
		for(int i=1; i<objects.size();){//CHANGE WHEN OBJECT LIST FIXED, BIRD SHOULDN"T BE FIRST OBJECT
			if(objects.get(i).getXPosition()> -GameEngineUI.myAppWidth || objects.get(i) == null) break;//CHANGE THIS TO PIPE WIDTH
			deReferenceObject(i);
			//-700 IS HARD CODED, SHOULD BE - SCREEN WIDTH
			objects.remove(i);
		}
		//System.out.println(objects.get(1).getXPosition());
	}
	
	private void deReferenceObject(int index) {
		currentGame.getCurrentLevel().removeGameObject(index);	
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
		gameEngineView.addHighScore(currentGame.getCurrentLevel().getScore());
		animation.stop();
		HighScoreScreen splash = new HighScoreScreen(currentGame.getCurrentLevel(),
				gameEngineView.getHighScores(), this);
		Stage stage = new Stage();
		stage.setScene(splash.getScene());
		stage.getScene().getStylesheets().add("gameEditorSplash.css");

		stage.setTitle("GAME OVER");
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
                reset();
			}
		});
	}

	public void stop(){
		gameEngineView.stopMusic();
		animation.stop();
	}

	@Override
	public void modifyScore(int score) {
		int prevScore = currentGame.getCurrentLevel().getScore();
		int currScore = prevScore+score;
		currentGame.getCurrentLevel().setScore(currScore);
	}

	public Scene getScene() {
		currentGame = parser.convertXMLtoGame(xmlData);
		return gameEngineView.getScene();
	}
	
	private void setScrolling(){
		ScrollType gameScroll = currentGame.getCurrentLevel().getscrollType();
		//System.out.println(gameScroll);
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
			gameScrolling = (Scrolling) cons.newInstance(gameScroll.getDirections().get(0), currentGame.getCurrentLevel().getGameConditions().get("scrollspeed"));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		animation.stop();
		startGame();
//		gameEngineView.resetMusic();
	}
}