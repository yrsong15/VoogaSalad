package gameengine.network.server;

import objects.ClientGame;
import objects.Game;
import objects.GameObject;

public interface UDPHandler {
	public void updateGame(ClientGame game);
	public int getCharIdx(GameObject player);
}
