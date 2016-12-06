package gameengine.model.boundary;

import objects.GameObject;

public class ToroidalBoundary extends BasicBoundary{

	public ToroidalBoundary(double width, double height) {
		super(width, height);
	}
	
	@Override
	public void moveToXPos(GameObject toMove, double newXPos) {
		if (getScreenWidth() <= newXPos){
			toMove.setXPosition(0);
		}
		else if (newXPos <= 0-toMove.getWidth()){
			toMove.setXPosition(getScreenWidth()-toMove.getWidth());
		}
		else{
			toMove.setXPosition(newXPos);
		}
	}

	@Override
	public void moveToYPos(GameObject toMove, double newYPos) {
		if (getScreenHeight() <= newYPos){
			toMove.setYPosition(0);
		}
		else if (newYPos <= 0){
			toMove.setYPosition(getScreenHeight()-toMove.getHeight());
		}
		else{
			toMove.setYPosition(newYPos);
		}	
	}


}
