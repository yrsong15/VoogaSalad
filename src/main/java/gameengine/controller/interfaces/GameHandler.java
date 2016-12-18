package gameengine.controller.interfaces;

import java.lang.reflect.InvocationTargetException;

import gameengine.model.EnemyMisreferencedException;
import objects.ClientGame;
import objects.Game;

public interface GameHandler {

	public Game getGame();
	public void addClientCharacter();
	public void updateGame() throws IllegalAccessException, InvocationTargetException, EnemyMisreferencedException;
	public void runControl(String controlName, int ID, int charIdx);
	public ClientGame getClientGame();
	public void addPlayersToClient(int ID);
	public void restart();
}
