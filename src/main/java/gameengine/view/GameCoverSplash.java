package gameengine.view;

import frontend.util.ButtonTemplate;
import gameengine.view.interfaces.ScoreScreen;
import general.MainController;
import general.NodeFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.GameObject;
import objects.Level;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Delia on 12/11/2016.
 */
public class GameCoverSplash {
    private String title, character, background;
    private static final String COVER_SPLASH_STYLE = "default.css";
    private static final int COVER_WIDTH = 700;
    private ArrayList<GameObject> playahs;
    private Scene coverScene;
    private Pane myWindow;
    private MainController mainController;
    private Level myLevel;
    private TextField addServer;
    private NodeFactory myFactory = new NodeFactory();

    public GameCoverSplash(Level level, MainController myMainController){
        this.playahs = (ArrayList) level.getPlayers();
        this.background = level.getBackgroundFilePath();
        this.title = level.getTitle();
        this.myLevel = level;
        this.mainController = myMainController;
    }

    public Scene createSplashScene(){
        myWindow = new Pane();
        int titleWidth = 100 + (30 * title.length());
        if(titleWidth < COVER_WIDTH) titleWidth = COVER_WIDTH;
        coverScene = new Scene(myWindow, titleWidth, 775);
        coverScene.getStylesheets().add(COVER_SPLASH_STYLE);
        Image backg = new Image(background);
        ImageView backgroundImage = new ImageView(backg);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitHeight(775);
        Text titleText = myFactory.bigNameTitle(title, 35, 100);
//        titleText.setOnMouseClicked(e -> testLevelScreens());
        ButtonTemplate singleTemp = new ButtonTemplate("Singleplayer", 300, 165);
        Button single = singleTemp.getButton();
        single.setOnMouseClicked(e -> mainController.startPlayingSingle());
        ButtonTemplate multiTemp = new ButtonTemplate("Multiplayer", 300, 265);
        Button multi = multiTemp.getButton();
        multi.setOnMouseClicked(e -> setUpMulti());
        myWindow.getChildren().addAll(backgroundImage, titleText, single, multi);
//        setUpJoin();
        addPlayahs();
        return coverScene;
    }

    private void setUpMulti(){
        ButtonTemplate hostTemp = new ButtonTemplate("HostGame", 300, 365);
        Button host = hostTemp.getButton();
        host.setOnMouseClicked(e -> mainController.startPlayingMulti(true, addServer.getText()));
        ButtonTemplate joinTemp = new ButtonTemplate("JoinGame", 300, 465);
        Button join = joinTemp.getButton();
        join.setOnMouseClicked(e -> mainController.startPlayingMulti(false, addServer.getText()));
        addServer = myFactory.makeTextField("Enter a server", 520, 485);
        myWindow.getChildren().addAll(host, join, addServer);
    }

    private void addPlayahs(){
        for (int i = 0; i < playahs.size(); i++){
            Image playah = new Image(myFactory.getUserDirectorySpritePrefix() + playahs.get(i).getImageFileName());
            ImageView newPlayah = new ImageView(playah);
            newPlayah.setPreserveRatio(true);
            newPlayah.setFitHeight(200);
            newPlayah.setTranslateX(50 + (i * 150));
            newPlayah.setTranslateY(500);
            myWindow.getChildren().add(newPlayah);
        }
    }

//    private void testLevelScreens(){
//        ArrayList<Integer> highScores = new ArrayList<Integer>();
//        highScores.add(myLevel.getScore());
////        System.out.println("level screen");
////        Level level = new Level(1);
//        ScoreScreen myLevelScreen = new LevelScreen(myLevel, highScores);
//        Stage smallLevelStage = new Stage();
//        smallLevelStage.setScene(myLevelScreen.getScene());
//        smallLevelStage.setTitle(myLevelScreen.getStageTitle());
//        smallLevelStage.show();
//    }

    public String getTitle(){
        return title;
    }
}
