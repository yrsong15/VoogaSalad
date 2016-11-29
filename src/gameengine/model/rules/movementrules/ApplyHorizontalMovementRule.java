package gameengine.model.rules.movementrules;

import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016.
 */
public class ApplyHorizontalMovementRule implements MovementRule{
    @Override
    public void applyRule(GameObject obj) {
        int moveSpeed = Integer.parseInt(obj.getProperty("horizontalmovement"));
        obj.setXPosition(obj.getXPosition() + moveSpeed);
    }
}
