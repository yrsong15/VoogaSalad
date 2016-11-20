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

/**
 * @author Noel Moon (nm142)
 *
 */
public class GameEngineUI implements IGameEngineUI {
	
    public static final double myAppWidth = 700;
	public static final double myAppHeight = 775;

	private Scene myScene;
	private ScrollerController scrollerController;
	private String myGameFileLocation;
	private IToolbar myToolbar;
	private IGameScreen myGameScreen;
	private boolean isPaused;
	
	public GameEngineUI() {
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		
		//Just a test method
		makeControls();
		
		//TODO: Instantiate the proper ScrollerController depending on game type, right now ScrollerController is abstract
		// All of the instantiable scrollercontrollers are in gameengine.controller package
		//scrollerController = new ScrollerController();
		//scrollerController.setScene(myScene);
	}
	
	private void makeControls() {
		this.myScene.setOnKeyPressed(event -> {
	      	  if (event.getCode() == KeyCode.RIGHT){
	      		  update();
	      	  }
	       });
	}
	
	public ScrollerController getScrollerController(){
		return scrollerController;
	}

	public Scene getScene() {
		return myScene;
	}
	
	public void update() {
		myGameScreen.update();
	}
	
	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		root.setTop(makeToolbar());
		root.setCenter(makeGameScreen());
		return root;
	}
	
	private Node makeToolbar() {
		myToolbar = new Toolbar(event -> loadGame(), event -> pause(), event -> reset());
		return myToolbar.getToolbar();
	}
	
	private Node makeGameScreen() {
		myGameScreen = new GameScreen();
		return myGameScreen.getScreen();
	}
	
	private void loadGame() {
		FileChooser gameChooser = new FileChooser();
		gameChooser.setTitle("Open Resource File");
		//gameChooser.setInitialDirectory(getInitialDirectory());
		File gameFile = gameChooser.showOpenDialog(new Stage());
		myGameFileLocation = gameFile.getAbsolutePath();
		System.out.println(myGameFileLocation);
	}
    
    private void pause() {
    	if (isPaused) {
    		isPaused = false;
    		System.out.println("resume");
    		myToolbar.resume();
    	} else {
    		isPaused = true;
    		System.out.println("pause");
    		myToolbar.pause();
    	}
    }
    
    private void reset() {
    	System.out.println("reset");
	}

}
