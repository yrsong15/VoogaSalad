package gameengine.controller;

import gameengine.model.SingletonInterface;
import objects.GameObject;

public class SingletonBoundaryChecker<T> implements SingletonInterface{
	public enum IntersectionAmount{NOT_INTERSECTING,PARTIALLY_ABOVE, PARTIALLY_BELOW, COMPLETELY_INSIDE_Y, PARTIALLY_LEFT, PARTIALLY_RIGHT, COMPLETELY_INSIDE_X}

	   //create your singleton instance
	   private static SingletonBoundaryChecker<?> instance = new SingletonBoundaryChecker();

	   //private constructor, can't be instantiated
	   private SingletonBoundaryChecker(){}

	   //Get the only instance available
	   public static SingletonBoundaryChecker<?> getInstance(){
	      return instance;
	   }
	   
	   /*
	    * Actual methods not related to Singleton Stuff
	    * Even though methods are quite similar, didn't want to run into another 20 lines of reflection for "good design"
	    * Way it is it's super self-explanatory and don't need to follow trail for 20 lines, and also covers 4 possible scenarios that 
	    * currently, and will always exist
	    */
	   public IntersectionAmount getHorizontalIntersectionAmount(GameObject mainChar, GameObject obstacle){
		   double mainCharRightEdge = mainChar.getXPosition() + mainChar.getWidth();
		   double obstacleRightEdge = obstacle.getXPosition() + obstacle.getWidth();
		   
		   boolean rightEdgeIsInside = (mainCharRightEdge <= obstacleRightEdge) && (mainCharRightEdge >= obstacle.getXPosition());
		   boolean leftEdgeIsInside = (mainChar.getXPosition() >= obstacle.getXPosition()) && (mainChar.getXPosition() <= obstacleRightEdge);
		   IntersectionAmount intersectionAmount = IntersectionAmount.NOT_INTERSECTING;
		   
		   if(rightEdgeIsInside && leftEdgeIsInside){
			   intersectionAmount = IntersectionAmount.COMPLETELY_INSIDE_X;
		   }
		   else if(leftEdgeIsInside && !rightEdgeIsInside){
			   intersectionAmount = IntersectionAmount.PARTIALLY_RIGHT;
		   }
		   else if(!leftEdgeIsInside && rightEdgeIsInside){
			   intersectionAmount = IntersectionAmount.PARTIALLY_LEFT;
		   }
		   return intersectionAmount;
	   }
	   
	   public IntersectionAmount getVerticalIntersectionAmount(GameObject mainChar, GameObject obstacle){		   
		   boolean topEdgeIsInside = (mainChar.getYPosition() >= obstacle.getYPosition()) && (mainChar.getYPosition() <= (obstacle.getYPosition() + obstacle.getHeight()));   
		   boolean bottomEdgeIsInside = ((mainChar.getYPosition() + mainChar.getHeight()) <= (obstacle.getYPosition() + obstacle.getHeight())) && ((mainChar.getYPosition() + mainChar.getHeight()) >= obstacle.getYPosition());
		   
		   IntersectionAmount intersectionAmount = IntersectionAmount.NOT_INTERSECTING;
		   if(topEdgeIsInside && bottomEdgeIsInside){
			   intersectionAmount = IntersectionAmount.COMPLETELY_INSIDE_Y;
		   }
		   else if(topEdgeIsInside && !bottomEdgeIsInside){
			   intersectionAmount = IntersectionAmount.PARTIALLY_BELOW;
		   }
		   else if(!topEdgeIsInside && bottomEdgeIsInside){
			   intersectionAmount = IntersectionAmount.PARTIALLY_ABOVE;
		   }
		   return intersectionAmount;
	   }
	   
	   public boolean checkIfAnyCollision(GameObject character, GameObject other){		
		   return character.getXPosition() < other.getXPosition() + other.getWidth() && character.getXPosition() + character.getWidth() > other.getXPosition()
			&& character.getYPosition() < other.getYPosition() + other.getHeight() && character.getYPosition() + character.getHeight() > other.getYPosition();
	   }


}
