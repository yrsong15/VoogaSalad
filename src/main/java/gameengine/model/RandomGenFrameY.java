package gameengine.model;

import java.util.ArrayList;

import gameengine.view.GameEngineUI;
import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;

public class RandomGenFrameY<T> extends RandomGenFrame<T>{
	private double benchmarkPoint = -200;
	
	public RandomGenFrameY(Level level, ArrayList<RandomGeneration<Integer>> randomGenerationRules) {
		this.randomGenRules = randomGenerationRules;
    	this.level = level;
	}
	
	@Override
	public void possiblyGenerateNewFrame(RandomGeneration<Integer> randomGenRules) {
        if (referenceFirstObject == null || referenceFirstObject.getYPosition() >= benchmarkPoint) {
            generateNewFrameAndSetBenchmark(level);
        }
    }
	
	@Override
	public void setNewFirstBenchmark(GameObject object) {
    	if(referenceFirstObject == null || (object.getYPosition() < referenceFirstObject.getYPosition())) {
    		referenceFirstObject = object;
    	}
    }

	@Override
	public double calculateY(int margin, RandomGeneration rg, double buffer) {
		return benchmarkPoint - buffer;
	}

	@Override
	public int calculateMargin(RandomGeneration rg) {
		RandomGeneration<Integer> elem = rg;
		return elem.getMaxX() - elem.getMinX();
	}

	@Override
	public double calculateX(int margin, RandomGeneration rg, double buffer) {
		RandomGeneration<Integer> elem = rg;
		return RNG.nextInt(margin) + elem.getMinX();
	}
}
