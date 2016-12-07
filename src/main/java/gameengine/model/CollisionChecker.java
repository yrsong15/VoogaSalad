package gameengine.model;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import exception.CollisionRuleNotFoundException;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.rules.CollisionRulebook;
import objects.GameObject;

public class CollisionChecker {
	private CollisionRulebook rulebook;

	public CollisionChecker(RuleActionHandler handler) {
		this.rulebook = new CollisionRulebook(handler);
	}

	public void checkCollisions(List<GameObject> firstObjects, List<GameObject> secondObjects){
        for (int i = 0; i < firstObjects.size(); i++) {
            for (int j = 0; j < secondObjects.size(); j++) {
                try {
                    GameObject firstObject = firstObjects.get(i);
                    GameObject secondObject = secondObjects.get(j);
                    if (firstObject != secondObject && collision(firstObject, secondObject)) {
                        try {
                            rulebook.applyRules(firstObject, secondObject);
                        } catch (CollisionRuleNotFoundException e) {

                        }
                    }
                } catch (ConcurrentModificationException e) {
                    checkCollisions(firstObjects, secondObjects);
                    break;
                }
            }
        }
    }

	public boolean collision(GameObject character, GameObject other) {
		double charX = character.getXPosition();
		double charY = character.getYPosition();
		double otherX = other.getXPosition();
		double otherY = other.getYPosition();

		return charX < otherX + other.getWidth() && charX + character.getWidth() > otherX
				&& charY < otherY + other.getHeight() && charY + character.getHeight() > otherY;

	}

}
