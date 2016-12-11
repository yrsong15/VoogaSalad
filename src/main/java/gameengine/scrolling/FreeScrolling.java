package gameengine.scrolling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.traversal.Direction;

import exception.ScrollDirectionNotFoundException;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.interfaces.Scrolling;
import objects.GameObject;
import utils.ReflectionUtil;


/**
 * @author Chalena Scholl
 */
public class FreeScrolling implements Scrolling{
	private static final String CLASS_PATH = "gameengine.scrolling.GeneralScroll";
	private Direction direction;
	private double scrollingSpeed;
	private double xDistanceScrolled;
	private double yDistanceScrolled;
	private GameBoundary gameBoundaries;
	
	
	public FreeScrolling(Direction dir, double speed, GameBoundary gameBoundaries){
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
	
	@Override
	public double getXDistanceScrolled() {
		return xDistanceScrolled;
	}

	@Override
	public double getYDistanceScrolled() {
		return yDistanceScrolled;
	}
	
	public boolean allowedToScroll(Direction requestedDir, GameObject player){
		if(requestedDir == Direction.RIGHT){
			return (xDistanceScrolled + player.getXPosition() < gameBoundaries.getWorldWidth()*0.8
					&& player.getXPosition() > gameBoundaries.getViewWidth()*0.5);
		}
		else if(requestedDir == Direction.LEFT){
			return (player.getXPosition() + xDistanceScrolled >  gameBoundaries.getWorldWidth()*0.2
					&& player.getXPosition() < gameBoundaries.getViewWidth()*0.5);
		}
		else if(requestedDir == Direction.UP){
			System.out.println(yDistanceScrolled + player.getYPosition());
			return (yDistanceScrolled + player.getYPosition() > gameBoundaries.getWorldHeight()*0.2)
					&& player.getYPosition() < gameBoundaries.getViewHeight()*0.5;
		}
		else if(requestedDir == Direction.DOWN){
			System.out.println(yDistanceScrolled + player.getYPosition());
			return (yDistanceScrolled + player.getYPosition() < gameBoundaries.getWorldHeight()*0.8)
					&& player.getYPosition() > gameBoundaries.getViewHeight()*0.5;

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
		trackDistanceScrolling(speed);
		String methodName = "scroll" + direction.toString();
		List<GameObject> scrollObjects = new ArrayList<GameObject>(gameObjects);
		scrollObjects.remove(mainChar);
		//System.out.println(xDistanceScrolled + ", y:  " + yDistanceScrolled);
		Object[] parameters = new Object[]{scrollObjects, speed};
 		Class<?>[] parameterTypes = new Class<?>[]{List.class, double.class};
         try {
				ReflectionUtil.runMethod(CLASS_PATH, methodName, parameters, parameterTypes);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw (new ScrollDirectionNotFoundException());
			}
	}

	private void trackDistanceScrolling(double speed) {
		if (direction == Direction.RIGHT){
			xDistanceScrolled+= speed;
		}
		else if(direction == Direction.LEFT){
			xDistanceScrolled-= speed;

		}
		else if(direction == Direction.UP){
			yDistanceScrolled-= speed;
		}
		else{
			yDistanceScrolled+= speed;
		}
		//System.out.println(xDistanceScrolled + "  " + yDistanceScrolled);
	}
}

