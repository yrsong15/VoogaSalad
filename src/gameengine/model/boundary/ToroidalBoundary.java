package gameengine.model.boundary;

import objects.GameObject;

public class ToroidalBoundary extends BasicBoundary{

	public ToroidalBoundary(double width, double height) {
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
