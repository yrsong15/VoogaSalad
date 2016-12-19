package gameengine.scrolling;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		double xWorldPos = player.getXDistanceMoved() - player.getXPosition();
		double yWorldPos = player.getYDistanceMoved() - player.getYPosition();
		GameBoundary gameBoundaries = getGameBoundaries();
		switch (requestedDir) {
			case DOWN:
				return (yWorldPos < gameBoundaries.getWorldHeight() - gameBoundaries.getViewHeight()
						&& playerInYScroll(player));
			case LEFT:
				return (xWorldPos > 0 && playerInXScroll(player));
			case RIGHT:
				return (xWorldPos < gameBoundaries.getWorldWidth() - gameBoundaries.getViewWidth()
						&& playerInXScroll(player));
			case UP:
				return (yWorldPos > 0 && playerInYScroll(player));
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
		scrollScreen(gameObjects, mainChar, Double.parseDouble(mainChar.getProperty("movespeed")));
	}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed){
		trackDistanceScrolling(speed, mainChar);
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);
		scrollObjects.remove(mainChar);
		this.scrollDirection(scrollObjects.stream()
		        .filter(scrollObj -> scrollObj.getProperty("nonscrollable") == null)
		        .collect(Collectors.toList()),  speed);
	}

	private void trackDistanceScrolling(double speed, GameObject mainChar) {
		switch (getDirection()) {
			case DOWN:
				mainChar.setYDistanceMoved(mainChar.getYDistanceMoved() + speed);
				break;
			case LEFT:
				mainChar.setXDistanceMoved(mainChar.getXDistanceMoved() - speed);
				break;
			case RIGHT:
				mainChar.setXDistanceMoved(mainChar.getXDistanceMoved() + speed);
				break;
			case UP:
				mainChar.setYDistanceMoved(mainChar.getYDistanceMoved() - speed);
				break;
		}
	}
}

