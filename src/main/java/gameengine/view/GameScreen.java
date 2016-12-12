package gameengine.view;
import com.sun.javafx.scene.traversal.Direction;
import gameengine.network.server.ServerMain;
import gameengine.view.onscreenbuttons.MuteButton;
import gameengine.view.onscreenbuttons.PauseButton;
import gameengine.view.onscreenbuttons.ResetButton;
import gameengine.view.onscreenbuttons.SaveButton;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
    private Map<Integer, ImageView> gameObjectImageViewMap;
    private List<Rectangle> barList;
    private ResetButton resetButton;
    private MuteButton muteButton;
    private PauseButton pauseButton;
    private SaveButton saveButton;
    
    public GameScreen(EventHandler<? super MouseEvent> resetEvent,  EventHandler<? super MouseEvent> muteEvent,
    		EventHandler<? super MouseEvent> pauseEvent, EventHandler<? super MouseEvent> saveEvent) {
        myScreen = new Pane();
        myScreen.setMaxSize(screenWidth, screenHeight);
        this.resetButton = new ResetButton(resetEvent);
        this.muteButton = new MuteButton(muteEvent);
        this.pauseButton = new PauseButton(pauseEvent);
        this.saveButton = new SaveButton(saveEvent);
        myScreen.getChildren().add(resetButton.getButton());
        gameObjectImageViewMap = new HashMap<>();
        barList = new ArrayList<Rectangle>();
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


    public void init(ClientGame game) {
        Map<Integer, ClientGameObject> allGameObjects = game.getAllGameObjects();
        for (Map.Entry<Integer, ClientGameObject> entry : allGameObjects.entrySet()) {
            addGameObject(entry.getValue());
        }
    }

    public void update(ClientGame game){
    	for (Rectangle bar : barList){
    		myScreen.getChildren().remove(bar);
    	}
        Map<Integer, ClientGameObject> allGameObjects = game.getAllGameObjects();
        for (Map.Entry<Integer, ClientGameObject> entry : allGameObjects.entrySet()) {
            ClientGameObject object = entry.getValue();
            
            if (gameObjectImageViewMap.containsKey(object.getID())) {
                gameObjectImageViewMap.get(object.getID()).relocate(object.getXPosition(),
                        object.getYPosition());
            }
            else {
                addGameObject(object);
                Rectangle bar = new Rectangle(object.getXPosition(), object.getYPosition() - 8, object.getWidth(), 10);
                //myScreen.getChildren().add(bar);
                //barList.add(bar);
            }
        }
        for(Iterator<Integer> it = gameObjectImageViewMap.keySet().iterator(); it.hasNext();){
            int ID = it.next();
            if(!allGameObjects.containsKey(ID)){
                myScreen.getChildren().remove(gameObjectImageViewMap.get(ID));
                it.remove();
            }
        }
    }

    public void reset() {
        gameObjectImageViewMap.clear();
        myScreen.getChildren().clear();
        myScreen.getChildren().addAll(resetButton.getButton(), muteButton.getButton(), pauseButton.getButton(), saveButton.getButton());
        resetButton.getButton().toFront();
        muteButton.getButton().toFront();
        pauseButton.getButton().toFront();
        saveButton.getButton().toFront();
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
        gameObjectImageViewMap.put(object.getID(), iv);
        myScreen.getChildren().add(iv);
    }

    private void addGameObjectHealthBar(ClientGameObject object){
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
        gameObjectImageViewMap.put(object.getID(), iv);
        myScreen.getChildren().add(iv);
    }
}