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
	private Level myLevel;
	private List<GameObject> myGameObjects;
	private List<ImageView> imageViews;
	private boolean updated;
	
	public GameScreen() {
		myScreen = new Pane();
		myScreen.setMaxSize(screenWidth, screenHeight);
		imageViews = new ArrayList<ImageView>();
		updated = false;
	}
	
	@Override
	public void setLevel(Level level){
		myLevel = level;
		myGameObjects = level.getGameObjects();

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
		//TO-DO: need to figure out why imageViews causing memory leak
		myScreen.getChildren().clear();
		if (!updated){
			for (GameObject object : level.getGameObjects()) {
				addGameObject(object);
			}
			updated = true;
		}
		else{
			addBackOld();
		}
		
		
/*		previous code
 * myScreen.getChildren().clear();
		for (GameObject object : level.getGameObjects()) {
			addGameObject(object);
		}*/
		
	}
	//temporary fix
	private void addBackOld(){
		List<GameObject> objects = myLevel.getGameObjects();
		for (int i = 0; i < imageViews.size(); i++){
			ImageView iv = imageViews.get(i);
			iv.setX(objects.get(i).getXPosition());
			iv.setY(objects.get(i).getYPosition());
			myScreen.getChildren().add(iv);
		}
	}
	
	private void addGameObject(GameObject object) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("Sprite/"+object.getImageFileName()));
		ImageView iv = new ImageView(image);
		iv.setFitHeight(object.getHeight());
		iv.setFitWidth(object.getWidth());
		iv.setX(object.getXPosition());
		iv.setY(object.getYPosition());
		imageViews.add(iv);  //temporary fix
		myScreen.getChildren().add(iv);
	}
	
}
