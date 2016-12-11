package objects;

import gameengine.network.server.ServerMain;

import java.util.ArrayList;
import java.util.List;

public class ClientGame {

	List<ClientGameObject> list;
    private String musicFilePath;
	private String backgroundFilePath;

	public ClientGame(String musicFilePath, String backgroundFilePath) {
		list = new ArrayList<ClientGameObject>();
		this.musicFilePath = musicFilePath;
		this.backgroundFilePath = backgroundFilePath;
	}

	public void addAll(List<GameObject> allGameObjects) {
		for (GameObject o : allGameObjects) {
			list.add(new ClientGameObject(o.getID(), o.getXPosition(), o.getYPosition(), o.getWidth(), o.getHeight(),
					o.getDirection(), o.getImageFileName()));
		}
	}
	
	public List<ClientGameObject> getAllGameObjects(){
		return list;
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
