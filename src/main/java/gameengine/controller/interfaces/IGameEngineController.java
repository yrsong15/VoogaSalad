// This entire file is part of my masterpiece.
// DELIA LI

package gameengine.controller.interfaces;

import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Game;
import objects.Level;
import objects.Player;

import java.util.List;
import java.util.Map;

/**
 * @author Delia
 *         This is the main controller class for the Game Engine that's responsible for handling the interaction between the
 *         model and the view. You also initiate games through this class.
 */
public interface IGameEngineController {
    double FRAMES_PER_SECOND = 60;
    double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    double SECOND_DELAY = 1 / FRAMES_PER_SECOND;

    /**
     * Launches the game specified by the current XML.
     * If it's a multiplayer game being hosted by this user, it starts the server thread before it
     * calls startClientGame().
     */
    void startGame();

    /**
     * This takes a string containing the xml data and serializes it, returning the game generated
     * This method notifies the user of incomplete games and bad input by checking whether the Level
     * is null. It pops up an alert if that's the case.
     *
     * @param xmlData
     * @return
     */
    Game createGameFromXML(String xmlData);

    /**
     * This method is called if the game is multiplayer and the current user is hosting the game.
     *
     * @param currentGame
     */
    void startServerGame(Game currentGame);

    /**
     * This method is called to launch both multiplayer and single player games.
     * It sets player mappings, the buttons at the top toolbar, and initializes other front end
     * requirements to play the game.
     *
     * @param playerMapping
     */
    void startClientGame(Map<Long, List<Player>> playerMapping);

    /**
     * Returns the scene of the current game.
     *
     * @return
     */
    Scene getScene();

    /**
     * Sets the boolean for whether a game is hosted (false = user is not hosting, true = user is hosting)
     * and the string name of the server that the user wishes to use.
     *
     * @param ishosted
     * @param serverName
     */
    void setHostMode(boolean ishosted, String serverName);

    /**
     * Returns current level that the game engine is on.
     *
     * @return
     */
    Level getLevel();

    /**
     * Tells GameEngineUI to quit the current server thread.
     */
    void setupServerShutdown();

    /**
     * Sets the current stage of the game being played.
     *
     * @param stage
     */
    void setEngineStage(Stage stage);
}