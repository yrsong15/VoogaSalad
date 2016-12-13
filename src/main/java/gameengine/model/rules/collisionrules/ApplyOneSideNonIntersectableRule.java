package gameengine.model.rules.collisionrules;

import gameengine.controller.SingletonBoundaryChecker;
import gameengine.controller.SingletonBoundaryChecker.IntersectionAmount;
import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyOneSideNonIntersectableRule extends ApplyNonIntersectableRule{
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		boolean passableTop = obj.getProperty("onewaynonintersectable").equals("top");
		boolean passableBottom = obj.getProperty("onewaynonintersectable").equals("bottom");
		
		IntersectionAmount intersectionAmountY = SingletonBoundaryChecker.getInstance().getVerticalIntersectionAmount(mainChar, obj);
		
		//Difficult to read but basically saying, apply blockage if you're trying to hit from top or bottom depending on status of platform/passability of platform
		if((((intersectionAmountY == IntersectionAmount.PARTIALLY_ABOVE)) && passableTop)
			|| (((intersectionAmountY == IntersectionAmount.PARTIALLY_BELOW)) && passableBottom))
		{
			this.isOneSided = true;
			applyPlatformCollision(handler,mainChar,obj);
		}

	}
	
}
