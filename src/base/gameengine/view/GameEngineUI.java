/**
 * 
 */
package base.gameengine.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * @author Noel Moon (nm142)
 *
 */
public class GameEngineUI implements IGameEngineUI {
	
    public static final double myAppWidth = 700;
	public static final double myAppHeight = 775;

	private Scene myScene;
	
	public GameEngineUI() {
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
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
