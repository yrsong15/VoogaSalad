package gameengine.view;

import frontend.util.ButtonTemplate;
import general.MainController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import objects.GameObject;
import objects.Level;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Delia on 12/11/2016.
 */
public class GameCoverSplash {
    private String title, background, character;
    private ArrayList<GameObject> playahs;
    private Scene coverScene;
    private Pane myWindow;
    private MainController mainController;

    public GameCoverSplash(Level level, MainController myMainController){
        this.playahs = (ArrayList) level.getPlayers();
        this.background = level.getBackgroundFilePath();
        this.title = level.getTitle();
        this.mainController = myMainController;
    }

    public Scene createSplashScene(){
        myWindow = new Pane();
        coverScene = new Scene(myWindow);
        ButtonTemplate startTemp = new ButtonTemplate("GalleryGameEngine");
        Button start = startTemp.getButton();
        start.setOnMouseClicked(e -> mainController.startPlaying());
        myWindow.getChildren().add(start);
        return coverScene;
    }
}
