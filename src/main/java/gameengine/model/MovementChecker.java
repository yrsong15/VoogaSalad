package gameengine.model;

import gameengine.model.boundary.ScreenBoundary;
import gameengine.model.rules.MovementRulebook;
import objects.GameObject;

import java.util.List;

import exception.MovementRuleNotFoundException;
import objects.Level;

/**
 * Created by Soravit on 11/22/2016. Modified by Chalena Scholl.
 */
public class MovementChecker {

    private MovementRulebook movementRulebook;

    public MovementChecker(ScreenBoundary gameBoundaries){
        movementRulebook = new MovementRulebook(gameBoundaries);
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
