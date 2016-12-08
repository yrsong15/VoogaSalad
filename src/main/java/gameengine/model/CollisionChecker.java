package gameengine.model;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import exception.CollisionRuleNotFoundException;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.rules.CollisionRulebook;
import objects.GameObject;

public class CollisionChecker {
	private CollisionRulebook rulebook;
	private HashSet<GameObject> currentlyCollidingObjectsWithCharacter;
	

	public CollisionChecker(RuleActionHandler handler) {
		this.rulebook = new CollisionRulebook(handler);
		this.currentlyCollidingObjectsWithCharacter = new HashSet<>();
	}

	/**
	 * Passes the mainCharacter and any object colliding with it to the rulebook
	 * 
	 * @param mainChar
	 * @param gameObjects
	 */
	public void checkCollisions(GameObject mainChar, List<GameObject> gameObjects) {
		//System.out.println(currentlyCollidingObjectsWithCharacter.size());
		for (Iterator<GameObject> itr = gameObjects.iterator(); itr.hasNext();) {
			try {
				GameObject gameObject = itr.next();
				if (mainChar != gameObject && collision(mainChar, gameObject)) {
					try {
						if(!checkIfAlreadyCollided(gameObject)){
							rulebook.applyRules(mainChar, gameObject);
							//System.out.println("Adding object");
						}
						
					} catch (CollisionRuleNotFoundException e) {
						
					}
				}
				else removeGameObjectFromSet(gameObject);
			} catch (ConcurrentModificationException e) {
				checkCollisions(mainChar, gameObjects);
				break;
			}
		}
	}
	
	//Used in both platform apply rules for collision rules
	public void manuallyRemoveFromConcurrentCollisionList(GameObject obj) {
		removeGameObjectFromSet(obj);
	}
	
	private boolean checkIfAlreadyCollided(GameObject gameObject){
		if(!currentlyCollidingObjectsWithCharacter.contains(gameObject)){
			currentlyCollidingObjectsWithCharacter.add(gameObject);
			return false;
		}
		return true;
	}
	
	private void removeGameObjectFromSet(GameObject gameObject){
		if(currentlyCollidingObjectsWithCharacter.contains(gameObject)){
			currentlyCollidingObjectsWithCharacter.remove(gameObject);
			//System.out.println("Removing");
		}
	}

	// TO-DO: better way to check for collisions, not sure this encompasses
	// everything
	public boolean collision(GameObject character, GameObject other) {
		double charX = character.getXPosition();
		double charY = character.getYPosition();
		double otherX = other.getXPosition();
		double otherY = other.getYPosition();

		return charX < otherX + other.getWidth() && charX + character.getWidth() > otherX
				&& charY < otherY + other.getHeight() && charY + character.getHeight() > otherY;

	}



}
