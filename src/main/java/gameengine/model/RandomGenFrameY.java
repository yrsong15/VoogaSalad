package gameengine.model;

import java.util.ArrayList;
import gameengine.controller.GameEngineController;
import gameengine.controller.interfaces.RGInterface;
import gameengine.model.SingletonPositionChecker.PositionStatus;
import gameengine.model.interfaces.BenchmarkInterface;
import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;

// This entire file is part of my masterpiece	
// BRIAN ZHOU

/**
 * Uncollapse this to see notes/what I'm proud of
 * What I'm Proud Of:
 * 
 * Required addition of subclass for the superclass RandomGenFrame. Most of the meat is within the RandomGenFrame so for most of the details about design decisions, see that masterpiece header in RandomGenFrame
 * For this specific class, I'm proud of how I managed to weave into and abstract the big method in the superclass so that this subclass only required the absolute methods that fluctuated depending on the axis you were randomly
 * generating on. There isn't anything that I'm particularly proud of that's unique to this class (most of it is explained in the superclass masterpiece header), but I feel that the Singleton behavior (other masterpiece header) is good in that
 * it is extendable for position checking.
 * 
 * Also, the generics comparable methods are interesting in that they make it so we can always use generics; so, in the future, if objects are required to use integers or doubles or a combination, we can easily use them without
 * having to integrate/write new code because the generics generalize the type behavior and the compareTo will ensure the proper logic.
 * In terms of extending the superclass, this class is an example in how you would implement the methods in that you would fill out the calculate methods with the proper metrics for random generation in creating new values, as well
 * as setting up the proper benchmark points and methods for changing benchmarks whenever you needed to update random generation. Most of the methods are clear in their purpose and if you wanted to extend this
 * into the z-axis, you could follow this class as a template to easily integrate it into the new part of the project.
 * 
 * Finally, this class is pretty much finished for most cases, so it follows the open-closed principle in that you wouldn't need to modify existing code due to the generics and comparators that cover generalized cases - you would only
 * need to change the methods if you drastically changed the algorithm for random generation. There is also basic error checking to narrow down the source issue for caused exceptions (created my own exception to guide coders to source of error)
 * 
 */

//End Masterpiece Header


/**
 * UNCOLLAPSE ABOVE
 * This class is responsible for most of the specific random generation behavior, such as generating the correct parameters for X and Y for the new randomly generated objects, in addition to checking when
 * it's necessary for a new randomly generated frame to be created and sent over to the controller (when benchmark object goes past benchmark point);
 * 
 * @author Brian
 * @method possiblyGenerateNewFrame
 * @method setNewFirstBenchmark
 * @method calculateY
 * @method calculateX
 * @method calculateMargin
 * @implements BenchmarkInterface, which requires a method that sets the new benchmark 
 */

public class RandomGenFrameY<T> extends RandomGenFrame<T> implements BenchmarkInterface{
	private double benchmarkPoint = GameEngineController.initialBenchmark;
	
	/**
	 * Constructor for RandomGenFrame
	 * @param level
	 * @param randomGenerationRules
	 * @param isGeneratingEnemies
	 */
	public RandomGenFrameY(Level level, ArrayList<RandomGeneration<Integer>> randomGenerationRules, boolean isGeneratingEnemies) {
		this.randomGenRules = randomGenerationRules;
    	this.level = level;
    	this.generatingEnemies = isGeneratingEnemies;
	}
	
	/**
	 * Class checks position of objects relative to benchmark and decides if you need to tell the controller to start a new random frame
	 * @throws IllegalNullInputException 
	 */
	@Override
	public <T extends Comparable<T>> void possiblyGenerateNewFrame(RGInterface handler, RandomGeneration<Integer> randomGenRules) throws IllegalNullInputException {
		try{
			if (referenceFirstObject == null || (SingletonPositionChecker.getInstance().checkVerticalPosition(referenceFirstObject.getYPosition(), benchmarkPoint) == PositionStatus.BELOW)) {
				generateNewFrameAndSetBenchmark(handler,level);
			}
		}
	   catch (Exception e){
		   throw new IllegalNullInputException("One of the input objects or level/handler is null within the list, the Singleton comparator cannot compare null objects",e);
	   } 
    }
	
	/**
	 * Sets a new benchmark/reference point for you to compare to when deciding whether or not you need to generate a new frame
	 * @throws IllegalNullInputException 
	 */
	@Override
	public void setNewFirstBenchmark(GameObject object) throws IllegalNullInputException {
    	try{
    		if(referenceFirstObject == null || (SingletonPositionChecker.getInstance().checkVerticalPosition(object.getYPosition(), referenceFirstObject.getYPosition()) == PositionStatus.ABOVE)) {
    			referenceFirstObject = object;
    		}
    	}
 	   catch (Exception e){
		   throw new IllegalNullInputException("One of the input objects or level/handler is null within the list, the Singleton comparator cannot compare null objects",e);
	   } 
    }
	
	/**
	 * Calculates the new Y-position of the object following the random generation parameters
	 */
	@Override
	public double calculateY(int margin, RandomGeneration<Integer> rg, double buffer) {
		//Cannot be null/have exception, subtracting two doubles
		return benchmarkPoint - buffer;
	}

	/**
	 * Calculates the possible separation distance that you can have between different objects (in this case, Y-separation)
	 */
	@Override
	public int calculateMargin(RandomGeneration<Integer> rg) {
		try{
			int difference = rg.getMaxX() - rg.getMinX();
			return (difference > 0) ? difference : 1;
		}
	   catch (NullPointerException e){
		   throw new IllegalNullInputException("Input List of RandomGeneration is null, make sure you've input proper random generation parameters such that random generation has something baseline off of",e);
	   } 
		
	}

	/**
	 * Calculates the new X-position of the object following the random generation parameters
	 */
	@Override
	public double calculateX(int margin, RandomGeneration<Integer> rg, double buffer) {
		try{
			return RNG.nextInt(margin) + rg.getMinX();
		}
	   catch (NullPointerException e){
		   throw new IllegalNullInputException("Input List of RandomGeneration is null, make sure you've input proper random generation parameters such that random generation has something baseline off of",e);
	   } 
	}
}
