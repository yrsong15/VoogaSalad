/**
 * 
 */
package gameengine.view;

import java.util.List;
import java.util.Set;

import gameengine.view.interfaces.IGameScreen;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import objects.GameObject;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 *
 * @citations http://stackoverflow.com/questions/9738146/javafx-how-to-set-scene-background-image
 */
public class GameScreen implements IGameScreen {
	public static final double screenWidth = GameEngineUI.myAppWidth;
	public static final double screenHeight = GameEngineUI.myAppHeight - 75;

	private Pane myScreen;
	private Level myLevel;
	private List<GameObject> myGameObjects;
	
	private Rectangle square = new Rectangle(0, 0, 50, 50);
	private Circle circle = new Circle(GameEngineUI.myAppWidth/2, GameEngineUI.myAppHeight/2, 30);
	
	public GameScreen(Level level) {
		myScreen = new Pane();
		myScreen.setMaxSize(screenWidth, screenHeight);
		//myScreen.getChildren().addAll(square, circle);
		myLevel = level;
		myGameObjects = level.getGameObjects();
		init();
	}
	
	public Pane getScreen() {
		return myScreen;
	}
	
	@Override
	public void setBackgroundImage(String imageFile) {
		BackgroundImage bi = new BackgroundImage(new Image(getClass().getClassLoader().getResourceAsStream(imageFile), 
				screenWidth, screenHeight, false, true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		        BackgroundSize.DEFAULT);
		myScreen.setBackground(new Background(bi));
		
	}
	
	private void init() {
		for (GameObject object : myGameObjects) {
			Image image = new Image(getClass().getClassLoader().getResourceAsStream("Sprite/"+object.getImageFileName()));
			ImageView iv = new ImageView(image);
			iv.setFitHeight(object.getHeight());
			iv.setFitWidth(object.getWidth());
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
