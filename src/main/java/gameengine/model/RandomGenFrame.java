package gameengine.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;
import gameengine.controller.interfaces.*;
import gameengine.model.interfaces.RandomInterface;

 // This entire file is part of my masterpiece	
 // BRIAN ZHOU

/**
 * See below for comments on what this class does, and it's role within the entire project
 * 
 * What I'm Proud Of:
 * Underneath I'm listing some of the features I enjoyed building, as well as the decision decisions I went through. More details are in the analysis but this is the TLDR.
 * 
 * 1. Callbacks & Reflection - Going into this class, there were a few interesting decisions about how to communicate with the main client-side controller, and I weighed the options below and finally decided on a callback procedure
 * 		- Option 1: Use Callbacks and Reflection - 
 * 				+ It lets me do everything I want to do in moving all of the object creation methods out of this class and
 * 				  letting this class do all of the random generation, while letting the main controller do all of the creation (encapsulation).
 * 				+ Some of the object creation classes are unique just to this RandomGeneration, so I wanted to keep them private in the main controller to highlight that by keeping the creation within the controller class - through using this reflection
 * 				  method, we can keep the classes private within the controller thereby minimizing method exposure as much as possible, as we only use those methods in here.
 * 					+ One interesting point is that we could have just let the private creation methods be in this class, but it might be confusing in the future if you want to create something, look for the creation classes in the general
 * 					  controller class, but can't find them because they're inside of the RandomGeneration class (here), so I think it's better design to keep all of the creation with the main Controller rather than here with all of our random generation stuff
 * 				+ Looking at this class from a big picture, callback methods are prevalent in many languages are widely used (especially in JS), so I think it's also good practice to use them here even though there are faster/better alternatives in situations
 * 				- Reflection is inherently slow, so it's a bit of sacrificing design for speed, so in the real world we may need to go for a different design
 * 	    - Option 2: Pass an interface of controller and just use the handler for the methods that need to be called
 * 				+ Most of the points are summarized above, but it's faster and is good in the sense that it minimizes exposure of the controller in only passing a few methods, and is much faster than the reflection method that is currently used
 * 				- The methods are required to be public within an interface, so one issue is that the creation methods within the main controller must be public, which seems bad simply because they're never used outside of the method and outside of this class, and are designed for usage with this class. 
 * 				  From a design perspective, I feel that it's better to let the methods be private in the other class to make it clear that it's only meant to be used here, and not for other classes thereby minimizing the exposure. As stated above, a good idea would be to just make the methods private in THIS class, 
 * 				  but that leads to another issue in that the object creation is within this Random Generation class that really just tells you when it's time to make a new frame of random objects, and lets the creators/controllers do all of the object creation
 * 		
 * 		Either way, need to comment the top of the GameEngine controller creation methods to indicate that they are used here, since otherwise it may be misleading/lost in them being private
 * 
 * 2. Generics
 * 	  - One of the huge inconveniences that came up was that sometimes you were comparing doubles with integers, and etc. so you would need to cast them in order to prevent type mismatches. I used generics for this case,
 * 		which can be seen within the gameengine.objects package under RandomGeneration<T> which basically generalizes the input so you can put in any type of object, and ensuring that you won't have type mismatches, and thus duplicated code that handles different types (int, double, etc).
 *	  - A big benefit was never having to typecast, and instead being able to use comparators so that the class was incredibly flexible in its inputs and could be used for comparison of any objects, regardless of what 
 *		values they used to record their numbers/position/frames/timing/etc. 
 *	  - Very extendable in the future so you can apply any type to it and it will easily accomodate, thereby keeping up with the open-closed principle in letting you easily extend random generation
 *    - Soravit wrote the initial non-generic code, but I changed RandomGeneration<T> (gameengine.objects package) to a Generic class to accomodate for more behavior
 * 
 * 3. Layering
 * 	  - Even though this isnt technically an "advanced" Java concept, I'm pretty proud of how the RandomGeneration procedure can layer on different amounts of RandomGeneration, in allowing you to randomly generate enemies on TOP of 
 * 		randomly generated platforms. That way, when you play games like Doodle Jump, not only will the platforms be random, but they also allow you to randomly generate enemies on top of the randomly generated platforms to truly create a 
 * 		unique experience every time. It's also extendable to create other objects in general, and not necessarily enemies in following the open-closed principle so if you really wanted to, you could generate platforms on other platforms or whatever.
 * 
 * 4. Iterator
 * 	  - With the generics addition, the Iterator allows us to be as flexible as possible in going through the RandomGeneration<T> lists and doesn't force us to use an index based data structure in case in the future we need to accommodate
 * 		for other forms of information 
 * 
 * 5. Inheritance Hierarchy 
 * 	  - Finally, there are different forms of RandomGeneration in the sense that you can randomly generate on the X-axis(Flappy Bird) or Y-axis(Doodle Jump), and in my initial design there was a ton of duplicated code so
 * 		I managed to separate out as much code as possible to keep the superclass(this) have the most generalized code possible to reduce duplication.
 * 	  - Used abstract methods and abstracted this class to show that it's here to generalize code, and not to be instantiated as a lone object, as you will either have RandomGeneration either along the X or Y axis (no 3-D game accommodation unless you add a Z-axis)
 *      and in doing so I think I organized code enough so that it's very easy to figure out where you need to go in case you want to add features for one type of random generation
 *	  - I made the abstract methods try and cover as much ground as possible while keeping them flexible such that if you ever needed to add an additional feature for that random generation, you likely wouldn't need to make another method as it should extend from one
 *		of the abstract methods within this class, and most of the specific behavior relevant to directional random generation is within the subclasses.
 * 
 * 6. Error Checking
 *    - Added my own exception (EnemyMisreferencedException) to highlight that when you have an error at a certain point, it's likely due to a reflection or misplaced pointer error, and not the generic runtime issues as they should have occurred previously at a different point
 *    - Added in generic error checking to ensure that reflection went smoothly and didn't have any NPEs.
 */

