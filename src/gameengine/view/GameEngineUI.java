/**
 * 
 */
package gameengine.view;

import java.io.File;
import java.net.URL;

import gameengine.controller.ScrollerController;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.IGameScreen;
import gameengine.view.interfaces.IToolbar;
import gameengine.view.interfaces.MovementInterface;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 */
public class GameEngineUI implements IGameEngineUI {
	
    public static final double myAppWidth = 700;
	public static final double myAppHeight = 775;
	public static final String DEFAULT_RESOURCE_PACKAGE = "css/";
    public static final String STYLESHEET = "default.css";

	private Scene scene;
	private Level level;
	private ScrollerController scrollerController;

	private MovementInterface movementInterface;
	
	public GameEngineUI(MovementInterface movementInterface) {
		this.movementInterface = movementInterface;
		scene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}
	private String myGameFileLocation;
	private String myLevelFileLocation;
	private IToolbar toolbar;
	private IGameScreen gameScreen;
	private boolean isPaused;
	private MediaPlayer mediaPlayer;
	
    public static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    
    

//	public GameEngineUI(Level level, MovementInterface movementInterface) {
	public Scene setLevel(Level level){
		this.level = level;
		//scene.getStylesheets().add(DEFAULT_RESOURCE_PACKAGE + STYLESHEET);
		
		//TODO: Instantiate the proper ScrollerController depending on game type, right now ScrollerController is abstract
		// All of the instantiable scrollercontrollers are in gameengine.controller package
		//scrollerController = new ScrollerController();
		//scrollerController.setScene(scene);

		setUpKeystrokeListeners();
		setBackgroundImage("Sprite/bird2.gif");
		setMusic("FlappyBirdThemeSong.mp3");
		return scene;
	}
	
	public ScrollerController getScrollerController(){
		return scrollerController;
	}

	public Scene getScene() {
		return scene;
	}
	
	public void update(Level level) {
		this.level = level;
		gameScreen.update(level);
	}
	
	public void setMusic(String musicFilename) {
		URL resource = getClass().getClassLoader().getResource(musicFilename);
		mediaPlayer = new MediaPlayer(new Media(resource.toString()));
		mediaPlayer.play();
	}
	
	public void setBackgroundImage(String imageFile) {
		gameScreen.setBackgroundImage(imageFile);
	}
	
	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		root.setTop(makeToolbar());
		root.setCenter(makeGameScreen());
		return root;
	}
	
	private Node makeToolbar() {
		toolbar = new Toolbar(event -> loadGame(), event -> loadLevel(), event -> pause(), event -> reset());
		return toolbar.getToolbar();
	}
	
	private Node makeGameScreen() {
		gameScreen = new GameScreen();
		return gameScreen.getScreen();
	}
	
	private void loadGame() {
		FileChooser gameChooser = new FileChooser();
		gameChooser.setTitle("Open Game File");
		//gameChooser.setInitialDirectory(getInitialDirectory());
		File gameFile = gameChooser.showOpenDialog(new Stage());
	}
	
	private void loadLevel() {
		FileChooser levelChooser = new FileChooser();
		levelChooser.setTitle("Open Level File");
		//gameChooser.setInitialDirectory(getInitialDirectory());
		File levelFile = levelChooser.showOpenDialog(new Stage());
		myLevelFileLocation = levelFile.getAbsolutePath();
		System.out.println(myLevelFileLocation);
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
    
    private void reset() {
    	System.out.println("reset");
	}
	
	private void setUpKeystrokeListeners(){
		this.scene.setOnKeyReleased(event -> {
	      	  if (event.getCode() == KeyCode.UP){
	      		  movementInterface.UPKeyPressed();
	      	  }
	      	  else if (event.getCode() == KeyCode.DOWN){
	      		movementInterface.DOWNKeyPressed();
	       	  }
	      	  else if (event.getCode() == KeyCode.LEFT){
	      		movementInterface.LEFTKeyPressed();
	          }
	      	  else if (event.getCode() == KeyCode.RIGHT){
	      		movementInterface.RIGHTKeyPressed();
	          }
	      	  gameScreen.update(level);
	       });
	}

}
