package general;


import gameeditor.controller.GameEditorBackendController;
import gameeditor.controller.GameEditorFrontEndController;
import gameeditor.view.GameEditorView;
import gameengine.controller.GameEngineController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Game;
import objects.GameObject;
import objects.Level;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MainController {
    public static final String STYLESHEET = "default.css";
    private static final String GALLERY_STAGE_TITLE = "Game Gallery"; //TODO: Replace this with a resource file
    private Stage myGalleryStage;
    private Stage mainStage;
    private Gallery myGallery;
    private GalleryView myGalleryView;
    private Stage myGameEditorStage;
    //private GameEditorView myGameEditorView;
    private GameEditorFrontEndController myGameEditorController;
    private GameEngineController myGameEngineController;

    public MainController(Stage stage) {
    	mainStage = stage;
        Scene scene = new Scene(new SplashScreen(stage, this).setUpWindow());
        //GameEditorView myView = new GameEditorView();
        //Scene scene = new Scene(myView.createRoot(),GameEditorView.SCENE_WIDTH,GameEditorView.SCENE_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        stage.setScene(scene);
        stage.setTitle("VoogaSalad");
        stage.show();
    }

    public void presentGallery() throws IOException {
        System.out.println("present");
        initializeGallery();
        myGalleryView = new GalleryView(myGallery, this);
        myGalleryStage.setScene(myGalleryView.getScene());
        myGalleryStage.setTitle(GALLERY_STAGE_TITLE);
        myGalleryStage.show();
    }

    private void initializeGallery() throws IOException {
        this.myGallery = new Gallery();
        this.myGalleryStage = new Stage();
// 	   this.gallery = new Gallery();
// 	   for(int i = 0; i < 40; i++)
// 	   {
// 		   myGallery.addToGallery(new GameFile());
// 	   }
// 	   this.galleryStage = new Stage();
    }

    public void presentEditor() {
        myGameEditorStage = new Stage();
        
        myGameEditorController = new GameEditorFrontEndController();
        Scene scene = new Scene(myGameEditorController.startEditor(), SplashScreen.SPLASH_WIDTH, SplashScreen.SPLASH_HEIGHT);
        myGameEditorStage.setScene(scene); 
        myGameEditorStage.show();
    }

    public void launchEngine(String XMLData){
    	XStream mySerializer = new XStream(new DomDriver());
        Game game = new Game("Flappy Bird");
        GameObject bird = new GameObject(250, 200, 150, 100, "bird3.png", new HashMap<>());
        GameObject pipe1 = new GameObject(50, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe1.setProperty("removeobject","");
        pipe1.setProperty("damage","30");
        pipe1.setProperty("points","30");
        GameObject pipe2 = new GameObject(180, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe2.setProperty("removeobject","");
        pipe2.setProperty("damage","30");
        pipe2.setProperty("points","30");
        GameObject pipe3 = new GameObject(310, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe3.setProperty("removeobject","");
        pipe3.setProperty("damage","30");
        pipe3.setProperty("points","30");
        GameObject pipe4 = new GameObject(440, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe4.setProperty("removeobject","");
        pipe4.setProperty("damage","30");
        pipe4.setProperty("points","30");
        GameObject pipe5 = new GameObject(570, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe5.setProperty("removeobject","");
        pipe5.setProperty("damage","30");
        pipe5.setProperty("points","30");
        pipe5.setProperty("removeobject","");
        Level level = new Level(1);
        level.addWinCondition("score", "10");
        level.addLoseCondition("time", "30");
        level.getViewSettings().setMusicFile("FlappyBirdThemeSong.mp3");
        level.getViewSettings().setBackgroundFilePath("Background/bg.png");
        level.addGameObject(bird);
        level.setMainCharacter(bird);
        level.addGameObject(pipe1);
        level.addGameObject(pipe2);
        level.addGameObject(pipe3);
        level.addGameObject(pipe4);
        level.addGameObject(pipe5);
        game.addLevel(level);
        game.setCurrentLevel(level);
        String s = mySerializer.toXML(game);
        System.out.println(s);
        
        GameEngineController gameEngineController = new GameEngineController();
        gameEngineController.setCurrentXML(s);
        mainStage.setScene(gameEngineController.getScene());
        gameEngineController.startGame();
    }
}