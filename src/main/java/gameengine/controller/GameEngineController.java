package gameengine.controller;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.network.client.ClientMain;
import gameengine.view.GameEngineUI;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.*;
import xml.XMLSerializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon, Ray Song, Delia Li
 */
public class GameEngineController implements CommandInterface {
	public static final double FRAMES_PER_SECOND = 60;
	public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1 / FRAMES_PER_SECOND;
	private XMLSerializer serializer;
	private Game currentGame;
	private GameEngineUI gameEngineView;
	private GameEngineBackend backend;
	private boolean hostGame;
	private String serverName;
	private Node toolbarHBox;
	private String xmlData;
	private Stage engineStage;
	public GameEngineController() {
		this.hostGame = true;
		serializer = new XMLSerializer();
		gameEngineView = new GameEngineUI(serializer, event -> reset());
	}
	public void startGame() {
		if (hostGame) {
			Thread serverThread = createServerThread();
			serverThread.start();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			//System.out.println("Error in Thread Sleep before StartClientGame");
			e.printStackTrace();
		}
		startClientGame(currentGame.getClientMappings());
	}
	private Thread createServerThread() {
		return new Thread() {
			public void run() {
				startServerGame(currentGame);
			}
		};
	}
	public Game createGameFromXML(String xmlData) {
		this.xmlData = xmlData;
		this.currentGame = serializer.getGameFromString(xmlData);
		if (currentGame.getCurrentLevel() == null || currentGame.getCurrentLevel().getPlayers().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Cannot start game.");
			alert.setContentText("You must create a level with a main character to start a game.");
			alert.showAndWait();
			return null;
		}
		currentGame.getCurrentLevel().setTitle(currentGame.getGameName());
		return currentGame;
	}
	public void startServerGame(Game currentGame) {
		this.currentGame = currentGame;
		backend = new GameEngineBackend(this, serverName);
		backend.startGame(currentGame);
		if (toolbarHBox != null) {
			backend.setToolbarHBox(toolbarHBox);
		}
	}
	public void startClientGame(Map<Long, List<Player>> playerMapping) {
		if(playerMapping.keySet().size()==0){
			playerMapping = new HashMap<Long, List<Player>>();
			playerMapping.put(0L,currentGame.getPlayers());
		}
		toolbarHBox = gameEngineView.getToolbar();
		gameEngineView.startClient(serverName);
		while (!gameEngineView.gameLoadedFromServer()) {
			// staller
			System.out.print("");
		}
		gameEngineView.initLevel(playerMapping);
		gameEngineView.setupKeyFrameAndTimeline(GameEngineController.MILLISECOND_DELAY);
	}
	public Scene getScene() {
		return gameEngineView.getScene();
	}
	@Override
	public void reset() {
		this.currentGame = createGameFromXML(xmlData);
		backend.setGame(currentGame);
		gameEngineView.closeLoseScreenStage();
	}
	public void setHostMode(boolean ishosted, String serverName){
		this.hostGame = ishosted;
		this.serverName = serverName;
	}
	@Override
	public void stop() {
		gameEngineView.stop();
	}
	@Override
	public void endGame() {
		gameEngineView.endGame();
	}
	public Level getLevel() {
		return currentGame.getCurrentLevel();
	}
	public void setupServerShutdown(){
		gameEngineView.serverShutdown();
	}

	public void setEngineStage(Stage stage) {
		this.engineStage = stage;
	}
}