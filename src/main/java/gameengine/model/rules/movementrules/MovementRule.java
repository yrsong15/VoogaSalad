package gameengine.model.rules.movementrules;

import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;

public interface MovementRule {


	//public void applyRule(GameObject obj, ControlInterface gameMovement);

	public void applyRule(GameObject obj, ControlInterface gameMovement, GameBoundary gameBoundaries);

}
