package gameengine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.sun.javafx.scene.traversal.Direction;

import gameengine.model.interfaces.Scrolling;
import objects.GameObject;
import utils.ReflectionUtil;

/* To-DO: right now this is just forced scrolling*/
public class LimitedScrolling implements Scrolling{
	private Direction direction;
	private double scrollingSpeed;
	
	
	public LimitedScrolling(Direction dir, double speed){
		this.direction = dir;
		this.scrollingSpeed = speed;
	}

	@Override
	public void setSpeed(double speed) {
		this.scrollingSpeed = speed;
		
	}

	@Override
	public void scrollScreen(List<GameObject> gameObjects, GameObject mainChar) {
		String className = "gameengine.scrolling.GeneralScroll";
		String methodName = "scroll" + direction.toString();
		//if(!(mainChar.getYPosition() <= 200)){
		//	return;
	//	}
		
		Method method = null;
		try {
			method = ReflectionUtil.getMethodFromClass(className, methodName,  new Class[]{List.class, GameObject.class, double.class});
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			method.invoke(new GeneralScroll(), gameObjects, mainChar, scrollingSpeed);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	

}
