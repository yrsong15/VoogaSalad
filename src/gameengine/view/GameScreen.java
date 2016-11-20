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
	
	private Rectangle square = new Rectangle(0, 0, 50, 50);
	private Circle circle = new Circle(GameEngineUI.myAppWidth/2, GameEngineUI.myAppHeight/2, 30);
	
	public GameScreen() {
		myScreen = new Pane();
		myScreen.setMaxSize(GameEngineUI.myAppWidth, GameEngineUI.myAppHeight - 75);
		myScreen.getChildren().addAll(square, circle);
	}
	
	public Pane getScreen() {
		return myScreen;
	}
	
	public void addObject() {
		
	}

	@Override
	public void update() {
		square.setX(square.getX()+5);
		circle.setCenterX(circle.getCenterX()+50);
	}
}
