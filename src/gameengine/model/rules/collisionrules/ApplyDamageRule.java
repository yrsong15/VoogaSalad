package gameengine.model.rules.collisionrules;

import gameengine.controller.GameOverHandler;
import objects.GameObject;

public class ApplyDamageRule extends CollisionRule {

	private static ApplyDamageRule instance = null;
	private GameOverHandler gameOverHandler;

	protected ApplyDamageRule() {
	}

	public static ApplyDamageRule getInstance() {
		if (instance == null) {
			instance = new ApplyDamageRule();
		}
		return instance;
	}

	public void setGameOverHandler(GameOverHandler gameOverHandler) {
		this.gameOverHandler = gameOverHandler;
	}

	@Override
	public void applyRule(GameObject mainChar, GameObject obj) {
		int currHealth = Integer.parseInt(mainChar.getProperty("health"));
		currHealth -= Integer.parseInt(obj.getProperty("damage"));
		if (currHealth <= 0)
			gameOverHandler.endGame();
		else
			mainChar.setProperty("health", Integer.toString(currHealth));

	}

}
