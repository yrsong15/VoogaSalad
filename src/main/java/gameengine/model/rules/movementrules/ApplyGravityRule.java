package gameengine.model.rules.movementrules;

import gameengine.controller.GameEngineViewController;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016. Modified by Eric Song, Chalena Scholl
 */
public class ApplyGravityRule implements MovementRule {
	@Override
	public void applyRule(GameObject obj, ControlInterface gameMovement, GameBoundary gameBoundaries) {
		double gravity = Double.parseDouble(obj.getProperty("gravity"));
		double speed = modifySpeed(obj, gravity);
		double newYPos = obj.getYPosition() + GameEngineViewController.SECOND_DELAY * speed;
		if(newYPos > obj.getYPosition() && !obj.isOnPlatform()){
			gameMovement.moveDown(obj, GameEngineViewController.SECOND_DELAY * speed);
		}
		else if (newYPos < obj.getYPosition()){
			gameMovement.moveUp(obj, GameEngineViewController.SECOND_DELAY * speed*-1);
		}
    }

	private double modifySpeed(GameObject obj, double gravity) {
		double speed = Double.parseDouble(obj.getProperty("fallspeed"));
		speed += GameEngineViewController.MILLISECOND_DELAY * gravity;
		obj.setProperty("fallspeed", Double.toString(speed));
		return speed;
	}
}
