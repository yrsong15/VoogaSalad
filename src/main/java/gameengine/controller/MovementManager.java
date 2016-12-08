package gameengine.controller;

import exception.ScrollDirectionNotFoundException;
import exception.ScrollTypeNotFoundException;
import gameengine.controller.interfaces.MovementInterface;
import gameengine.model.MovementChecker;
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
	private MovementChecker movementChecker;
	private Direction scrollDir;
	
	
	public MovementManager(Level currLevel, double screenWidth, double screenHeight){
		this.currLevel = currLevel;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		scrollName = currLevel.getScrollType().getScrollTypeName();
		scrollDir = currLevel.getScrollType().getDirections().get(0);
		initManager();
		
		
	}
	
	//TO-DO: define scroll-width, pass movement interface through to movement rulebook through movement checker
	
	private void initManager(){
		try {
			setScrolling();
		} catch (ScrollTypeNotFoundException e) {
			e.printStackTrace();
		}
		movementChecker = new MovementChecker(currLevel.getScrollType().getScreenBoundary());
		controlManager = new ControlManager(currLevel, currLevel.getScrollType().getScreenBoundary());
		
	}
	
	public MovementInterface getMovementInterface(){
		return (MovementInterface) this;
	}
	
	public void runActions(){
		if(scrollName.equals("ForcedScrolling")){
			try {
				gameScrolling.scrollScreen(currLevel.getGameObjects(), currLevel.getMainCharacter());
			} catch (ScrollDirectionNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		movementChecker.updateMovement(currLevel.getGameObjects());		
	}

	@Override
	public void moveUp() {
		if (scrollName.equals("FreeScrolling") || (scrollName.equals("LimitedScrolling")&& scrollDir == Direction.UP)){
			gameScrolling.setDirection(Direction.UP);
			runGameScrolling();
		}
		else{
			controlManager.moveUp();
		}
		
	}


	@Override
	public void moveDown() {
		if (scrollName.equals("FreeScrolling") || (scrollName.equals("LimitedScrolling")&& scrollDir == Direction.DOWN)){
			gameScrolling.setDirection(Direction.DOWN);
			runGameScrolling();
		}
		else{
			controlManager.moveDown();
		}
		
	}

	@Override
	public void moveRight() {
		if (scrollName.equals("FreeScrolling") || (scrollName.equals("LimitedScrolling")&& scrollDir == Direction.RIGHT)){
			gameScrolling.setDirection(Direction.RIGHT);
			runGameScrolling();
		}
		else{
			controlManager.moveRight();
		}		
	}

	@Override
	public void moveLeft() {
		if (scrollName.equals("FreeScrolling") || (scrollName.equals("LimitedScrolling")&& scrollDir == Direction.LEFT)){
			gameScrolling.setDirection(Direction.LEFT);
			runGameScrolling();
		}
		else{
			controlManager.moveLeft();
		}
	}

	@Override
	public void jump() {
		controlManager.jump();
	}

	@Override
	public void shootProjectile() {
		controlManager.shootProjectile();
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
	
	private void runGameScrolling() {
		try {
			gameScrolling.scrollScreen(currLevel.getGameObjects(), currLevel.getMainCharacter());
		} catch (ScrollDirectionNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
