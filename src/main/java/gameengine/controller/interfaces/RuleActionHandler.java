package gameengine.controller.interfaces;

import objects.GameObject;

/**
 * @author Eric Song
 *
 */
public interface RuleActionHandler {

	public void removeObject(GameObject obj);
	public void endGame();
	public void modifyScore(int score);
	public void resetObjectPosition(GameObject mainChar);
	public void winGame();
}
