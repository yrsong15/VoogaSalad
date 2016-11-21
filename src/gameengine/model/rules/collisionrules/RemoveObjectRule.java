package gameengine.model.rules.collisionrules;

import gameengine.controller.RuleActionHandler;
import objects.GameObject;

public class RemoveObjectRule implements CollisionRule{
	
	public static void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
			handler.removeObject(obj);
			//later will need to check if mainChar is above or below enemies (to see whether the enemy dies or if mainChar takes damage)
	}
	
}
