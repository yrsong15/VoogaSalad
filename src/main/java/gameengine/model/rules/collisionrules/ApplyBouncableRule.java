package gameengine.model.rules.collisionrules;

import gameengine.controller.SingletonBoundaryChecker;
import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;

/**
 * Created by Soravit on 12/11/2016.
 */
public class ApplyBouncableRule {

    public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
        boolean passableTop = obj.getProperty("onewaynonintersectable").equals("top");
        boolean passableBottom = obj.getProperty("onewaynonintersectable").equals("bottom");

        SingletonBoundaryChecker.IntersectionAmount intersectionAmountY = SingletonBoundaryChecker.getInstance().getVerticalIntersectionAmount(mainChar, obj);

        //Difficult to read but basically saying, apply blockage if you're trying to hit from top or bottom depending on status of platform/passability of platform
        if((((intersectionAmountY == SingletonBoundaryChecker.IntersectionAmount.COMPLETELY_INSIDE_Y) || (intersectionAmountY == SingletonBoundaryChecker.IntersectionAmount.PARTIALLY_ABOVE)) && passableTop)
                || (((intersectionAmountY == SingletonBoundaryChecker.IntersectionAmount.COMPLETELY_INSIDE_Y) || (intersectionAmountY == SingletonBoundaryChecker.IntersectionAmount.PARTIALLY_BELOW)) && passableBottom))
        {
            String jumpVelocity = obj.getProperty("bounce");
            if(jumpVelocity!=null){
                mainChar.setProperty("fallspeed", "-" + jumpVelocity);
            }
        }

    }

}
