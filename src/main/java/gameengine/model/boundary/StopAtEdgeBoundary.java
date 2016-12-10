package gameengine.model.boundary;

import objects.GameObject;

public class StopAtEdgeBoundary extends BasicBoundary{
	
	public StopAtEdgeBoundary(double width, double height) {
		super(width, height);
	}


	@Override
	public void moveToXPos(GameObject toMove, double newXPos) {
		if(newXPos <= getScreenWidth()-toMove.getWidth() && newXPos >= 0){
			toMove.setXPosition(newXPos);
		}
		else{
			toMove.killSpeed();
		}		
	}

	@Override
	public void moveToYPos(GameObject toMove, double newYPos) {
		if (newYPos <= getScreenHeight()-toMove.getHeight()*1.8 && newYPos >= 0){
			toMove.setYPosition(newYPos);
		}		
		else{
			toMove.killSpeed();
		}
	}

}
