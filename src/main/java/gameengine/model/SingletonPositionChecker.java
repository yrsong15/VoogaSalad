package gameengine.model;

//This entire file is part of my masterpiece	
//BRIAN ZHOU

/**Uncollapse this for all details
* What I'm Proud Of:
* 
* Singleton Design Pattern:
* +  I always thought it was just a sketchy way to do globals and wasn't necessary, but after seeing that I was doing the same old boundary
* 	 checking mechanism with >= or <= I realized that across the entire Game Engine part of the project, this class and the SingletonBoundaryChecker all did a good job
* 	  in figuring out what the relative positions were of objects, and thus generalized a ton of code to these two Singleton classes
* +  Plus, an additional benefit is that all of the boundary and position checking are in these classes, so whenever there are changes/extensions that need to be made to position
* 	 checking or modification, they're all located within this class and it can be extended accordingly
* 
* Generic (Type T input parameters to functions): 
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
* 
* Error Checking:
* + Created my own exception in the IllegalNullInputException as when the methods are called, if an error is being caused, it's going to be an error in comparison for the singleton due to the Null reference of one of the objects, so I wrote my own error
*   checking to guide the programmer to the source of the error rather than use generic exceptions (NPE)
*/


//End Masterpiece Header



/**
 * UNCOLLAPSE ABOVE
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
	    * Checks the horizontal position of two objects and returns the correct enumerated state for their relative positions
	    * Class takes in generic inputs so it's flexible to accommodate for any input type
	    * 
	    * @param objectPosition
	    * @param referencePosition
	    * @return proper enumerated relative X position of the object to benchmark
	    * @throws IllegalNullInputException 
	    */
	   public <T extends Comparable<T>> PositionStatus checkHorizontalPosition(T objectPosition, T referencePosition) throws IllegalNullInputException{
		   try{
			   return objectPosition.compareTo(referencePosition) < 0 ? PositionStatus.LEFT : PositionStatus.RIGHT;
		   }
		   catch (NullPointerException e){
			   throw new IllegalNullInputException("One of the input objects is null, benchmark comparison cannot work with a null object",e);
		   }
	   }
	   
	   /**
	    * Checks the vertical position of two objects and returns the correct enumerated state for their relative positions
	    * Class takes in generic inputs so it's flexible to accommodate for any input type
	    * 
	    * @param objectPosition
	    * @param referencePosition
	    * @return proper enumerated relative Y position of the object to benchmark
	    * @throws IllegalNullInputException 
	    */
	   public <T extends Comparable<T>> PositionStatus checkVerticalPosition(T objectPosition, T referencePosition) throws IllegalNullInputException{
		  try{
			  return objectPosition.compareTo(referencePosition) < 0 ? PositionStatus.ABOVE : PositionStatus.BELOW;
		  }
		  catch (NullPointerException e){
			  throw new IllegalNullInputException("One of the input objects is null, benchmark comparison cannot work with a null object",e);
		  }
	   }

}
