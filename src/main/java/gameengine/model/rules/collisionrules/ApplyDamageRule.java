package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyDamageRule extends ApplyEnemyCollisionRule implements CollisionRule {

	
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		takeDamage(handler,mainChar,obj);
	}

}
