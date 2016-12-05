package gameengine.model.boundary;

import objects.GameObject;

public class NoBoundary implements ScreenBoundary{

	public void moveGameObject(GameObject toMove, double newXPos, double newYPos) {
		toMove.setXPosition(newXPos);
		toMove.setYPosition(newYPos);
	}

}
