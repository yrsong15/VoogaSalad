package gameengine.model.rules.collisionrules;
import objects.GameObject;

public class ApplyScoreRule extends CollisionRule{

    private static ApplyScoreRule instance = null;

    protected ApplyScoreRule() {
    }

    public static ApplyScoreRule getInstance() {
        if (instance == null) {
            instance = new ApplyScoreRule();
        }
        return instance;
    }

    @Override
    public void applyRule(GameObject mainChar, GameObject obj) {
        RuleActionHandler ruleHandler;
        ruleHandler.modifyScore();
    }
}