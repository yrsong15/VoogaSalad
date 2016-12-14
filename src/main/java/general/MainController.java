package general;
import java.io.File;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import frontend.util.FileOpener;
import gameeditor.controller.GameEditorController;
import gameengine.controller.GameEngineController;
import gameengine.view.GameCoverSplash;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.*;

/**
 * @author Delia Li, Pratiksha
 */
public class MainController {
    public static final String STYLESHEET = "default.css";
    private static final String GAME_TITLE = "VoogaSalad";
    private Stage gameEngineStage;
    private Gallery gallery;
    private GameEditorController gameEditorController;
    private GameEngineController gameEngineController;

    public MainController(Stage stage) throws IOException {
        this.gallery = new Gallery();
        Scene scene = new Scene(new SplashScreen(gallery, this).setUpWindow());
        scene.getStylesheets().add(STYLESHEET);
        stage.setScene(scene);
        stage.setTitle(GAME_TITLE);
        stage.show();
        initializeGallery();
        gameEngineController = new GameEngineController();
        gameEditorController = new GameEditorController();
    }

    private void initializeGallery() throws IOException {
        this.gallery = new Gallery();
    }

    private void addNewGameFile(String title, String gameData) {
        GameFile newGame = new GameFile(title, gameData);
        gallery.addToGallery(newGame);
    }

    public void presentEditor(Game game) {
        gameEditorController = new GameEditorController();
        gameEditorController.startEditor(game);
        gameEditorController.setOnLoadGame(e -> sendDataToEngine());
    }

    public void presentEditor(Game game, String gameType) {
        gameEditorController = new GameEditorController(gameType);
        gameEditorController.startEditor(game);
        gameEditorController.setOnLoadGame(e -> sendDataToEngine());
    }

    private void setUpGameEngineStage(Level level) {
        gameEngineStage = new Stage();
        GameCoverSplash myCover = new GameCoverSplash(level, this);
        gameEngineStage.setScene(myCover.createSplashScene());
        gameEngineStage.setTitle(myCover.getTitle());
        gameEngineStage.show();
        gameEngineController.setEngineStage(gameEngineStage);
    }

    public void startPlayingMulti(boolean isHosted, String myServer){
        gameEngineController.setHostMode(isHosted, myServer);
        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
        gameEngineStage.setOnCloseRequest(event -> shutdownClient());
        gameEngineController.startGame();
    }

    public void startPlayingSingle() {
        gameEngineController.setHostMode(true, "localhost");
        gameEngineStage.setScene(gameEngineController.getScene());
        gameEngineStage.show();
        gameEngineStage.setOnCloseRequest(event -> shutdownClient());
        gameEngineController.startGame();
    }

    private void shutdownClient(){
        gameEngineController.setupServerShutdown();
        gameEngineController.stop();
    }

    private void sendDataToEngine() {
        String title = gameEditorController.getGameTitle();
        String gameFile = gameEditorController.getGameFile();
        addNewGameFile(title, gameFile);
        launchEngine(gameFile);
    }

    public void launchEngine(String XMLData) {
        GameExamples gameExamples = new GameExamples();
//        XMLData = gameExamples.getDanceDanceRevolution();
//        XMLData = gameExamples.getMultiplayerDDR();
        XMLData = gameExamples.getDoodleJumpXML();
//        XMLData = gameExamples.getScrollingXML();
        XMLData = gameExamples.getMarioXML();
        boolean multiplayer = true;
        @SuppressWarnings("unused")
        boolean isServer = false;
        Game game = gameEngineController.createGameFromXML(XMLData);
        Level level = game.getCurrentLevel();
        if (level != null) {
            setUpGameEngineStage(level);
        }
    }

    public void editGame() {
        FileOpener chooser = new FileOpener();
        File file = chooser.chooseFile("XML", "data");
        XStream mySerializer = new XStream(new DomDriver());
        Game myGame = (Game) mySerializer.fromXML(file);
        presentEditor(myGame);
    }
}