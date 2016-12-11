package gameengine.view;
import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import objects.ClientGame;
import objects.ClientGameObject;
import objects.GameObject;

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
        BackgroundImage bi = new BackgroundImage(
                new Image(getClass().getClassLoader().getResourceAsStream(imageFile), screenWidth, screenHeight, false,
                        true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        myScreen.setBackground(new Background(bi));
    }
    //lol eric did this for testing, lmk if you need me to change it back caus ethis is sllo as shit
    public void init(ClientGame game) {
        for (ClientGameObject object : game.getAllGameObjects()) {
            addGameObject(object);
        }
    }
    public void removeObject(GameObject object) {
        myScreen.getChildren().remove(object);
        myScreen.getChildren().remove(gameObjectImageViewMap.get(object));
        gameObjectImageViewMap.remove(object);
    }
    // public void update(Level level) {
    // for (GameObject object : level.getAllGameObjects()) {
    // if (gameObjectImageViewMap.containsKey(object)) {
    // gameObjectImageViewMap.get(object).relocate(object.getXPosition(),
    // object.getYPosition());
    // } else {
    // addGameObject(object);
    // }
    // }
    // }
    public void update(ClientGame game) {
        myScreen.getChildren().clear();
        init(game);
    }
    public void reset() {
        gameObjectImageViewMap.clear();
        myScreen.getChildren().clear();
    }
    private void addGameObject(ClientGameObject object) {
        if (object.getImageFileName() == null)
            return;
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("Sprite/" + object.getImageFileName()));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(object.getHeight());
        iv.setFitWidth(object.getWidth());
        iv.setX(object.getXPosition());
        iv.setY(object.getYPosition());
        iv.setRotationAxis(Rotate.Y_AXIS);
        if(object.getDirection() == null){
            object.setDirection(Direction.RIGHT);
        }
        if(object.getDirection().equals(Direction.LEFT)){
            iv.setRotate(180);
        }else{
            iv.setRotate(0);
        }
        myScreen.getChildren().add(iv);
    }
}