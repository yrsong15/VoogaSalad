/**
 * 
 */
package gameengine.view;

import gameengine.controller.ScrollerController;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.IGameScreen;
import gameengine.view.interfaces.IToolbar;
import javafx.scene.Node;
import javafx.scene.Scene;
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
	
	public GameEngineUI() {
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		
		//TODO: Instantiate the proper ScrollerController depending on game type, right now ScrollerController is abstract
		// All of the instantiable scrollercontrollers are in gameengine.controller package
		scrollerController = new ScrollerController();
		scrollerController.setScene(myScene);
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

}
