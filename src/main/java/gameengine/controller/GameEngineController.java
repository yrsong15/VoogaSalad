// This entire file is part of my masterpiece.
// DELIA LI

package gameengine.controller;

import frontend.util.NodeFactory;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.IGameEngineController;
import gameengine.view.GameEngineUI;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import objects.*;
import xml.XMLSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou, Chalena Scholl, Noel
 *         Moon, Ray Song, Delia Li
 */
public class GameEngineController implements CommandInterface, IGameEngineController {
    private XMLSerializer serializer;
    private Game currentGame;
    private GameEngineUI gameEngineView;
    private GameEngineBackend backend;
    private NodeFactory myFactory;
    private boolean hostGame;
    private String serverName;
    private Node toolbarHBox;
    private String xmlData;
    private Stage engineStage;

    public GameEngineController() {
        this.hostGame = true;
        this.serializer = new XMLSerializer();
        this.gameEngineView = new GameEngineUI(serializer, event -> reset());
        this.myFactory = new NodeFactory();
    }

    @Override
    public void startGame() {

        if (hostGame) {
            Thread serverThread = createServerThread();
            serverThread.start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
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

    @Override
    public Game createGameFromXML(String xmlData) {
        this.xmlData = xmlData;
        this.currentGame = serializer.getGameFromString(xmlData);
        if (currentGame.getCurrentLevel() == null
                || currentGame.getCurrentLevel().getPlayers().isEmpty()) {
            Alert alert = myFactory.makeInfoAlert("CantStartGameHeader", "CreateLevel");
            alert.showAndWait();
            return null;
        }
        currentGame.getCurrentLevel().setTitle(currentGame.getGameName());
        return currentGame;
    }

    @Override
    public void startServerGame(Game currentGame) {
        this.currentGame = currentGame;
        backend = new GameEngineBackend(this, serverName);
        backend.startGame(currentGame);
        if (toolbarHBox != null) {
            backend.setToolbarHBox(toolbarHBox);
        }
    }

    @Override
    public void startClientGame(Map<Long, List<Player>> playerMapping) {
        if (playerMapping.keySet().size() == 0) {
            playerMapping = new HashMap<Long, List<Player>>();
            playerMapping.put(0L, currentGame.getPlayers());
        }
        toolbarHBox = gameEngineView.getToolbar();
        gameEngineView.startClient(serverName);
        while (!gameEngineView.gameLoadedFromServer()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        gameEngineView.initLevel(playerMapping);
        gameEngineView.setupKeyFrameAndTimeline(GameEngineController.MILLISECOND_DELAY);
    }

    @Override
    public Scene getScene() {
        return gameEngineView.getScene();
    }

    @Override
    public void reset() {
        this.currentGame = createGameFromXML(xmlData);
        backend.setGame(currentGame);
        gameEngineView.closeLoseScreenStage();
    }

    @Override
    public void setHostMode(boolean ishosted, String serverName) {
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

    @Override
    public Level getLevel() {
        return currentGame.getCurrentLevel();
    }

    @Override
    public void setupServerShutdown() {
        gameEngineView.serverShutdown();
    }

    @Override
    public void setEngineStage(Stage stage) {
        this.engineStage = stage;
    }
}