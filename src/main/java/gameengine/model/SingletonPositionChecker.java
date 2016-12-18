package gameengine.model;

//This entire file is part of my masterpiece	
//BRIAN ZHOU

/**
* What I'm Proud Of:
* 
* Singleton Design Pattern:
* +  I always thought it was just a sketchy way to do globals and wasn't necessary, but after seeing that I was doing the same old boundary
* 	 checking mechanism with >= or <= I realized that across the entire Game Engine part of the project, this class and the SingletonBoundaryChecker all did a good job
* 	  in figuring out what the relative positions were of objects, and thus generalized a ton of code to these two Singleton classes
* +  Plus, an additional benefit is that all of the boundary and position checking are in these classes, so whenever there are changes/extensions that need to be made to position
* 	 checking or modification, they're all located within this class and it can be extended accordingly
* 
* Generic: 
* + By themselves, generics are already good for type generalizing in that they allow for any type of input to be given to a class.
*   For this class especially, since all boundary and position checking is done within this Singleton class, and thus compartmentalized all within, the boundary and position checking
*   needs to be flexible for different types (especially doubles vs. integers) and the generic methods in the class accommodate for the different type mismatching
* + Comparator is good in that it allows you to properly return the relative results of two numbers without caring about the type, thereby allowing you to generalize
*   even further in not only storing different types of objects/primitives, but also comparing them properly to get the correct behavior
* + Saves you from having to duplicate code in creating similar to identical classes that are specifically designed to work with different types (int, double, float, etc)
* + T generic can accommodate for multiple types of primitives or objects
* 
* Enumerated Types:
* + The only possible 4 instances, Left or Right (X-Position) and Above or Below (Y-Position) are enumerated to highlight that in the present and foreseeable future only those 4 should be necessary for position comparison.
*   For extension, they're easily extended and identified as different from number constants, and would only need to be extended in the case of Z-axis handling for the project (3-D). As a result, the enumerated types
*   do a great job in keeping constant behavior while highlighting the different possible outcomes available from the methods of this class
* + Work well with generics and the Singleton design pattern in encapsulating all of the relevant data/position checking for extension in this class, thus following open/closed principle decently well.
*/

//End Masterpiece Header



/**
 * Class is reponsible for all Position checking, in comparing objects to benchmarks or other objects. Returns an enumerated type that describes the current object relative to its benchmark object in position,
 * and can take in any types due to generic nature with the proper enumerated type response
 * 
 * @author Brian
 * @method SingletonPositionChecker
 * @method checkHorizontalPosition
 * @method checkVerticalPosition
 * @implements SingletonInterface, which requires you to have a method that returns the Singleton instance
 */

public class SingletonPositionChecker<T> implements SingletonInterface{
	public enum PositionStatus{ABOVE,BELOW,LEFT,RIGHT}
	   /**
	    * Initiate the one instance
	    */
	   private static SingletonPositionChecker<?> instance = new SingletonPositionChecker();

	   /**
	    * Private constructor so we can't instantiate it
	    */
	   private SingletonPositionChecker(){}

	   /**
	    * Gets the one instance
	    * @return only instance created
	    */
	   public static SingletonPositionChecker<?> getInstance(){
	      return instance;
	   }
	   
	   /**
	    * 
	    * @param objectPosition
	    * @param referencePosition
	    * @return proper enumerated relative X position of the object to benchmark
	    */
	   public <T extends Comparable<T>> PositionStatus checkHorizontalPosition(T objectPosition, T referencePosition){
		   return objectPosition.compareTo(referencePosition) < 0 ? PositionStatus.LEFT : PositionStatus.RIGHT;
	   }
	   
	   /**
	    * 
	    * @param objectPosition
	    * @param referencePosition
	    * @return proper enumerated relative Y position of the object to benchmark
	    */
	   public <T extends Comparable<T>> PositionStatus checkVerticalPosition(T objectPosition, T referencePosition){
		   return objectPosition.compareTo(referencePosition) < 0 ? PositionStatus.ABOVE : PositionStatus.BELOW;
	   }

}
