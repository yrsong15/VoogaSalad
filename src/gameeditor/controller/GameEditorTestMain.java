package gameeditor.controller;

import gameeditor.xml.XMLSerializer;

public class GameEditorTestMain {
	public static void main(String[] args){
		GameEditorController myController = new GameEditorController();
		XMLSerializer mySerializer = new XMLSerializer();
		
		myController.createGame("flappy bird");
		myController.addToProperties("collidable", "die");
		myController.createGameObject(15, 30, 100, 50, "bird.jpg", myController.getProperties());
		myController.createLevel(1);
		myController.addCurrentGameObjectToLevel();
		myController.addCurrentLevelToGame();
		
		System.out.println(mySerializer.serializeGame(myController.getGame()));
	}
}
