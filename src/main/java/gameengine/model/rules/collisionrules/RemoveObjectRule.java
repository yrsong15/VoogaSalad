package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class RemoveObjectRule implements CollisionRule{
	
	public static void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
			handler.removeObject(obj);
	}
	
}
