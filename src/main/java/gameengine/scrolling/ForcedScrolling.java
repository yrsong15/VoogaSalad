package gameengine.scrolling;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gameengine.model.boundary.GameBoundary;
import objects.GameObject;

public class ForcedScrolling extends GeneralScroll{	
	
	public ForcedScrolling(ScrollDirection dir, double speed, GameBoundary gameBoundaries){
		super(dir, speed, gameBoundaries);
	}
	
	public boolean allowedToScroll(ScrollDirection requestedDir, GameObject player){
		return requestedDir == getDirection();
	}


	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar){
			scrollScreen(gameObjects, mainChar, getScrollingSpeed());
		}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed){
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);
		scrollObjects.remove(mainChar);
		this.scrollDirection(scrollObjects.stream()
		        .filter(scrollObj -> scrollObj.getProperty("nonscrollable") == null)
		        .collect(Collectors.toList()), getScrollingSpeed());
	}
}

