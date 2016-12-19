// This entire file is part of my masterpiece.
// Soravit Sophastienphong
/**
 * I chose the game engine model controller as one of the classes in my masterpiece because it is the front-end half
 * of the controller that we divided into two parts in order to be able to integrate networking into the game engine.
 * Like the game engine model controller, this class also makes user of an interface CommandInterface to provide access
 * to a select couple of public methods that other classes can access. Since the view is only updated by the model when
 * data is sent over the server, then the view has almost no access the public API of the backend itself, which is
 * contributes to our use of the MVC design pattern, where data between the view and the model is encapsulated, and
 * these controllers in addition to the client and server are responsible for the facilitating the interaction. In
 * addition, this class also shows good use of data-driven design, since the whole of the game engine relies on the
 * data specified in the XML serialized by the game editor. Since the game editor does error-checks to ensure that
 * it serializes a valid XML that can be run in the engine, then we know that the data passed to us will not have
 * any issues running in the engine. As Steve Rabin states in his article, this is ideal because of the lack of
 * hardcoding involved in creating a game and the flexibility it grants us in debugging and testing, where we can
 * make changes to the game without modifying a single line of code. Since this class is only responsible for
 * instantiation of client games and managing the server, then the implementation the user interface itself is always
 * encapsulated in the GameEngineUI class, which has most of the behavior for the front-end.
 *
 */

package gameengine.controller;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.network.server.ServerMain;
import gameengine.view.GameEngineUI;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.*;
import xml.XMLSerializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Soravit Sophastienphong, Eric Song
 */
public class GameEngineViewController implements CommandInterface {

	public static final double FRAMES_PER_SECOND = 60;
	public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1 / FRAMES_PER_SECOND;

    private XMLSerializer serializer;
	private Game currentGame;
	private GameEngineUI gameEngineView;
	private GameEngineModelController gameEngineModelController;
    private Stage engineStage;
    private String serverName;
	private String xmlData;
    private boolean hostGame;

    /**
     * Instantiates the XML serializer and the game engine user interface
     */
    public GameEngineViewController() {
		serializer = new XMLSerializer();
		gameEngineView = new GameEngineUI(serializer, event -> reset());
	}

    /**
     * @return The JavaFX scene of the game engine user interface
     */
    public Scene getScene() {
        return gameEngineView.getScene();
    }

    /**
     * @return A reference to the current level of the current game
     */
    public Level getLevel() {
        return currentGame.getCurrentLevel();
    }

    /**
     * Terminates the server in the game engine view
     */
    public void shutDownServer(){
        gameEngineView.serverShutdown();
    }

    /**
     * @param stage The stage to be set for the game engine view
     */
    public void setEngineStage(Stage stage) {
        this.engineStage = stage;
    }

    /**
     * Starts a server thread if hosting an online game. Otherwise, starts a client game.
     */
    public void startGame() throws InterruptedException {
		if (hostGame) {
			Thread serverThread = createServerThread();
			serverThread.start();
		}
        Thread.sleep(1000);
		startClientGame(currentGame.getClientMappings());
	}

    /**
     * @param xmlData The XML specifying the game to be run
     * @return The Game object deserialized from the XML
     */
	public Game createGameFromXML(String xmlData) {
		this.xmlData = xmlData;
		this.currentGame = serializer.getGameFromString(xmlData);
		currentGame.getCurrentLevel().setTitle(currentGame.getGameName());
		return currentGame;
	}

    /**
     * @param currentGame The current game
     * Starts the game engine model on the server
     */
	public void startServerGame(Game currentGame) {
		this.currentGame = currentGame;
		gameEngineModelController = new GameEngineModelController(this, serverName);
		gameEngineModelController.startGame(currentGame);
	}

    /**
     * @param playerMapping The mapping of clients/players to game objects
     * Starts the game engine view on the client
     */

	public void startClientGame(Map<Long, List<Player>> playerMapping) {
		if(playerMapping.keySet().size() == 0){
			playerMapping = new HashMap<>();
			playerMapping.put(0L,currentGame.getPlayers());
		}
		gameEngineView.startClient(serverName);
		while (!gameEngineView.gameLoadedFromServer()) {
            ServerMain.stall();
		}
		gameEngineView.initLevel(playerMapping);
		gameEngineView.setupKeyFrameAndTimeline(GameEngineViewController.MILLISECOND_DELAY);
	}

    /**
     * Pauses the game engine view
     */
    @Override
    public void pause() {
        gameEngineView.stop();
    }

    /**
     * Resets the current game
     */
	@Override
	public void reset() {
		this.currentGame = createGameFromXML(xmlData);
		gameEngineModelController.setGame(currentGame);
		gameEngineView.closeLoseScreenStage();
	}

    /**
     * @param hostGame Specifies whether or not you are hosting an online game
     * @param serverName The name of the server to host or join
     */
	public void setHostMode(boolean hostGame, String serverName){
		this.hostGame = hostGame;
		this.serverName = serverName;
	}

    private Thread createServerThread() {
        return new Thread() {
            public void run() {
                startServerGame(currentGame);
            }
        };
    }
}