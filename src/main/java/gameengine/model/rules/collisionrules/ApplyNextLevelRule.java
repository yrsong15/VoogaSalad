package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

public class ApplyNextLevelRule implements CollisionRule{
	
    public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
    	//System.out.println("next level");
        handler.goNextLevel();
}

}
