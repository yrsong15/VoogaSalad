package gameengine.view;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import frontend.util.FileOpener;
import gameengine.controller.MovementManager;
import gameengine.controller.ScrollerController;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.controller.interfaces.GameHandler;
import gameengine.network.client.ClientMain;
import gameengine.network.server.UDPHandler;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.ScoreScreen;
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
import objects.ClientGame;
import objects.Game;
import objects.GameObject;
import objects.Level;
import objects.Player;
import objects.interfaces.ILevelInfo;
import utils.ResourceReader;
import xml.XMLSerializer;
/**
 * @author Noel Moon (nm142), Soravit, Eric Song (ess42), Ray Song
 *
 */
public class GameEngineUI implements UDPHandler, IGameEngineUI{
	public static final double myAppWidth = 700;
	public static final double myAppHeight = 775;
	public static final String RESOURCE_FILENAME = "GameEngineUI";
	private static final String EDITOR_SPLASH_STYLE = "gameEditorSplash.css";
	private ResourceBundle myResources;
	private Scene scene;
	private ScrollerController scrollerController;
	private ErrorMessage myErrorMessage;
	private String myLevelFileLocation;
	private Toolbar toolbar;
	private Node toolbarHBox;
	private HUD myHUD;
	private GameScreen gameScreen;
	private MediaPlayer mediaPlayer;
	private Map<KeyCode, Player> playerMappings = new HashMap<>();
	private Map<KeyCode, Method> keyMappings = new HashMap<KeyCode, Method>();
	private Map<String, Method> methodMappings = new HashMap<>();
	private Map<KeyCode, Boolean> keyPressed = new HashMap<>();
	private EventHandler<ActionEvent> resetEvent;
	private Timeline animation;
	private ClientMain clientMain;
	private Stage endGameStage;
	private ClientGame currentGame;
	private XMLSerializer mySerializer;
	private List<Player> clientPlayerList;
	private boolean isPaused,isMuted;
	private int currLevel;

	public GameEngineUI(XMLSerializer mySerializer, 
			EventHandler<ActionEvent> resetEvent, String serverName) {
		this.myResources = ResourceBundle.getBundle(RESOURCE_FILENAME, Locale.getDefault());
		this.myErrorMessage = new ErrorMessage();
		this.resetEvent = resetEvent;
		this.scene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		scene.getStylesheets().add(EDITOR_SPLASH_STYLE);
//		controlInterface = new ClientMain(serverName, 9090, -1, this);
		clientMain = new ClientMain(serverName, 9090, -1, this);
		this.mySerializer = mySerializer;
		setUpMethodMappings();
	}

	public void initLevel(Map<Long, List<Player>> playerMapping) {
		if (currentGame.getMusicFilePath() != null) {
			playMusic(currentGame.getMusicFilePath());
		}
		if (currentGame.getBackgroundFilePath() != null && currentGame.getBackgroundObject()==null) {
			setBackgroundImage(currentGame.getBackgroundFilePath());
		}
		gameScreen.reset();
		gameScreen.init(currentGame);
		myHUD.resetTimer();
		System.out.println(playerMapping.keySet().size());
		clientPlayerList = playerMapping.get(clientMain.getID());
		for(Player player : clientPlayerList) {
			mapKeys(player, player.getControls());
		}
	}

	public Scene getScene() {
		return scene;
	}

