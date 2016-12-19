package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyNonIntersectableRule implements CollisionRule{
	private static final double zero = 0.0;
	protected boolean isOneSided = false;
	
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		applyPlatformCollision(handler,mainChar,obj);
	}
	
	protected void applyPlatformCollision(RuleActionHandler handler, GameObject mainChar, GameObject obj){
		handler.resetObjectPosition(mainChar, obj,isOneSided);
		mainChar.killSpeed();
		// Need to do below because the previous frame makes it still think it's colliding, need to manually remove the object from the collision list so it can collide again and give the appearance of standing on platform
		handler.removeFromCollidedList(obj);
	}
	
}
