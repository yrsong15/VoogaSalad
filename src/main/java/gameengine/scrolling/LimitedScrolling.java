package gameengine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.traversal.Direction;

import exception.MovementRuleNotFoundException;
import exception.ScrollDirectionNotFoundException;
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
	private double screenWidth;
	private double screenHeight;
	private double lastXPosition;
	private double lastYPosition;
	
	public LimitedScrolling(Direction dir, double speed, double width, double height){
		this.direction = dir;
		this.scrollingSpeed = speed;
		this.screenWidth = width;
		this.screenHeight = height;
	}

	@Override
	public void setSpeed(double speed) {
		this.scrollingSpeed = speed;
	}
	
	@Override
	public void setDirection(Direction scrollDirection){
		this.direction = scrollDirection;
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
		scrollObjects.remove(mainChar);
		
 		Object[] parameters = new Object[]{scrollObjects, speed};
 		Class<?>[] parameterTypes = new Class<?>[]{List.class, double.class};
         try {
				ReflectionUtil.runMethod(CLASS_PATH, methodName, parameters, parameterTypes);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw (new ScrollDirectionNotFoundException());
			}
		
	}
}