	public void update() {
		if (currLevel != currentGame.getLevel()){
			pause();
			makeLevelScreen(currentGame.getHighScores(), currentGame.getLevel(), currentGame.getScores(), this);
			currLevel = currentGame.getLevel();
		}
		gameScreen.update(currentGame);
		myHUD.update(currentGame.getScores());
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
			System.out.println(myResources.getString("MusicFileError"));
		}
	}

	public void setBackgroundImage(String imageFile) {
		try {
			gameScreen.setBackgroundImage(imageFile);
		} catch (Exception e) {
			myErrorMessage.showError(myResources.getString("BackgroundImageFileError"));
		}
	}
	private void checkKeyPressed() throws InvocationTargetException, IllegalAccessException {
		for(KeyCode key : keyPressed.keySet()){
			if(keyPressed.get(key).equals(true)){
				Player player = playerMappings.get(key);
				keyMappings.get(key).invoke(clientMain, player.getMainChar(), Double.parseDouble(player.getMainChar().getProperty("movespeed")));
			}
		}
	}

	public void mapKeys(Player player, Map<KeyCode, String> mappings) {
		for(KeyCode key : mappings.keySet()){
			playerMappings.put(key, player);
		}
		mapKeysToMethods(mappings);
		setUpKeystrokeListeners();
	}

	public void setupKeyFrameAndTimeline(double delay) {
		KeyFrame frame = new KeyFrame(Duration.millis(delay), e -> {
			try {
				update();
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
//		HighScoreScreen splash = new HighScoreScreen(currentGame, new ArrayList<Integer>(),
//				commandInterface);
//		if (endGameStage == null) {
//			endGameStage = new Stage();
//		}
//		endGameStage.setScene(splash.getScene());
//		endGameStage.getScene().getStylesheets().add(EDITOR_SPLASH_STYLE);
//		endGameStage.setTitle("GAME OVER");
//		endGameStage.show();
	}

	public void saveGame(){
		FileOpener chooser = new FileOpener();
		chooser.saveFile(myResources.getString("XML"), myResources.getString("data"),
				mySerializer.serializeClientGame(currentGame), myResources.getString("DefaultGameTitle"));
	}

	public void stop() {
		stopMusic();
		animation.stop();
	}

	public void stopMusic() {
		if (currentGame.getMusicFilePath() != null) {
			mediaPlayer.stop();
		}
	}

	public void resetGameScreen() {
		gameScreen.reset();
		myHUD.resetTimer();
	}

	private void setUpMethodMappings() {
		try {
			ResourceReader resources = new ResourceReader("Controls");
			Iterator<String> keys = resources.getKeys();
			while (keys.hasNext()) {
				String key = keys.next();
				methodMappings.put(key, clientMain.getClass().getDeclaredMethod(resources.getResource(key),
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
		vb.setFillWidth(true);
		vb.getChildren().addAll(makeToolbar(), makeHUD());
	//	root.setTop(vb);
		root.setCenter(makeGameScreen());
		root.setTop(vb);
		return root;
	}

	private Node makeToolbar() {
		toolbar = new Toolbar(myResources, event -> loadLevel(), event -> pause(), resetEvent,
				event -> mute(), event -> saveGame());
		toolbarHBox = toolbar.getToolbar();
		return toolbarHBox;
	}
	
	public Node getToolbar(){
		return toolbarHBox;
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

	public void pause() {
		if (isPaused) {
			toolbar.resume();
			animation.play();
		} else {
			toolbar.pause();
			animation.stop();
		}
		clientMain.pause();
		stopMusic();
		isPaused = !isPaused;
	}

	private void setUpKeystrokeListeners() {
		this.scene.setOnKeyPressed(event -> {
			if (keyMappings.containsKey(event.getCode())) {
				keyPressed.put(event.getCode(), true);
			}
			try {
				checkKeyPressed();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		this.scene.setOnKeyReleased(event -> {
			if (keyMappings.containsKey(event.getCode())) {
				keyPressed.put(event.getCode(), false);
			}
		});
	}

	@Override
	public void updateGame(ClientGame game) {
		if (currLevel == 0){
			currLevel = game.getLevel();
		}
		currentGame = game;
	}

	public boolean gameLoadedFromServer(){
		return currentGame != null;
	}

	@Override
	public int getCharIdx(GameObject player) {
		for(int i = 0; i < clientPlayerList.size(); i++){
			if(clientPlayerList.get(i).getMainChar() == player) return i;
		}
		return -1;
	}

	public void makeLevelScreen(List<Integer> highScores, int time, Map<Long,
			Integer> scoreMapping, IGameEngineUI iGameEngine){
		ScoreScreen myLevelScreen = new LevelScreen(highScores, time, scoreMapping, iGameEngine);
		Stage myLevelStage = new Stage();
		myLevelStage.setTitle(myLevelScreen.getStageTitle());
		myLevelStage.setScene(myLevelScreen.getScene());
		myLevelStage.show();
	}
}