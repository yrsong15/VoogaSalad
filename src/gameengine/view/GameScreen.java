/**
 * 
 */
package gameengine.view;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import objects.GameObject;
import objects.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Noel Moon (nm142)
 *
 *
 * @citations http://stackoverflow.com/questions/9738146/javafx-how-to-set-scene-background-image
 */
public class GameScreen {
	public static final double screenWidth = GameEngineUI.myAppWidth;
	public static final double screenHeight = GameEngineUI.myAppHeight - 100;

	private Pane myScreen;
    private Map<GameObject, ImageView> gameObjectImageViewMap;

	public GameScreen() {
		myScreen = new Pane();
		myScreen.setMaxSize(screenWidth, screenHeight);
        gameObjectImageViewMap = new HashMap<GameObject, ImageView>();
	}
	
	public Pane getScreen() {
		return myScreen;
	}
	
	public double getScreenHeight() {
		return screenHeight;
	}
	
	public void setBackgroundImage(String imageFile) {
		BackgroundImage bi = new BackgroundImage(new Image(getClass().getClassLoader().getResourceAsStream(imageFile), 
				screenWidth, screenHeight, false, true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		        BackgroundSize.DEFAULT);
		myScreen.setBackground(new Background(bi));
		
	}

	public void init(Level level){
        for(GameObject gameObject : level.getGameObjects()){
            addGameObject(gameObject);
        }
    }

    public void removeObject(GameObject object){
        myScreen.getChildren().remove(object);
        gameObjectImageViewMap.remove(object);
    }

	public void update(Level level) {
			for (GameObject object : level.getGameObjects()) {
                if(gameObjectImageViewMap.containsKey(object)){
                    gameObjectImageViewMap.get(object).relocate(object.getXPosition(), object.getYPosition());
                }else{
                    addGameObject(object);
                }
			}
		}

    public void reset(){
        gameObjectImageViewMap.clear();
        myScreen.getChildren().clear();
    }
	
	private void addGameObject(GameObject object) {
		if(object.getImageFileName()==null) return;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("Sprite/"+object.getImageFileName()));
		ImageView iv = new ImageView(image);
		iv.setFitHeight(object.getHeight());
		iv.setFitWidth(object.getWidth());
		iv.setX(object.getXPosition());
		iv.setY(object.getYPosition());
		myScreen.getChildren().add(iv);
        gameObjectImageViewMap.put(object, iv);
    }
}
