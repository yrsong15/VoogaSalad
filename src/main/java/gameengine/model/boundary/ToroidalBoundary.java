package gameengine.model.boundary;

import objects.GameObject;

public class ToroidalBoundary extends BasicBoundary{
	
	boolean movedToroidally;

	public ToroidalBoundary(double width, double height) {
		super(width, height);
	}
	
	public ToroidalBoundary(double viewWidth, double viewHeight, double worldWidth, double worldHeight){
		super(viewWidth, viewHeight, worldWidth, worldHeight);
	}
	
	@Override
	public boolean moveToXPos(GameObject toMove, double newXPos, double speed) {
		if (getViewWidth()-toMove.getWidth() <= newXPos){
			toMove.setXPosition(0);
		}
		else if (newXPos <= 0-toMove.getWidth()){
			toMove.setXPosition(getViewWidth()-toMove.getWidth());
		}
		else{
			toMove.setVelX(speed);
		}
		return true;
	}

	@Override
	public boolean moveToYPos(GameObject toMove, double newYPos) {

			toMove.setYPosition(newYPos);
		return true;
	}


}
