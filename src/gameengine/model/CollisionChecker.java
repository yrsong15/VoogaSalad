package gameengine.model;

import java.util.Iterator;
import java.util.List;

import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.rules.CollisionRulebook;
import objects.GameObject;

public class CollisionChecker {
	private CollisionRulebook rulebook;
	
	public CollisionChecker(RuleActionHandler handler){
		this.rulebook = new CollisionRulebook(handler);
	}
	
	/**
	 * Passes the mainCharacter and any object colliding with it to the rulebook
	 * @param mainChar
	 * @param gameObjects
	 */
	public void checkCollisions(GameObject mainChar, List<GameObject> gameObjects){
		for(Iterator<GameObject> itr = gameObjects.iterator(); itr.hasNext();){
			GameObject gameObject = itr.next();
			if (collision(mainChar, gameObject)){
				try {
					rulebook.applyRules(mainChar,gameObject);
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
