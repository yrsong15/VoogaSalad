package gameengine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.traversal.Direction;

import exception.MovementRuleNotFoundException;
import exception.ScrollDirectionNotFoundException;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.interfaces.Scrolling;
import objects.GameObject;
import utils.ReflectionUtil;


/**
 * @author Chalena Scholl
 */

public class LimitedScrolling implements Scrolling{
	private static final String CLASS_PATH = "gameengine.scrolling.GeneralScroll";
	private Direction direction;
	private double scrollingSpeed;
	private double lastXPosition;
	private double lastYPosition;
	private GameBoundary gameBoundaries;
	
	public LimitedScrolling(Direction dir, double speed, GameBoundary gameBoundaries){
		this.direction = dir;
		this.scrollingSpeed = speed;
		this.gameBoundaries = gameBoundaries;
	}

	@Override
	public void setSpeed(double speed) {
		this.scrollingSpeed = speed;
	}
	
	@Override
	public void setDirection(Direction scrollDirection){
		this.direction = scrollDirection;
	}
	
	
	public boolean allowedToScroll(Direction requestedDir, GameObject player){
		double viewWidth = gameBoundaries.getViewWidth();
		double viewHeight = gameBoundaries.getViewHeight();
		return (direction == requestedDir)  
			    && (requestedDir == Direction.LEFT && player.getXPosition()<= viewWidth*0.3
				||  requestedDir == Direction.RIGHT && player.getXPosition()>= viewWidth*0.7
				||  requestedDir == Direction.UP && player.getYPosition() <= viewHeight*0.3
				||  requestedDir == Direction.DOWN && player.getYPosition() >= viewHeight*0.7);
	}
	
	
	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar) throws ScrollDirectionNotFoundException {
		scrollScreen(gameObjects, mainChar, Double.parseDouble(mainChar.getProperty("movespeed")));
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
		
 		Object[] parameters = new Object[]{scrollObjects, speed};
 		Class<?>[] parameterTypes = new Class<?>[]{List.class, double.class};
         try {
				ReflectionUtil.runMethod(CLASS_PATH, methodName, parameters, parameterTypes);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
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

