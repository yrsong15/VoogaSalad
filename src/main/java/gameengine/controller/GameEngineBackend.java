package gameengine.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import com.sun.javafx.scene.traversal.Direction;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.GameHandler;
import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.CollisionChecker;
import gameengine.controller.SingletonBoundaryChecker.IntersectionAmount;
import gameengine.model.ConditionChecker;
import gameengine.model.EnemyMisreferencedException;
import gameengine.model.RandomGenFrame;
import gameengine.network.server.ServerMain;
import gameengine.view.GameEngineUI;
import javafx.scene.Node;
import objects.*;

public class GameEngineBackend implements RGInterface, GameHandler, RuleActionHandler {
	private static final int marginCollisionSeparation = 10;
    private static final double enemySize = 50;
	private List<RandomGenFrame> randomlyGeneratedFrames;
	private List<Integer> highScores;
	private CollisionChecker collisionChecker;
	private ConditionChecker conditionChecker;
	private Game currentGame;
	private MovementManager gameMovement;
	private ServerMain serverMain;
	private Map<GameObject, Position> mainCharImprints;
	private String serverName;
	private Node toolbarHBox;
	private CommandInterface commandInterface;
	private GameObject referenceObject;

	public GameEngineBackend(CommandInterface commandInterface, String serverName) {
		this.commandInterface = commandInterface;
		this.serverName = serverName;
		this.commandInterface = commandInterface;
		collisionChecker = new CollisionChecker(this);
		randomlyGeneratedFrames = new ArrayList<>();
		highScores = new ArrayList<>();
		mainCharImprints = new HashMap<>();
	}

	public void startGame(Game currentGame) {
		this.currentGame = currentGame;
		currentGame.getCurrentLevel().removeAllPlayers();
	
		
		String scrollType = currentGame.getCurrentLevel().getScrollType().getScrollTypeName();
		if (scrollType.equals("FreeScrolling")){
			currentGame.getCurrentLevel().setBackgroundObject();
		}
		gameMovement = new MovementManager(currentGame.getCurrentLevel(), GameEngineUI.myAppWidth,
				GameEngineUI.myAppHeight);
        conditionChecker = new ConditionChecker();
        serverMain = new ServerMain(this, 9090, serverName);
	}

	public void addPlayersToClient(int ID) {
		for (Long id: currentGame.getClientMappings().keySet()) {
			if (id.equals(new Long(ID))) {
				for (Player p : currentGame.getClientMappings().get(id)) {
					currentGame.getCurrentLevel().addPlayer(p.getMainChar());
				}
			}
		}
	}

	/**
	 * Applies gravity and scrolls, checks for collisions
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws EnemyMisreferencedException 
	 */
	public void updateGame() throws IllegalAccessException, InvocationTargetException, EnemyMisreferencedException {
		Level currLevel = currentGame.getCurrentLevel();
		gameMovement.setCurrLevel(currentGame.getCurrentLevel());
		if (currentGame.getCurrentLevel().getScrollType().getScrollTypeName().equals("ForcedScrolling") || currentGame.getCurrentLevel().getScrollType().getScrollTypeName().equals("LimitedScrolling")) {
			removeOffscreenElements();
		}
		gameMovement.runActions();
		
		checkProjectileDistance();
		if(currLevel.getRandomGenRules().size() > 0) {
            try {
				randomlyGenerateFrames();
			} catch (NoSuchMethodException e) {
			}
        }
        if(toolbarHBox != null){
			toolbarHBox.toFront();
		}
		
		collisionChecker.checkCollisions(currLevel.getPlayers(), currLevel.getGameObjects());
		collisionChecker.checkCollisions(currLevel.getProjectiles(), currLevel.getGameObjects()); // checkProjectileDistance();
		conditionChecker.checkConditions(this, currentGame.getCurrentLevel().getWinConditions(), currentGame.getCurrentLevel().getLoseConditions());

		List<GameObject> objects = currLevel.getAllGameObjects();
		for(GameObject object : objects){
			object.setXPosition(object.getXPosition() + object.getVelX());

		}

		List<GameObject> mainChars = currLevel.getPlayers();
		for (GameObject mainChar : mainChars) {
			Position position = new Position();
			position.setPosition(mainChar.getXPosition(), mainChar.getYPosition());
			mainCharImprints.put(mainChar, position);
			mainChar.checkPlatformStatus();
		}
	}
	private double setNewPosition(double mainCharacterReference, GameObject mainChar, GameObject obj){
		double newPosition =  mainCharImprints.get(mainChar).getY();
		if (mainCharacterReference < obj.getYPosition()) {
			newPosition = obj.getYPosition() - mainChar.getHeight() - marginCollisionSeparation/2;
			mainChar.setPlatformCharacterIsOn(obj);
		}
		
		return newPosition;
	}
	

