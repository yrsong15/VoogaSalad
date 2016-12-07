package gameengine.model.rules.collisionrules;

import gameengine.controller.GameEngineController;
import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyNonIntersectableRule implements CollisionRule{
	private static final double zero = 0.0;
	
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		mainChar.killSpeed();
		handler.resetObjectPosition(mainChar);
	}
	
}
