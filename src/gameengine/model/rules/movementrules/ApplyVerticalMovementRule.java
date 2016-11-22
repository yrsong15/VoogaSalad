package gameengine.model.rules.movementrules;

import objects.GameObject;

/**
 * Created by Soravit on 11/22/2016.
 */
public class ApplyVerticalMovementRule implements MovementRule {
    @Override
    public void applyRule(GameObject obj) {
        int moveSpeed = Integer.parseInt(obj.getProperty("verticalmovement"));
        obj.setYPosition(obj.getYPosition() + moveSpeed);
    }
}
