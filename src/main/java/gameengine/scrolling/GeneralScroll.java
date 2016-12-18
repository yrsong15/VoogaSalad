package gameengine.scrolling;

import java.util.List;
import gameengine.model.boundary.GameBoundary;
import objects.GameObject;


/**
 * This is a general scrolling class that shifts all objects given to it to give the 
 * appearance of movement to whichever objects are not given to it.
 * @author Chalena Scholl, 
 */
public abstract class GeneralScroll implements Scrolling{
		private ScrollDirection scrollDir;
		private double scrollingSpeed;
		private GameBoundary gameBoundaries;
		
		public GeneralScroll(ScrollDirection dir, double speed, GameBoundary gameBoundaries){
			this.scrollDir = dir;
			this.setScrollingSpeed(speed);
			this.setGameBoundaries(gameBoundaries);
		}
		
		public void setSpeed(double speed) {
			this.setScrollingSpeed(speed);
		}
		
		public void setDirection(ScrollDirection scrollDirection){
			this.scrollDir = scrollDirection;
		}
		
		public ScrollDirection getDirection(){
			return scrollDir;
		}
	
		public void scrollDirection(List<GameObject> gameObjects, double speed){
			System.out.println("scrolling");
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

		public GameBoundary getGameBoundaries() {
			return gameBoundaries;
		}

		public void setGameBoundaries(GameBoundary gameBoundaries) {
			this.gameBoundaries = gameBoundaries;
		}

		public double getScrollingSpeed() {
			return scrollingSpeed;
		}

		public void setScrollingSpeed(double scrollingSpeed) {
			this.scrollingSpeed = scrollingSpeed;
		}
}

