package gameeditor.xml;

public class GameEditorTestMain {
	public static void main(String[] args){
		GameEditorGameManager myManager = new GameEditorGameManager();
		XMLSerializer mySerializer = new XMLSerializer();
		
		myManager.createGame("flappy bird");
		myManager.addToProperties("collidable", "die");
		myManager.createGameObject(15, 30, 100, 50, "bird.jpg", myManager.getProperties());
		myManager.createLevel(1);
		myManager.addCurrentGameObjectToLevel();
		myManager.addCurrentLevelToGame();
		
		System.out.println(mySerializer.serializeGame(myManager.getGame()));
	}
}
