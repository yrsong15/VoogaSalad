package gameengine.controller;

import exception.ScrollDirectionNotFoundException;
import exception.ScrollTypeNotFoundException;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.MovementChecker;
import gameengine.model.interfaces.Scrolling;
import objects.GameObject;
import objects.Level;
import objects.ProjectileProperties;
import objects.ScrollType;
import utils.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
		if (scrollName.equals("FreeScrolling") || scrollForLimited(Direction.UP, player)){
			gameScrolling.setDirection(Direction.UP);
			runGameScrolling(speed);
		}
		else{
			controlManager.moveUp(player, speed);
		}
	}


	@Override
	public void moveDown(GameObject player, double speed) {
		if (scrollName.equals("FreeScrolling") || scrollForLimited(Direction.DOWN, player)){
			gameScrolling.setDirection(Direction.DOWN);
			runGameScrolling(speed);
		}
		else{
			controlManager.moveDown(player, speed);
		}
		
	}
	
	@Override
	public void moveRight(GameObject player, double speed) {
		if (scrollName.equals("FreeScrolling") || scrollForLimited(Direction.RIGHT, player)){
			gameScrolling.setDirection(Direction.RIGHT);
			runGameScrolling(speed);
		}
		else{
			controlManager.moveRight(player, speed);
		}		
	}

	@Override
	public void moveLeft(GameObject player, double speed) {
		if (scrollName.equals("FreeScrolling") || scrollForLimited(Direction.LEFT, player)){
			gameScrolling.setDirection(Direction.LEFT);
			runGameScrolling(speed);
		}
		else{
			controlManager.moveLeft(player, speed);
		}
	}
	
	private boolean scrollForLimited(Direction requestedDir, GameObject player){
		return (scrollName.equals("LimitedScrolling")&& scrollDir == requestedDir)  
			    && (requestedDir == Direction.LEFT && player.getXPosition()<= screenWidth*0.3
				||  requestedDir == Direction.RIGHT && player.getXPosition()>= screenWidth*0.7
				||  requestedDir == Direction.UP && player.getYPosition() <= screenWidth*0.3
				||  requestedDir == Direction.DOWN && player.getYPosition() >= screenWidth*0.7);
	}

	@Override
	public void jump(GameObject player, double speed) {
        String jumpVelocity = player.getProperty("jump");
    	if(jumpVelocity!=null){
    		player.setProperty("fallspeed", "-" + jumpVelocity);
    	}
	}

	@Override
	public void shootProjectile(GameObject player, double speed) {
        if(player.getProjectileProperties() != null){
            ProjectileProperties properties = player.getProjectileProperties();
            GameObject projectile = new GameObject(player.getXPosition(), player.getYPosition(),
                    properties.getWidth(), properties.getHeight(), properties.getImageFileName(), new HashMap<>());
            if(properties.getDirection().equals(Direction.LEFT)){
                projectile.setProperty("horizontalmovement", String.valueOf(properties.getSpeed()*-1));
            }else if(properties.getDirection().equals(Direction.RIGHT)){
                projectile.setProperty("horizontalmovement", String.valueOf(properties.getSpeed()));
            }else if(properties.getDirection().equals(Direction.DOWN)){
                projectile.setProperty("gravity", String.valueOf(properties.getSpeed()));
            }else if(properties.getDirection().equals(Direction.UP)){
                projectile.setProperty("gravity", String.valueOf(properties.getSpeed() * -1));
            }
            projectile.setProperty("damage", String.valueOf(properties.getDamage()));
            currLevel.getProjectiles().add(projectile);
        }
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
