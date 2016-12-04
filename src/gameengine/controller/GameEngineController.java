package gameengine.controller;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import com.sun.javafx.scene.traversal.Direction;

import exception.ScrollTypeNotFoundException;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.*;
import gameengine.model.interfaces.Scrolling;
import gameengine.view.GameEngineUI;
import gameengine.view.HighScoreScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.*;
import utils.ReflectionUtil;

import javax.swing.*;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon
 */

public class GameEngineController implements RuleActionHandler, RGInterface, CommandInterface {
    public static final double FRAMES_PER_SECOND = 30;
    public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1 / FRAMES_PER_SECOND;
    private static final String EDITOR_SPLASH_STYLE = "gameEditorSplash.css";

	private List<RandomGenFrame> RGFrames;
    private List<Integer> highScores;
    private String xmlData;
	private GameParser parser;
	private CollisionChecker collisionChecker;
	private MovementChecker movementChecker;
	private Game currentGame;
	private GameEngineUI gameEngineView;
	private Timeline animation;
	private MovementController movementController;
	private Scrolling gameScrolling;
	private Stage endGameStage;

	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker(this);
		movementChecker = new MovementChecker();
		movementController = new MovementController(this);
		gameEngineView = new GameEngineUI(movementController, event -> reset());
		RGFrames = new ArrayList<>();
        highScores = new ArrayList<>();
    }

    public Scene getScene() {
        return gameEngineView.getScene();
    }

	public boolean startGame(String xmlData) {
        this.xmlData = xmlData;
		currentGame = parser.convertXMLtoGame(xmlData);
        if(currentGame.getCurrentLevel() == null || currentGame.getCurrentLevel().getMainCharacter() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Cannot start game.");
            alert.setContentText("You must create a level with a main character to start a game.");
            alert.showAndWait();
            return false;
        }
		movementController.setGame(currentGame);
        gameEngineView.initLevel(currentGame.getCurrentLevel());
		gameEngineView.mapKeys(currentGame.getCurrentLevel().getControls());
        addRGFrames();
        try {
			setScrolling();
		} catch (ScrollTypeNotFoundException e1) {
			System.out.println("The scroll type could not be found/instantiated through reflection.");
		}
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
			try {
				updateGame();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		});
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
        return true;
	}

	/**
	 * Applies gravity and scrolls, checks for collisions
	 */
	public void updateGame(){
		Level currLevel = currentGame.getCurrentLevel();
		GameObject mainChar = currLevel.getMainCharacter();
		gameScrolling.scrollScreen(currLevel.getGameObjects(), mainChar);
        if(currLevel.getScrollType().getScrollTypeName().equals("ForcedScrolling")) {
            removeOffscreenElements();
        }
		gameEngineView.update(currLevel);
		movementChecker.updateMovement(currLevel.getGameObjects());
		for(RandomGenFrame elem: RGFrames){
            for(RandomGeneration randomGeneration : currLevel.getRandomGenRules()) {
                try {
					elem.possiblyGenerateNewFrame(100, randomGeneration, this.getClass().getMethod("setNewBenchmark"));
				} catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException
						| NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		}
         collisionChecker.checkCollisions(mainChar, currLevel.getGameObjects());
		 LossChecker.checkLossConditions(this,
				 		currLevel.getLoseConditions(), currLevel.getGameConditions());
		 WinChecker.checkWinConditions(this,
				 		currLevel.getWinConditions(), currLevel.getGameConditions());
	}

    public void setNewBenchmark() {
        List<GameObject> objects = currentGame.getCurrentLevel().getGameObjects();
        for(RandomGenFrame elem: RGFrames){
            elem.setNewBenchmark(new Integer((int) objects.get(objects.size() - 1).getXPosition() / 2));
        }
    }

    @Override
    public void removeObject(GameObject obj) {
        currentGame.getCurrentLevel().removeGameObject(obj);
    }

    @Override
    public void endGame() {
        addHighScore(currentGame.getCurrentLevel().getScore());
        animation.stop();
        HighScoreScreen splash = new HighScoreScreen(currentGame.getCurrentLevel(),
                highScores, this);
        if (endGameStage == null) {
        	endGameStage = new Stage();
        }
        endGameStage.setScene(splash.getScene());
        endGameStage.getScene().getStylesheets().add(EDITOR_SPLASH_STYLE);
        endGameStage.setTitle("GAME OVER");
        endGameStage.show();
    }

    @Override
    public void reset() {
        animation.stop();
        gameEngineView.resetGameScreen();
        startGame(xmlData);
    }

    public void stop(){
        gameEngineView.stopMusic();
        animation.stop();
    }

    private void addHighScore(int score) {
        if (highScores.size() == 0) {
            highScores.add(score);
        } else if (highScores.size() < 5) {
            highScores.add(score);
            Collections.sort(highScores);
            Collections.reverse(highScores);
        } else {
            Integer lowestHighScore = highScores.get(4);
            if (score > lowestHighScore) {
                highScores.remove(lowestHighScore);
                highScores.add(score);
                Collections.sort(highScores);
                Collections.reverse(highScores);
            }
        }
    }

    private void addRGFrames(){
        List<RandomGeneration> randomGenerations = currentGame.getCurrentLevel().getRandomGenRules();
        for (RandomGeneration randomGeneration : randomGenerations) {
            RGFrames.add(new RandomGenFrame(this, 300, currentGame.getCurrentLevel()));
        }
    }

	private void removeOffscreenElements() {
		List<GameObject> objects = currentGame.getCurrentLevel().getGameObjects();
		if(objects.size() == 0 || objects == null) return;
		for(int i= objects.size()-1; i >= 0; i--){
			if(objects.get(i).getXPosition()> -(2*GameEngineUI.myAppWidth) || objects.get(i) == null) continue;//CHANGE THIS TO PIPE WIDTH
            gameEngineView.removeObject(objects.get(i));
            objects.remove(i);
		}
	}

	@Override
	public void modifyScore(int score) {
		int prevScore = currentGame.getCurrentLevel().getScore();
		int currScore = prevScore+score;
		currentGame.getCurrentLevel().setScore(currScore);
	}
	
	private void setScrolling() throws ScrollTypeNotFoundException{
		ScrollType gameScroll = currentGame.getCurrentLevel().getScrollType();
		String classPath = "gameengine.scrolling." + gameScroll.getScrollTypeName();
		Object[] parameters = new Object[]{gameScroll.getDirections().get(0), gameScroll.getScrollSpeed(), 
											gameEngineView.getScreenWidth(), gameEngineView.getScreenHeight()};
		Class<?>[] parameterTypes = new Class<?>[]{Direction.class, double.class, double.class, double.class};
		try {
			gameScrolling = (Scrolling) ReflectionUtil.getInstance(classPath, parameters, parameterTypes);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw (new ScrollTypeNotFoundException());
		}
	}
}