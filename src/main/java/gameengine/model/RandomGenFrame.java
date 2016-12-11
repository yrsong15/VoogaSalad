package gameengine.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
	private double benchmarkPoint = -200;
    private Level level;
    private ArrayList<RandomGeneration<Integer>> randomGenRules;
    private GameObject referenceFirstObject;
    private static final Random RNG = new Random();

    public RandomGenFrame (Level level, ArrayList<RandomGeneration<Integer>> randomGenerationRules) {
    	this.randomGenRules = randomGenerationRules;
    	this.level = level;
    }
    
    public ArrayList<RandomGeneration<Integer>> getRandomGenerationRules(){
    	return this.randomGenRules;
    }

    public void possiblyGenerateNewFrame (RandomGeneration<Integer> randomGenRules) {
        if (referenceFirstObject == null || referenceFirstObject.getYPosition() >= benchmarkPoint) {
            generateNewFrameAndSetBenchmark(level);
        }
    }

    private void generateNewFrameAndSetBenchmark(Level level) {
        for(RandomGeneration<Integer> elem:randomGenRules){
	        int minSep = elem.getMinSpacing();
	        int maxSep = elem.getMaxSpacing();
	        int buffer = 0;
	        for(int i=0; i<elem.getNumObjects();i++){
	            double nextSeparationDist = RNG.nextInt(maxSep - minSep) + minSep;
	            buffer += nextSeparationDist;
	            double YPosition = benchmarkPoint - buffer;
	            int xMargin = elem.getMaxX() - elem.getMinX();
	            if(xMargin <= 0) xMargin = 1;
	            double randomXPosition = RNG.nextInt(xMargin) + elem.getMinX();
	            double width = elem.getWidth();
	            double height = elem.getHeight();
	            generateObject(randomXPosition, YPosition, width, height, elem.getImageURL(),elem.getObjectProperties());
	        }
        }

    }

    private void generateObject(double xPosition,double yPosition, double width, double height, String URL, Map<String, String> objectProperties) {
        GameObject object = new GameObject(xPosition, yPosition, width, height, URL,objectProperties);
        level.getGameObjects().add(object);
        setNewFirstBenchmark(object);
    }
    
    private void setNewFirstBenchmark(GameObject object){
    	if(referenceFirstObject == null || (object.getYPosition() < referenceFirstObject.getYPosition())) {
    		referenceFirstObject = object;
    	}
    }
   
    
}
