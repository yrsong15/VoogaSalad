package gameengine.model;

import gameengine.model.rules.MovementRulebook;
import objects.GameObject;

import java.util.List;

/**
 * Created by Soravit on 11/22/2016.
 */
public class MovementChecker {
    private MovementRulebook movementRulebook;

    public MovementChecker(){
        movementRulebook = new MovementRulebook();
    }

    public void updateMovement(List<GameObject> gameObjects) throws InstantiationException, ClassNotFoundException {
        for(GameObject gameObject: gameObjects){
            movementRulebook.applyRules(gameObject);
        }
    }
}
