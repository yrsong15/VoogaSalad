package gameengine.model.rules.movementrules;

import gameengine.controller.GameEngineController;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016. Modified by Chalena Scholl
 */
public class ApplyHorizontalMovementRule implements MovementRule{
	@Override
	public void applyRule(GameObject obj, ControlInterface gameMovement, GameBoundary gameBoundaries) {
        double moveSpeed = Double.parseDouble(obj.getProperty("horizontalmovement"));
        double newXPos = obj.getXPosition() + moveSpeed;
        
		if(newXPos > obj.getXPosition()){
			gameMovement.moveRight(obj, moveSpeed);
		}
		else if (newXPos < obj.getXPosition()){
			gameMovement.moveLeft(obj, moveSpeed);
		}
	}
}
