package gameengine.model.boundary;

import objects.GameObject;

public class StopAtEdgeBoundary implements ScreenBoundary{
	private double screenWidth;
	private double screenHeight;
	
	public StopAtEdgeBoundary(double width, double height){
		screenWidth = width;
		screenHeight = height;
	}

	@Override
	public void moveGameObject(GameObject toMove, double newXPos, double newYPos) {
		if (newYPos <= screenHeight && newYPos >= 0){
			toMove.setYPosition(newYPos);
		}
		if(newXPos <= screenWidth && newXPos >= 0){
			toMove.setXPosition(newXPos);
		}
	}

}
