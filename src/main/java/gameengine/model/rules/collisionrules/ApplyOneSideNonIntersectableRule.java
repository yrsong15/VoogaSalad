package gameengine.model.rules.collisionrules;

<<<<<<< HEAD
import gameengine.controller.SingletonBoundaryChecker;
import gameengine.controller.SingletonBoundaryChecker.IntersectionAmount;
=======
>>>>>>> 375b21238c5648174731f58d886ac3721116ad8b
import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyOneSideNonIntersectableRule extends ApplyNonIntersectableRule{
<<<<<<< HEAD
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		boolean passableTop = obj.getProperty("onewaynonintersectable").equals("top");
		boolean passableBottom = obj.getProperty("onewaynonintersectable").equals("bottom");
		
		IntersectionAmount intersectionAmountY = SingletonBoundaryChecker.getInstance().getVerticalIntersectionAmount(mainChar, obj);
		
		//Difficult to read but basically saying, apply blockage if you're trying to hit from top or bottom depending on status of platform/passability of platform
		if((((intersectionAmountY == IntersectionAmount.COMPLETELY_INSIDE_Y) || (intersectionAmountY == IntersectionAmount.PARTIALLY_ABOVE)) && passableTop)
			|| (((intersectionAmountY == IntersectionAmount.COMPLETELY_INSIDE_Y) || (intersectionAmountY == IntersectionAmount.PARTIALLY_BELOW)) && passableBottom))
		{
			applyPlatformCollision(handler,mainChar,obj);
		}

	}
	
=======
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
>>>>>>> 375b21238c5648174731f58d886ac3721116ad8b
}
