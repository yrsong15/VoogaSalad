package gameengine.model.rules.movementrules;

import gameengine.controller.GameEngineController;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.boundary.ScreenBoundary;
import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016. Modified by Eric Song, Chalena Scholl
 */
public class ApplyVerticalMovementRule implements MovementRule {

	@Override
	public void applyRule(GameObject obj, ControlInterface gameMovement, ScreenBoundary gameBoundaries) {
		double movespeed = Double.parseDouble(obj.getProperty("verticalmovement"));
		//gameBoundaries.moveToYPos(obj, obj.getYPosition() + GameEngineController.SECOND_DELAY * movespeed);
		obj.setYDistanceMoved(obj.getYDistanceMoved() + GameEngineController.SECOND_DELAY * movespeed);		
	}


}
