package gameengine.controller.interfaces;

import objects.GameObject;

/**
 * @author Eric Song
 *
 */
public interface RuleActionHandler {
	public void removeObject(GameObject obj);
	public void endGame();
	public void modifyScore(long ID, int score);
	public void resetObjectPosition(GameObject mainChar, GameObject obj, boolean isOneSided);
	public void removeFromCollidedList(GameObject obj);
	public void winGame();
	public void goNextLevel();
	public boolean reachedScore(int score);
	public int getTime();
	public long getPlayerID(GameObject object);
}
