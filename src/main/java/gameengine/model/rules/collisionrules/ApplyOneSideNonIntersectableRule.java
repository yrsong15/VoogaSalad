package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyOneSideNonIntersectableRule extends ApplyNonIntersectableRule{
	public enum Directions {TOP,LEFT,DOWN,RIGHT};
	
	private Directions direction; // Potentially future extension w/ side accessibility or platforms
	
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {

		if(mainChar.getYPosition() <= obj.getYPosition())
		{
			applyPlatformCollision(handler,mainChar,obj);
		}
	}
	
	public void setDirection(Directions direction){
		this.direction = direction;
	}
}
