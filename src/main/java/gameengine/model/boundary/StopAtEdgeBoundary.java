package gameengine.model.boundary;

import objects.GameObject;

public class StopAtEdgeBoundary extends BasicBoundary{
	
	public StopAtEdgeBoundary(double width, double height) {
		super(width, height);
	}


	@Override
	public boolean moveToXPos(GameObject toMove, double newXPos) {
		if(newXPos <= getScreenWidth()-toMove.getWidth() && newXPos >= 0){
			toMove.setXPosition(newXPos);
			return true;
		}
		else{
			toMove.killSpeed();
			return false;
		}
		
	}


	@Override
	public boolean moveToYPos(GameObject toMove, double newYPos) {
		if (newYPos <= getScreenHeight()-toMove.getHeight()*1.8 && newYPos >= 0){
			toMove.setYPosition(newYPos);
			return true;
		}		
		else{
			toMove.killSpeed();
			return false;
		}
	}

}
