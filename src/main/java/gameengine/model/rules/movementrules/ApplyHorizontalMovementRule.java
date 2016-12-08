package gameengine.model.rules.movementrules;

import gameengine.controller.GameEngineController;
import gameengine.model.boundary.ScreenBoundary;
import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016.
 */
public class ApplyHorizontalMovementRule implements MovementRule{
    @Override
    public void applyRule(GameObject obj, ScreenBoundary gameBoundaries) {
        double moveSpeed = Double.parseDouble(obj.getProperty("horizontalmovement"));
        gameBoundaries.moveToXPos(obj, obj.getXPosition() + moveSpeed);
        obj.setXDistanceMoved(obj.getXDistanceMoved() + GameEngineController.SECOND_DELAY * moveSpeed);
    }
}
