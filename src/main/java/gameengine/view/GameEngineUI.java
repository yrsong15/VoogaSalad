/**
 *
 */
package gameengine.view;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import gameengine.controller.GameEngineController;
import gameengine.controller.MovementManager;
import gameengine.controller.ScrollerController;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.network.client.ClientMain;
import gameengine.network.server.UDPHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.Game;
import objects.GameObject;
import objects.Level;
import objects.Player;
import utils.ResourceReader;

/**
 * @author Noel Moon (nm142), Soravit, Eric Song (ess42), Ray Song
 *
 */
public class GameEngineUI implements UDPHandler{

	public static final double myAppWidth = 700;
	public static final double myAppHeight = 775;
	public static final String RESOURCE_FILENAME = "GameEngineUI";
	private static final String EDITOR_SPLASH_STYLE = "gameEditorSplash.css";

	private ResourceBundle myResources;
	private Scene scene;
	private Level level;
	private ScrollerController scrollerController;
	private ErrorMessage myErrorMessage;
	private String myLevelFileLocation;
	private Toolbar toolbar;
	private HUD myHUD;
	private GameScreen gameScreen;
	private boolean isPaused;
	private boolean isMuted;
	private MediaPlayer mediaPlayer;
	private Map<KeyCode, Method> keyMappings = new HashMap<KeyCode, Method>();
	private Map<String, Method> methodMappings = new HashMap<>();
	private EventHandler<ActionEvent> resetEvent;
	private Timeline animation;
	private ControlInterface controlInterface;
	private CommandInterface commandInterface;
	private Stage endGameStage;
	private Game currentGame;
	private Player mainPlayer;

	public GameEngineUI(CommandInterface commandInterface, EventHandler<ActionEvent> resetEvent) {
		this.myResources = ResourceBundle.getBundle(RESOURCE_FILENAME, Locale.getDefault());
		this.myErrorMessage = new ErrorMessage();
		this.resetEvent = resetEvent;
		this.scene = new Scene(makeRoot(), myAppWidth, myAppHeight);
//		controlInterface = new ClientMain("25.16.229.50", 9090, -1, this);
		controlInterface = new ClientMain("localhost", 9090, -1, this);
		this.commandInterface = commandInterface;
		setUpMethodMappings();
	}
	

	public void initLevel() {
		this.level = currentGame.getCurrentLevel();
		if (level.getMusicFilePath() != null) {
			playMusic(level.getMusicFilePath());
		}
		if (level.getBackgroundFilePath() != null) {
			setBackgroundImage(level.getBackgroundFilePath());
		}
		gameScreen.reset();
		gameScreen.init(level);
		myHUD.resetTimer();
	}

	public ScrollerController getScrollerController() {
		return scrollerController;
	}

	public Scene getScene() {
		return scene;
	}

	public double getScreenHeight() {
		return gameScreen.getScreenHeight();
	}

	public double getScreenWidth() {
		return gameScreen.screenWidth;
	}

	public void update(Level level) {
		this.level = level;
		gameScreen.update(level);
		myHUD.update(level);
	}

	public void playMusic(String musicFileName) {
		try {
			if (mediaPlayer != null) {
				mediaPlayer.stop();
			}
			URL resource = getClass().getClassLoader().getResource(musicFileName);
			mediaPlayer = new MediaPlayer(new Media(resource.toString()));
			if (!isMuted) {
				mediaPlayer.play();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBackgroundImage(String imageFile) {
		try {
			gameScreen.setBackgroundImage(imageFile);
		} catch (Exception e) {
			myErrorMessage.showError(myResources.getString("BackgroundImageFileError"));
		}

	}

	public void mapKeys() {
		mainPlayer = currentGame.getPlayers().get(0);
		mapKeysToMethods(mainPlayer.getControls());
		setUpKeystrokeListeners(mainPlayer);

	}

	public void setupKeyFrameAndTimeline(double delay) {
		KeyFrame frame = new KeyFrame(Duration.millis(delay), e -> {
			try {
				update(currentGame.getCurrentLevel());
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		});

		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public void endGame() {
		animation.stop();
		// HighScoreScreen splash = new
		// HighScoreScreen(currentGame.getCurrentLevel(), highScores, this);
		HighScoreScreen splash = new HighScoreScreen(currentGame.getCurrentLevel(), new ArrayList<Integer>(),
				commandInterface);
		if (endGameStage == null) {
			endGameStage = new Stage();
		}
		endGameStage.setScene(splash.getScene());
		endGameStage.getScene().getStylesheets().add(EDITOR_SPLASH_STYLE);
		endGameStage.setTitle("GAME OVER");
		endGameStage.show();
	}

	public void stop() {
		stopMusic();
		animation.stop();
	}

	public void stopMusic() {
		if (level.getMusicFilePath() != null) {
			mediaPlayer.stop();
		}
	}

	public void resetGameScreen() {
		gameScreen.reset();
		myHUD.resetTimer();
	}

	public void removeObject(GameObject object) {
		gameScreen.removeObject(object);
	}

	private void setUpMethodMappings() {

		try {
			ResourceReader resources = new ResourceReader("Controls");
			Iterator<String> keys = resources.getKeys();

			while (keys.hasNext()) {
				String key = keys.next();
				methodMappings.put(key, controlInterface.getClass().getDeclaredMethod(resources.getResource(key),
						GameObject.class, double.class));
			}
		} catch (

		NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	private void mapKeysToMethods(Map<KeyCode, String> mappings) {
		for (Map.Entry<KeyCode, String> m : mappings.entrySet()) {
			if (methodMappings.containsKey(m.getValue())) {
				keyMappings.put(m.getKey(), methodMappings.get(m.getValue()));
			}
		}
	}

	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		VBox vb = new VBox();
		vb.getChildren().addAll(makeToolbar(), makeHUD());
		root.setTop(vb);
		root.setCenter(makeGameScreen());
		return root;
	}

	private Node makeToolbar() {
		toolbar = new Toolbar(myResources, event -> loadLevel(), event -> pause(), resetEvent, event -> mute());
		return toolbar.getToolbar();
	}

	private Node makeHUD() {
		myHUD = new HUD();
		return myHUD.getHUD();
	}

	private Node makeGameScreen() {
		gameScreen = new GameScreen();
		return gameScreen.getScreen();
	}

	private void mute() {
		if (isMuted) {
			isMuted = false;
			toolbar.unmute();
			mediaPlayer.play();
			mediaPlayer.setMute(false);
		} else {
			isMuted = true;
			toolbar.mute();
			mediaPlayer.setMute(true);
		}
	}

	private void loadLevel() {
		FileChooser levelChooser = new FileChooser();
		levelChooser.setTitle("Open Level File");
		File levelFile = levelChooser.showOpenDialog(new Stage());
		myLevelFileLocation = levelFile.getAbsolutePath();
	}

	private void pause() {
		if (isPaused) {
			toolbar.resume();
//			mediaPlayer.play();
			animation.play();
		} else {
			toolbar.pause();
//			mediaPlayer.pause();
			animation.stop();
		}
		isPaused = !isPaused;
	}

	private void setUpKeystrokeListeners(Player player) {
		this.scene.setOnKeyPressed(event -> {
			try {
				if (keyMappings.containsKey(event.getCode())) {
					keyMappings.get(event.getCode()).invoke(controlInterface, player.getMainChar(),
							Double.parseDouble(player.getMainChar().getProperty("movespeed")));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}


	@Override
	public void updateGame(Game game) {
		currentGame = game;
//		System.out.println("updated game");
	}
	
	public boolean gameLoadedFromServer(){
		return currentGame!=null;
	}
}