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
	}


	@Override
	public void moveToYPos(GameObject toMove, double newYPos) {
		System.out.println("checking y");
		if (newYPos <= getScreenHeight()-toMove.getHeight() && newYPos >= 0){
			toMove.setYPosition(newYPos);
		}		
	}

}
