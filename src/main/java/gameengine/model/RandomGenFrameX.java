package gameengine.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import gameengine.controller.GameEngineController;
import gameengine.controller.interfaces.RGInterface;
import gameengine.model.SingletonPositionChecker.PositionStatus;
import gameengine.model.interfaces.BenchmarkInterface;
import gameengine.view.GameEngineUI;
import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;

public class RandomGenFrameX<T> extends RandomGenFrame<T> implements BenchmarkInterface {

	private static final double benchmarkPoint = GameEngineUI.myAppWidth + GameEngineController.delayUntilStart;

	public RandomGenFrameX(Level level, ArrayList<RandomGeneration<Integer>> randomGenerationRules, boolean isGeneratingEnemies) {
		this.randomGenRules = randomGenerationRules;
    	this.level = level;
    	this.generatingEnemies = isGeneratingEnemies;
	}
	
	@Override
	public <T extends Comparable<T>> void possiblyGenerateNewFrame(RGInterface handler, RandomGeneration<Integer> randomGenRules) {
        try{
        	if (referenceFirstObject == null || (SingletonPositionChecker.getInstance().checkHorizontalPosition(referenceFirstObject.getXPosition(), benchmarkPoint) == PositionStatus.LEFT)) {
        		generateNewFrameAndSetBenchmark(handler,level);
        	}
        }
 	   catch (Exception e){
		   throw new IllegalNullInputException("One of the input objects or level/handler is null within the list, the Singleton comparator cannot compare null objects",e);
	   } 
    }
	
	@Override
	public void setNewFirstBenchmark(GameObject object) { 
    	if(referenceFirstObject == null || (SingletonPositionChecker.getInstance().checkHorizontalPosition(object.getXPosition(), referenceFirstObject.getXPosition()) == PositionStatus.RIGHT)) {
    		referenceFirstObject = object;
    	}
    	
    }
	@Override
	public double calculateY(int margin, RandomGeneration<Integer> rg, double buffer) {
		try{
			return RNG.nextInt(margin) + rg.getMinY();
		}
	    catch (NullPointerException e){
		   throw new IllegalNullInputException("Input List of RandomGeneration is null, make sure you've input proper random generation parameters such that random generation has something baseline off of",e);
	    } 
	}
	@Override
	public int calculateMargin(RandomGeneration<Integer> rg) {
		try{
			int difference = rg.getMaxY() - rg.getMinY();
			return (difference > 0) ? difference : 1;
		}
	   catch (NullPointerException e){
		   throw new IllegalNullInputException("Input List of RandomGeneration is null, make sure you've input proper random generation parameters such that random generation has something baseline off of",e);
	   } 
	}
	@Override
	public double calculateX(int margin, RandomGeneration<Integer> rg, double buffer) {	
		//Comparing/adding two doubles, cannot have NPE
		return benchmarkPoint + buffer;
	}
}
	

