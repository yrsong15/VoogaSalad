package gameengine.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gameengine.controller.interfaces.GameHandler;
import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.CollisionChecker;
import gameengine.model.LossChecker;
import gameengine.model.RandomGenFrame;
import gameengine.model.WinChecker;
import gameengine.network.server.ServerMain;
import gameengine.view.GameEngineUI;
import javafx.scene.control.Alert;
import objects.ClientGame;
import objects.Game;
import objects.GameObject;
import objects.Level;
import objects.Player;
import objects.Position;
import objects.RandomGeneration;

public class GameEngineBackend implements RGInterface, GameHandler, RuleActionHandler {

	private List<RandomGenFrame> randomlyGeneratedFrames;
	private List<Integer> highScores;
	private CollisionChecker collisionChecker;
	private Game currentGame;
	private Position mainCharImprint;
	private MovementManager gameMovement;
	private ServerMain serverMain;

	
	public GameEngineBackend() {
		collisionChecker = new CollisionChecker(this);
		randomlyGeneratedFrames = new ArrayList<>();
		highScores = new ArrayList<>();
	}

	public void startGame(Game currentGame) {
		currentGame.getCurrentLevel().removeAllPlayers();
		this.currentGame = currentGame;
		this.mainCharImprint = new Position();
		gameMovement = new MovementManager(currentGame.getCurrentLevel(), GameEngineUI.myAppWidth,
				GameEngineUI.myAppHeight);
		addRGFrames();
		serverMain = new ServerMain(this, 9090);

	}
	
	public void addMainCharacter(int ID){
		currentGame.getCurrentLevel().addPlayer(currentGame.getPlayers().get(ID).getMainChar());
	}

	private void addRGFrames() {
		List<RandomGeneration> randomGenerations = currentGame.getCurrentLevel().getRandomGenRules();
		for (RandomGeneration randomGeneration : randomGenerations) {
			randomlyGeneratedFrames.add(new RandomGenFrame(this, 300, currentGame.getCurrentLevel()));
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
		/*
		 * for(RandomGenFrame elem: randomlyGeneratedFrames){
		 * for(RandomGeneration randomGeneration :
		 * currLevel.getRandomGenRules()) { try {
		 * elem.possiblyGenerateNewFrame(100, randomGeneration,
		 * this.getClass().getMethod("setNewBenchmark")); } catch
		 * (IllegalArgumentException | InvocationTargetException |
		 * IllegalAccessException | NoSuchMethodException | SecurityException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); } } }
		 */

		List<GameObject> mainChars = currLevel.getPlayers();
		for (GameObject mainChar : mainChars) {
			mainCharImprint.setPosition(mainChar.getXPosition(), mainChar.getYPosition());
			mainChar.checkPlatformStatus();
			collisionChecker.checkCollisions(mainChar, currLevel.getGameObjects());
		}
		collisionChecker.checkCollisions(currLevel.getProjectiles(), currLevel.getGameObjects());
		// checkProjectileDistance();
		LossChecker.checkLossConditions(this, currLevel.getLoseConditions(), currLevel.getGameConditions());
		WinChecker.checkWinConditions(this, currLevel.getWinConditions(), currLevel.getGameConditions());
	}

	public void setNewBenchmark() {
		List<GameObject> objects = currentGame.getCurrentLevel().getGameObjects();
		for (RandomGenFrame elem : randomlyGeneratedFrames) {
			elem.setNewBenchmark(new Integer((int) objects.get(objects.size() - 1).getXPosition() / 2));
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
		// TODO: SPLASH SCREEN

	}

	public void goNextLevel() {
		if (currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel() + 1) != null) {
			currentGame.setCurrentLevel(currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel() + 1));
		} else {
			winGame();
		}
	}

	public void addClientCharacter() {
//		currentGame.addPlayer(tempPlayer);
	}

	public void resetObjectPosition(GameObject mainChar, GameObject obj) {
		double newPosition;
		if (mainCharImprint.getY() < obj.getYPosition()) {
			newPosition = obj.getYPosition() - mainChar.getHeight();
			mainChar.setPlatformCharacterIsOn(obj);
		} else
			newPosition = obj.getYPosition() + obj.getHeight();

		mainChar.setYPosition(newPosition);
		mainChar.setXPosition(mainCharImprint.getX());
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
	public void runControl(String controlName, int ID) {
		try {
			Method method = gameMovement.getClass().getDeclaredMethod(controlName, GameObject.class, double.class);
			method.invoke(gameMovement, currentGame.getPlayers().get(ID).getMainChar(), 10);

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
		ClientGame clientGame = new ClientGame(currLevel.getMusicFilePath(), currLevel.getBackgroundFilePath());
		clientGame.addAll(game.getCurrentLevel().getAllGameObjects());
		return clientGame;
	}

}
