package gameengine.model;

import java.util.ArrayList;

import gameengine.view.GameEngineUI;
import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;

public class RandomGenFrameX<T> extends RandomGenFrame<T>{
	private double benchmarkPoint = GameEngineUI.myAppWidth + 200;

	public RandomGenFrameX(Level level, ArrayList<RandomGeneration<Integer>> randomGenerationRules, boolean isGeneratingEnemies) {
		this.randomGenRules = randomGenerationRules;
    	this.level = level;
    	this.generatingEnemies = isGeneratingEnemies;
	}
	
	@Override
	public void possiblyGenerateNewFrame(RandomGeneration<Integer> randomGenRules) {
        if (referenceFirstObject == null || referenceFirstObject.getXPosition() <= benchmarkPoint) {
            generateNewFrameAndSetBenchmark(level);
        }
    }
	
	@Override
	public void setNewFirstBenchmark(GameObject object) {
    	if(referenceFirstObject == null || (object.getXPosition() > referenceFirstObject.getXPosition())) {
    		referenceFirstObject = object;
    	}
    }
	@Override
	public double calculateY(int margin, RandomGeneration rg, double buffer) {
		RandomGeneration<Integer> elem = rg;
		return RNG.nextInt(margin) + elem.getMinY();
	}
	@Override
	public int calculateMargin(RandomGeneration rg) {
		RandomGeneration<Integer> elem = rg;
		return elem.getMaxY() - elem.getMinY();
	}
	@Override
	public double calculateX(int margin, RandomGeneration rg, double buffer) {	
		return benchmarkPoint + buffer;
	}
}
	

