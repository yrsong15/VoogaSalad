package gameengine.scrolling;

import java.util.ArrayList;
import java.util.List;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;


/**
 * @author Chalena Scholl
 */

public class LimitedScrolling extends GeneralScroll{
	
	public LimitedScrolling(ScrollDirection dir, double speed, GameBoundary gameBoundaries){
		super(dir, speed, gameBoundaries);
	}
	
	public boolean allowedToScroll(ScrollDirection requestedDir, GameObject player){
		double viewWidth = this.getGameBoundaries().getViewWidth();
		double viewHeight = this.getGameBoundaries().getViewHeight();
		return (this.getDirection() == requestedDir)  
			    && (requestedDir == ScrollDirection.LEFT && player.getXPosition()<= viewWidth*0.3
				||  requestedDir == ScrollDirection.RIGHT && player.getXPosition()>= viewWidth*0.7
				||  requestedDir == ScrollDirection.UP && player.getYPosition() <= viewHeight*0.3
				||  requestedDir == ScrollDirection.DOWN && player.getYPosition() >= viewHeight*0.7);
	}
	
	
	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar){
		scrollScreen(gameObjects, mainChar, Double.parseDouble(mainChar.getProperty("movespeed")));
	}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed){
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);
		for (GameObject obj: gameObjects){
			if (obj.getProperty("nonscrollable") != null){
				scrollObjects.remove(obj);
			}
		}
		scrollObjects.remove(mainChar);
		this.scrollDirection(scrollObjects, speed);
		
	}	
}

