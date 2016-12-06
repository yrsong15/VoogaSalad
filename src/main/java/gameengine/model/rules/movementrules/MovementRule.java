package gameengine.model.rules.movementrules;

import gameengine.model.boundary.ScreenBoundary;
import objects.GameObject;

public interface MovementRule {

    public void applyRule(GameObject obj, ScreenBoundary gameBoundary);

}
