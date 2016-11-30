package gameengine.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;
import gameengine.controller.interfaces.*;
import gameengine.view.GameScreen;

public class RandomGenFrame<T> {

	private T benchmark;
	private Level level;
	private RGInterface RGinterface;
	private static final Random RNG = new Random();

	public RandomGenFrame(RGInterface RGinterface, T newFramePoint, Level level) {
		this.benchmark = newFramePoint;
		this.level = level;
		this.RGinterface = RGinterface;
	}

	public <T extends Comparable<T>> void possiblyGenerateNewFrame(T xPosition,
			RandomGeneration<Integer> randomGenRules, Method callback)
			throws IllegalArgumentException, InvocationTargetException, IllegalAccessException {
		// System.out.println("benchmark " + benchmark + " Position: " +
		// xPosition);
		if (xPosition.compareTo((T) benchmark) >= 0) {
			generateNewFrame(level, randomGenRules);
		}
		callback.invoke(this.RGinterface);

	}

	private void generateNewFrame(Level level, RandomGeneration<Integer> randomGenRules) {
		int val = 0;
		int minX = randomGenRules.getMinX();
		int minSep = randomGenRules.getMinSpacing();
		int maxSep = randomGenRules.getMaxSpacing();

		while (val < randomGenRules.getNumObjects()) {
			val++;
			int nextSeparationDist = RNG.nextInt(maxSep - minSep) + minSep;
			minX += nextSeparationDist;
			int randomYPosition = RNG.nextInt(randomGenRules.getMaxY() - randomGenRules.getMinY())
					+ randomGenRules.getMinY();
			// generatePipesAndScoreObjects(minX, randomYPosition, 80, 200,
			// objectURL, new HashMap<>());
			generatePipesAndScoreObjects(minX, randomYPosition, 80, randomGenRules.getObjectProperties());
		}
	}

	private void generatePipesAndScoreObjects(double xPosition, double yPosition, double width,
			Map objectProperties) {
		double gapHeight = 100; // between the two pipes

		GameObject topPipe = new GameObject(xPosition, 0, width, yPosition - gapHeight / 2, "Pipes.png", new HashMap<>());
		topPipe.setPropertiesList(objectProperties);
		level.getGameObjects().add(topPipe);

		GameObject bottomPipe = new GameObject(xPosition, yPosition + gapHeight / 2, width,
				GameScreen.screenHeight - (yPosition + gapHeight / 2), "Pipes.png", new HashMap<>());
		bottomPipe.setPropertiesList(objectProperties);
		level.getGameObjects().add(bottomPipe);
		
		
	}

	public void setNewBenchmark(T newVal) {
		this.benchmark = newVal;
	}

}
