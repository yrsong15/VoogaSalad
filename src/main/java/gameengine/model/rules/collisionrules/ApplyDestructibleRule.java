package gameengine.model.rules.collisionrules;

import gameengine.controller.SingletonBoundaryChecker;
import gameengine.controller.SingletonBoundaryChecker.IntersectionAmount;
import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyDestructibleRule extends ApplyNonIntersectableRule{
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		mainChar.killSpeed();
		handler.removeObject(obj);
	}
	
