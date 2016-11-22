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

	private Scene myScene;
	private Level myLevel;
	private ScrollerController scrollerController;

	private MovementInterface movementInterface;
	
	public GameEngineUI(MovementInterface movementInterface) {
//		this.myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		this.movementInterface = movementInterface;
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}
	private String myGameFileLocation;
	private String myLevelFileLocation;
	private IToolbar myToolbar;
	private IGameScreen myGameScreen;
	private boolean isPaused;
	private MediaPlayer myMediaPlayer;

//	public GameEngineUI(Level level, MovementInterface movementInterface) {
	public Scene setLevel(Level level){
		myLevel = level;
		
		//myScene.getStylesheets().add(DEFAULT_RESOURCE_PACKAGE + STYLESHEET);
		
		//TODO: Instantiate the proper ScrollerController depending on game type, right now ScrollerController is abstract
		// All of the instantiable scrollercontrollers are in gameengine.controller package
		//scrollerController = new ScrollerController();
		//scrollerController.setScene(myScene);
		setUpKeystrokeListeners();
		setBackgroundImage("Sprite/bird2.gif");
		setMusic("FlappyBirdThemeSong.mp3");
		return myScene;
	}
	
	public ScrollerController getScrollerController(){
		return scrollerController;
	}

	public Scene getScene() {
		return myScene;
	}
	
	public void update(Level level) {
		myLevel = level;
		myGameScreen.update(level);
	}
	
	public void setMusic(String musicFilename) {
		URL resource = getClass().getClassLoader().getResource(musicFilename);
		myMediaPlayer = new MediaPlayer(new Media(resource.toString()));
		myMediaPlayer.play();
	}
	
	public void setBackgroundImage(String imageFile) {
		myGameScreen.setBackgroundImage(imageFile);
	}
	
	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		root.setTop(makeToolbar());
		root.setCenter(makeGameScreen());
		return root;
	}
	
	private Node makeToolbar() {
		myToolbar = new Toolbar(event -> loadGame(), event -> loadLevel(), event -> pause(), event -> reset());
		return myToolbar.getToolbar();
	}
	
	private Node makeGameScreen() {
		myGameScreen = new GameScreen();
		return myGameScreen.getScreen();
	}
	
	private void loadGame() {
		FileChooser gameChooser = new FileChooser();
		gameChooser.setTitle("Open Game File");
		//gameChooser.setInitialDirectory(getInitialDirectory());
		File gameFile = gameChooser.showOpenDialog(new Stage());
		myGameFileLocation = gameFile.getAbsolutePath();
		System.out.println(myGameFileLocation);
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
    		myToolbar.resume();
    		myMediaPlayer.play();
    	} else {
    		isPaused = true;
    		myToolbar.pause();
    		myMediaPlayer.pause();
    	}
    }
    
    private void reset() {
    	System.out.println("reset");
	}
	
	private void setUpKeystrokeListeners(){
		this.myScene.setOnKeyReleased(event -> {
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
	      	  myGameScreen.update(myLevel);
	       });
	}

}
