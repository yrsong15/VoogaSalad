package gameengine.controller;

import exception.ScrollDirectionNotFoundException;
import exception.ScrollTypeNotFoundException;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.MovementChecker;
import gameengine.model.interfaces.Scrolling;
import gameengine.view.GameEngineUI;
import objects.GameObject;
import objects.Level;
import objects.Player;
import objects.ScrollType;
import utils.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

import com.sun.javafx.scene.traversal.Direction;

public class MovementManager implements ControlInterface{
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
		movementChecker = new MovementChecker((ControlInterface) this, currLevel.getScrollType().getScreenBoundary());
		controlManager = new ControlManager(currLevel, currLevel.getScrollType().getScreenBoundary());
		
	}
	
	public ControlInterface getControlInterface(){
		return (ControlInterface) this;
	}
	
	public void runActions(){
		if(scrollName.equals("ForcedScrolling")){
			runGameScrolling();
		}
		movementChecker.updateMovement(currLevel);		
	}

	@Override
	public void moveUp(GameObject player, double speed) {
		if (scrollName.equals("FreeScrolling") || (scrollName.equals("LimitedScrolling")&& scrollDir == Direction.UP)){
			gameScrolling.setDirection(Direction.UP);
			runGameScrolling(speed);
		}
		else{
			controlManager.moveUp(player, speed);
		}
		
	}


	@Override
	public void moveDown(GameObject player, double speed) {
		if (scrollName.equals("FreeScrolling") || (scrollName.equals("LimitedScrolling")&& scrollDir == Direction.DOWN)){
			gameScrolling.setDirection(Direction.DOWN);
			runGameScrolling(speed);
		}
		else{
			controlManager.moveDown(player, speed);
		}
		
	}

	@Override
	public void moveRight(GameObject player, double speed) {
		if (scrollName.equals("FreeScrolling") || (scrollName.equals("LimitedScrolling")&& scrollDir == Direction.RIGHT)){
			gameScrolling.setDirection(Direction.RIGHT);
			runGameScrolling(speed);
		}
		else{
			controlManager.moveRight(player, speed);
		}		
	}

	@Override
	public void moveLeft(GameObject player, double speed) {
		if (scrollName.equals("FreeScrolling") || (scrollName.equals("LimitedScrolling")&& scrollDir == Direction.LEFT)){
			gameScrolling.setDirection(Direction.LEFT);
			runGameScrolling(speed);
		}
		else{
			controlManager.moveLeft(player, speed);
		}
	}

	@Override
	public void jump(GameObject player, double speed) {
		controlManager.jump(player, Double.parseDouble(player.getProperty("jump")));
	}

	@Override
	public void shootProjectile(GameObject player, double speed) {
		controlManager.shootProjectile(player, speed);
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
			gameScrolling.scrollScreen(currLevel.getGameObjects(), currLevel.getPlayers().get(0));
		} catch (ScrollDirectionNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void runGameScrolling(double speed) {
		try {
			gameScrolling.scrollScreen(currLevel.getGameObjects(), currLevel.getPlayers().get(0), speed);
		} catch (ScrollDirectionNotFoundException e) {
			e.printStackTrace();
		}
	}
}
