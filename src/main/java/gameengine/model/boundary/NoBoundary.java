package gameengine.model.boundary;

import objects.GameObject;

public class NoBoundary extends BasicBoundary{

	public NoBoundary(double width, double height) {
		super(width, height);
	}
	
	public NoBoundary(double viewWidth, double viewHeight, double worldWidth, double worldHeight){
		super(viewWidth, viewHeight, worldWidth, worldHeight);
	}
	
	
	@Override
	public boolean moveToXPos(GameObject toMove, double newPosition, double speed) {
		System.out.println("set");
		toMove.setVelX(speed);
		return true;
	}

	@Override
	public boolean moveToYPos(GameObject toMove, double newYPos) {
		toMove.setYPosition(newYPos);	
		return true;
	}
	
	

}
