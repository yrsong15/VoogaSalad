/**
 * 
 */
package gameengine.view;

import gameengine.controller.ScrollerController;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.IGameScreen;
import gameengine.view.interfaces.IToolbar;
import gameengine.view.interfaces.MovementInterface;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

/**
 * @author Noel Moon (nm142)
 *
 */
public class GameEngineUI implements IGameEngineUI {
	
    public static final double myAppWidth = 700;
	public static final double myAppHeight = 775;

	private Scene myScene;
	private ScrollerController scrollerController;
	private MovementInterface movementInterface;
	
	public GameEngineUI(MovementInterface movementInterface) {
		this.myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		this.movementInterface = movementInterface;

	}
	
	public ScrollerController getScrollerController(){
		return scrollerController;
	}

	public Scene getScene() {
		return myScene;
	}
	
	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		root.setTop(makeToolbar());
		root.setCenter(makeGameScreen());
		return root;
	}
	
	private Node makeToolbar() {
		IToolbar tb = new Toolbar();
		return tb.getToolbar();
	}
	
	private Node makeGameScreen() {
		IGameScreen screen = new GameScreen();
		return screen.getScreen();
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
	       });
	}

}
