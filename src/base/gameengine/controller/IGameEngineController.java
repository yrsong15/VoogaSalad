package base.gameengine.controller;

import java.util.Observable;
import java.util.Observer;

public interface IGameEngineController {

    void startGame(String fileName);

    void update (Observable o, Object arg);



}
