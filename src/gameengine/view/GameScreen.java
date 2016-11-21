/**
 * 
 */
package gameengine.view;

import java.util.List;
import java.util.Set;

import gameengine.view.interfaces.IGameScreen;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import objects.GameObject;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 */
public class GameScreen implements IGameScreen {

	private Pane myScreen;
	private Level myLevel;
	private List<GameObject> myGameObjects;
	
	private Rectangle square = new Rectangle(0, 0, 50, 50);
	private Circle circle = new Circle(GameEngineUI.myAppWidth/2, GameEngineUI.myAppHeight/2, 30);
	
	public GameScreen(Level level) {
		myScreen = new Pane();
		myScreen.setMaxSize(GameEngineUI.myAppWidth, GameEngineUI.myAppHeight - 75);
		//myScreen.getChildren().addAll(square, circle);
		myLevel = level;
		myGameObjects = level.getGameObjects();
		init();
	}
	
	public Pane getScreen() {
		return myScreen;
	}
	
	public void addObject() {
		
	}
	
	private void init() {
		for (GameObject object : myGameObjects) {
			System.out.println(object.getImageFileName());
			Image image = new Image(getClass().getClassLoader().getResourceAsStream(object.getImageFileName()));
			ImageView iv = new ImageView(image);
			iv.setX(object.getXPosition());
			iv.setY(object.getYPosition());
			myScreen.getChildren().add(iv);
		}
	}

	@Override
	public void update(Level level) {
		for (GameObject object : level.getGameObjects()) {
			Image image = new Image(getClass().getClassLoader().getResourceAsStream(object.getImageFileName()));
			ImageView iv = new ImageView(image);
			iv.setX(object.getXPosition());
			iv.setY(object.getYPosition());
			myScreen.getChildren().add(iv);
		}
		
		square.setX(square.getX()+5);
		circle.setCenterX(circle.getCenterX()+5);
	}
}