	public void resetObjectPosition(GameObject mainChar, GameObject obj, boolean oneSided) {
		double newPosition;
		if(oneSided && SingletonBoundaryChecker.getInstance().getHorizontalIntersectionAmount(mainChar,
				obj) != IntersectionAmount.NOT_INTERSECTING) {
			newPosition = setNewPosition(mainCharImprints.get(mainChar).getY(), mainChar, obj);
		}
		else if ((SingletonBoundaryChecker.getInstance().getHorizontalIntersectionAmount(mainChar,obj) != IntersectionAmount.NOT_INTERSECTING)) {
			newPosition = setNewPosition(mainCharImprints.get(mainChar).getY() + mainChar.getHeight() - marginCollisionSeparation, mainChar, obj);
		} else {
			newPosition = mainCharImprints.get(mainChar).getY();
		}

		mainChar.setYPosition(newPosition);
		mainChar.setXPosition(mainCharImprints.get(mainChar).getX());
	}

	private void randomlyGenerateFrames() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, EnemyMisreferencedException{
		for(RandomGeneration<Integer> randomGeneration : currentGame.getCurrentLevel().getRandomGenRules()) {
			try {
				//this.getClass().getMethod("generateEnemyOnPlatform"),this.getClass().getMethod("generateObject")
				currentGame.getCurrentLevel().getRandomGenerationFrame().possiblyGenerateNewFrame(this,randomGeneration);
			} catch (IllegalArgumentException | SecurityException e) {
			}
		}
	}
	
	//Used within the RandomGenFrame, better to create objects here though with all other creation methods
	private void generateEnemyOnPlatform(GameObject referencePlatform){
		GameObject enemy = new Enemy(referencePlatform.getXPosition() + ((referencePlatform.getWidth() - enemySize)/2), referencePlatform.getYPosition() - enemySize, enemySize, enemySize, "duvall.png", new HashMap<>());
		currentGame.getCurrentLevel().getGameObjects().add(enemy);
	}
	
	//Used within the RandomGenFrame, better to create objects here though with all other creation methods
	private void generateObject(double xPosition,double yPosition, double width, double height, String URL, Map<String, String> objectProperties) {
    	GameObject object = new GameObject(xPosition, yPosition, width, height, URL,objectProperties);
        currentGame.getCurrentLevel().getGameObjects().add(object);
        this.referenceObject = object;
    }
    
    public GameObject getReferenceObject(){
    	return this.referenceObject;
    }
	

	private void removeOffscreenElements() {
		List<GameObject> objects = currentGame.getCurrentLevel().getAllGameObjects();
		if (objects == null || objects.size() == 0)
			return;
		for (int i = objects.size() - 1; i >= 0; i--) {
			GameObject obj = objects.get(i);
            if(obj.getYPosition() > GameEngineUI.myAppHeight && obj.getProperty("nonscrollable") == null){
                removeObject(obj);
            }
		}
	}

	@Override
	public void removeObject(GameObject obj) {
		currentGame.getCurrentLevel().removeGameObject(obj);
	}

	@Override
	public void winGame() {
		currentGame.setGameWon(true);
	}

	@Override
	public boolean reachedScore(int score) {
	    for(Map.Entry<Long, Integer> mapping : currentGame.getScoreMapping().entrySet()) {
            if(mapping.getValue() >= score){
                return true;
            }
        }
        return false;
	}

	@Override
	public int getTime() {
		return currentGame.getCurrentLevel().getTime();
	}

    public long getPlayerID(GameObject object) {
        for(Map.Entry<Long, List<Player>> mapping : currentGame.getClientMappings().entrySet()){
            for(Player player : mapping.getValue()){
                if(player.getMainChar() == object){
                    return mapping.getKey();
                }else{
                    List<GameObject> projectiles = player.getMainChar().getProjectiles();
                    for(int i = 0; i <projectiles.size(); i ++){
                        if(projectiles.get(i) == object){
                            return mapping.getKey();
                        }
                    }
                }
            }
        }
        return -1;
    }

