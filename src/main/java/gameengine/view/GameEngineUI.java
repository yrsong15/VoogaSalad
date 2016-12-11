/**
 *
 */
package gameengine.view;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import gameengine.controller.ScrollerController;
import gameengine.controller.interfaces.ControlInterface;
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
import objects.GameObject;
import objects.Level;
import objects.Player;
import utils.ResourceReader;

/**
 * @author Noel Moon (nm142), Soravit
 *
 */
public class GameEngineUI {

	public static final double myAppWidth = 700;
	public static final double myAppHeight = 775;
	public static final String RESOURCE_FILENAME = "GameEngineUI";

	private ResourceBundle resources;
	private Scene scene;
	private Stage prefStage;
	private Level level;
	private ScrollerController scrollerController;
	private ErrorMessage errorMessage;
	private ControlInterface controlInterface;
	private Toolbar toolbar;
	private HUD hud;
	private GameScreen gameScreen;
	private MultiplayerPrefScreen multiplayerPrefScreen;
	private boolean isPaused, isMuted;
	private MediaPlayer mediaPlayer;
	private Map<KeyCode, Method> keyMappings = new HashMap<KeyCode, Method>();
	private Map<String, Method> methodMappings = new HashMap<>();
	private EventHandler<ActionEvent> resetEvent;

	public GameEngineUI(ControlInterface controlInterface, EventHandler<ActionEvent> resetEvent) {
		this.resources = ResourceBundle.getBundle(RESOURCE_FILENAME, Locale.getDefault());
		this.multiplayerPrefScreen = new MultiplayerPrefScreen(resources, event -> onlineMulti(), event -> localMulti());
		this.errorMessage = new ErrorMessage();
		this.resetEvent = resetEvent;
		this.controlInterface = controlInterface;
		this.scene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		setUpMethodMappings();
	}

	public void initLevel(Level level) {
		this.level = level;
        if(level.getMusicFilePath() != null){
            playMusic(level.getMusicFilePath());
        }
        if(level.getBackgroundFilePath() != null){
            setBackgroundImage(level.getBackgroundFilePath());
        }
        gameScreen.reset();
        gameScreen.init(level);
        hud.resetTimer();
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
		return gameScreen.getScreenWidth();
	}

	public void update(Level level) {
		this.level = level;
		gameScreen.update(level);
		hud.update(level);
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
			errorMessage.showError(resources.getString("BackgroundImageFileError"));
		}
		
	}

	public void mapKeys(Player player, Map<KeyCode, String> mappings) {
		mapKeysToMethods(mappings);
		setUpKeystrokeListeners(player);
	}
	
	public void stopMusic() {
		if(level.getMusicFilePath() != null) {
            mediaPlayer.stop();
        }
	}

	public void resetGameScreen(){
        gameScreen.reset();
        hud.resetTimer();
    }

    public void removeObject(GameObject object){
        gameScreen.removeObject(object);
    }

	private void setUpMethodMappings() {

		try {
			ResourceReader resources = new ResourceReader("Controls");
			Iterator<String> keys = resources.getKeys();
			
			while(keys.hasNext()){ 
				String key = keys.next();
				methodMappings.put(key, controlInterface.getClass().getDeclaredMethod(resources.getResource(key), GameObject.class, double.class));
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
		toolbar = new Toolbar(resources, event -> loadLevel(), event -> pause(), resetEvent, event -> mute(), event -> pref());
		return toolbar.getToolbar();
	}
	
	private Node makeHUD() {
		hud = new HUD();
		return hud.getHUD();
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
		//myLevelFileLocation = levelFile.getAbsolutePath();
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
	
	private void pref() {
		if (prefStage == null){
			prefStage = new Stage();
		}
		prefStage.setScene(multiplayerPrefScreen.getScene());
		prefStage.show();
	}
	
	public void onlineMulti() {
		multiplayerPrefScreen.onlineMulti();
		pref();
		multiplayerPrefScreen.reset();
	}
	
	public void localMulti() {
		
	}

	private void setUpKeystrokeListeners(Player player) {
		this.scene.setOnKeyPressed(event -> {
			try {
				if (keyMappings.containsKey(event.getCode())) {
						keyMappings.get(event.getCode()).invoke(controlInterface, player.getMainChar(), Double.parseDouble(player.getMainChar().getProperty("movespeed")));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}
}