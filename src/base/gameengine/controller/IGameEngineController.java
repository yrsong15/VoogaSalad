package base.gameengine.controller;

import java.util.Observable;
import java.util.Observer;

public interface IGameEngineController {

    public void startGame(String fileName);

    public void update (Observable o, Object arg);



}
