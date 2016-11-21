package gameengine.model;

import java.util.List;

import gameengine.controller.RuleActionHandler;
import gameengine.model.rules.CollisionRulebook;
import objects.GameObject;

public class CollisionChecker {
	private CollisionRulebook rulebook;
	
	public CollisionChecker(){
		this.rulebook = new CollisionRulebook();
	}
	
	/**
	 * Passes the mainCharacter and any object colliding with it to the rulebook
	 * @param mainChar
	 * @param gameObjects
	 * @param ruleActionHandler 
	 */
	public void checkCollisions(GameObject mainChar, List<GameObject> gameObjects, RuleActionHandler handler){
		for (GameObject obj: gameObjects){
			if (collision(mainChar, obj)){
				try {
					rulebook.applyRules(mainChar, obj, handler);
				} catch (ClassNotFoundException e) {
					// TODO handle this error
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//TO-DO: better way to check for collisions, not sure this encompasses everything
	public boolean collision(GameObject character, GameObject other){
		double charX = character.getXPosition();
		double charY = character.getYPosition();
		double otherX = other.getXPosition();
		double otherY = other.getYPosition();

		return charX < otherX + other.getWidth() 
				&& charX + character.getWidth() > otherX 
				&& charY < otherY + other.getHeight() 
				&& charY + character.getHeight() > otherY;

	}


}
