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
	public boolean moveToXPos(GameObject toMove, double newXPos) {
		if (getWorldWidth()-toMove.getWidth() <= newXPos){
			toMove.setXPosition(0);
		}
		else if (newXPos <= 0-toMove.getWidth()){
			toMove.setXPosition(getWorldWidth()-toMove.getWidth());
		}
		else{
			toMove.setXPosition(newXPos);
		}
		return true;
	}

	@Override
	public boolean moveToYPos(GameObject toMove, double newYPos) {
		if (getViewHeight() <= newYPos){
			toMove.setYPosition(0);
		}
		else if (newYPos <= 0){
			toMove.setYPosition(getWorldHeight()-toMove.getHeight());
		}
		else{
			toMove.setYPosition(newYPos);
		}	
		return true;
	}


}
