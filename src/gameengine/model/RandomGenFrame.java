package gameengine.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;
import gameengine.controller.interfaces.*;

public class RandomGenFrame<T>{
	
	private T benchmark;
	private Level level;
	private RGInterface RGinterface;
	private static final Random RNG = new Random();
	private String objectURL;
	
	public RandomGenFrame(RGInterface RGinterface, T newFramePoint, Level level,String objectURL){
		this.benchmark = newFramePoint;
		this.level = level;
		this.RGinterface = RGinterface;
		this.objectURL = objectURL;
	}
	
	public <T extends Comparable<T>> void possiblyGenerateNewFrame(T xPosition, RandomGeneration<Integer> randomGenRules, Method callback) throws IllegalArgumentException, InvocationTargetException, IllegalAccessException {
		//System.out.println("benchmark " + benchmark + " Position: " + xPosition);
		if(xPosition.compareTo((T) benchmark) >= 0){
			generateNewFrame(level,randomGenRules);
		}
		callback.invoke(this.RGinterface);
		
	}
	
	private void generateNewFrame(Level level, RandomGeneration<Integer> randomGenRules) {
		int val = 0; 
		int minX = randomGenRules.getMinX();
		int minSep = randomGenRules.getMinSpacing(); int maxSep = randomGenRules.getMaxSpacing();

		while(val<randomGenRules.getNumObjects()){
			val++;
			int nextSeparationDist = RNG.nextInt(maxSep - minSep) + minSep;
			minX += nextSeparationDist;
			
			int randomYPosition = RNG.nextInt(randomGenRules.getMaxY()- randomGenRules.getMinY()) + randomGenRules.getMinY();
	        GameObject pipe = new GameObject(minX, randomYPosition, 80, 200, objectURL, new HashMap<>());
            pipe.setPropertiesList(randomGenRules.getGameObject().getProperties());
			level.getGameObjects().add(pipe);
		}
	}
	
	
	
	public void setNewBenchmark(T newVal){
		this.benchmark = newVal;
	}

}
