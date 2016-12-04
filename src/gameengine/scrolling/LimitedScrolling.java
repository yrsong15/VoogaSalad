package gameengine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.traversal.Direction;

import gameengine.model.interfaces.Scrolling;
import objects.GameObject;
import utils.ReflectionUtil;


public class LimitedScrolling implements Scrolling{
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
	
	private boolean needToMoveScreen(GameObject mainChar){
		if (direction==Direction.LEFT){
			return mainChar.getXPosition() <= screenWidth*0.3;
		}
		else if (direction == Direction.RIGHT){
			return mainChar.getXPosition() >= screenWidth*0.7;
		}
		else if (direction == Direction.UP){
			return mainChar.getYPosition() <= screenWidth*0.3;
		}
		
		else if(direction == Direction.DOWN){
			return mainChar.getYPosition() <= screenWidth*0.7;
		}
		return false;
	}

	
	private boolean needToMoveScreen2(GameObject mainChar){
		if (direction==Direction.LEFT || direction == Direction.RIGHT){
			return mainChar.getXPosition() != lastXPosition;
		}
		else if (direction==Direction.UP || direction == Direction.DOWN){
			return mainChar.getYPosition() != lastYPosition;
		}
		
		return false;
	}
	
	
	
	// only scroll screen if maincharacter is at 50%
	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar) {
		if(!needToMoveScreen(mainChar)) return;
		String className = "gameengine.scrolling.GeneralScroll";
		String methodName = "scroll" + direction.toString();
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);
		if (mainChar.getProperties().containsKey("gravity") && Double.parseDouble(mainChar.getProperty("gravity")) != 0.0){
			scrollObjects.remove(mainChar);
		}
		Method method = null;
		try {
			method = ReflectionUtil.getMethodFromClass(className, methodName,  new Class[]{List.class, double.class});
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			method.invoke(new GeneralScroll(), scrollObjects, Double.parseDouble(mainChar.getProperty("movespeed")));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	

}

