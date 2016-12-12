package gameengine.model.boundary;

import objects.GameObject;

public class StopAtEdgeBoundary extends BasicBoundary{
	
	public StopAtEdgeBoundary(double width, double height) {
		super(width, height);
	}
	
	public StopAtEdgeBoundary(double viewWidth, double viewHeight, double worldWidth, double worldHeight){
		super(viewWidth, viewHeight, worldWidth, worldHeight);
	}


	@Override
	public boolean moveToXPos(GameObject toMove, double newXPos) {
		if(newXPos <= getViewWidth()-toMove.getWidth() && newXPos >= 0){
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
		if (newYPos <= getViewHeight()-toMove.getHeight()*1.8 && newYPos >= 0){
			toMove.setYPosition(newYPos);
			return true;
		}		
		else{
			toMove.killSpeed();
			return false;
		}
	}

}
