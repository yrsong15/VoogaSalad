/**
 * 
 */
package gameengine.view;

import java.io.File;

import gameengine.controller.ScrollerController;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.IGameScreen;
import gameengine.view.interfaces.IToolbar;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
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
	private String myGameFileLocation;
	private String myLevelFileLocation;
	private IToolbar myToolbar;
	private IGameScreen myGameScreen;
	private boolean isPaused;
	
	public GameEngineUI(Level level) {
		myLevel = level;
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		//myScene.getStylesheets().add(DEFAULT_RESOURCE_PACKAGE + STYLESHEET);
		
		//TODO: Instantiate the proper ScrollerController depending on game type, right now ScrollerController is abstract
		// All of the instantiable scrollercontrollers are in gameengine.controller package
		//scrollerController = new ScrollerController();
		//scrollerController.setScene(myScene);
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
		myGameScreen = new GameScreen(myLevel);
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
    	} else {
    		isPaused = true;
    		myToolbar.pause();
    	}
    }
    
    private void reset() {
    	System.out.println("reset");
	}

}
