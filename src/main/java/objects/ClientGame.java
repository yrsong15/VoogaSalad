package objects;

import gameengine.network.server.ServerMain;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientGame {

	Map<Integer, ClientGameObject> gameObjectMap;
	private String musicFilePath;
	private String backgroundFilePath;
	private ClientGameObject background;
	private List<Integer> highScores;
	private Map<Long, Integer> scoreMapping;
	private int currLevel;
	private boolean gameOver;
	private boolean gameWon;

	public ClientGame(String musicFilePath, String backgroundFilePath, List<Integer> highScores) {
		gameObjectMap = new HashMap<>();
		this.highScores = highScores;
		this.musicFilePath = musicFilePath;
		this.backgroundFilePath = backgroundFilePath;
	}

	public void addAll(List<GameObject> allGameObjects) {
		for (GameObject o : allGameObjects) {
			if (o.getID() == 0) {
				ServerMain.idCounter++;
				o.setID(ServerMain.idCounter);
			}
			gameObjectMap.put(o.getID(),
					new ClientGameObject(o.getID(), o.getXPosition(), o.getYPosition(), o.getWidth(), o.getHeight(),
							o.getDirection(), o.getImageFileName(),
							o.getProperty("health") == null ? null : Integer.parseInt(o.getProperty("health"))));
		}
	}

	public void setBackgroundObject(GameObject bg) {
		this.background = new ClientGameObject(bg.getID(), bg.getXPosition(), bg.getYPosition(), bg.getWidth(),
				bg.getHeight(), bg.getDirection(), bg.getImageFileName(), null);
	}

	public Map<Integer, ClientGameObject> getAllGameObjects() {
		return gameObjectMap;
	}

	public ClientGameObject getBackgroundObject() {
		return background;
	}
	
	public void addScores(Map<Long, Integer> scores){
		scoreMapping = scores;
	}
	
	public Map<Long, Integer> getScores() {
		return scoreMapping;
	}

	public String getMusicFilePath() {
		return musicFilePath;
	}

	public void setMusicFilePath(String musicFilePath) {
		this.musicFilePath = musicFilePath;
	}
	
	public void setLevel(int level){
		currLevel = level;
	}
	
	public int getLevel(){
		return currLevel;
	}
	
	public boolean isGameOver(){
		return gameOver;
	}
	
	public boolean isGameWon(){
		return gameOver;
	}

	public String getBackgroundFilePath() {
		return backgroundFilePath;
	}

	public void setBackgroundFilePath(String backgroundFilePath) {
		this.backgroundFilePath = backgroundFilePath;
	}

	public Map<Integer, ClientGameObject> getGameObjectMap() {
		return gameObjectMap;
	}

	public void setGameObjectMap(Map<Integer, ClientGameObject> gameObjectMap) {
		this.gameObjectMap = gameObjectMap;
	}

	public ClientGameObject getBackground() {
		return background;
	}

	public void setBackground(ClientGameObject background) {
		this.background = background;
	}

	public List<Integer> getHighScores() {
		return highScores;
	}

	public void setHighScores(List<Integer> highScores) {
		this.highScores = highScores;
	}

}
