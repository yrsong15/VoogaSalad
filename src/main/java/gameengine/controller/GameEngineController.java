package gameengine.controller;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import com.sun.javafx.scene.traversal.Direction;
import exception.ScrollDirectionNotFoundException;
import exception.ScrollTypeNotFoundException;
import gameengine.controller.SingletonBoundaryChecker.IntersectionAmount;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.GameHandler;
import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.*;
import gameengine.model.interfaces.Scrolling;
import gameengine.view.GameEngineUI;
import gameengine.view.HighScoreScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.*;
import utils.ReflectionUtil;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon
 */


public class GameEngineController implements RuleActionHandler, RGInterface, CommandInterface, GameHandler {
    public static final double FRAMES_PER_SECOND = 60;
    public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1 / FRAMES_PER_SECOND;
    private static final String EDITOR_SPLASH_STYLE = "gameEditorSplash.css";

	private List<RandomGenFrame> randomlyGeneratedFrames;
    private List<Integer> highScores;
    private String xmlData;
	private GameParser parser;
	private CollisionChecker collisionChecker;
	private Game currentGame;
	private GameEngineUI gameEngineView;
	private Timeline animation;
	private MovementManager gameMovement;
	private Stage endGameStage;
	private Map<GameObject, Position> mainCharImprints;

	
	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker(this);
		randomlyGeneratedFrames = new ArrayList<>();
	    highScores = new ArrayList<>();
	    gameEngineView = new GameEngineUI(event -> reset());
	    mainCharImprints = new HashMap<>();
    }

    public Scene getScene() {
        return gameEngineView.getScene();
    }

	public boolean startGame(String xmlData) {
        this.xmlData = xmlData;
        this.mainCharImprint = new Position();
		currentGame = parser.convertXMLtoGame(xmlData);
        if(currentGame.getCurrentLevel() == null || currentGame.getCurrentLevel().getPlayers().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Cannot start game.");
            alert.setContentText("You must create a level with a main character to start a game.");
            alert.showAndWait();
            return false;
        }
        gameMovement = new MovementManager(currentGame.getCurrentLevel(), GameEngineUI.myAppWidth, GameEngineUI.myAppHeight);
		gameEngineView.setControlInterface(gameMovement.getControlInterface());
        gameEngineView.initLevel(currentGame.getCurrentLevel());
		for(Player player : currentGame.getPlayers()){
            gameEngineView.mapKeys(player, player.getControls());
        }
        addRGFrames();
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
	public void updateGame() throws InvocationTargetException, IllegalAccessException {
	    gameEngineView.checkKeyPressed();
		Level currLevel = currentGame.getCurrentLevel();
		for(GameObject mainChar : currLevel.getPlayers()) {
            Position position = new Position();
            position.setPosition(mainChar.getXPosition(), mainChar.getYPosition());
            mainCharImprints.put(mainChar, position);
            mainChar.checkPlatformStatus();
        }
		gameMovement.runActions();
        if(currLevel.getScrollType().getScrollTypeName().equals("ForcedScrolling")) {
            removeOffscreenElements();
        }
		gameEngineView.update(currLevel);
		/*for(RandomGenFrame elem: randomlyGeneratedFrames){
            for(RandomGeneration randomGeneration : currLevel.getRandomGenRules()) {
                try {
					elem.possiblyGenerateNewFrame(100, randomGeneration, this.getClass().getMethod("setNewBenchmark"));
				} catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException
						| NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		}*/
        collisionChecker.checkCollisions(currLevel.getPlayers(), currLevel.getGameObjects());
        collisionChecker.checkCollisions(currLevel.getProjectiles(), currLevel.getGameObjects());
        checkProjectileDistance();
        LossChecker.checkLossConditions(this,
				 		currLevel.getLoseConditions(), currLevel.getGameConditions());
        WinChecker.checkWinConditions(this,
				 		currLevel.getWinConditions(), currLevel.getGameConditions());
	}

    public void setNewBenchmark() {
        List<GameObject> objects = currentGame.getCurrentLevel().getGameObjects();
        for(RandomGenFrame elem: randomlyGeneratedFrames){
            elem.setNewBenchmark(new Integer((int) objects.get(objects.size() - 1).getXPosition() / 2));
        }
    }

    @Override
    public void removeObject(GameObject obj) {
        currentGame.getCurrentLevel().removeGameObject(obj);
        gameEngineView.removeObject(obj);

    }

    @Override
    public void winGame(){
        //SPLASH SCREEN

    }

    public void goNextLevel(){
        if(currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel() + 1) != null) {
            currentGame.setCurrentLevel(currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel() + 1));
        }else{
            winGame();
        }
    }

    @Override
    public void endGame() {
        animation.stop();
        addHighScore(currentGame.getCurrentLevel().getScore());
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
    
<<<<<<< HEAD
    public void resetObjectPosition(GameObject mainChar,GameObject obj){
    	double newPosition;
    	if(SingletonBoundaryChecker.getInstance().getHorizontalIntersectionAmount(mainChar, obj) == IntersectionAmount.COMPLETELY_INSIDE_X){
    		if(mainCharImprint.getY() < obj.getYPosition()){
        		newPosition = obj.getYPosition() - mainChar.getHeight();
        		mainChar.setPlatformCharacterIsOn(obj);
        	}
        	else 
        		newPosition = obj.getYPosition() + obj.getHeight();
    	}
    	else{
    		newPosition = mainCharImprint.getY();
    	}
    	
    	
    	
    	mainChar.setYPosition(newPosition);
    	mainChar.setXPosition(mainCharImprint.getX());
    }

    @Override
    public void reset() {
        gameEngineView.stopMusic();
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
            randomlyGeneratedFrames.add(new RandomGenFrame(this, 300, currentGame.getCurrentLevel()));
        }
    }

	private void removeOffscreenElements() {
		List<GameObject> objects = currentGame.getCurrentLevel().getAllGameObjects();
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

	@Override
	public void removeFromCollidedList(GameObject obj) {
		collisionChecker.manuallyRemoveFromConcurrentCollisionList(obj);
	}

	@Override
	public void removeFromCollidedList(GameObject obj) {
		collisionChecker.manuallyRemoveFromConcurrentCollisionList(obj);
	}

	@Override
	public Game getGame() {
		return currentGame;
	}
	
	private void checkProjectileDistance(){
        for(Iterator<GameObject> itr = currentGame.getCurrentLevel().getProjectiles().iterator(); itr.hasNext();){
            GameObject projectile = itr.next();
            ProjectileProperties properties = projectile.getProjectileProperties();
            if(properties.getDirection().equals(Direction.RIGHT) || properties.getDirection().equals(Direction.LEFT)){
                if(projectile.getXDistanceMoved() >= properties.getRange()){
                    removeObject(projectile);
                    itr.remove();
                }
            }else{
                if(projectile.getYDistanceMoved() >= properties.getRange()){
                    removeObject(projectile);
                    itr.remove();
                }
            }
        }
    }
}