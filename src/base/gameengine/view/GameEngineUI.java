/**
 * 
 */
package base.gameengine.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @author Noel Moon (nm142)
 *
 */
public class GameEngineUI implements IGameEngineUI {
	
    public static final double myAppWidth = 750;
	public static final double myAppHeight = 750;

	private Scene myScene;
	
	public GameEngineUI() {
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		//root.setTop(makeToolbar());
		root.setCenter(new Pane());
		return root;
	}
	
	/*
	private Toolbar makeToolbar() {
		
	}
	*/

}