    public void goNextLevel() {
		if (currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel()+1) != null) {
			currentGame.setCurrentLevel(currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel() + 1));
			if (currentGame.getCurrentLevel().getScrollType().getScrollTypeName().equals("FreeScrolling")){
				currentGame.getCurrentLevel().setBackgroundObject();
			}
			GameObject mario = currentGame.getCurrentLevel().getPlayers().get(0);
			mario.setXPosition(50);
			mario.setYPosition(50);
			mario.setXDistanceMoved(0);
			mario.setYDistanceMoved(0);
			
		} else {
			winGame();
		}
	}

	public void addClientCharacter() {
		// currentGame.addPlayer(tempPlayer);
	}
	
	public void setToolbarHBox(Node toolbarHBox){
		this.toolbarHBox = toolbarHBox;
	}

	private void addHighScore(int score) {
		if (highScores.size() == 0) {
			highScores.add(score);
		} else if (highScores.size() < 5) {
			highScores.add(score);
			Collections.sort(highScores);
			Collections.reverse(highScores);
		} else {
			Integer lowestHighScore = highScores.get(4);
			if (score > lowestHighScore) {
				highScores.remove(lowestHighScore);
				highScores.add(score);
				Collections.sort(highScores);
				Collections.reverse(highScores);
			}
		}
	}

	@Override
	public void modifyScore(long ID, int score) {
		int prevScore = currentGame.getScore(ID);
		int currScore = prevScore + score;
		currentGame.modifyScore(ID, currScore);
	}

	@Override
	public void removeFromCollidedList(GameObject obj) {
		collisionChecker.manuallyRemoveFromConcurrentCollisionList(obj);
	}

	@Override
	public Game getGame() {
		return currentGame;
	}

	@Override
	public void endGame() {
        for(Map.Entry<Long, Integer> mapping : currentGame.getScoreMapping().entrySet()) {
			addHighScore(mapping.getValue());
		}
        currentGame.setGameOver(true);
	}

	@Override
	public void runControl(String controlName, int ID, int charIdx) {
		try {
			Method method = gameMovement.getClass().getDeclaredMethod(controlName, GameObject.class, double.class);
			method.invoke(gameMovement, currentGame.getClientMappings().get(new Long(ID)).get(charIdx).getMainChar(), 10);

		} catch (Exception ex) {
		}
	}

	@Override
	public ClientGame getClientGame() {
		return generateClientGame(getGame());
	}

	private ClientGame generateClientGame(Game game) {
		Level currLevel = game.getCurrentLevel();
		ClientGame clientGame = new ClientGame(currLevel.getMusicFilePath(), currLevel.getBackgroundFilePath(), highScores);
		
		clientGame.addAll(game.getCurrentLevel().getAllGameObjects());
		clientGame.addScores(game.getScoreMapping());
		clientGame.setGameInfo(currLevel.getLevel(), game.isGameLost(), game.isGameWon());
		if (currLevel.getBackground()!=null){
			clientGame.setBackgroundObject(currLevel.getBackground());
		}
		return clientGame;
	}

	private void checkProjectileDistance() {
		for (Iterator<GameObject> itr = currentGame.getCurrentLevel().getProjectiles().iterator(); itr.hasNext();) {
			GameObject projectile = itr.next();
			ProjectileProperties properties = projectile.getProjectileProperties();
			if (properties.getDirection().equals(Direction.RIGHT) || properties.getDirection().equals(Direction.LEFT)) {
				if (projectile.getXDistanceMoved() >= properties.getRange()) {
				    projectile.getProjectiles().remove(projectile);
					removeObject(projectile);
					itr.remove();
				}
			} else {
				if (Math.abs(projectile.getYDistanceMoved()) >= properties.getRange()) {
                    projectile.getProjectiles().remove(projectile);
                    removeObject(projectile);
					itr.remove();
				}
			}
		}
	}

	@Override
	public void restart() {
		commandInterface.reset();
	}

	public void setGame(Game currentGame) {
		this.currentGame = currentGame;
		if (currentGame.getCurrentLevel().getScrollType().getScrollTypeName().equals("FreeScrolling")){
			currentGame.getCurrentLevel().setBackgroundObject();
		}
	}
}
