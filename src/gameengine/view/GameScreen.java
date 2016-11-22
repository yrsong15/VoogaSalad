/**
 * 
 */
package gameengine.view;

import java.util.ArrayList;
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

	public GameScreen() {
		myScreen = new Pane();
		myScreen.setMaxSize(screenWidth, screenHeight);
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

	@Override
	public void update(Level level) {
		myScreen.getChildren().clear();
			for (GameObject object : level.getGameObjects()) {
				addGameObject(object);
			}
		}
	
	private void addGameObject(GameObject object) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("Sprite/"+object.getImageFileName()));
		ImageView iv = new ImageView(image);
		iv.setFitHeight(object.getHeight());
		iv.setFitWidth(object.getWidth());
		iv.setX(object.getXPosition());
		iv.setY(object.getYPosition());
		myScreen.getChildren().add(iv);
	}
	
}
