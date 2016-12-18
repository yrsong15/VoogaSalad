package gameengine.scrolling;

import java.util.ArrayList;
import java.util.List;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;


/**
 * @author Chalena Scholl
 */
public class FreeScrolling extends GeneralScroll{	
	public static final double LOWER_LIMIT = 0.45;
	public static final double UPPER_LIMIT = 0.55;
	
	public FreeScrolling(ScrollDirection dir, double speed, GameBoundary gameBoundaries){
		super(dir, speed, gameBoundaries);
	}
	
	public boolean allowedToScroll(ScrollDirection requestedDir, GameObject player){
		if(requestedDir == ScrollDirection.RIGHT){
			return (player.getXDistanceMoved() - player.getXPosition() < getGameBoundaries().getWorldWidth() - getGameBoundaries().getViewWidth()
					&& playerInXScroll(player));

		}
		else if(requestedDir == ScrollDirection.LEFT){
			return (player.getXDistanceMoved() - player.getXPosition()> 0
					&& playerInXScroll(player));

		}
		else if(requestedDir == ScrollDirection.UP){
			return (player.getYDistanceMoved() - player.getYPosition()> 0
					&& playerInYScroll(player));
		}
		else if(requestedDir == ScrollDirection.DOWN){
			return (player.getYDistanceMoved() - player.getYPosition() 
					< getGameBoundaries().getWorldHeight() - getGameBoundaries().getViewHeight()
					&& playerInYScroll(player));

		}
		return false;			
	}
	
	private boolean playerInYScroll(GameObject player){
		return 	player.getYPosition() > getGameBoundaries().getViewHeight()*LOWER_LIMIT
				&& player.getYPosition() < getGameBoundaries().getViewHeight()*UPPER_LIMIT;
	}
	
	private boolean playerInXScroll(GameObject player){
		return player.getXPosition() > getGameBoundaries().getViewWidth()*LOWER_LIMIT
				&& player.getXPosition() < getGameBoundaries().getViewWidth()*UPPER_LIMIT;
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

