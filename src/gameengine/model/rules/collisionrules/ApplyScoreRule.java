package gameengine.model.rules.collisionrules;

import gameengine.controller.RuleActionHandler;
import gameengine.model.rules.ScoreRulebook;
import objects.GameObject;

public class ApplyScoreRule implements CollisionRule{

	public static void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
		//TODO: not hard code key
		handler.modifyScore(Integer.parseInt(obj.getProperty("points")));
	}

}
