package general;
import gameeditor.controller.GameEditorController;
import gameengine.controller.GameEngineController;
import gameengine.view.GameEngineUI;
import gameengine.view.GameScreen;
import general.interfaces.IMainController;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import objects.Game;
import objects.GameObject;
import objects.Level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.sun.javafx.scene.traversal.Direction;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import objects.RandomGeneration;
import objects.ScrollType;

public class MainController implements IMainController{
    public static final String STYLESHEET = "default.css";
    private static final String GALLERY_STAGE_TITLE = "Game Gallery"; //TODO: Replace this with a resource file
    private Stage myGalleryStage, myGameEditorStage, myGameEngineStage;
    private Stage mainStage;
    private Gallery myGallery;
    private GalleryView myGalleryView;
    private Game game;
    private GameObject bird, pipe1, pipe2, pipe3, pipe4, pipe5, ground;
    private ScrollType gameScroll;
    private RandomGeneration randomGeneration;
    private Level level;
    private GameEditorController myGameEditorController;
    private GameEngineController myGameEngineController;


    public MainController(Stage stage) throws IOException {
        mainStage = stage;
        Scene scene = new Scene(new SplashScreen(stage, this).setUpWindow());
        scene.getStylesheets().add(STYLESHEET);
        stage.setScene(scene);
        stage.setTitle("VoogaSalad");
        stage.show();
        initializeGallery();
    }

    @Override
    public void presentGallery() {
        //System.out.println("present");
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

    }

    private void addNewGameFile(String title, String gameData)
    {
    	GameFile newGame = new GameFile(title,gameData);
    	myGallery.addToGallery(newGame);

    }

    @Override
    public void presentEditor() {
        myGameEditorStage = new Stage();
        myGameEditorStage.setTitle("Game Editor");
        myGameEditorController = new GameEditorController();
        Scene scene = new Scene(myGameEditorController.startEditor(),
                SplashScreen.SPLASH_WIDTH, SplashScreen.SPLASH_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        myGameEditorStage.setScene(scene); 
        scene.getStylesheets().add("gameEditorSplash.css");
        myGameEditorStage.show();

        myGameEditorController.setOnLoadGame(e -> sendDataToEngine());
    }

    //TODO: Remove hardcoded values in this method and the ones after it! Let's make another properties file or something
    //for these strings
    @Override
    public void launchEngine(String XMLData){
        System.out.println("engine launched");
        game = new Game("Flappy Bird");
        createGameObjects();
        generateGameAttributes();
        setUpLevel();
        setMyGameEngineController();
        setMyGameEngineStage();
        myGameEngineController.startGame();
    }

    private void createGameObjects(){
        bird = new GameObject(250, 200, 75, 50, "bird3.png", new HashMap<>());
        bird.setProperty("gravity", "0.8");
        bird.setProperty("health", "100000");
        bird.setProperty("jump", "400");
        pipe1 = new GameObject(50, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe1.setProperty("damage", "30");
        pipe1.setProperty("points", "1");
        pipe2 = new GameObject(180, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe2.setProperty("damage", "30");
        pipe2.setProperty("points", "1");
        pipe3 = new GameObject(310, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe3.setProperty("damage", "30");
        pipe3.setProperty("points", "1");
        pipe4 = new GameObject(440, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe4.setProperty("damage", "30");
        pipe4.setProperty("points", "1");
        pipe5 = new GameObject(570, 450, 80, 200, "Pipes.png", new HashMap<>());
        pipe5.setProperty("damage", "30");
        pipe5.setProperty("points", "1");
        ground = new GameObject(0, 600, 1000000, 200, new HashMap<>());
        ground.setProperty("damage", "30");
    }

    private void generateGameAttributes(){
        gameScroll = new ScrollType("ForcedScrolling");
        gameScroll.addScrollDirection(Direction.RIGHT);
        randomGeneration = new RandomGeneration(pipe1.getProperties(), 5,
                (int) GameScreen.screenWidth / 5,
                (int) GameScreen.screenWidth,
                (int) (GameScreen.screenHeight*0.2),
                (int) (GameScreen.screenHeight*0.6), 250, 500);
    }

    private void setUpLevel(){
        level = new Level(1);
        level.addWinCondition("score", "10");
        level.addLoseCondition("time", "30");
        level.getViewSettings().setMusicFile("FlappyBirdThemeSong.mp3");
        level.getViewSettings().setBackgroundFilePath("Background/bg.png");
        level.addGameObject(bird);
        level.addGameObject(ground);

        ScrollType gameScroll = new ScrollType("ForcedScrolling");
        gameScroll.addScrollDirection(Direction.RIGHT);

        level.setScrollType(gameScroll);
        RandomGeneration randomGeneration = new RandomGeneration(pipe1.getProperties(), 5, (int) GameScreen.screenWidth / 5, (int) GameScreen.screenWidth,
                                                                 (int) (GameScreen.screenHeight*0.2), (int) (GameScreen.screenHeight*0.6), 250, 500);

        level.setScrollType(gameScroll);
        level.addRandomGeneration(randomGeneration);

        level.addControl(KeyCode.W, "jump");
    }

    private void setMyGameEngineController(){
        XStream mySerializer = new XStream(new DomDriver());
        game.addLevel(level);
        game.setCurrentLevel(level);
        String s = mySerializer.toXML(game);
        myGameEngineController = new GameEngineController();
        myGameEngineController.setCurrentXML(s);
    }

    private void setMyGameEngineStage(){
        myGameEngineStage = new Stage();
        myGameEngineStage.setOnCloseRequest(e -> myGameEngineStage.close());
        myGameEngineStage.setScene(myGameEngineController.getScene());
        myGameEngineStage.setOnCloseRequest(event -> myGameEngineController.stop());
        myGameEngineStage.show();

//        myGameEngineController.startGame(); 
//        System.out.println(s);
    }

    private void sendDataToEngine() {

        String title = myGameEditorController.getGameTitle();
        String gameFile = myGameEditorController.getGameFile();
        addNewGameFile(title,gameFile);
        
        // THIS IS ENTIRELY FOR TEST PURPOSES ::
        
        System.out.println(gameFile);
//        String file=null;
//        
//        try {
//            file = new String(Files.readAllBytes(Paths.get("testFiles/test1")));
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }     
        GameEngineController gameEngineController = new GameEngineController();   
        
       //gameEngineController.setCurrentXML(file);
        
        gameEngineController.setCurrentXML(gameFile);
       
        myGameEngineStage = new Stage();
        myGameEngineStage.setOnCloseRequest(e -> {
            myGameEngineStage.close();
        });
        myGameEngineStage.setScene(gameEngineController.getScene());
        myGameEngineStage.setOnCloseRequest(event -> gameEngineController.stop());
        myGameEngineStage.show();
        gameEngineController.startGame(); 
        myGameEngineStage.setOnCloseRequest(e -> myGameEngineStage.close());     
        myGameEngineStage.setScene(gameEngineController.getScene());
        myGameEngineStage.setOnCloseRequest(event -> gameEngineController.stop());
        myGameEngineStage.show();
        gameEngineController.startGame();
    }   
}