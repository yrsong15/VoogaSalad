package gameengine.controller.interfaces;

import objects.ClientGame;
import objects.Game;

import java.lang.reflect.InvocationTargetException;

public interface ServerInterface {

	public Game getGame();
	public void updateModel();
	public void runControl(String controlName, int ID, int charIdx) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
	public ClientGame getClientGame();
	public void addPlayersToClient(int ID);
	public void restart();
}
