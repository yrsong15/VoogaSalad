package gameengine.controller.interfaces;

import objects.Game;

public interface GameHandler {

	public Game getGame();
	public void addClientCharacter();
	public void updateGame();
	public void runControl(String controlName);
}
