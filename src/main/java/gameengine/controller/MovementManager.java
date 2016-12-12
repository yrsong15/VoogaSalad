package gameengine.controller;

import exception.ScrollDirectionNotFoundException;
import exception.ScrollTypeNotFoundException;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.MovementChecker;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.boundary.ToroidalBoundary;
import gameengine.model.interfaces.Scrolling;
import gameengine.network.server.ServerMain;
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
		movementChecker = new MovementChecker((ControlInterface) this, currLevel.getScrollType().getGameBoundary());
		genMovement = new GeneralMovement(currLevel, currLevel.getScrollType().getGameBoundary());
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
		if (obj.isPlayer() && gameScrolling.allowedToScroll(Direction.UP, obj)){
			gameScrolling.setDirection(Direction.UP);
			runGameScrolling(speed);
		}
		else{
			double newYPos = obj.getYPosition() - Math.abs(speed);
			genMovement.moveUp(obj, speed);
			checkYToroidalChange(obj, newYPos);
		}
	}


	@Override
	public void moveDown(GameObject obj, double speed) {
		if (obj.isPlayer() &&  gameScrolling.allowedToScroll(Direction.DOWN, obj)){
			gameScrolling.setDirection(Direction.DOWN);
			runGameScrolling(speed);
		}
		else{
			double newYPos = obj.getYPosition() + Math.abs(speed);
			genMovement.moveDown(obj, speed);
			checkYToroidalChange(obj, newYPos);
		}
	}

	@Override
	public void moveRight(GameObject obj, double speed) {
		if (obj.isPlayer() &&  gameScrolling.allowedToScroll(Direction.RIGHT, obj)){
			gameScrolling.setDirection(Direction.RIGHT);
			runGameScrolling(speed);
		}
		else {
            double newXPos = obj.getXPosition() + Math.abs(speed);
            genMovement.moveRight(obj, speed);
            checkXToroidalChange(obj, newXPos);
            }
        obj.setDirection(Direction.RIGHT);
    }
	

	@Override
	public void moveLeft(GameObject obj, double speed) {
		if (obj.isPlayer() &&  gameScrolling.allowedToScroll(Direction.LEFT, obj)){
			gameScrolling.setDirection(Direction.LEFT);
            runGameScrolling(speed);
		}
		else{
			double newXPos = obj.getXPosition() - Math.abs(speed);
			genMovement.moveLeft(obj, speed);
			checkXToroidalChange(obj, newXPos);
		}
        obj.setDirection(Direction.LEFT);
    }

	public void checkXToroidalChange(GameObject obj, double newXPos){
		GameBoundary gameBoundary = currLevel.getScrollType().getGameBoundary();
		if (obj.isPlayer() && gameBoundary.getClass() == ToroidalBoundary.class
			&& obj.getXPosition() != newXPos){
				if (obj.getXPosition()==0){
					gameScrolling.setDirection(Direction.LEFT);
					runGameScrolling(gameBoundary.getWorldWidth()-gameBoundary.getViewWidth());
					obj.setXDistanceMoved(0);
				}
				else{
					gameScrolling.setDirection(Direction.RIGHT);
					runGameScrolling(gameBoundary.getWorldWidth()-gameBoundary.getViewWidth());
					obj.setXDistanceMoved(gameBoundary.getWorldWidth()-obj.getWidth());
				}
		}
    }

	
	public void checkYToroidalChange(GameObject obj, double newYPos){
		GameBoundary gameBoundary = currLevel.getScrollType().getGameBoundary();
		if (obj.isPlayer() && currLevel.getScrollType().getGameBoundary().getClass() == ToroidalBoundary.class
			&& obj.getYPosition() != newYPos){
				if (obj.getYPosition()==0){
					gameScrolling.setDirection(Direction.UP);
					runGameScrolling(gameBoundary.getWorldHeight()-gameBoundary.getViewHeight());
					obj.setYDistanceMoved(obj.getHeight());
				}
				else{
					gameScrolling.setDirection(Direction.DOWN);
					runGameScrolling(gameBoundary.getWorldHeight()-gameBoundary.getViewHeight());
					obj.setYDistanceMoved(gameBoundary.getWorldHeight()-obj.getHeight());
				}
		}
	}
	
	
	@Override
	public void jump(GameObject obj, double speed) {
        String jumpVelocity;
        if(obj.getProperty("jumponce") != null && obj.isOnPlatform()){
			jumpVelocity = obj.getProperty("jumponce");
			obj.setProperty("fallspeed", "-" + jumpVelocity);
		}else if(obj.getProperty("jumpunlimited") != null){
        	jumpVelocity = obj.getProperty("jumpunlimited");
			obj.setProperty("fallspeed", "-" + jumpVelocity);
		}
	}

	@Override
	public void shootProjectile(GameObject obj, double speed) {
	    if(!projectileStatus.containsKey(obj) || (projectileStatus.containsKey(obj) && (System.currentTimeMillis() - projectileStatus.get(obj) > obj.getProjectileProperties().getTimeBetweenShots()*1000))) {
            projectileStatus.put(obj, System.currentTimeMillis());
	        if (obj.getProjectileProperties() != null) {
                ProjectileProperties properties = obj.getProjectileProperties();
                GameObject projectile = new GameObject(0, obj.getXPosition(), obj.getYPosition(),
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
											currLevel.getScrollType().getGameBoundary()};
		Class<?>[] parameterTypes = new Class<?>[]{Direction.class, double.class, GameBoundary.class};
			try {
				gameScrolling = (Scrolling) ReflectionUtil.getInstance(classPath, parameters, parameterTypes);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
