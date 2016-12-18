package gameengine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import exception.ScrollDirectionNotFoundException;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;
import utils.ReflectionUtil;

public class ForcedScrolling extends GeneralScroll{
	private static final String CLASS_PATH = "gameengine.scrolling.GeneralScroll";
	private ScrollDirection direction;
	private double scrollingSpeed;
	private GameBoundary gameBoundaries;
	
	
	public ForcedScrolling(ScrollDirection dir, double speed, GameBoundary gameBoundaries){
		this.direction = dir;
		this.scrollingSpeed = speed;
		this.gameBoundaries = gameBoundaries;
	}

	@Override
	public void setSpeed(double speed) {
		this.scrollingSpeed = speed;
	}
	
	@Override
	public void setDirection(ScrollDirection scrollDir) {
		this.direction = scrollDir;	
	}
	
	public boolean allowedToScroll(ScrollDirection requestedDir, GameObject player){
		return requestedDir == direction;
	}


	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar) throws ScrollDirectionNotFoundException {
			scrollScreen(gameObjects, mainChar, scrollingSpeed);
		}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed)
			throws ScrollDirectionNotFoundException {
		String methodName = "scroll" + direction.toString();
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);

		for (GameObject obj: gameObjects){
			if (obj.getProperty("nonscrollable") != null){
				scrollObjects.remove(obj);
			}
		}
		scrollObjects.remove(mainChar);
		Object[] parameters = new Object[]{scrollObjects, scrollingSpeed};
 		Class<?>[] parameterTypes = new Class<?>[]{List.class, double.class};
         try {
				ReflectionUtil.runMethod(CLASS_PATH, methodName, parameters, parameterTypes);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				//throw (new ScrollDirectionNotFoundException());
			}
		
	}

	@Override
	public double getXDistanceScrolled() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getYDistanceScrolled() {
		// TODO Auto-generated method stub
		return 0;
	}
}

