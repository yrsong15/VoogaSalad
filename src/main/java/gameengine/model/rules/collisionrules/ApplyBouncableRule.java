package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

/**
 * Created by Soravit on 12/11/2016.
 */
public class ApplyBouncableRule {

    public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
        mainChar.killSpeed();
        handler.removeObject(obj);
    }
}
