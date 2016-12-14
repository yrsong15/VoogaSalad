package gameengine.model.rules.movementrules;

import gameengine.controller.interfaces.ControlInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.rules.collisionrules.CollisionRule;
import objects.GameObject;

public class ApplyBounceBackMovementShiftRule implements MovementRule{
	
	public void applyRule(GameObject obj, ControlInterface gameMovement, GameBoundary gameBoundaries) {
		int bounceBackGoal = Integer.parseInt(obj.getProperty("bounceBack"));
		int bounceTracker =  Integer.parseInt(obj.getProperty("bounceTracker"));
		
		if  (bounceTracker == bounceBackGoal){
			String bounceSpeed = obj.getProperty("bounceSpeed");
			double bounce = Double.parseDouble(bounceSpeed);
			bounce = bounce*-1;
			obj.setProperty("bounceSpeed", String.valueOf(bounce));
			obj.setProperty("bounceTracker", String.valueOf(0));
		}
		else{
			obj.setProperty("bounceTracker", String.valueOf(bounceTracker+1));
	        ApplyHorizontalMovementRule.moveObj(gameMovement, obj, Double.parseDouble(obj.getProperty("bounceSpeed")));
		}
		

}

}
