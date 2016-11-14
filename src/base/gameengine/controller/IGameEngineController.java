package base.gameengine.controller;

import java.util.Observable;
import java.util.Observer;

public interface IGameEngineController extends Observer{

    void startGame(String fileName);

}