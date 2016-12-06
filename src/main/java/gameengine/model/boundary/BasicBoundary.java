package gameengine.model.boundary;

import objects.GameObject;

public abstract class BasicBoundary implements ScreenBoundary{
	private double screenWidth;
	private double screenHeight;
	
	
	public BasicBoundary(double width, double height){
		screenWidth = width;
		screenHeight = height;
	}
	
	protected double getScreenHeight(){
		return screenHeight;
	}
	
	protected double getScreenWidth(){
		return screenWidth;
	}

	public void moveGameObject(GameObject toMove, double newXPos, double newYPos) {
		moveToXPos(toMove, newXPos);
		moveToYPos(toMove, newYPos);
	}

	@Override
	public abstract void moveToXPos(GameObject toMove, double newXPos);

	@Override
	public abstract void moveToYPos(GameObject toMove, double newYPos);

}
