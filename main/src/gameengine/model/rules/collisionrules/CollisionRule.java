package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

/**
 * @author Eric Song
 * 
 */
public interface CollisionRule {

	//doesnt actually enforce implementation, but all implementing classes should implement this method
	public static void applyRule(GameObject mainChar, GameObject obj, RuleActionHandler handler) {
	}

}
