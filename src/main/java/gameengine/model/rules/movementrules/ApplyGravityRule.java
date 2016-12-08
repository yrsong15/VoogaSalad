package gameengine.model.rules.movementrules;

import gameengine.controller.GameEngineController;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.boundary.ScreenBoundary;
import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016. Modified by Eric Song, Chalena Scholl
 */
public class ApplyGravityRule implements MovementRule {
	@Override
	public void applyRule(GameObject obj, ControlInterface gameMovement, ScreenBoundary gameBoundaries) {
		double gravity = Double.parseDouble(obj.getProperty("gravity"));
		double speed = modifySpeed(obj, gravity);
		double newYPos = obj.getYPosition() + GameEngineController.SECOND_DELAY * speed;
		System.out.println(obj.getYPosition() + " " +  newYPos);
		//System.out.println(newYPos);
		/**
		if(newYPos != obj.getYPosition()){
			if(newYPos > obj.getYPosition()){
				System.out.println("speed:  " + GameEngineController.SECOND_DELAY * speed);
				gameMovement.moveDown(obj, GameEngineController.SECOND_DELAY * speed);
			}
			else{
				gameMovement.moveUp(obj, GameEngineController.SECOND_DELAY * speed*-1);
			}
		}**/
		//System.out.println("speed:  " + GameEngineController.SECOND_DELAY * speed);
		//System.out.println("moving from y " + obj.getYPosition() + " to y: " + newYPos);
		gameBoundaries.moveToYPos(obj, obj.getYPosition() + GameEngineController.SECOND_DELAY * speed);
        obj.setYDistanceMoved(obj.getYDistanceMoved() + GameEngineController.SECOND_DELAY * speed);
    }

	private double modifySpeed(GameObject obj, double gravity) {
		double speed = Double.parseDouble(obj.getProperty("fallspeed"));
		speed += GameEngineController.MILLISECOND_DELAY * gravity;
		obj.setProperty("fallspeed", Double.toString(speed));
		return speed;
	}
}
