package gameengine.controller.interfaces;

import java.util.Observable;

/**
 * This is the main controller class for the Game Engine that's responsible for handling the interaction between the
 * model and the view. You also initiate games through this class.
 */
public interface IGameEngineController {

    /**
     * Starts the game specified by the current XML.
     */
    public void startGame();

    /**
     * Sets the XML file for the game to be run.
     * @param fileName The name of the XML file
     */
    public void setCurrentXML(String fileName);

    /**
     * Updates the view based on the current model.
     */
    public void update (Observable o, Object arg);
    
    /**
     * Pauses the current game
     */
    public void stopGame();
    
    /**
     * Resumes the game
     */
    public void resumeGame();

}