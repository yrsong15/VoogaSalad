/**
 *
 */
package gameengine.view;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.Key;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import gameengine.controller.ScrollerController;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.IGameScreen;
import gameengine.view.interfaces.IToolbar;
import gameengine.controller.interfaces.MovementInterface;
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
import objects.Level;
import utils.ResourceReader;

/**
 * @author Noel Moon (nm142)
 *
 */
public class GameEngineUI implements IGameEngineUI {

	public static final double myAppWidth = 700;
	public static final double myAppHeight = 775;
	public static final String DEFAULT_RESOURCE_PACKAGE = "css/";
	public static final String STYLESHEET = "default.css";
	public static final String RESOURCE_FILENAME = "GameEngineUI";
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

	private ResourceBundle myResources;
	private Scene scene;
	private Level level;
	private ScrollerController scrollerController;
	private ErrorMessage myErrorMessage;
	private MovementInterface movementInterface;
	private String myGameFileLocation;
	private String myLevelFileLocation;
	private IToolbar toolbar;
	private HUD myHUD;
	private GameScreen gameScreen;
	private boolean isPaused;
	private MediaPlayer mediaPlayer;
	private Map<KeyCode, Method> keyMappings = new HashMap<KeyCode, Method>();
	private Map<String, Method> methodMappings = new HashMap<>();
	private EventHandler<ActionEvent> myResetEvent;


	public GameEngineUI(MovementInterface movementInterface, EventHandler<ActionEvent> resetEvent) {
		this.myResources = ResourceBundle.getBundle(RESOURCE_FILENAME, Locale.getDefault());
		this.myErrorMessage = new ErrorMessage();
		this.myResetEvent = resetEvent;
		this.movementInterface = movementInterface;
		this.scene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		setUpMethodMappings();
	}

	public Scene setLevel(Level level) {
		this.level = level;
		
		setUpKeystrokeListeners();
		
		return scene;
	}

	public ScrollerController getScrollerController() {
		return scrollerController;
	}

	public Scene getScene() {
		return scene;
	}
	
	public double getScreenHeight() {
		return gameScreen.screenHeight;
	}

	public void update(Level level) {
		this.level = level;
		gameScreen.update(level);
	}

	public void setMusic(String musicFileName) {
		try {
			if (mediaPlayer != null) {
				mediaPlayer.stop();
			}
			URL resource = getClass().getClassLoader().getResource(musicFileName);
			mediaPlayer = new MediaPlayer(new Media(resource.toString()));
			mediaPlayer.play();
		} catch (Exception e) {
			myErrorMessage.showError(myResources.getString("MusicFileError"));
		}
	}

	public void setBackgroundImage(String imageFile) {
		try {
			gameScreen.setBackgroundImage(imageFile);
		} catch (Exception e) {
			myErrorMessage.showError(myResources.getString("BackgroundImageFileError"));
		}
		
	}

	public void mapKeys(Map<KeyCode, String> mappings) {
		mapKeysToMethods(mappings);
		setUpKeystrokeListeners();
	}
	
	public void stopMusic() {
		try{
			mediaPlayer.stop();
		}catch (NullPointerException e){
			//System.out.println("GameEngineUI: Music was null");
		}
	}
	
	public void updateStat(String name, String value) {
		myHUD.addStat(name, value);
		myHUD.updateStats();
	}
	
	public void resetMusic() {
		mediaPlayer.stop();
		mediaPlayer.play();
	}

	private void setUpMethodMappings() {

		try {
			ResourceReader resources = new ResourceReader("Controls");
			Iterator<String> keys = resources.getKeys();
			while(keys.hasNext()){
				String key = keys.next();
				methodMappings.put(key, movementInterface.getClass().getDeclaredMethod(resources.getResource(key)));
			}
			// methodMappings.put("down",
			// movementInterface.getClass().getDeclaredMethod("moveDown"));
			// methodMappings.put("right",
			// movementInterface.getClass().getDeclaredMethod("moveRight"));
			// methodMappings.put("left",
			// movementInterface.getClass().getDeclaredMethod("moveLeft"));
			// methodMappings.put("jump",
			// movementInterface.getClass().getDeclaredMethod("jump"));
			// methodMappings.put("shoot",
			// movementInterface.getClass().getDeclaredMethod("shootProjectile"));
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
		toolbar = new Toolbar(myResources, event -> loadGame(), event -> loadLevel(), event -> pause(), myResetEvent);
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

	private void loadGame() {
		FileChooser gameChooser = new FileChooser();
		gameChooser.setTitle("Open Game File");
		File gameFile = gameChooser.showOpenDialog(new Stage());
	}

	private void loadLevel() {
		FileChooser levelChooser = new FileChooser();
		levelChooser.setTitle("Open Level File");
		File levelFile = levelChooser.showOpenDialog(new Stage());
		myLevelFileLocation = levelFile.getAbsolutePath();
		//System.out.println(myLevelFileLocation);
	}

	private void pause() {
		if (isPaused) {
			isPaused = false;
			toolbar.resume();
			mediaPlayer.play();
		} else {
			isPaused = true;
			toolbar.pause();
			mediaPlayer.pause();
		}
	}

	private void setUpKeystrokeListeners() {
		this.scene.setOnKeyReleased(event -> {
			try {
				if (keyMappings.containsKey(event.getCode())) {
					keyMappings.get(event.getCode()).invoke(movementInterface);
					gameScreen.update(level);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}
}
