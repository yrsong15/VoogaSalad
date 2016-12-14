package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyDamageRule extends ApplyEnemyCollisionRule implements CollisionRule {

	
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		int currHealth = Integer.parseInt(mainChar.getProperty("health"));
		currHealth -= Integer.parseInt(obj.getProperty("damage"));
		if (currHealth <= 0) {
			handler.endGame();
		}
		else {
			mainChar.setProperty("health", Integer.toString(currHealth));
		}
	}

}
