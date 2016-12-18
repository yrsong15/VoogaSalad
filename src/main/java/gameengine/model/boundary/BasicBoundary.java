package gameengine.model.boundary;

import gameengine.scrolling.Scrolling;
import objects.GameObject;

public abstract class BasicBoundary implements GameBoundary{
	private double viewWidth;
	private double viewHeight;
	private double worldWidth;
	private double worldHeight;
	private Scrolling gameScrolling;
	
	public BasicBoundary(double width, double height){
		this.viewWidth = width;
		this.viewHeight = height;
		this.worldWidth = Double.MAX_VALUE;
		this.worldHeight = Double.MAX_VALUE;
	}
	
	public BasicBoundary(double viewWidth, double viewHeight, double worldWidth, double worldHeight){
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
	}
	
	public double getViewHeight(){
		return viewHeight;
	}
	
	public double getViewWidth(){
		return viewWidth;
	}
	
	public double getWorldHeight(){
		return worldHeight;
	}
	
	public double getWorldWidth(){
		return worldWidth;
	}
	
	public void setScrolling(Scrolling gameScrolling){
		this.gameScrolling = gameScrolling;
	}
	
	public Scrolling getScrolling(){
		return gameScrolling;
	}

	@Override
	public abstract boolean moveToXPos(GameObject toMove, double newXPos, double speed);

	@Override
	public abstract boolean moveToYPos(GameObject toMove, double newYPos);

}
