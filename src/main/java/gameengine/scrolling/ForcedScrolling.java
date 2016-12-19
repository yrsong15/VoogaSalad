package gameengine.scrolling;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;


//This entire file is part of my masterpiece.
//Chalena Scholl

/** 
 * I believe this class shows good design because it extends an abstract class and only provides implementations to the methods
 * that it would like to implement differently. This is an example of the Template Method pattern. The GeneralScroll class is the skeletal 
 * class that serves as the basis for the various scrolling types defined (i.e this one or limited/free). As such, it contains some abstract 
 * methods which are overridden by the corresponding methods in the subclass because these implementations can vary. For example, 
 * each scrolling type has different ways of deciding whether or not the screen should be scrolled given certain information. As one can 
 * see below, forcedScrolling does just that; always scrolls the screen no matter what. However, the other scrolling types
 * have different ways of deciding whether the screen should be scrolled (free scrolling is based on the character's position 
 * in the world). 
 * 
 * Any other methods required by the Scrolling interface are implemented in the abstract GeneralScroll class which makes
 * duplicated code basically nonexistent and is very useful because the code can easily be changed in one place if the 
 * programmer would like to change the functionality. Furthermore, instead of using a for loop it uses a lambda to get the objects in the 
 * list that it would like to send as parameters to the function that will scroll them.
 */

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