//---- End Masterpiece Header
 
 
/**
 * Generic Abstract superclass that is responsible for all of the random generation within games such as Doodle Jump, Dance Dance Revolution, Flappy Bird, etc.
 * At certain intervals and proper conditions, method calls for a new frame to be created and added to the scene, and layers randoming
 * such that it can randomly generate enemies on top of other randomly generated objects. Finally, class is also responsible for updating benchmarks such that the
 * random generations are only done when necessary, and at intervals specified within the Game Editor.
 * 
 * @author Brian
 * @method getRandomGenerationRules
 * @method generateNewFrameAndSetBenchmark
 * @method updateReferenceObject
 * @implements RandomInterface, which requires the current RandomGeneration rules
 */

public abstract class RandomGenFrame<T> implements RandomInterface{
	public static final Random RNG = new Random();

    protected Level level;
    protected List<RandomGeneration<Integer>> randomGenRules;
    protected GameObject referenceFirstObject;
    protected boolean generatingEnemies;

    /**
     * Checks conditions and calls proper methods if a new frame needs to be generated
     * @param handler
     * @param randomGenRules
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws EnemyMisreferencedException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public abstract <T extends Comparable<T>> void possiblyGenerateNewFrame (RGInterface handler, RandomGeneration<Integer> randomGenRules) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, EnemyMisreferencedException, NoSuchMethodException, SecurityException;
   
    /**
     * Sets the new benchmark object that indicates when a new frame might need to be generated
     * @param object
     */
    public abstract void setNewFirstBenchmark(GameObject object);
    
    /**
     * @param margin
     * @param elem
     * @param buffer
     * @return The new Y position for a randomized object
     */
    public abstract double calculateY(int margin, RandomGeneration<Integer> elem, double buffer);
    
    /**
     * @param margin
     * @param elem
     * @param buffer
     * @return The new X position for a randomized object
     */
    public abstract double calculateX(int margin, RandomGeneration<Integer> elem, double buffer);
    
    /**
     * @param elem
     * @return distance between the minimum and maximum separation of randomly generated objects
     */
    public abstract int calculateMargin(RandomGeneration<Integer> elem);
    
    /**
     * @return Returns the list of currently enacted rules for generation (parameters)
     */
    public List<RandomGeneration<Integer>> getRandomGenerationRules(){
        return this.randomGenRules;
    }

    /**
     * Method is the heart of random generation, and randomly generates frames when necessary in comparing object positions to relative character position benchmarks, and updating those
     * benchmark objects whenever necessary (object is moving off frame). Finally, it's also responsible for calculating the positions of the new objects per the new frame
     * 
     * @param handler
     * @param level
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws EnemyMisreferencedException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    protected void generateNewFrameAndSetBenchmark(RGInterface handler, Level level) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, EnemyMisreferencedException, NoSuchMethodException, SecurityException {
        List<RandomGeneration<Integer>> randomGenRulesList = randomGenRules;
        List<GameObject> newlyCreatedPlatforms;
        Iterator<RandomGeneration<Integer>> randomGenerationIterator = randomGenRulesList.iterator();
        
        //Here are all of the callback methods that we invoke when we need to, methods within our controller
        Method callbackObject = handler.getClass().getDeclaredMethod("generateObject", new Class[]{Double.TYPE,Double.TYPE,Double.TYPE,Double.TYPE,String.class,Map.class});
        Method callbackEnemy = handler.getClass().getDeclaredMethod("generateEnemyOnPlatform", new Class[]{GameObject.class});
        callbackObject.setAccessible(true); callbackEnemy.setAccessible(true);
        
        while(randomGenerationIterator.hasNext()){
        	RandomGeneration<Integer> elem = randomGenerationIterator.next();
            int platformIndexWithEnemyForThisFrame = RNG.nextInt(elem.getNumObjects()), newSpacing = elem.getMaxSpacing() - elem.getMinSpacing(), buffer = 0, margin;   
            newlyCreatedPlatforms = new ArrayList<>();
           
            //Don't need an iterator here, not iterating over an array but rather X number of times where X is current number of objects
            for(int i=0; i<elem.getNumObjects();i++){
                buffer += RNG.nextInt(newSpacing) + elem.getMinSpacing();
                margin = calculateMargin(elem);
                //Tell Controller to generate a new GameObject/Platform/Obstacle/etc.
                callbackObject.invoke(handler, calculateX(margin,elem,buffer), calculateY(margin,elem,buffer), elem.getWidth(),  elem.getHeight(), elem.getImageURL(),elem.getObjectProperties());  
                updateReferenceObject(handler,newlyCreatedPlatforms);
            }       
            
            if(generatingEnemies)
				try {
					//Tell Controller to generate a new Enemy, layered on a GameObject
					callbackEnemy.invoke(handler, newlyCreatedPlatforms.get(platformIndexWithEnemyForThisFrame));
				} catch (Exception e) {
					//At this point, if we can create a GameObject (invoked right above) without error, then we know it's because the controller misplaced the Enemy pointer, or misnamed method for reflection
					throw new EnemyMisreferencedException("Controller cannot obtain pointer for new Enemy or cannot find method",e);
				}			
        }
    }
    
    private void updateReferenceObject(RGInterface handler, List<GameObject> newlyCreatedPlatforms){
    	newlyCreatedPlatforms.add(handler.getReferenceObject());
        setNewFirstBenchmark(handler.getReferenceObject());
    }

}