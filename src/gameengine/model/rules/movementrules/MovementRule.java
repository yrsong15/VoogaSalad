package gameengine.model.rules.movementrules;

import objects.GameObject;

public interface MovementRule {

    //doesnt actually enforce implementation, but all implementing classes should implement this method
    public void applyRule(GameObject obj);

}
