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
	
    public static final double myAppWidth = 150;
	public static final double myAppHeight = 150;

	private Scene myScene;
	
	public GameEngineUI() {
		System.out.println("hey");
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}

	public Scene getScene() {
		return myScene;
	}
	
	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		root.setCenter(new Label("hey"));
		//root.setTop(makeToolbar());
		//root.setCenter(new Pane());
		root.setId("root");
		return root;
	}
	
	private Node makeToolbar() {
		IToolbar tb = new Toolbar();
		return tb.getToolbar();
	}
	

}
