package gameengine.scrolling;

import java.util.List;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;


//This entire file is part of my masterpiece.
//Chalena Scholl


/**
 * This shows that I've learned the importance of abstraction. By creating an abstract class that extends an interface, I ensure that 
 * all the required methods will be implemented. I could also do this by having the various types of scrolling classes simply implement
 * this interface. However, some common methods are shared between the scrolling classes so by creating an abstract class those classes
 * can simply share their common methods. This is better than making each scrolling class implement it's own version of setSpeed which is
 * something that will be the same regardless of which scrolling type class it is being used for. Thus, the implementation of certain 
 * methods can be inherited which allows us to avoid duplicated code. Avoiding duplicated code makes the code base more modular, readable,
 * and easier to change because it would only need to be changed in one place.
 * 
 * Furthermore, the instance variables are all private and getters/setters were only created for the variables that need them.
 * None of the methods are very long and it is easy to see which methods do what, simply because they are appropriately named.
 * 
 * Lastly, this abstract class implements the Scrolling interface, which means that all sub-types of this class can be referred to as a 
 * Scrolling type. This follows the Liskov Substitution principle.Since those sub types of scrolling are created using reflection, 
 * they can easily be replaced with different types of scrolling that do not extend this abstract class as well. 
 * This increases flexibility without changing the functionality of the program itself.
 */

public abstract class GeneralScroll implements Scrolling{
		private ScrollDirection scrollDir;
		private double scrollingSpeed;
		private GameBoundary gameBoundaries;
		
		public GeneralScroll(ScrollDirection dir, double speed, GameBoundary gameBoundaries){
			this.scrollDir = dir;
			this.setScrollingSpeed(speed);
			this.gameBoundaries = gameBoundaries;
		}
		
	
		public void scrollDirection(List<GameObject> gameObjects, double speed){
			  switch (scrollDir) {
			case DOWN:
				scrollDown(gameObjects, speed);
				break;
			case LEFT:
				scrollLeft(gameObjects, speed);
				break;
			case RIGHT:
				scrollRight(gameObjects, speed);
				break;
			case UP:
				scrollUp(gameObjects, speed);
				break;
			  }	
		}
	
	    private void scrollUp(List<GameObject> gameObjects, double speed){
			for(GameObject obstacle: gameObjects){
				double newPos = obstacle.getYPosition() + speed;
				obstacle.setYPosition(newPos);
			}
	    }
	    

	    private void scrollDown(List<GameObject> gameObjects, double speed){
	    	for(GameObject obstacle: gameObjects){
				double newPos = obstacle.getYPosition() - speed;
				obstacle.setYPosition(newPos);
	    	}
	    }

	    private void scrollRight(List<GameObject> gameObjects, double speed){
	        for(GameObject obstacle: gameObjects){
	            double newPos = obstacle.getXPosition() - speed;
	            obstacle.setXPosition(newPos);
	        }
	    }

	    private void scrollLeft(List<GameObject> gameObjects, double speed){
	        for(GameObject obstacle: gameObjects){
	            double newPos = obstacle.getXPosition() + speed;
	            obstacle.setXPosition(newPos);
	        }
	    }
	    
		public void setDirection(ScrollDirection scrollDirection){
			this.scrollDir = scrollDirection;
		}
		
		public ScrollDirection getDirection(){
			return scrollDir;
		}
		
		public GameBoundary getGameBoundaries() {
			return gameBoundaries;
		}


		public double getScrollingSpeed() {
			return scrollingSpeed;
		}

		public void setScrollingSpeed(double scrollingSpeed) {
			this.scrollingSpeed = scrollingSpeed;
		}
}

