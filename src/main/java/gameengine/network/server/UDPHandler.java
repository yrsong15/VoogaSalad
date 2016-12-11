package gameengine.network.server;

import objects.ClientGame;
import objects.Game;

public interface UDPHandler {
	public void updateGame(ClientGame game);
}
