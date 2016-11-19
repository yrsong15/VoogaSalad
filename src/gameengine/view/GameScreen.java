/**
 * 
 */
package gameengine.view;

import gameengine.view.interfaces.IGameScreen;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * @author Noel Moon (nm142)
 *
 */
public class GameScreen implements IGameScreen {

	private Pane myScreen;
	
	public GameScreen() {
		myScreen = new Pane();
		myScreen.setMaxSize(GameEngineUI.myAppWidth, GameEngineUI.myAppHeight - 75);
		Rectangle a = new Rectangle(0, 0, 50, 50);
		myScreen.getChildren().add(a);
		myScreen.getChildren().add(new Circle(GameEngineUI.myAppWidth/2, GameEngineUI.myAppHeight/2, 30));
	}
	
	public Pane getScreen() {
		return myScreen;
	}
}
