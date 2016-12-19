package gameengine.scrolling;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gameengine.model.boundary.GameBoundary;
import objects.GameObject;


/**
 * @author Chalena Scholl
 */

public class LimitedScrolling extends GeneralScroll{
	public static final double HORIZONTAL_LIMIT = 0.3;
	public static final double VERTICAL_LIMIT = 0.7;
	
	public LimitedScrolling(ScrollDirection scrollDir, double speed, GameBoundary gameBoundaries){
		super(scrollDir, speed, gameBoundaries);
	}
	
	public boolean allowedToScroll(ScrollDirection requestedDir, GameObject player){
		double viewWidth = this.getGameBoundaries().getViewWidth();
		double viewHeight = this.getGameBoundaries().getViewHeight();
		double playerX = player.getXPosition();
		double playerY = player.getYPosition();
		return (this.getDirection() == requestedDir)  
			    && (requestedDir == ScrollDirection.LEFT && playerX<= viewWidth*HORIZONTAL_LIMIT
				||  requestedDir == ScrollDirection.RIGHT && playerX>= viewWidth*VERTICAL_LIMIT
				||  requestedDir == ScrollDirection.UP && playerY <= viewHeight*HORIZONTAL_LIMIT
				||  requestedDir == ScrollDirection.DOWN && playerY >= viewHeight*VERTICAL_LIMIT);
	}
	
	
	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar){
		scrollScreen(gameObjects, mainChar, Double.parseDouble(mainChar.getProperty("movespeed")));
	}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed){
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);
		scrollObjects.remove(mainChar);
		this.scrollDirection(scrollObjects.stream()
		        .filter(scrollObj -> scrollObj.getProperty("nonscrollable") == null)
		        .collect(Collectors.toList()), speed);
	}	
}

