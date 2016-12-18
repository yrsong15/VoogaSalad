package gameengine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import exception.ScrollDirectionNotFoundException;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;
import utils.ReflectionUtil;


/**
 * @author Chalena Scholl
 */
public class FreeScrolling extends GeneralScroll{
	private static final String CLASS_PATH = "gameengine.scrolling.GeneralScroll";
	private ScrollDirection scrollDir;
	private double scrollingSpeed;
	private double xDistanceScrolled;
	private double yDistanceScrolled;
	private GameBoundary gameBoundaries;
	
	
	public FreeScrolling(ScrollDirection dir, double speed, GameBoundary gameBoundaries){
		this.scrollDir = dir;
		this.scrollingSpeed = speed;
		this.gameBoundaries = gameBoundaries;
	}

	@Override
	public void setSpeed(double speed) {
		this.scrollingSpeed = speed;
		
	}
	
	@Override
	public void setDirection(ScrollDirection scrollDir){
		this.scrollDir = scrollDir;
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
		if(requestedDir == ScrollDirection.RIGHT){
			return (player.getXDistanceMoved() - player.getXPosition() < gameBoundaries.getWorldWidth() - gameBoundaries.getViewWidth()
					&& player.getXPosition() > gameBoundaries.getViewWidth()*0.45
					&& player.getXPosition() < gameBoundaries.getViewWidth()*0.55);
		}
		else if(requestedDir == ScrollDirection.LEFT){
			return (player.getXDistanceMoved() - player.getXPosition()> 0
					&& player.getXPosition() > gameBoundaries.getViewWidth()*0.45
					&& player.getXPosition() < gameBoundaries.getViewWidth()*0.55);
		}
		else if(requestedDir == ScrollDirection.UP){
			/**return (player.getYDistanceMoved() - player.getYPosition()> 0
					&& player.getYPosition() > gameBoundaries.getViewHeight()*0.45
					&& player.getYPosition() < gameBoundaries.getViewHeight()*0.55);**/
		}
		else if(requestedDir == ScrollDirection.DOWN){
			return (player.getYDistanceMoved() - player.getYPosition() < gameBoundaries.getWorldHeight() - gameBoundaries.getViewHeight()
					&& player.getYPosition() > gameBoundaries.getViewHeight()*0.45
					&& player.getYPosition() < gameBoundaries.getViewHeight()*0.55);

		}
		return false;			
	}
	
	
	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar) throws ScrollDirectionNotFoundException {
		scrollScreen(gameObjects, mainChar, Double.parseDouble(mainChar.getProperty("movespeed")));
	}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar, double speed)
			throws ScrollDirectionNotFoundException {
		trackDistanceScrolling(speed, mainChar);
		String methodName = "scroll" + scrollDir.toString();
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);
		for (GameObject obj: gameObjects){
			if (obj.getProperty("nonscrollable") != null){
				scrollObjects.remove(obj);
			}
		}
		scrollObjects.remove(mainChar);
		Object[] parameters = new Object[]{scrollObjects, speed};
 		Class<?>[] parameterTypes = new Class<?>[]{List.class, double.class};
         try {
				ReflectionUtil.runMethod(CLASS_PATH, methodName, parameters, parameterTypes);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				
				//throw (new ScrollScrollDirectionNotFoundException());
			}
	}

	private void trackDistanceScrolling(double speed, GameObject mainChar) {
		if (scrollDir == ScrollDirection.RIGHT){
			mainChar.setXDistanceMoved(mainChar.getXDistanceMoved() + speed);
		}
		else if(scrollDir == ScrollDirection.LEFT){
			mainChar.setXDistanceMoved(mainChar.getXDistanceMoved() - speed);
		}
		else if(scrollDir == ScrollDirection.UP){
			mainChar.setYDistanceMoved(mainChar.getYDistanceMoved() - speed);
		}
		else{
			mainChar.setYDistanceMoved(mainChar.getYDistanceMoved() + speed);

		}
	}
}

