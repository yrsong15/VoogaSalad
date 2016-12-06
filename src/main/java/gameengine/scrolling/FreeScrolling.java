package gameengine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.sun.javafx.scene.traversal.Direction;

import exception.ScrollDirectionNotFoundException;
import gameengine.model.interfaces.Scrolling;
import objects.GameObject;
import utils.ReflectionUtil;

public class FreeScrolling implements Scrolling{
	private static final String CLASS_PATH = "gameengine.scrolling.GeneralScroll";
	private Direction direction;
	private double scrollingSpeed;
	private double screenWidth;
	private double screenHeight;
	
	
	public FreeScrolling(Direction dir, double speed, double width, double height){
		this.direction = dir;
		this.scrollingSpeed = speed;
		this.screenWidth = width;
		this.screenHeight = height;
	}

	@Override
	public void setSpeed(double speed) {
		this.scrollingSpeed = speed;
		
	}
	
	private Direction findScreenDirection(GameObject mainChar){
		if (mainChar.getXPosition() <= screenWidth*0.2){
			return Direction.LEFT;
		}
		else if (mainChar.getXPosition() >= screenWidth*0.6){
			return Direction.RIGHT;
		}
		else if (mainChar.getYPosition() <= screenWidth*0.2){
			return Direction.UP;
		}
		
		else if(mainChar.getYPosition() >= screenWidth*0.6){
			return Direction.DOWN;
		}
		return null;
	}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar) throws ScrollDirectionNotFoundException {
		direction = findScreenDirection(mainChar);
		if(direction==null)return;
		String methodName = "scroll" + direction.toString();
		Object[] parameters = new Object[]{gameObjects, scrollingSpeed};
 		Class<?>[] parameterTypes = new Class<?>[]{List.class, double.class};
         try {
				ReflectionUtil.runMethod(CLASS_PATH, methodName, parameters, parameterTypes);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw (new ScrollDirectionNotFoundException());
			}
	}
}

