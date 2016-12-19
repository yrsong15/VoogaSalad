package gameengine.model.rules.movementrules;

import gameengine.controller.GameEngineViewController;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016. Modified by Eric Song, Chalena Scholl
 */
public class ApplyVerticalMovementRule implements MovementRule {

	@Override
	public void applyRule(GameObject obj, ControlInterface gameMovement, GameBoundary gameBoundaries) {
		double movespeed = Double.parseDouble(obj.getProperty("verticalmovement"));
		double newYPos = obj.getYPosition() + GameEngineViewController.SECOND_DELAY * movespeed;
		if(newYPos > obj.getYPosition()){
			gameMovement.moveDown(obj, GameEngineViewController.SECOND_DELAY * movespeed);
		}
		else if (newYPos < obj.getYPosition()){
			gameMovement.moveUp(obj, GameEngineViewController.SECOND_DELAY * movespeed*-1);
		}
	}
}
