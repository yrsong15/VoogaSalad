package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;

import general.GameExamples;
import objects.GameObject;

public class ApplyScoreRule implements CollisionRule{

    public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
            handler.modifyScore(handler.getPlayerID(mainChar), Integer.parseInt(obj.getProperty("points")));
            GameExamples.score += Integer.parseInt(obj.getProperty("points"));
    }
}