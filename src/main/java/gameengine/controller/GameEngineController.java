package gameengine.controller;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.view.GameEngineUI;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import objects.*;
import xml.XMLSerializer;
/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon
 */
public class GameEngineController implements CommandInterface {
    public static final double FRAMES_PER_SECOND = 60;
    public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1 / FRAMES_PER_SECOND;
    private XMLSerializer serializer;
    private GameEngineUI gameEngineView;
    private GameEngineBackend backend;
    private boolean multiplayer;
    private boolean isServer;

    public GameEngineController() {
        this.multiplayer = false;
        this.isServer = false;
        serializer = new XMLSerializer();
    }
    public boolean startGame(String xmlData) {
        Game currentGame = serializer.getGameFromString(xmlData);
        if (currentGame.getCurrentLevel() == null || currentGame.getCurrentLevel().getPlayers().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Cannot start game.");
            alert.setContentText("You must create a level with a main character to start a game.");
            alert.showAndWait();
            return false;
        }
        if (!multiplayer) {
            Thread serverThread = new Thread() {
                public void run() {
                    startServerGame(currentGame);
                }
            };
            serverThread.start();
            startClientGame(currentGame.getPlayers().get(0));
        } else if (isServer) {
            startServerGame(currentGame);
        } else {
            startClientGame(currentGame.getPlayers().get(0));
        }
        return true;
    }

    public void startServerGame(Game currentGame) {
        backend = new GameEngineBackend();
        backend.startGame(currentGame);
    }
    public void startClientGame(Player player) {
        gameEngineView = new GameEngineUI(this, serializer, event -> reset(), player);
//		Timer timer = new Timer();
//		timer.scheduleAtFixedRate(new TimerTask() {
//
//			@Override
//			public void run() {
//				if(gameEngineView.gameLoadedFromServer()){
//					timer.cancel();
//					timer.purge();
//					beginUI();
//				}
//			}
//
//		}, 0, 30);
        while (!gameEngineView.gameLoadedFromServer()) {
            //staller
            System.out.print("");
        }
        beginUI(player);
    }
    public void beginUI(Player player) {
        gameEngineView.initLevel();
        gameEngineView.mapKeys(player, player.getControls());//NEED TO MAP FOR MULTIPLE PLAYERS
        gameEngineView.setupKeyFrameAndTimeline(GameEngineController.MILLISECOND_DELAY);
    }
    public Scene getScene() {
        return gameEngineView.getScene();
    }
    @Override
    public void reset() {
        stop();
        gameEngineView.resetGameScreen();
    }
    @Override
    public void stop() {
        gameEngineView.stop();
    }
    @Override
    public void endGame() {
        gameEngineView.endGame();
    }
}