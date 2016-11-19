package gameengine.model.rules.collisionrules;

import gameengine.controller.GameObjectRemoveHandler;
import objects.GameObject;

public class RemoveObjectRule extends CollisionRule{

	private static RemoveObjectRule instance = null;
	private GameObjectRemoveHandler gameObjectRemoveHandler;

	protected RemoveObjectRule() {
	}

	public static RemoveObjectRule getInstance() {
		if (instance == null) {
			instance = new RemoveObjectRule();
		}
		return instance;
	}
	
	public void setGameObjectRemoveHandler(GameObjectRemoveHandler gameObjectRemoveHandler){
		this.gameObjectRemoveHandler = gameObjectRemoveHandler;
	}

	@Override
	public void applyRule(GameObject mainChar, GameObject obj) {
			gameObjectRemoveHandler.removeObject(obj);
	}

}
