package gameengine.controller;

import exception.ScrollDirectionNotFoundException;
import exception.ScrollTypeNotFoundException;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.MovementChecker;
import gameengine.model.interfaces.Scrolling;
import gameengine.scrolling.LimitedScrolling;
import javafx.scene.input.KeyCode;
import objects.GameObject;
import objects.Level;
import objects.ProjectileProperties;
import objects.ScrollType;
import utils.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.sun.javafx.scene.traversal.Direction;


public class MovementManager implements ControlInterface{
	private Level currLevel;
	private double screenWidth;
	private double screenHeight;
	private String scrollName;
	private GeneralMovement genMovement;
	private Scrolling gameScrolling;
	private MovementChecker movementChecker;
	private Direction scrollDir;
	private Map<GameObject, Long> projectileStatus;


	public MovementManager(Level currLevel, double screenWidth, double screenHeight){
		projectileStatus = new HashMap<>();
		this.currLevel = currLevel;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		scrollName = currLevel.getScrollType().getScrollTypeName();
		scrollDir = currLevel.getScrollType().getDirections().get(0);
		initManager();
	}

	private void initManager(){
		try {
			setScrolling();
		} catch (ScrollTypeNotFoundException e) {
			e.printStackTrace();
		}
		movementChecker = new MovementChecker((ControlInterface) this, currLevel.getScrollType().getScreenBoundary());
		genMovement = new GeneralMovement(currLevel, currLevel.getScrollType().getScreenBoundary());
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
	public void moveUp(GameObject obj, double speed) {
		if (obj.isPlayer() && (scrollName.equals("FreeScrolling") || scrollForLimited(Direction.UP, obj))){
			gameScrolling.setDirection(Direction.UP);
			runGameScrolling(speed);
		}
		else{
			genMovement.moveUp(obj, speed);
		}
	}


	@Override
	public void moveDown(GameObject obj, double speed) {
		if (obj.isPlayer() && (scrollName.equals("FreeScrolling") || scrollForLimited(Direction.DOWN, obj))){
			gameScrolling.setDirection(Direction.DOWN);
			runGameScrolling(speed);
		}
		else{
			genMovement.moveDown(obj, speed);
		}

	}

	@Override
	public void moveRight(GameObject obj, double speed) {
		if (obj.isPlayer() && (scrollName.equals("FreeScrolling") || scrollForLimited(Direction.RIGHT, obj))){
			gameScrolling.setDirection(Direction.RIGHT);
			runGameScrolling(speed);
		}
		else{
			genMovement.moveRight(obj, speed);
		}
        obj.setDirection(Direction.RIGHT);
    }

	@Override
	public void moveLeft(GameObject obj, double speed) {
		if (obj.isPlayer() && (scrollName.equals("FreeScrolling") || scrollForLimited(Direction.LEFT, obj))){
			gameScrolling.setDirection(Direction.LEFT);
            runGameScrolling(speed);
		}
		else{
			genMovement.moveLeft(obj, speed);
		}
        obj.setDirection(Direction.LEFT);
    }

	private boolean scrollForLimited(Direction requestedDir, GameObject player){
		if (scrollName.equals("LimitedScrolling")){
			LimitedScrolling limScroll = (LimitedScrolling) gameScrolling;
			return limScroll.needToScroll(requestedDir, player);
		}
		return false;
	}

	@Override
	public void jump(GameObject obj, double speed) {
        String jumpVelocity = obj.getProperty("jump");
    	if(jumpVelocity!=null){
    		obj.setProperty("fallspeed", "-" + jumpVelocity);
    	}
	}

	@Override
	public void shootProjectile(GameObject obj, double speed) {
	    if(!projectileStatus.containsKey(obj) || (projectileStatus.containsKey(obj) && (System.currentTimeMillis() - projectileStatus.get(obj) > 1000))) {
            projectileStatus.put(obj, System.currentTimeMillis());
	        if (obj.getProjectileProperties() != null) {
                ProjectileProperties properties = obj.getProjectileProperties();
                GameObject projectile = new GameObject(obj.getXPosition(), obj.getYPosition(),
                        properties.getWidth(), properties.getHeight(), properties.getImageFileName(), new HashMap<>());
                if (properties.getDirection().equals(Direction.LEFT)) {
                    projectile.setProperty("horizontalmovement", String.valueOf(properties.getSpeed() * -1));
                } else if (properties.getDirection().equals(Direction.RIGHT)) {
                    projectile.setProperty("horizontalmovement", String.valueOf(properties.getSpeed()));
                } else if (properties.getDirection().equals(Direction.DOWN)) {
                    projectile.setProperty("verticalmovement", String.valueOf(properties.getSpeed()));
                } else if (properties.getDirection().equals(Direction.UP)) {
                    projectile.setProperty("verticalmovement", String.valueOf(properties.getSpeed() * -1));
                }
                projectile.setProperty("damage", String.valueOf(properties.getDamage()));
                projectile.setProjectileProperties(properties);
                currLevel.getProjectiles().add(projectile);
            }
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
