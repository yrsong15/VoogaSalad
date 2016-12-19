package gameengine.controller.interfaces;

import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Game;
import objects.Level;
import objects.Player;

import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * This is the main controller class for the Game Engine that's responsible for handling the interaction between the
 * model and the view. You also initiate games through this class.
 */
public interface IGameEngineController {
    double FRAMES_PER_SECOND = 60;
    double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    double SECOND_DELAY = 1 / FRAMES_PER_SECOND;

    /**
     * Starts the game specified by the current XML.
     */
    void startGame();

    Game createGameFromXML(String xmlData);

    void startServerGame(Game currentGame);

    void startClientGame(Map<Long, List<Player>> playerMapping);

    Scene getScene();

    void setHostMode(boolean ishosted, String serverName);

    Level getLevel();

    void setupServerShutdown();

    void setEngineStage(Stage stage);

    /**
     * Updates the view based on the current model.
     */
//    public void update (Observable o, Object arg);

}