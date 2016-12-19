// This entire file is part of my masterpiece.
// Noel Moon

package gameengine.view;

import com.sun.javafx.scene.traversal.Direction;
import gameengine.network.server.ServerMain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import objects.ClientGame;
import objects.ClientGameObject;
import objects.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this class is to manage the game screen that is part of the user interface for the game engine. This class contains the 
 * implementation details for the animation that occurs on the game screen.
 * 
 * I think this class is good design because it shows how I dealt with the design issue of heap space errors occurring with infinite scrollers.
 * I implemented the update() method so that it only visualizes the objects that need to be visualized, but it also keeps track of all of the 
 * objects. This implementation allows both finite and infinite scrollers to be visualized properly. The problem with infinite scrollers
 * was that objects would continuously be instantiated and added to the game screen, taking up a lot of memory but this implementation deals
 * with that problem by only visualizing the objects that need to be visualized. The problem with finite scrollers was that if I got rid of
 * the objects on the screen after they disappeared from the screen, then games like Mario would be unable to go back to the part of the map
 * that they were once at. I dealt with this problem by keeping track of the information of the game objects on the screen but only visualizing
 * the objects that need to be on the screen, and by doing so this saves a lot of memory.
 * 
 * @author Noel Moon (nm142)
 * @citations http://stackoverflow.com/questions/9738146/javafx-how-to-set-scene-background-image
 */
public class GameScreen {
    public static final double screenWidth = GameEngineUI.myAppWidth;
    public static final double screenHeight = GameEngineUI.myAppHeight - 100;
    
    private Pane myScreen;
    private Map<Integer, ImageView> gameObjectImageViewMap;

    public GameScreen() {
        myScreen = new Pane();
        myScreen.setMaxSize(screenWidth, screenHeight);
        gameObjectImageViewMap = new HashMap<>();
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

    public void nextLevel() {
        gameObjectImageViewMap.remove(0);
    }

    public void init(ClientGame game) {
        Map<Integer, ClientGameObject> allGameObjects = game.getAllGameObjects();
        if (game.getBackgroundObject() != null) {
            addGameObject(game.getBackgroundObject());
        }
        for (Map.Entry<Integer, ClientGameObject> entry : allGameObjects.entrySet()) {
            addGameObject(entry.getValue());
        }
    }

    public void updatePosition(ClientGameObject obj) {
        if (gameObjectImageViewMap.containsKey(obj.getID())) {
            ImageView iv = gameObjectImageViewMap.get(obj.getID());
            if (obj.getXPosition() != iv.getX() || obj.getYPosition() != iv.getY()) {
                iv.relocate(obj.getXPosition(),
                        obj.getYPosition());
            }
            if (obj.getDirection() == null) {
                obj.setDirection(Direction.RIGHT);
            }
            if (obj.getDirection().equals(Direction.LEFT)) {
                iv.setRotate(180);
            } else {
                iv.setRotate(0);
            }
        } else {
            addGameObject(obj);
        }
    }


    public void update(ClientGame game) {
        Map<Integer, ClientGameObject> allGameObjects = game.getAllGameObjects();
        if (game.getBackgroundObject() != null) {
            updatePosition(game.getBackgroundObject());
        }
        for (Map.Entry<Integer, ClientGameObject> entry : allGameObjects.entrySet()) {
            ClientGameObject object = entry.getValue();
            updatePosition(object);
        }
        for (Iterator<Integer> it = gameObjectImageViewMap.keySet().iterator(); it.hasNext(); ) {
            int ID = it.next();
            if (game.getBackgroundObject() != null && game.getBackgroundObject().getID() == ID) {
                continue;
            }
            if (!allGameObjects.containsKey(ID)) {
                myScreen.getChildren().remove(gameObjectImageViewMap.get(ID));
                it.remove();
            }
        }
    }

    public void reset() {
        gameObjectImageViewMap.clear();
        myScreen.getChildren().clear();
    }

    private void addGameObject(ClientGameObject object) {
        if (object.getImageFileName() == null) {
            if (object.getImageFileName() == null)
                return;
        }
        Image image = null;
        try {
            image = new Image(getClass().getClassLoader().getResourceAsStream("Sprite/" + object.getImageFileName()));
        } catch (NullPointerException e) {
            image = new Image(getClass().getClassLoader().getResourceAsStream(object.getImageFileName()));
        }
        ImageView iv = new ImageView(image);
        iv.setFitHeight(object.getHeight());
        iv.setFitWidth(object.getWidth());
        iv.setX(object.getXPosition());
        iv.setY(object.getYPosition());
        iv.setRotationAxis(Rotate.Y_AXIS);
        if (object.getDirection() == null) {
            object.setDirection(Direction.RIGHT);
        }
        if (object.getDirection().equals(Direction.LEFT)) {
            iv.setRotate(180);
        } else {
            iv.setRotate(0);
        }
        gameObjectImageViewMap.put(object.getID(), iv);
        myScreen.getChildren().add(iv);
    }
}