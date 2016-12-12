package gameengine.controller;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.view.GameEngineUI;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import objects.*;
import xml.XMLSerializer;
import java.util.List;
import java.util.Map;
/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon, Ray Song
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

	public GameEngineController() {
		this.hostGame = true;
		serverName = "localhost";
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
		if (hostGame) {
			Thread serverThread = new Thread() {
				public void run() {
					startServerGame(currentGame);
				}
			};
			serverThread.start();
		}
		startClientGame(currentGame.getClientMappings());
		return true;
	}
	public void startServerGame(Game currentGame) {
		this.currentGame = currentGame;
		backend = new GameEngineBackend(serverName);
		backend.startGame(currentGame);
		if(toolbarHBox != null){
			backend.setToolbarHBox(toolbarHBox);
		}
	}


	public void startClientGame(Map<Long, List<Player>> playerMapping) {

		gameEngineView = new GameEngineUI(this, serializer, event -> reset(), serverName);
		while (!gameEngineView.gameLoadedFromServer()) {
			// staller
			System.out.print("");
		}
		gameEngineView.initLevel(playerMapping);
		gameEngineView.setupKeyFrameAndTimeline(GameEngineController.MILLISECOND_DELAY);
		toolbarHBox = gameEngineView.getToolbar();
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

	public Level getLevel() { return currentGame.getCurrentLevel(); }
}