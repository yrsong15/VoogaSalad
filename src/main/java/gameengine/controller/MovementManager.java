package gameengine.controller;

import exception.ScrollTypeNotFoundException;
import gameengine.controller.interfaces.MovementInterface;
import gameengine.model.interfaces.Scrolling;
import gameengine.view.GameEngineUI;
import objects.Level;
import objects.ScrollType;
import utils.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

import com.sun.javafx.scene.traversal.Direction;

public class MovementManager implements MovementInterface{
	private Level currLevel;
	private double screenWidth;
	private double screenHeight;
	private String scrollName;
	private ControlManager controlManager;
	private Scrolling gameScrolling;
	
	
	public MovementManager(Level currLevel, double screenWidth, double screenHeight){
		this.currLevel = currLevel;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		initManager();
		
	}
	
	private void initManager(){
		try {
			setScrolling();
		} catch (ScrollTypeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controlManager = new ControlManager(currLevel, currLevel.getScrollType().getScreenBoundary());
		
	}
	
	public runActions(){
		
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shootProjectile() {
		// TODO Auto-generated method stub
		
	}
	
	
	private void setScrolling() throws ScrollTypeNotFoundException{
		ScrollType gameScroll = currLevel.getScrollType();
		String classPath = "gameengine.scrolling." + gameScroll.getScrollTypeName();
		Object[] parameters = new Object[]{gameScroll.getDirections().get(0), gameScroll.getScrollSpeed(), 
											screenWidth, screenHeight};
		Class<?>[] parameterTypes = new Class<?>[]{Direction.class, double.class, double.class, double.class};
			try {
				gameScrolling = (Scrolling) ReflectionUtil.getInstance(classPath, parameters, parameterTypes);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
