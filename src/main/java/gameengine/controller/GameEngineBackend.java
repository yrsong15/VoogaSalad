package gameengine.controller;

import java.lang.reflect.Method;
import java.util.*;

import com.sun.javafx.scene.traversal.Direction;

import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.GameHandler;
import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.CollisionChecker;
import gameengine.controller.SingletonBoundaryChecker.IntersectionAmount;
import gameengine.model.LossChecker;
import gameengine.model.RandomGenFrame;
import gameengine.model.WinChecker;
import gameengine.network.server.ServerMain;
import gameengine.view.GameEngineUI;
import javafx.scene.Node;
import objects.*;

public class GameEngineBackend implements RGInterface, GameHandler, RuleActionHandler {

	private List<RandomGenFrame> randomlyGeneratedFrames;
	private List<Integer> highScores;
	private CollisionChecker collisionChecker;
	private Game currentGame;
	private MovementManager gameMovement;
	private ServerMain serverMain;
	private Map<GameObject, Position> mainCharImprints;
	private String serverName;
	private Node toolbarHBox;
	private CommandInterface commandInterface;

	public GameEngineBackend(CommandInterface commandInterface, String serverName) {
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
	 */
	public void updateGame() {
		Level currLevel = currentGame.getCurrentLevel();
		if (currentGame.getCurrentLevel().getScrollType().getScrollTypeName().equals("ForcedScrolling")) {
			removeOffscreenElements();
		}
		gameMovement = new MovementManager(currentGame.getCurrentLevel(), GameEngineUI.myAppWidth,
				GameEngineUI.myAppHeight);
		gameMovement.runActions();

		List<GameObject> mainChars = currLevel.getPlayers();
		for (GameObject mainChar : mainChars) {
			Position position = new Position();
			position.setPosition(mainChar.getXPosition(), mainChar.getYPosition());
			mainCharImprints.put(mainChar, position);
			mainChar.checkPlatformStatus();
		}
		checkProjectileDistance();
		if(currLevel.getRandomGenRules().size() > 0) {
            randomlyGenerateFrames();
        }
        if(toolbarHBox != null){
			toolbarHBox.toFront();
		}
		
		collisionChecker.checkCollisions(currLevel.getPlayers(), currLevel.getGameObjects());
		collisionChecker.checkCollisions(currLevel.getProjectiles(), currLevel.getGameObjects()); // checkProjectileDistance();
		LossChecker.checkLossConditions(this, currLevel.getLoseConditions(), currLevel.getGameConditions());
		WinChecker.checkWinConditions(this, currLevel.getWinConditions(), currLevel.getGameConditions());
	}

	private void randomlyGenerateFrames(){
		for(RandomGeneration<Integer> randomGeneration : currentGame.getCurrentLevel().getRandomGenRules()) {
			try {
				currentGame.getCurrentLevel().getRandomGenerationFrame().possiblyGenerateNewFrame(randomGeneration);
			} catch (IllegalArgumentException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void removeOffscreenElements() {
		List<GameObject> objects = currentGame.getCurrentLevel().getAllGameObjects();
		if (objects.size() == 0 || objects == null)
			return;
		for (int i = objects.size() - 1; i >= 0; i--) {
			if (objects.get(i).getXPosition() > -(2 * GameEngineUI.myAppWidth) || objects.get(i) == null)
				continue;// CHANGE THIS TO PIPE WIDTH
			objects.remove(i);
		}
	}

	@Override
	public void removeObject(GameObject obj) {
		currentGame.getCurrentLevel().removeGameObject(obj);
	}

	@Override
	public void winGame() {
		

	}

	public void goNextLevel() {
		if (currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel() + 1) != null) {
			currentGame.setCurrentLevel(currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel() + 1));
			commandInterface.nextLevel();
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

	public void resetObjectPosition(GameObject mainChar, GameObject obj) {
		double newPosition;
		if (SingletonBoundaryChecker.getInstance().getHorizontalIntersectionAmount(mainChar,
				obj) == IntersectionAmount.COMPLETELY_INSIDE_X) {
			if (mainCharImprints.get(mainChar).getY() < obj.getYPosition()) {
				newPosition = obj.getYPosition() - mainChar.getHeight() + 5;
				mainChar.setPlatformCharacterIsOn(obj);
			} else
				newPosition = obj.getYPosition() + obj.getHeight();
		} else {
			newPosition = mainCharImprints.get(mainChar).getY();
		}

		mainChar.setYPosition(newPosition);
		mainChar.setXPosition(mainCharImprints.get(mainChar).getX());
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
	public void modifyScore(int score) {
		int prevScore = currentGame.getCurrentLevel().getScore();
		int currScore = prevScore + score;
		currentGame.getCurrentLevel().setScore(currScore);
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
		addHighScore(currentGame.getCurrentLevel().getScore());
	}

	@Override
	public void runControl(String controlName, int ID, int charIdx) {
		try {
			Method method = gameMovement.getClass().getDeclaredMethod(controlName, GameObject.class, double.class);
			method.invoke(gameMovement, currentGame.getClientMappings().get(new Long(ID)).get(charIdx).getMainChar(), 10);

		} catch (Exception ex) {
			ex.printStackTrace();
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
					removeObject(projectile);
					itr.remove();
				}
			} else {
				if (Math.abs(projectile.getYDistanceMoved()) >= properties.getRange()) {
					removeObject(projectile);
					itr.remove();
				}
			}
		}
	}

}
