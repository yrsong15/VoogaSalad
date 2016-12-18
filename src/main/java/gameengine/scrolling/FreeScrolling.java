package gameengine.scrolling;

import java.util.ArrayList;
import java.util.List;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;


/**
 * @author Chalena Scholl
 */
public class FreeScrolling extends GeneralScroll{
	private double xDistanceScrolled;
	private double yDistanceScrolled;
	
	
	public FreeScrolling(ScrollDirection dir, double speed, GameBoundary gameBoundaries){
		super(dir, speed, gameBoundaries);
	}
	
	@Override
	public double getXDistanceScrolled() {
		return xDistanceScrolled;
	}

	@Override
	public double getYDistanceScrolled() {
		return yDistanceScrolled;
	}
	
	public boolean allowedToScroll(ScrollDirection requestedDir, GameObject player){
		System.out.println("allowed to scroll");
		if(requestedDir == ScrollDirection.RIGHT){
			return (player.getXDistanceMoved() - player.getXPosition() < getGameBoundaries().getWorldWidth() - getGameBoundaries().getViewWidth()
					&& player.getXPosition() > getGameBoundaries().getViewWidth()*0.45
					&& player.getXPosition() < getGameBoundaries().getViewWidth()*0.55);
		}
		else if(requestedDir == ScrollDirection.LEFT){
			return (player.getXDistanceMoved() - player.getXPosition()> 0
					&& player.getXPosition() > getGameBoundaries().getViewWidth()*0.45
					&& player.getXPosition() < getGameBoundaries().getViewWidth()*0.55);
		}
		else if(requestedDir == ScrollDirection.UP){
			/**return (player.getYDistanceMoved() - player.getYPosition()> 0
					&& player.getYPosition() > gameBoundaries.getViewHeight()*0.45
					&& player.getYPosition() < gameBoundaries.getViewHeight()*0.55);**/
		}
		else if(requestedDir == ScrollDirection.DOWN){
			return (player.getYDistanceMoved() - player.getYPosition() < getGameBoundaries().getWorldHeight() - getGameBoundaries().getViewHeight()
					&& player.getYPosition() > getGameBoundaries().getViewHeight()*0.45
					&& player.getYPosition() < getGameBoundaries().getViewHeight()*0.55);

		}
		return false;			
	}
	
	
	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar){
		System.out.println("scroll");
		scrollScreen(gameObjects, mainChar, Double.parseDouble(mainChar.getProperty("movespeed")));
	}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed){
		System.out.println("again");
		trackDistanceScrolling(speed, mainChar);
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);
		for (GameObject obj: gameObjects){
			if (obj.getProperty("nonscrollable") != null){
				scrollObjects.remove(obj);
			}
		}
		scrollObjects.remove(mainChar);
		this.scrollDirection(scrollObjects, speed);
	}

	private void trackDistanceScrolling(double speed, GameObject mainChar) {
		if (getDirection() == ScrollDirection.RIGHT){
			mainChar.setXDistanceMoved(mainChar.getXDistanceMoved() + speed);
		}
		else if(getDirection() == ScrollDirection.LEFT){
			mainChar.setXDistanceMoved(mainChar.getXDistanceMoved() - speed);
		}
		else if(getDirection() == ScrollDirection.UP){
			mainChar.setYDistanceMoved(mainChar.getYDistanceMoved() - speed);
		}
		else{
			mainChar.setYDistanceMoved(mainChar.getYDistanceMoved() + speed);

		}
	}
}

