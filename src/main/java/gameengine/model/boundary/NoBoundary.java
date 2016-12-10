package gameengine.model.boundary;

import objects.GameObject;

public class NoBoundary extends BasicBoundary{

	public NoBoundary(double width, double height) {
		super(width, height);
	}
	
	
	@Override
	public void moveToXPos(GameObject toMove, double newXPos) {
		toMove.setXPosition(newXPos);	
	}

	@Override
	public void moveToYPos(GameObject toMove, double newYPos) {
		toMove.setYPosition(newYPos);		
	}
}
