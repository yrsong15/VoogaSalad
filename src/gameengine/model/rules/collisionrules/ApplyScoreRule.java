package gameengine.model.rules.collisionrules;

import gameengine.model.rules.ScoreRulebook;
import objects.GameObject;

public class ApplyScoreRule extends CollisionRule{

	private static ApplyScoreRule instance = null;
	private ScoreRulebook scoreRulebook;

	protected ApplyScoreRule() {
	}

	public static ApplyScoreRule getInstance() {
		if (instance == null) {
			instance = new ApplyScoreRule();
		}
		return instance;
	}
	
	public void setIPointsRulebook(ScoreRulebook scoreRulebook){
		this.scoreRulebook = scoreRulebook;
	}

	@Override
	public void applyRule(GameObject mainChar, GameObject obj) {
		//TODO: not hard code key
		scoreRulebook.modifyScore(Integer.parseInt(obj.getProperty("points")));
	}

}
