package gameeditor.controller;

import java.util.Map;

import gameeditor.controller.interfaces.IGameObjectManager;
import objects.GameObject;

/**
 * This is an intermediate controller that manages game objects of the Game.
 * @author Ray Song(ys101)
 *
 */
public class GameObjectManager implements IGameObjectManager{
	private GameObject go;

	@Override
	public void createGameObject(double xPos, double yPos, double width, double height, String imageFileName,
			Map<String, String> properties) {
		// TODO Auto-generated method stub
		
	}
	
}
