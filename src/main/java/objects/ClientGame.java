package objects;

import gameengine.network.server.ServerMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientGame {

	Map<Integer, ClientGameObject> gameObjectMap;
    private String musicFilePath;
	private String backgroundFilePath;

	public ClientGame(String musicFilePath, String backgroundFilePath) {
		gameObjectMap = new HashMap<>();
		this.musicFilePath = musicFilePath;
		this.backgroundFilePath = backgroundFilePath;
	}

	public void addAll(List<GameObject> allGameObjects) {
		for (GameObject o : allGameObjects) {
			if(o.getID() == 0) {
			    ServerMain.idCounter++;
				o.setID(ServerMain.idCounter);
			}
			gameObjectMap.put(o.getID(), new ClientGameObject(o.getID(), o.getXPosition(), o.getYPosition(), o.getWidth(), o.getHeight(),
					o.getDirection(), o.getImageFileName()));
		}
	}
	
	public Map<Integer, ClientGameObject> getAllGameObjects(){
		return gameObjectMap;
	}

	public String getMusicFilePath() {
		return musicFilePath;
	}

	public void setMusicFilePath(String musicFilePath) {
		this.musicFilePath = musicFilePath;
	}

	public String getBackgroundFilePath() {
		return backgroundFilePath;
	}

	public void setBackgroundFilePath(String backgroundFilePath) {
		this.backgroundFilePath = backgroundFilePath;
	}

}
