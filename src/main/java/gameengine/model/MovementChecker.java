package gameengine.model;

import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.boundary.ScreenBoundary;
import gameengine.model.rules.MovementRulebook;
import objects.GameObject;
import exception.MovementRuleNotFoundException;
import objects.Level;

/**
 * Created by Soravit on 11/22/2016. Modified by Chalena Scholl.
 */
public class MovementChecker {

    private MovementRulebook movementRulebook;

    public MovementChecker(ControlInterface gameMovement, ScreenBoundary gameBoundaries){
        movementRulebook = new MovementRulebook(gameMovement, gameBoundaries);
    }

    public void updateMovement(Level level){
        for(GameObject gameObject: level.getAllGameObjects()){
            try {
				movementRulebook.applyRules(gameObject);
			} catch (MovementRuleNotFoundException e) {
			}
        }
    }
}
