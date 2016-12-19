package gameengine.view;

import frontend.util.ButtonTemplate;
import gameengine.view.interfaces.IGameCoverSplash;
import general.MainController;
import frontend.util.NodeFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import objects.GameObject;
import objects.Level;

import java.util.ArrayList;

/**
 * Created by Delia on 12/11/2016.
 */
public class GameCoverSplash implements IGameCoverSplash{
    private String title, background;
    private ArrayList<GameObject> playahs;
    private TextField addServer;
    private Scene coverScene;
    private Pane myWindow;
    private MainController mainController;
    private Level myLevel;
    private NodeFactory myFactory = new NodeFactory();

    public GameCoverSplash(Level level, MainController myMainController) {
        this.playahs = (ArrayList) level.getPlayers();
        this.background = level.getBackgroundFilePath();
        this.title = level.getTitle();
        this.myLevel = level;
        this.mainController = myMainController;
    }

    @Override
    public Scene createSplashScene() {
        myWindow = new Pane();
        if (title == null){
        	title = "Untitled";
        }
        int titleWidth = 100 + (30 * title.length());
        if (titleWidth < COVER_WIDTH) titleWidth = COVER_WIDTH;
        coverScene = new Scene(myWindow, titleWidth, 775);
        coverScene.getStylesheets().add(COVER_SPLASH_STYLE);
        ImageView backgroundImage = makeBackground();
        Text titleText = myFactory.bigNameTitle(title, 35, 100);
        myWindow.getChildren().addAll(backgroundImage, titleText);

        if(title.contains("Multi")){
            setUpMulti();
        }
        else{
            setUpSingle();
        }
        addPlayahs();
        return coverScene;
    }

    private void setUpMulti(){
//        ButtonTemplate singleTemp = new ButtonTemplate("Singleplayer", 150, 165);
//        Button single = singleTemp.getButton();
//        single.setOnMouseClicked(e -> mainController.startPlayingSingleDDR());
        ButtonTemplate multiTemp = new ButtonTemplate("Multiplayer", 150, 265);
        Button multi = multiTemp.getButton();
        multi.setOnMouseClicked(e -> multiHandler());
//        myWindow.getChildren().addAll(single, multi);
        myWindow.getChildren().add(multi);
    }

    private void multiHandler() {
        ButtonTemplate hostTemp = new ButtonTemplate("HostGame", 150, 365);
        Button host = hostTemp.getButton();
        host.setOnMouseClicked(e -> mainController.startPlayingMulti(true, addServer.getText()));
        ButtonTemplate joinTemp = new ButtonTemplate("JoinGame", 150, 465);
        Button join = joinTemp.getButton();
        join.setOnMouseClicked(e -> mainController.startPlayingMulti(false, addServer.getText()));
        addServer = myFactory.makeTextField("Enter a server", 370, 485);
        myWindow.getChildren().addAll(host, join, addServer);
    }

    private void setUpSingle(){
        ButtonTemplate startTemp = new ButtonTemplate("StartGame", 250, 300);
        Button start = startTemp.getButton();
        start.setOnMouseClicked(e -> mainController.startPlayingSingle());
        myWindow.getChildren().add(start);
    }

    private void addPlayahs() {
        for (int i = 0; i < playahs.size(); i++) {
            Image playah = new Image(myFactory.getUserDirectorySpritePrefix() + playahs.get(i).getImageFileName());
            ImageView newPlayah = new ImageView(playah);
            newPlayah.setPreserveRatio(true);
            newPlayah.setFitHeight(200);
            newPlayah.setTranslateX(50 + (i * 150));
            newPlayah.setTranslateY(500);
            myWindow.getChildren().add(newPlayah);
        }
    }

    private ImageView makeBackground(){
        Image backg = new Image(background);
        ImageView backgroundImage = new ImageView(backg);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitHeight(775);
        return backgroundImage;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
