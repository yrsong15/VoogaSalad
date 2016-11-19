package gameengine.model.rules.collisionrules;

import objects.GameObject;

public abstract class CollisionRule {

	public abstract void applyRule(GameObject mainChar, GameObject obj);

}
