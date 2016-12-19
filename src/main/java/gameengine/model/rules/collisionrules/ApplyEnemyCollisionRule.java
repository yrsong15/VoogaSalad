package gameengine.model.rules.collisionrules;

import gameengine.controller.GameEngineViewController;
import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyEnemyCollisionRule{
	
	public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		if(mainChar.getYPosition() + mainChar.getHeight() - GameEngineViewController.FRAMES_PER_SECOND/3 < obj.getYPosition()){
			mainChar.killSpeed();
			handler.removeObject(obj);
			return;
		}
		takeDamage(handler,mainChar,obj);
    }
	
	protected void takeDamage(RuleActionHandler handler, GameObject mainChar, GameObject obj){
		int currHealth = Integer.parseInt(mainChar.getProperty("health"));
		currHealth -= Integer.parseInt(obj.getProperty("enemy"));
		if (currHealth <= 0) {
			handler.loseGame();
		}
		else {
			mainChar.setProperty("health", Integer.toString(currHealth));
		}
	}
}
