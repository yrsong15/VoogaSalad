package gameengine.model.rules.movementrules;

import gameengine.controller.GameEngineController;
import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016. Modified by Eric Song
 */
public class ApplyGravityRule implements MovementRule {
	@Override
	public void applyRule(GameObject obj) {
		double gravity = Double.parseDouble(obj.getProperty("gravity"));
		double speed = modifySpeed(obj, gravity);
		obj.setYPosition(obj.getYPosition() + GameEngineController.SECOND_DELAY * speed);
	}

	private double modifySpeed(GameObject obj, double gravity) {
		double speed = Double.parseDouble(obj.getProperty("fallspeed"));
		speed += GameEngineController.MILLISECOND_DELAY * gravity;
		obj.setProperty("fallspeed", Double.toString(speed));
		return speed;
	}
}
