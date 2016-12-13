package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyBounceBackMovementShiftRule implements CollisionRule{
	
	public static void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		System.out.println("applying");
		String horizontalSpeed = obj.getProperty("horizontalmovement");
		int horiz = Integer.parseInt(horizontalSpeed);
		horiz = horiz*-1;
		obj.setProperty("horizontalMovement", String.valueOf(horiz));
		handler.removeObject(obj);
}

}
