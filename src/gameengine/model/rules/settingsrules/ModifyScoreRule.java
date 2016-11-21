package gameengine.model.rules.settingsrules;

import gameengine.model.rules.ScoreRulebook;
import gameengine.model.rules.collisionrules.CollisionRule;
import objects.GameObject;
import objects.Level;

public class ModifyScoreRule extends SettingsRule {

	@Override
	public void applyRule(Level level, int num){
        level.setScore(level.getScore() + num);
	}

}
