package usecases;

import gameengine.controller.GameEngineController;

import java.lang.reflect.InvocationTargetException;

/**
 * In order to start a game in the game engine from an XML, we can simply set the current XML of the GameEngine
 * Controller to the specified XML and call the start game method, the implementation of which is abstracted.
 *
 */
public class UseCaseStartGameFromXML {

    GameEngineController controller;

    public UseCaseStartGameFromXML(GameEngineController controller){
        this.controller = controller;
    }

    public void startGameFromXML(String fileName) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        controller.setCurrentXML(fileName);
        controller.startGame();
    }
}
