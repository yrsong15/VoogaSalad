package gameengine.controller;

import exception.ScrollDirectionNotFoundException;
import exception.ScrollTypeNotFoundException;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.model.MovementChecker;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.boundary.ToroidalBoundary;
import gameengine.network.server.ServerMain;
import gameengine.scrolling.ScrollDirection;
import gameengine.scrolling.Scrolling;
import objects.GameObject;
import objects.Level;
import objects.ProjectileProperties;
import objects.ScrollType;
import utils.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.scene.traversal.Direction;


public class MovementManager implements ControlInterface{
	private Level currLevel;
	private String scrollName;
	private GeneralMovement genMovement;
	private Scrolling gameScrolling;
	private MovementChecker movementChecker;
	private Direction scrollDir;
	private Map<Integer, Long> projectileStatus;

	public MovementManager(Level currLevel, double screenWidth, double screenHeight){
		projectileStatus = new HashMap<>();
		this.currLevel = currLevel;
		scrollName = currLevel.getScrollType().getScrollTypeName();
		initManager();
	}

	public void setCurrLevel(Level currLevel){
		this.currLevel = currLevel;
	}

	private void initManager(){
		try {
			setScrolling();
		} catch (ScrollTypeNotFoundException e) {
		}
		movementChecker = new MovementChecker((ControlInterface) this, currLevel.getScrollType().getGameBoundary());
		genMovement = new GeneralMovement(currLevel, currLevel.getScrollType().getGameBoundary());
	}

	public ControlInterface getControlInterface(){
		return (ControlInterface) this;
	}

	public void runActions(){
		if(scrollName.equals("ForcedScrolling") && currLevel.getPlayers().size() != 0){
			runGameScrolling();
		}
		movementChecker.updateMovement(currLevel);
	}

	@Override
	public void moveUp(GameObject obj, double speed) {
		if (obj.isPlayer() && gameScrolling.allowedToScroll(ScrollDirection.UP, obj)){
			gameScrolling.setDirection(ScrollDirection.UP);
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
		if (obj.isPlayer() &&  gameScrolling.allowedToScroll(ScrollDirection.DOWN, obj)){
			System.out.println("scrolling down");
			gameScrolling.setDirection(ScrollDirection.DOWN);
			runGameScrolling(speed);
		}
		else{
			System.out.println("not allowed to scroll");
			double newYPos = obj.getYPosition() + Math.abs(speed);
			genMovement.moveDown(obj, speed);
			checkYToroidalChange(obj, newYPos);
		}
	}

	@Override
	public void moveRight(GameObject obj, double speed) {
		if (obj.isPlayer() &&  gameScrolling.allowedToScroll(ScrollDirection.RIGHT, obj)){
			gameScrolling.setDirection(ScrollDirection.RIGHT);
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
		if (obj.isPlayer() &&  gameScrolling.allowedToScroll(ScrollDirection.LEFT, obj)){
			gameScrolling.setDirection(ScrollDirection.LEFT);
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
					gameScrolling.setDirection(ScrollDirection.LEFT);
					runGameScrolling(gameBoundary.getWorldWidth()-gameBoundary.getViewWidth());
					obj.setXDistanceMoved(0);
				}
				else{
					gameScrolling.setDirection(ScrollDirection.RIGHT);
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
					gameScrolling.setDirection(ScrollDirection.UP);
					runGameScrolling(gameBoundary.getWorldHeight()-gameBoundary.getViewHeight());
					obj.setYDistanceMoved(obj.getHeight());
				}
				else{
					gameScrolling.setDirection(ScrollDirection.DOWN);
					runGameScrolling(gameBoundary.getWorldHeight()-gameBoundary.getViewHeight());
					obj.setYDistanceMoved(gameBoundary.getWorldHeight()-obj.getHeight());
				}
		}
	}
	
	
	@Override
	public void jump(GameObject obj, double speed) {
        String jumpVelocity = null;
        if(obj.getProperty("jumponce") != null && obj.isOnPlatform()){
			jumpVelocity = obj.getProperty("jumponce");
			obj.setProperty("fallspeed", "-" + jumpVelocity);
		}else if(obj.getProperty("jumpunlimited") != null){
        	jumpVelocity = obj.getProperty("jumpunlimited");
			obj.setProperty("fallspeed", "-" + jumpVelocity);
		}
    	if(jumpVelocity!=null){
    		obj.setProperty("fallspeed", "-" + jumpVelocity);
    	}
	}

	@Override
	public void shootProjectile(GameObject obj, double speed){
	    if(!projectileStatus.containsKey(obj.getID()) || ((projectileStatus.containsKey(obj.getID()) && (System.currentTimeMillis() - projectileStatus.get(obj.getID()) > obj.getProjectileProperties().getTimeBetweenShots()*1000)))) {
	        projectileStatus.put(obj.getID(), System.currentTimeMillis());
            if (obj.getProjectileProperties() != null) {
                ProjectileProperties properties = obj.getProjectileProperties();
                GameObject projectile = new GameObject(0, obj.getXPosition()+obj.getWidth()/2, obj.getYPosition()+obj.getHeight()/3,
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
                projectile.setProjectileProperties(properties);
                obj.getProjectiles().add(projectile);
                currLevel.getProjectiles().add(projectile);
            }
        }
    }

	private void setScrolling() throws ScrollTypeNotFoundException{
		System.out.println("setting scrolling");
		ScrollType gameScroll = currLevel.getScrollType();
		String classPath = "gameengine.scrolling." + gameScroll.getScrollTypeName();
		Object[] parameters = new Object[]{gameScroll.getDirections().get(0), gameScroll.getScrollSpeed(), 
											currLevel.getScrollType().getGameBoundary()};
		Class<?>[] parameterTypes = new Class<?>[]{ScrollDirection.class, double.class, GameBoundary.class};
			try {
				gameScrolling = (Scrolling) ReflectionUtil.getInstance(classPath, parameters, parameterTypes);
			System.out.println("set it");
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				
			}
	}
	
	private void runGameScrolling() {
		System.out.println("running game scrolling");
		try {			
			List<GameObject> scrollObjects = new ArrayList<GameObject>(currLevel.getGameObjects());
			if (currLevel.getBackground()!=null){
				scrollObjects.add(currLevel.getBackground());
			}
			gameScrolling.scrollScreen(scrollObjects, currLevel.getPlayers().get(0));
		} catch (ScrollDirectionNotFoundException e) {
			System.out.println("not found");
		}
	}
	
	private void runGameScrolling(double speed) {
		System.out.println("funning other scrolignf for masdf");
		try {
			List<GameObject> scrollObjects = new ArrayList<GameObject>(currLevel.getGameObjects());
			if (currLevel.getBackground()!=null){
				scrollObjects.add(currLevel.getBackground());
			}
			gameScrolling.scrollScreen(scrollObjects, currLevel.getPlayers().get(0), speed);
		} catch (ScrollDirectionNotFoundException e) {
			System.out.println("still not found");
		}
	}
}
